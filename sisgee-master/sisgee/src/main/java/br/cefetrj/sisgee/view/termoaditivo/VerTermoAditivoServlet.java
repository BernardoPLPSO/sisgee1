package br.cefetrj.sisgee.view.termoaditivo;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.sisgee.control.TermoAditivoServices;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import br.cefetrj.sisgee.view.utils.ValidaUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet criada para visualização dos termos aditivos na tela
 *
 * @author Paulo Cantuária
 *
 */

@WebServlet("/VerTermoAditivoServlet")
public class VerTermoAditivoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);

        String idTermoAditivo = request.getParameter("idTermo");
        Integer id = null;
        TermoEstagio termo = null;
        //TermoEstagio termoEstagio = null;

        String msg = "";
        String campo = "Termo Aditivo";
        boolean isValid = true;

        msg = ValidaUtils.validaObrigatorio("campo", idTermoAditivo);
        if (msg.trim().isEmpty()) {
            msg = ValidaUtils.validaInteger(campo, idTermoAditivo);
            if (msg.trim().isEmpty()) {
                id = Integer.parseInt(idTermoAditivo);
                termo = TermoAditivoServices.buscarTermoAditivo(id);
                if (termo != null) {
                    //termoEstagio = TermoAditivoServices.termoEstagioAtualizadoByTermoAditivo(termoAditivo);
                    SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
                    Date dataInicio = termo.getDataInicioTermoEstagio();
                    System.out.println("Ola"+dataInicio);
                    String dataIni = formata.format(dataInicio);
                    System.out.println("Data ini: "+dataIni);
                    try {
                        dataInicio = formata.parse(dataIni);
                    } catch (ParseException ex) {
                        Logger.getLogger(VerTermoAditivoServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(dataInicio);
                    request.setAttribute("dataInicio", "dataInicio");
                    request.setAttribute("termo", termo);

                } else {
                    isValid = false;
                    msg = messages.getString("br.cefetrj.sisgee.ver_termo_aditivo_servlet.id_termo_invalido");
                }
            } else {
                isValid = false;
                msg = messages.getString(msg);
            }
        } else {
            isValid = false;
            msg = messages.getString(msg);
        }

        if (isValid) {
            boolean isVisualizacao = true;
            String aditivo = "sim";
            request.setAttribute("isVisualizacao", isVisualizacao);
            request.setAttribute("aditivo", aditivo);
            request.getRequestDispatcher("/form_termo_estagio_lista.jsp").forward(request, response);
        }
    }

}
