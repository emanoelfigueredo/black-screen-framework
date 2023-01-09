package br.com.efigueredo.blackscreen.comandos.invocacao;

import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.anotacao.Injecao;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>Classe responsável por instânciar o controlador requisitado, com todas as
 * suas depêndencias.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class InstanciadorControlador {

	/** Objeto responsável pela inversão de controle e injeção de dependências. */
	private ContainerIoc containerIoc;

	/**
	 * Construtor.
	 *
	 * @throws PacoteInexistenteException Ocorrerá se o pacote raiz do projeto não
	 *                                    existir no sistema de arquivos do sistema
	 *                                    operacional.
	 */
	public InstanciadorControlador() throws PacoteInexistenteException {
		this.containerIoc = new ContainerIoc();
	}

	/**
	 * Obtenha uma intância do controlador inserido como parâmetro.
	 *
	 * @param controlador Objeto {@linkplain Class} que represente a classe do
	 *                    controlador que deve ser instânciada.
	 * @return Uma intância do controlador.
	 * @throws InversaoDeControleInvalidaException Ocorrerá se houver algum problema
	 *                                             na inversão de controle e injeção
	 *                                             de dependências no momento da
	 *                                             instânciação da classe
	 *                                             Controladora inserida. Podendo
	 *                                             ser:<br>
	 *                                             <ul>
	 *                                             <li>Dois construtores anotados
	 *                                             com {@linkplain Injecao}.</li>
	 *                                             <li>Inexistência de construtor
	 *                                             anotado com {@linkplain Injecao}
	 *                                             e construtor padrão</li>
	 *                                             </ul>
	 * @throws ClasseIlegalParaIntanciaException   Ocorrerá se alguma depêndencia
	 *                                             for uma interface e não houve
	 *                                             classe concetra configurada para
	 *                                             ser instânciada.
	 */
	public Object intanciarControlador(Class<?> controlador)
			throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		return this.containerIoc.getIntancia(controlador);
	}

}
