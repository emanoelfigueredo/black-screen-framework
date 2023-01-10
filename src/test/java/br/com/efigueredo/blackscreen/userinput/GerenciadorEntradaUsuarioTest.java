package br.com.efigueredo.blackscreen.userinput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import br.com.efigueredo.blackscreen.userinput.exception.MaisDeUmParametroNaExpressaoException;

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
	public void deveriaRetornarObjetoEntradaUsuarioCorreto_DadosExpressaoCorreta_QuandoManipularExpressao() throws MaisDeUmParametroNaExpressaoException {
		String expressao = "comando --parametro valor";
		when(this.manipulador.extrairComando(expressao)).thenReturn("comando");
		when(this.manipulador.extrairParametros(expressao)).thenReturn(Arrays.asList("--parametro"));
		when(this.manipulador.extrairValores(expressao)).thenReturn(Arrays.asList("valor"));
		EntradaUsuario entradaUsuarioObj = this.gerenciador.manipularExpressao(expressao);
		assertEquals("comando", entradaUsuarioObj.getComando());
		assertEquals(Arrays.asList("--parametro"), entradaUsuarioObj.getParametros());
		assertEquals(Arrays.asList("valor"), entradaUsuarioObj.getValores());
	}
	
	@Test
	public void DeveriaRetornarObjetoEntradaUsuarioIncorreto_DadosExpressaoIncorreta_QuandoManipularExpressao() throws MaisDeUmParametroNaExpressaoException {
		String expressao = "--comando --parametro1 --parametro2 valor";
		when(this.manipulador.extrairComando(expressao)).thenReturn("--comando");
		when(this.manipulador.extrairParametros(expressao)).thenReturn(Arrays.asList("--parametro1", "--parametro2"));
		when(this.manipulador.extrairValores(expressao)).thenReturn(Arrays.asList("valor"));
		EntradaUsuario entradaUsuarioObj = this.gerenciador.manipularExpressao(expressao);
		assertEquals("--comando", entradaUsuarioObj.getComando());
		assertEquals(Arrays.asList("--parametro1", "--parametro2"), entradaUsuarioObj.getParametros());
		assertEquals(Arrays.asList("valor"), entradaUsuarioObj.getValores());
	}

}
