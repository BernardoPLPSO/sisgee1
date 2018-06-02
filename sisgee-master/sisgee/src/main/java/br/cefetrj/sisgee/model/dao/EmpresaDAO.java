package br.cefetrj.sisgee.model.dao;

import br.cefetrj.sisgee.model.entity.Empresa;

public class EmpresaDAO extends GenericDAO<Empresa> {

	public EmpresaDAO() {
		super(Empresa.class, PersistenceManager.getEntityManager());
	}
	
	public Empresa buscarByCnpj(String cnpj){
		return (Empresa) manager.createQuery(
		    "SELECT e FROM Empresa e WHERE e.cnpjEmpresa LIKE :cnpj")
		    .setParameter("cnpj", cnpj)
		    .getSingleResult();
	}
	
	public Empresa buscarByNome(String nome){
		return (Empresa) manager.createQuery(
		    "SELECT e FROM Empresa e WHERE e.nomeEmpresa LIKE :nome")
		    .setParameter("nome", nome)
		    .getSingleResult();
	}

}
