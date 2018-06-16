package br.cefetrj.sisgee.view.termoaditivo;

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

import br.cefetrj.sisgee.control.ProfessorOrientadorServices;
import br.cefetrj.sisgee.control.TermoAditivoServices;
import br.cefetrj.sisgee.control.TermoEstagioServices;
import br.cefetrj.sisgee.model.entity.ProfessorOrientador;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import br.cefetrj.sisgee.view.utils.UF;
import br.cefetrj.sisgee.view.utils.ValidaUtils;

/**
 * Servlet para trazer os dados do banco para a tela de cadastro de Termo
 * Aditivo.
 * 
 * @author Paulo Cantuária
 * @since 1.0
 *
 */
@WebServlet("/FormTermoAditivoServlet")
public class FormTermoAditivoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Método doGet: carrega as listas necessárias para seleção dos atributos de relacionamento e redireciona para a tela de Registro de Termo
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String aditivo = "sim";
		request = carregarListas(request);
		request.setAttribute("aditivo", aditivo);

		request.getRequestDispatcher("/form_termo_estagio.jsp").forward(request, response);

	}

	/**
	 * Método para validação e inclusão do Termo Aditivo
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Locale locale = ServletUtils.getLocale(request);
		ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);			
		
		
		String dataFimTermoAditivo = (String)request.getParameter("dataFimTermoEstagio");		
		String cargaHorariaTermoAditivo = (String)request.getParameter("cargaHorariaTermoEstagio");
		String valorBolsaTermoAditivo = (String)request.getParameter("valorBolsa");
		
		/**
		 * campos de endereço
		 */
		String enderecoTermoAditivo = (String)request.getParameter("enderecoTermoEstagio");
		String complementoEnderecoTermoAditivo = (String)request.getParameter("complementoEnderecoTermoEstagio");
		String bairroEnderecoTermoAditivo = (String)request.getParameter("bairroEnderecoTermoEstagio");
		String cepEnderecoTermoAditivo = (String)request.getParameter("cepEnderecoTermoEstagio");
		String cidadeEnderecoTermoAditivo = (String)request.getParameter("cidadeEnderecoTermoEstagio");
		String estadoEnderecoTermoAditivo = (String)request.getParameter("estadoEnderecoTermoEstagio");	
		
		/**
		 * Campos possíveis selecionados para atualização
		 */
		String updValorBolsa = request.getParameter("updValorBolsa");
		String updVigencia = request.getParameter("updVigencia");
		String updCargaHoraria = request.getParameter("updCargaHoraria");
		String updProfessor = request.getParameter("updProfessor");	
		String updEndereco = request.getParameter("updEndereco");
		
		
		String idProfessorOrientador = request.getParameter("idProfessorOrientador");		
		String idTermoEstagio = request.getParameter("idTermoEstagio");
		
		
		
		TermoEstagio termoEstagio = null;
		Integer idTermo = null;		
		
		boolean isValid = true;
		String msg = "";
		String campo = "";
		Integer tamanho = 0;
		
		Date dataFim = null;
		Float valor = null;
		Integer cargaHoraria = null;
		ProfessorOrientador professorOrientador = null;
		
		
		
		
		/**
		 * Validação do idTermoEstagio
		 */
		campo = "Termo de Estágio";
		msg = ValidaUtils.validaObrigatorio(campo, idTermoEstagio);
		if (msg.trim().isEmpty()) {
			msg = ValidaUtils.validaInteger(campo, idTermoEstagio);
			if (msg.trim().isEmpty()) {
				idTermo = Integer.parseInt(idTermoEstagio);
				termoEstagio = TermoEstagioServices.buscarTermoEstagio(idTermo);			
				
			}else {
				msg = messages.getString(msg);
				isValid = false;
			}
			
		}else {
			msg = messages.getString(msg);
			isValid = false;
		}
		
		
		/**
		 * Validações dos campos, com base nas opções selecionadas para alteração
		 */
		if(termoEstagio != null) {
			request.setAttribute("termoEstagio", termoEstagio);
			
			/**
			 * Validação de vigência
			 */
			if(updVigencia != null && !updVigencia.trim().isEmpty()) {
				
				campo = "Data de Término";
				Boolean hasDataFim = false;
				String dataFimMsg = "";
				dataFimMsg = ValidaUtils.validaObrigatorio(campo, dataFimTermoAditivo);
				if (dataFimMsg.trim().isEmpty()) {					
					dataFimMsg = ValidaUtils.validaDate(campo , dataFimTermoAditivo);
					if (dataFimMsg.trim().isEmpty()) {
						try {
                                                        String dataFimStr = ServletUtils.mudarFormatoData(dataFim);
							request.setAttribute("dataFim", dataFimStr);
							hasDataFim = true;
						} catch (Exception e) {							
							isValid = false;
						}
					} else {
						dataFimMsg = messages.getString(dataFimMsg);
						request.setAttribute("dataFimMsg", dataFimMsg);
						isValid = false;						
					} 
				}
				request.setAttribute("hasDataFim", hasDataFim);
				
				String periodoMsg = "";
				periodoMsg = ValidaUtils.validaDatas(termoEstagio.getDataInicioTermoEstagio(), dataFim);
				if(!periodoMsg.trim().isEmpty()) {
					periodoMsg = messages.getString(periodoMsg);
					request.setAttribute("periodoMsg", periodoMsg);
					isValid = false;					
				}
			}
			
			/**
			 * Validação de valor da Bolsa
			 */
			if (updValorBolsa != null && !updValorBolsa.trim().isEmpty() ) {
				String valorBolsaMsg = "";
				campo = "Valor";
				valorBolsaMsg = ValidaUtils.validaObrigatorio(campo, valorBolsaTermoAditivo);
				if (valorBolsaMsg.trim().isEmpty()) {
					valorBolsaMsg = ValidaUtils.validaFloat(campo, valorBolsaTermoAditivo);
					if (valorBolsaMsg.trim().isEmpty()) {
						valor = Float.parseFloat(valorBolsaTermoAditivo);
						request.setAttribute("valor", valor);
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
			}
			
			/**
			 * Validação de Carga Horária
			 */
			if (updCargaHoraria != null && !updCargaHoraria.trim().isEmpty()) {
				String cargaHorariaMsg = "";
				campo = "Horas por dia";
				tamanho = 6;		
				cargaHorariaMsg = ValidaUtils.validaObrigatorio(campo , cargaHorariaTermoAditivo);
				if (cargaHorariaMsg.trim().isEmpty()) {
					cargaHorariaMsg = ValidaUtils.validaInteger(campo, cargaHorariaTermoAditivo);
					if (cargaHorariaMsg.trim().isEmpty()) {
						cargaHoraria = Integer.parseInt(cargaHorariaTermoAditivo);
						if (cargaHorariaMsg.trim().isEmpty()) {
							cargaHorariaMsg = ValidaUtils.validaTamanho(campo, tamanho, cargaHoraria);
							if (cargaHorariaMsg.trim().isEmpty()) {
							request.setAttribute("cargaHoraria", cargaHoraria);
							}else {
								cargaHorariaMsg = messages.getString(cargaHorariaMsg);
								cargaHorariaMsg = ServletUtils.mensagemFormatada(cargaHorariaMsg, locale, tamanho);
								request.setAttribute("cargaHorariaMsg", cargaHorariaMsg);
							}
						} else {
							cargaHorariaMsg = messages.getString(cargaHorariaMsg);
							request.setAttribute("cargaHorariaMsg", cargaHorariaMsg);
							isValid = false;
							
							
						}
					} else {
						cargaHorariaMsg = messages.getString(cargaHorariaMsg);
						request.setAttribute("cargaHorariaMsg", cargaHorariaMsg);
						isValid = false;
						
					}
				} else {
					cargaHorariaMsg = messages.getString(cargaHorariaMsg);
					request.setAttribute("cargaHorariaMsg", cargaHorariaMsg);
					isValid = false;
					
				}
			}
			
			/**
			 * Validação de Professor
			 */
			if (updProfessor != null && !updProfessor.trim().isEmpty()) {
				String idProfessorMsg = "";
				campo = "Professor Orientador";
				Boolean hasProfessor = false;
				idProfessorMsg = ValidaUtils.validaObrigatorio(campo, idProfessorOrientador);
				if (idProfessorMsg.trim().isEmpty()) {
					idProfessorMsg = ValidaUtils.validaInteger(campo, idProfessorOrientador);
					if (idProfessorMsg.trim().isEmpty()) {
						Integer idProfessor = Integer.parseInt(idProfessorOrientador);
						List<ProfessorOrientador> listaProfessores = ProfessorOrientadorServices.listarProfessorOrientador();
						if (listaProfessores != null) {
							if (listaProfessores.contains(new ProfessorOrientador(idProfessor))) {
								professorOrientador = ProfessorOrientadorServices.buscarProfessorOrientador(new ProfessorOrientador(idProfessor));								
								request.setAttribute("idProfessor", idProfessor);
								hasProfessor = true;
							} else {
								idProfessorMsg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.professor_invalido");
								isValid = false;
								
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
				}
				request.setAttribute("hasProfessor", hasProfessor);
			}
			
			
			/**
			 * Validação de Endereço
			 */
			if (updEndereco != null && !updEndereco.trim().isEmpty()) {

				/**
				 * Validação do endereço do TermoEstagio usando métodos da Classe ValidaUtils.
				 * Campo obrigatório e tamanho máximo de 255 caracteres.
				 */
				String enderecoMsg = "";
				campo = "Endereço";
				tamanho = 255;
				enderecoMsg = ValidaUtils.validaObrigatorio(campo, enderecoTermoAditivo);
				if(enderecoMsg.trim().isEmpty()) {
					enderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, enderecoTermoAditivo);
					if(enderecoMsg.trim().isEmpty()) {
						request.setAttribute("enderecoTermoEstagio", enderecoTermoAditivo);
					}else {
						enderecoMsg = messages.getString(enderecoMsg);
						enderecoMsg = ServletUtils.mensagemFormatada(enderecoMsg, locale, tamanho);
						request.setAttribute("enderecoMsg", enderecoMsg);
						isValid = false;
						//TODO Fazer log
						System.out.println(enderecoMsg);
					}
				}else {
					enderecoMsg = messages.getString(enderecoMsg);
					request.setAttribute("enderecoMsg", enderecoMsg);
					isValid = false;
					//TODO Fazer log
					System.out.println(enderecoMsg);
				}
				
				
				/**
				 * Validação do complemento do endereço do TermoEstagio usando os métodos da Classe ValidaUtils.
				 * Campo não obrigatório e tamanho máximo de 150 caracteres.
				 */		
				String complementoEnderecoMsg = "";
				campo = "Complemento";
				tamanho = 150;
				if(complementoEnderecoMsg.trim().isEmpty()) {
					complementoEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, complementoEnderecoTermoAditivo);
					if(complementoEnderecoMsg.trim().isEmpty()) {
						request.setAttribute("complementoEnderecoTermoEstagio", complementoEnderecoTermoAditivo);
					}else {				
						complementoEnderecoMsg = messages.getString(complementoEnderecoMsg);
						complementoEnderecoMsg = ServletUtils.mensagemFormatada(complementoEnderecoMsg, locale, tamanho);
						request.setAttribute("complementoEnderecoMsg", complementoEnderecoMsg);
						isValid = false;
						
					}
				}		
				
				/**
				 * Validação do bairro do endereço do TermoEstagio usando métodos da Classe ValidaUtils.
				 * Campo obrigatório e tamanho máximo de 150 caracteres.
				 */
				String bairroEnderecoMsg = "";
				campo = "Bairro";
				tamanho = 150;
				bairroEnderecoMsg = ValidaUtils.validaObrigatorio(campo, bairroEnderecoTermoAditivo);
				if(bairroEnderecoMsg.trim().isEmpty()) {
					bairroEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, bairroEnderecoTermoAditivo);
					if(bairroEnderecoMsg.trim().isEmpty()) {
						request.setAttribute("bairroEnderecoTermoEstagio", bairroEnderecoTermoAditivo);
					}else {				
						bairroEnderecoMsg = messages.getString(bairroEnderecoMsg);
						bairroEnderecoMsg = ServletUtils.mensagemFormatada(bairroEnderecoMsg, locale, tamanho);
						request.setAttribute("bairroEnderecoMsg", bairroEnderecoMsg);
						isValid = false;
						//TODO Fazer log
						System.out.println(bairroEnderecoMsg);
					}
				}else {
					bairroEnderecoMsg = messages.getString(bairroEnderecoMsg);
					request.setAttribute("bairroEnderecoMsg", bairroEnderecoMsg);
					isValid = false;
					//TODO Fazer log
					System.out.println(bairroEnderecoMsg);
				}			
						
				/**
				 * Validação do cep do endereço do TermoEstagio usando métodos da Classe ValidaUtils.
				 * Campo obrigatório e tamanho máximo de 15 caracteres.
				 */
				String cepEnderecoMsg = "";	
				campo = "CEP";
				tamanho = 15;
				cepEnderecoMsg = ValidaUtils.validaObrigatorio(campo, cepEnderecoTermoAditivo);
				if(cepEnderecoMsg.trim().isEmpty()) {
					cepEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, cepEnderecoTermoAditivo);
					if(bairroEnderecoMsg.trim().isEmpty()) {
						request.setAttribute("cepEnderecoTermoEstagio", cepEnderecoTermoAditivo);
					}else {				
						cepEnderecoMsg = messages.getString(cepEnderecoMsg);	
						cepEnderecoMsg = ServletUtils.mensagemFormatada(bairroEnderecoMsg, locale, tamanho);
						request.setAttribute("cepEnderecoMsg", cepEnderecoMsg);
						isValid = false;
						//TODO Fazer log
						System.out.println(cepEnderecoMsg);
					}
				}else {
					cepEnderecoMsg = messages.getString(cepEnderecoMsg);
					request.setAttribute("cepEnderecoMsg", cepEnderecoMsg);
					isValid = false;
					//TODO Fazer log
					System.out.println(cepEnderecoMsg);
				}			
				
				
				/**
				 * Validação da Cidade do endereço do TermoEstagio, usando métodos da Classe ValidaUtils.
				 * Campo obrigatório e tamanho máximo de 150 caracteres.
				 */
				String cidadeEnderecoMsg = "";
				campo = "Cidade";
				tamanho = 150;
				cidadeEnderecoMsg = ValidaUtils.validaObrigatorio(campo, cidadeEnderecoTermoAditivo);
				if(cidadeEnderecoMsg.trim().isEmpty()) {
					cidadeEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, cidadeEnderecoTermoAditivo);
					if(cidadeEnderecoMsg.trim().isEmpty()) {
						request.setAttribute("cidadeEnderecoTermoEstagio", cidadeEnderecoTermoAditivo);
					}else {
						cidadeEnderecoMsg = messages.getString(cidadeEnderecoMsg);	
						cidadeEnderecoMsg = ServletUtils.mensagemFormatada(cidadeEnderecoMsg, locale, tamanho);
						request.setAttribute("cidadeEnderecoMsg", cidadeEnderecoMsg);
						isValid = false;
						//TODO Fazer log
						System.out.println(cidadeEnderecoMsg);
					}
				}else {
					cidadeEnderecoMsg = messages.getString(cidadeEnderecoMsg);	
					request.setAttribute("cidadeEnderecoMsg", cidadeEnderecoMsg);
					isValid = false;
					//TODO Fazer log
					System.out.println(cidadeEnderecoMsg);
				}					
				
				/**
				 * Validação do Estado do endereço do TermoEstagio, usando métodos da Classe ValidaUtils.
				 * Campo obrigatório e contido na Enum de UFs.
				 */
				String estadoEnderecoMsg = "";
				campo = "Estado";
				estadoEnderecoMsg = ValidaUtils.validaObrigatorio(campo, estadoEnderecoTermoAditivo);
				if(estadoEnderecoMsg.trim().isEmpty()) {
					estadoEnderecoMsg = ValidaUtils.validaUf(campo, estadoEnderecoTermoAditivo);
					if(estadoEnderecoMsg.trim().isEmpty()) {
						request.setAttribute("estadoEnderecoTermoEstagio", estadoEnderecoTermoAditivo);
					}else {
						estadoEnderecoMsg = messages.getString(estadoEnderecoMsg);
						request.setAttribute("estadoEnderecoMsg", estadoEnderecoMsg);
						isValid = false;
						//TODO Fazer log
						System.out.println(estadoEnderecoMsg);
					}
				}else {			
					estadoEnderecoMsg = messages.getString(estadoEnderecoMsg);
					request.setAttribute("estadoEnderecoMsg", estadoEnderecoMsg);
					isValid = false;
					//TODO Fazer log
					System.out.println(estadoEnderecoMsg);
				}
			}		
			
			
		}else {
			msg = messages.getString("br.cefetrj.sisgee.form_termo_aditivo_servlet.msg_termo_estagio_invalido");
			isValid = false;
			
		}		

		if (isValid) {
			TermoEstagio termoAditivo = null;
			List <TermoEstagio> termosAditivos = termoEstagio.getTermosAditivos();		
			
			if(termosAditivos.size() >= 1) {
				termoAditivo = termosAditivos.get(termosAditivos.size() -1);
				if(updVigencia != null && !updVigencia.trim().isEmpty()) {
					termoAditivo.setDataFimTermoEstagio(dataFim);
				}
				
				if(updCargaHoraria != null && !updCargaHoraria.trim().isEmpty()) {
					termoAditivo.setCargaHorariaTermoEstagio(cargaHoraria);
				}
				
				if(updProfessor != null && !updProfessor.trim().isEmpty()) {
					termoAditivo.setProfessorOrientador(professorOrientador);
				}
				
				if(updValorBolsa != null && !updValorBolsa.trim().isEmpty()) {
					termoAditivo.setValorBolsa(valor);
				}
				
				if(updEndereco != null && !updEndereco.trim().isEmpty()) {
					termoAditivo.setEnderecoTermoEstagio(enderecoTermoAditivo);
					termoAditivo.setComplementoEnderecoTermoEstagio(complementoEnderecoTermoAditivo);
					termoAditivo.setBairroEnderecoTermoEstagio(bairroEnderecoTermoAditivo);
					termoAditivo.setCidadeEnderecoTermoEstagio(cidadeEnderecoTermoAditivo);
					termoAditivo.setEstadoEnderecoTermoEstagio(estadoEnderecoTermoAditivo);
					termoAditivo.setCepEnderecoTermoEstagio(cepEnderecoTermoAditivo);
                                        termoAditivo.setNomeSupervisor(updEndereco);
                                        termoAditivo.setCargoSupervisor(campo);
				}
				
				//termoAditivo.setIdTermoAditivo(null);
				termoAditivo.setTermoEstagio(termoEstagio);
				
				TermoAditivoServices.incluirTermoAditivo(termoAditivo);
				String registroAditivoConcluido = messages.getString("br.cefetrj.sisgee.incluir_termo_aditivo_servlet.msg_registroAditivoConcluido");
				request.setAttribute("msg", registroAditivoConcluido);
				
				request.getRequestDispatcher("/index.jsp").forward(request, response);
								
			}else {
				termoAditivo = new TermoEstagio();
				if(updVigencia != null && !updVigencia.trim().isEmpty()) {
					termoAditivo.setDataFimTermoEstagio(dataFim);
				}else {
					termoAditivo.setDataFimTermoEstagio(termoEstagio.getDataFimTermoEstagio());
				}
				
				if(updCargaHoraria != null && !updCargaHoraria.trim().isEmpty()) {
					termoAditivo.setCargaHorariaTermoEstagio(cargaHoraria);
				}else {
					termoAditivo.setCargaHorariaTermoEstagio(termoEstagio.getCargaHorariaTermoEstagio());
				}
				
				if(updProfessor != null && !updProfessor.trim().isEmpty()) {
					termoAditivo.setProfessorOrientador(professorOrientador);
				}else {
					termoAditivo.setProfessorOrientador(termoEstagio.getProfessorOrientador());
				}
				
				if(updValorBolsa != null && !updValorBolsa.trim().isEmpty()) {
					termoAditivo.setValorBolsa(valor);
				}else {
					termoAditivo.setValorBolsa(termoEstagio.getValorBolsa());
				}
				
				if(updEndereco != null && !updEndereco.trim().isEmpty()) {
					termoAditivo.setEnderecoTermoEstagio(enderecoTermoAditivo);
					termoAditivo.setComplementoEnderecoTermoEstagio(complementoEnderecoTermoAditivo);
					termoAditivo.setBairroEnderecoTermoEstagio(bairroEnderecoTermoAditivo);
					termoAditivo.setCidadeEnderecoTermoEstagio(cidadeEnderecoTermoAditivo);
					termoAditivo.setEstadoEnderecoTermoEstagio(estadoEnderecoTermoAditivo);
					termoAditivo.setCepEnderecoTermoEstagio(cepEnderecoTermoAditivo);
				}else {
					termoAditivo.setEnderecoTermoEstagio(termoEstagio.getEnderecoTermoEstagio());
					termoAditivo.setComplementoEnderecoTermoEstagio(termoEstagio.getComplementoEnderecoTermoEstagio());
					termoAditivo.setBairroEnderecoTermoEstagio(termoEstagio.getBairroEnderecoTermoEstagio());
					termoAditivo.setCidadeEnderecoTermoEstagio(termoEstagio.getCidadeEnderecoTermoEstagio());
					termoAditivo.setEstadoEnderecoTermoEstagio(termoEstagio.getEstadoEnderecoTermoEstagio());
					termoAditivo.setCepEnderecoTermoEstagio(termoEstagio.getCepEnderecoTermoEstagio());
				}
				
				termoAditivo.setIdTermoEstagio(null);
				termoAditivo.setTermoEstagio(termoEstagio);
				
				TermoAditivoServices.incluirTermoAditivo(termoAditivo);
				String registroAditivoConcluido = messages.getString("br.cefetrj.sisgee.incluir_termo_aditivo_servlet.msg_registroAditivoConcluido");
				request.setAttribute("msg", registroAditivoConcluido);
				
				request.getRequestDispatcher("/index.jsp").forward(request, response);
								
				
			
			}		
			
			
		} else {
			request.setAttribute("updVigencia", updVigencia);
			request.setAttribute("updCargaHoraria", updCargaHoraria);
			request.setAttribute("updProfessor", updProfessor);
			request.setAttribute("updValorBolsa", updValorBolsa);
			request.setAttribute("updEndereco", updEndereco);
			
			msg += "Alguns campos precisam de atenção";
			String aditivo = "sim";
			request = carregarListas(request);
			request.setAttribute("msg", msg);
			request.setAttribute("aditivo", aditivo);
			
			request.getRequestDispatcher("/form_termo_estagio.jsp").forward(request, response);
		}

	}

	private static HttpServletRequest carregarListas(HttpServletRequest request) {

		List<ProfessorOrientador> professores = ProfessorOrientadorServices.listarProfessorOrientador();
		UF[] uf = UF.asList();

		request.setAttribute("professores", professores);
		request.setAttribute("uf", uf);

		return request;

	}

}
