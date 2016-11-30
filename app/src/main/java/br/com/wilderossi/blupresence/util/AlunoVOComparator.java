package br.com.wilderossi.blupresence.util;

import java.util.Comparator;

import br.com.wilderossi.blupresence.vo.AlunoVO;

public class AlunoVOComparator implements Comparator<AlunoVO> {
    @Override
    public int compare(AlunoVO lhs, AlunoVO rhs) {
        return lhs.getNome().compareTo(rhs.getNome());
    }
}
