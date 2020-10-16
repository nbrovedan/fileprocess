package br.com.brovetech.fileprocess.mapper;

import br.com.brovetech.fileprocess.config.Constants;
import br.com.brovetech.fileprocess.model.Salesman;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

import static br.com.brovetech.fileprocess.config.Constants.SEPARATOR;

@UtilityClass
public class SalesmanMapper {

    public static Salesman toSalesman(String line){
        List<String> salesman = Arrays.asList(line.split(SEPARATOR));

        return Salesman.builder()
                .cpf(salesman.get(1))
                .name(salesman.get(2))
                .salary(Double.valueOf(salesman.get(3)))
                .build();
    }
}
