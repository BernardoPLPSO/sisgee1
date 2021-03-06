/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.view.empresa_agente;

import br.cefetrj.sisgee.control.PessoaFisicaServices;
import br.cefetrj.sisgee.control.PessoaJuridicaServices;
import br.cefetrj.sisgee.model.entity.PessoaFisica;
import br.cefetrj.sisgee.model.entity.PessoaJuridica;
import br.cefetrj.sisgee.view.utils.ItemRelatorio;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lucas
 */
@WebServlet("/ValidaRenovaConvenioServlet")
public class ValidaRenovaConvenioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);

        String numeroConvenio = request.getParameter("convenio");

        System.out.println("ouu" + numeroConvenio);

        PessoaFisica ConvePF = null;
        PessoaJuridica ConvePJ = null;

        if (PessoaFisicaServices.buscarConvenioByNumero(numeroConvenio) != null) {
            ConvePF = PessoaFisicaServices.buscarConvenioByNumero(numeroConvenio);
        }
        if (PessoaJuridicaServices.buscarConvenioByNumero(numeroConvenio) != null) {
            ConvePJ = PessoaJuridicaServices.buscarConvenioByNumero(numeroConvenio);
        }

        if (ConvePF == null && ConvePJ == null) {

            String msg = messages.getString("br.cefetrj.sisgee.resources.form.busca.semResultado");
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/erro.jsp").forward(request, response);

        } else {
            if (ConvePJ == null) {
                request.setAttribute("tipoPessoa", "cpf");
                request.setAttribute("cpfConvenio", ConvePF.getCpf());
                request.setAttribute("nomePessoa", ConvePF.getNome());
                Date dataAssinatura = ConvePF.getDataAssinatura();
                try {
                    String dataAssinaturaStr = ServletUtils.mudarFormatoData(dataAssinatura);
                    request.setAttribute("dataAssinatura", dataAssinaturaStr);
                } catch (Exception e) {
                    System.out.println("erro data");
                }
                String teste = ConvePF.getEmail();
                request.setAttribute("email", teste);
                request.setAttribute("telefone", ConvePF.getTelefone());
                System.out.println(ConvePF.getCpf());
                System.out.println(ConvePF.getNome());

                request.getRequestDispatcher("/form_renova_atualiza.jsp").forward(request, response);

            } else if (ConvePF == null) {
                request.setAttribute("tipoPessoa", "cnpj");
                request.setAttribute("cnpjConvenio", ConvePJ.getCnpj());
                request.setAttribute("razaoSocial", ConvePJ.getRazaoSocial());
                request.setAttribute("pessoaContato", ConvePJ.getPessoaCOntato());
                System.out.println("Is agente integracao: " + ConvePJ.isAgenteIntegracao());
                String aiStr = Boolean.toString(ConvePJ.isAgenteIntegracao());
                request.setAttribute("agenteIntegracao", aiStr);
                Date dataAssinatura = ConvePJ.getDataAssinatura();
                try {
                    String dataAssinaturaStr = ServletUtils.mudarFormatoData(dataAssinatura);
                    request.setAttribute("dataAssinatura", dataAssinaturaStr);
                } catch (Exception e) {
                    System.out.println("erro data");
                }
                String teste = ConvePJ.getEmail();
                request.setAttribute("email", teste);
                request.setAttribute("telefone", ConvePJ.getTelefone());
                System.out.println(ConvePJ.getCnpj());
                System.out.println(ConvePJ.getRazaoSocial());

                request.getRequestDispatcher("/form_renova_atualiza.jsp").forward(request, response);

            }
        }

    }
}
