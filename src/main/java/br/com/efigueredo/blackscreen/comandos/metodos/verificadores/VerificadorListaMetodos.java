package br.com.efigueredo.blackscreen.comandos.metodos.verificadores;

import java.lang.reflect.Method;
import java.util.List;

import br.com.efigueredo.blackscreen.comandos.metodos.exception.ComandoInvalidoException;
import br.com.efigueredo.blackscreen.comandos.metodos.exception.ControladorException;
import br.com.efigueredo.blackscreen.sistema.exception.BlackStreenException;

public class VerificadorListaMetodos {

	public void lancarErroComandoSeEstiverVazia(List<Method> listaMetodos, String mensagemErro) throws BlackStreenException {
		if (listaMetodos.size() == 0) {
			throw new ComandoInvalidoException(mensagemErro);
		}
			
	}
	
	public void lancarErroComandoSeHouverMaisDeUmValor(List<Method> listaMetodos, String mensagemErro) throws BlackStreenException {
		if (listaMetodos.size() > 1) {
			throw new ComandoInvalidoException(mensagemErro);
		}
	}
	
	public void lancarErroControladorSeEstiverVazia(List<Method> listaMetodos, String mensagemErro) throws BlackStreenException {
		if (listaMetodos.size() == 0) {
			throw new ControladorException(mensagemErro);
		}
			
	}

	public void lancarErroControladorSeValerNull(List<Method> listaMetodos, String mensagemErro) throws BlackStreenException {
		if (listaMetodos == null) {
			throw new ControladorException(mensagemErro);
		}
			
	}
	
	public void lancarErroControladorSeHouverMaisDeUmValor(List<Method> listaMetodos, String mensagemErro) throws BlackStreenException {
		if (listaMetodos.size() > 1) {
			throw new ControladorException(mensagemErro);
		}
	}
	
}
