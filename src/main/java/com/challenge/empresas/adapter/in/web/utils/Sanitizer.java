package com.challenge.empresas.adapter.in.web.utils;

import org.springframework.web.util.HtmlUtils;

/**
 * Clase utilitaria para sanitizar datos de entrada.
 * <p>
 * Proporciona métodos para prevenir XSS (Cross-Site Scripting) y SQL Injection.
 * Nota: La prevención de SQL Injection ya está cubierta por JPA al usar consultas parametrizadas.
 * Este método se proporciona como una capa adicional de seguridad.
 */
public class Sanitizer {

    /**
     * Sanitiza un String para prevenir XSS (Cross-Site Scripting).
     *
     * @param input El String a sanitizar.
     * @return El String sanitizado, con caracteres HTML escapados.
     */
    public static String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        return HtmlUtils.htmlEscape(input.trim());
    }

    /**
     * Sanitiza un número eliminando caracteres no numéricos.
     *
     * @param number El campo numerico a sanitizar.
     * @return El campo numerico sanitizado, solo con caracteres numéricos.
     */
    public static String sanitizeNumber(String number) {
        if (number == null) {
            return null;
        }
        return number.replaceAll("[^0-9]", "").trim();
    }

    /**
     * Sanitiza un String para prevenir SQL Injection.
     * <p>
     * Nota: La prevención de SQL Injection ya está cubierta por JPA al usar consultas parametrizadas.
     * Este método se proporciona como una capa adicional de seguridad.
     *
     * @param input El String a sanitizar.
     * @return El String sanitizado, con caracteres peligrosos para SQL eliminados.
     */
    public static String sanitizeForSQL(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("[';\"]", "");
    }
}
