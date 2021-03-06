package br.cefetrj.sisgee.view.termoestagio;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.sisgee.control.AgenteIntegracaoServices;
import br.cefetrj.sisgee.control.AlunoServices;
import br.cefetrj.sisgee.control.EmpresaServices;
import br.cefetrj.sisgee.control.PessoaFisicaServices;
import br.cefetrj.sisgee.control.PessoaJuridicaServices;
import br.cefetrj.sisgee.control.ProfessorOrientadorServices;
import br.cefetrj.sisgee.model.entity.AgenteIntegracao;
import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.Empresa;
import br.cefetrj.sisgee.model.entity.PessoaFisica;
import br.cefetrj.sisgee.model.entity.PessoaJuridica;
import br.cefetrj.sisgee.model.entity.ProfessorOrientador;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import br.cefetrj.sisgee.view.utils.UF;
import br.cefetrj.sisgee.view.utils.ValidaUtils;

/**
 * Servlet para tratar os dados da tela de cadastro de Termo de Estágio.
 *
 * @author Paulo Cantuária
 * @since 1.0
 *
 */
@WebServlet("/FormTermoEstagioServlet")
public class FormTermoEstagioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Método doGet: carrega as listas necessárias para seleção dos atributos de
     * relacionamento e redireciona para a tela de Registro de Termo de Estágio
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request = carregarListas(request);

        request.getRequestDispatcher("/form_termo_estagio.jsp").forward(request, response);

    }

    /**
     * Método doPost: Valida os campos da tela de Registro de Termo de Estágio.
     * Retorna para a tela caso não passe em alguma validação ou encaminha para
     * o servlet de inclusão de Termo.
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);

        String dataInicioTermoEstagio = request.getParameter("dataInicioTermoEstagio");
        String dataFimTermoEstagio = request.getParameter("dataFimTermoEstagio");
        String cargaHorariaTermoEstagio = request.getParameter("cargaHorariaTermoEstagio");
        System.out.println("Carga Horaria Termo Estagio: "+cargaHorariaTermoEstagio);
        String valorBolsa = request.getParameter("valorBolsa");
        String enderecoTermoEstagio = request.getParameter("enderecoTermoEstagio");
        String complementoEnderecoTermoEstagio = request.getParameter("complementoEnderecoTermoEstagio");
        String bairroEnderecoTermoEstagio = request.getParameter("bairroEnderecoTermoEstagio");
        String cepEnderecoTermoEstagio = request.getParameter("cepEnderecoTermoEstagio");
        String cidadeEnderecoTermoEstagio = request.getParameter("cidadeEnderecoTermoEstagio");
        String estadoEnderecoTermoEstagio = request.getParameter("estadoEnderecoTermoEstagio");
        String eEstagioObrigatorio = request.getParameter("eEstagioObrigatorio");
        String idProfessorOrientador = request.getParameter("idProfessorOrientador");
        String idAluno = request.getParameter("idAluno");
        System.out.println(idAluno);
        String numeroConvenio = request.getParameter("numeroConvenio");
        String nomeConvenio = request.getParameter("nomeConvenio");
        String nomeSupervisor = request.getParameter("nomeSupervisor");
        String cargoSupervisor = request.getParameter("cargoSupervisor");
        System.out.println("Cargo Supervisor: " + cargoSupervisor);
        System.out.println(numeroConvenio);
        String isAgenteIntegracao = request.getParameter("isAgenteIntegracao");
        String agenciada = request.getParameter("Agenciada");

        boolean isValid = true;
        String msg = "";
        String campo = "";
        Integer tamanho = 0;

        /**
         * Validação da Data de início do estágio usando os métodos da Classe
         * ValidaUtils Campo obrigatório
         */
        Date dataInicio = null;
        String dataInicioMsg = "";
        campo = "Data de Início";

        dataInicioMsg = ValidaUtils.validaObrigatorio(campo, dataInicioTermoEstagio);
        if (dataInicioMsg.trim().isEmpty()) {
            dataInicioMsg = ValidaUtils.validaDate(campo, dataInicioTermoEstagio);
            if (dataInicioMsg.trim().isEmpty()) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    dataInicio = format.parse(dataInicioTermoEstagio);
                    request.setAttribute("dataInicio", dataInicio);
                } catch (Exception e) {
                    //TODO trocar saída de console por Log
                    System.out.println("Data em formato incorreto, mesmo após validação na classe ValidaUtils");
                    isValid = false;
                }
            } else {
                dataInicioMsg = messages.getString(dataInicioMsg);
                request.setAttribute("dataInicioMsg", dataInicioMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(dataInicioMsg);
            }
        } else {
            dataInicioMsg = messages.getString(dataInicioMsg);
            request.setAttribute("dataInicioMsg", dataInicioMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(dataInicioMsg);
        }

        /**
         * Validação da Data de fim do estágio usando os métodos da Classe
         * ValidaUtils
         */
        Date dataFim = null;
        String dataFimMsg = "";
        campo = "Data de Fim";

        dataFimMsg = ValidaUtils.validaObrigatorio(campo, dataFimTermoEstagio);
        if (dataFimMsg.trim().isEmpty()) {
            dataFimMsg = ValidaUtils.validaDate(campo, dataFimTermoEstagio);
            if (dataFimMsg.trim().isEmpty()) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    dataFim = format.parse(dataFimTermoEstagio);
                    request.setAttribute("dataFim", dataFim);
                } catch (Exception e) {
                    //TODO trocar saída de console por Log
                    System.out.println("Data em formato incorreto, mesmo após validação na classe ValidaUtils");
                    isValid = false;
                }
            } else {
                dataInicioMsg = messages.getString(dataFimMsg);
                request.setAttribute("dataFimMsg", dataFimMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(dataFimMsg);
            }
        } else {
            dataInicioMsg = messages.getString(dataFimMsg);
            request.setAttribute("dataFimMsg", dataFimMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(dataFimMsg);
        }

        /**
         * Validação do período (entre o início e fim do estágio) usando o
         * método validaDatas da Classe ValidaUtils
         */
        String periodoMsg = "";
        if (!(dataFimTermoEstagio == null || dataFimTermoEstagio.isEmpty())) {
            periodoMsg = ValidaUtils.validaDatas(dataInicio, dataFim);
            if (!periodoMsg.trim().isEmpty()) {
                periodoMsg = messages.getString(ValidaUtils.validaDatas(dataInicio, dataFim));
                request.setAttribute("periodoMsg", periodoMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(periodoMsg);
            }
        }

        /**
         * Validação da carga horária usando os métodos da Classe ValidaUtils
         * Campo obrigatório e valor menor que 255 (No banco), valor menor que
         * 24, por ser horas diárias.
         */
        String cargaHorariaMsg = "";
        campo = "Horas por dia";
        tamanho = 6;
        cargaHorariaMsg = ValidaUtils.validaObrigatorio(campo, cargaHorariaTermoEstagio);
        if (cargaHorariaMsg.trim().isEmpty()) {
            cargaHorariaMsg = ValidaUtils.validaInteger(campo, cargaHorariaTermoEstagio);
            if (cargaHorariaMsg.trim().isEmpty()) {
                Integer cargaHoraria = Integer.parseInt(cargaHorariaTermoEstagio);
                System.out.println("Carga Int: " + cargaHoraria);
                if (cargaHorariaMsg.trim().isEmpty()) {
                    cargaHorariaMsg = ValidaUtils.validaTamanho(campo, tamanho, cargaHoraria);
                    System.out.println("IF 1");
                    if (cargaHorariaMsg.trim().isEmpty()) {
                        request.setAttribute("cargaHoraria", cargaHoraria);
                        System.out.println("IF 2");
                    } else {
                        cargaHorariaMsg = messages.getString(cargaHorariaMsg);
                        cargaHorariaMsg = ServletUtils.mensagemFormatada(cargaHorariaMsg, locale, tamanho);
                        request.setAttribute("cargaHorariaMsg", cargaHorariaMsg);
                        System.out.println("Msg carga horaria: "+cargaHorariaMsg);
                    }
                } else {
                    cargaHorariaMsg = messages.getString(cargaHorariaMsg);
                    request.setAttribute("cargaHorariaMsg", cargaHorariaMsg);
                    isValid = false;
                    //TODO Fazer log
                    System.out.println(cargaHorariaMsg);

                }
            } else {
                cargaHorariaMsg = messages.getString(cargaHorariaMsg);
                request.setAttribute("cargaHorariaMsg", cargaHorariaMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(cargaHorariaMsg);
            }
        } else {
            cargaHorariaMsg = messages.getString(cargaHorariaMsg);
            request.setAttribute("cargaHorariaMsg", cargaHorariaMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(cargaHorariaMsg);
        }

        /**
         * Validação do valor da bolsa usando os métodos da Classe ValidaUtils
         * Campo obrigatório e valor float.
         */
        String valorBolsaMsg = "";
        campo = "Valor";
        valorBolsaMsg = ValidaUtils.validaObrigatorio(campo, valorBolsa);
        if (valorBolsaMsg.trim().isEmpty()) {
            valorBolsa = valorBolsa.replace(".", "");
            valorBolsa = valorBolsa.replace(",", ".");
            System.out.println("Valor Bolsa: " + valorBolsa);
            valorBolsaMsg = ValidaUtils.validaFloat(campo, valorBolsa);
            if (valorBolsaMsg.trim().isEmpty()) {
                Float valor = Float.parseFloat(valorBolsa);
                request.setAttribute("valor", valor);
                /*NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
				Number n;
				float valor = 0;
				try {
					n = nf.parse(valorBolsa);
					valor = n.floatValue();
				} catch (ParseException e) {
				}*/
            } else {
                valorBolsaMsg = messages.getString(valorBolsaMsg);
                request.setAttribute("valorBolsaMsg", valorBolsaMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(valorBolsaMsg);
            }
        } else {
            valorBolsaMsg = messages.getString(valorBolsaMsg);
            request.setAttribute("valorBolsaMsg", valorBolsaMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(valorBolsaMsg);
        }

        /**
         * Validação do endereço do TermoEstagio usando métodos da Classe
         * ValidaUtils. Campo obrigatório e tamanho máximo de 255 caracteres.
         */
        String enderecoMsg = "";
        campo = "Endereço";
        tamanho = 255;
        enderecoMsg = ValidaUtils.validaObrigatorio(campo, enderecoTermoEstagio);
        if (enderecoMsg.trim().isEmpty()) {
            enderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, enderecoTermoEstagio);
            if (enderecoMsg.trim().isEmpty()) {
                request.setAttribute("enderecoTermoEstagio", enderecoTermoEstagio);
            } else {
                enderecoMsg = messages.getString(enderecoMsg);
                enderecoMsg = ServletUtils.mensagemFormatada(enderecoMsg, locale, tamanho);
                request.setAttribute("enderecoMsg", enderecoMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(enderecoMsg);
            }
        } else {
            enderecoMsg = messages.getString(enderecoMsg);
            request.setAttribute("enderecoMsg", enderecoMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(enderecoMsg);
        }

        /**
         * Validação do complemento do endereço do TermoEstagio usando os
         * métodos da Classe ValidaUtils. Campo obrigatório e tamanho máximo de
         * 150 caracteres.
         */
        String complementoEnderecoMsg = "";
        campo = "Complemento";
        tamanho = 150;
        if (complementoEnderecoMsg.trim().isEmpty()) {
            complementoEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, complementoEnderecoTermoEstagio);
            if (complementoEnderecoMsg.trim().isEmpty()) {
                request.setAttribute("complementoEnderecoTermoEstagio", complementoEnderecoTermoEstagio);
                System.out.println("Tamanho complemento válido");
            } else {
                complementoEnderecoMsg = messages.getString(complementoEnderecoMsg);
                complementoEnderecoMsg = ServletUtils.mensagemFormatada(complementoEnderecoMsg, locale, tamanho);
                request.setAttribute("complementoEnderecoMsg", complementoEnderecoMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(complementoEnderecoMsg);
                System.out.println("Tamanho complemento inválido");
            }
        } else {
            complementoEnderecoMsg = messages.getString(complementoEnderecoMsg);
            request.setAttribute("complementoEnderecoMsg", complementoEnderecoMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(complementoEnderecoMsg);
        }

        /**
         * Validação do bairro do endereço do TermoEstagio usando métodos da
         * Classe ValidaUtils. Campo obrigatório e tamanho máximo de 150
         * caracteres.
         */
        String bairroEnderecoMsg = "";
        campo = "Bairro";
        tamanho = 150;
        bairroEnderecoMsg = ValidaUtils.validaObrigatorio(campo, bairroEnderecoTermoEstagio);
        if (bairroEnderecoMsg.trim().isEmpty()) {
            bairroEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, bairroEnderecoTermoEstagio);
            if (bairroEnderecoMsg.trim().isEmpty()) {
                request.setAttribute("bairroEnderecoTermoEstagio", bairroEnderecoTermoEstagio);
            } else {
                bairroEnderecoMsg = messages.getString(bairroEnderecoMsg);
                bairroEnderecoMsg = ServletUtils.mensagemFormatada(bairroEnderecoMsg, locale, tamanho);
                request.setAttribute("bairroEnderecoMsg", bairroEnderecoMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(bairroEnderecoMsg);
            }
        } else {
            bairroEnderecoMsg = messages.getString(bairroEnderecoMsg);
            request.setAttribute("bairroEnderecoMsg", bairroEnderecoMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(bairroEnderecoMsg);
        }

        /**
         * Validação do cep do endereço do TermoEstagio usando métodos da Classe
         * ValidaUtils. Campo obrigatório e tamanho máximo de 15 caracteres.
         */
        String cepEnderecoMsg = "";
        campo = "CEP";
        tamanho = 15;
        cepEnderecoMsg = ValidaUtils.validaObrigatorio(campo, cepEnderecoTermoEstagio);
        if (cepEnderecoMsg.trim().isEmpty()) {
            cepEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, cepEnderecoTermoEstagio);
            if (bairroEnderecoMsg.trim().isEmpty()) {
                request.setAttribute("cepEnderecoTermoEstagio", cepEnderecoTermoEstagio);
            } else {
                cepEnderecoMsg = messages.getString(cepEnderecoMsg);
                cepEnderecoMsg = ServletUtils.mensagemFormatada(bairroEnderecoMsg, locale, tamanho);
                request.setAttribute("cepEnderecoMsg", cepEnderecoMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(cepEnderecoMsg);
            }
        } else {
            cepEnderecoMsg = messages.getString(cepEnderecoMsg);
            request.setAttribute("cepEnderecoMsg", cepEnderecoMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(cepEnderecoMsg);
        }

        /**
         * Validação da Cidade do endereço do TermoEstagio, usando métodos da
         * Classe ValidaUtils. Campo obrigatório e tamanho máximo de 150
         * caracteres.
         */
        String cidadeEnderecoMsg = "";
        campo = "Cidade";
        tamanho = 150;
        cidadeEnderecoMsg = ValidaUtils.validaObrigatorio(campo, cidadeEnderecoTermoEstagio);
        if (cidadeEnderecoMsg.trim().isEmpty()) {
            cidadeEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, cidadeEnderecoTermoEstagio);
            if (cidadeEnderecoMsg.trim().isEmpty()) {
                request.setAttribute("cidadeEnderecoTermoEstagio", cidadeEnderecoTermoEstagio);
            } else {
                cidadeEnderecoMsg = messages.getString(cidadeEnderecoMsg);
                cidadeEnderecoMsg = ServletUtils.mensagemFormatada(cidadeEnderecoMsg, locale, tamanho);
                request.setAttribute("cidadeEnderecoMsg", cidadeEnderecoMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(cidadeEnderecoMsg);
            }
        } else {
            cidadeEnderecoMsg = messages.getString(cidadeEnderecoMsg);
            request.setAttribute("cidadeEnderecoMsg", cidadeEnderecoMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(cidadeEnderecoMsg);
        }

        /**
         * Validação do Estado do endereço do TermoEstagio, usando métodos da
         * Classe ValidaUtils. Campo obrigatório e contido na Enum de UFs.
         */
        String estadoEnderecoMsg = "";
        campo = "Estado";
        estadoEnderecoMsg = ValidaUtils.validaObrigatorio(campo, estadoEnderecoTermoEstagio);
        if (estadoEnderecoMsg.trim().isEmpty()) {
            estadoEnderecoMsg = ValidaUtils.validaUf(campo, estadoEnderecoTermoEstagio);
            if (estadoEnderecoMsg.trim().isEmpty()) {
                request.setAttribute("estadoEnderecoTermoEstagio", estadoEnderecoTermoEstagio);
            } else {
                estadoEnderecoMsg = messages.getString(estadoEnderecoMsg);
                request.setAttribute("estadoEnderecoMsg", estadoEnderecoMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(estadoEnderecoMsg);
            }
        } else {
            estadoEnderecoMsg = messages.getString(estadoEnderecoMsg);
            request.setAttribute("estadoEnderecoMsg", estadoEnderecoMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(estadoEnderecoMsg);
        }

        /**
         * Validação do campo Estágio Obrigatório, usando métodos da Classe
         * ValidaUtils. Deve ser campo booleano
         */
        String eEstagioObrigatorioMsg = "";
        campo = "Estágio obrigatório";
        eEstagioObrigatorioMsg = ValidaUtils.validaObrigatorio(campo, eEstagioObrigatorio);
        if (eEstagioObrigatorioMsg.trim().isEmpty()) {
            Boolean obrigatorio;
            if (eEstagioObrigatorio.equals("sim")) {
                obrigatorio = true;
                request.setAttribute("obrigatorio", obrigatorio);
            } else if (eEstagioObrigatorio.equals("nao")) {
                obrigatorio = false;
                request.setAttribute("obrigatorio", obrigatorio);
            } else {
                eEstagioObrigatorioMsg = "Valor inválido";
                request.setAttribute("eEstagioObrigatorioMsg", eEstagioObrigatorioMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(eEstagioObrigatorioMsg);
            }

        } else {
            eEstagioObrigatorioMsg = messages.getString(eEstagioObrigatorioMsg);
            request.setAttribute("eEstagioObrigatorioMsg", eEstagioObrigatorioMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(eEstagioObrigatorioMsg);
        }

        /**
         * Validação do Id do Professor Orientador, usando métodos da Classe
         * ValidaUtils. Consultando a lista de Professores para validar
         */
        String idProfessorMsg = "";
        campo = "Professor Orientador";
        Boolean hasProfessor = false;
        idProfessorMsg = ValidaUtils.validaObrigatorio("idProfessor", idProfessorOrientador);
        if (!(idProfessorOrientador.trim().isEmpty() || idProfessorOrientador == null)) {
            idProfessorMsg = ValidaUtils.validaInteger(campo, idProfessorOrientador);
            if (idProfessorMsg.trim().isEmpty()) {
                Integer idProfessor = Integer.parseInt(idProfessorOrientador);
                List<ProfessorOrientador> listaProfessores = ProfessorOrientadorServices.listarProfessorOrientador();
                if (listaProfessores != null) {
                    if (listaProfessores.contains(new ProfessorOrientador(idProfessor))) {
                        request.setAttribute("idProfessor", idProfessor);
                        hasProfessor = true;
                    } else {
                        idProfessorMsg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.professor_invalido");
                        isValid = false;
                        //TODO Fazer log
                        System.out.println(idProfessorMsg);
                    }
                } else {
                    idProfessorMsg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.lista_professores_vazia");
                    isValid = false;
                    //TODO Fazer log
                    System.out.println(idProfessorMsg);
                }
            } else {
                idProfessorMsg = messages.getString(idProfessorMsg);
                request.setAttribute("idProfessorMsg", idProfessorMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(idProfessorMsg);
            }
        } else {
            idProfessorMsg = messages.getString(idProfessorMsg);
            request.setAttribute("idProfessorMsg", idProfessorMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(idProfessorMsg);
        }
        request.setAttribute("hasProfessor", hasProfessor);

        /**
         * Validação do Id do Aluno, usando métodos da Classe ValidaUtils. Valor
         * obrigatório e Integer
         */
        Boolean alunoExiste = false;
        String idAlunoMsg = "";
        campo = "Aluno";
        idAlunoMsg = ValidaUtils.validaObrigatorio(campo, idAluno);
        if (idAlunoMsg.trim().isEmpty()) {
            idAlunoMsg = ValidaUtils.validaInteger(campo, idAluno);
            if (idAlunoMsg.trim().isEmpty()) {
                Integer idAlunoInt = Integer.parseInt(idAluno);
                List<Aluno> listaAlunos = AlunoServices.listarAlunos();
                if (listaAlunos != null) {
                    if (listaAlunos.contains(new Aluno(idAlunoInt))) {
                        request.setAttribute("idAluno", idAlunoInt);
                        alunoExiste = true;
                    } else {
                        idAlunoMsg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.aluno_invalido");
                        isValid = false;
                        //TODO Fazer log
                        System.out.println(idAlunoMsg);
                    }
                } else {
                    idAlunoMsg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.lista_alunos_vazia");
                    isValid = false;
                    //TODO Fazer log
                    System.out.println(idAlunoMsg);
                }

            } else {
                idAlunoMsg = messages.getString(idAlunoMsg);
                request.setAttribute("idAlunoMsg", idAlunoMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(idAlunoMsg);
            }
        } else {
            idAlunoMsg = messages.getString(idAlunoMsg);
            request.setAttribute("idAlunoMsg", idAlunoMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(idAlunoMsg);
        }

        /**
         * Validação do Nº de Convênio Campo obrigatório, tamanho máximo 10
         *
         */
        String numeroConvenioMsg = "";
        campo = "Número do convênio";
        tamanho = 6;
        if (numeroConvenioMsg.trim().isEmpty()) {
            numeroConvenioMsg = ValidaUtils.validaTamanho(campo, tamanho, numeroConvenio);
            if (numeroConvenioMsg.trim().isEmpty()) {
                request.setAttribute("numeroConvenio", numeroConvenio);
            } else {
                numeroConvenioMsg = messages.getString(numeroConvenioMsg);
                numeroConvenioMsg = ServletUtils.mensagemFormatada(numeroConvenioMsg, locale, tamanho);
                request.setAttribute("numeroConvenioMsg", numeroConvenioMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(numeroConvenioMsg);
            }
        }

        /**
         * Validação nome do convênio para busca
         */
        String nomeConvenioMsg = "";
        campo = "Nome do convênio";
        tamanho = 100;
        if (nomeConvenioMsg.trim().isEmpty()) {
            nomeConvenioMsg = ValidaUtils.validaTamanho(campo, tamanho, nomeConvenioMsg);
            if (nomeConvenioMsg.trim().isEmpty()) {
                request.setAttribute("nomeConvenio", nomeConvenio);
            } else {
                nomeConvenioMsg = messages.getString(nomeConvenioMsg);
                nomeConvenioMsg = ServletUtils.mensagemFormatada(nomeConvenioMsg, locale, tamanho);
                request.setAttribute("nomeConvenioMsg", nomeConvenioMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println("NomeConvenio: " + nomeConvenioMsg);
            }
        }

        if ((numeroConvenio == null || numeroConvenio.equals("")) && (nomeConvenio == null || nomeConvenio.equals(""))) {
            numeroConvenioMsg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.busca_convenio_vazia");
            nomeConvenioMsg = numeroConvenioMsg;
            request.setAttribute("numeroConvenioMsg", numeroConvenioMsg);
            request.setAttribute("nomeConvenioMsg", nomeConvenioMsg);
            System.out.println("NumeroConvenioMSG: " + numeroConvenioMsg);
            isValid = false;
        }

        /**
         * Validação do nome de supervisor
         */
        String nomeSupervisorMsg = "";
        campo = "Nome Supervisor";
        tamanho = 150;
        System.out.println(nomeSupervisor);
        nomeSupervisorMsg = ValidaUtils.validaTamanho(campo, tamanho, nomeSupervisor);
        if (!nomeSupervisor.isEmpty() && !cargoSupervisor.isEmpty() || nomeSupervisor.isEmpty() && cargoSupervisor.isEmpty() || !cargoSupervisor.isEmpty()) {
            if (nomeSupervisorMsg.trim().isEmpty() && nomeSupervisorMsg.trim().equals("")) {
                request.setAttribute("nomeSupervisor", nomeSupervisor);
            } else {
                nomeSupervisorMsg = messages.getString(nomeSupervisorMsg);
                nomeSupervisorMsg = ServletUtils.mensagemFormatada(nomeSupervisorMsg, locale, tamanho);
                request.setAttribute("nomeSupervisorMsg", nomeSupervisorMsg);
                isValid = false;
                //TODO Fazer log
                System.out.println(nomeSupervisorMsg);
            }
        } else {
            nomeSupervisorMsg = "br.cefetrj.sisgee.valida_utils.msgNomeSemCargo";
            nomeSupervisorMsg = messages.getString(nomeSupervisorMsg);
            nomeSupervisorMsg = ServletUtils.mensagemFormatada(nomeSupervisorMsg, locale, tamanho);
            request.setAttribute("nomeSupervisorMsg", nomeSupervisorMsg);
        }

        /**
         * Validação do cargo do supervisor
         */
        String cargoSupervisorMsg = "";
        campo = "Cargo Supervisor";
        tamanho = 150;
        cargoSupervisorMsg = ValidaUtils.validaTamanho(campo, tamanho, cargoSupervisor);
        if (!nomeSupervisor.isEmpty() || cargoSupervisor.isEmpty() && cargoSupervisorMsg.trim().equals("")) {
            cargoSupervisorMsg = ValidaUtils.validaObrigatorio(campo, cargoSupervisor);
            System.out.println("cargoSupervisorMsg: " + cargoSupervisorMsg);
            if (cargoSupervisorMsg.trim().isEmpty()) {
                if (cargoSupervisorMsg.trim().isEmpty()) {
                    System.out.println("cargo valido");
                    request.setAttribute("cargoSupervisor", cargoSupervisor);
                } else {
                    cargoSupervisorMsg = messages.getString(cargoSupervisorMsg);
                    cargoSupervisorMsg = ServletUtils.mensagemFormatada(cargoSupervisorMsg, locale, tamanho);
                    request.setAttribute("cargoSupervisorMsg", cargoSupervisorMsg);
                    isValid = false;
                    //TODO Fazer log
                    System.out.println(cargoSupervisorMsg);
                }
            }
        } else {
            cargoSupervisorMsg = "br.cefetrj.sisgee.valida_utils.msgCargoSemNome";
            cargoSupervisorMsg = messages.getString(cargoSupervisorMsg);
            cargoSupervisorMsg = ServletUtils.mensagemFormatada(cargoSupervisorMsg, locale, tamanho);
            request.setAttribute("cargoSupervisorMsg", cargoSupervisorMsg);
        }

        /**
         * Validação do nome de agenciada
         */
        String agenciadaMsg = "";
        campo = "Agenciada";
        tamanho = 150;
        System.out.println(agenciada);
        nomeSupervisorMsg = ValidaUtils.validaTamanho(campo, tamanho, agenciada);
        if (agenciadaMsg.trim().isEmpty()) {
            request.setAttribute("Agenciada", agenciada);
        } else {
            agenciadaMsg = messages.getString(agenciadaMsg);
            agenciadaMsg = ServletUtils.mensagemFormatada(agenciadaMsg, locale, tamanho);
            request.setAttribute("agenciadaMsg", agenciadaMsg);
            isValid = false;
            //TODO Fazer log
            System.out.println(agenciadaMsg);
        }

        /**
         * *************************************************************************
         * Se aluno já possui estágio aberto, não pode cadastrar outro
         * *************************************************************************
         */
        if (alunoExiste) {
            Integer idAlunoInt = Integer.parseInt(idAluno);
            Aluno a = new Aluno(idAlunoInt);
            Aluno aluno = AlunoServices.buscarAluno(a);
            Boolean hasTermoAberto = false;

            List<TermoEstagio> termosEstagio = aluno.getTermoEstagios();
            for (TermoEstagio t : termosEstagio) {
                if (t.getDataRescisaoTermoEstagio() == null) {
                    hasTermoAberto = true;
                    break;
                }
            }

            if (hasTermoAberto) {
                String msg2 = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.msg_aluno_has_termo_aberto");
                request.setAttribute("msg2", msg2);
                isValid = false;
            }
        }

        /**
         * Teste da variável booleana após validação. Redirecionamento para a
         * inclusão ou devolver para o formulário com as mensagens.
         */
        if (isValid) {
            request.getRequestDispatcher("/IncluirTermoEstagioServlet").forward(request, response);
        } else {
            msg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.msg_atencao");
            request.setAttribute("msg", msg);
            request = carregarListas(request);

            request.getRequestDispatcher("/form_termo_estagio.jsp").forward(request, response);
        }
    }

    private static HttpServletRequest carregarListas(HttpServletRequest request) {

        List<PessoaFisica> conveniosPF = PessoaFisicaServices.listarConvenios();
        List<PessoaJuridica> conveniosPJ = PessoaJuridicaServices.listarConvenios();
        List<Aluno> alunos = AlunoServices.listarAlunos();
        List<ProfessorOrientador> professores = ProfessorOrientadorServices.listarProfessorOrientador();
        UF[] uf = UF.asList();

        request.setAttribute("conveniosPF", conveniosPF);
        request.setAttribute("conveniosPJ", conveniosPJ);
        request.setAttribute("alunos", alunos);
        request.setAttribute("professores", professores);
        request.setAttribute("uf", uf);

        return request;
    }
}
