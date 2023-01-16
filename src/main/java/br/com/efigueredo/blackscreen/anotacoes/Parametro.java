package br.com.efigueredo.blackscreen.anotacoes;

import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention (RetentionPolicy.RUNTIME)
@Documented
@Target(PARAMETER)
public @interface Parametro {
	
	public String nome();

}
