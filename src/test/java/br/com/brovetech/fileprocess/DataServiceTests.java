package br.com.brovetech.fileprocess;

import br.com.brovetech.fileprocess.dto.ProcessedDataDTO;
import br.com.brovetech.fileprocess.dto.SalesDTO;
import br.com.brovetech.fileprocess.model.Customer;
import br.com.brovetech.fileprocess.model.Sale;
import br.com.brovetech.fileprocess.model.SaleItem;
import br.com.brovetech.fileprocess.model.Salesman;
import br.com.brovetech.fileprocess.service.DataService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataServiceTests {

    private DataService dataService = new DataService();

    @Test
    public void shouldReturnEmptyResult(){
        SalesDTO salesDTO = SalesDTO.builder()
                .customers(new ArrayList<>())
                .salemans(new ArrayList<>())
                .sales(new ArrayList<>())
                .build();

        ProcessedDataDTO processedDataDTO = dataService.processData(salesDTO);

        assertEquals(processedDataDTO.getNumberOfCustomers(), Integer.valueOf(0));
        assertEquals(processedDataDTO.getNumberOfSalesman(), Integer.valueOf(0));
        Assert.assertNull(processedDataDTO.getMostExpensiveSale());
        Assert.assertNull(processedDataDTO.getWorstSalesman());
    }

    @Test
    public void shouldReturnOneCustomer(){
        SalesDTO salesDTO = SalesDTO.builder()
                .customers(getCustomers(1))
                .salemans(new ArrayList<>())
                .sales(new ArrayList<>())
                .build();

        ProcessedDataDTO processedDataDTO = dataService.processData(salesDTO);

        assertEquals(processedDataDTO.getNumberOfCustomers(), Integer.valueOf(1));
    }

    @Test
    public void shouldReturnOnlyDistinctCustomer(){
        SalesDTO salesDTO = SalesDTO.builder()
                .customers(getCustomers(4))
                .salemans(new ArrayList<>())
                .sales(new ArrayList<>())
                .build();

        ProcessedDataDTO processedDataDTO = dataService.processData(salesDTO);

        assertEquals(processedDataDTO.getNumberOfCustomers(), Integer.valueOf(3));
    }

    @Test
    public void shouldReturnOneSalesman(){
        SalesDTO salesDTO = SalesDTO.builder()
                .customers(new ArrayList<>())
                .salemans(getSalesman(1))
                .sales(new ArrayList<>())
                .build();

        ProcessedDataDTO processedDataDTO = dataService.processData(salesDTO);

        assertEquals(processedDataDTO.getNumberOfSalesman(), Integer.valueOf(1));
    }

    @Test
    public void shouldReturnOnlyDistinctSalesman(){
        SalesDTO salesDTO = SalesDTO.builder()
                .customers(new ArrayList<>())
                .salemans(getSalesman(4))
                .sales(new ArrayList<>())
                .build();

        ProcessedDataDTO processedDataDTO = dataService.processData(salesDTO);

        assertEquals(processedDataDTO.getNumberOfSalesman(), Integer.valueOf(3));
    }

    @Test
    public void shouldReturnMostExpensiveSale(){
        List<Sale> sales = getSales();
        SalesDTO salesDTO = SalesDTO.builder()
                .customers(new ArrayList<>())
                .salemans(new ArrayList<>())
                .sales(sales)
                .build();

        ProcessedDataDTO processedDataDTO = dataService.processData(salesDTO);

        assertEquals(processedDataDTO.getMostExpensiveSale(), sales.get(1));
    }

    @Test
    public void shouldReturnWorstSalesman(){
        List<Salesman> salesmans = getSalesman(4);
        SalesDTO salesDTO = SalesDTO.builder()
                .customers(new ArrayList<>())
                .salemans(salesmans)
                .sales(getSales())
                .build();

        ProcessedDataDTO processedDataDTO = dataService.processData(salesDTO);

        assertEquals(processedDataDTO.getWorstSalesman(), salesmans.get(0));
    }

    private List<Customer> getCustomers(Integer maxItens){
        List<Customer> customers = new ArrayList<>();
        customers.add(Customer.builder()
                .cnpj("64006332000166")
                .name("Jose")
                .businessArea("Rural")
                .build());
        customers.add(Customer.builder()
                .cnpj("05380582000100")
                .name("Joao")
                .businessArea("Cidade")
                .build());
        customers.add(Customer.builder()
                .cnpj("65340009000197")
                .name("Pedro")
                .businessArea("Cidade")
                .build());
        customers.add(Customer.builder()
                .cnpj("64006332000166")
                .name("Jose")
                .businessArea("Rural")
                .build());

        return customers.subList(0, Math.min(4, maxItens));
    }

    private List<Salesman> getSalesman(Integer maxItens){
        List<Salesman> salesman = new ArrayList<>();
        salesman.add(Salesman.builder()
                .cpf("22920432036")
                .name("Paulo")
                .salary(2500.90)
                .build());
        salesman.add(Salesman.builder()
                .cpf("41841205044")
                .name("Mario")
                .salary(5601.00)
                .build());
        salesman.add(Salesman.builder()
                .cpf("58102377038")
                .name("Marcos")
                .salary(3540.50)
                .build());
        salesman.add(Salesman.builder()
                .cpf("22920432036")
                .name("Paulo")
                .salary(2500.90)
                .build());

        return salesman.subList(0, Math.min(4, maxItens));
    }

    private List<Sale> getSales(){
        List<Sale> sales = new ArrayList<>();

        sales.add(Sale.builder()
                .id(1)
                .salesmanName("Paulo")
                .itens(Arrays.asList(
                        SaleItem.builder()
                                .id(1)
                                .quantity(5.0)
                                .price(10.90)
                                .build(),
                        SaleItem.builder()
                                .id(2)
                                .quantity(10.50)
                                .price(0.99)
                                .build()))
                .build());

        sales.add(Sale.builder()
                .id(2)
                .salesmanName("Mario")
                .itens(Collections.singletonList(
                        SaleItem.builder()
                                .id(1)
                                .quantity(5.0)
                                .price(10560.99)
                                .build()))
                .build());

        sales.add(Sale.builder()
                .id(3)
                .salesmanName("Marcos")
                .itens(Arrays.asList(
                        SaleItem.builder()
                                .id(1)
                                .quantity(1000.0)
                                .price(0.10)
                                .build(),
                        SaleItem.builder()
                                .id(2)
                                .quantity(500.0)
                                .price(0.25)
                                .build(),
                        SaleItem.builder()
                                .id(1)
                                .quantity(850.0)
                                .price(0.22)
                                .build()))
                .build());

        return sales;
    }
}
