package com.bancoxyz.banco_xyz_bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bancoxyz.bff", "com.bancoxyz.banco_xyz_bff"})
@EnableJpaRepositories(basePackages = {"com.bancoxyz.bff.repository", "com.bancoxyz.banco_xyz_bff.repository"})
@EntityScan(basePackages = {"com.bancoxyz.bff.model", "com.bancoxyz.banco_xyz_bff.model"})
public class BancoXyzBffApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancoXyzBffApplication.class, args);
	}

}
