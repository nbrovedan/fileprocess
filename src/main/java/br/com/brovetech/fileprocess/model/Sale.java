package br.com.brovetech.fileprocess.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.util.List;

@Data
@With
@Builder
public class Sale {

    private Integer id;
    private List<SaleItem> itens;
    private String salesmanName;

    public Double getTotalSale(){
        return itens.stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
    }
}
