/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.view.utils;

import br.cefetrj.sisgee.model.entity.TermoEstagio;
import java.util.Comparator;

/**
 *
 * @author Pirlimpimposo
 */
public class ComparaTermos implements Comparator<TermoEstagio>{

    @Override
    public int compare(TermoEstagio o1, TermoEstagio o2) {
        if(o1.getDataInicioTermoEstagio().compareTo(o2.getDataInicioTermoEstagio()) != 0){
            return (o1.getDataInicioTermoEstagio().compareTo(o2.getDataInicioTermoEstagio()));
        }
        else{
            return (o1.getIdTermoEstagio().compareTo(o2.getIdTermoEstagio()));
        }
    }
    
}
