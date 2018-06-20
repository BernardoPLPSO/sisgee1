package br.cefetrj.sisgee.view.empresa_agente;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.cefetrj.sisgee.control.AgenteIntegracaoServices;
import br.cefetrj.sisgee.control.AlunoServices;
import br.cefetrj.sisgee.control.EmpresaServices;
import br.cefetrj.sisgee.control.PessoaFisicaServices;
import br.cefetrj.sisgee.control.PessoaJuridicaServices;
import br.cefetrj.sisgee.control.ProfessorOrientadorServices;
import br.cefetrj.sisgee.model.entity.AgenteIntegracao;
import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.Empresa;
import br.cefetrj.sisgee.model.entity.PessoaFisica;
import br.cefetrj.sisgee.model.entity.PessoaJuridica;
import br.cefetrj.sisgee.model.entity.ProfessorOrientador;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import br.cefetrj.sisgee.view.utils.UF;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author diego
 */
@WebServlet("/IncluirCadastroEmpresaServlet")
public class IncluirCadastroEmpresaServlet extends HttpServlet {

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

        String tipoPessoa = request.getParameter("tipoPessoa");

        String dataAssinaturaStr = (String) request.getAttribute("dataAssinatura");
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAssinatura = null;
        try{
           dataAssinatura = (Date)formatter.parse(dataAssinaturaStr);
        }
        catch(Exception e){
            System.out.println("Erro data");
        }
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        telefone = telefone.replaceAll("\\D", "");
        String numConvenio = criarNumeroConvenio(request);
        System.out.println("nume convenio " +numConvenio);
        System.out.println("agente string " + request.getParameter("agenteIntegracao"));
        boolean agenteIntegracao = Boolean.parseBoolean(request.getParameter("agenteIntegracao"));
        System.out.println("agente sem string " + agenteIntegracao);
        
        if (tipoPessoa.equals("cnpj")) {
            String cnpjConvenio = request.getParameter("cnpjConvenio");
            cnpjConvenio = cnpjConvenio.replaceAll("\\D", "");
            String razaoSocial = request.getParameter("razaoSocial");
//            boolean agenteIntegracao = Boolean.parseBoolean(request.getParameter("agenteIntegracao"));
            System.out.println("Agente Integracao: "+ agenteIntegracao);
            String pessoaContato = request.getParameter("pessoaContato");

            PessoaJuridica J = new PessoaJuridica(cnpjConvenio, razaoSocial, agenteIntegracao, dataAssinatura);
            J.setNumeroConvenio(numConvenio);
            if (email != null) {
                J.setEmail(email);
            }
            if (telefone != null) {
                J.setTelefone(telefone);
            }
            if (pessoaContato != null) {
                J.setPessoaCOntato(pessoaContato);
            }
            
            
            System.out.println("");
            
            String msg = "";
            Logger lg = Logger.getLogger(IncluirCadastroEmpresaServlet.class);
            try {
                PessoaJuridicaServices.incluirConvenio(J);

                msg = messages.getString("br.cefetrj.sisgee.incluir_cadastro_empresa_servlet.msg_empresa_cadastrada");
                msg = msg +  "\n com número: " + J.getNumeroConvenio();
                request.setAttribute("msg", msg);
                lg.info(msg);
                request.getRequestDispatcher("/index.jsp").forward(request, response);

            } catch (Exception e) {
                msg = messages.getString("br.cefetrj.sisgee.incluir_cadastro_empresa_servlet.msg_ocorreu_erro");
                request.setAttribute("msg", msg);
                lg.error("Exception ao tentar inserir um convenio", e);
                request.getRequestDispatcher("/form_convenio.jsp").forward(request, response);

            }
        } else if (tipoPessoa.equals("cpf")) {
            String cpfConvenio = request.getParameter("cpfConvenio");
            String nomePessoa = request.getParameter("nomePessoa");
            cpfConvenio = cpfConvenio.replaceAll("\\D", "");

            PessoaFisica F = new PessoaFisica(cpfConvenio, nomePessoa, dataAssinatura);
            F.setNumeroConvenio(numConvenio);
            if (email != null) {
                F.setEmail(email);
            }
            if (telefone != null) {
                F.setTelefone(telefone);
            }

            String msg = "";
            Logger lg = Logger.getLogger(IncluirCadastroEmpresaServlet.class);
            try {
                PessoaFisicaServices.incluirConvenio(F);

                msg = messages.getString("br.cefetrj.sisgee.incluir_cadastro_empresa_servlet.msg_empresa_cadastrada");
                msg = msg +  "\n com número: " + F.getNumeroConvenio();
                request.setAttribute("msg", msg);
                lg.info(msg);
                request.getRequestDispatcher("/index.jsp").forward(request, response);

            } catch (Exception e) {
                msg = messages.getString("br.cefetrj.sisgee.incluir_cadastro_empresa_servlet.msg_ocorreu_erro");
                request.setAttribute("msg", msg);
                lg.error("Exception ao tentar inserir um convenio", e);
                request.getRequestDispatcher("/form_convenio.jsp").forward(request, response);

            }

        }
                

    }

    private static String criarNumeroConvenio(HttpServletRequest request) {

        List<PessoaFisica> conveniosPF = PessoaFisicaServices.listarConvenios();
        List<PessoaJuridica> conveniosPJ = PessoaJuridicaServices.listarConvenios();
        
        int total = conveniosPF.size() + conveniosPJ.size()+1;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String ano = Integer.toString(year);
        return (total +"/"+ano);
    }

}
