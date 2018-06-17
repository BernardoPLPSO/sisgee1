package br.cefetrj.sisgee.model.dao;

import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.TermoEstagio;

public class AlunoDAO extends GenericDAO<Aluno> {
	
	public AlunoDAO() {
		super(Aluno.class, PersistenceManager.getEntityManager());
	}
	
	public Aluno buscarByMatricula(String matricula){
                    Aluno aluno = (Aluno) manager.createQuery(
		    "SELECT a FROM Aluno a WHERE a.matricula LIKE :matricula")
		    .setParameter("matricula", matricula)
		    .getSingleResult();
                    manager.refresh(aluno);
                    
                    return aluno;
	}

}
