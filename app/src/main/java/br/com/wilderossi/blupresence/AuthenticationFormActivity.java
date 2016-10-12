package br.com.wilderossi.blupresence;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import br.com.wilderossi.blupresence.api.AuthenticationApi;
import br.com.wilderossi.blupresence.api.ProfessorVO;
import br.com.wilderossi.blupresence.api.StubUtils;
import br.com.wilderossi.blupresence.components.LoaderDialog;
import br.com.wilderossi.blupresence.transaction.Instituicao;
import br.com.wilderossi.blupresence.transaction.services.InstituicaoService;
import br.com.wilderossi.blupresence.vo.LoginVO;

public class AuthenticationFormActivity extends BaseActivity {

    private InstituicaoService alunoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alunoService = new InstituicaoService(getBaseContext());
    }

    @Override
    public int getActivity() {
        return R.layout.auth_form;
    }

    public void onClickAutenticar(View view){

        final LoaderDialog loader = new LoaderDialog(this);

        EditText loginTextView = (EditText) findViewById(R.id.txtLogin);
        EditText senhaTextView = (EditText) findViewById(R.id.txtSenha);

        LoginVO login =  new LoginVO();
        login.setLogin(loginTextView.getText().toString());
        login.setSenha(senhaTextView.getText().toString());

        AuthenticationApi service = new AuthenticationApi(StubUtils.BASE_URL, login){
            @Override
            protected void onPostExecute(ProfessorVO prof) {
                loader.cancel();
                Bundle extras = getIntent().getExtras();
                Instituicao instituicao = new Instituicao();
                instituicao.setNome(extras.getString(InstituicaoFormActivity.PARAM_NOME_INSTITUICAO));
                instituicao.setUrl(extras.getString(InstituicaoFormActivity.PARAM_URL_INSTITUICAO));
                instituicao.setIdProfessor(prof.getId());

                alunoService.salvar(instituicao);
                AuthenticationFormActivity.this.finish();
            }
        };

        loader.show();
        service.execute();
    }
}
