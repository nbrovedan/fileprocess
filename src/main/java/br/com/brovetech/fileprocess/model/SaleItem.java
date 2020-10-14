package br.com.brovetech.fileprocess.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder
public class SaleItem {

    private Integer id;
    private Double quantity;
    private Double price;
}
