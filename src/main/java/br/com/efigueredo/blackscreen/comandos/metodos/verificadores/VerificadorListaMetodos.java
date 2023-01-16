package br.com.efigueredo.blackscreen.comandos.metodos.verificadores;

import java.lang.reflect.Method;
import java.util.List;

import br.com.efigueredo.blackscreen.comandos.metodos.exception.ComandoInvalidoException;

public class VerificadorListaMetodos {

	public void lancarErroSeEstiverVazia(List<Method> listaMetodos, String mensagemErro) throws ComandoInvalidoException {
		if (listaMetodos.size() == 0) {
			throw new ComandoInvalidoException(mensagemErro);
		}
			
	}

	public void lancarErroSeHouverMaisDeUmValor(List<Method> listaMetodos, String mensagemErro) throws ComandoInvalidoException {
		if (listaMetodos.size() > 1) {
			throw new ComandoInvalidoException(mensagemErro);
		}
	}
	
}
