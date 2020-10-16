package br.com.brovetech.fileprocess.parser;

import br.com.brovetech.fileprocess.model.Customer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import static br.com.brovetech.fileprocess.config.Constants.SEPARATOR;
import static br.com.brovetech.fileprocess.enumeration.ErrorMessageEnum.ERROR_INVALID_LINE;
import static java.lang.String.format;
import static java.lang.String.join;

@Slf4j
@UtilityClass
public class CustomerLineParser {

    public static void parse(List<Customer> customers, String line) {
        List<String> customer = Arrays.asList(line.split(SEPARATOR));

        if(!isValid(customer)){
            return;
        }

        customers.add(Customer.builder()
                .cnpj(customer.get(1))
                .name(customer.get(2))
                .businessArea(customer.get(3))
                .build());
    }

    private static boolean isValid(List<String> data) {
        if(data.size() < 4){
            log.error(format(ERROR_INVALID_LINE.toString(), join(SEPARATOR, data)));
            return false;
        }
        return true;
    }
}
