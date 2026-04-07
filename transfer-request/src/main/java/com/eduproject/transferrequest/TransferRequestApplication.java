package com.eduproject.transferrequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransferRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferRequestApplication.class, args);
    }

}
