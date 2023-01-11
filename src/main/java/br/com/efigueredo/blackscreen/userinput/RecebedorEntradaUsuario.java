package br.com.efigueredo.blackscreen.userinput;

import java.util.Scanner;

import org.reflections.Reflections;

import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistema;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.RespostasSistemaFactory;
import br.com.efigueredo.blackscreen.sistema.configuracoes.respostas.exception.ConfiguracaoRespostaSistemaException;
import br.com.efigueredo.container.exception.ContainerIocException;

/**
 * <h4>Classe responsável por receber a entrada via console pelo usuário.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class RecebedorEntradaUsuario {

	/**
	 * Objeto responsável por obter inputs via console.
	 */
	private Scanner scan;

	/**
	 * Objeto responsável por imprimir no console conforme configurado.
	 * 
	 * Sua função nessa classe será imprimir o indicador configurado.
	 */
	private RespostasSistema respostas;

	/**
	 * Construtor.
	 *
	 * @param reflections Objeto responsável pela reflexão do projeto.
	 * @param pacoteRaiz Pacote raiz do projeto.
	 * @throws ContainerIocException                Erro no Container Ioc.
	 * @throws ConfiguracaoRespostaSistemaException Falha no carregamento das
	 *                                              configurações de respostas do
	 *                                              sistema. Analise a cuasa.
	 */
	public RecebedorEntradaUsuario(Reflections reflections, String pacoteRaiz)
			throws ContainerIocException, ConfiguracaoRespostaSistemaException {
		this.scan = new Scanner(System.in);
		this.respostas = new RespostasSistemaFactory().getRespostasSistema(reflections, pacoteRaiz);
	}

	/**
	 * Método reponsável por obter a entrada do usuário via console.
	 * 
	 * @return Expressão inserida pelo usuário.
	 */
	public String receberEntrada() {
		this.respostas.imprimirIndicador();
		return this.scan.nextLine().strip();
	}

}
