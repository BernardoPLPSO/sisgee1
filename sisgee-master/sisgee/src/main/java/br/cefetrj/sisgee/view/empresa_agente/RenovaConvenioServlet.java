/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.view.empresa_agente;

import br.cefetrj.sisgee.control.PessoaFisicaServices;
import static br.cefetrj.sisgee.control.PessoaFisicaServices.buscarListaNome;
import br.cefetrj.sisgee.control.PessoaJuridicaServices;
import static br.cefetrj.sisgee.control.PessoaJuridicaServices.listarConvenios;
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

@WebServlet("/RenovaConvenioServlet")
public class RenovaConvenioServlet extends HttpServlet {

   

    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);
        Logger lg = Logger.getLogger(RenovaConvenioServlet.class);
        List<Object> Resultado = null;
        
        String buscaNumero = request.getParameter("buscaNumero");
        String buscaRazaoNome = request.getParameter("buscaRazaoNome");
        
        System.out.println(buscaNumero);
        System.out.println(buscaRazaoNome);
        try{
            if(!buscaRazaoNome.isEmpty()){
                 List<PessoaFisica> buscaConvePF = PessoaFisicaServices.buscarListaNome(buscaRazaoNome);
                 List<PessoaJuridica> buscaConvePJ = PessoaJuridicaServices.buscarListaNome(buscaRazaoNome);
                 
                 for(PessoaFisica P : buscaConvePF){
                     Resultado.add(P);
                 }
                 for(PessoaJuridica J : buscaConvePJ){
                     Resultado.add(J); 
                 }
                 request.setAttribute("Resultado", Resultado);
                 
            }else if(!buscaNumero.isEmpty()){
                 PessoaFisica buscaConvePF = PessoaFisicaServices.buscarConvenioByNumero(buscaNumero);
                 PessoaJuridica buscaConvePJ = PessoaJuridicaServices.buscarConvenioByNumero(buscaNumero);
                if(buscaConvePF == null && buscaConvePJ==null){
                    request.setAttribute("Resultado", null);
                    
                }else{ if(buscaConvePJ == null){
                    Resultado.add(buscaConvePF);
                    request.setAttribute("Resultado", Resultado);
                    
                }else{
                    Resultado.add(buscaConvePJ);
                    request.setAttribute("Resultado", Resultado);
                    
                }
                } 
                
               request.getRequestDispatcher("/form_renovacaoResul.jsp").forward(request, response);  
                 
            }
        }catch(Exception e){
            lg.error("Exception ao tentar inserir um convenio", e);
            String msg = messages.getString("br.cefetrj.sisgee.valida_cadastro_empresa_servlet.msg_atencao");
	    request.setAttribute("msg", msg);
            request.getRequestDispatcher("/form_renovacao.jsp").forward(request, response);
        }
            
            
        
        
        
    }

   
}
