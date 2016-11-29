package br.com.wilderossi.blupresence.components;

import java.util.Comparator;

import br.com.wilderossi.blupresence.transaction.Chamada;

public class ChamadaDateComparator implements Comparator<Chamada> {
    @Override
    public int compare(Chamada lhs, Chamada rhs) {
        return rhs.getData().compareTo(lhs.getData());
    }
}
