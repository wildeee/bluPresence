package br.com.wilderossi.blupresence.navigation;

import java.util.UUID;

import br.com.wilderossi.blupresence.ChamadaListActivity;
import br.com.wilderossi.blupresence.InstituicaoListActivity;
import br.com.wilderossi.blupresence.vo.TurmaVO;

public class SingletonHelper {

    public static InstituicaoListActivity instituicaoListActivity;
    public static TurmaVO turmaVO;
    public static ChamadaListActivity chamadaListActivity;
    public static String URL_INSTITUICAO;
    public static final UUID APP_UUID = UUID.fromString("9c2907f9-7ab7-42a9-9130-41ba83ec64ec");
}
