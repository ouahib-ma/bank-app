package com.cacib.partenaireservice;

import org.springframework.boot.SpringApplication;

public class TestPartenaireServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(PartenaireServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
