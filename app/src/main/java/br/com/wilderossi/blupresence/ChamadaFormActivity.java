package br.com.wilderossi.blupresence;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.wilderossi.blupresence.components.ChamadaAlunoAdapter;
import br.com.wilderossi.blupresence.components.DatePickerFragment;
import br.com.wilderossi.blupresence.transaction.Aluno;
import br.com.wilderossi.blupresence.transaction.Chamada;
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
        ChamadaService serviceSQLite = new ChamadaService(getBaseContext());

        chamada.setData(dataChamada);
        chamada.setIdTurma(idTurma);
        chamada.setSincronizado(Boolean.FALSE);

        Long chamadaId = serviceSQLite.salvar(chamada);
        for (Chamada c : serviceSQLite.buscar()){
            Log.v("dataChamada", c.getDataSQLite());
        }

        for (int i = 0; i < alunosListView.getAdapter().getCount(); i++){
            AlunoPresencaVO vo = (AlunoPresencaVO) alunosListView.getItemAtPosition(i);
        }
    }
}
