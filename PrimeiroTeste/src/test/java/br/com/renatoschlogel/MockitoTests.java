package br.com.renatoschlogel;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTests {

	@Mock
	List<String> lista;
	
	@Test
	public void primeiroTesteMockito() throws Exception {
		
		lista.add("");
		lista.size();
		
		InOrder inOrder = Mockito.inOrder(lista);
		
		inOrder.verify(lista).size();
		inOrder.verify(lista).add("");
	}
}
