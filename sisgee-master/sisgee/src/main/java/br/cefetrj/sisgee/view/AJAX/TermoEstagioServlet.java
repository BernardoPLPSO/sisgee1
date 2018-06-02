package br.cefetrj.sisgee.view.AJAX;

import br.cefetrj.sisgee.control.TermoEstagioServices;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import java.io.IOException;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Servlet que recupera os dados do termo de estágio em formato JSON.
 * 
 * @author Diogo
 */
@WebServlet("/TermoEstagioServlet")
public class TermoEstagioServlet extends HttpServlet {

    /**
     * Recupera o termo de estágio no formato JSON. 
     * Deve ser passado o parâmetro idTermoEstagio no request.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException se um erro específico de servlet ocorrer
     * @throws IOException se um erro de I/O ocorrer
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        boolean error = false;
        ServletUtils.ErrorCode errorCode = null;
        String idTermoEstagio = request.getParameter("idTermoEstagio");
        if(idTermoEstagio != null && idTermoEstagio.trim().length() > 0){
            //termo
            TermoEstagio t = null;
            try{
                t = TermoEstagioServices.buscarTermoEstagio(Integer.parseInt(idTermoEstagio));
                if(t != null){
                    response.getWriter().print(t.toJson());
                }else{
                    error = true;
                    errorCode = ServletUtils.ErrorCode.INVALID;
                }
            }catch(NumberFormatException e){
                Logger lg = Logger.getLogger(TermoEstagioServlet.class);
		lg.error("Erro ao converter para inteiro idTermoEstagio = " + idTermoEstagio, e);
                error = true;
                errorCode = ServletUtils.ErrorCode.INVALID;
            }
        }else{
            error = true;
            errorCode = ServletUtils.ErrorCode.REQUIRED;
        }
        
        if (error){
            JsonObjectBuilder json = Json.createObjectBuilder();
            json.add("idTermoEstagio", idTermoEstagio);
            json.add("errorCode", errorCode.toString());
            StringWriter stWriter = new StringWriter();
            JsonWriter jsonWriter = Json.createWriter(stWriter);
            jsonWriter.writeObject(json.build());
            jsonWriter.close();
            String jsonData = stWriter.toString();
            response.getWriter().print(jsonData);
        }
        
    }


}
