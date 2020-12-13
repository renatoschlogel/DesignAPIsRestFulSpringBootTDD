package br.com.renatoschlogel;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class CadastroDePessoasTest {
	
	private CadastroDePessoas cadastroDePessoas;
	
	@Before
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
	
	@Test(expected = PessoaSemNomeException.class)
	public void naoDeveAdicionarPessoaComNomeVazio() throws Exception {
		Pessoa pessoa = new Pessoa();
		cadastroDePessoas.adicionar(pessoa);
		
	}
	
	@Test
	public void deveRemoverUmaPessoa() throws Exception {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Renato");
		cadastroDePessoas.adicionar(pessoa);
		cadastroDePessoas.remover(pessoa);
		
		Assertions.assertThat(cadastroDePessoas.getPessoas()).isEmpty();
		
	}
	
	@Test(expected = PessoaInexistente.class)
	public void deveLancarUmaExcecaoAoTentarRemoverUmaPessoaInexistente() {
		Pessoa pessoa = new Pessoa();
		cadastroDePessoas.remover(pessoa);
	}
}
