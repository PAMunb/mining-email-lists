package br.unb.scrap.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A anotação {@code NotReady} é usada para marcar classes ou métodos que ainda não estão completamente implementados ou prontos para uso.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface NotReady {
	String reason() default "Not implemented yet";
}
