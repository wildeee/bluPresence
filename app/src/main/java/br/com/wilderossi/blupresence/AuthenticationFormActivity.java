package br.com.wilderossi.blupresence;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.wilderossi.blupresence.api.AuthenticationApi;
import br.com.wilderossi.blupresence.vo.ProfessorVO;
import br.com.wilderossi.blupresence.components.LoaderDialog;
import br.com.wilderossi.blupresence.navigation.SingletonHelper;
import br.com.wilderossi.blupresence.transaction.Instituicao;
import br.com.wilderossi.blupresence.transaction.services.InstituicaoService;
import br.com.wilderossi.blupresence.vo.LoginVO;

public class AuthenticationFormActivity extends BaseActivity {

    private InstituicaoService instituicaoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instituicaoService = new InstituicaoService(getBaseContext());
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

        AuthenticationApi service = new AuthenticationApi(getIntent().getExtras().getString(InstituicaoFormActivity.PARAM_URL_INSTITUICAO), login){
            @Override
            protected void onPostExecute(ProfessorVO prof) {
                loader.cancel();
                Bundle extras = getIntent().getExtras();
                Instituicao instituicao = new Instituicao();
                instituicao.setNome(extras.getString(InstituicaoFormActivity.PARAM_NOME_INSTITUICAO));
                instituicao.setUrl(extras.getString(InstituicaoFormActivity.PARAM_URL_INSTITUICAO));
                instituicao.setIdProfessor(prof.getId());

                instituicaoService.salvar(instituicao);
                Context context = getApplicationContext();
                CharSequence text = "Instituição salva com sucesso!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                AuthenticationFormActivity.this.finish();
                SingletonHelper.instituicaoListActivity.carregaInstituicoes();
            }
        };

        loader.show();
        service.execute();
    }
}
