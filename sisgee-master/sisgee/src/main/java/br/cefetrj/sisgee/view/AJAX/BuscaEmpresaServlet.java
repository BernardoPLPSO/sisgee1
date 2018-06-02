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


/**
 * Servlet para trazer os dados da Empresa para a tela de cadastro de Termo
 * de Estágio, por meio de requisição AJAX.
 * 
 * @author Augusto Jose
 * @since 1.0
 *
 */
@WebServlet("/BuscaEmpresaServlet")
public class BuscaEmpresaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String numeroConvenio = request.getParameter("numeroConvenio");
		String idConvenio = "";
		String nomeConvenio = "";
                System.out.println(numeroConvenio);
                if(numeroConvenio != null){
                    numeroConvenio = numeroConvenio.replaceAll("[.|/|-]", "");
                }
                PessoaJuridica pj = PessoaJuridicaServices.buscarConvenioByNumero(numeroConvenio);
                PessoaFisica pf = PessoaFisicaServices.buscarConvenioByNumero(numeroConvenio);
                if(pj != null) {
                    idConvenio = Integer.toString(pj.getIdConvenio());
                    nomeConvenio = pj.getRazaoSocial();
		}
                else if(pf != null){
                    idConvenio = Integer.toString(pf.getIdConvenio());
                    nomeConvenio = pf.getNome();
                }
                else{
                    idConvenio = "";
                    nomeConvenio = "";
                    System.out.println("Não encontrou");
                }
                System.out.println(nomeConvenio);
                
		
		//JSON
		JsonObject model = Json.createObjectBuilder()
				.add("idConvenio", idConvenio)
				.add("nomeConvenio", nomeConvenio)
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
