package br.com.efigueredo.blackscreen.userinput;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.blackscreen.userinput.exception.ExpressaoInvalidaException;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioParametrosValores;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioValores;

@Tag("integracao")
class GerenciadorVerificacaoesExpressaoUsuarioTest {
	
	private GerenciadorVerificacaoesExpressaoUsuario gerenciador;

	@BeforeEach
	void setUp() throws Exception {
		this.gerenciador = new GerenciadorVerificacaoesExpressaoUsuario();
	}

	@Test
	public void naoDeveriaJogarExcecao_QuandoEntardaUsuarioComandoCorreto() {	
		String comando = "comando";
		List<String> valores = Arrays.asList("valor1", "valor2", "valor3", "valor4");
		ExpressaoUsuarioValores expressao = new ExpressaoUsuarioValores(comando, valores);
		assertDoesNotThrow(() -> this.gerenciador.executar(expressao));
		
		valores = Arrays.asList("valor1", "\"valor2 com detalhes\"", "valor3");
		ExpressaoUsuarioValores expressao2 = new ExpressaoUsuarioValores(comando, valores);
		assertDoesNotThrow(() -> this.gerenciador.executar(expressao2));
		
		valores = Arrays.asList("valor1", "\"valor2 @\"com@\" detalhes\"", "valor3");
		ExpressaoUsuarioValores expressao3 = new ExpressaoUsuarioValores(comando, valores);
		assertDoesNotThrow(() -> this.gerenciador.executar(expressao3));
		
		Map<String, List<String>> parametrosValores = this.getMapaParametrosValores();
		ExpressaoUsuarioParametrosValores expressao4 = new ExpressaoUsuarioParametrosValores(comando, parametrosValores);
		assertDoesNotThrow(() -> this.gerenciador.executar(expressao4));
	}
	
	private Map<String, List<String>> getMapaParametrosValores() {
		Map<String, List<String>> parametrosValores = new HashMap<String, List<String>>();	
		parametrosValores.put("--param1", Arrays.asList("valor1"));
		parametrosValores.put("--param2", Arrays.asList("\"valor2 com detalhes\""));
		parametrosValores.put("--param3", Arrays.asList("\"valor3 @\"com@\" detalhes\""));
		return parametrosValores;
	}
	
	@Test
	public void deveriaJogarExcecao_QuandoEntardaUsuarioComandoIncorreto_ComecaoComDoisHifens() {	
		String comando = "--comando";
		List<String> valores = Arrays.asList("valor1", "valor2", "valor3", "valor4");
		ExpressaoUsuarioValores expressao = new ExpressaoUsuarioValores(comando, valores);
		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.executar(expressao));
		
		valores = Arrays.asList("valor1", "\"valor2 com detalhes\"", "valor3");
		ExpressaoUsuarioValores expressao2 = new ExpressaoUsuarioValores(comando, valores);
		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.executar(expressao2));
		
		valores = Arrays.asList("valor1", "\"valor2 @\"com@\" detalhes\"", "valor3");
		ExpressaoUsuarioValores expressao3 = new ExpressaoUsuarioValores(comando, valores);
		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.executar(expressao3));
		
		Map<String, List<String>> parametrosValores = this.getMapaParametrosValores();
		ExpressaoUsuarioParametrosValores expressao4 = new ExpressaoUsuarioParametrosValores(comando, parametrosValores);
		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.executar(expressao4));
	}

}
