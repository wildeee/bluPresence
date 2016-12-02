package br.com.wilderossi.blupresence;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.wilderossi.blupresence.api.EnviarDadosApi;
import br.com.wilderossi.blupresence.util.ChamadaDateComparator;
import br.com.wilderossi.blupresence.components.SubtitledArrayAdapter;
import br.com.wilderossi.blupresence.components.Toaster;
import br.com.wilderossi.blupresence.navigation.SingletonHelper;
import br.com.wilderossi.blupresence.transaction.Chamada;
import br.com.wilderossi.blupresence.transaction.services.AlunoPresencaService;
import br.com.wilderossi.blupresence.transaction.services.ChamadaService;
import br.com.wilderossi.blupresence.util.DateUtils;
import br.com.wilderossi.blupresence.vo.EnviarDadosErroVO;
import br.com.wilderossi.blupresence.vo.EnviarDadosVO;

public class ChamadaListActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private String url;
    private Long idTurma;
    private String nomeTurma;

    private ListView listViewChamada;

    private Chamada chamadaClicada;

    private ChamadaService chamadaService;
    private AlunoPresencaService alunoPresencaService;

    public static final String TURMA_PARAM = "turma";
    public static final String NOME_TURMA_PARAM = "nomeTurma";
    public static final String CHAMADA_CLICADA = "chamadaClicada";

    @Override
    public int getActivity() {
        return R.layout.chamada_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getStringExtra(savedInstanceState, ChamadaTurmaListActivity.URL_PARAM);
        idTurma = getLongExtra(savedInstanceState, ChamadaTurmaListActivity.TURMA_PARAM);
        nomeTurma = getStringExtra(savedInstanceState, ChamadaTurmaListActivity.NOME_TURMA_PARAM);

        chamadaService = new ChamadaService(this);
        alunoPresencaService = new AlunoPresencaService(this);

        listViewChamada = (ListView) findViewById(R.id.listViewChamadas);
        listViewChamada.setOnItemClickListener(this);
        listViewChamada.setOnItemLongClickListener(this);
        carregaChamadas();
    }

    public void onClickEnviarDados(View view){
        new AlertDialog.Builder(this)
                .setTitle("Enviar dados")
                .setMessage("Após enviar as chamadas para o servidor, não será mais possível alterar ou remover pelo aplicativo. Deseja mesmo realizar a operação?")
                .setPositiveButton("Sim, enviar dados.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final List<EnviarDadosVO> dadosParaEnvio = chamadaService.getDadosParaEnvio(idTurma);
                        EnviarDadosApi enviarDadosApi = new EnviarDadosApi(url, dadosParaEnvio){
                            @Override
                            protected void onPostExecute(List<EnviarDadosErroVO> failedInsertIds) {
                                final List<EnviarDadosVO> dadosSucesso = new ArrayList<EnviarDadosVO>();
                                final List<EnviarDadosVO> dadosErro    = new ArrayList<EnviarDadosVO>();
                                for (EnviarDadosVO dado : dadosParaEnvio){
                                    if (isFalha(failedInsertIds, dado)){
                                        dadosErro.add(dado);
                                    } else {
                                        dadosSucesso.add(dado);
                                    }
                                }
                                chamadaService.setSincronizado(dadosSucesso);
                                carregaChamadas();
                                if (dadosErro.isEmpty()){
                                    Toaster.makeToast(ChamadaListActivity.this, "Sucesso! Chamadas enviadas para a instituição.");
                                    return;
                                }
                                showErrorDialog(dadosErro, failedInsertIds);
                            }
                        };
                        enviarDadosApi.execute();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();

    }

    private void showErrorDialog(List<EnviarDadosVO> dadosErro, List<EnviarDadosErroVO> failedInsertIds) {
        StringBuilder errors = new StringBuilder();
        for (EnviarDadosVO dado : dadosErro){
            for (EnviarDadosErroVO erro : failedInsertIds){
                if (dado.getIdChamadaApp().equals(erro.getIdChamadaApp())){
                    errors.append(DateUtils.getDateString(dado.getData()))
                            .append(" - ")
                            .append(erro.getErro())
                            .append("\n");
                    continue;
                }
            }
        }
        showAlert(errors.toString());
    }

    private void showAlert(String errorMessage) {
        new AlertDialog.Builder(this)
                .setTitle("Erro ao enviar dados")
                .setMessage("Problema ao enviar dados:\n" + errorMessage)
                .setPositiveButton("Ok.", null)
                .show();
    }

    private Boolean isFalha(List<EnviarDadosErroVO> failedInsertIds, EnviarDadosVO envio){
        for (EnviarDadosErroVO falha : failedInsertIds){
            if (falha.getIdChamadaApp().equals(envio.getIdChamadaApp())){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public void onClickNovaChamada(View view){
        chamadaClicada = null;
        redirectTo(ChamadaFormActivity.class);
    }

    @Override
    protected Intent setParameters(Intent intent) {
        SingletonHelper.chamadaListActivity = this;
        intent.putExtra(TURMA_PARAM, idTurma);
        intent.putExtra(NOME_TURMA_PARAM, nomeTurma);
        intent.putExtra(CHAMADA_CLICADA, chamadaClicada != null ? chamadaClicada.getId() : -1L);
        return super.setParameters(intent);
    }

    public void carregaChamadas(){
        List<Chamada> chamadasTurma = chamadaService.findByTurma(idTurma);
        Collections.sort(chamadasTurma, new ChamadaDateComparator());

        listViewChamada.setAdapter(new SubtitledArrayAdapter(
                this,
                R.layout.subtitled_listadapter_layout,
                chamadasTurma
        ));

        Button btnEnviarDados = (Button) findViewById(R.id.btnEnviarDados);
        for (Chamada chamada : chamadasTurma){
            if (!chamada.getSincronizado()){
                btnEnviarDados.setEnabled(Boolean.TRUE);
                return;
            }
        }
        btnEnviarDados.setEnabled(Boolean.FALSE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        chamadaClicada = (Chamada) listViewChamada.getItemAtPosition(position);
        redirectTo(ChamadaFormActivity.class);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final Chamada chamadaLongClicada = (Chamada) listViewChamada.getItemAtPosition(position);
        new AlertDialog.Builder(this)
                .setTitle("Excluir Chamada")
                .setMessage(chamadaLongClicada.getSincronizado() ?
                          "A chamada já foi enviada para o servidor. A exclusão será feita somente no aplicativo"
                        : "Não será possível recuperar os dados após a exclusão.")
                .setPositiveButton("Ok, remover.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alunoPresencaService.removeByChamadaId(chamadaLongClicada.getId());
                        chamadaService.remover(chamadaLongClicada.getId());
                        ChamadaListActivity.this.carregaChamadas();
                        Toaster.makeToast(ChamadaListActivity.this, "Chamada do dia " + chamadaLongClicada.getDataStr() + " removida com sucesso!");
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
        return true;
    }
}
