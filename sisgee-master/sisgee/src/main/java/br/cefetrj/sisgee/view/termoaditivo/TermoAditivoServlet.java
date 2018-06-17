package br.cefetrj.sisgee.view.termoaditivo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.sisgee.control.AlunoServices;
import br.cefetrj.sisgee.control.ProfessorOrientadorServices;
import br.cefetrj.sisgee.control.TermoAditivoServices;
import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.ProfessorOrientador;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import br.cefetrj.sisgee.view.utils.UF;
import br.cefetrj.sisgee.view.utils.ValidaUtils;
import java.text.ParseException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/TermoAditivoServlet")
public class TermoAditivoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);

        String idAluno = request.getParameter("idAluno");
        String cargaHoraria = request.getParameter("cargaHoraria") == null ? "readonly" : "";
        request.setAttribute("cargaHoraria", cargaHoraria);
        String valor = request.getParameter("valor") == null ? "readonly" : "";
        request.setAttribute("valor", valor);
        String endereco = request.getParameter("endereco") == null ? "readonly" : "";
        request.setAttribute("endereco", endereco);
        String vigencia = request.getParameter("vigencia") == null ? "readonly" : "";
        request.setAttribute("vigencia", vigencia);
        String professor = request.getParameter("professor") == null ? "readonly" : "";
        request.setAttribute("professor", professor);

        Aluno aluno = null;
        TermoEstagio termoEstagio = null;
        List<TermoEstagio> termosAditivos = null;
        TermoEstagio termoAditivo = null;

        boolean isValid = true;
        String campo = "";
        String msg = "";

        /**
         * Validação do Id do Aluno, usando métodos da Classe ValidaUtils.
         * Instanciando o objeto e pegando o TermoEstagio válido (sem data de
         * rescisão)
         */
        String idAlunoMsg = "";
        campo = "Aluno";
        idAlunoMsg = ValidaUtils.validaObrigatorio(campo, idAluno);
        System.out.println(idAlunoMsg);
        if (idAlunoMsg.trim().isEmpty()) {
            idAlunoMsg = ValidaUtils.validaInteger(campo, idAluno);
            if (idAlunoMsg.trim().isEmpty()) {
                Integer idAlunoInt = Integer.parseInt(idAluno);
                aluno = AlunoServices.buscarAluno(new Aluno(idAlunoInt));
                if (aluno != null) {
                    List<TermoEstagio> termosEstagio = aluno.getTermoEstagios();
                    for (TermoEstagio termoEstagio2 : termosEstagio) {
                        if (termoEstagio2.getDataRescisaoTermoEstagio() == null && termoEstagio2.getTermoEstagio() == null ) {
                                termoEstagio = termoEstagio2;
                                System.out.println("Termo Estagio Pai: "+ termoEstagio2.getIdTermoEstagio());
                                break;
                        }
                    }

                } else {
                    idAlunoMsg = messages.getString("br.cefetrj.sisgee.incluir_termo_aditivo_servlet.msg_AlunoEscolhido");
                    request.setAttribute("idAlunoMsg", idAlunoMsg);
                }

            } else {
                idAlunoMsg = messages.getString(idAlunoMsg);
                request.setAttribute("idAlunoMsg", idAlunoMsg);
                isValid = false;
            }
        } else {
            idAlunoMsg = messages.getString(idAlunoMsg);
            request.setAttribute("idAlunoMsg", idAlunoMsg);
            isValid = false;
        }

        if (termoEstagio != null) {
            //TODO implementar lógica de encaminhamento para a tela de registro
            termosAditivos = termoEstagio.getTermosAditivos();
            if (termosAditivos != null && !termosAditivos.isEmpty()) {
                termoAditivo = termosAditivos.get(termosAditivos.size() - 1);
            }

            // se existe algum termo aditivo para o termo estagio
            if (termoAditivo != null) {
                termoEstagio = TermoAditivoServices.termoEstagioAtualizadoByTermoAditivo(termoAditivo);
            }

            List<ProfessorOrientador> professores = ProfessorOrientadorServices.listarProfessorOrientador();
            UF[] uf = UF.asList();

            request.setAttribute("termoEstagio", termoEstagio);
            request.setAttribute("termoAditivo", termoAditivo);
            request.setAttribute("professores", professores);
            request.setAttribute("uf", uf);

            request.setAttribute("updVigencia", termoEstagio.getDataFimTermoEstagio());
            request.setAttribute("updCargaHoraria", termoEstagio.getCargaHorariaTermoEstagio());
            request.setAttribute("updProfessor", termoEstagio.getProfessorOrientador());
            request.setAttribute("updValorBolsa", termoEstagio.getValorBolsa());
            request.setAttribute("updEndereco", termoEstagio.getEnderecoTermoEstagio());

        } else {
            msg = messages.getString("br.cefetrj.sisgee.form_termo_aditivo_servlet.msg_termo_estagio_invalido");
            request.setAttribute("msg", msg);
            isValid = false;
        }

        if (isValid) {
            String di = null;
            String df = null;
            try {
                di = ServletUtils.mudarFormatoData(termoEstagio.getDataInicioTermoEstagio());
                df = ServletUtils.mudarFormatoData(termoEstagio.getDataFimTermoEstagio());
            } catch (ParseException ex) {
                Logger.getLogger(TermoAditivoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            request.setAttribute("dataIni", di);
            request.setAttribute("dataFim", df);
            System.out.println("data inicio "+di);
            System.out.println("data fim "+df);
            request.setAttribute("termo", termoEstagio);
            request.getRequestDispatcher("/form_termo_estagio_adicionar_aditivo.jsp").forward(request, response);

        } else {
            request.getRequestDispatcher("/form_termo_aditivo.jsp").forward(request, response);
        }

    }
}
