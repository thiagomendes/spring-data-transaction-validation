package br.com.tmdev.spring.data.transaction.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringDataTransactionValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataTransactionValidationApplication.class, args);
	}

}
