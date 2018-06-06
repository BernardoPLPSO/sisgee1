/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.control;

import br.cefetrj.sisgee.model.dao.GenericDAO;
import br.cefetrj.sisgee.model.dao.PersistenceManager;
import br.cefetrj.sisgee.model.dao.PessoaJuridicaDAO;
import br.cefetrj.sisgee.model.entity.PessoaJuridica;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Pirlimpimposo
 */
public class PessoaJuridicaServices {
    public static List<PessoaJuridica> listarConvenios() {
        GenericDAO<PessoaJuridica> pessoaJuridicaDAO = PersistenceManager.createGenericDAO(PessoaJuridica.class);
        return pessoaJuridicaDAO.buscarTodos();
    }
    
    public static PessoaJuridica buscarConvenio(PessoaJuridica pessoaJuridica) {
        GenericDAO<PessoaJuridica> pessoaJuridicaDAO = PersistenceManager.createGenericDAO(PessoaJuridica.class);
        return pessoaJuridicaDAO.buscar(pessoaJuridica.getIdConvenio());
    }

    public static void incluirConvenio(PessoaJuridica pessoaJuridica) {
        GenericDAO<PessoaJuridica> pessoaJuridicaDAO = PersistenceManager.createGenericDAO(PessoaJuridica.class);
        PersistenceManager.getTransaction().begin();
        try {
            pessoaJuridicaDAO.incluir(pessoaJuridica);
            PersistenceManager.getTransaction().commit();
        } catch (Exception e) {
            PersistenceManager.getTransaction().rollback();
        }
    }

    public static PessoaJuridica buscarConvenioByNumero(String numero) {
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
        try {
            numero += "%";
            System.out.println("Numero PJ "+numero);
            PessoaJuridica c = pessoaJuridicaDAO.buscarByNumeroConvenio(numero);
            System.out.println("PJServicesSuccess "+c);
            return c;
        } catch (Exception e) {
            System.out.println("PJServicesError "+e);
            return null;
        }
    }
    
    public static PessoaJuridica buscarConvenioByNome(String nome) {
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
        try {
            PessoaJuridica c = pessoaJuridicaDAO.buscarByNome(nome);
            return c;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static List<PessoaJuridica> buscarListaNome(String nome){
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
        String nome1 = "%";
        nome1 += nome;
        nome1 += "%"; 
        try{
            List<PessoaJuridica> c = pessoaJuridicaDAO.buscarListaNome(nome1);
            return c;
        
        }catch(Exception e){
            return null;
        }
    }        
    public static PessoaJuridica buscarConvenioByCNPJ(String cnpj) {
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
        try {
            PessoaJuridica c = pessoaJuridicaDAO.buscarByCNPJ(cnpj);
            return c;
        } catch (Exception e) {
            return null;
        }
    }
    
     public static String atualizarConvenioPJ(String numeroConvenio, String email, String pessoaContato ,String telefone, Date dataAssinatura) {
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
        String msg = "";
         System.out.println(numeroConvenio + email + pessoaContato);
         System.out.println("Entrou Atualiza");
        try {
            System.out.println(telefone);
            pessoaJuridicaDAO.mergePessoaJuridica(numeroConvenio, email, telefone, dataAssinatura, pessoaContato);
            return msg;
        } catch (Exception e) {
            System.out.println("Exception: "+e);
            msg = "ERROR";
            return msg;
        }
    }
    
    
}
