package br.com.renatoschlogel;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrimeiroTest {
	
	private Calculadora calculadora;
	
	@BeforeEach
	public void setUp() {
		calculadora = new Calculadora();
	}

    @Test
    public void deveSomarDoisNumeros(){
        int resultado  = calculadora.somar(1, 5);
        Assertions.assertThat(resultado).isEqualTo(6);
    }
    
    @Test
	public void deveDominuirOSegundoNumeroDoPrimeiro() {
		int resultado = calculadora.subtrair(10, 2);
		Assertions.assertThat(resultado).isEqualTo(8);
	}
    
    @Test
	public void deveDividirOPrimeiroNumeroPeloSegundo() {
		int resultado = calculadora.dividir(10, 2);
		Assertions.assertThat(resultado).isEqualTo(5);
	}
    
    @Test
	public void deveLancarUmaExcecaoAoTentarDividirPorZero() {
    	org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> calculadora.dividir(10, 0) );
	}
    
    @Test
	public void deveMultiplicarDoisNumeros() {
		int resultado = calculadora.multiplicar(10, 2);
		Assertions.assertThat(resultado).isEqualTo(20);
	}
}

class Calculadora{
	
    public int somar(int primeiroNumero, int segundoNumero) {
        return primeiroNumero + segundoNumero;
    }

	public int multiplicar(int primeiroNumero, int segundoNumero) {
		return primeiroNumero * segundoNumero;
	}

	public int dividir(int primeiroNumero, int segundoNumero) {
		if (segundoNumero == 0) {
			throw new RuntimeException("Não é possivel fazer divisão por zero!");
		}
		return primeiroNumero / segundoNumero;
	}

	public int subtrair(int primeiroNumero, int segundoNumero) {
		return primeiroNumero - segundoNumero;
	}
}