/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.view.empresa_agente;

import br.cefetrj.sisgee.view.utils.ItemRelatorio;
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

/**
 *
 * @author lucas
 */
@WebServlet("/ValidaRenovaConvenioServlet")
public class ValidaRenovaConvenioServlet extends HttpServlet {

    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);
        
        List<ItemRelatorio> Resultado = (List<ItemRelatorio>)request.getAttribute("Resultado");
        String index = request.getParameter("OLHA AQUI:"+"status");
        System.out.println(index);
        
    }
}