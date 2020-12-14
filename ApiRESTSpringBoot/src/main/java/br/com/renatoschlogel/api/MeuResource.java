package br.com.renatoschlogel.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class MeuResource {
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody Cliente cliente) {
		return cliente;
	}

	@GetMapping("/{id}")
	public Cliente buscar(@PathVariable Long id) {
		System.out.println(id);	
		return new Cliente("Renato", "20121212112");
	}
	
	@PutMapping
	public Cliente atualizar(@RequestBody Cliente cliente) {
		return cliente;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		System.out.println(id);	
	}
	
}
