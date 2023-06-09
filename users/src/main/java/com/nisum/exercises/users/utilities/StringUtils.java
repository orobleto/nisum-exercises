package com.nisum.exercises.users.utilities;

/**
 * 
 * Proporciona una clase de utilidad para cadenas de carateres.
 * 
 * @author <a href="https://octaviorobleto.com" target="_blank">Octavio
 *         Robleto</a>
 * @version 1.0
 * @date 2022-07-13
 * @class StringUtils
 */
public final class StringUtils {

	/**
	 * No permitir crear una instancia de {@code StringUtils}
	 */
	private StringUtils() {
	}

	/**
	 * Devuelve si el String es nulo
	 * 
	 * @param str {@link CharSequence} Puede proporcionar {@code null}
	 * @return {@link Boolean} si la secuencia de caracteres proporcionada es nula o
	 *         vacía / en blanco
	 * 
	 */
	public static boolean isEmpty(final CharSequence str) {
		return str == null || str.length() == 0;
	}

	/**
	 * Devuelve si String es numerico o no.
	 * 
	 * @param str {@link String}
	 * @return {@link Boolean} si el str proporcionado es numérico o no.
	 * 
	 */
	public static boolean isNumber(final String str) {
		return !isEmpty(str) && str.matches("\\d+(\\.\\d+)?");
	}

	/**
	 * Devuelve si el str es un numero entero o no
	 * 
	 * @param str {@link String}
	 * @return {@link Boolean} si el str proporcionado es entero o no.
	 * 
	 */
	public static boolean isInteger(final String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Pone en mayuscula el primer caracter del str proporcionado sin alterar los
	 * demas caracteres
	 *
	 * @param str {@link String} Puede proporcionar {@code null}
	 * @return el primer caracter del str proporcionado en mayuscula, {@code null}
	 *         si la entrada del str es nula
	 */
	public static String capitalize(final String str) {
		return !isEmpty(str) ? str.substring(0, 1).toUpperCase().concat(str.substring(1)) : str;
	}

	/**
	 * Devuelve si el String es correo electronico
	 * 
	 * @param str {@link String} Puede proporcionar {@code null}
	 * @return {@link Boolean} si el str proporcionado es de tipo correo electronico
	 * 
	 */
	public static boolean isMail(final String str) {
		return !isEmpty(str) && str.matches("([a-zA-Z0-9]+(\\.?[a-zA-Z0-9])*)+@(([a-zA-Z]+)\\.([a-zA-Z]+))");
	}

}
