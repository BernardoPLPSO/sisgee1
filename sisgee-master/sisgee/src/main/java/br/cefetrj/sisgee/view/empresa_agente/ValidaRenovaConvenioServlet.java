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
        
        String numeroConvenio =  request.getParameter("convenio");
        
        System.out.println("ouu"+numeroConvenio);
        
        PessoaFisica ConvePF = null;
        PessoaJuridica ConvePJ = null;
        
        if (PessoaFisicaServices.buscarConvenioByNumero(numeroConvenio) != null) {
            ConvePF = PessoaFisicaServices.buscarConvenioByNumero(numeroConvenio);
        }
        if (PessoaJuridicaServices.buscarConvenioByNumero(numeroConvenio) != null) {
            ConvePJ = PessoaJuridicaServices.buscarConvenioByNumero(numeroConvenio);
        } 
        
        if(ConvePF == null && ConvePJ==null){
                    
                    String msg = messages.getString("br.cefetrj.sisgee.resources.form.busca.semResultado");
                    request.setAttribute("msg", msg);
                    
                }else{ if(ConvePJ == null){
                    
                    
                    
                    
                }else if(ConvePF == null){
                    
                   
                    
                    
                }
                } 
        
        
        
        
    }
}