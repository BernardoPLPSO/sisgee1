package br.cefetrj.sisgee.model.dao;

import br.cefetrj.sisgee.model.entity.AgenteIntegracao;

/**
 *
 * @author diego
 */
public class AgenteIntegracaoDAO extends GenericDAO<AgenteIntegracao> {

    /**
     *
     */
    public AgenteIntegracaoDAO() {
		super(AgenteIntegracao.class, PersistenceManager.getEntityManager());
	}
	
    /**
     *
     * @param cnpj
     * @return
     */
    public AgenteIntegracao buscarByCnpj(String cnpj){
		return (AgenteIntegracao) manager.createQuery(
		   "SELECT e FROM AgenteIntegracao e WHERE e.cnpjAgenteIntegracao LIKE :cnpj")
		   .setParameter("cnpj", cnpj)
		   .getSingleResult();
	}
	
    /**
     *
     * @param nome
     * @return
     */
    public AgenteIntegracao buscarByNome(String nome){
		return (AgenteIntegracao) manager.createQuery(
		   "SELECT e FROM AgenteIntegracao e WHERE e.nomeAgenteIntegracao LIKE :nome")
		   .setParameter("nome", nome)
		   .getSingleResult();
	}

}
