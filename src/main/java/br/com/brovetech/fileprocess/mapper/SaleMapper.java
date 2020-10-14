package br.com.brovetech.fileprocess.mapper;

import br.com.brovetech.fileprocess.model.Sale;
import br.com.brovetech.fileprocess.model.SaleItem;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class SaleMapper {

    private static final String SEPARATOR = "ç";
    private static final String ITEM_SEPARATOR = ",";
    private static final String REGEX = "\\[|\\]";
    private static final String EMPTY = "";

    public static Sale toSale(String line){
        List<String> sale = Arrays.asList(line.split(SEPARATOR));
        return Sale.builder()
                .id(Integer.valueOf(sale.get(1)))
                .itens(getSaleItens(sale.get(2)))
                .salesmanName(sale.get(3))
                .build();
    }

    private static List<SaleItem> getSaleItens(String lineItem){
        return Arrays.stream(lineItem.replaceAll(REGEX, EMPTY).split(ITEM_SEPARATOR)).map(SaleItemMapper::toSaleItem).collect(Collectors.toList());
    }
}
