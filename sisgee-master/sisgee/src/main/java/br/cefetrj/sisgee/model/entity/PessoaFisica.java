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
public class PessoaFisica{
    
    @Id
    @GeneratedValue
    private Integer idConvenio;

    @Column(length = 10, nullable = false)
    private String numeroConvenio;
    
    @OneToMany(mappedBy = "convenioPF")
    private List<TermoEstagio> termoEstagios;
    
    @Column(nullable = false)
    private Date dataAssinatura;
    
    @Column(length = 100, nullable = true)
    private String email;
    
    @Column(length = 11, nullable = true)
    private String telefone;
    
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;
   
    @Column(length = 100, nullable = false)
    private String nome;
    
    public PessoaFisica(){
        
    }
    
    public PessoaFisica(String numeroConvenio){
        this.numeroConvenio = numeroConvenio;
    }
    
    public PessoaFisica(String cpf, String nome, Date dataAssinatura){
        this.cpf = cpf;
        this.nome = nome;
        this.dataAssinatura = dataAssinatura;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public JsonObject toJsonObj() {
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(this);

        JsonReader jsonReader = Json.createReader(new StringReader(result));
        JsonObject jobj = jsonReader.readObject();
        return jobj;
    }
}
