package br.com.brovetech.fileprocess;

import br.com.brovetech.fileprocess.model.Customer;
import br.com.brovetech.fileprocess.parser.CustomerLineParser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerParserTests {

    @Test
    public void shouldParserNoneCustomers(){
        List<String> lines = Arrays.asList("","");

        List<Customer> customers = new ArrayList<>();
        lines.forEach(line -> CustomerLineParser.parse(customers, line));

        Assert.assertTrue(customers.isEmpty());
    }

    @Test
    public void shouldParserNoneCustomersWithInvalidType(){
        List<String> lines = Arrays.asList("003ç2345675434544345çJose da SilvaçRural","");

        List<Customer> customers = new ArrayList<>();
        lines.forEach(line -> CustomerLineParser.parse(customers, line));

        Assert.assertTrue(customers.isEmpty());
    }

    @Test
    public void shouldParserOneCustomersWithEmptyLine(){
        List<String> lines = Arrays.asList("002ç2345675434544345çJose da SilvaçRural","");

        List<Customer> customers = new ArrayList<>();
        lines.forEach(line -> CustomerLineParser.parse(customers, line));

        Assert.assertEquals(customers.size(), 1);
    }

    @Test
    public void shouldParserOneCustomersWithInvalidLine(){
        List<String> lines = Arrays.asList("002ç2345675434544345çJose da SilvaçRural","002ç2345675434544345çJose da Silva");

        List<Customer> customers = new ArrayList<>();
        lines.forEach(line -> CustomerLineParser.parse(customers, line));

        Assert.assertEquals(customers.size(), 1);
    }

    @Test
    public void shouldParserTwoCustomers(){
        List<String> lines = Arrays.asList("002ç2345675434544345çJose da SilvaçRural","002ç2345675433444345çEduardo PereiraçRural");

        List<Customer> customers = new ArrayList<>();
        lines.forEach(line -> CustomerLineParser.parse(customers, line));

        Assert.assertEquals(customers.size(), 2);
    }
}
