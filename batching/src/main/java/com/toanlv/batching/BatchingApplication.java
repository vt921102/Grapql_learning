package com.toanlv.batching;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class BatchingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchingApplication.class, args);
    }

}


@Controller
class BatchController {

    @QueryMapping
    public Flux<Customer> customers() {
        return Flux.fromIterable(this.customerList);
    }

    @BatchMapping
    Map<Customer, Account> account(List<Customer> customers) {
        System.out.println("Calling account from customer");
        return customers.stream().collect(Collectors.toMap(customer -> customer,
                customer -> new Account(customer.getId())));
    }

/* BatchMapping is the same the below code
    @SchemaMapping(typeName = "Customer")
    Account account(Customer customer) {
        return new Account(customer.getId());
    }
*/

    private List<Customer> customerList = List.of(new Customer(1, "A"), new Customer(2, "B"));
}


@AllArgsConstructor
@Getter
class Customer {
    private Integer id;
    private String name;
}


@AllArgsConstructor
class Account {
    private Integer id;
}