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
 * @author Lucas Carvalho
 */
@WebServlet("/BuscaConvenioServlet")
public class BuscaConvenioServlet extends HttpServlet {
    
  @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
        boolean validado;
        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);
        
        String buscaNumero = request.getParameter("buscaNumero");
        String buscaRazaoNome = request.getParameter("buscaRazaoNome");
        
        String buscaNumeroMsg;
        String buscaRazaoNomeMsg;
        String msg;
         
        List<PessoaFisica> conveniosPF = PessoaFisicaServices.listarConvenios();
        List<PessoaJuridica> conveniosPJ = PessoaJuridicaServices.listarConvenios();
        
        try{
            if(!buscaNumero.trim().isEmpty() && !buscaRazaoNome.trim().isEmpty() ){
                
                msg = messages.getString("Preencha apenas um dos campos de busca!");
                request.setAttribute("msg", msg);
                validado = false;
                
                
            }else if(buscaNumero.trim().isEmpty() && buscaRazaoNome.trim().isEmpty() ){
                msg = messages.getString("Preencha um dos campos de busca!");
                request.setAttribute("msg", msg);
                validado = false;   
                
            }
        if(!buscaNumero.trim().isEmpty()){
            if(buscaNumero.contains("\\D")){
                buscaNumeroMsg = messages.getString("Apenas numeros são válidos neste campo");
                request.setAttribute("buscaNumeroMsg", buscaNumeroMsg);
                validado = false;
                 } else{
                validado = true;
                request.setAttribute("buscaNumero", buscaNumero);
                 }
         
                  }else if(!buscaRazaoNome.trim().isEmpty()){
                      validado = true;
                      request.setAttribute("buscaRazaoNome", buscaRazaoNome);
                    }else{
                      validado = false;
                    }
        
        if(validado){
            request.getRequestDispatcher("/RenovaConvenioServlet").forward(request, response);
        }
        
        
        }catch(Exception e){
                msg = messages.getString("br.cefetrj.sisgee.incluir_cadastro_empresa_servlet.msg_ocorreu_erro");
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("/form_renovacao.jsp").forward(request, response);
            
        }
        
        
    }
}
