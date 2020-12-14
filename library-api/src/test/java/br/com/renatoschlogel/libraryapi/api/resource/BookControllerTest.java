package br.com.renatoschlogel.libraryapi.api.resource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

	@Autowired
	MockMvc mvn;
	
	@Test
	@DisplayName("Deve criar um livro com sucesso!")
	public void createBookTest() {
		
	}
	
	@Test
	@DisplayName("Deve lançar erro de validação quando não houver dados suficientes para a criação do livro.")
	void createInvalidBookTest() throws Exception {
		
	}
}
