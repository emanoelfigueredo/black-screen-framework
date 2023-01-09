package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.ManipuladorConfiguracoesRespostas;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistema;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistemaFactory;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistema.RespostasSistemaBuilder;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClasseDeConfiguracaoSemImplementacaoException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoInterrompidaException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.MaisDeUmaClasseDeConfiguracaoResposta;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;

class RespostasSistemaFactoryTest {
	
	@InjectMocks
	private RespostasSistemaFactory factory;
	
	@Mock
	private ManipuladorConfiguracoesRespostas manipulador;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveriaRetornarObjetoRespostasSistemaPadrao() throws ConfiguracaoInterrompidaException, MaisDeUmaClasseDeConfiguracaoResposta, ClasseDeConfiguracaoSemImplementacaoException, InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		RespostasSistemaBuilder builder = new RespostasSistemaBuilder();
		when(this.manipulador.getBuilder()).thenReturn(builder);
		RespostasSistema resultado = this.factory.getRespostasSistema();
		assertTrue(resultado instanceof RespostasSistema);
	}

}
