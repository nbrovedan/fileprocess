package br.com.brovetech.fileprocess.mapper;


import br.com.brovetech.fileprocess.model.SaleItem;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class SaleItemMapper {

    private static final String SEPARATOR = "-";

    public static SaleItem toSaleItem(String line){
        List<String> item = Arrays.asList(line.split(SEPARATOR));

        return SaleItem.builder()
                .id(Integer.valueOf(item.get(0)))
                .quantity(Double.valueOf(item.get(1)))
                .price(Double.valueOf(item.get(2)))
                .build();
    }
}
