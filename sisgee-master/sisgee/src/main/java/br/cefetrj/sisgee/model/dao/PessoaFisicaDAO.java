/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.model.dao;

import br.cefetrj.sisgee.control.PessoaFisicaServices;
import br.cefetrj.sisgee.model.entity.PessoaFisica;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Pirlimpimposo
 */
public class PessoaFisicaDAO extends GenericDAO<PessoaFisica> {

    /**
     *
     */
    public PessoaFisicaDAO() {
        super(PessoaFisica.class, PersistenceManager.getEntityManager());
    }

    /**
     *
     * @param numero
     * @return
     */
    public PessoaFisica buscarByNumeroConvenio(String numero) {
        Query q = manager.createQuery("SELECT c FROM PessoaFisica c WHERE c.numeroConvenio LIKE :numero");
        q.setFirstResult(0);
        q.setMaxResults(1);
        PessoaFisica f = (PessoaFisica) q.setParameter("numero", numero).getSingleResult();
        return  f;
    }
    
    /**
     *
     * @param nome
     * @return
     */
    public PessoaFisica buscarByNome(String nome) {
        return (PessoaFisica) manager.createQuery(
                "SELECT c FROM PessoaFisica c WHERE c.nome LIKE :nome")
                .setParameter("nome", nome)
                .getSingleResult();
    }
    
    /**
     *
     * @param nome
     * @return
     */
    public List<PessoaFisica> buscarListaNome(String nome){
        
        List<PessoaFisica> Resultado = (List<PessoaFisica>) manager.createQuery("SELECT c FROM PessoaFisica c WHERE c.nome LIKE :nome").setParameter("nome", nome).getResultList();
        System.out.println(Resultado.get(0).getNome());
        return Resultado;
    }
    
    /**
     *
     * @param cpf
     * @return
     */
    public PessoaFisica buscarByCPF(String cpf) {
        return (PessoaFisica) manager.createQuery(
                "SELECT c FROM PessoaFisica c WHERE c.cpf LIKE :cpf")
                .setParameter("cpf", cpf)
                .getSingleResult();
    }
    
    /**
     *
     * @param numeroConvenio
     * @param email
     * @param telefone
     * @param dataAssinatura
     */
    public void mergePessoaFisica(String numeroConvenio, String email, String telefone, Date dataAssinatura){
        PessoaFisica pf = PessoaFisicaServices.buscarConvenioByNumero(numeroConvenio);
        Integer id = pf.getIdConvenio();
        pf = manager.find(PessoaFisica.class, id);
        manager.getTransaction().begin();
        pf.setNumeroConvenio(retornaConvenioNovo(numeroConvenio));
        pf.setEmail(email);
        pf.setTelefone(telefone);
        pf.setDataAssinatura(dataAssinatura);
        manager.getTransaction().commit();
    }
    
    /**
     *
     * @param numeroConvenio
     * @return
     */
    public String retornaConvenioNovo(String numeroConvenio){
        
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
    
    
    
    
    

