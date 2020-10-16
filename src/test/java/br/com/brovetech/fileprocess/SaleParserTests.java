package br.com.brovetech.fileprocess;

import br.com.brovetech.fileprocess.model.Sale;
import br.com.brovetech.fileprocess.parser.SaleLineParser;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SaleParserTests {

    @Test
    public void shouldParserNoneSales(){
        List<String> lines = Arrays.asList("","");

        List<Sale> sales = new ArrayList<>();
        lines.forEach(line -> SaleLineParser.parse(sales, line));

        assertTrue(sales.isEmpty());
    }

    @Test
    public void shouldParserNoneSalesWithInvalidType(){
        List<String> lines = Arrays.asList("004ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro","");

        List<Sale> sales = new ArrayList<>();
        lines.forEach(line -> SaleLineParser.parse(sales, line));

        assertTrue(sales.isEmpty());
    }

    @Test
    public void shouldParserOneSaleWithEmptyLine(){
        List<String> lines = Arrays.asList("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro","");

        List<Sale> sales = new ArrayList<>();
        lines.forEach(line -> SaleLineParser.parse(sales, line));

        assertEquals(sales.size(), 1);
    }

    @Test
    public void shouldParserOneSaleWithInvalidLine(){
        List<String> lines = Arrays.asList("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro","003ç10ç[1-10-100,2-30-2.50,3-40-3.10]");

        List<Sale> sales = new ArrayList<>();
        lines.forEach(line -> SaleLineParser.parse(sales, line));

        assertEquals(sales.size(), 1);
    }

    @Test
    public void shouldParserTwoSales(){
        List<String> lines = Arrays.asList("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro","003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro");

        List<Sale> sales = new ArrayList<>();
        lines.forEach(line -> SaleLineParser.parse(sales, line));

        assertEquals(sales.size(), 2);
    }

    @Test
    public void shouldParserNoneItemsInSale(){
        List<String> lines = Arrays.asList("003ç10ç[1-10]çPedro");

        List<Sale> sales = new ArrayList<>();
        lines.forEach(line -> SaleLineParser.parse(sales, line));

        System.out.println(sales.get(0).getItens());

        assertTrue(sales.get(0).getItens().isEmpty());
    }

    @Test
    public void shouldParserOneItemInSale(){
        List<String> lines = Arrays.asList("003ç10ç[1-10-100]çPedro");

        List<Sale> sales = new ArrayList<>();
        lines.forEach(line -> SaleLineParser.parse(sales, line));

        assertEquals(sales.get(0).getItens().size(), 1);
    }

    @Test
    public void shouldParserOneItemWithInvalidItemInSale(){
        List<String> lines = Arrays.asList("003ç10ç[1-10-100,1-100]çPedro");

        List<Sale> sales = new ArrayList<>();
        lines.forEach(line -> SaleLineParser.parse(sales, line));

        assertEquals(sales.get(0).getItens().size(), 1);
    }

    @Test
    public void shouldParserTwoItemsInSale(){
        List<String> lines = Arrays.asList("003ç10ç[1-10-100,1-5-100]çPedro");

        List<Sale> sales = new ArrayList<>();
        lines.forEach(line -> SaleLineParser.parse(sales, line));

        assertEquals(sales.get(0).getItens().size(), 2);
    }

    @Test
    public void shouldReturnTotalSale(){
        List<String> lines = Arrays.asList("003ç10ç[1-10-100,1-5-100]çPedro");

        List<Sale> sales = new ArrayList<>();
        lines.forEach(line -> SaleLineParser.parse(sales, line));

        assertEquals(sales.get(0).getTotalSale(), Double.valueOf(1500.00));
    }
}
