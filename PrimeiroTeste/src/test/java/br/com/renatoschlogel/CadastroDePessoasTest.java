package br.com.renatoschlogel;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CadastroDePessoasTest {
	
	private CadastroDePessoas cadastroDePessoas;
	
	@BeforeEach
	public void setUp() {
		cadastroDePessoas = new CadastroDePessoas();
	}
	
	@Test
	public void deveCriarOCadastroDePessoas() throws Exception {
		CadastroDePessoas cadastroDePessoas = new CadastroDePessoas();
		Assertions.assertThat(cadastroDePessoas.getPessoas()).isEmpty();
	}

	@Test
	public void deveAdicionarUmaPessoa() throws Exception {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Renato");
		cadastroDePessoas.adicionar(pessoa);
		
		Assertions.assertThat(cadastroDePessoas.getPessoas()).isNotEmpty()
		                                                     .hasSize(1)
		                                                     .contains(pessoa);
	}
	
	@Test()
	public void naoDeveAdicionarPessoaComNomeVazio() throws Exception {
		Pessoa pessoa = new Pessoa();
		
		org.junit.jupiter.api.Assertions.assertThrows(PessoaSemNomeException.class, () -> cadastroDePessoas.adicionar(pessoa) );
	}
	
	@Test
	public void deveRemoverUmaPessoa() throws Exception {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Renato");
		cadastroDePessoas.adicionar(pessoa);
		cadastroDePessoas.remover(pessoa);
		
		Assertions.assertThat(cadastroDePessoas.getPessoas()).isEmpty();
		
	}
	
	@Test()
	public void deveLancarUmaExcecaoAoTentarRemoverUmaPessoaInexistente() {
		Pessoa pessoa = new Pessoa();
		org.junit.jupiter.api.Assertions.assertThrows(PessoaInexistente.class, () -> cadastroDePessoas.remover(pessoa) );
	}
}
