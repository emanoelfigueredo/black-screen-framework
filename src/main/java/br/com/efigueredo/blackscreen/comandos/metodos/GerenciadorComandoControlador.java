package br.com.efigueredo.blackscreen.comandos.metodos;

import java.lang.reflect.Method;
import java.util.List;

import br.com.efigueredo.blackscreen.comandos.metodos.obtentores.ObtentorDeComandoPeloNome;
import br.com.efigueredo.blackscreen.comandos.metodos.obtentores.ObtentorDeComandosComParametros;
import br.com.efigueredo.blackscreen.comandos.metodos.obtentores.ObtentorDeComandosSemParametros;
import br.com.efigueredo.blackscreen.sistema.exception.BlackStreenException;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuario;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioParametrosValores;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioValores;

public class GerenciadorComandoControlador {

	private ObtentorDeComandoPeloNome obtentorComandoPorNome;
	private ObtentorDeComandosComParametros obtentoComandosComParametros;
	private ObtentorDeComandosSemParametros obtentoComandosSemParametros;

	public GerenciadorComandoControlador() {
		this.obtentorComandoPorNome = new ObtentorDeComandoPeloNome();
		this.obtentoComandosComParametros = new ObtentorDeComandosComParametros();
		this.obtentoComandosSemParametros = new ObtentorDeComandosSemParametros();
	}

	public Method getMetodoComando(ExpressaoUsuario expressaoUsuario, Class<?> classeControladoraAtual)
			throws BlackStreenException {
		List<Method> metodos = this.obtentorComandoPorNome.getMetodosAnotadosComParametroNomeCorrespondente(
				expressaoUsuario.getComando(), classeControladoraAtual);
		if (expressaoUsuario instanceof ExpressaoUsuarioValores) {
			return this.obtentoComandosSemParametros.obterMetodoComandoSemParametrosDeComandoAdequado(
					classeControladoraAtual, metodos, (ExpressaoUsuarioValores) expressaoUsuario);
		} else {
			return this.obtentoComandosComParametros.obterComandoComParametrosCorrespondentes(metodos,
					(ExpressaoUsuarioParametrosValores) expressaoUsuario);
		}
	}

}
