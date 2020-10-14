package br.com.brovetech.fileprocess.utils;

import br.com.brovetech.fileprocess.model.Sale;

import java.util.Comparator;
import java.util.List;

public class SaleBySalesmanComparator implements Comparator<List<Sale>> {
    @Override
    public int compare(List<Sale> list1, List<Sale> list2) {
        return Double.compare(list1.stream().mapToDouble(Sale::getTotalSale).sum(), list2.stream().mapToDouble(Sale::getTotalSale).sum());
    }
}
