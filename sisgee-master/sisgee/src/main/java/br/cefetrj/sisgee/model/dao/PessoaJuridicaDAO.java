/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.model.dao;

import br.cefetrj.sisgee.control.PessoaJuridicaServices;
import br.cefetrj.sisgee.model.entity.PessoaFisica;
import br.cefetrj.sisgee.model.entity.PessoaJuridica;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Pirlimpimposo
 */
public class PessoaJuridicaDAO extends GenericDAO<PessoaJuridica>{

    /**
     *
     */
    public PessoaJuridicaDAO() {
        super(PessoaJuridica.class, PersistenceManager.getEntityManager());
    }

    /**
     *
     * @param numero
     * @return
     */
    public PessoaJuridica buscarByNumeroConvenio(String numero) {
        Query q = manager.createQuery("SELECT c FROM PessoaJuridica c WHERE c.numeroConvenio LIKE :numero");
        q.setFirstResult(0);
        q.setMaxResults(1);
        System.out.println(numero);
        PessoaJuridica j = (PessoaJuridica) q.setParameter("numero", numero).getSingleResult();
        System.out.println(j);
        return  j;
    }

    /**
     *
     * @param razaosocial
     * @return
     */
    public PessoaJuridica buscarByNome(String razaosocial) {
        return (PessoaJuridica) manager.createQuery(
                "SELECT c FROM PessoaJuridica c WHERE c.razaoSocial LIKE :razaosocial")
                .setParameter("razaosocial", razaosocial)   
                .getSingleResult();
    }
    
    /**
     *
     * @param nome
     * @return
     */
    public List<PessoaJuridica> buscarListaNome(String nome){
        return (List<PessoaJuridica>) manager.createQuery("SELECT c FROM PessoaJuridica c WHERE c.razaoSocial LIKE :nome").setParameter("nome", nome).getResultList();
    }
    
    /**
     *
     * @param cnpj
     * @return
     */
    public PessoaJuridica buscarByCNPJ(String cnpj) {
        return (PessoaJuridica) manager.createQuery(
                "SELECT c FROM PessoaJuridica c WHERE c.cnpj LIKE :cnpj")
                .setParameter("cnpj", cnpj)
                .getSingleResult();
    }
    
    /**
     *
     * @param numeroConvenio
     * @param email
     * @param telefone
     * @param dataAssinatura
     * @param pessoaContato
     */
    public void mergePessoaJuridica(String numeroConvenio, String email, String telefone, Date dataAssinatura, String pessoaContato){
        PessoaJuridica pj = PessoaJuridicaServices.buscarConvenioByNumero(numeroConvenio);
        Integer id = pj.getIdConvenio();
        pj = manager.find(PessoaJuridica.class, id);
        manager.getTransaction().begin();
        pj.setNumeroConvenio(retornaConvenioNovo(numeroConvenio));
        pj.setEmail(email);
        pj.setTelefone(telefone);
        pj.setDataAssinatura(dataAssinatura);
        pj.setPessoaCOntato(pessoaContato);
        manager.getTransaction().commit();
    }
    
    /**
     *
     * @param numeroConvenio
     * @return
     */
    public static String retornaConvenioNovo(String numeroConvenio){
        
        int anoInt = Calendar.getInstance().get(Calendar.YEAR);
        String ano = Integer.toString(anoInt);
        String convenio[] = numeroConvenio.split("/");
        String convenioAtual = convenio[0]+"/"+ano;
        System.out.println(anoInt);
        System.out.println(convenio[0]);
        System.out.println(convenioAtual);
        
        return convenioAtual;
    }
        
    }


