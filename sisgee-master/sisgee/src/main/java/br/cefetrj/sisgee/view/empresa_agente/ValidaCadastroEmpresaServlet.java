package br.cefetrj.sisgee.view.empresa_agente;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.sisgee.control.AgenteIntegracaoServices;
import br.cefetrj.sisgee.control.EmpresaServices;
import br.cefetrj.sisgee.control.PessoaFisicaServices;
import br.cefetrj.sisgee.control.PessoaJuridicaServices;
import br.cefetrj.sisgee.model.entity.AgenteIntegracao;
import br.cefetrj.sisgee.model.entity.Empresa;
import br.cefetrj.sisgee.model.entity.PessoaFisica;
import br.cefetrj.sisgee.model.entity.PessoaJuridica;
import br.cefetrj.sisgee.view.filters.TodasRequisicoesFilter;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import br.cefetrj.sisgee.view.utils.ValidaUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * Servlet para validar os dados da tela de cadastro de empresa.
 *
 * @author NatÃ¡lia Nunes
 * @since 1.0
 *
 */
@WebServlet("/ValidaCadastroEmpresaServlet")
public class ValidaCadastroEmpresaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean isValid = true;
        Integer tamanho = 0;

        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);

        String tipoPessoa = request.getParameter("tipoPessoa");

        /**
         * Validação do campo Tipo Pessoa, usando métodos da Classe ValidaUtils.
         */
        String tipoPessoaMsg = "";
        tipoPessoaMsg = ValidaUtils.validaObrigatorio("Agente Integração", tipoPessoa);
        if (tipoPessoaMsg.trim().isEmpty()) {
            System.out.println("Tipo pessoa válido");
            request.setAttribute("tipoPessoa", tipoPessoa);

        } else {
            tipoPessoaMsg = messages.getString(tipoPessoaMsg);
            request.setAttribute("tipoPessoaMsg", tipoPessoaMsg);
            System.out.println("Tipo pessoa inválido");
            isValid = false;
        }
        System.out.println(tipoPessoa);
        Logger lg = Logger.getLogger(TodasRequisicoesFilter.class);
        lg.error(tipoPessoa);

        if (tipoPessoa.equals("cnpj")) {
            String cnpjConvenio = request.getParameter("cnpjConvenio");
            String razaoSocial = request.getParameter("razaoSocial");
            String agenteIntegracao = request.getParameter("agenteIntegracao");
            System.out.println("PRINTZAO" + agenteIntegracao);
            String pessoaContato = request.getParameter("pessoaContato");
            /**
             * Validação do campo Agente Integração, usando métodos da Classe
             * ValidaUtils. Deve ser campo booleano
             */
            String agenteIntegracaoMsg = "";
            agenteIntegracaoMsg = ValidaUtils.validaObrigatorio("Agente Integração", agenteIntegracao);
            System.out.println("Razao: "+ razaoSocial);
            System.out.println("Agente Integracao: "+ agenteIntegracao);
            if (agenteIntegracaoMsg.trim().isEmpty()) {
                agenteIntegracaoMsg = ValidaUtils.validaBoolean("Agente Integração", agenteIntegracao);
                if (agenteIntegracaoMsg.trim().isEmpty()) {
                    Boolean obrigatorio = Boolean.parseBoolean(agenteIntegracao);
                    System.out.println("Agente Integração válido");
                    request.setAttribute("agenteIntegracao", agenteIntegracao);
                } else {
                    System.out.println("Agente Integração inválido por boolean incorreto");
                    agenteIntegracaoMsg = messages.getString(agenteIntegracaoMsg);
                    request.setAttribute("agenteIntegracaoMsg", agenteIntegracaoMsg);
                    isValid = false;
                }
            } else {
                System.out.println("Agente Integração inválido por vazio");
                agenteIntegracaoMsg = messages.getString(agenteIntegracaoMsg);
                request.setAttribute("agenteIntegracaoMsg", agenteIntegracaoMsg);
                isValid = false;
            }
            System.out.println(agenteIntegracaoMsg);

            /**
             * Validação do CNPJ da empresa usando os mÃ©todos da Classe
             * ValidaUtils Campo obrigatório; Tamanho de 14 caracteres; CNPJ
             * repetido.
             */
            String cnpjConvenioMsg = "";
            tamanho = 14;
            cnpjConvenioMsg = ValidaUtils.validaObrigatorio("CNPJ", cnpjConvenio);
            if (cnpjConvenioMsg.trim().isEmpty()) {
                //remove caracteres especiais antes de vazer a validação numérica do CNPJ
                cnpjConvenio = cnpjConvenio.replaceAll("[.|/|-]", "");
                cnpjConvenioMsg = ValidaUtils.validaInteger("CNPJ", cnpjConvenio);
                if (cnpjConvenioMsg.trim().isEmpty()) {
                    cnpjConvenioMsg = ValidaUtils.validaTamanhoExato("CNPJ", tamanho, cnpjConvenio);
                    if (cnpjConvenioMsg.trim().isEmpty()) {
                        PessoaJuridica e = PessoaJuridicaServices.buscarConvenioByCNPJ(cnpjConvenio);
                        if (e == null) {

                            request.setAttribute("cnpjConvenio", cnpjConvenio);

                        } else {
                            cnpjConvenioMsg = messages.getString("br.cefetrj.sisgee.valida_cadastro_empresa_servlet.msg_empresa_duplicada");
                            request.setAttribute("cnpjConvenioMsg", cnpjConvenioMsg);
                            isValid = false;
                        }
                    } else {
                        cnpjConvenioMsg = messages.getString(cnpjConvenioMsg);
                        cnpjConvenioMsg = ServletUtils.mensagemFormatada(cnpjConvenioMsg, locale, tamanho);
                        request.setAttribute("cnpjConvenioMsg", cnpjConvenioMsg);
                        isValid = false;
                    }
                } else {
                    cnpjConvenioMsg = messages.getString(cnpjConvenioMsg);
                    request.setAttribute("cnpjConvenioMsg", cnpjConvenioMsg);
                    isValid = false;
                }
            } else {
                cnpjConvenioMsg = messages.getString(cnpjConvenioMsg);
                request.setAttribute("cnpjConvenioMsg", cnpjConvenioMsg);
                isValid = false;
            }
            System.out.println(cnpjConvenioMsg);

            /**
             * ValidaÃ§Ã£o da RazÃ£o Social do Cadastro Empresa usando mÃ©todos
             * da Classe ValidaUtils. Campo obrigatÃ³rio; Tamanho mÃ¡ximo de 100
             * caracteres; RazÃ£o Social jÃ¡ existente.
             */
            String razaoSocialMsg = "";
            razaoSocialMsg = ValidaUtils.validaObrigatorio("Razão Social", razaoSocial);
            if (razaoSocialMsg.trim().isEmpty()) {
                razaoSocialMsg = ValidaUtils.validaTamanho("Razão Social", 100, razaoSocial);
                if (razaoSocialMsg.trim().isEmpty()) {
                    PessoaJuridica e = PessoaJuridicaServices.buscarConvenioByNome(razaoSocial);
                    if (e == null) {

                        request.setAttribute("razaoSocial", razaoSocial);
                    } else {
                        razaoSocialMsg = messages.getString("br.cefetrj.sisgee.valida_cadastro_empresa_servlet.msg_empresa_duplicada");
                        request.setAttribute("razaoSocialMsg", razaoSocialMsg);
                        isValid = false;
                    }
                } else {
                    razaoSocialMsg = ServletUtils.mensagemFormatada(razaoSocialMsg, locale, tamanho);
                    request.setAttribute("razaoSocialMsg", razaoSocialMsg);
                    isValid = false;
                }
            } else {
                razaoSocialMsg = messages.getString(razaoSocialMsg);
                request.setAttribute("razaoSocialMsg", razaoSocialMsg);
                isValid = false;
            }
            System.out.println(razaoSocialMsg);

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
            System.out.println(pessoaContatoMsg);

        } else {

            String cpfConvenio = request.getParameter("cpfConvenio");
            String nomePessoa = request.getParameter("nomePessoa");

            /**
             * Validação do CPF da Pessoa Física usando os mÃ©todos da Classe
             * ValidaUtils Campo obrigatório; Tamanho de 11 caracteres; CPF
             * repetido.
             */
            String cpfConvenioMsg = "";
            tamanho = 11;
            cpfConvenioMsg = ValidaUtils.validaObrigatorio("CPF", cpfConvenio);
            if (cpfConvenioMsg.trim().isEmpty()) {
                //remove caracteres especiais antes de vazer a validação numérica do CNPJ
                cpfConvenio = cpfConvenio.replaceAll("[.|-]", "");
                cpfConvenioMsg = ValidaUtils.validaInteger("CPF", cpfConvenio);
                if (cpfConvenioMsg.trim().isEmpty()) {
                    cpfConvenioMsg = ValidaUtils.validaTamanhoExato("CPF", tamanho, cpfConvenio);
                    if (cpfConvenioMsg.trim().isEmpty()) {
                        PessoaFisica e = PessoaFisicaServices.buscarConvenioByCPF(cpfConvenio);
                        if (e == null) {

                            request.setAttribute("cpfConvenio", cpfConvenio);

                        } else {
                            cpfConvenioMsg = messages.getString("br.cefetrj.sisgee.valida_cadastro_empresa_servlet.msg_empresa_duplicada");
                            request.setAttribute("cpfConvenioMsg", cpfConvenioMsg);
                            isValid = false;
                        }
                    } else {
                        cpfConvenioMsg = messages.getString(cpfConvenioMsg);
                        cpfConvenioMsg = ServletUtils.mensagemFormatada(cpfConvenioMsg, locale, tamanho);
                        request.setAttribute("cpfConvenioMsg", cpfConvenioMsg);
                        isValid = false;
                    }
                } else {
                    cpfConvenioMsg = messages.getString(cpfConvenioMsg);
                    request.setAttribute("cpfConvenioMsg", cpfConvenioMsg);
                    isValid = false;
                }
            } else {
                cpfConvenioMsg = messages.getString(cpfConvenioMsg);
                request.setAttribute("cpfConvenioMsg", cpfConvenioMsg);
                isValid = false;
            }

            /**
             * Validação do Nome de Pessoa Física usando métodos da Classe
             * ValidaUtils. Campo obrigatório; Tamanho máximo de 100 caracteres;
             * Nome ja existente.
             */
            String nomePessoaMsg = "";
            nomePessoaMsg = ValidaUtils.validaObrigatorio("Nome Pessoa", nomePessoa);
            if (nomePessoaMsg.trim().isEmpty()) {
                nomePessoaMsg = ValidaUtils.validaTamanho("Nome Pessoa", 100, nomePessoa);
                if (nomePessoaMsg.trim().isEmpty()) {

                    request.setAttribute("nomePessoa", nomePessoa);

                } else {
                    nomePessoaMsg = ServletUtils.mensagemFormatada(nomePessoaMsg, locale, tamanho);
                    request.setAttribute("nomePessoaMsg", nomePessoaMsg);
                    isValid = false;
                }
            } else {
                nomePessoaMsg = messages.getString(nomePessoaMsg);
                request.setAttribute("nomePessoaMsg", nomePessoaMsg);
                System.out.println("NomePessoaMsg: "+nomePessoaMsg);
                isValid = false;
            }

        }

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
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                    dataAssinatura = (Date)formatter.parse(dataAss);
                    String dataAssinaturaStr = ServletUtils.mudarFormatoData(dataAssinatura);
                    request.setAttribute("dataAssinatura", dataAssinaturaStr);
                    System.out.println("Data assinatura mudada: "+dataAssinaturaStr);
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

        if (email == null || email.trim().isEmpty() || email.equals("")) {
            System.out.println("BOLINHA");
        } else {
            System.out.println("Email dentro do if: " + email);
            EmailMsg = ValidaUtils.validaTamanho("Email", 50, email);
            if (EmailMsg.trim().isEmpty()) {
                EmailMsg = ValidaUtils.validaEmail("Email", email);
                if (EmailMsg.trim().isEmpty()) {

                    request.setAttribute("email", email);

                } else {
                    EmailMsg = messages.getString(EmailMsg);
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
        System.out.println("Telefone "+telefone);
        String telefoneMsg = "";
        telefoneMsg = ValidaUtils.validaTamanhoTelefone("telefone", telefone);
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

        /**
         * Teste das variÃ¡veis booleanas apÃ³s validaÃ§Ã£o. Redirecionamento
         * para a inclusÃ£o ou devolver para o formulÃ¡rio com as mensagens.
         */
        if (isValid) {
            request.getRequestDispatcher("/IncluirCadastroEmpresaServlet").forward(request, response);
        } else {

            String msg = messages.getString("br.cefetrj.sisgee.valida_cadastro_empresa_servlet.msg_atencao");
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/form_convenio.jsp").forward(request, response);

        }
    }
}

/*

                                                             

 */
