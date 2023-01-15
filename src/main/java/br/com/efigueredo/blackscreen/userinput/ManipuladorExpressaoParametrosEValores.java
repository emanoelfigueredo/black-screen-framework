package br.com.efigueredo.blackscreen.userinput;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import br.com.efigueredo.blackscreen.userinput.exception.ExpressaoInvalidaException;

public class ManipuladorExpressaoParametrosEValores {

	/**
	 *
	 * 
	 * @param expressao Expressão inserida no sistema pelo usuário.
	 * @return Uma lista contendo todas os parâmetros extraidos.
	 * @throws ExpressaoInvalidaException 
	 */
	public Map<String, List<String>> extrairParametrosEValores(String expressao) throws ExpressaoInvalidaException {
		Map<String, List<String>> mapaParametroValor = new HashMap<String, List<String>>();
		List<String> listaParametrosEValores = Arrays.asList(expressao.split("--"));
		for (String parametroEValor : listaParametrosEValores) {
			parametroEValor = parametroEValor.strip();
			System.out.println(parametroEValor);
			if (parametroEValor.length() == 0) {
				continue;
			}
			if(parametroEValor.indexOf(" ") == -1) {
				throw new ExpressaoInvalidaException("A expressão inserida contém um parâmetro sem valor correspondente");
			}
			if (parametroEValor.contains("\"")) {
				
				int indexPrimeiroEspaco = parametroEValor.indexOf(" ");
				String parametro = parametroEValor.substring(0, indexPrimeiroEspaco);
				String valores = parametroEValor.substring(indexPrimeiroEspaco, parametroEValor.length()).trim();
				ManipuladorExpressaoSomenteValores m = new ManipuladorExpressaoSomenteValores();
				List<String> extrairValores = m.extrairValores(valores);	
				mapaParametroValor.put(parametro, extrairValores);
						
			} else {
				int quantidadeEspacoesEmBranco = StringUtils.countMatches(parametroEValor, " ");
				if(quantidadeEspacoesEmBranco > 1) {
					
					List<String> parametroEValores = Arrays.asList(parametroEValor.split(" "));

					String parametro = parametroEValores.get(0);
					List<String> valores = parametroEValores.subList(1, parametroEValores.size());
					
					mapaParametroValor.put(parametro, valores);	
					
					
					
				} else {
					String[] arrayParametroValor = extrairParametroValor(parametroEValor, " ");
					mapaParametroValor.put(arrayParametroValor[0], Arrays.asList(arrayParametroValor[1]));
				}
				
				
			}
			
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
