package br.cefetrj.sisgee.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Paulo Cantuaria
 * @since 1.0
 *
 */
@Entity
public class Aluno {

    @Id
    @GeneratedValue
    private Integer idAluno;

    @Column(length = 100, nullable = false, unique = true)
    private String matricula;

    @Column(length = 100, nullable = false, unique = false)
    private String nomeAluno;

    @Column(length = 100, nullable = false, unique = false)
    private String nomeCurso;

    @Column(length = 100, nullable = false, unique = false)
    private String nomeCampus;

    @OneToMany(mappedBy = "aluno")
    private List<TermoEstagio> termoEstagios;

    /**
     *
     */
    public Aluno() {
    }

    /**
     *
     * @param idAluno
     */
    public Aluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    /**
     *
     * @return
     */
    public Integer getIdAluno() {
        return idAluno;
    }

    /**
     *
     * @param idAluno
     */
    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    /**
     *
     * @return
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     *
     * @param matricula
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     *
     * @return
     */
    public String getNomeAluno() {
        return nomeAluno;
    }

    /**
     *
     * @param nomeAluno
     */
    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    /**
     *
     * @return
     */
    public String getNomeCurso() {
        return nomeCurso;
    }

    /**
     *
     * @param nomeCurso
     */
    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    /**
     *
     * @return
     */
    public String getNomeCampus() {
        return nomeCampus;
    }

    /**
     *
     * @param nomeCampus
     */
    public void setNomeCampus(String nomeCampus) {
        this.nomeCampus = nomeCampus;
    }

    /**
     *
     * @return
     */
    public List<TermoEstagio> getTermoEstagios() {
        return termoEstagios;
    }

    /**
     *
     * @param termoEstagios
     */
    public void setTermoEstagios(List<TermoEstagio> termoEstagios) {
        this.termoEstagios = termoEstagios;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idAluno == null) ? 0 : idAluno.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Aluno other = (Aluno) obj;
        if (idAluno == null) {
            if (other.idAluno != null) {
                return false;
            }
        } else if (!idAluno.equals(other.idAluno)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return matricula;
    }

}
