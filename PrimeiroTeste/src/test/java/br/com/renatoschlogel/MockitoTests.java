package br.com.renatoschlogel;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTests {

	@Mock
	List<String> lista;
	
	@Test
	public void primeiroTesteMockito() throws Exception {
		
		Mockito.when(lista.size()).thenReturn(10);
		
		// Assertions.assertThat(lista.size()).isEqualTo(10);
		
		Mockito.verify(lista).size();
		
	}
}
