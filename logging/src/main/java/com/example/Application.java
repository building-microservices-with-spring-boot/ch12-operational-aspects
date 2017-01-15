package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;

@SpringBootApplication
public class Application {

    @Bean
    public Filter eTagFilter(){
        return new ShallowEtagHeaderFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

