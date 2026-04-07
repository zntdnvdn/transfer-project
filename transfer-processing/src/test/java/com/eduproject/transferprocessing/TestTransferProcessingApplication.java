package com.eduproject.transferprocessing;

import com.eduproject.transferprocessing.config.TestcontainersConfiguration;
import org.springframework.boot.SpringApplication;

public class TestTransferProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.from(TransferProcessingApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
