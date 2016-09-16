package br.com.wilderossi.blupresence;

import android.view.View;
import android.widget.EditText;

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
//        TesteConexaoApi service = new TesteConexaoApi("http://10.0.2.2:3000", loader){
        TesteConexaoApi service = new TesteConexaoApi(urlField.getText().toString(), loader){
            @Override
            protected void onPostExecute(TesteConexaoVO testeConexaoVO) {
                super.onPostExecute(testeConexaoVO);
                loader.cancel();
            }
        };

        loader.show();
        service.execute();
    }

}
