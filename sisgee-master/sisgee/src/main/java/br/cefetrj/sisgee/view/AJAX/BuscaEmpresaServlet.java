package br.cefetrj.sisgee.view.AJAX;

import java.io.IOException;
import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.sisgee.control.PessoaFisicaServices;
import br.cefetrj.sisgee.control.PessoaJuridicaServices;
import br.cefetrj.sisgee.model.entity.PessoaFisica;
import br.cefetrj.sisgee.model.entity.PessoaJuridica;
import java.math.BigDecimal;

/**
 * Servlet para trazer os dados da Empresa para a tela de cadastro de Termo de
 * Estágio, por meio de requisição AJAX.
 *
 * @author Augusto Jose
 * @since 1.0
 *
 */
@WebServlet("/BuscaEmpresaServlet")
public class BuscaEmpresaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        PessoaJuridica pj = null;
        PessoaFisica pf = null;
        String numeroConvenio = request.getParameter("numeroConvenio");
        String idConvenio = "";
        String nomeConvenio = "";
        String cpfConvenio = "";
        String cnpjConvenio = "";
        String razaoSocial = "";
        String isPessoa = "";
        boolean agenteIntegracao = false;
        if (numeroConvenio != null) {
            numeroConvenio = numeroConvenio.replaceAll("[.|/|-]", "");
        }
        System.out.println(numeroConvenio);
        if (numeroConvenio != "") {
            pj = PessoaJuridicaServices.buscarConvenioByNumero(numeroConvenio);
            pf = PessoaFisicaServices.buscarConvenioByNumero(numeroConvenio);
        }
        else{
            pj = PessoaJuridicaServices.buscarConvenioByNome(nomeConvenio);
            pf = PessoaFisicaServices.buscarConvenioByNome(nomeConvenio);
        }
        if (pj != null) {
            idConvenio = Integer.toString(pj.getIdConvenio());
            razaoSocial = pj.getRazaoSocial();
            cnpjConvenio = pj.getCnpj();
            agenteIntegracao = pj.isAgenteIntegracao();
            isPessoa = "PJ";
        } else if (pf != null) {
            idConvenio = Integer.toString(pf.getIdConvenio());
            nomeConvenio = pf.getNome();
            cpfConvenio = pf.getCpf();
            isPessoa = "PF";
        } else {
            System.out.println("Não encontrou");
        }

        //JSON
        JsonObject model = Json.createObjectBuilder()
                .add("idConvenio", idConvenio)
                .add("nomePF", nomeConvenio)
                .add("cpfConvenio", cpfConvenio)
                .add("cnpjConvenio", cnpjConvenio)
                .add("razaoSocial", razaoSocial)
                .add("isPessoa",isPessoa)
                .add("agenteIntegracao",agenteIntegracao)
                .build();

        StringWriter stWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(stWriter);
        jsonWriter.writeObject(model);
        jsonWriter.close();
        String jsonData = stWriter.toString();

        response.setContentType("application/json");
        response.getWriter().print(jsonData);
    }

}