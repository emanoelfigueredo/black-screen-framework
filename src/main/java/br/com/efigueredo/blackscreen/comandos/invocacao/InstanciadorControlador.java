package br.com.efigueredo.blackscreen.comandos.invocacao;

import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ContainerIocException;

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
	 * @throws ContainerIocException Erro no container Ioc.
	 */
	public InstanciadorControlador(String pacoteRaiz) throws ContainerIocException {
		this.containerIoc = new ContainerIoc(pacoteRaiz);
	}

	/**
	 * Obtenha uma intância do controlador inserido como parâmetro.
	 *
	 * @param controlador Objeto {@linkplain Class} que represente a classe do
	 *                    controlador que deve ser instânciada.
	 * @return Uma intância do controlador.
	 * @throws ContainerIocException Erro no container Ioc.
	 */
	public Object intanciarControlador(Class<?> controlador) throws ContainerIocException {
		return this.containerIoc.getInstancia(controlador);
	}

}
