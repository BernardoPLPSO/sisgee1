package br.cefetrj.sisgee.control;

import java.util.Date;
import java.util.List;

import br.cefetrj.sisgee.model.dao.GenericDAO;
import br.cefetrj.sisgee.model.dao.PersistenceManager;
import br.cefetrj.sisgee.model.dao.TermoEstagioDAO;
import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.PessoaFisica;
import br.cefetrj.sisgee.model.entity.PessoaJuridica;
import br.cefetrj.sisgee.model.entity.TermoEstagio;

/**
 * Serviços de TermoEstagio. Trata a lógica de negócios associada com a entidade
 * TermoEstagio.
 *
 * @author Paulo Cantuária, Augusto Jose
 * @since 1.0
 */
public class TermoEstagioServices {

    /**
     * Recupera todos os Termos de Estágio e retorna em uma lista.
     *
     * @return lista com todos os Termos de Estágio
     */
    public static List<TermoEstagio> listarTermoEstagio() {
        GenericDAO<TermoEstagio> termoEstagioDao = PersistenceManager.createGenericDAO(TermoEstagio.class);
        return termoEstagioDao.buscarTodos();
    }

    public static TermoEstagio buscarTermoEstagio(Integer idTermoEstagio) {
        GenericDAO<TermoEstagio> termoEstagioDao = PersistenceManager.createGenericDAO(TermoEstagio.class);
        return termoEstagioDao.buscar(idTermoEstagio);
    }

    /**
     *
     * Metodo para receber uma matriz de com conteudo do banco
     *
     * @author Marcos Eduardo
     * @param obrigatorio boolean do form para filtrar resultado
     * @param inicio date do form para filtrar resultado
     * @param termino date do form para filtrar resultado
     * @return author matriz com conteúdo obtido do banco ou null
     */
    public static List<Object[]> listarTermoEstagioFiltrado(Boolean obrigatorio, Date inicio, Date termino) {
        TermoEstagioDAO termoEstagioDAO = new TermoEstagioDAO();

        try {
            List<Object[]> author = null;

            if (obrigatorio == null) {
                author = termoEstagioDAO.buscarFiltrado(inicio, termino);
            } else {
                author = termoEstagioDAO.buscarFiltrado(obrigatorio, inicio, termino);
            }
            return author;
        } catch (Exception e) {
            return null;
        }
    }

    public static void incluirTermoEstagio(TermoEstagio termoEstagio) {

        /**
         * Lógica de negócio
         *
         * É Agente de Integração? Empresa já está ligada ao Agente de
         * Integração? NÃO - Atualizar Empresa.idAgenteIntegracao
         *
         * Convênio já existe para a Empresa selecionada? SIM - Encapsular em
         * termo estagio NÃO - Criar novo convênio e encapsular
         *
         * Registrar termo
         *
         */
        PersistenceManager.getTransaction().begin();
        try {
            PessoaJuridica jur = new PessoaJuridica();
            PessoaFisica fis = new PessoaFisica();
            System.out.println("Convenio PF "+termoEstagio.getConvenioPF());
            System.out.println("Convenio PJ "+termoEstagio.getConvenioPJ());
            if(termoEstagio.getConvenioPF()!= null){
                fis = PessoaFisicaServices.buscarConvenioByNumero((termoEstagio.getConvenioPF()).getNumeroConvenio());
            }
            else if(termoEstagio.getConvenioPJ() != null){
                jur = PessoaJuridicaServices.buscarConvenioByNumero(termoEstagio.getConvenioPJ().getNumeroConvenio());
            }
            if (fis.getNumeroConvenio() != null) {
                // SIM - Encapsular em termo estagio
                termoEstagio.setConvenioPF(fis);
            }else if(jur.getNumeroConvenio() != null){
                // SIM - Encapsular em termo estagio
                termoEstagio.setConvenioPJ(jur);
            }

            // encapsula aluno
            GenericDAO<Aluno> alunoDao = PersistenceManager.createGenericDAO(Aluno.class);
            Aluno al = alunoDao.buscar(termoEstagio.getAluno().getIdAluno());
            termoEstagio.setAluno(al);

            System.out.println("valor estagioObrigatorio: " + termoEstagio.getEEstagioObrigatorio());

            GenericDAO<TermoEstagio> termoEstagioDao = PersistenceManager.createGenericDAO(TermoEstagio.class);
            termoEstagioDao.incluir(termoEstagio);

            PersistenceManager.getTransaction().commit();
        } catch (Exception e) {
            //TODO remover saída do console
            System.out.println(e);
            e.printStackTrace();
            PersistenceManager.getTransaction().rollback();
        }

    }

    public static void alterarTermoEstagio(TermoEstagio termoEstagio) {

        GenericDAO<TermoEstagio> termoEstagioDao = PersistenceManager.createGenericDAO(TermoEstagio.class);

        try {
            PersistenceManager.getTransaction().begin();
            termoEstagioDao.alterar(termoEstagio);
            PersistenceManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.getTransaction().rollback();
        }
    }
}
