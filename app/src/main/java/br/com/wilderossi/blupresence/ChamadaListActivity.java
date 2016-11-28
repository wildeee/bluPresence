package br.com.wilderossi.blupresence;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.wilderossi.blupresence.components.SubtitledArrayAdapter;
import br.com.wilderossi.blupresence.components.Toaster;
import br.com.wilderossi.blupresence.navigation.SingletonHelper;
import br.com.wilderossi.blupresence.transaction.Chamada;
import br.com.wilderossi.blupresence.transaction.services.AlunoPresencaService;
import br.com.wilderossi.blupresence.transaction.services.ChamadaService;

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
        listViewChamada.setAdapter(new SubtitledArrayAdapter(
                this,
                R.layout.subtitled_listadapter_layout,
                chamadasTurma
        ));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        chamadaClicada = (Chamada) listViewChamada.getItemAtPosition(position);
        redirectTo(ChamadaFormActivity.class);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir Chamada")
                .setMessage("Não será possível recuperar os dados após a exclusão.")
                .setPositiveButton("Ok, remover.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Chamada chamadaLongClicada = (Chamada) listViewChamada.getItemAtPosition(position);
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
