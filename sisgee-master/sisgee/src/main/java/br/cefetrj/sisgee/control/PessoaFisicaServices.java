/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.control;

import br.cefetrj.sisgee.model.dao.GenericDAO;
import br.cefetrj.sisgee.model.dao.PersistenceManager;
import br.cefetrj.sisgee.model.dao.PessoaFisicaDAO;
import br.cefetrj.sisgee.model.entity.PessoaFisica;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Pirlimpimposo
 */
public class PessoaFisicaServices {

    public static List<PessoaFisica> listarConvenios() {
        GenericDAO<PessoaFisica> pessoaFisicaDAO = PersistenceManager.createGenericDAO(PessoaFisica.class);
        return pessoaFisicaDAO.buscarTodos();
    }

    public static PessoaFisica buscarConvenio(PessoaFisica pessoaFisica) {
        GenericDAO<PessoaFisica> pessoaFisicaDAO = PersistenceManager.createGenericDAO(PessoaFisica.class);
        return pessoaFisicaDAO.buscar(pessoaFisica.getIdConvenio());
    }

    public static void incluirConvenio(PessoaFisica pessoaFisica) {
        GenericDAO<PessoaFisica> pessoaFisicaDAO = PersistenceManager.createGenericDAO(PessoaFisica.class);
        PersistenceManager.getTransaction().begin();
        try {
            pessoaFisicaDAO.incluir(pessoaFisica);
            PersistenceManager.getTransaction().commit();
        } catch (Exception e) {
            PersistenceManager.getTransaction().rollback();
        }
    }

    public static PessoaFisica buscarConvenioByNumero(String numero) {
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        try {
            numero += "%";
            System.out.println("Numero PF " + numero);
            PessoaFisica c = pessoaFisicaDAO.buscarByNumeroConvenio(numero);
            System.out.println("PFServicesSuccess "+c);
            return c;
        } catch (Exception e) {
            System.out.println("PJServicesError "+e);
            return null;
        }
    }

    public static PessoaFisica buscarConvenioByNome(String nome) {
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        try {
            PessoaFisica c = pessoaFisicaDAO.buscarByNome(nome);
            return c;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static List<PessoaFisica> buscarListaNome(String nome){
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        String nome1 = "%";
        nome1 += nome;
        nome1 += "%"; 
        try{
            List<PessoaFisica> c = pessoaFisicaDAO.buscarListaNome(nome1);
            System.out.println(c);
            return c;
            
        
        }catch(Exception e){
            System.out.println("He");
            System.out.println(e);
            System.out.println("He");
            return null;
        }
    }

    public static PessoaFisica buscarConvenioByCPF(String cpf) {
        PessoaFisicaDAO pessoaFisicaDao = new PessoaFisicaDAO();
        try {
            PessoaFisica c = pessoaFisicaDao.buscarByCPF(cpf);
            return c;
        } catch (Exception e) {
            return null;
        }

    }
    
    
    public static String atualizarConvenioPF(String numeroConvenio, String email ,String telefone, Date dataAssinatura) {
        PessoaFisicaDAO pessoaFisicaDao = new PessoaFisicaDAO();
        String msg = "";
        try {
            pessoaFisicaDao.atualizaPessoaFisica(numeroConvenio, email ,telefone, dataAssinatura);
            return msg;
        } catch (Exception e) {
            msg = "ERROR";
            return msg;
        }
    }

}
