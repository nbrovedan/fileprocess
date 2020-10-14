package br.com.brovetech.fileprocess.utils;

import br.com.brovetech.fileprocess.model.Sale;

import java.util.Comparator;

public class SaleComparator  implements Comparator<Sale> {
    @Override
    public int compare(Sale s1, Sale s2) {
        return Double.compare(s1.getTotalSale(), s2.getTotalSale());
    }
}
