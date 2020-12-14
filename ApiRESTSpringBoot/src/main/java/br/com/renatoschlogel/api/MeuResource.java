package br.com.renatoschlogel.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeuResource {

	@GetMapping("/api/clientes/{id}")
	public Cliente buscarCliente(@PathVariable Long id) {
		
		System.out.println(id);
		
		return new Cliente("Renato", "20121212112");
	}
	
}
