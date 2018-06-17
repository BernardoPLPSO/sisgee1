package br.cefetrj.sisgee.view.termoestagio;

import br.cefetrj.sisgee.control.PessoaFisicaServices;
import br.cefetrj.sisgee.control.PessoaJuridicaServices;
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

import br.cefetrj.sisgee.control.TermoEstagioServices;
import br.cefetrj.sisgee.model.entity.AgenteIntegracao;
import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.PessoaFisica;
import br.cefetrj.sisgee.model.entity.PessoaJuridica;
import br.cefetrj.sisgee.model.entity.ProfessorOrientador;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import br.cefetrj.sisgee.view.utils.ServletUtils;

/**
 *
 * @author Paulo Cantuária
 * @since 1.0
 *
 */
@WebServlet("/IncluirTermoEstagioServlet")
public class IncluirTermoEstagioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);

        //OBRIGATÓRIO
        Date dataInicioTermoEstagio = (Date) request.getAttribute("dataInicio");
        Integer cargaHorariaTermoEstagio = (Integer) request.getAttribute("cargaHoraria");
        Float valorBolsa = (Float) request.getAttribute("valor");
        String enderecoTermoEstagio = (String) request.getAttribute("enderecoTermoEstagio");
        String complementoEnderecoTermoEstagio = (String) request.getAttribute("complementoEnderecoTermoEstagio");
        String bairroEnderecoTermoEstagio = (String) request.getAttribute("bairroEnderecoTermoEstagio");
        String cepEnderecoTermoEstagio = (String) request.getAttribute("cepEnderecoTermoEstagio");
        String cidadeEnderecoTermoEstagio = (String) request.getAttribute("cidadeEnderecoTermoEstagio");
        String estadoEnderecoTermoEstagio = (String) request.getAttribute("estadoEnderecoTermoEstagio");
        Boolean eEstagioObrigatorio = (Boolean) request.getAttribute("obrigatorio");
        Aluno aluno = new Aluno((Integer) request.getAttribute("idAluno"));
        String numeroConvenio = (String)request.getAttribute("numeroConvenio");
        String nomeSupervisor = (String)request.getAttribute("nomeSupervisor");
        System.out.println("nomeSupervisor: "+nomeSupervisor);
        String cargoSupervisor = (String)request.getAttribute("cargoSupervisor");
        System.out.println("cargoSupervisor: "+cargoSupervisor);
        PessoaFisica pf = PessoaFisicaServices.buscarConvenioByNumero(numeroConvenio);
        System.out.println(pf);
        PessoaJuridica pj = PessoaJuridicaServices.buscarConvenioByNumero(numeroConvenio);
        System.out.println(pj);
        String agenciada = (String)request.getAttribute("Agenciada");
        System.out.println(agenciada);


        //NÃO OBRIGATÓRIO
        Boolean hasDataFim = (Boolean) request.getAttribute("hasDataFim");
        Boolean hasProfessor = (Boolean) request.getAttribute("hasProfessor");
        String isAgenteIntegracao = (String) request.getAttribute("isAgenteIntegracao");

        Date dataFimTermoEstagio = null;
        ProfessorOrientador professorOrientador = null;
        AgenteIntegracao agenteIntegracao = null;

        if (hasDataFim) {
            dataFimTermoEstagio = (Date) request.getAttribute("dataFim");
        }

        if (hasProfessor) {
            professorOrientador = new ProfessorOrientador((Integer) request.getAttribute("idProfessor"));
        }

        if (isAgenteIntegracao != null) {
            if (isAgenteIntegracao.equals("sim")) {
                pj.setAgenteIntegracao(true);
            }
        }
        
        TermoEstagio termoEstagio = null;
        if (pf != null) {
            termoEstagio = new TermoEstagio(dataInicioTermoEstagio, dataFimTermoEstagio, cargaHorariaTermoEstagio,
                    valorBolsa, enderecoTermoEstagio,complementoEnderecoTermoEstagio, 
                    bairroEnderecoTermoEstagio, cepEnderecoTermoEstagio,
                    cidadeEnderecoTermoEstagio, estadoEnderecoTermoEstagio, eEstagioObrigatorio,
                    aluno, pf, professorOrientador, nomeSupervisor, cargoSupervisor);
            termoEstagio.setAgenciada(agenciada);
        } else {
            termoEstagio = new TermoEstagio(dataInicioTermoEstagio, dataFimTermoEstagio, cargaHorariaTermoEstagio,
                    valorBolsa, enderecoTermoEstagio,
                    complementoEnderecoTermoEstagio, bairroEnderecoTermoEstagio, cepEnderecoTermoEstagio,
                    cidadeEnderecoTermoEstagio, estadoEnderecoTermoEstagio, eEstagioObrigatorio,
                    aluno, pj, professorOrientador, nomeSupervisor, cargoSupervisor);
            termoEstagio.setAgenciada(agenciada);
        }

        String msg = "";
        Logger lg = Logger.getLogger(IncluirTermoEstagioServlet.class);
        try {

            TermoEstagioServices.incluirTermoEstagio(termoEstagio);
            msg = messages.getString("br.cefetrj.sisgee.incluir_termo_estagio_servlet.msg_sucesso");
            request.setAttribute("msg", msg);

            lg.info(msg);
            request.getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (Exception e) {
            msg = messages.getString("br.cefetrj.sisgee.incluir_termo_estagio_servlet.msg_falha");
            request.setAttribute("msg", msg);
            System.out.println("Erro incluirTermoEstagioServlet: "+e);
            lg.error("Exception ao tentar inserir o Termo de Estágio", e);
            request.getRequestDispatcher("FormTermoEstagioServlet").forward(request, response);

        }

        System.out.println(msg);
    }
}