/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.view.empresa_agente;

import br.cefetrj.sisgee.control.PessoaFisicaServices;
import br.cefetrj.sisgee.control.PessoaJuridicaServices;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import br.cefetrj.sisgee.view.utils.ValidaUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet("/RenovaServlet")
public class RenovaServlet extends HttpServlet {
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        boolean isValid = true;
        Integer tamanho = 0;
        
        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);

        String tipoPessoa = request.getParameter("tipoPessoa");
        String pessoaContato = request.getParameter("pessoaContato");
        
        
        if (tipoPessoa.equals("cnpj")) {
           
            
            
            /**
             * Validação da Pessoa de Contato do Cadastro Empresa usando
             * mÃ©todos da Classe ValidaUtils. Campo Opcional; Tamanho mÃ¡ximo
             * de 50 caracteres; RazÃ£o Social jÃ¡ existente.
             */
            String pessoaContatoMsg = "";
            pessoaContatoMsg = ValidaUtils.validaTamanho("Pessoa de Contato", 50, pessoaContato);
            if (pessoaContatoMsg.trim().isEmpty()) {
                request.setAttribute("pessoaContato", pessoaContato);
            } else {
                pessoaContatoMsg = messages.getString(pessoaContatoMsg);
                request.setAttribute("pessoaContatoMsg", pessoaContatoMsg);
                isValid = false;
            }
        }
        String numeroConvenio = request.getParameter("numeroConvenio");
        String dataAss = request.getParameter("dataAssinatura");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        
        
        
        /**
         * Validação da Data de Assinatura do Convênio com métodos da classe
         * ValidaUtils
         *
         * Campo obrigatório
         *
         */
        Date dataAssinatura = null;
        String dataAssinaturaMsg = "";

        dataAssinaturaMsg = ValidaUtils.validaObrigatorio("Data Assinatura", dataAss);
        if (dataAssinaturaMsg.trim().isEmpty()) {
            dataAssinaturaMsg = ValidaUtils.validaDate("Data Assinatura", dataAss);
            if (dataAssinaturaMsg.trim().isEmpty()) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    dataAssinatura = format.parse(dataAss);
                    request.setAttribute("dataAssinatura", dataAssinatura);
                } catch (Exception e) {
                    //TODO trocar saída de console por Log
                    System.out.println("Data em formato incorreto, mesmo após validação na classe ValidaUtils");
                    isValid = false;
                }
            } else {
                dataAssinaturaMsg = messages.getString(dataAssinaturaMsg);
                request.setAttribute("dataAssinaturaMsg", dataAssinaturaMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(dataAssinaturaMsg);
            }
        } else {
            dataAssinaturaMsg = messages.getString(dataAssinaturaMsg);
            request.setAttribute("dataAssinaturaMsg", dataAssinaturaMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(dataAssinaturaMsg);
        }

        /**
         * Validação do Email usando métodos da Classe ValidaUtils. Campo
         * Opcional; Tamanho máximo de 50 caracteres;
         *
         */
        String EmailMsg = "";

        System.out.println("email:" + email);

        if (email == null || email.trim().isEmpty() || email.equals("")){
            System.out.println("BOLINHA");
        } 
        else{
            System.out.println("Email dentro do if: "+email);
            EmailMsg = ValidaUtils.validaTamanho("Email", 50, email);
            if (EmailMsg.trim().isEmpty()) {
                EmailMsg = ValidaUtils.validaEmail("Email", email);
                if (EmailMsg.trim().isEmpty()) {

                    request.setAttribute("email", email);

                } else {
                    EmailMsg = ServletUtils.mensagemFormatada(EmailMsg, locale, tamanho);
                    request.setAttribute("EmailMsg", EmailMsg);
                    isValid = false;
                }
            } else {
                EmailMsg = messages.getString(EmailMsg);
                request.setAttribute("EmailMsg", EmailMsg);
                isValid = false;
            }
        }

        /**
         * Validação do Telefone usando métodos da Classe ValidaUtils. Campo
         * Opcional; Tamanho máximo de 11 caracteres;
         *
         */
        telefone = telefone.replaceAll("\\D", "");
        telefone = telefone.trim();

        String telefoneMsg = "";
        telefoneMsg = ValidaUtils.validaTamanho("telefone", 11, telefone);
        if (telefoneMsg.trim().isEmpty()) {
            telefoneMsg = ValidaUtils.validaInteger("telefone", telefone);
            if (telefoneMsg.trim().isEmpty()) {

                request.setAttribute("telefone", telefone);

            } else {
                telefoneMsg = ServletUtils.mensagemFormatada(telefoneMsg, locale, tamanho);
                request.setAttribute("telefoneMsg", telefoneMsg);
                isValid = false;
            }
        } else {
            telefoneMsg = messages.getString(telefoneMsg);
            request.setAttribute("telefoneMsg", telefoneMsg);
            isValid = false;
        }
        
        if (isValid) {
            String sucesso = "";
            if(tipoPessoa.equals("cnpj")){
                sucesso = PessoaJuridicaServices.atualizarConvenioPJ(numeroConvenio, email, pessoaContato, telefone, dataAssinatura);
            }else{
                    sucesso = PessoaFisicaServices.atualizarConvenioPF(numeroConvenio, email,telefone, dataAssinatura);
            }
            if(sucesso.trim().isEmpty()){
                request.getRequestDispatcher("/sucesso_renova.jsp").forward(request, response);
            }else {
                String msg = messages.getString("br.cefetrj.sisgee.valida_cadastro_empresa_servlet.msg_atencao");
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("/form_renova_atualiza.jsp").forward(request, response);
                
            }
            
         }else{
            String msg = messages.getString("br.cefetrj.sisgee.valida_cadastro_empresa_servlet.msg_atencao");
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/form_renova_atualiza.jsp").forward(request, response);
        }
  }
    
}
