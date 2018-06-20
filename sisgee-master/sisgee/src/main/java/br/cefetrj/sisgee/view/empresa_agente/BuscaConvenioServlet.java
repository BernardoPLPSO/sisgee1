/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.view.empresa_agente;

import antlr.StringUtils;
import br.cefetrj.sisgee.control.PessoaFisicaServices;
import br.cefetrj.sisgee.control.PessoaJuridicaServices;
import br.cefetrj.sisgee.model.entity.PessoaFisica;
import br.cefetrj.sisgee.model.entity.PessoaJuridica;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Lucas Carvalho
 */
@WebServlet("/BuscaConvenioServlet")
public class BuscaConvenioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean validado = false;
        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);

        String buscaNumero = request.getParameter("buscaNumero");
        String buscaRazaoNome = request.getParameter("buscaRazaoNome");
        System.out.println("Busca Numero: " + buscaNumero);
        System.out.println("Busca Razao Nome: " + buscaRazaoNome);

        String buscaNumeroMsg;
        String buscaRazaoNomeMsg;
        String msg = "";

        if (buscaNumero.isEmpty() && buscaRazaoNome.isEmpty()) {

            msg = messages.getString("br.cefetrj.sisgee.resources.form.busca.nenhumCampo");
            buscaRazaoNomeMsg = messages.getString("br.cefetrj.sisgee.resources.form.busca.nenhumCampo");
            buscaNumeroMsg = messages.getString("br.cefetrj.sisgee.resources.form.busca.nenhumCampo");
            request.setAttribute("msg", msg);
            request.setAttribute("buscaRazaoNomeMsg", buscaRazaoNomeMsg);
            request.setAttribute("buscaNumeroMsg", buscaNumeroMsg);
            validado = false;

        } else if (!buscaNumero.isEmpty() && !buscaRazaoNome.isEmpty()) {

            msg = messages.getString("br.cefetrj.sisgee.resources.form.busca.doisCampos");
            buscaRazaoNomeMsg = messages.getString("br.cefetrj.sisgee.resources.form.busca.doisCampos");
            buscaNumeroMsg = messages.getString("br.cefetrj.sisgee.resources.form.busca.doisCampos");
            request.setAttribute("msg", msg);
            request.setAttribute("buscaRazaoNomeMsg", buscaRazaoNomeMsg);
            request.setAttribute("buscaNumeroMsg", buscaNumeroMsg);
            validado = false;

        } else {
            if (!buscaNumero.isEmpty()) {
                try {
                    int numeroConvenio = Integer.parseInt(buscaNumero);
                    validado = true;
                    request.setAttribute("buscaNumero", buscaNumero);
                } catch (Exception e) {
                    buscaNumeroMsg = messages.getString("br.cefetrj.sisgee.resources.form.busca.campoNumerico");
                    request.setAttribute("buscaNumeroMsg", buscaNumeroMsg);
                    validado = false;
                }

            } else if (!buscaRazaoNome.isEmpty()) {
                validado = true;
                request.setAttribute("buscaRazaoNome", buscaRazaoNome);

            } else {
                buscaRazaoNomeMsg = messages.getString("br.cefetrj.sisgee.resources.form.busca.nenhumCampo");
                request.setAttribute("buscaRazaoNomeMsg", buscaRazaoNomeMsg);
                validado = false;

            }
        }

        if (validado) {
            System.out.println("MSG: " + msg);
            request.getRequestDispatcher("/RenovaConvenioServlet").forward(request, response);
        } else {
            request.getRequestDispatcher("/form_renovacao.jsp").forward(request, response);
        }

    }
}
