package br.com.wilderossi.blupresence;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.com.wilderossi.blupresence.transaction.services.InstituicaoService;

public abstract class InstituicaoListBaseActivity extends BaseActivity {

    private InstituicaoService instituicaoService;
    protected ListView listagem;

    protected abstract ListView getListagem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instituicaoService = new InstituicaoService(getBaseContext());
        listagem = getListagem();
        carregaInstituicoes();
    }

    public void carregaInstituicoes(){
        assert listagem != null;
        listagem.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        instituicaoService.buscar()
                )
        );
    }
}
