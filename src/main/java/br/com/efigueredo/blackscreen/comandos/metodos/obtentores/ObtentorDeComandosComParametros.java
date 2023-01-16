package br.com.efigueredo.blackscreen.comandos.metodos.obtentores;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import br.com.efigueredo.blackscreen.anotacoes.Parametro;
import br.com.efigueredo.blackscreen.comandos.metodos.manipuladores.ManipuladorDeMetodos;
import br.com.efigueredo.blackscreen.comandos.metodos.manipuladores.ManipuladorMetodosComandos;
import br.com.efigueredo.blackscreen.comandos.metodos.verificadores.VerificadorListaMetodos;
import br.com.efigueredo.blackscreen.sistema.exception.BlackStreenException;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioParametrosValores;

public class ObtentorDeComandosComParametros {
	
	private ManipuladorMetodosComandos manipuladorMetodosComando;
	private ManipuladorDeMetodos manipuladorMetodos;
	private VerificadorListaMetodos verificadorListas;

	public ObtentorDeComandosComParametros() {
		this.manipuladorMetodos = new ManipuladorDeMetodos();
		this.manipuladorMetodosComando = new ManipuladorMetodosComandos();
		this.verificadorListas = new VerificadorListaMetodos();
	}

	public Method obterComandoComParametrosCorrespondentes(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) throws BlackStreenException {
		List<Method> poolMetodos;
		poolMetodos = this.extrairMetodosComParametrosAnotadosECorretos(metodos);
		poolMetodos = this.extrairMetodosComQuantidadeDeParametrosCorrespondentes(poolMetodos, expressaoUsuario);
		poolMetodos = this.extrairMetodosComNomesDeParametosCorrespondentes(poolMetodos, expressaoUsuario);
		poolMetodos = this.extrairMetodosPelasClassesDeSeusParametros(poolMetodos, expressaoUsuario);
		return poolMetodos.get(0);
	}

	private List<Method> extrairMetodosPelasClassesDeSeusParametros(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) throws BlackStreenException {
		metodos = this.manipuladorMetodosComando
				.extrairMetodosFiltradosPelaCapacidadeDeValoresParaSeremInjetados(metodos, expressaoUsuario);
		this.verificadorListas.lancarErroComandoSeEstiverVazia(metodos,
				"Os únicos comandos que possuem os parâmetros correpondetes não podem receber a quantidade de valores inserida.\nMétodos: "
						+ metodos);
		this.verificadorListas.lancarErroComandoSeHouverMaisDeUmValor(metodos, "Comando iguais:\n" + metodos);
		return metodos;
	}

	private List<Method> extrairMetodosComNomesDeParametosCorrespondentes(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) throws BlackStreenException {
		Set<String> nomesParametros = expressaoUsuario.getParametrosValores().keySet();
		metodos = this.manipuladorMetodosComando
				.extrairMetodosOndeSeusNomesDeParametrosCorrespondemAListaDeNomes(nomesParametros, metodos);
		this.verificadorListas.lancarErroComandoSeEstiverVazia(metodos,
				"Os comandos correspondentes não possuem os parâmetros inseridos.\nParâmetros: " + nomesParametros);
		return metodos;
	}

	private List<Method> extrairMetodosComQuantidadeDeParametrosCorrespondentes(List<Method> metodos,
			ExpressaoUsuarioParametrosValores expressaoUsuario) {
		return this.manipuladorMetodos.extrairMetodosPelaQuantidadeDeParametros(metodos,
				expressaoUsuario.getParametrosValores().size());
	}

	private List<Method> extrairMetodosComParametrosAnotadosECorretos(List<Method> metodos)
			throws BlackStreenException {
		metodos = this.manipuladorMetodos.extrairMetodosOndeTodosOsParametrosEstaoAnotadosCom(Parametro.class, metodos);
		this.verificadorListas.lancarErroComandoSeEstiverVazia(metodos,
				"Não existe comando com parâmetros na classe controladora.");
		return metodos;
	}
	
}
