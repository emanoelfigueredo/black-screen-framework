package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import java.util.Arrays;
import java.util.List;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClasseDeConfiguracaoSemImplementacaoException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClassesDeConfiguracoesInexistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ClassesDeConfiguracoesRespostaInexistentesException;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.MaisDeUmaClasseDeConfiguracaoResposta;

/**
 * <h4>Classe que encapsula os métodos de verificação para a classe
 * {@linkplain CarregadorConfiguracaoResposta}.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class VerificadorClassesConfiguracaoRespostas {

	/**
	 * Verifique se a classe de configuração de resposta implementa a interface
	 * {@linkplain ConfiguracaoResposta}.
	 *
	 * @param classe A classe de configuração de respostas.
	 * @throws ClasseDeConfiguracaoSemImplementacaoException Ocorrerá caso a classe
	 *                                                       de configuração de
	 *                                                       respostas não
	 *                                                       implemente
	 *                                                       {@linkplain ConfiguracaoResposta}.
	 */
	public void verificarSeAClasseImplementaAInterfaceConfiguracaoResposta(Class<?> classe)
			throws ClasseDeConfiguracaoSemImplementacaoException {
		List<Class<?>> interfaces = Arrays.asList(classe.getInterfaces());
		boolean contemInterfaceConfiguracaoResposta = interfaces.contains(ConfiguracaoResposta.class);
		if (!contemInterfaceConfiguracaoResposta) {
			throw new ClasseDeConfiguracaoSemImplementacaoException("A classe de configuracao de resposta "
					+ classe.getName() + " não implementa a interface: ConfiguracaoResposta.");
		}
	}

	/**
	 * Verifique se a lista de classes de configuração vala null.
	 *
	 * @param classesAnotadas Lista de classes de configuração.
	 * @throws ClassesDeConfiguracoesInexistentesException Ocorrerá se a lista de
	 *                                                     classes valer null.
	 */
	public void verificarSeExisteAlgumaClasseDeConfiguracao(List<Class<?>> classesAnotadas)
			throws ClassesDeConfiguracoesInexistentesException {
		if (classesAnotadas.isEmpty()) {
			throw new ClassesDeConfiguracoesInexistentesException();
		}
	}

	/**
	 * Verifique se a lista de classes de configuração de respostas é vazia.
	 *
	 * @param classesDeConfiguracaoMensagem Lista de classes de configuração de
	 *                                      respostas.
	 * @throws ClassesDeConfiguracoesRespostaInexistentesException Ocorrerá se a
	 *                                                             lista de classes
	 *                                                             inserida for
	 *                                                             vazia.
	 */
	public void verificarSeExisteAlgumaClasseDeConfiguracaoRespostas(List<Class<?>> classesDeConfiguracaoMensagem)
			throws ClassesDeConfiguracoesRespostaInexistentesException {
		if (classesDeConfiguracaoMensagem.isEmpty()) {
			throw new ClassesDeConfiguracoesRespostaInexistentesException();
		}
	}

	/**
	 * Verifique se exista mais de uma classe de configuração de respostas.
	 *
	 * @param classesDeConfiguracaoMensagem Lista de classes de configuração de
	 *                                      respostas.
	 * @throws MaisDeUmaClasseDeConfiguracaoResposta Ocorrerá se houver mais de uma
	 *                                               classe de configuração de
	 *                                               respostas na lista.
	 */
	public void verificarSeExisteMaisDeUmaClasseDeConfiguracaoRespostas(List<Class<?>> classesDeConfiguracaoMensagem)
			throws MaisDeUmaClasseDeConfiguracaoResposta {
		if (classesDeConfiguracaoMensagem.size() > 1) {
			throw new MaisDeUmaClasseDeConfiguracaoResposta("Existe " + classesDeConfiguracaoMensagem.size()
					+ " classes de configuração para respostas do sistema. Manenha apenas uma.");
		}
	}

}
