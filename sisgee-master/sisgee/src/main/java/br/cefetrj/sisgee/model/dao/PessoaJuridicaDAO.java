/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.model.dao;

import br.cefetrj.sisgee.model.entity.PessoaFisica;
import br.cefetrj.sisgee.model.entity.PessoaJuridica;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Pirlimpimposo
 */
public class PessoaJuridicaDAO extends GenericDAO<PessoaJuridica>{
    public PessoaJuridicaDAO() {
        super(PessoaJuridica.class, PersistenceManager.getEntityManager());
    }

    public PessoaJuridica buscarByNumeroConvenio(String numero) {
        Query q = manager.createQuery("SELECT c FROM PessoaJuridica c WHERE c.numeroConvenio LIKE :numero");
        q.setFirstResult(0);
        q.setMaxResults(1);
        PessoaJuridica j = (PessoaJuridica) q.setParameter("numero", numero).getSingleResult();
        System.out.println(j);
        return  j;
    }
    public PessoaJuridica buscarByNome(String razaosocial) {
        return (PessoaJuridica) manager.createQuery(
                "SELECT c FROM PessoaJuridica c WHERE c.razaoSocial LIKE :razaosocial")
                .setParameter("razaosocial", razaosocial)   
                .getSingleResult();
    }
    
    public List<PessoaJuridica> buscarListaNome(String nome){
        return (List<PessoaJuridica>) manager.createQuery("SELECT c FROM PessoaJuridica c WHERE c.nome LIKE :nome");
    }
    
    public PessoaJuridica buscarByCNPJ(String cnpj) {
        return (PessoaJuridica) manager.createQuery(
                "SELECT c FROM PessoaJuridica c WHERE c.cnpj LIKE :cnpj")
                .setParameter("cnpj", cnpj)
                .getSingleResult();
    }

}
