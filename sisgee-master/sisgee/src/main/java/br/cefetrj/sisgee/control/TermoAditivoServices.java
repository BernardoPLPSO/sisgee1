package br.cefetrj.sisgee.control;

import java.util.Date;
import java.util.List;

import br.cefetrj.sisgee.model.dao.GenericDAO;
import br.cefetrj.sisgee.model.dao.PersistenceManager;
import br.cefetrj.sisgee.model.dao.TermoAditivoDAO;
import br.cefetrj.sisgee.model.entity.TermoEstagio;

/**
 * Serviços de TermoAditivo. 
 * Trata a lógica de negócios
 * associada com a entidade TermoAditivo.
 * 
 * @author Paulo Cantuária
 * @since 1.0
 */
public class TermoAditivoServices {
	
	/**
	 * Recupera todos os Termos Aditivos e retorna em uma lista.
	 * 
	 * @return lista com todos os Termos Aditivos
	 */
	public static List<TermoEstagio> listarTermoAditivo(){
		GenericDAO<TermoEstagio> termoAditivoDao = PersistenceManager.createGenericDAO(TermoEstagio.class);
		return termoAditivoDao.buscarTodos();
	}	
	
	/**
	 * Método para persistir um termo aditivo no banco
	 * @param termoAditivo Termo aditivo a ser persistido
	 */
	public static void incluirTermoAditivo(TermoEstagio termoAditivo){
		GenericDAO<TermoEstagio> termoAditivoDao = PersistenceManager.createGenericDAO(TermoEstagio.class);		
		PersistenceManager.getTransaction().begin();
		try{
			termoAditivoDao.incluir(termoAditivo);
			PersistenceManager.getTransaction().commit();
		}catch(Exception e){
			//TODO remover saída do console
			System.out.println(e);
			PersistenceManager.getTransaction().rollback();
		}
	}
	
	/**
	 * Método para buscar um termo aditivo por id
	 * @param idTermoAditivo Id do termo aditivo
	 * @return termo aditivo referente ao id passado
	 */
	public static TermoEstagio buscarTermoAditivo(Integer idTermoAditivo) {
		GenericDAO<TermoEstagio> termoAditivoDao = PersistenceManager.createGenericDAO(TermoEstagio.class);	
		return termoAditivoDao.buscar(idTermoAditivo);
	}
	
	/**
	 * 
	 * Metodo para receber uma matriz de com conteudo do banco
	 * @author Marcos Eduardo
	 * @param  obrigatorio boolean do form para filtrar resultado
	 * @param  inicio date do form para filtrar resultado
	 * @param  termino date do form para filtrar resultado
	 * @return   author matriz com conteúdo obtido do banco ou null
	 */
	public static List<Object[]> listarTermoAditivoFiltrado(Boolean obrigatorio, Date inicio, Date termino){
		TermoAditivoDAO termoAditivoDAO = new TermoAditivoDAO();
		
		try{
			List<Object[]> author = null;
			
			if(obrigatorio == null) {
				author = termoAditivoDAO.buscarFiltrado( inicio, termino);
			}else {
				author = termoAditivoDAO.buscarFiltrado(obrigatorio , inicio, termino);
			}
			return author;
		}catch(Exception e){
			return null;
		}
	}	
}
