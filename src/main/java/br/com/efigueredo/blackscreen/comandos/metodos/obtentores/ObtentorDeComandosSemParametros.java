package br.com.efigueredo.blackscreen.comandos.metodos.obtentores;

import java.lang.reflect.Method;
import java.util.List;

import br.com.efigueredo.blackscreen.comandos.metodos.exception.ComandoInvalidoException;
import br.com.efigueredo.blackscreen.comandos.metodos.manipuladores.ManipuladorDeMetodos;
import br.com.efigueredo.blackscreen.comandos.metodos.verificadores.VerificadorListaMetodos;
import br.com.efigueredo.blackscreen.userinput.expressao.ExpressaoUsuarioValores;

public class ObtentorDeComandosSemParametros {
	
	private ManipuladorDeMetodos manipuladorMetodos;
	private VerificadorListaMetodos verificadorListas;
	
	public ObtentorDeComandosSemParametros() {
		this.manipuladorMetodos = new ManipuladorDeMetodos();
		this.verificadorListas = new VerificadorListaMetodos();
	}

	public Method obterMetodoComandoSemParametrosDeComandoAdequado(Class<?> classeControladora, List<Method> metodos,
			ExpressaoUsuarioValores expressao) throws ComandoInvalidoException {
		List<Method> poolMetodos;
		int quantidadeValores = expressao.getValores().size();
		poolMetodos = this.manipuladorMetodos.extrairMetodosPelaQuantidadeDeParametros(metodos, quantidadeValores);
		poolMetodos = this.manipuladorMetodos.extrairMetodosQueNaoRecebemAClasseNosParametros(poolMetodos, List.class);
		if (poolMetodos.size() == 0) {
			poolMetodos = this.manipuladorMetodos.extrairMetodosQueSoRecebamUmParametro(metodos);
			poolMetodos = manipuladorMetodos.extrairMetodosTodosParametrosRecebemMesmaClasse(poolMetodos, List.class);
			this.executarVerificacoesPoolMetodosSoValores(poolMetodos, expressao, classeControladora);
			return poolMetodos.get(0);
		}
		this.verificadorListas.lancarErroSeHouverMaisDeUmValor(poolMetodos, "Para o comando " + expressao.getComando()
				+ " existe mais de um método que receba a quantidade de parâmetros inserida. Mantenha somente um.\nMétodos: "
				+ poolMetodos);
		return poolMetodos.get(0);
	}

	private void executarVerificacoesPoolMetodosSoValores(List<Method> poolMetodos, ExpressaoUsuarioValores expressao,
			Class<?> classeControladora) throws ComandoInvalidoException {
		this.verificadorListas.lancarErroSeEstiverVazia(poolMetodos,
				"Não existe comando que possa receber " + expressao.getValores().size()
						+ " valor/es e nem comando que possa receber uma lista de valores na classe controladora ["
						+ classeControladora + "].");
		this.verificadorListas.lancarErroSeHouverMaisDeUmValor(poolMetodos,
				"Existe mais de um comando com o nome " + expressao.getComando() + " que receba um List<String> "
						+ "como único parâmetro. Só é possível que exista um comando com essa característica.");
	}
	
}
