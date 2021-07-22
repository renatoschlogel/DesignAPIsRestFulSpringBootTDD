package br.com.renatoschlogel.libraryapi;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import br.com.renatoschlogel.libraryapi.service.EmailService;

@SpringBootApplication
@EnableScheduling
public class LibraryApiApplication {
	
	@Autowired
	private EmailService emailService;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public CommandLineRunner runner() {
		return args -> {
			List<String> emails = Arrays.asList("library-api-866fa3@inbox.mailtrap.io");
			emailService.sendMails("Teste de serviço de email", emails);
			System.out.println("email enviados");
		};
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
	}

}
