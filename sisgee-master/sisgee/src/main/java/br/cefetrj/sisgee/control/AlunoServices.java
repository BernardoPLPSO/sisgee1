package br.cefetrj.sisgee.control;

import java.util.List;

import br.cefetrj.sisgee.model.dao.AlunoDAO;
import br.cefetrj.sisgee.model.dao.GenericDAO;
import br.cefetrj.sisgee.model.dao.PersistenceManager;
import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import java.util.ArrayList;

/**
 * Serviços de alunos. Trata a lógica de negócios
 * associada com a entidade Aluno.
 * 
 * @author Paulo Cantuária
 * @since 1.0
 */
public class AlunoServices {
	
	/**
	 * Recupera todos os alunos e retorna em uma lista.
	 * 
	 * @return lista com todos os alunos
	 */
	public static List<Aluno> listarAlunos(){
		GenericDAO<Aluno> alunoDao = PersistenceManager.createGenericDAO(Aluno.class);
		return alunoDao.buscarTodos();
	}
	
	public static Aluno buscarAluno(Aluno aluno) {
		GenericDAO<Aluno> alunoDao = PersistenceManager.createGenericDAO(Aluno.class);
		return alunoDao.buscar(aluno.getIdAluno());
	}
	
	public static void incluirAluno(Aluno aluno){
		GenericDAO<Aluno> alunoDao = PersistenceManager.createGenericDAO(Aluno.class);		
		PersistenceManager.getTransaction().begin();
		try{
			alunoDao.incluir(aluno);
			PersistenceManager.getTransaction().commit();
		}catch(Exception e){
			PersistenceManager.getTransaction().rollback();
		}
	}
	
	public static Aluno buscarAlunoByMatricula(String matricula) {
		AlunoDAO alunoDao = new AlunoDAO();
		try{
			Aluno a = alunoDao.buscarByMatricula(matricula);
			return a;
		}catch(Exception e){
			return null;
		}
	}
        
        public static TermoEstagio buscarTermoEstagioAtivo(Aluno a){
            List<TermoEstagio> t = null;
            t = a.getTermoEstagios();
            TermoEstagio aux = null;
            
            for (TermoEstagio te : t){
            System.out.println(te.getDataRescisaoTermoEstagio());
            System.out.println(te.getTermosAditivos());   
            if(te.getDataRescisaoTermoEstagio() == null && te.getTermoEstagio() == null){
                    aux = te;
                }
            }
            return aux;
        }
	

}
