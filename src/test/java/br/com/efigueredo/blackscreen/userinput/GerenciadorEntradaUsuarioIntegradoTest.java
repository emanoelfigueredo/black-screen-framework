package br.com.efigueredo.blackscreen.userinput;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoRespostaSistemaException;
import br.com.efigueredo.blackscreen.userinput.exception.ExpressaoInvalidaException;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuario;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioParametrosValores;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioValores;
import br.com.efigueredo.container.exception.ContainerIocException;

@Tag("integrado")
public class GerenciadorEntradaUsuarioIntegradoTest {

	private GerenciadorEntradaUsuario gerenciador;

	@BeforeEach
	public void setup() throws ContainerIocException, ConfiguracaoRespostaSistemaException {
		String pacoteRaiz = "br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.correta.unica";
		Reflections reflections = new Reflections(pacoteRaiz, new SubTypesScanner(false), new TypeAnnotationsScanner());
		this.gerenciador = new GerenciadorEntradaUsuario(reflections, pacoteRaiz);
	}

	@Test
	public void deveriaRetornarObjetoEntradaUsuarioIncorreto_DadosExpressaoIncorreta_QuandoManipularExpressao()
			throws ExpressaoInvalidaException {
		String expressao = "--comando valor1 \"valor2 com detalhes\" \"valor3 @\"com@\" detalhes\"";
		ExpressaoUsuario expressaoUsuarioObj = this.gerenciador.manipularExpressao(expressao);
		ExpressaoUsuarioValores expressaoConvertida = (ExpressaoUsuarioValores) expressaoUsuarioObj;
		assertEquals("--comando", expressaoConvertida.getComando());
		List<String> valores = expressaoConvertida.getValores();
		this.verificarValores(valores);

		String expressao2 = "--comando --param1 valor1 --param2 \"valor2 com detalhes\" --param3 \"valor3 @\"com@\" detalhes\"";
		ExpressaoUsuario expressaoUsuarioObj2 = this.gerenciador.manipularExpressao(expressao2);
		ExpressaoUsuarioParametrosValores expressaoConvertida2 = (ExpressaoUsuarioParametrosValores) expressaoUsuarioObj2;
		assertEquals("--comando", expressaoConvertida2.getComando());
		Map<String, String> parametrosValores = expressaoConvertida2.getParametrosValores();
		this.verificarParametrosValores(parametrosValores);
	}

	@Test
	public void deveriaRetornarObjetoEntradaUsuarioCorreto_DadosExpressaoCorreta_QuandoManipularExpressao()
			throws ExpressaoInvalidaException {
		String expressao = "comando valor1 \"valor2 com detalhes\" \"valor3 @\"com@\" detalhes\"";
		ExpressaoUsuario expressaoUsuarioObj = this.gerenciador.manipularExpressao(expressao);
		ExpressaoUsuarioValores expressaoConvertida = (ExpressaoUsuarioValores) expressaoUsuarioObj;
		assertEquals("comando", expressaoConvertida.getComando());
		List<String> valores = expressaoConvertida.getValores();
		this.verificarValores(valores);

		String expressao2 = "comando --param1 valor1 --param2 \"valor2 com detalhes\" --param3 \"valor3 @\"com@\" detalhes\"";
		ExpressaoUsuario expressaoUsuarioObj2 = this.gerenciador.manipularExpressao(expressao2);
		ExpressaoUsuarioParametrosValores expressaoConvertida2 = (ExpressaoUsuarioParametrosValores) expressaoUsuarioObj2;
		assertEquals("comando", expressaoConvertida2.getComando());
		Map<String, String> parametrosValores = expressaoConvertida2.getParametrosValores();
		this.verificarParametrosValores(parametrosValores);
	}

	private void verificarParametrosValores(Map<String, String> parametrosValores) {
		assertTrue(parametrosValores.size() == 3);
		assertTrue(parametrosValores.get("param1").equals("valor1"));
		assertTrue(parametrosValores.get("param2").equals("valor2 com detalhes"));
		assertTrue(parametrosValores.get("param3").equals("valor3 \"com\" detalhes"));
	}

	private void verificarValores(List<String> valores) {
		assertTrue(valores.size() == 3);
		assertTrue(valores.contains("valor1"));
		assertTrue(valores.contains("valor2 com detalhes"));
		assertTrue(valores.contains("valor3 \"com\" detalhes"));
	}

