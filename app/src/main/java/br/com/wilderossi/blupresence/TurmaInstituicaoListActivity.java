package br.com.wilderossi.blupresence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.wilderossi.blupresence.transaction.Instituicao;

public class TurmaInstituicaoListActivity extends InstituicaoListBaseActivity implements AdapterView.OnItemClickListener {

    public static final String PARAM_URL_INSTITUICAO         = "instituicaoUrl";
    public static final String PARAM_IDPROFESSOR_INSTITUICAO = "idProfessor";
    public static final String PARAM_ID_INSTITUICAO          = "idInstituicao";
    private Instituicao instituicao;

    @Override
    public int getActivity() {
        return R.layout.turma_instituicao_list;
    }

    @Override
    protected ListView getListagem() {
        return (ListView) findViewById(R.id.listagemInstituicoesTurma);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listagem.setOnItemClickListener(this);
    }

    @Override
    protected Intent setParameters(Intent intent) {
        intent.putExtra(PARAM_URL_INSTITUICAO, instituicao.getUrl());
        intent.putExtra(PARAM_IDPROFESSOR_INSTITUICAO, instituicao.getIdProfessor());
        intent.putExtra(PARAM_ID_INSTITUICAO, instituicao.getId());
        return super.setParameters(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        instituicao = (Instituicao) listagem.getItemAtPosition(position);
        redirectTo(TurmaListActivity.class);
    }
}
