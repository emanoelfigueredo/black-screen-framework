package br.com.efigueredo.blackscreen.userinput;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManipuladorExpressaoParametrosEValores {

	/**
	 *
	 * 
	 * @param expressao Expressão inserida no sistema pelo usuário.
	 * @return Uma lista contendo todas os parâmetros extraidos.
	 */
	public Map<String, String> extrairParametrosEValores(String expressao) {
		Map<String, String> mapaParametroValor = new HashMap<String, String>();
		List<String> listaParametrosEValores = Arrays.asList(expressao.split("--"));
		for (String parametroEValor : listaParametrosEValores) {
			parametroEValor = parametroEValor.strip();
			if (parametroEValor.length() == 0) {
				continue;
			}
			if (parametroEValor.contains("\"")) {
				if (parametroEValor.contains("@\"")) {
					String parametroEValorSemAspasEscapadas = parametroEValor.replace("@\"", "");
					String[] arrayParametroValor = extrairParametroValor(parametroEValorSemAspasEscapadas, " \"");
					String parametro = arrayParametroValor[0];
					String valor = inserirAspasEscapadasNoValor(parametroEValor, parametro.length(),
							arrayParametroValor[1]);
					mapaParametroValor.put(parametro, valor);
					continue;
				}
				String[] arrayParametroValor = extrairParametroValor(parametroEValor, " \"");
				mapaParametroValor.put(arrayParametroValor[0], arrayParametroValor[1]);
				continue;
			}
			String[] arrayParametroValor = extrairParametroValor(parametroEValor, " ");
			mapaParametroValor.put(arrayParametroValor[0], arrayParametroValor[1]);
		}
		return mapaParametroValor;
	}

	

	private String inserirAspasEscapadasNoValor(String paramValor, int tamanhoParametro,
			String valorSemAspasEscapadas) {
		int begin = obterIndexInicialDasAspasEscapadasDoValor(paramValor, tamanhoParametro);
		int end = obterIndexFinalDasAspasEscapadasDoValor(paramValor, tamanhoParametro, begin);
		return recolocarAspasEscapadasNoValor(valorSemAspasEscapadas, begin, end);
	}

	private String recolocarAspasEscapadasNoValor(String valor, int begin, int end) {
		StringBuilder builder = new StringBuilder();
		return builder.append(valor.substring(0, begin)).append("\"").append(valor.substring(begin, end)).append("\"")
				.append(valor.substring(end, valor.length())).toString();
	}

	private int obterIndexFinalDasAspasEscapadasDoValor(String paramValor, int tamanhoParametro, int begin) {
		int end = obterIndexFinalDasAspasEscape(paramValor);
		return end - tamanhoParametro - 3;
	}

	private int obterIndexInicialDasAspasEscapadasDoValor(String paramValor, int tamanhoParametro) {
		int begin = obterIndexInicialDasAspasEscape(paramValor);
		return begin - tamanhoParametro - 2;

	}

	private int obterIndexInicialDasAspasEscape(String paramValor) {
		return paramValor.indexOf("@\"");
	}

	private int obterIndexFinalDasAspasEscape(String paramValor) {
		int indexBegin = obterIndexInicialDasAspasEscape(paramValor);
		return indexBegin + paramValor.substring(indexBegin + 1).indexOf("@\"");
	}

	private String obterParametro(String expressao, int indexSeparacao) {
		return expressao.substring(0, indexSeparacao);
	}

	private String obterValor(String expressao, int indexSeparacao) {
		return expressao.substring(indexSeparacao + 1, expressao.length()).replaceAll("\"", "");
	}

	private String[] extrairParametroValor(String stringSemAspasEscapadas, String separador) {
		int indexSeparacao = stringSemAspasEscapadas.indexOf(separador);
		String parametro = obterParametro(stringSemAspasEscapadas, indexSeparacao);
		String valor = obterValor(stringSemAspasEscapadas, indexSeparacao);
		String[] resultado = { parametro, valor };
		return resultado;
	}
	
}
