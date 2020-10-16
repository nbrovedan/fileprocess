package br.com.brovetech.fileprocess.parser;

import br.com.brovetech.fileprocess.enumeration.FileTypeEnum;
import br.com.brovetech.fileprocess.model.Salesman;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import static br.com.brovetech.fileprocess.config.Constants.SEPARATOR;
import static br.com.brovetech.fileprocess.enumeration.ErrorMessageEnum.ERROR_INVALID_LINE;
import static br.com.brovetech.fileprocess.enumeration.ErrorMessageEnum.ERROR_INVALID_TYPE;
import static java.lang.String.format;
import static java.lang.String.join;

@Slf4j
@UtilityClass
public class SalesmanLineParser {

    public static void parse(List<Salesman> salesmans, String line) {
        List<String> salesman = Arrays.asList(line.split(SEPARATOR));

        if(!isValid(salesman)){
            return;
        }

        salesmans.add(Salesman.builder()
                .cpf(salesman.get(1))
                .name(salesman.get(2))
                .salary(Double.valueOf(salesman.get(3)))
                .build());
    }

    private static boolean isValid(List<String> data) {
        FileTypeEnum fileTypeEnum = FileTypeEnum.fromFileCode(data.get(0));

        if(!FileTypeEnum.SALESMAN.equals(fileTypeEnum)){
            log.error(format(ERROR_INVALID_TYPE.toString(), data.get(0)));
            return false;
        }
        if(data.size() < 4){
            log.error(format(ERROR_INVALID_LINE.toString(), join(SEPARATOR, data)));
            return false;
        }
        return true;
    }
}
