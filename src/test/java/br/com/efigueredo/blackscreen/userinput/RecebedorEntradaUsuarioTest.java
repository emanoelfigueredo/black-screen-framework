package br.com.efigueredo.blackscreen.userinput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RecebedorEntradaUsuarioTest {
	
	@InjectMocks
	private RecebedorEntradaUsuario recebedorEntrada;
	
	@Mock
	private Scanner scan;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void deviaRetornarEntradaUsuario() {
		String input = "adicionar --nome nome";
		when(this.scan.nextLine()).thenReturn(input);
		String inputUsuario = this.recebedorEntrada.receberEntrada();
		assertEquals(inputUsuario, input);
	}

}