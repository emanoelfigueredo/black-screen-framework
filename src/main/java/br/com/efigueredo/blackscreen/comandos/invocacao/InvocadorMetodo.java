package br.com.efigueredo.blackscreen.comandos.invocacao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import br.com.efigueredo.blackscreen.anotacoes.Parametro;
import br.com.efigueredo.blackscreen.comandos.invocacao.exception.InvocacaoComandoInterrompidaException;

/**
 * <h4>Classe responsável por invocar os métodos de comando.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class InvocadorMetodo {

	public void invocarComandoSemParametrosDeComando(Object objetoControlador, Method metodoComando,
			List<String> valores) throws InvocacaoComandoInterrompidaException {
		try {
			if (metodoComando.getParameterCount() == 1 && metodoComando.getParameters()[0].getType().equals(List.class)) {
				metodoComando.invoke(objetoControlador, (List<String>) valores);
			} else {
				metodoComando.invoke(objetoControlador, valores.toArray());
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InvocacaoComandoInterrompidaException(
					"A invocação do comando de método [" + metodoComando.getName() + "] foi interrompida.",
					e.getCause());
		}

	}

	public void invocarComandoComParametrosDeComando(Object objetoControlador, Method metodoComando,
			Map<String, List<String>> parametrosValores) throws InvocacaoComandoInterrompidaException {
		try {
			List<Object> parametrosNaOrdem = new ArrayList<Object>();
			List<Parameter> parametros = Arrays.asList(metodoComando.getParameters());
			for (Parameter parametro : parametros) {
				String nomeParametro = parametro.getAnnotation(Parametro.class).value();
				List<String> valorParaOParametro = parametrosValores.get(nomeParametro);
				if (valorParaOParametro.size() == 1) {
					parametrosNaOrdem.add(valorParaOParametro.get(0));
				} else {
					parametrosNaOrdem.add((List<String>) valorParaOParametro);
				}
			}
			metodoComando.invoke(objetoControlador, parametrosNaOrdem.toArray());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InvocacaoComandoInterrompidaException(
					"A invocação do comando de método [" + metodoComando.getName() + "] foi interrompida.",
					e.getCause());
		}
	}

}
