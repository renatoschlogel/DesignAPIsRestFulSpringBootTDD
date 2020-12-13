package br.com.renatoschlogel;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

public class MockitoTests {

	
	@Test
	public void primeiroTesteMockito() throws Exception {
		
		List<String> lista = Mockito.mock(ArrayList.class);
		
		Mockito.when(lista.size()).thenReturn(10);
		
		Assertions.assertThat(lista.size()).isEqualTo(10);
		
	}
}
