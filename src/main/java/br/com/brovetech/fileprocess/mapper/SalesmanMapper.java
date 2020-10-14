package br.com.brovetech.fileprocess.mapper;

import br.com.brovetech.fileprocess.model.Salesman;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class SalesmanMapper {

    private static final String SEPARATOR = "ç";

    public static Salesman toSalesman(String line){
        List<String> salesman = Arrays.asList(line.split(SEPARATOR));

        return Salesman.builder()
                .cpf(salesman.get(1))
                .name(salesman.get(2))
                .salary(Double.valueOf(salesman.get(3)))
                .build();
    }
}