	@Test
	public void deveriaLancarExcecao_QuandoExpressaComParametrosEValoresIncorretos() throws ExpressaoInvalidaException {
//		String expressao1 = "comando --param1 valor1 --param2";
//		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.manipularExpressao(expressao1),
//				"A expressão inserida contém um parâmetro sem valor correspondente");
//
//		String expressao2 = "comando --param1 valor1 valor";
//		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.manipularExpressao(expressao2),
//				"A expressão inserida possui valores inseridos de forma incorreta.");
		
//		String expressao4 = "comando --param1 valor1 \"valor\"";
		
		String expressao4 = "comando --param1 valor --param valor1 valor2 valor3";
		
		ExpressaoUsuario manipularExpressao = this.gerenciador.manipularExpressao(expressao4);
		
		ExpressaoUsuarioParametrosValores e = (ExpressaoUsuarioParametrosValores) manipularExpressao;
		Map<String, List<String>> parametrosValores = e.getParametrosValores();
		
		System.out.println(parametrosValores);
		
//		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.manipularExpressao(expressao4),
//				"A expressão inserida possui valores inseridos de forma incorreta.");

//		String expressao3 = "comando --param1 --param2 valor";
//		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.manipularExpressao(expressao3),
//				"A expressão inserida contém um parâmetro sem valor correspondente");
	}
	
	@Test
	public void naoDeveriaJogarExcecao_QuandoVerificarExpressao_DadoEntradaUsuarioCorreto() throws ExpressaoInvalidaException {
		String expressao = "comando valor1 \"valor2 com detalhes\" \"valor3 @\"com@\" detalhes\"";
		ExpressaoUsuario expressaoUsuarioObj = this.gerenciador.manipularExpressao(expressao);
		assertDoesNotThrow(() -> this.gerenciador.executarVerificacoesExpressao(expressaoUsuarioObj));
		
		String expressao2 = "comando --param1 valor1 --param2 \"valor2 com detalhes\" --param3 \"valor3 @\"com@\" detalhes\"";
		ExpressaoUsuario expressaoUsuarioObj2 = this.gerenciador.manipularExpressao(expressao2);
		assertDoesNotThrow(() -> this.gerenciador.executarVerificacoesExpressao(expressaoUsuarioObj2));
		
		String expressao3 = "comando valor1 \"valor2 com detalhes\" \"valor3 @\"com@\" detalhes\"";
		ExpressaoUsuario expressaoUsuarioObj3 = this.gerenciador.manipularExpressao(expressao3);
		assertDoesNotThrow(() -> this.gerenciador.executarVerificacoesExpressao(expressaoUsuarioObj3));
	}
	
	@Test
	public void deveriaJogarExcecao_QuandoVerificarExpressao_DadoEntradaUsuarioIncorretoComando() throws ExpressaoInvalidaException {
		String expressao = "--comando valor1 \"valor2 com detalhes\" \"valor3 @\"com@\" detalhes\"";
		ExpressaoUsuario expressaoUsuarioObj = this.gerenciador.manipularExpressao(expressao);
		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.executarVerificacoesExpressao(expressaoUsuarioObj));
		
		String expressao2 = "--comando --param1 valor1 --param2 \"valor2 com detalhes\" --param3 \"valor3 @\"com@\" detalhes\"";
		ExpressaoUsuario expressaoUsuarioObj2 = this.gerenciador.manipularExpressao(expressao2);
		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.executarVerificacoesExpressao(expressaoUsuarioObj2));
		
		String expressao3 = "--comando valor1 \"valor2 com detalhes\" \"valor3 @\"com@\" detalhes\"";
		ExpressaoUsuario expressaoUsuarioObj3 = this.gerenciador.manipularExpressao(expressao3);
		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.executarVerificacoesExpressao(expressaoUsuarioObj3));
	}	
//	@Test
//	public void deveriaJogarExcecao_QuandoVerificarExpressao_DadoEntradaUsuarioIncorretoParametro() {
//		EntradaUsuario entradaUsuario = new EntradaUsuario("comando", Arrays.asList("--param1", "--param2"), Arrays.asList("valor"));
//		assertThrows(EntradaUsuarioInvalidaException.class, () -> this.gerenciador.executarVerificacoesExpressao(entradaUsuario));
//	}
//	
//	@Test
//	public void deveriaJogarExcecao_QuandoVerificarExpressao_DadoEntradaUsuarioIncorretoComandoParametro() {
//		EntradaUsuario entradaUsuario = new EntradaUsuario("--comando", Arrays.asList("--param1", "--param2"), Arrays.asList("valor"));
//		assertThrows(EntradaUsuarioInvalidaException.class, () -> this.gerenciador.executarVerificacoesExpressao(entradaUsuario));
//	}

}
