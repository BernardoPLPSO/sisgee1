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
import br.cefetrj.sisgee.control.EmpresaServices;
import br.cefetrj.sisgee.model.entity.AgenteIntegracao;
import br.cefetrj.sisgee.model.entity.Empresa;
import br.cefetrj.sisgee.view.utils.ServletUtils;

@WebServlet("/IncluirCadastroEmpresaServlet")
public class IncluirCadastroEmpresaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Locale locale = ServletUtils.getLocale(request);
		ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);
		String cnpjEmpresa = request.getParameter("cnpjEmpresa");
                //remove caracteres especiais antes de salvar o CNPJ
                cnpjEmpresa = cnpjEmpresa.replaceAll("[.|/|-]", "");
		String nomeEmpresa = request.getParameter("nomeEmpresa");
		String agenteIntegracao = request.getParameter("agenteIntegracao");


		Empresa empresa = new Empresa(cnpjEmpresa, nomeEmpresa, null);
		AgenteIntegracao agente = new AgenteIntegracao(nomeEmpresa, cnpjEmpresa);
		

		if (agenteIntegracao.equals("nao")) {
			System.out.println(agenteIntegracao);
			String msg = "";
			Logger lg = Logger.getLogger(IncluirCadastroEmpresaServlet.class);
			try {
				EmpresaServices.incluirEmpresa(empresa);
				msg = messages.getString("br.cefetrj.sisgee.incluir_cadastro_empresa_servlet.msg_empresa_cadastrada");
				request.setAttribute("msg", msg);
				lg.info(msg);
				request.getRequestDispatcher("/index.jsp").forward(request, response);

			} catch (Exception e) {
				msg = messages.getString("br.cefetrj.sisgee.incluir_cadastro_empresa_servlet.msg_ocorreu_erro");
				request.setAttribute("msg", msg);
				lg.error("Exception ao tentar inserir uma Empresa", e);
				request.getRequestDispatcher("/form_empresa.jsp").forward(request, response);

			}

		} else {
			String msg = "";
			Logger lg = Logger.getLogger(IncluirCadastroEmpresaServlet.class);
			try {
				AgenteIntegracaoServices.incluirAgenteIntegracao(agente);
				msg = messages.getString("br.cefetrj.sisgee.incluir_cadastro_empresa_servlet.msg_agente_cadastrado");
				request.setAttribute("msg", msg);
				lg.info(msg);
				request.getRequestDispatcher("/index.jsp").forward(request, response);

			} catch (Exception e) {
				msg = messages.getString("br.cefetrj.sisgee.incluir_cadastro_empresa_servlet.msg_ocorreu_erro");
				request.setAttribute("msg", msg);
				lg.error("Exception ao tentar inserir uma Empresa", e);
				request.getRequestDispatcher("/form_empresa.jsp").forward(request, response);
                                

			}

		}
	}

}
