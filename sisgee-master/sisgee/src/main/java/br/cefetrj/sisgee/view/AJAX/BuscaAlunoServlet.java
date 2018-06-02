package br.cefetrj.sisgee.view.AJAX;

import java.io.IOException;
import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.sisgee.control.AlunoServices;
import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 * Servlet para trazer os dados do Aluno por meio de requisição AJAX.
 *
 * @author Augusto Jose
 * @since 1.0
 *
 */
@WebServlet("/BuscaAlunoServlet")
public class BuscaAlunoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String matricula = request.getParameter("matricula");
        String idAluno = "";
        String nomeAluno = "";
        String nomeCurso = "";
        String nomeCampus = "";
        String idTermoEstagioAtivo = "";

        
        Aluno aluno = AlunoServices.buscarAlunoByMatricula(matricula.trim());
        if (aluno != null) {
            List<TermoEstagio> termosAditivos = aluno.getTermoEstagios();
            request.getServletContext().setAttribute("termosAditivos", termosAditivos);
            for (TermoEstagio t : termosAditivos){
            System.out.println(t.getTermoEstagio());    
            }
            
            idAluno = Integer.toString(aluno.getIdAluno());
            nomeAluno = aluno.getNomeAluno();
            nomeCurso = aluno.getNomeCurso();
            nomeCampus = aluno.getNomeCampus();
            List<TermoEstagio> termos = aluno.getTermoEstagios();
            System.out.println(aluno.getNomeAluno());
            if (!termos.isEmpty() || termos == null) {
                System.out.println("Passei aqui");
                for (TermoEstagio termo : termos) {
                    if (termo.getDataFimTermoEstagio() == null) {
                        idTermoEstagioAtivo
                                = (termo.getIdTermoEstagio() != null
                                ? termo.getIdTermoEstagio().toString()
                                : "");
                        termo.getDataInicioTermoEstagio();

                    }
                }
            }
        } else {
            System.out.println("Vazio");
        }
        //JSON
        JsonObject model = Json.createObjectBuilder()
                .add("idAluno", idAluno)
                .add("nome", nomeAluno)
                .add("nomeCurso", nomeCurso)
                .add("nomeCampus", nomeCampus)
                .add("idTermoEstagioAtivo", idTermoEstagioAtivo)
                .build();

        StringWriter stWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(stWriter);
        jsonWriter.writeObject(model);
        jsonWriter.close();
        String jsonData = stWriter.toString();

        response.setContentType("application/json");
        response.getWriter().print(jsonData);
    }

}
