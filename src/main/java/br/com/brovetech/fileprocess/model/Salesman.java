package br.com.brovetech.fileprocess.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder
public class Salesman {

    private String cpf;
    private String name;
    private Double salary;
}
