package br.com.efigueredo.blackscreen.comandos.metodos;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

public class ManipuladorDeMetodos {

	public List<Method> extrairMetodosPelaQuantidadeDeParametros(List<Method> metodos, int quantidadeParametros) {
		return metodos.stream().filter(metodo -> metodo.getParameterCount() == quantidadeParametros).toList();
	}

	public List<Method> extrairMetodosQueNaoRecebemAClasseNosParametros(List<Method> metodos,
			Class<?> classeQueNaoDeveReceberNoParametro) {
		return metodos.stream().filter(metodo -> Stream.of(metodo.getParameters())
				.allMatch(p -> !p.getType().equals(classeQueNaoDeveReceberNoParametro))).toList();
	}

	public List<Method> extrairMetodosQueSoRecebamUmParametro(List<Method> metodos) {
		return metodos.stream().filter(metodo -> metodo.getParameterCount() == 1).toList();
	}

	public List<Method> extrairMetodosTodosParametrosRecebemMesmaClasse(List<Method> metodos, Class<?> classe) {
		return metodos.stream()
				.filter(metodo -> Stream.of(metodo.getParameters()).allMatch(p -> p.getType().equals(classe))).toList();
	}

	public List<Method> extrairMetodosOndeTodosOsParametrosEstaoAnotadosCom(Class<? extends Annotation> classeAnotacao,
			List<Method> metodos) {
		return metodos.stream().filter(metodo -> Stream.of(metodo.getParameters())
				.allMatch(parametro -> parametro.isAnnotationPresent(classeAnotacao))).toList();
	}

}
