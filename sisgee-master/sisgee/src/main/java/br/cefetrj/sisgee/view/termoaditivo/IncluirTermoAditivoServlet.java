package br.cefetrj.sisgee.view.termoaditivo;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.cefetrj.sisgee.control.TermoAditivoServices;
import br.cefetrj.sisgee.control.TermoEstagioServices;
import br.cefetrj.sisgee.model.entity.ProfessorOrientador;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import java.awt.BorderLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.logging.Level;

/**
 *
 * @author Paulo Cantuária
 * @since 1.0
 *
 */
@WebServlet("/IncluirTermoAditivoServlet")
public class IncluirTermoAditivoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);
        String idtermo = request.getParameter("termoid");

        TermoEstagio termoAditivo = TermoEstagioServices.buscarTermoEstagio(Integer.parseInt(idtermo));
        System.out.println(termoAditivo);

        Date dataFimTermoAditivo;
        try {
            dataFimTermoAditivo = DateFormat.getInstance().parse(request.getParameter("updVigencia"));
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(IncluirTermoAditivoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Integer cargaHorariaTermoAditivo = Integer.parseInt(request.getParameter("updCargaHoraria"));
        System.out.println(cargaHorariaTermoAditivo);
        Float valorBolsaTermoAditivo = Float.parseFloat(request.getParameter("updValorBolsa"));
        System.out.println(valorBolsaTermoAditivo);
        String enderecoTermoAditivo = (String) request.getParameter("updEndereco");
       // ProfessorOrientador professorOrientador = (ProfessorOrientador) request.getParameter("updProfessor");

        String myObjectId = request.getParameter("myObjectId");

        request.getSession().removeAttribute(myObjectId);

        System.out.println(termoAditivo);
        /*if (dataFimTermoAditivo != null) {
            termoAditivo.setDataFimTermoEstagio(dataFimTermoAditivo);
        }*/

        if (cargaHorariaTermoAditivo != null) {
            termoAditivo.setCargaHorariaTermoEstagio(cargaHorariaTermoAditivo);
        }

        if (valorBolsaTermoAditivo != null) {
            termoAditivo.setValorBolsa(valorBolsaTermoAditivo);
        }

        if (enderecoTermoAditivo != null) {
            termoAditivo.setEnderecoTermoEstagio(enderecoTermoAditivo);
        }

        /*if (professorOrientador != null) {
            termoAditivo.setProfessorOrientador(professorOrientador);
        }*/
        System.out.println(termoAditivo.getBairroEnderecoTermoEstagio());
        System.out.println(termoAditivo.getCargaHorariaTermoEstagio());
        System.out.println(termoAditivo.getBairroEnderecoTermoEstagio());
        System.out.println(termoAditivo.getCepEnderecoTermoEstagio());
        System.out.println(termoAditivo.getCidadeEnderecoTermoEstagio());
        System.out.println(termoAditivo.getComplementoEnderecoTermoEstagio());
        System.out.println(termoAditivo.getConvenioPF());
        System.out.println(termoAditivo.getConvenioPJ());
        System.out.println(termoAditivo.getDataFimTermoEstagio());
        System.out.println(termoAditivo.getDataInicioTermoEstagio());
        System.out.println(termoAditivo.getDataRescisaoTermoEstagio());
        System.out.println(termoAditivo.getEEstagioObrigatorio());
        System.out.println(termoAditivo.getEnderecoTermoEstagio());
        System.out.println(termoAditivo.getEstadoEnderecoTermoEstagio());
        System.out.println(termoAditivo.getIdTermoEstagio());
        System.out.println(termoAditivo.getNumeroEnderecoTermoEstagio());
        System.out.println(termoAditivo.getProfessorOrientador());
        System.out.println(termoAditivo.getTermoEstagio());
        System.out.println(termoAditivo.getTermosAditivos());
        System.out.println(termoAditivo.getValorBolsa());
        System.out.println(termoAditivo.geteEstagioObrigatorio());

        String registroAditivoConcluido = "";
        String msgOcorreuErro = "";
        Logger lg = Logger.getLogger(IncluirTermoAditivoServlet.class);
        try {
            TermoAditivoServices.incluirTermoAditivo(termoAditivo);
            registroAditivoConcluido = messages.getString("br.cefetrj.sisgee.incluir_termo_aditivo_servlet.msg_registroAditivoConcluido");
            request.setAttribute("msg", registroAditivoConcluido);
            lg.info(registroAditivoConcluido);
            request.getRequestDispatcher("/index.jsp").forward(request, response);

            //TODO remover saída do console
        } catch (Exception e) {
            msgOcorreuErro = messages.getString("br.cefetrj.sisgee.incluir_termo_aditivo_servlet.msg_ocorreuErro");
            request.setAttribute("msg", msgOcorreuErro);

            lg.error("Exception ao tentar inserir o Termo Aditivo", e);
            request.getRequestDispatcher("FormTermoAditivoServlet").forward(request, response);

            //TODO remover saída do console
            System.out.println(msgOcorreuErro);
        }

        System.out.println(registroAditivoConcluido);

    }

}
