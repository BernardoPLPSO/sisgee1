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
import java.text.SimpleDateFormat;
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
        String idtermo = request.getParameter("idTermoEstagio");
        //Pega prof
        String professorNome = (String)request.getAttribute("idProfessorOrientador");
        //Pega data fim
        String vigencia = request.getParameter("dataFimTermoEstagio");
        request.getParameter("dataFimTermoEstagio");
        //Pega carga horaria
        String cargaHoraria = request.getParameter("cargaHorariaTermoEstagio");
        //Pega valor da bolsa
        String valor = request.getParameter("valorBolsa");
        valor = valor.replace(".", "");
        valor = valor.replace(",", ".");
        //Pega campos de endereco
        String endereco = request.getParameter("enderecoTermoEstagio");
        String cep = request.getParameter("cepEnderecoTermoEstagio");
        String estado = request.getParameter("estadoEnderecoTermoEstagio");
        String numero = request.getParameter("numeroEnderecoTermoEstagio");
        String cidade = request.getParameter("cidadeEnderecoTermoEstagio");
        String complemento = request.getParameter("complementoEnderecoTermoEstagio");
        String bairro = request.getParameter("bairroEnderecoTermoEstagio");

        TermoEstagio t = TermoEstagioServices.buscarTermoEstagio(Integer.parseInt(idtermo));
        
        TermoEstagio termoAditivo = new TermoEstagio(t.getDataInicioTermoEstagio(),t.getCargaHorariaTermoEstagio(), t.getValorBolsa(), t.getEnderecoTermoEstagio(), t.getProfessorOrientador());
        termoAditivo.setAluno(t.getAluno());
        termoAditivo.setBairroEnderecoTermoEstagio(t.getBairroEnderecoTermoEstagio());
        termoAditivo.setCepEnderecoTermoEstagio(t.getCepEnderecoTermoEstagio());
        termoAditivo.setCidadeEnderecoTermoEstagio(t.getCidadeEnderecoTermoEstagio());
        termoAditivo.setComplementoEnderecoTermoEstagio(t.getComplementoEnderecoTermoEstagio());
        termoAditivo.setDataFimTermoEstagio(t.getDataFimTermoEstagio());
        termoAditivo.setDataInicioTermoEstagio(t.getDataInicioTermoEstagio());
        termoAditivo.setEEstagioObrigatorio(t.getEEstagioObrigatorio());
        termoAditivo.setEnderecoTermoEstagio(t.getEnderecoTermoEstagio());
        termoAditivo.setEstadoEnderecoTermoEstagio(t.getEstadoEnderecoTermoEstagio());
        termoAditivo.setNumeroEnderecoTermoEstagio(t.getNumeroEnderecoTermoEstagio());
        termoAditivo.setConvenioPF(t.getConvenioPF());
        termoAditivo.setConvenioPJ(t.getConvenioPJ());      
        termoAditivo.setTermoEstagio(t);
        
        
        
        
        Integer cargaHorariaTermoAditivo = Integer.parseInt(request.getParameter("updCargaHoraria"));
        Float valorBolsaTermoAditivo = Float.parseFloat(request.getParameter("updValorBolsa"));
        String enderecoTermoAditivo = (String) request.getParameter("updEndereco");
       // ProfessorOrientador professorOrientador = (ProfessorOrientador) request.getParameter("updProfessor");

        if (vigencia != null) {
            String aux = vigencia.substring(8);
            aux = aux + "/";
            aux = aux + vigencia.charAt(6);
            aux = aux + vigencia.charAt(7);
            aux = aux + "/";
            aux = aux + vigencia.charAt(0);
            aux = aux + vigencia.charAt(1);
            aux = aux + vigencia.charAt(3);
            aux = aux + vigencia.charAt(4);
            System.out.println(aux);
            SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            Date data = null;
            try {
                data = formata.parse(aux);
                termoAditivo.setDataFimTermoEstagio(data);
            } catch (ParseException ex) {
                java.util.logging.Logger.getLogger(IncluirTermoAditivoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (Integer.parseInt(cargaHoraria) != termoAditivo.getCargaHorariaTermoEstagio()) {
            termoAditivo.setCargaHorariaTermoEstagio(Integer.parseInt(cargaHoraria));
        }
        
        if (Float.parseFloat(valor) != termoAditivo.getValorBolsa()) {
            termoAditivo.setValorBolsa(Float.parseFloat(valor));
        }

        if (endereco != termoAditivo.getEnderecoTermoEstagio()) {
            termoAditivo.setEnderecoTermoEstagio(endereco);
        }
        
        if (cidade != termoAditivo.getCidadeEnderecoTermoEstagio()) {
            termoAditivo.setCidadeEnderecoTermoEstagio(cidade);
        }
        
        if (numero != termoAditivo.getNumeroEnderecoTermoEstagio()) {
            termoAditivo.setNumeroEnderecoTermoEstagio(numero);
        }
        
        if (cep != termoAditivo.getCepEnderecoTermoEstagio()) {
            termoAditivo.setCepEnderecoTermoEstagio(cep);
        }
        
        if (bairro != termoAditivo.getBairroEnderecoTermoEstagio()) {
            termoAditivo.setBairroEnderecoTermoEstagio(bairro);
        }
        
        if (endereco != termoAditivo.getEnderecoTermoEstagio()) {
            termoAditivo.setEnderecoTermoEstagio(endereco);
        }
        
        if (estado != termoAditivo.getEstadoEnderecoTermoEstagio()) {
            termoAditivo.setEstadoEnderecoTermoEstagio(estado);
        }
        
        if (complemento != termoAditivo.getComplementoEnderecoTermoEstagio()) {
            termoAditivo.setComplementoEnderecoTermoEstagio(complemento);
        }
        /*if (professorOrientador != null) {
            termoAditivo.setProfessorOrientador(professorOrientador);
        }*/
        

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
