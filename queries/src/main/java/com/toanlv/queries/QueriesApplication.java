package com.toanlv.queries;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class QueriesApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueriesApplication.class, args);
    }

}

@Controller
class GreetingsController{

    @QueryMapping
    String hello(){
        return "Hello World";
    }
    @QueryMapping
    String helloWithName(@Argument String name){
        return "Hello " + name+ "!";
    }

    @QueryMapping
    Customer customerById(@Argument Integer id) {
       return new Customer(id,"A");
    }

    @QueryMapping
    Flux<Customer> customers(){
        return Flux.fromIterable(this.customerList);
    }

    private final List<Customer> customerList = List.of(new Customer(1,"A"), new Customer(2,"B"));
}


@AllArgsConstructor
//@SchemaMapping(typeName = "Customer")
class Customer{
    private Integer id;
    private String name;
}