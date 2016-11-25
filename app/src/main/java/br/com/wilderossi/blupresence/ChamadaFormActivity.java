package br.com.wilderossi.blupresence;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.wilderossi.blupresence.components.ChamadaAlunoAdapter;
import br.com.wilderossi.blupresence.components.DatePickerFragment;
import br.com.wilderossi.blupresence.navigation.SingletonHelper;
import br.com.wilderossi.blupresence.transaction.Aluno;
import br.com.wilderossi.blupresence.transaction.AlunoPresenca;
import br.com.wilderossi.blupresence.transaction.Chamada;
import br.com.wilderossi.blupresence.transaction.services.AlunoPresencaService;
import br.com.wilderossi.blupresence.transaction.services.AlunoService;
import br.com.wilderossi.blupresence.transaction.services.ChamadaService;
import br.com.wilderossi.blupresence.transaction.services.DatabaseServiceException;
import br.com.wilderossi.blupresence.util.DateUtils;
import br.com.wilderossi.blupresence.vo.AlunoPresencaVO;

public class ChamadaFormActivity extends BaseActivity {

    private Long idTurma;
    private Calendar dataChamada;
    private EditText txtDataChamada;
    private ListView alunosListView;

    @Override
    public int getActivity() {
        return R.layout.chamada_form;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = getResources().getConfiguration().locale;
        Locale.setDefault(locale);

        idTurma = getLongExtra(savedInstanceState, ChamadaListActivity.TURMA_PARAM);

        String nomeTurma = getStringExtra(savedInstanceState, ChamadaListActivity.NOME_TURMA_PARAM);
        TextView txtChamadaTurma = (TextView) findViewById(R.id.txtChamadaTurma);
        txtChamadaTurma.setText(nomeTurma);

        txtDataChamada = (EditText) findViewById(R.id.dataChamada);
        dataChamada = Calendar.getInstance();
        txtDataChamada.setText(DateUtils.getDateString(dataChamada));

        alunosListView = (ListView) findViewById(R.id.listViewAlunosChamada);
        List<Aluno> alunos = new AlunoService(this).buscarPorTurma(idTurma);
        List<AlunoPresencaVO> alunosVO = new ArrayList<>();
        for (Aluno aluno : alunos){
            AlunoPresencaVO vo = new AlunoPresencaVO();
            vo.setAluno(aluno);
            alunosVO.add(vo);
        }
        alunosListView.setAdapter(new ChamadaAlunoAdapter(
                this,
                R.layout.chamada_listadapter_layout,
                alunosVO
        ));
    }

    public void onClickDatePicker(View view){
        DialogFragment newFragment = new DatePickerFragment(dataChamada, txtDataChamada);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onClickSalvarChamada(View view) throws DatabaseServiceException {

        Chamada chamada = new Chamada();
        ChamadaService serviceChamadaSQLite = new ChamadaService(getBaseContext());
        AlunoPresencaService serviceAlunoPresencaSQLite = new AlunoPresencaService(getBaseContext());

        chamada.setData(dataChamada);
        chamada.setIdTurma(idTurma);
        chamada.setSincronizado(Boolean.FALSE);

        Long chamadaId = serviceChamadaSQLite.salvar(chamada);

        for (int i = 0; i < alunosListView.getAdapter().getCount(); i++){
            AlunoPresencaVO vo = (AlunoPresencaVO) alunosListView.getItemAtPosition(i);
            AlunoPresenca alunoPresenca = new AlunoPresenca();

            alunoPresenca.setIdAluno(vo.getAluno().getId());
            alunoPresenca.setIdChamada(chamadaId);
            alunoPresenca.setPresente(vo.getPresente());

            serviceAlunoPresencaSQLite.salvar(alunoPresenca);
        }

        Context context = getApplicationContext();
        CharSequence text = "Chamada salva com sucesso!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        SingletonHelper.chamadaListActivity.carregaChamadas();
        this.finish();
    }
}
