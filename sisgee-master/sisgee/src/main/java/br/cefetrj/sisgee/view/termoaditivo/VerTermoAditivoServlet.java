package br.cefetrj.sisgee.view.termoaditivo;

import br.cefetrj.sisgee.control.AlunoServices;
import br.cefetrj.sisgee.control.ProfessorOrientadorServices;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.sisgee.control.TermoAditivoServices;
import br.cefetrj.sisgee.control.TermoEstagioServices;
import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.ProfessorOrientador;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import br.cefetrj.sisgee.view.utils.UF;
import br.cefetrj.sisgee.view.utils.ValidaUtils;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
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

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);

        String idAluno = request.getParameter("idAluno");
        String idTermo = request.getParameter("idTermo");
        int idTermoInt = Integer.parseInt(idTermo);
        Integer id = null;
        TermoEstagio termo = null;
        //TermoEstagio termoEstagio = null;
        String msg = "";
        String campo = "Termo Aditivo";
        boolean isValid = true;
        msg = ValidaUtils.validaObrigatorio("campo", idAluno);
        if (msg.trim().isEmpty()) {
            if (msg.trim().isEmpty()) {
                Aluno aluno = AlunoServices.buscarAlunoByMatricula(idAluno);
                for(TermoEstagio termo1 : aluno.getTermoEstagios()){
                    if(termo1.getIdTermoEstagio() == idTermoInt){
                        termo = TermoEstagioServices.buscarTermoEstagio(termo1.getIdTermoEstagio());
                    }
                }
                if (termo != null) {
                    try {
                        String di = ServletUtils.mudarFormatoData(termo.getDataInicioTermoEstagio());
                        String df = ServletUtils.mudarFormatoData(termo.getDataFimTermoEstagio());
                        //String dr = ServletUtils.mudarFormatoData(termo.getDataRescisaoTermoEstagio());

                        request.setAttribute("dataIni", di);
                        request.setAttribute("dataFim", df);
                        //termo.setDataInicioTermoEstagio(di);
                        //termo.setDataFimTermoEstagio(df);
                        //System.out.println(dr);
                    } catch (ParseException ex) {
                        Logger.getLogger(VerTermoAditivoServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    NumberFormat formatter = NumberFormat.getCurrencyInstance();
                    String valorPassar = formatter.format(termo.getValorBolsa());
                    System.out.println("Valor Passar: " + valorPassar);
                    request.setAttribute("valorBolsa", valorPassar);
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
            List<ProfessorOrientador> professores = ProfessorOrientadorServices.listarProfessorOrientador();
            request.setAttribute("professores", professores);
            System.out.println("Professores "+professores);
            System.out.println("Professor TERMO: "+ termo.getProfessorOrientador().getIdProfessorOrientador());
            request.setAttribute("professor", termo.getProfessorOrientador().getIdProfessorOrientador());
            boolean isVisualizacao = true;
            String aditivo = "sim";
            request.setAttribute("isVisualizacao", isVisualizacao);
            request.setAttribute("aditivo", aditivo);
            request.getRequestDispatcher("/form_termo_estagio_lista.jsp").forward(request, response);
        }
    }

}
