package br.com.brovetech.fileprocess.parser;

import br.com.brovetech.fileprocess.model.Sale;
import br.com.brovetech.fileprocess.model.SaleItem;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.brovetech.fileprocess.config.Constants.SEPARATOR;
import static br.com.brovetech.fileprocess.enumeration.ErrorMessageEnum.ERROR_INVALID_LINE;
import static java.lang.String.format;
import static java.lang.String.join;
import static org.apache.logging.log4j.util.Strings.EMPTY;

@Slf4j
@UtilityClass
public class SaleLineParser {

    private static final String ITEM_SEPARATOR = ",";
    private static final String REGEX = "\\[|\\]";
    private static final String SALE_ITENS_SEPARATOR = "-";

    public static void parse(List<Sale> sales, String line) {
        List<String> sale = Arrays.asList(line.split(SEPARATOR));

        if(!isValid(sale)){
            return;
        }

        sales.add(Sale.builder()
                .id(Integer.valueOf(sale.get(1)))
                .itens(getSaleItens(sale.get(2)))
                .salesmanName(sale.get(3))
                .build());
    }

    private static boolean isValid(List<String> data) {
        if(data.size() < 4){
            log.error(format(ERROR_INVALID_LINE.toString(), join(SEPARATOR, data)));
            return false;
        }
        return true;
    }

    private static List<SaleItem> getSaleItens(String lineItem){
        return Arrays.stream(lineItem.replaceAll(REGEX, EMPTY).split(ITEM_SEPARATOR)).map(SaleLineParser::parseSaleItem).collect(Collectors.toList());
    }

    private static SaleItem parseSaleItem(String line){
        List<String> item = Arrays.asList(line.split(SALE_ITENS_SEPARATOR));

        return SaleItem.builder()
                .id(Integer.valueOf(item.get(0)))
                .quantity(Double.valueOf(item.get(1)))
                .price(Double.valueOf(item.get(2)))
                .build();
    }
}
