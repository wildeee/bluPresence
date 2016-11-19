package br.com.wilderossi.blupresence;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.wilderossi.blupresence.api.AlunoVO;
import br.com.wilderossi.blupresence.api.AlunosApi;
import br.com.wilderossi.blupresence.api.TurmaVO;
import br.com.wilderossi.blupresence.components.LoaderDialog;
import br.com.wilderossi.blupresence.navigation.SingletonHelper;

public class TurmaFormActivity extends BaseActivity {

    private TurmaVO turmaVo;
    private List<AlunoVO> alunos;

    @Override
    public int getActivity() {
        return R.layout.turma_form;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.turmaVo = SingletonHelper.turmaVO;
        TextView txtTurmaDisabled = (TextView) findViewById(R.id.txtTurmaDisabled);
        txtTurmaDisabled.setText(turmaVo.getDescricao());

        if (turmaVo.getAlunos().isEmpty()){
            Button btnSalvarTurma = (Button) findViewById(R.id.btnSalvarTurma);
            btnSalvarTurma.setClickable(Boolean.FALSE);
            return;
        }

        String baseUrl = super.getStringExtra(savedInstanceState, TurmaListActivity.PARAM_URL_INSTITUICAO);
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
}
