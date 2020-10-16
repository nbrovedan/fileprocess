package br.com.brovetech.fileprocess;

import br.com.brovetech.fileprocess.model.Salesman;
import br.com.brovetech.fileprocess.parser.SalesmanLineParser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalesmanParserTests {

    @Test
    public void shouldParserNoneSalesman(){
        List<String> lines = Arrays.asList("","");

        List<Salesman> salesmans = new ArrayList<>();
        lines.forEach(line -> SalesmanLineParser.parse(salesmans, line));

        Assert.assertTrue(salesmans.isEmpty());
    }

    @Test
    public void shouldParserNoneSalesmanWithInvalidType(){
        List<String> lines = Arrays.asList("002ç1234567891234çPedroç1250.60","");

        List<Salesman> salesmans = new ArrayList<>();
        lines.forEach(line -> SalesmanLineParser.parse(salesmans, line));

        Assert.assertTrue(salesmans.isEmpty());
    }

    @Test
    public void shouldParserOneSalesmanWithEmptyLine(){
        List<String> lines = Arrays.asList("001ç1234567891234çPedroç1250.60","");

        List<Salesman> salesmans = new ArrayList<>();
        lines.forEach(line -> SalesmanLineParser.parse(salesmans, line));

        Assert.assertEquals(salesmans.size(), 1);
    }

    @Test
    public void shouldParserOneSalesmanWithInvalidLine(){
        List<String> lines = Arrays.asList("001ç1234567891234çPedroç1250.60","001ç3245678865434çPaulo");

        List<Salesman> salesmans = new ArrayList<>();
        lines.forEach(line -> SalesmanLineParser.parse(salesmans, line));

        Assert.assertEquals(salesmans.size(), 1);
    }

    @Test
    public void shouldParserTwoSalesmans(){
        List<String> lines = Arrays.asList("001ç1234567891234çPedroç1250.60","001ç3245678865434çPauloç40000.99");

        List<Salesman> salesmans = new ArrayList<>();
        lines.forEach(line -> SalesmanLineParser.parse(salesmans, line));

        Assert.assertEquals(salesmans.size(), 2);
    }
}
