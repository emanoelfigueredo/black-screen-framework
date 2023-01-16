package br.com.efigueredo.blackscreen.comandos.invocacao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import br.com.efigueredo.blackscreen.comandos.invocacao.exception.InvocacaoComandoInterrompidaException;

/**
 * <h4>Classe responsável por invocar os métodos de comando.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class InvocadorMetodo {

	/**
	 * Invoque o método de comando.
	 * 
	 * Seu funcionamento consiste em invocar o método com ou sem parâmetros.
	 *
	 * @param objetoControlador Instância da classe controladora que contém o
	 *                          método.
	 * @param metodoComando     Objeto {@linkplain Method} que representa o método
	 *                          do comando.
	 * @param valores           Lista de objetos necessários para preencher os
	 *                          parâmetros do método.
	 * @throws InvocacaoComandoInterrompidaException Ocorrerá se houver alguma falha
	 *                                               na invocação do comando. A
	 *                                               cuasa estára indicada na stack
	 *                                               trace.
	 */
	public void invocar(Object objetoControlador, Method metodoComando, List<String> valores)
			throws InvocacaoComandoInterrompidaException {
		try {
			if (valores.isEmpty()) {
				metodoComando.invoke(objetoControlador);
				return;
			}
			metodoComando.invoke(objetoControlador, valores.toArray());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InvocacaoComandoInterrompidaException(
					"A invocação do comando de método [" + metodoComando.getName() + "] foi interrompida.",
					e.getCause());
		}
	}

}
