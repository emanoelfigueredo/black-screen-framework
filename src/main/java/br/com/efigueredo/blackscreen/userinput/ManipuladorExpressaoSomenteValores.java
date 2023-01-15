package br.com.efigueredo.blackscreen.userinput;

import java.util.ArrayList;
import java.util.List;

import br.com.efigueredo.blackscreen.userinput.exception.ExpressaoInvalidaException;

public class ManipuladorExpressaoSomenteValores {

	public List<String> extrairValores(String expressao) throws ExpressaoInvalidaException {
		List<String> listaValores = new ArrayList<String>();
		String expressaoManipulacao = expressao;
		while (!expressaoEstaVazia(expressaoManipulacao)) {
			expressaoManipulacao = extrairValoresSemAspas(listaValores, expressaoManipulacao);
			expressaoManipulacao = extrairValoresComAspas(listaValores, expressaoManipulacao);
			verificarValorInseridoIncorretamente(expressaoManipulacao);
		}
		return this.removerArrobasDeEscapes(listaValores);
	}

	private List<String> removerArrobasDeEscapes(List<String> listaValores) {
		return listaValores.stream().map(v -> v.replace("@\"", "\"")).toList();
	}

	private boolean expressaoEstaVazia(String expressao) {
		return expressao.equals("");
	}

	private boolean proximoValorTemAspaEscapadaComArroba(String expressao) {
		return expressao.substring(0, 2).contains("@\"");
	}

	private boolean proximoValorTemAspaSemEscapeArroba(String expressao) {
		return expressao.substring(0, 1).equals("\"");
	}

	private boolean finalDaExpressaoDeManipulacao(int valor) {
		return valor == -1;
	}

	private void verificarValorInseridoIncorretamente(String expressaoManipulacao) throws ExpressaoInvalidaException {
		if (!this.expressaoEstaVazia(expressaoManipulacao)
				&& this.proximoValorTemAspaEscapadaComArroba(expressaoManipulacao)) {
			throw new ExpressaoInvalidaException("Expressao incorreta");
		}
	}

	private String extrairValoresSemAspas(List<String> listaValores, String expressaoManipulacao) {
		while (!this.expressaoEstaVazia(expressaoManipulacao)
				&& !this.proximoValorTemAspaSemEscapeArroba(expressaoManipulacao)
				&& !this.proximoValorTemAspaEscapadaComArroba(expressaoManipulacao)) {

			int primeiroEspacoAposOValor = expressaoManipulacao.indexOf(" ");
			String valor;
			if (!this.finalDaExpressaoDeManipulacao(primeiroEspacoAposOValor)) {
				valor = expressaoManipulacao.substring(0, primeiroEspacoAposOValor);
				expressaoManipulacao = expressaoManipulacao.substring(primeiroEspacoAposOValor + 1).strip();
			} else {
				valor = expressaoManipulacao.substring(0, expressaoManipulacao.length());
				expressaoManipulacao = "";
			}
			listaValores.add(valor);
		}
		return expressaoManipulacao;
	}

	private boolean aspasMaisProximaEstaEscapadaComArroba(String expressao, int indexAspaMaisProxima) {
		return expressao.substring(indexAspaMaisProxima - 1, indexAspaMaisProxima).equals("@");
	}

	private String extrairValoresComAspas(List<String> listaValores, String expressaoManipulacao) {
		while (!this.expressaoEstaVazia(expressaoManipulacao)
				&& this.proximoValorTemAspaSemEscapeArroba(expressaoManipulacao)
				&& !this.proximoValorTemAspaEscapadaComArroba(expressaoManipulacao)) {
			String[] valorExpressao = extrairValorComAspaDaSequencia(expressaoManipulacao);
			listaValores.add(valorExpressao[0]);
			expressaoManipulacao = valorExpressao[1];
		}
		return expressaoManipulacao;
	}

	private String[] extrairValorComAspaDaSequencia(String expressaoManipulacao) {
		int contador = 0;
		int indexUltimaAspaDoValor = 0;
		String expressaoSemAspasIniciais = expressaoManipulacao.substring(1, expressaoManipulacao.length() - 1);
		String expressaoManipuladaSemAspasIniciais = expressaoManipulacao.substring(1,
				expressaoManipulacao.length() - 1);
		while (this.proximoValorTemAspaSemEscapeArroba(expressaoManipulacao)
				&& !this.proximoValorTemAspaEscapadaComArroba(expressaoManipulacao)) {
			int aspasMaisProxima = expressaoManipuladaSemAspasIniciais.indexOf("\"");
			if (contador != 0) {
				indexUltimaAspaDoValor += aspasMaisProxima + 1;
			} else {
				indexUltimaAspaDoValor += aspasMaisProxima;
				contador++;
			}
			if (this.finalDaExpressaoDeManipulacao(aspasMaisProxima)) {
				String[] resultado = { expressaoSemAspasIniciais, "" };
				return resultado;
			}
			if (this.aspasMaisProximaEstaEscapadaComArroba(expressaoManipuladaSemAspasIniciais, aspasMaisProxima)) {
				expressaoManipuladaSemAspasIniciais = expressaoManipuladaSemAspasIniciais
						.substring(aspasMaisProxima + 1);
				continue;
			}
			break;
		}
		return obterValorCompletoEExpressaoDeManipulacaoAtualizada(expressaoSemAspasIniciais, expressaoManipulacao,
				indexUltimaAspaDoValor);
	}

	private String[] obterValorCompletoEExpressaoDeManipulacaoAtualizada(String expressaoSemAspasIniciais,
			String expressaoManipulacao, int indexUltimaAspaDoValor) {
		String valorResultado = expressaoSemAspasIniciais.substring(0, indexUltimaAspaDoValor);
		String expressaoResultadoCompletaComAspasIniciais = expressaoManipulacao.substring(0,
				indexUltimaAspaDoValor + 2);
		expressaoManipulacao = expressaoManipulacao.replace(expressaoResultadoCompletaComAspasIniciais, "").strip();
		String[] resultado = { valorResultado, expressaoManipulacao };
		return resultado;
	}
	
}
