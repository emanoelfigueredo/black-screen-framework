package br.com.efigueredo.blackscreen.comandos.invocacao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
			if (metodoComando.getParameterCount() == 1
					&& metodoComando.getParameters()[0].getType().equals(List.class)) {
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

			boolean todosOsParametrosRecebemLista = Stream.of(metodoComando.getParameters())
					.allMatch(parametro -> parametro.getType().equals(List.class));

			if (!todosOsParametrosRecebemLista) {
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
				return;
			}
			
			List<Object> parametrosNaOrdem = new ArrayList<Object>();
			List<Parameter> parametros = Arrays.asList(metodoComando.getParameters());
			for (Parameter parametro : parametros) {
				String nomeParametro = parametro.getAnnotation(Parametro.class).value();
				List<String> valorParaOParametro = parametrosValores.get(nomeParametro);
				parametrosNaOrdem.add((List<String>) valorParaOParametro);
			}
			metodoComando.invoke(objetoControlador, parametrosNaOrdem.toArray());
			return;
			
			

			// quando era somente 1 valor, teste para ver se com todos funciona
//			List<String> listaValoresDoUnicoParametro = parametrosValores.values().stream().toList().get(0);
//			Parameter unicoParametro = metodoComando.getParameters()[0];
//			if(unicoParametro.getType().equals(List.class)) {
//				metodoComando.invoke(objetoControlador, (List<String>) listaValoresDoUnicoParametro);
//				return;
//			}
//			metodoComando.invoke(objetoControlador, listaValoresDoUnicoParametro.get(0));

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InvocacaoComandoInterrompidaException(
					"A invocação do comando de método [" + metodoComando.getName() + "] foi interrompida.\n");
		}
	}

}
