package br.com.efigueredo.blackscreen.comandos.metodos;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.efigueredo.blackscreen.anotacoes.Parametro;

public class ManipuladorDeParametrosComandos {

	public boolean nomesDeParametrosCorrespondemAListaDeNomesInserida(Set<String> nomesParametros,
			List<Parameter> parametros) {
		if (nomesParametros.size() != parametros.size()) {
			return false;
		}
		return parametros.stream()
				.allMatch(parametro -> nomesParametros.contains(parametro.getAnnotation(Parametro.class).value()));
	}

	public Map<String, Class<?>> obterMapaDeRelacaoParametroEClasseInjecao(
			Map<String, List<String>> parametrosEValores) {
		Map<String, Class<?>> mapaParametroClasseNecessaria = new HashMap<String, Class<?>>();
		List<String> parametros = parametrosEValores.keySet().stream().toList();
		parametros.forEach(parametro -> {
			List<String> valoresDoParametro = parametrosEValores.get(parametro);
			if (valoresDoParametro.size() == 1) {
				mapaParametroClasseNecessaria.put(parametro, String.class);
			} else {
				mapaParametroClasseNecessaria.put(parametro, List.class);
			}
		});
		return mapaParametroClasseNecessaria;
	}

	public boolean parametroCorrespondeAoTipoNecessarioParaInjecao(Parameter parametro,
			Map<String, Class<?>> mapaRelacaoParametroClasse) {
		String nomeParametroMetodo = parametro.getAnnotation(Parametro.class).value();
		Class<?> classeQueOParametroDoMetodoDeveReceber = mapaRelacaoParametroClasse.get(nomeParametroMetodo);
		return parametro.getType().equals(classeQueOParametroDoMetodoDeveReceber);
	}
	
	
}
