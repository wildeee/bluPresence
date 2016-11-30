package br.com.wilderossi.blupresence.util;

import java.util.Comparator;

import br.com.wilderossi.blupresence.vo.AlunoPresencaVO;

public class AlunoPresencaVOComparator implements Comparator<AlunoPresencaVO> {
    @Override
    public int compare(AlunoPresencaVO lhs, AlunoPresencaVO rhs) {
        return lhs.getAluno().getNome().compareTo(rhs.getAluno().getNome());
    }
}
