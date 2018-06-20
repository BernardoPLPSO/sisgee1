package br.cefetrj.sisgee.view.utils;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Classe criada para incluir método estáticos para melhor reuso de código nos
 * Servlets.
 *
 * @author padu
 * @since 1.0
 *
 */
public class ServletUtils {

    /**
     *
     */
    public enum ErrorCode {

        /**
         *
         */
        INVALID,

        /**
         *
         */
        REQUIRED
    }

    /**
     * Método estático para buscar o locale no request, senão pega da sessão,
     * senão pega o padrão
     *
     * @param d
     * @param request requisição do usuário
     * @return Locale localização do usuário ou selecionado pela página
     * @return locale do user ou selecionado
     * @throws java.text.ParseException
     */
    public static String mudarFormatoData(Date d) throws ParseException {
        final String OLD_FORMAT = "yyyy/MM/dd";
        final String NEW_FORMAT = "dd/MM/yyyy";
        
        System.out.println("Old date: "+ d.toString());
        String newDateString;
        String data = new SimpleDateFormat("dd/MM/yyyy").format(d);
        System.out.println("new date: "+ data);
        return data;
    }

    /**
     *
     * @param request
     * @return
     * @throws ServletException
     */
    public static Locale getLocale(HttpServletRequest request) throws ServletException {

        Locale locale = null;

        Object localeFound = request.getParameter("lang");
        if (localeFound == null) {
            localeFound = request.getSession().getAttribute("lang");
        }

        if (localeFound == null) {
            localeFound = request.getLocale();
        }

        if (localeFound instanceof Locale) {
            locale = (Locale) localeFound;
        } else if (localeFound instanceof String) {
            String[] parts = ((String) localeFound).split("_");
            locale = new Locale(parts[0], parts[1].toUpperCase());
        } else {
            throw new ServletException("Locale not found!!!");
        }
        return locale;
    }

    /**
     * Método estático para formatar mensagens que precisam de formatação
     *
     * @param msg chave que deverá ser formatada
     * @param locale localização do usuário ou selecionado pela página
     * @param param O valor variável da mensagem
     * @return Mensagem formatada
     */
    public static String mensagemFormatada(String msg, Locale locale, Integer param) {
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);
        formatter.applyPattern(msg);

        msg = formatter.format(new Object[]{param});
        System.out.println("Mensagem: "+msg);
        return msg;
    }

}
