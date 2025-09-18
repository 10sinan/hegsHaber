package com.sinan.hegsHaber.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.sinan.hegsHaber") // Taramasi gereken paketlerin ana dizini
@EnableJpaRepositories(basePackages = "com.sinan.hegsHaber.repository") // JPA repository'lerinin bulundugu paket
@EntityScan(basePackages = "com.sinan.hegsHaber.entity") // Entity siniflarinin bulundugu paket
public class HegsHaberApplication {

	public static void main(String[] args) {
		SpringApplication.run(HegsHaberApplication.class, args);
	}// mamın

}
