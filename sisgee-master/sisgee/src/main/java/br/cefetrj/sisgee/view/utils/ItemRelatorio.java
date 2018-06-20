package br.cefetrj.sisgee.view.utils;

import br.cefetrj.sisgee.model.entity.PessoaFisica;
import br.cefetrj.sisgee.model.entity.PessoaJuridica;
import static com.oracle.nio.BufferSecrets.instance;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe para auxiliar na organizacao do relatorio consolidado.
 *
 * @author Marcos Eduardo
 * @since 1.0
 */
public class ItemRelatorio {

    private String nomeCurso;
    private int qntTermoEstagio;
    private int qntTermoAditivo;
    private int qntRescReg;

    private String numeroConvenio;
    private String idConvenio;
    private String nomeConvenio;
    private Object objConvenio;
    private Class tipoConvenio;
    private String email;
    private String telefone;
    private String pessoaContato;
    private Date dataVencimento;
    private String dataFormatada;

    public String getDataFormatada() {
        final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        dataFormatada = df.format(dataVencimento);
        return dataFormatada;
    }

    public void setDataFormatada(String dataFormatada) {
        final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        this.dataFormatada = df.format(dataVencimento);
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public ItemRelatorio(String numeroConvenio, String idConvenio, String nomeConvenio, Object objConvenio, Class tipoConvenio) throws ParseException {
        super();
        this.numeroConvenio = numeroConvenio;
        if (idConvenio.length() == 11) {

        } else {

        }
        this.idConvenio = idConvenio;
        this.nomeConvenio = nomeConvenio;
        this.objConvenio = objConvenio;
        this.tipoConvenio = tipoConvenio;
        this.tipoConvenio = tipoConvenio;
        if (objConvenio instanceof PessoaFisica) {
            this.email = ((PessoaFisica) objConvenio).getEmail();
            this.telefone = ((PessoaFisica) objConvenio).getTelefone();
            this.pessoaContato = "";
            Calendar c = Calendar.getInstance();
            c.setTime(((PessoaFisica) objConvenio).getDataAssinatura());
            c.add(Calendar.YEAR, +5);
            Date dataVencimentoC = c.getTime();
            SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            dataVencimentoC = formata.parse(formata.format(dataVencimentoC));
            this.dataVencimento = dataVencimentoC;

        }
        if (objConvenio instanceof PessoaJuridica) {
            this.email = ((PessoaJuridica) objConvenio).getEmail();
            this.telefone = ((PessoaJuridica) objConvenio).getTelefone();
            this.telefone = ((PessoaJuridica) objConvenio).getTelefone();
            this.pessoaContato = ((PessoaJuridica) objConvenio).getPessoaCOntato();
            Calendar c = Calendar.getInstance();
            c.setTime(((PessoaJuridica) objConvenio).getDataAssinatura());
            c.add(Calendar.YEAR, +5);
            Date dataVencimentoC = c.getTime();
            SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            dataVencimentoC = formata.parse(formata.format(dataVencimentoC));
            System.out.println(dataVencimentoC);
            this.dataVencimento = dataVencimentoC;
        }

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

    public String getPessoaContato() {
        return pessoaContato;
    }

    public void setPessoaContato(String pessoaContato) {
        this.pessoaContato = pessoaContato;
    }

    public String getNumeroConvenio() {
        return numeroConvenio;
    }

    public void setNumeroConvenio(String numeroConvenio) {
        this.numeroConvenio = numeroConvenio;
    }

    public String getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(String idConvenio) {
        this.idConvenio = idConvenio;
    }

    public String getNomeConvenio() {
        return nomeConvenio;
    }

    public void setNomeConvenio(String nomeConvenio) {
        this.nomeConvenio = nomeConvenio;
    }

    public Object getObjConvenio() {
        return objConvenio;
    }

    public void setObjConvenio(Object objConvenio) {
        this.objConvenio = objConvenio;
    }

    public Class getTipoConvenio() {
        return tipoConvenio;
    }

    public void setTipoConvenio(Class tipoConvenio) {
        this.tipoConvenio = tipoConvenio;
    }

    public ItemRelatorio(String nomeCurso) {
        super();
        this.nomeCurso = nomeCurso;
    }

    public ItemRelatorio(String nomeCurso, int qntTermoEstagio, int qntRescReg) {
        super();
        this.nomeCurso = nomeCurso;
        this.qntTermoEstagio = qntTermoEstagio;
        this.qntRescReg = qntRescReg;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public int getQntTermoEstagio() {
        return qntTermoEstagio;
    }

    public void setQntTermoEstagio(int qntTermoEstagio) {
        this.qntTermoEstagio += qntTermoEstagio;
    }

    public int getQntTermoAditivo() {
        return qntTermoAditivo;
    }

    public void setQntTermoAditivo(int qntTermoAditivo) {
        this.qntTermoAditivo += qntTermoAditivo;
    }

    public int getQntRescReg() {
        return qntRescReg;
    }

    public void setQntRescReg(int qntRescReg) {
        this.qntRescReg = qntRescReg;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nomeCurso == null) ? 0 : nomeCurso.hashCode());
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
        ItemRelatorio other = (ItemRelatorio) obj;
        if (nomeCurso == null) {
            if (other.nomeCurso != null) {
                return false;
            }
        } else if (!nomeCurso.equals(other.nomeCurso)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItemRelatorio [nomeCurso=" + nomeCurso + ", qntTermoEstagio=" + qntTermoEstagio + ", qntTermoAditivo="
                + qntTermoAditivo + ", qntRescReg=" + qntRescReg + "]";
    }

}
