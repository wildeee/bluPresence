package br.com.wilderossi.blupresence;

import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import br.com.wilderossi.blupresence.components.BluetoothServer;
import br.com.wilderossi.blupresence.components.ChamadaAlunoAdapter;
import br.com.wilderossi.blupresence.components.DatePickerFragment;
import br.com.wilderossi.blupresence.components.Toaster;
import br.com.wilderossi.blupresence.navigation.SingletonHelper;
import br.com.wilderossi.blupresence.transaction.Aluno;
import br.com.wilderossi.blupresence.transaction.AlunoPresenca;
import br.com.wilderossi.blupresence.transaction.Chamada;
import br.com.wilderossi.blupresence.transaction.services.AlunoPresencaService;
import br.com.wilderossi.blupresence.transaction.services.AlunoService;
import br.com.wilderossi.blupresence.transaction.services.ChamadaService;
import br.com.wilderossi.blupresence.transaction.services.DatabaseServiceException;
import br.com.wilderossi.blupresence.util.AlunoPresencaVOComparator;
import br.com.wilderossi.blupresence.util.DateUtils;
import br.com.wilderossi.blupresence.vo.AlunoPresencaVO;
import br.com.wilderossi.blupresence.vo.ChamadaEditVO;

public class ChamadaFormActivity extends BaseActivity {

    private Long idTurma;
    private Calendar dataChamada;
    private TextView txtDataChamada;
    private ListView alunosListView;
    private Button btnSalvarChamada;
    private Button btnAbrirConexao;

    private Boolean conexaoAberta;

    private Boolean editMode;
    private ChamadaEditVO chamadaEditVO;

    private ChamadaService serviceChamadaSQLite;

    private BluetoothServer bluetoothServer;

    private String nomeTurma;

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
        Long idChamada = getLongExtra(savedInstanceState, ChamadaListActivity.CHAMADA_CLICADA);
        serviceChamadaSQLite = new ChamadaService(this);

        nomeTurma = getStringExtra(savedInstanceState, ChamadaListActivity.NOME_TURMA_PARAM);
        TextView txtChamadaTurma = (TextView) findViewById(R.id.txtChamadaTurma);
        txtChamadaTurma.setText(nomeTurma);

        txtDataChamada = (TextView) findViewById(R.id.dataChamada);
        alunosListView = (ListView) findViewById(R.id.listViewAlunosChamada);
        btnSalvarChamada = (Button) findViewById(R.id.btnSalvarChamada);
        btnAbrirConexao = (Button) findViewById(R.id.btnAbrirConexao);
        conexaoAberta = Boolean.FALSE;

        editMode = idChamada != -1L;
        if (editMode){
            chamadaEditVO = serviceChamadaSQLite.findById(idChamada);
            setUpViewEdit();
        } else {
            setUpView();
        }
    }

    private void setUpViewEdit() {
        if (chamadaEditVO.getSincronizado()){
            btnSalvarChamada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toaster.makeToast(ChamadaFormActivity.this, "A chamada já foi enviada para o servidor, e não pode ser alterada.");
                }
            });
            btnAbrirConexao.setEnabled(Boolean.FALSE);
        }
        dataChamada = chamadaEditVO.getData();
        txtDataChamada.setText(DateUtils.getDateString(dataChamada));
        Collections.sort(chamadaEditVO.getAlunos(), new AlunoPresencaVOComparator());
        alunosListView.setAdapter(new ChamadaAlunoAdapter(
                this,
                R.layout.chamada_listadapter_layout,
                chamadaEditVO.getAlunos()
        ));
    }

    private void setUpView(){
        dataChamada = Calendar.getInstance();
        txtDataChamada.setText(DateUtils.getDateString(dataChamada));


        List<Aluno> alunos = new AlunoService(this).buscarPorTurma(idTurma);
        List<AlunoPresencaVO> alunosVO = new ArrayList<>();
        for (Aluno aluno : alunos){
            AlunoPresencaVO vo = new AlunoPresencaVO();
            vo.setAluno(aluno);
            alunosVO.add(vo);
        }
        Collections.sort(alunosVO, new AlunoPresencaVOComparator());
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

        if (editMode){
            editaChamada();
        } else {
            salvaNovaChamada();
        }

        SingletonHelper.chamadaListActivity.carregaChamadas();
        this.finish();
    }

    private void editaChamada() throws DatabaseServiceException {
        Chamada chamada = new Chamada();
        AlunoPresencaService serviceAlunoPresencaSQLite = new AlunoPresencaService(this);

        chamada.setData(dataChamada);
        chamada.setIdTurma(chamadaEditVO.getIdTurma());
        chamada.setSincronizado(chamadaEditVO.getSincronizado());
        chamada.setId(chamadaEditVO.getId());

        serviceChamadaSQLite.salvar(chamada);

        for (int i = 0; i < alunosListView.getAdapter().getCount(); i++){
            AlunoPresencaVO vo = (AlunoPresencaVO) alunosListView.getItemAtPosition(i);
            AlunoPresenca alunoPresenca = new AlunoPresenca();

            alunoPresenca.setId(chamadaEditVO.getAlunos().get(i).getId());
            alunoPresenca.setIdAluno(vo.getAluno().getId());
            alunoPresenca.setIdChamada(chamada.getId());
            alunoPresenca.setPresente(vo.getPresente());

            Log.v(vo.getAluno().getNome(), alunoPresenca.getPresente() ? "SIM" : "NAO");

            serviceAlunoPresencaSQLite.salvar(alunoPresenca);
        }

        Toaster.makeToast(getApplicationContext(), "Chamada alterada com sucesso!");
    }

    private void salvaNovaChamada() throws DatabaseServiceException {
        Chamada chamada = new Chamada();
        AlunoPresencaService serviceAlunoPresencaSQLite = new AlunoPresencaService(this);

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

        Toaster.makeToast(getApplicationContext(), "Chamada salva com sucesso!");
    }

    public void onClickAbrirConexao(View view) throws IOException {
        if (!conexaoAberta){
            final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
            if (mBluetoothAdapter == null || mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600);
                startActivity(discoverableIntent);
                return;
            }

            btnAbrirConexao.setText("Fechar conexão");
            mBluetoothAdapter.setName(nomeTurma);
            bluetoothServer = new BluetoothServer(mBluetoothAdapter, this);
            new Thread(bluetoothServer).start();
        } else {
            btnAbrirConexao.setText("Abrir conexão");
            bluetoothServer.cancel();
        }
        conexaoAberta = !conexaoAberta;
    }

    public void insereAluno(final String alunoServerId) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                ListAdapter adapter = alunosListView.getAdapter();
                List<AlunoPresencaVO> alunosPresenca = new ArrayList<>();
                AlunoPresencaVO alunoPresencaVO;
                for (int i = 0; i < adapter.getCount(); i++){
                    alunoPresencaVO = (AlunoPresencaVO) adapter.getItem(i);
                    if (alunoServerId.equals(alunoPresencaVO.getAluno().getServerId())){
                        alunoPresencaVO.setPresente(Boolean.TRUE);
                    }
                    alunosPresenca.add(alunoPresencaVO);
                }
                ChamadaAlunoAdapter chamadaAdapter = new ChamadaAlunoAdapter(
                        ChamadaFormActivity.this,
                        R.layout.chamada_listadapter_layout,
                        alunosPresenca
                );
                alunosListView.setAdapter(chamadaAdapter);
                chamadaAdapter.notifyDataSetChanged();
            }
        });
    }
}
