/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.model.entity;

import java.io.StringReader;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author lucas
 */
@Entity
public class PessoaJuridica{
    @Id
    @GeneratedValue
    private Integer idConvenio;

    @Column(length = 10, nullable = false)
    private String numeroConvenio;
    
    @OneToMany(mappedBy = "convenioPJ")
    private List<TermoEstagio> termoEstagios;
    
    @Column(nullable = false)
    private Date dataAssinatura;

    @Column(length = 100, nullable = true)
    private String email;
    
    @Column(length = 11, nullable = true)
    private String telefone;
    
    @Column(length = 14, nullable = false, unique = true)
    private String cnpj;
    
    @Column(length = 100, nullable = false)
    private String razaoSocial;
    
    @Column(length = 100, nullable = true)
    private String pessoaContato;
    
    @Column(nullable = false)
    private boolean agenteIntegracao;
    
    public PessoaJuridica(){
        
    }
    
    public PessoaJuridica(String numeroConvenio){
        this.numeroConvenio = numeroConvenio;
    }
    
    public PessoaJuridica(String cnpj, String razaoSocial, boolean agenteIntegracao, Date dataAssinatura){
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.agenteIntegracao = agenteIntegracao;
        this.dataAssinatura = dataAssinatura;
    }
    
    public Integer getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(Integer idConvenio) {
        this.idConvenio = idConvenio;
    }

    public String getNumeroConvenio() {
        return numeroConvenio;
    }

    public void setNumeroConvenio(String numeroConvenio) {
        this.numeroConvenio = numeroConvenio;
    }
    
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getPessoaContato() {
        return pessoaContato;
    }

    public void setPessoaContato(String pessoaContato) {
        this.pessoaContato = pessoaContato;
    }

    public boolean isAgenteIntegracao() {
        return agenteIntegracao;
    }

    public void setAgenteIntegracao(boolean agenteIntegracao) {
        this.agenteIntegracao = agenteIntegracao;
    }

    public List<TermoEstagio> getTermoEstagios() {
        return termoEstagios;
    }

    public void setTermoEstagios(List<TermoEstagio> termoEstagios) {
        this.termoEstagios = termoEstagios;
    }

    public Date getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(Date dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public JsonObject toJsonObj() {
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(this);

        JsonReader jsonReader = Json.createReader(new StringReader(result));
        JsonObject jobj = jsonReader.readObject();
        return jobj;
    }
}
