package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistema.RespostasSistemaBuilder;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClasseDeConfiguracaoSemImplementacaoException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClassesDeConfiguracoesInexistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClassesDeConfiguracoesRespostaInexistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoInterrompidaException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.MaisDeUmaClasseDeConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.IntefaceConfiguracaoResposta;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.prototipo.ConfiguracaoRespostaPrototipo1;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;

class ManipuladorConfiguracoesRespostasTest {

	@InjectMocks
	private ManipuladorConfiguracoesRespostas manipulador;

	@Mock
	private CarregadorConfiguracaoResposta carregador;

	@Mock
	private ManipuladorClasseConfiguracaoResposta manipuladorClasse;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveriaRetornarOBuilder_DadoUmMetodoDeConfiguracaoObtido()
			throws NoSuchMethodException, SecurityException, ConfiguracaoInterrompidaException,
			ClassesDeConfiguracoesInexistentesException, ClassesDeConfiguracoesRespostaInexistentesException,
			MaisDeUmaClasseDeConfiguracaoResposta, ClasseDeConfiguracaoSemImplementacaoException,
			InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		Class<?> classeConfiguracao = ConfiguracaoRespostaPrototipo1.class;
		Method metodoConfiguracao = ConfiguracaoRespostaPrototipo1.class.getMethod("configurarRespostas",
				IntefaceConfiguracaoResposta.class);
		ConfiguracaoRespostaPrototipo1 intanciaClasseConfiguracao = new ConfiguracaoRespostaPrototipo1();

		Mockito.<Class<?>>when(this.carregador.getClasseConfiguracaoResposta()).thenReturn(classeConfiguracao);
		when(this.manipuladorClasse.getMetodoConfiguracao(classeConfiguracao)).thenReturn(metodoConfiguracao);
		when(this.manipuladorClasse.getIntanciaClasseConfiguracao(classeConfiguracao))
				.thenReturn(intanciaClasseConfiguracao);

		RespostasSistemaBuilder builder = this.manipulador.getBuilder();
		RespostasSistema respostasSistema = builder.build();
	}

	@Test
	public void deveriaRetornarNull_QuandoNaoHouverClasseDeConfiguracao() throws ClassesDeConfiguracoesInexistentesException, ClassesDeConfiguracoesRespostaInexistentesException, MaisDeUmaClasseDeConfiguracaoResposta, ClasseDeConfiguracaoSemImplementacaoException, ConfiguracaoInterrompidaException, InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		when(this.carregador.getClasseConfiguracaoResposta()).thenThrow(ClassesDeConfiguracoesInexistentesException.class);
		RespostasSistemaBuilder resultado = this.manipulador.getBuilder();
		assertNull(resultado);	
	}

	@Test
	public void deveriaRetornarNull_QuandoNaoHouverClasseDeConfiguracaoRespostas() throws ClassesDeConfiguracoesInexistentesException, ClassesDeConfiguracoesRespostaInexistentesException, MaisDeUmaClasseDeConfiguracaoResposta, ClasseDeConfiguracaoSemImplementacaoException, ConfiguracaoInterrompidaException, InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		when(this.carregador.getClasseConfiguracaoResposta()).thenThrow(ClassesDeConfiguracoesRespostaInexistentesException.class);
		RespostasSistemaBuilder resultado = this.manipulador.getBuilder();
		assertNull(resultado);	
	}

}
