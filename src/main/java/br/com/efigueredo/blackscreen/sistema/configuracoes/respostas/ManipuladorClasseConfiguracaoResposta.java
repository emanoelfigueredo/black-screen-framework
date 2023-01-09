package br.com.efigueredo.blackscreen.sistema.configuracoes.respostas;

import java.lang.reflect.Method;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.fontes.IntefaceConfiguracaoResposta;
import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>Classe responsável por manipular os recursos de uma classe de
 * configuração de respostas do sistema.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ManipuladorClasseConfiguracaoResposta {

	/** Objeto responsável pela inversão de controle e injeção de dependências. */
	private ContainerIoc container;

	/**
	 * Construtor.
	 *
	 * @throws PacoteInexistenteException Ocorrerá caso o pacote raiz do projeto não
	 *                                    exista no sistema de arquivos do sistema
	 *                                    operacional.
	 */
	public ManipuladorClasseConfiguracaoResposta() throws PacoteInexistenteException {
		this.container = new ContainerIoc();
	}

	/**
	 * Obtenha uma intância da classe de configuração inserida.
	 *
	 * @param classeConfiguracao Classe de configuração.
	 * @return Intância da classe de configuração inserida.
	 * @throws InversaoDeControleInvalidaException Lançado quando há alguma situação
	 *                                             em que não seja possível realizar
	 *                                             a intanciação do objeto.
	 * @throws ClasseIlegalParaIntanciaException   Lançado quando é requerido uma
	 *                                             intância de interface.
	 */
	public Object getIntanciaClasseConfiguracao(Class<?> classeConfiguracao)
			throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		return this.container.getIntancia(classeConfiguracao);
	}

	/**
	 * Obtenha o método de configuração da classe de configuração de respostas.
	 *
	 * @param classeConfiguracao Classe de configuração de respostas.
	 * @return O método de configuração.
	 * @throws NoSuchMethodException Ocorrerá caso o método
	 *                               {@code configurarRespostas} não exista na
	 *                               classe de configuração.
	 * @throws SecurityException     Lançado pelo gerente de segurança para indicar
	 *                               uma violação de segurança.
	 */
	public Method getMetodoConfiguracao(Class<?> classeConfiguracao) throws NoSuchMethodException, SecurityException {
		return classeConfiguracao.getMethod("configurarRespostas", IntefaceConfiguracaoResposta.class);
	}

}
