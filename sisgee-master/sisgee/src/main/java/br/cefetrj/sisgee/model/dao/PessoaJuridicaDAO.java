/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.model.dao;

import br.cefetrj.sisgee.model.entity.PessoaJuridica;

/**
 *
 * @author Pirlimpimposo
 */
public class PessoaJuridicaDAO extends GenericDAO<PessoaJuridica>{
    public PessoaJuridicaDAO() {
        super(PessoaJuridica.class, PersistenceManager.getEntityManager());
    }

    public PessoaJuridica buscarByNumeroConvenio(String numero) {
        return (PessoaJuridica) manager.createQuery(
            "SELECT c FROM PessoaJuridica c WHERE c.numeroConvenio LIKE :numero")
            .setParameter("numero", numero)
            .getSingleResult();
    }    
    public PessoaJuridica buscarByNome(String razaosocial) {
        return (PessoaJuridica) manager.createQuery(
                "SELECT c FROM PessoaJuridica c WHERE c.razaoSocial LIKE :razaosocial")
                .setParameter("razaosocial", razaosocial)   
                .getSingleResult();
    }
    
    public PessoaJuridica buscarByCNPJ(String cnpj) {
        return (PessoaJuridica) manager.createQuery(
                "SELECT c FROM PessoaJuridica c WHERE c.cnpj LIKE :cnpj")
                .setParameter("cnpj", cnpj)
                .getSingleResult();
    }

}
