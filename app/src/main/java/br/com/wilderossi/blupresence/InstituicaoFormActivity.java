package br.com.wilderossi.blupresence;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import br.com.wilderossi.blupresence.api.TesteConexaoApi;
import br.com.wilderossi.blupresence.components.LoaderDialog;
import br.com.wilderossi.blupresence.transaction.Instituicao;
import br.com.wilderossi.blupresence.util.DateUtils;
import br.com.wilderossi.blupresence.vo.TesteConexaoVO;

public class InstituicaoFormActivity extends BaseActivity {

    public static final String PARAM_NOME_INSTITUICAO = "instituicaoNome";
    public static final String PARAM_URL_INSTITUICAO  = "instituicaoUrl";

    @Override
    public int getActivity() {
        return R.layout.instituicao_form;
    }

    @Override
    protected Intent setParameters(Intent intent) {
        EditText instituicaoField = (EditText) findViewById(R.id.txtInstituicao);
        EditText urlField = (EditText) findViewById(R.id.txtUrl);
        intent.putExtra(PARAM_NOME_INSTITUICAO, instituicaoField.getText().toString());
        intent.putExtra(PARAM_URL_INSTITUICAO, urlField.getText().toString());
        return super.setParameters(intent);
    }

    public void onClickTestarConexao(View view){
        final LoaderDialog loader = new LoaderDialog(this);
        EditText urlField = (EditText) findViewById(R.id.txtUrl);
        TesteConexaoApi service = new TesteConexaoApi(urlField.getText().toString()){
            @Override
            protected void onPostExecute(TesteConexaoVO testeConexaoVO) {
                super.onPostExecute(testeConexaoVO);
                loader.cancel();
                if (testeConexaoVO == null){
                    Context context = InstituicaoFormActivity.this;
                    AlertDialog.Builder urlInvalidaDialog = new AlertDialog.Builder(context);
                    urlInvalidaDialog.setPositiveButton(context.getString(R.string.OK), null);
                    urlInvalidaDialog.setTitle(context.getString(R.string.invalidUrl));
                    urlInvalidaDialog.setMessage(context.getString(R.string.invalidUrlMessage));
                    urlInvalidaDialog.show();
                    return;
                }
                InstituicaoFormActivity.this.redirectTo(AuthenticationFormActivity.class);
                InstituicaoFormActivity.this.finish();
            }
        };

        loader.show();
        service.execute();
    }

}
