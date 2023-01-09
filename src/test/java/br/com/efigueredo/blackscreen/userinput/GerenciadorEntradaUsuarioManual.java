//package br.com.efigueredo.blackscreen.userinput;
//
//import static org.junit.Assert.assertNull;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import java.util.Arrays;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//
//import br.com.efigueredo.blackscreen.userinput.exception.EntradaUsuarioInvalidaException;
//
//@Tag("manual")
//public class GerenciadorEntradaUsuarioManual {
//
//private GerenciadorEntradaUsuario gerenciador = new GerenciadorEntradaUsuario();
//	
//	@BeforeEach
//	public void setup() {
//		this.gerenciador = new GerenciadorEntradaUsuario();
//	}
//	
//	//Copiar e colar expressão no console "comando --param1 valor1 valor2"
//	
//	@Test
//	public void deveriaRetornarObjetoEntradaUsuarioSemJogarExecao_DadoExpressaoCorreta_QuandoGetEntradaUsuario() throws EntradaUsuarioInvalidaException {
//		EntradaUsuario entrada = this.gerenciador.buildEntradaUsuario();
//		assertDoesNotThrow(() -> this.gerenciador.buildEntradaUsuario());
//		assertEquals("comando", entrada.getComando());
//		assertEquals(Arrays.asList("--param1"), entrada.getParametros());
//		assertEquals(Arrays.asList("valor1", "valor2"), entrada.getValores());
//	}
//	
//	//Copiar e colar expressão no console "--comando --param1 valor1 valor2"
//	
//	@Test
//	public void naoDeveriaRetornarObjetoEntradaUsuario_DeveriaJogarExcecao_DadoExpressaoElementoComandoIncorreto_QuandoGetEntradaUsuario() throws EntradaUsuarioInvalidaException {
//		try {
//			EntradaUsuario entrada = this.gerenciador.buildEntradaUsuario();
//			assertThrows(EntradaUsuarioInvalidaException.class, () -> this.gerenciador.buildEntradaUsuario());
//			assertNull(entrada);
//		} catch (EntradaUsuarioInvalidaException e) {}
//	}
//	
//	//Copiar e colar expressão no console comando --param1 --param2 valor1 valor2
//	
//	@Test
//	public void naoDeveriaRetornarObjetoEntradaUsuario_DeveriaJogarExcecao_DadoExpressaoElementoParametroIncorreto_QuandoGetEntradaUsuario() throws EntradaUsuarioInvalidaException {
//		try {
//			EntradaUsuario entrada = this.gerenciador.buildEntradaUsuario();
//			assertThrows(EntradaUsuarioInvalidaException.class, () -> this.gerenciador.buildEntradaUsuario());
//			assertNull(entrada);
//		} catch (EntradaUsuarioInvalidaException e) {}
//	}
//	
//}
