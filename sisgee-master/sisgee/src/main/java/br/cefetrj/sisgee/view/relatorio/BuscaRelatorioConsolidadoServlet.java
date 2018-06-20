package br.cefetrj.sisgee.view.relatorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.sisgee.model.entity.TermoEstagio;
import br.cefetrj.sisgee.control.TermoEstagioServices;
import br.cefetrj.sisgee.control.TermoAditivoServices;
import br.cefetrj.sisgee.view.utils.ItemRelatorio;
import br.cefetrj.sisgee.model.entity.Aluno;

/**
 * Servlet para buscar e processar os dados obtidos do banco.
 *
 * @author Marcos E Carvalho
 * @since 1.0
 *
 */
@WebServlet("/BuscaRelatorioConsolidadoServlet")
public class BuscaRelatorioConsolidadoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscaRelatorioConsolidadoServlet() {
        super();
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Date dataInicio = (Date) request.getAttribute("dataInicio");
        Date dataTermino = (Date) request.getAttribute("dataTermino");
        Boolean estagioObrig = (Boolean) request.getAttribute("estagioObrig");
        Boolean estagioNaoObrig = (Boolean) request.getAttribute("estagioNaoObrig");

        Locale locale = (Locale) request.getAttribute("Locale");
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);

        System.out.println(dataInicio);
        System.out.println(dataTermino);
        System.out.println(estagioObrig);
        System.out.println(estagioNaoObrig);

        List<Object[]> termosEstagioLista = null;

        // pegando os termos com estagio obrigatorio ou não ou ambos
        if (estagioObrig == true && estagioNaoObrig == true) {
            termosEstagioLista = TermoEstagioServices.listarTermoEstagioFiltrado(null, dataInicio, dataTermino);
        } else if (estagioObrig == true && estagioNaoObrig == false) {
            termosEstagioLista = TermoEstagioServices.listarTermoEstagioFiltrado(true, dataInicio, dataTermino);
        } else if (estagioObrig == false && estagioNaoObrig == true) {
            termosEstagioLista = TermoEstagioServices.listarTermoEstagioFiltrado(false, dataInicio, dataTermino);
        }
        
        List<ItemRelatorio> listaItemRelatorio = new ArrayList<ItemRelatorio>();

        if (!(termosEstagioLista.isEmpty())) {
            listaItemRelatorio = qntdPorCurso(termosEstagioLista);
            Object[] aux = null;
            String cursoNome = null;
            for (Iterator<ItemRelatorio> iterator = listaItemRelatorio.iterator(); iterator.hasNext();) {
                ItemRelatorio itemRelatorio = (ItemRelatorio) iterator.next();
            }
            
        } else {
            String msgRelatorio = messages.getString("br.cefetrj.sisgee.relatorio.busca_relatorio_consolidado_servlet.nenhum_resultado");
            request.setAttribute("msgRelatorio", msgRelatorio);
        }

        request.getSession().setAttribute("relatorio", listaItemRelatorio);
        request.getRequestDispatcher("/relatorio_consolidado.jsp").forward(request, response);
    }

    /**
     * Metodo para relacionar nomecurso com termo estagio e termo rescisao.
     *
     * @param termosEstagioLista matriz com conteúdo obtido do banco
     * @return listaItemRelatorio
     */
    public static List<ItemRelatorio> qntdPorCurso(List<Object[]> termosEstagioLista) {
        List<ItemRelatorio> listaItemRelatorio = new ArrayList<ItemRelatorio>();

        // guarda os valores retornados do SELECT no banco
        Object[] aux = null;
        ItemRelatorio item = null;
        String stg = null;
        /*for (TermoEstagio termo : termosEstagioLista){
            String curso = termo.getAluno().getNomeCurso();
            int idx = 0;
            if(listaItemRelatorio.contains(new ItemRelatorio(curso))){
                idx = listaItemRelatorio.indexOf(new ItemRelatorio(curso));
                item = listaItemRelatorio.get(idx);
                item.setQntTermoEstagio(1);
            }
            if(!termo.getTermosAditivos().isEmpty()){
                item.setQntTermoAditivo(termo.getTermosAditivos().size());
            }
            else {
                listaItemRelatorio.add(new ItemRelatorio(curso, 1, 0));
            }
            
        }*/
        for (int i = 0; i < termosEstagioLista.size(); i++) {

            aux = termosEstagioLista.get(i);
            // deveria receber o nome do curso da tabela rs
            stg = (String) aux[2];
            // index do curso na lista
            int idx = 0;
            int verifResc = 0;

            //verifica se existe termo de rescisao
            if (aux[1] == null) {
                verifResc = 0;
            } else {
                verifResc = 1;
            }

            // verifica se a lista contem o curso 
            if (listaItemRelatorio.contains(new ItemRelatorio(stg))) {
                // se a lista possuir o curso, entao os dados serão adicionados no index do curso já existente
                idx = listaItemRelatorio.indexOf(new ItemRelatorio(stg));
                item = listaItemRelatorio.get(idx);
                item.setQntTermoEstagio(1);

                // verifica se existe aditivo
                if (aux[3] != null) {
                    item.setQntTermoAditivo(1);
                }

                item.setQntRescReg(item.getQntRescReg() + verifResc);
                // se a lista não possuir o curso, ele é adicionado
            } else {
                item = new ItemRelatorio(stg, 1, verifResc);
                listaItemRelatorio.add(item);
                if(aux[3] != null){
                    item.setQntTermoAditivo(1);
                }
            }
        }

        return listaItemRelatorio;
    }

}