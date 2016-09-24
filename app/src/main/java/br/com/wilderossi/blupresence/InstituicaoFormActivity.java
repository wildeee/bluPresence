package br.com.wilderossi.blupresence;

import android.view.View;
import android.widget.EditText;

import br.com.wilderossi.blupresence.api.StubUtils;
import br.com.wilderossi.blupresence.api.TesteConexaoApi;
import br.com.wilderossi.blupresence.components.LoaderDialog;
import br.com.wilderossi.blupresence.vo.TesteConexaoVO;

public class InstituicaoFormActivity extends BaseActivity {

    @Override
    public int getActivity() {
        return R.layout.instituicao_form;
    }

    public void onClickTestarConexao(View view){
        final LoaderDialog loader = new LoaderDialog(this);
        EditText urlField = (EditText) findViewById(R.id.txtUrl);
        TesteConexaoApi service = new TesteConexaoApi(StubUtils.BASE_URL){
//        TesteConexaoApi service = new TesteConexaoApi(urlField.getText().toString()){
            @Override
            protected void onPostExecute(TesteConexaoVO testeConexaoVO) {
                super.onPostExecute(testeConexaoVO);
                loader.cancel();
                InstituicaoFormActivity.this.redirectTo(AuthenticationFormActivity.class);
            }
        };

        loader.show();
        service.execute();
    }

}
