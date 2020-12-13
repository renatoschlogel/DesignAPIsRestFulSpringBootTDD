package br.com.renatoschlogel;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockitoTests {

	@Mock
	List<String> lista;
	
	@Test
	public void primeiroTesteMockito() {
		
		lista.size();
		lista.add("");
		
		InOrder inOrder = Mockito.inOrder(lista);
		
		inOrder.verify(lista).size();
		inOrder.verify(lista).add("");
	}
}
