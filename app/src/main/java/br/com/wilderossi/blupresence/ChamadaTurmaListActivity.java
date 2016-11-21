package br.com.wilderossi.blupresence;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import br.com.wilderossi.blupresence.transaction.services.TurmaService;
import br.com.wilderossi.blupresence.vo.TurmaInstituicaoVO;

public class ChamadaTurmaListActivity extends BaseActivity{

    private ListView listView;

    @Override
    public int getActivity() {
        return R.layout.chamada_turma_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TurmaService service = new TurmaService(getBaseContext());

        List<TurmaInstituicaoVO> turmasInstituicaoVO = service.buscarTurmasInstituicao();

        listView = (ListView) findViewById(R.id.listViewChamadaTurma);
    }
}
