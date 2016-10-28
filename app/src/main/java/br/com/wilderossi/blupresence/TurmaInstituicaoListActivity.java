package br.com.wilderossi.blupresence;

import android.widget.ListView;

public class TurmaInstituicaoListActivity extends InstituicaoListBaseActivity {
    @Override
    public int getActivity() {
        return R.layout.turma_instituicao_list;
    }

    @Override
    protected ListView getListagem() {
        return (ListView) findViewById(R.id.listagemInstituicoesTurma);
    }
}
