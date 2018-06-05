/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.view.relatorio;

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
public class ImprimeRelatorioConsolidadoServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        List<ItemRelatorio> listaItemRelatorio = (List) request.getAttribute("relatorio");
        
        request.setAttribute("relatorioIm", listaItemRelatorio);
        request.getRequestDispatcher("/relatorio_consolidado_lista.jsp").forward(request, response);
        
    }
    
}
