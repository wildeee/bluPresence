package br.com.wilderossi.blupresence;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import br.com.wilderossi.blupresence.api.AuthenticationApi;
import br.com.wilderossi.blupresence.api.ProfessorVO;
import br.com.wilderossi.blupresence.api.StubUtils;
import br.com.wilderossi.blupresence.vo.LoginVO;

public class AuthenticationFormActivity extends BaseActivity {

    @Override
    public int getActivity() {
        return R.layout.auth_form;
    }

    public void onClickAutenticar(View view){

        EditText loginTextView = (EditText) findViewById(R.id.txtLogin);
        EditText senhaTextView = (EditText) findViewById(R.id.txtSenha);

        LoginVO login =  new LoginVO();
        login.setLogin(loginTextView.getText().toString());
        login.setSenha(senhaTextView.getText().toString());

        AuthenticationApi service = new AuthenticationApi(StubUtils.BASE_URL, login){
            @Override
            protected void onPostExecute(ProfessorVO s) {
                Log.v("AUTENICACAO", s.getLogin());
            }
        };


        service.execute();

    }

}
