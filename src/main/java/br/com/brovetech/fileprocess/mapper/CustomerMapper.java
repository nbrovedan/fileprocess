package br.com.brovetech.fileprocess.mapper;

import br.com.brovetech.fileprocess.config.Constants;
import br.com.brovetech.fileprocess.model.Customer;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

import static br.com.brovetech.fileprocess.config.Constants.SEPARATOR;

@UtilityClass
public class CustomerMapper {

    public static Customer toCustomer(String line){
        List<String> customer = Arrays.asList(line.split(SEPARATOR));

        return Customer.builder()
                .cnpj(customer.get(1))
                .name(customer.get(2))
                .businessArea(customer.get(3))
                .build();
    }
}
