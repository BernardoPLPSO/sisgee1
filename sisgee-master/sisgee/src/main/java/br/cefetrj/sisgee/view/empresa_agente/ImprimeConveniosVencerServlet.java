/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.view.empresa_agente;

import br.cefetrj.sisgee.view.utils.ItemRelatorio;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author daniel
 */
public class ImprimeConveniosVencerServlet extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        List<ItemRelatorio> listaItemRelatorio = (List) request.getAttribute("Resultado");
        
        request.setAttribute("Resultado", listaItemRelatorio);
        request.getRequestDispatcher("/form_convenioVencerRelatorio.jsp").forward(request, response);
        request.getSession().setAttribute("Resultado", null);
    }
    
}
