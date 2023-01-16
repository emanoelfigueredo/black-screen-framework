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
		Map<String, List<String>> parametrosValores = expressaoConvertida2.getParametrosValores();
		this.verificarParametrosValores(parametrosValores);
		
		String expressao3 = "--comando --param1 valor1 \"valor1 com detalhes\" \"valor1 @\"com@\" detalhes\" \"valor1 @\"com@\" detalhes\" "
				+ "--param2 valor2 \"valor2 com detalhes\" \"valor2 @\"com@\" detalhes\" \"valor2 @\"com@\" detalhes\"";
		ExpressaoUsuario expressaoUsuarioObj3 = this.gerenciador.manipularExpressao(expressao3);
		ExpressaoUsuarioParametrosValores expressaoConvertida3 = (ExpressaoUsuarioParametrosValores) expressaoUsuarioObj3;
		assertEquals("--comando", expressaoConvertida2.getComando());
		Map<String, List<String>> parametrosValores2 = expressaoConvertida3.getParametrosValores();
		this.verificarParametrosEVariosValores(parametrosValores2);
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
		Map<String, List<String>> parametrosValores = expressaoConvertida2.getParametrosValores();
		this.verificarParametrosValores(parametrosValores);
		
		String expressao3 = "comando --param1 valor1 \"valor1 com detalhes\" \"valor1 @\"com@\" detalhes\" \"valor1 @\"com@\" detalhes\" "
				+ "--param2 valor2 \"valor2 com detalhes\" \"valor2 @\"com@\" detalhes\" \"valor2 @\"com@\" detalhes\"";
		ExpressaoUsuario expressaoUsuarioObj3 = this.gerenciador.manipularExpressao(expressao3);
		ExpressaoUsuarioParametrosValores expressaoConvertida3 = (ExpressaoUsuarioParametrosValores) expressaoUsuarioObj3;
		assertEquals("comando", expressaoConvertida2.getComando());
		Map<String, List<String>> parametrosValores2 = expressaoConvertida3.getParametrosValores();
		this.verificarParametrosEVariosValores(parametrosValores2);
	}

	private void verificarParametrosValores(Map<String, List<String>> parametrosValores) {
		assertTrue(parametrosValores.size() == 3);
		assertTrue(parametrosValores.get("param1").get(0).equals("valor1"));
		assertTrue(parametrosValores.get("param2").get(0).equals("valor2 com detalhes"));
		assertTrue(parametrosValores.get("param3").get(0).equals("valor3 \"com\" detalhes"));
	}
	
	private void verificarParametrosEVariosValores(Map<String, List<String>> parametrosValores) {
		List<String> parametro1 = parametrosValores.get("param1");
		List<String> parametro2 = parametrosValores.get("param2");
		assertTrue(parametro1.get(0).equals("valor1"));
		assertTrue(parametro1.get(1).equals("valor1 com detalhes"));
		assertTrue(parametro1.get(2).equals("valor1 \"com\" detalhes"));
		assertTrue(parametro1.get(3).equals("valor1 \"com\" detalhes"));
		assertTrue(parametro2.get(0).equals("valor2"));
		assertTrue(parametro2.get(1).equals("valor2 com detalhes"));
		assertTrue(parametro2.get(2).equals("valor2 \"com\" detalhes"));
		assertTrue(parametro2.get(3).equals("valor2 \"com\" detalhes"));
	}

	private void verificarValores(List<String> valores) {
		assertTrue(valores.size() == 3);
		assertTrue(valores.contains("valor1"));
		assertTrue(valores.contains("valor2 com detalhes"));
		assertTrue(valores.contains("valor3 \"com\" detalhes"));
	}

	@Test
	public void deveriaLancarExcecao_QuandoExpressaComParametrosEValoresIncorretos() throws ExpressaoInvalidaException {
		String expressao1 = "comando --param1 valor1 --param2";
		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.manipularExpressao(expressao1),
				"A expressão inserida contém um parâmetro sem valor correspondente");
		
		String expressao3 = "comando --param1 --param2 valor";
		assertThrows(ExpressaoInvalidaException.class, () -> this.gerenciador.manipularExpressao(expressao3),
				"A expressão inserida contém um parâmetro sem valor correspondente");
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

}
