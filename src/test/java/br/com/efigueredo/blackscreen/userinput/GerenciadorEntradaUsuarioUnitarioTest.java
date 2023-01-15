package br.com.efigueredo.blackscreen.userinput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import br.com.efigueredo.blackscreen.userinput.exception.ExpressaoInvalidaException;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuario;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioParametrosValores;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioValores;

@Tag("unitario")
class GerenciadorEntradaUsuarioUnitarioTest {

	@Mock
	private RecebedorEntradaUsuario recebedor;
	
	@Mock
	private ManipuladorEntradaUsuario manipulador;
	
	@Mock
	private GerenciadorVerificacaoesExpressaoUsuario gerenciadorVerificacoes;
	
	@InjectMocks
	private GerenciadorEntradaUsuario gerenciador;

	@BeforeEach
	public void setup() throws Exception {
		String pacoteRaiz = "br.com.efigueredo.blackscreen.prototipo_configuracao_resposta.correta.unica";
		Reflections reflections = new Reflections(pacoteRaiz, new SubTypesScanner(false), new TypeAnnotationsScanner());
		this.gerenciador = new GerenciadorEntradaUsuario(reflections, pacoteRaiz);
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void deveriaRetornarExpressaoUsuario_QuandoReceberExpressao() {
		String expressao = "comando --parametro valor";
		when(this.recebedor.receberEntrada()).thenReturn(expressao);
		String resultado = this.gerenciador.receberExpressao();
		assertEquals(expressao, resultado);
	}
	
	@Test
	public void deveriaRetornarObjetoExpressaoUsuarioCorreto_DadosExpressaoCorreta_QuandoManipularExpressao_ParametrosValores() throws ExpressaoInvalidaException {
		String expressao = "comando --parametro1 valor1 --param2 \"valor2\" --param3 \"valor3 @\"com@\" detalhes\"";
		Map<String, String> parametrosValores = new HashMap<String, String>();
		parametrosValores.put("--param1", "valor1");
		parametrosValores.put("--param2", "\"valor2\"");
		parametrosValores.put("--param3", "\"valor3 @\"com@\" detalhes\"");
		when(this.manipulador.extrairComando(expressao)).thenReturn("comando");
		when(this.manipulador.extrairParametrosEValores(expressao)).thenReturn(parametrosValores);
		ExpressaoUsuario expressaoUsuario = this.gerenciador.manipularExpressao(expressao);
		assertTrue(expressaoUsuario instanceof ExpressaoUsuarioParametrosValores);
		ExpressaoUsuarioParametrosValores expressaoConvertida = (ExpressaoUsuarioParametrosValores) expressaoUsuario;
		assertEquals("comando", expressaoConvertida.getComando());
		assertEquals(parametrosValores, expressaoConvertida.getParametrosValores());
	}
	
	@Test
	public void deveriaRetornarObjetoExpressaoUsuarioCorreto_DadosExpressaoCorreta_QuandoManipularExpressao_SomenteValores() throws ExpressaoInvalidaException {
		String expressao = "comando valor1 \"valor2\" \"valor3 @\"com@\" detalhes\"";
		List<String> valores = Arrays.asList("valor1", "\"valor2\"", "\"valor3 @\"com@\" detalhes\"");
		when(this.manipulador.extrairComando(expressao)).thenReturn("comando");
		when(this.manipulador.extrairValoresExpressaoSemParametros(expressao)).thenReturn(valores);
		ExpressaoUsuario expressaoUsuario = this.gerenciador.manipularExpressao(expressao);
		assertTrue(expressaoUsuario instanceof ExpressaoUsuarioValores);
		ExpressaoUsuarioValores expressaoConvertida = (ExpressaoUsuarioValores) expressaoUsuario;
		assertEquals("comando", expressaoConvertida.getComando());
		assertEquals(valores, expressaoConvertida.getValores());
	}
	
	@Test
	public void deveriaRetornarObjetoExpressaoUsuarioIncorreto_DadosExpressaoCorreta_QuandoManipularExpressao_ParametrosValores() throws ExpressaoInvalidaException {
		String expressao = "--comando --parametro1 valor1 --param2 \"valor2\" --param3 \"valor3 @\"com@\" detalhes\"";
		Map<String, String> parametrosValores = new HashMap<String, String>();
		parametrosValores.put("--param1", "valor1");
		parametrosValores.put("--param2", "\"valor2\"");
		parametrosValores.put("--param3", "\"valor3 @\"com@\" detalhes\"");
		when(this.manipulador.extrairComando(expressao)).thenReturn("--comando");
		when(this.manipulador.extrairParametrosEValores(expressao)).thenReturn(parametrosValores);
		ExpressaoUsuario expressaoUsuario = this.gerenciador.manipularExpressao(expressao);
		assertTrue(expressaoUsuario instanceof ExpressaoUsuarioParametrosValores);
		ExpressaoUsuarioParametrosValores expressaoConvertida = (ExpressaoUsuarioParametrosValores) expressaoUsuario;
		assertEquals("--comando", expressaoConvertida.getComando());
		assertEquals(parametrosValores, expressaoConvertida.getParametrosValores());
	}
	
	@Test
	public void deveriaRetornarObjetoExpressaoUsuarioIncorreto_DadosExpressaoCorreta_QuandoManipularExpressao_SomenteValores() throws ExpressaoInvalidaException {
		String expressao = "--comando valor1 \"valor2\" \"valor3 @\"com@\" detalhes\"";
		List<String> valores = Arrays.asList("valor1", "\"valor2\"", "\"valor3 @\"com@\" detalhes\"");
		when(this.manipulador.extrairComando(expressao)).thenReturn("--comando");
		when(this.manipulador.extrairValoresExpressaoSemParametros(expressao)).thenReturn(valores);
		ExpressaoUsuario expressaoUsuario = this.gerenciador.manipularExpressao(expressao);
		assertTrue(expressaoUsuario instanceof ExpressaoUsuarioValores);
		ExpressaoUsuarioValores expressaoConvertida = (ExpressaoUsuarioValores) expressaoUsuario;
		assertEquals("--comando", expressaoConvertida.getComando());
		assertEquals(valores, expressaoConvertida.getValores());
	}

}
