package br.cefetrj.sisgee.model.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
/**
 * Implementacao do padrao DAO para pesquisa especifica do Termo Estagio
 * 
 * @author Marcos Eduardo
 * @since 1.0
 *
 */

public class TermoEstagioDAO  {
	
    /**
     *
     * @param obrigatorio
     * @param inicio
     * @param termino
     * @return
     */
    public List<Object[]> buscarFiltrado(Boolean obrigatorio , Date inicio, Date termino){
		EntityManagerFactory factory =
				Persistence.createEntityManagerFactory("sisgeePU");
		EntityManager manager = factory.createEntityManager();
			
			Query query = manager
				.createNativeQuery("SELECT idtermoestagio, datarescisaotermoestagio, nomecurso, termoestagio_idtermoestagio " + 
						"FROM termoestagio, aluno " + 
                                                "WHERE termoestagio.aluno_idaluno = aluno.idaluno " +
						"AND datainiciotermoestagio > :inicio " + 
						"AND datainiciotermoestagio < :termino " + 
						"AND eestagioobrigatorio = :obrigatorio");
                        
                        //"SELECT nomecurso FROM termoestagio, aluno WHERE termoestagio.aluno_idaluno == aluno.idaluno"
		
		query.setParameter("obrigatorio", obrigatorio);
		query.setParameter("inicio", inicio);
		query.setParameter("termino", termino);
				
		@SuppressWarnings("unchecked")
		List<Object[]> authors = query.getResultList();
		 
		manager.close();
		factory.close();
		return  authors;
	}
	
    /**
     *
     * @param inicio
     * @param termino
     * @return
     */
    public List<Object[]> buscarFiltrado( Date inicio, Date termino){
		EntityManagerFactory factory =
				Persistence.createEntityManagerFactory("sisgeePU");
		EntityManager manager = factory.createEntityManager();
			
			Query query = manager
				.createNativeQuery("SELECT idtermoestagio, datarescisaotermoestagio, nomecurso, termoestagio_idtermoestagio " + 
						"FROM termoestagio, aluno " + 
                                                "WHERE termoestagio.aluno_idaluno = aluno.idaluno " +
						"AND termoestagio.datainiciotermoestagio >= :inicio " + 
						"AND :termino >= termoestagio.datafimtermoestagio ");
		
		query.setParameter("inicio", inicio);
		query.setParameter("termino", termino);
				
		@SuppressWarnings("unchecked")
		List<Object[]> authors = query.getResultList();
		 
		manager.close();
		factory.close();
		return  authors;
	}

}