package br.cefetrj.sisgee.model.entity;

import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.annotation.JsonbTransient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author padu
 * @since 1.0
 */
@Entity
public class TermoEstagio {

    @Id
    @GeneratedValue
    private Integer idTermoEstagio;

    @Column(nullable = false)
    private Date dataInicioTermoEstagio;

    private Date dataFimTermoEstagio;

    private Date dataRescisaoTermoEstagio;

    @Column(nullable = false)
    private Integer cargaHorariaTermoEstagio;

    @Column(nullable = false)
    private Float valorBolsa;

    @Column(length = 255, nullable = false)
    private String enderecoTermoEstagio;

    @Column(length = 150, nullable = true)
    private String complementoEnderecoTermoEstagio;

    @Column(length = 150, nullable = false)
    private String bairroEnderecoTermoEstagio;

    @Column(length = 15, nullable = false)
    private String cepEnderecoTermoEstagio;

    @Column(length = 150, nullable = false)
    private String cidadeEnderecoTermoEstagio;

    @Column(length = 2, nullable = false)
    private String estadoEnderecoTermoEstagio;

    @Column(length = 100, nullable = true)
    private String nomeSupervisor;

    @Column(length = 100, nullable = true)
    private String cargoSupervisor;

    @Column(length = 100, nullable = true)
    private String agenciada;

