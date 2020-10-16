package br.com.brovetech.fileprocess.service;

import br.com.brovetech.fileprocess.dto.ProcessedDataDTO;
import br.com.brovetech.fileprocess.dto.SalesDTO;
import br.com.brovetech.fileprocess.model.Customer;
import br.com.brovetech.fileprocess.model.Sale;
import br.com.brovetech.fileprocess.model.Salesman;
import br.com.brovetech.fileprocess.utils.SaleBySalesmanComparator;
import br.com.brovetech.fileprocess.utils.SaleComparator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static br.com.brovetech.fileprocess.config.Constants.EMPTY;
import static java.util.Map.Entry.comparingByValue;
import static java.util.concurrent.ConcurrentHashMap.newKeySet;
import static java.util.stream.Collectors.groupingBy;

@Service
public class DataService {

    public ProcessedDataDTO processData(SalesDTO salesDTO){
        return ProcessedDataDTO.builder()
                .numberOfCustomers(getDistinctCustomers(salesDTO).size())
                .numberOfSalesman(getDistinctSalesmans(salesDTO).size())
                .mostExpensiveSale(getMostExpensiveSale(salesDTO))
                .worstSalesman(getWorstSalesman(salesDTO))
                .build();
    }

    private static List<Customer> getDistinctCustomers(SalesDTO salesDTO){
        return salesDTO.getCustomers().stream().filter(distinctByField(Customer::getCnpj)).collect(Collectors.toList());
    }

    private static List<Salesman> getDistinctSalesmans(SalesDTO salesDTO){
        return salesDTO.getSalemans().stream().filter(distinctByField(Salesman::getCpf)).collect(Collectors.toList());
    }

    private static Sale getMostExpensiveSale(SalesDTO salesDTO){
        return salesDTO.getSales()
                .stream()
                .max(new SaleComparator())
                .orElse(Sale.builder().build());
    }

    private static Salesman getWorstSalesman(SalesDTO salesDTO){
        Map<String, List<Sale>> salesBySalesman = salesDTO.getSales().stream().filter(distinctByField(Sale::getId)).collect(groupingBy(Sale::getSalesmanName));
        String worstSalesmanName = salesBySalesman.entrySet()
                .stream()
                .min(comparingByValue(new SaleBySalesmanComparator()))
                .map(Map.Entry::getKey)
                .orElse(EMPTY);
        return salesDTO.getSalemans().stream().filter(salesman -> salesman.getName().equalsIgnoreCase(worstSalesmanName)).findFirst().orElse(Salesman.builder().build());
    }

    private static <T> Predicate<T> distinctByField(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
