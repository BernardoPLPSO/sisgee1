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
import br.cefetrj.sisgee.view.utils.ItemRelatorio;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        List<ItemRelatorio> Resultado = new ArrayList<ItemRelatorio>();
        
        PessoaFisica ConvePF = null;
        PessoaJuridica ConvePJ = null;
        List<PessoaFisica> buscaConvePF = null;
        List<PessoaJuridica> buscaConvePJ = null;
        
        String buscaNumero = request.getParameter("buscaNumero");
        String buscaRazaoNome = request.getParameter("buscaRazaoNome");
        
        System.out.println(buscaNumero);
        System.out.println(buscaRazaoNome);
        try{
            if(!buscaRazaoNome.isEmpty()){
                 buscaConvePF = PessoaFisicaServices.buscarListaNome(buscaRazaoNome);
                 buscaConvePJ = PessoaJuridicaServices.buscarListaNome(buscaRazaoNome);
                 System.out.println(buscaConvePF+"LOLO");
                 if(buscaConvePF!=null){
                 for(PessoaFisica P : buscaConvePF){
                     ItemRelatorio item = new ItemRelatorio(P.getNumeroConvenio(),P.getCpf(),P.getNome(),P,P.getClass());
                     Resultado.add(item);
                 }}
                 if(buscaConvePJ!=null){
                 for(PessoaJuridica J : buscaConvePJ){
                     ItemRelatorio item = new ItemRelatorio(J.getNumeroConvenio(),J.getCnpj(),J.getRazaoSocial(),J,J.getClass());
                     Resultado.add(item); 
                 }}
                 if(Resultado.isEmpty()){
                    request.setAttribute("Resultado", null); 
                 }else{
                 request.setAttribute("Resultado", Resultado);
                 }
            }else if(!buscaNumero.isEmpty()){
                if(PessoaFisicaServices.buscarConvenioByNumero(buscaNumero)!= null){
                    ConvePF = PessoaFisicaServices.buscarConvenioByNumero(buscaNumero);
                }
                if(PessoaJuridicaServices.buscarConvenioByNumero(buscaNumero)!=null){
                    ConvePJ = PessoaJuridicaServices.buscarConvenioByNumero(buscaNumero);
                }
                if(buscaConvePF == null && buscaConvePJ==null){
                    request.setAttribute("Resultado", null);
                    String msg = messages.getString("br.cefetrj.sisgee.resources.form.busca.semResultado");
                    request.setAttribute("msg", msg);
                    
                }else{ if(buscaConvePJ == null){
                    ItemRelatorio item = new ItemRelatorio(ConvePF.getNumeroConvenio(),ConvePF.getCpf(),ConvePF.getNome(),ConvePF,ConvePF.getClass());
                    Resultado.add(item);
                    request.setAttribute("Resultado", Resultado);
                    
                }else{
                    ItemRelatorio item = new ItemRelatorio(ConvePJ.getNumeroConvenio(),ConvePJ.getCnpj(),ConvePJ.getRazaoSocial(),ConvePJ,ConvePJ.getClass());
                    Resultado.add(item);
                    request.setAttribute("Resultado", Resultado);
                    
                }
                } 
              
                
                 
            }
            
            request.getRequestDispatcher("/form_renovacao.jsp").forward(request, response); 
        }catch(Exception e){
            lg.error("Exception ao tentar inserir um convenio", e);
            String msg = messages.getString("br.cefetrj.sisgee.resources.form.busca.erroBusca");
	    request.setAttribute("msg", msg);
            request.getRequestDispatcher("/form_renovacao.jsp").forward(request, response);
        }
            
            
        
        
        
    }

   
}