    @Column(nullable = false)
    private Boolean eEstagioObrigatorio;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Aluno aluno;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private PessoaFisica convenioPF;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private PessoaJuridica convenioPJ;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.EAGER)
    private ProfessorOrientador professorOrientador;

    @JsonbTransient
    @OneToMany(mappedBy = "termoEstagio")
    private List<TermoEstagio> termosAditivos;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.EAGER)
    private TermoEstagio termoEstagio;

    /**
     *
     * @param dataFimTermoEstagio
     * @param cargaHorariaTermoEstagio
     * @param valorBolsa
     * @param enderecoTermoEstagio
     * @param professorOrientador
     */
    public TermoEstagio(Date dataFimTermoEstagio, Integer cargaHorariaTermoEstagio, Float valorBolsa, String enderecoTermoEstagio, ProfessorOrientador professorOrientador) {
        this.dataFimTermoEstagio = dataFimTermoEstagio;
        this.cargaHorariaTermoEstagio = cargaHorariaTermoEstagio;
        this.valorBolsa = valorBolsa;
        this.enderecoTermoEstagio = enderecoTermoEstagio;
        this.professorOrientador = professorOrientador;
    }

    /**
     *
     * @return
     */
    public TermoEstagio getTermoEstagio() {
        return termoEstagio;
    }

    /**
     *
     * @param termoEstagio
     */
    public void setTermoEstagio(TermoEstagio termoEstagio) {
        this.termoEstagio = termoEstagio;
    }

    /**
     *
     */
    public TermoEstagio() {
    }

    /**
     *
     * @param dataInicioTermoEstagio
     * @param dataFimTermoEstagio
     * @param cargaHorariaTermoEstagio
     * @param valorBolsa
     * @param enderecoTermoEstagio
     * @param complementoEnderecoTermoEstagio
     * @param bairroEnderecoTermoEstagio
     * @param cepEnderecoTermoEstagio
     * @param cidadeEnderecoTermoEstagio
     * @param estadoEnderecoTermoEstagio
     * @param eEstagioObrigatorio
     * @param aluno
     * @param convenioPF
     * @param professorOrientador
     * @param nomeSupervisor
     * @param cargoSupervisor
     */
    public TermoEstagio(Date dataInicioTermoEstagio, Date dataFimTermoEstagio, Integer cargaHorariaTermoEstagio,
            Float valorBolsa, String enderecoTermoEstagio, String complementoEnderecoTermoEstagio, String bairroEnderecoTermoEstagio, String cepEnderecoTermoEstagio,
            String cidadeEnderecoTermoEstagio, String estadoEnderecoTermoEstagio, Boolean eEstagioObrigatorio,
            Aluno aluno, PessoaFisica convenioPF, ProfessorOrientador professorOrientador, String nomeSupervisor, String cargoSupervisor) {

        this.dataInicioTermoEstagio = dataInicioTermoEstagio;
        this.dataFimTermoEstagio = dataFimTermoEstagio;
        this.cargaHorariaTermoEstagio = cargaHorariaTermoEstagio;
        this.valorBolsa = valorBolsa;
        this.enderecoTermoEstagio = enderecoTermoEstagio;
        this.complementoEnderecoTermoEstagio = complementoEnderecoTermoEstagio;
        this.bairroEnderecoTermoEstagio = bairroEnderecoTermoEstagio;
        this.cepEnderecoTermoEstagio = cepEnderecoTermoEstagio;
        this.cidadeEnderecoTermoEstagio = cidadeEnderecoTermoEstagio;
        this.estadoEnderecoTermoEstagio = estadoEnderecoTermoEstagio;
        this.eEstagioObrigatorio = eEstagioObrigatorio;
        this.aluno = aluno;
        this.convenioPF = convenioPF;
        this.professorOrientador = professorOrientador;
        this.nomeSupervisor = nomeSupervisor;
        this.cargoSupervisor = cargoSupervisor;
    }

    /**
     *
     * @return
     */
    public String getNomeSupervisor() {
        return nomeSupervisor;
    }

    /**
     *
     * @param nomeSupervisor
     */
    public void setNomeSupervisor(String nomeSupervisor) {
        this.nomeSupervisor = nomeSupervisor;
    }

    /**
     *
     * @return
     */
    public String getCargoSupervisor() {
        return cargoSupervisor;
    }

    /**
     *
     * @param cargoSupervisor
     */
    public void setCargoSupervisor(String cargoSupervisor) {
        this.cargoSupervisor = cargoSupervisor;
    }

    /**
     *
     * @param dataInicioTermoEstagio
     * @param dataFimTermoEstagio
     * @param cargaHorariaTermoEstagio
     * @param valorBolsa
     * @param enderecoTermoEstagio
     * @param complementoEnderecoTermoEstagio
     * @param bairroEnderecoTermoEstagio
     * @param cepEnderecoTermoEstagio
     * @param cidadeEnderecoTermoEstagio
     * @param estadoEnderecoTermoEstagio
     * @param eEstagioObrigatorio
     * @param aluno
     * @param convenioPJ
     * @param professorOrientador
     * @param nomeSupervisor
     * @param cargoSupervisor
     */
    public TermoEstagio(Date dataInicioTermoEstagio, Date dataFimTermoEstagio, Integer cargaHorariaTermoEstagio,
            Float valorBolsa, String enderecoTermoEstagio, String complementoEnderecoTermoEstagio, String bairroEnderecoTermoEstagio, String cepEnderecoTermoEstagio,
            String cidadeEnderecoTermoEstagio, String estadoEnderecoTermoEstagio, Boolean eEstagioObrigatorio,
            Aluno aluno, PessoaJuridica convenioPJ, ProfessorOrientador professorOrientador, String nomeSupervisor, String cargoSupervisor) {

        this.dataInicioTermoEstagio = dataInicioTermoEstagio;
        this.dataFimTermoEstagio = dataFimTermoEstagio;
        this.cargaHorariaTermoEstagio = cargaHorariaTermoEstagio;
        this.valorBolsa = valorBolsa;
        this.enderecoTermoEstagio = enderecoTermoEstagio;
        this.complementoEnderecoTermoEstagio = complementoEnderecoTermoEstagio;
        this.bairroEnderecoTermoEstagio = bairroEnderecoTermoEstagio;
        this.cepEnderecoTermoEstagio = cepEnderecoTermoEstagio;
        this.cidadeEnderecoTermoEstagio = cidadeEnderecoTermoEstagio;
        this.estadoEnderecoTermoEstagio = estadoEnderecoTermoEstagio;
        this.eEstagioObrigatorio = eEstagioObrigatorio;
        this.aluno = aluno;
        this.convenioPJ = convenioPJ;
        this.professorOrientador = professorOrientador;
        this.nomeSupervisor = nomeSupervisor;
        this.cargoSupervisor = cargoSupervisor;
    }

    /**
     *
     * @return
     */
    public Integer getIdTermoEstagio() {
        return idTermoEstagio;
    }

    /**
     *
     * @param idTermoEstagio
     */
    public void setIdTermoEstagio(Integer idTermoEstagio) {
        this.idTermoEstagio = idTermoEstagio;
    }

    /**
     *
     * @return
     */
    public Date getDataInicioTermoEstagio() {
        return dataInicioTermoEstagio;
    }

    /**
     *
     * @param dataInicioTermoEstagio
     */
    public void setDataInicioTermoEstagio(Date dataInicioTermoEstagio) {
        this.dataInicioTermoEstagio = dataInicioTermoEstagio;
    }

    /**
     *
     * @return
     */
    public Date getDataFimTermoEstagio() {
        return dataFimTermoEstagio;
    }

    /**
     *
     * @param dataFimTermoEstagio
     */
    public void setDataFimTermoEstagio(Date dataFimTermoEstagio) {
        this.dataFimTermoEstagio = dataFimTermoEstagio;
    }

    /**
     *
     * @return
     */
    public Date getDataRescisaoTermoEstagio() {
        return dataRescisaoTermoEstagio;
    }

    /**
     *
     * @param dataRescisaoTermoEstagio
     */
    public void setDataRescisaoTermoEstagio(Date dataRescisaoTermoEstagio) {
        this.dataRescisaoTermoEstagio = dataRescisaoTermoEstagio;
    }

    /**
     *
     * @return
     */
    public Integer getCargaHorariaTermoEstagio() {
        return cargaHorariaTermoEstagio;
    }

    /**
     *
     * @param cargaHorariaTermoEstagio
     */
    public void setCargaHorariaTermoEstagio(Integer cargaHorariaTermoEstagio) {
        this.cargaHorariaTermoEstagio = cargaHorariaTermoEstagio;
    }

    /**
     *
     * @return
     */
    public Float getValorBolsa() {
        return valorBolsa;
    }

    /**
     *
     * @param valorBolsa
     */
    public void setValorBolsa(Float valorBolsa) {
        this.valorBolsa = valorBolsa;
    }

    /**
     *
     * @return
     */
    public String getEnderecoTermoEstagio() {
        return enderecoTermoEstagio;
    }

    /**
     *
     * @param enderecoTermoEstagio
     */
    public void setEnderecoTermoEstagio(String enderecoTermoEstagio) {
        this.enderecoTermoEstagio = enderecoTermoEstagio;
    }

    /**
     *
     * @return
     */
    public String getComplementoEnderecoTermoEstagio() {
        return complementoEnderecoTermoEstagio;
    }

    /**
     *
     * @param complementoEnderecoTermoEstagio
     */
    public void setComplementoEnderecoTermoEstagio(String complementoEnderecoTermoEstagio) {
        this.complementoEnderecoTermoEstagio = complementoEnderecoTermoEstagio;
    }

    /**
     *
     * @return
     */
    public String getBairroEnderecoTermoEstagio() {
        return bairroEnderecoTermoEstagio;
    }

    /**
     *
     * @param bairroEnderecoTermoEstagio
     */
    public void setBairroEnderecoTermoEstagio(String bairroEnderecoTermoEstagio) {
        this.bairroEnderecoTermoEstagio = bairroEnderecoTermoEstagio;
    }

    /**
     *
     * @return
     */
    public String getCepEnderecoTermoEstagio() {
        return cepEnderecoTermoEstagio;
    }

    /**
     *
     * @param cepEnderecoTermoEstagio
     */
    public void setCepEnderecoTermoEstagio(String cepEnderecoTermoEstagio) {
        this.cepEnderecoTermoEstagio = cepEnderecoTermoEstagio;
    }

    /**
     *
     * @return
     */
    public String getCidadeEnderecoTermoEstagio() {
        return cidadeEnderecoTermoEstagio;
    }

    /**
     *
     * @param cidadeEnderecoTermoEstagio
     */
    public void setCidadeEnderecoTermoEstagio(String cidadeEnderecoTermoEstagio) {
        this.cidadeEnderecoTermoEstagio = cidadeEnderecoTermoEstagio;
    }

    /**
     *
     * @return
     */
    public String getEstadoEnderecoTermoEstagio() {
        return estadoEnderecoTermoEstagio;
    }

    /**
     *
     * @param estadoEnderecoTermoEstagio
     */
    public void setEstadoEnderecoTermoEstagio(String estadoEnderecoTermoEstagio) {
        this.estadoEnderecoTermoEstagio = estadoEnderecoTermoEstagio;
    }

    /**
     *
     * @return
     */
    public Boolean getEEstagioObrigatorio() {
        return eEstagioObrigatorio;
    }

    /**
     *
     * @param eEstagioObrigatorio
     */
    public void setEEstagioObrigatorio(Boolean eEstagioObrigatorio) {
        this.eEstagioObrigatorio = eEstagioObrigatorio;
    }

    /**
     *
     * @return
     */
    public Aluno getAluno() {
        return aluno;
    }

    /**
     *
     * @param aluno
     */
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    /**
     *
     * @return
     */
    public ProfessorOrientador getProfessorOrientador() {
        return professorOrientador;
    }

    /**
     *
     * @param professorOrientador
     */
    public void setProfessorOrientador(ProfessorOrientador professorOrientador) {
        this.professorOrientador = professorOrientador;
    }

    /**
     *
     * @return
     */
    public List<TermoEstagio> getTermosAditivos() {
        return termosAditivos;
    }

    /**
     *
     * @param termosAditivos
     */
    public void setTermosAditivos(List<TermoEstagio> termosAditivos) {
        this.termosAditivos = termosAditivos;
    }

    /**
     *
     * @return
     */
    public Boolean geteEstagioObrigatorio() {
        return eEstagioObrigatorio;
    }

    /**
     *
     * @param eEstagioObrigatorio
     */
    public void seteEstagioObrigatorio(Boolean eEstagioObrigatorio) {
        this.eEstagioObrigatorio = eEstagioObrigatorio;
    }

    /**
     *
     * @return
     */
    public PessoaFisica getConvenioPF() {
        return convenioPF;
    }

    /**
     *
     * @param convenioPF
     */
    public void setConvenioPF(PessoaFisica convenioPF) {
        this.convenioPF = convenioPF;
    }

    /**
     *
     * @return
     */
    public PessoaJuridica getConvenioPJ() {
        return convenioPJ;
    }

    /**
     *
     * @param convenioPJ
     */
    public void setConvenioPJ(PessoaJuridica convenioPJ) {
        this.convenioPJ = convenioPJ;
    }

    /**
     *
     * @return
     */
    public String getAgenciada() {
        return agenciada;
    }

    /**
     *
     * @param agenciada
     */
    public void setAgenciada(String agenciada) {
        this.agenciada = agenciada;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idTermoEstagio == null) ? 0 : idTermoEstagio.hashCode());
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
        TermoEstagio other = (TermoEstagio) obj;
        if (idTermoEstagio == null) {
            if (other.idTermoEstagio != null) {
                return false;
            }
        } else if (!idTermoEstagio.equals(other.idTermoEstagio)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public String toJson() {
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(this);

        JsonReader jsonReader = Json.createReader(new StringReader(result));
        JsonObject jobj = jsonReader.readObject();

        JsonObjectBuilder builder = Json.createObjectBuilder();
        if (this.convenioPF != null) {
            builder.add("convenio", this.getConvenioPF().toJsonObj());
        } else if (this.convenioPJ != null) {
            builder.add("convenio", this.getConvenioPJ().toJsonObj());
        }
        jobj.entrySet().
                forEach(e -> builder.add(e.getKey(), e.getValue()));
        return builder.build().toString();

    }
}
