package br.com.efigueredo.blackscreen.userinput;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.userinput.exception.EntradaUsuarioInvalidaException;

@Tag("integracao")
class GerenciadorVerificacaoesExpressaoUsuarioTest {
	
	private GerenciadorVerificacaoesExpressaoUsuario gerenciador;
	private EntradaUsuario entradaUsuarioCorreta;
	private EntradaUsuario entradaUsuarioComandoIncorreto;
	private EntradaUsuario entradaUsuarioParametroIncorreto;

	@BeforeEach
	void setUp() throws Exception {
		this.gerenciador = new GerenciadorVerificacaoesExpressaoUsuario();
		this.entradaUsuarioCorreta = new EntradaUsuario("comando", Arrays.asList("--param1"), Arrays.asList("valor1"));
		this.entradaUsuarioComandoIncorreto = new EntradaUsuario("--comando", Arrays.asList("--param1"), Arrays.asList("valor1"));
		this.entradaUsuarioParametroIncorreto = new EntradaUsuario("--comando", Arrays.asList("--param1", "--param2"), Arrays.asList("valor1"));
	}

	@Test
	public void deveriaJogarExcecao_QuandoEntardaUsuarioComandoIncorreto() {	
		assertThrows(EntradaUsuarioInvalidaException.class, () -> this.gerenciador.executar(this.entradaUsuarioComandoIncorreto));
	}
	
	@Test
	public void deveriaJogarExcecao_QuandoEntardaUsuarioParametroIncorreto() {	
		assertThrows(EntradaUsuarioInvalidaException.class, () -> this.gerenciador.executar(this.entradaUsuarioParametroIncorreto));
	}
	
	@Test
	public void naoDeveriaJogarExcecao_QuandoEntardaUsuarioCorreta() {	
		assertDoesNotThrow(() -> this.gerenciador.executar(this.entradaUsuarioCorreta));
	}

}
