package br.com.wilderossi.blupresence;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.wilderossi.blupresence.transaction.Aluno;
import br.com.wilderossi.blupresence.transaction.Turma;
import br.com.wilderossi.blupresence.transaction.services.AlunoService;
import br.com.wilderossi.blupresence.transaction.services.DatabaseServiceException;
import br.com.wilderossi.blupresence.transaction.services.TurmaService;
import br.com.wilderossi.blupresence.vo.AlunoVO;
import br.com.wilderossi.blupresence.api.AlunosApi;
import br.com.wilderossi.blupresence.vo.TurmaVO;
import br.com.wilderossi.blupresence.components.LoaderDialog;
import br.com.wilderossi.blupresence.navigation.SingletonHelper;

public class TurmaFormActivity extends BaseActivity {

    private Long idInstituicao;
    private TurmaVO turmaVo;
    private List<AlunoVO> alunos;

    private TurmaService turmaSqliteService;
    private AlunoService alunoSqliteService;

    @Override
    public int getActivity() {
        return R.layout.turma_form;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        turmaSqliteService = new TurmaService(getBaseContext());
        alunoSqliteService = new AlunoService(getBaseContext());
        this.turmaVo = SingletonHelper.turmaVO;
        TextView txtTurmaDisabled = (TextView) findViewById(R.id.txtTurmaDisabled);
        txtTurmaDisabled.setText(turmaVo.getDescricao());

        if (turmaVo.getAlunos().isEmpty()){
            Button btnSalvarTurma = (Button) findViewById(R.id.btnSalvarTurma);
            btnSalvarTurma.setClickable(Boolean.FALSE);
            return;
        }

        String baseUrl = super.getStringExtra(savedInstanceState, TurmaListActivity.PARAM_URL_INSTITUICAO);
        idInstituicao = Long.valueOf(getIntExtra(savedInstanceState, TurmaListActivity.PARAM_ID_INSTITUICAO));
        inicializaListagemAlunos(baseUrl);
    }

    private void inicializaListagemAlunos(String baseUrl){
        final LoaderDialog loader = new LoaderDialog(this);
        StringBuilder bufferAlunos = new StringBuilder(turmaVo.getAlunos().get(0));
        for (int i = 1; i < turmaVo.getAlunos().size(); i++)
            bufferAlunos
                    .append(",")
                    .append(turmaVo.getAlunos().get(i));

        String commaSeparatedAlunos = bufferAlunos.toString();
        final ListView listagem = (ListView) findViewById(R.id.listViewTurmasForm);
        AlunosApi service = new AlunosApi(baseUrl, commaSeparatedAlunos){
            @Override
            protected void onPostExecute(List<AlunoVO> alunoVOs) {
                loader.cancel();
                alunos = alunoVOs;
                listagem.setAdapter(
                        new ArrayAdapter<>(
                                TurmaFormActivity.this,
                                android.R.layout.simple_list_item_1,
                                alunoVOs
                        )
                );
            }
        };

        loader.show();
        service.execute();
    }

    public void onClickNovaTurma(View view) throws DatabaseServiceException {
        Turma turma = new Turma();
        turma.setInstituicaoId(idInstituicao);
        turma.setServerId(turmaVo.getId());
        turma.setDescricao(turmaVo.getDescricao());

        Long idTurma = turmaSqliteService.salvar(turma);

        for (AlunoVO alunoVO : alunos){
            Aluno aluno = new Aluno();
            aluno.setNome(alunoVO.getNome());
            aluno.setServerId(alunoVO.getId());
            aluno.setTurmaId(idTurma);

            alunoSqliteService.salvar(aluno);
        }
    }
}
