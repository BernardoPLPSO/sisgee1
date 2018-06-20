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
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
@WebServlet("/ConveniosVencerServlet")
public class ConveniosVencerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);
        Logger lg = Logger.getLogger(RenovaConvenioServlet.class);
        List<ItemRelatorio> Resultado = new ArrayList<ItemRelatorio>();

        PessoaFisica ConvePF = null;
        PessoaJuridica ConvePJ = null;
        List<PessoaFisica> buscaConvePF = new ArrayList();
        List<PessoaJuridica> buscaConvePJ = new ArrayList();

        try {

            buscaConvePF = PessoaFisicaServices.listarConvenios();
            buscaConvePJ = PessoaJuridicaServices.listarConvenios();

            if (buscaConvePF != null) {
                for (PessoaFisica P : buscaConvePF) {
                    Date Expira = P.getDataAssinatura();
                    Calendar c = Calendar.getInstance();
                    Date atual = c.getTime();
                    c.setTime(Expira);
                    c.add(Calendar.YEAR, +4);
                    c.add(Calendar.MONTH, +10);
                    Date dataFim = c.getTime();
                    System.out.println("Data Assinatura: "+Expira);
                    System.out.println("Data Alerta: "+dataFim);
                    c.setTime(Expira);
                    c.add(Calendar.YEAR, +5);
                    Date pos = c.getTime();
                    System.out.println("Já passou de 5 anos "+pos);
                    
                    SimpleDateFormat F = new SimpleDateFormat("yyyy-MM-dd");
                    String Format = F.format(atual);
                    String Format2 = F.format(dataFim);
                    dataFim = F.parse(Format2);
                    atual = F.parse(Format);

                    if (atual.after(dataFim)) {
                        if (atual.before(pos)) {

                            System.out.println(P.getDataAssinatura());

                            System.out.println(atual);
                            System.out.println(dataFim);
                            ItemRelatorio item = new ItemRelatorio(P.getNumeroConvenio(), P.getCpf(), P.getNome(), P, P.getClass());
                            Resultado.add(item);
                            
                        }
                        System.out.println("PF - Tem resultado? "+ Resultado);
                    }

                }
            }
            if (buscaConvePJ != null) {
                for (PessoaJuridica J : buscaConvePJ) {

                    Date Expira = J.getDataAssinatura();
                    Calendar c = Calendar.getInstance();
                    Date atual = c.getTime();
                    c.setTime(Expira);
                    c.add(Calendar.YEAR, +4);
                    c.add(Calendar.MONTH, +10);
                    Date dataFim = c.getTime();
                    c.add(Calendar.MONTH, +2);
                    Date pos = c.getTime();

                    SimpleDateFormat F = new SimpleDateFormat("yyyy-MM-dd");
                    String Format = F.format(atual);
                    String Format2 = F.format(dataFim);
                    dataFim = F.parse(Format2);
                    atual = F.parse(Format);

                    if (atual.after(dataFim)) {
                        if (atual.before(pos)) {

                            System.out.println(atual);
                            System.out.println(dataFim);
                            ItemRelatorio item = new ItemRelatorio(J.getNumeroConvenio(), J.getCnpj(), J.getRazaoSocial(), J, J.getClass());
                            Resultado.add(item);
                        }
                    }
                    System.out.println("PJ - Tem resultado? " + Resultado);
                }
            }
            if (Resultado.isEmpty()) {
                request.setAttribute("Resultado", null);
                String msg = messages.getString("br.cefetrj.sisgee.incluir_cadastro_empresa_servlet.msg_empresa_semResultado");
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("Resultado", Resultado);
            }

            request.getRequestDispatcher("/form_convenioVencer.jsp").forward(request, response);
        } catch (Exception e) {
            lg.error("Exception ao tentar inserir um convenio", e);
            String msg = messages.getString("br.cefetrj.sisgee.resources.form.busca.erroBusca");
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }

    }

}
