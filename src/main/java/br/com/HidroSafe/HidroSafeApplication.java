package br.com.HidroSafe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HidroSafeApplication {
	@Value("${spring.datasource.username}")
	private static String dbUser;

	public static void main(String[] args) {
		System.out.println("usuario do banco" + dbUser);
		SpringApplication.run(HidroSafeApplication.class, args);
	}
}
