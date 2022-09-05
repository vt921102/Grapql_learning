package com.toanlv.mutation;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class MutationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MutationApplication.class, args);
    }

}

@Controller
class MutationsController {
    private final Map<Integer,Customer> db = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    @QueryMapping
    public Customer customerById(@Argument Integer id) {
       return this.db.get(id);
    }

    @QueryMapping
    Flux<Customer> customers(){
        List<Customer> customerList = new ArrayList<>();
        for (Integer i = 1; i <= Integer.valueOf(this.id.toString()); i++) {
            customerList.add(this.db.get(i));
        }
        return Flux.fromIterable(customerList);
    }
    @MutationMapping
    public Customer addCustomer(@Argument String name) {
      var id = this.id.incrementAndGet();
      Customer customer =  new Customer(id,name);
      db.put(id,customer);
      return customer;
    }
}

@AllArgsConstructor
class Customer {
    private Integer id;
    private String name;
}