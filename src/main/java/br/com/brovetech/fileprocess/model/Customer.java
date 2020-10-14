package br.com.brovetech.fileprocess.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder
public class Customer {

    private String cnpj;
    private String name;
    private String businessArea;
}
