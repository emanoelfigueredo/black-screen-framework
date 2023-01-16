package br.com.efigueredo.blackscreen.comandos.metodos;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;

import br.com.efigueredo.blackscreen.anotacoes.Parametro;

public class ManipuladorDeParametrosComandos {

	public boolean nomesDeParametrosCorrespondemAListaDeNomesInserida(Set<String> nomesParametros, List<Parameter> parametros) {
		if(nomesParametros.size() != parametros.size()) {
			return false;
		}
		return parametros.stream().allMatch(parametro -> nomesParametros.contains(parametro.getAnnotation(Parametro.class).value()));
	}
	
}
