<!DOCTYPE html>
<html lang="en">
    <head>
        <%@include file="import_head.jspf"%>

        <style type="text/css">
            div.container {
                margin-bottom: 2em;
            }
            form {
                margin-top: 50px;
            }
            fieldset.form-group {
                border:1px solid #999999;
            }
            fieldset legend.col-form-legend {
                font-weight: bold;
            }
            div.form-row {
                padding: 0px 15px;
            }
        </style>

        <title>
            <c:choose>
                <c:when test="${ not empty termo }">
                    <fmt:message key = "br.cefetrj.sisgee.resources.form.registroTermoAditivo"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key = "br.cefetrj.sisgee.resources.form.registroTermoEstagio"/>
                </c:otherwise>
            </c:choose>
        </title>
    </head>
    <body>
        <%@include file="import_navbar.jspf"%>	


        <div class="container">
            <c:if test="${ not empty msg }">
                <div class="alert alert-warning" role="alert">
                    ${ msg }
                </div>
            </c:if>
            <c:if test="${ not empty msg2 }">
                <div class="alert alert-warning" role="alert">
                    ${ msg2 }
                </div>
            </c:if>

            <p class="tituloForm">

            <h5>		
                <c:choose>
                    <c:when test="${ not empty termo }">
                        <fmt:message key = "br.cefetrj.sisgee.resources.form.registroTermoAditivo"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key = "br.cefetrj.sisgee.resources.form.registroTermoEstagio"/>
                    </c:otherwise>
                </c:choose>
            </h5>		
        </p>		

        <c:choose>
            <c:when test="${ not empty termo }">
                <form action="FormTermoAditivoServlet" method="post">
                </c:when>
                <c:otherwise>
                    <form action="FormTermoEstagioServlet" method="post">
                    </c:otherwise>
                </c:choose>

                <fieldset class="form-group" ${ not empty termo ? 'disabled' : '' }>
                    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.dadosEmpresaConveniada"/></legend>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="numeroConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.numeroConvenio"/></label>
                            <div class="input-group">						  
                                <input type="text" class="form-control numeroConvenio" placeholder="Digite o Número" id="numeroConvenio" name="numeroConvenio" value="${ not empty termo.convenioPJ ? termo.convenioPJ.numeroConvenio : termo.convenioPF.numeroConvenio }">
                                <span class="input-group-btn">
                                    <button class="btn btn-primary" type="button" id="btnBuscarNumeroConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.buscar"/></button>
                                </span>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="nomeConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.nomeConvenio"/></label>
                            <div class="input-group">						  
                                <input type="text" class="form-control nomeConvenioNotAI nomeConvenio" placeholder="Digite o Nome" id="nomeConvenio" name="nomeConvenio" value="${ not empty termo.convenioPJ ? termo.convenioPJ.razaoSocial : termo.convenioPF.nome  }">
                                <span class="input-group-btn">
                                    <button class="btn btn-primary" type="button" id="btnBuscarNomeConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.buscar"/></button>
                                </span>
                                &nbsp;&nbsp;
                                <button type="button" class="btn btn-primary addEmpresa">+</button>
                            </div>
                        </div>
                    </div>
                    <div class="form-row"  id="divPF" style="display:${not empty termo.convenioPF.nome ? '' : "none"}">
                        <div class="form-group col-md-5">
                            <label for="cpfConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.cpf.pf"/></label>
                            <input type="text" class="form-control" id="cpfConvenio" name="cpfConvenio" value="${ termo.convenioPF.cpf }" readonly>
                        </div>
                        <div class="form-group col-md-5">
                            <label for="nomePF"><fmt:message key = "br.cefetrj.sisgee.resources.form.nomePessoaFisica"/></label>
                            <input type="text" class="form-control nomeEmpresaIsAI nomePF" id="nomePF" name="nomePF" value="${ termo.convenioPF.nome }" readonly>
                        </div>
                    </div>
                    <div class="form-row" id="divPJ" style="display:${not empty termo.convenioPJ ? '' : "none"}">
                        <div class="form-group col-md-5">
                            <label for="cnpjConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.cnpj"/></label>
                            <input type="text" class="form-control nomeEmpresaIsAI nomePF" id="cnpjConvenio" name="cnpjConvenio" value="${ termo.convenioPJ.cnpj }" readonly>
                        </div>
                        <div class="form-group col-md-5">
                            <label for="razaoSocial"><fmt:message key = "br.cefetrj.sisgee.resources.form.razaoSocial"/></label>
                            <input type="text" class="form-control nomeEmpresaIsAI nomePF" id="razaoSocial" name="razaoSocial" value="${ termo.convenioPJ.razaoSocial }" readonly>
                        </div>
                        <div class="custom-controls-stacked d-block my-3" id="divAgt">
                            <label for="agenteIntegracao"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_agente_integracao"/></label>
                            <label class="custom-control custom-radio">
                                <input id="Agente" type="radio" class="custom-control-input isAgenteChk" name="agtInt" value="sim" ${not empty termo.convenioPF ? termo.convenioPF.agenteIntegracao == true ? "checked" : '' : termo.convenioPJ.agenteIntegracao == true ? "checked" : '' }> 
                                <span class="custom-control-indicator"></span> 
                                <span class="custom-control-description" ><fmt:message key = "br.cefetrj.sisgee.resources.form.sim"/></span>
                            </label>						
                            <label class="custom-control custom-radio"> 
                                <input id="NaoAgente" type="radio" class="custom-control-input isAgenteChk" name="agtInt" value="nao" ${not empty termo.convenioPF ? termo.convenioPF.agenteIntegracao == false ? "checked" : '' : termo.convenioPJ.agenteIntegracao == false ? "checked" : ''} > 
                                <span class="custom-control-indicator"></span> 
                                <span class="custom-control-description"><fmt:message key = "br.cefetrj.sisgee.resources.form.nao"/></span>
                            </label>
                        </div>
                    </div>
                    <div class="custom-controls-stacked d-block my-3" id="divTipo" style="visibility:hidden">							
                        <label class="custom-control custom-radio">
                            <input id="PessoaFisica" class="custom-control-input isJuridicaChk ${not empty isPessoa ? 'is-invalid' : '' }" type="radio" name="tipoPessoa" value="cpf"> 
                            <span class="custom-control-indicator"></span> 
                            <span class="custom-control-description" ><fmt:message key = "br.cefetrj.sisgee.resources.form.pFisica"/></span>
                        </label>						
                        <label class="custom-control custom-radio"> 
                            <input id="PessoaJuridica" class="custom-control-input isJuridicaChk ${not empty isPessoa ? 'is-invalid' : '' }" type="radio" name="tipoPessoa" value="cnpj" > 
                            <span class="custom-control-indicator"></span> 
                            <span class="custom-control-description"><fmt:message key = "br.cefetrj.sisgee.resources.form.pJuridica"/></span>
                        </label>
                    </div>
                </fieldset>


                <fieldset class="form-group dadosAluno" ${ not empty termo ? 'disabled' :'' }>
                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.dadosAluno"/></legend>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="matricula"><fmt:message key = "br.cefetrj.sisgee.resources.form.matricula"/></label>
                            <div class="input-group">
                                <input type="hidden" id="idAluno" name="idAluno" value="${ param.idAluno }">
                                <input type="text" maxlength="100" class="form-control ${ not empty idAlunoMsg ? 'is-invalid': 'is-valid' }" id="matricula" name="matriculaPopUp" value="${ matriculaPopUp }" readonly>


                                <c:if test="${ not empty idAlunoMsg }">
                                    <div class="invalid-feedback">${ idAlunoMsg }</div>
                                </c:if>

                            </div>					    
                        </div>
                        <div class="form-group col-md">
                            <label for="nome"><fmt:message key = "br.cefetrj.sisgee.resources.form.nome"/></label>
                            <input type="text" class="form-control" id="nome" name="nome" value="${ nomeAlunoPopUp}" readonly>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="nomeCurso"><fmt:message key = "br.cefetrj.sisgee.resources.form.curso"/></label>
                            <input type="text" class="form-control" id="nomeCurso"  name="nomeCurso" value="${ nomeCursoPopUp }" readonly>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="nomeCampus"><fmt:message key = "br.cefetrj.sisgee.resources.form.unidade"/></label>
                            <input type="text" class="form-control" id="nomeCampus"  name="nomeCampus" value="${ nomeCampusPopUp }" readonly>
                        </div>
                    </div>			

                </fieldset>


                <c:if test="${ not empty periodoMsg }">
                    <div class="alert alert-danger" role="alert">${ periodoMsg }</div>
                </c:if>
                <fieldset class="form-group" ${ isVisualizacao eq true ? 'disabled' :'' }>
                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.vigenciaEstagio"/></legend>
                    <div class="form-row">
                        <div class="form-group col-md-6">

                            <label for="dataInicioTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.dataInicio"/></label>
                            <input type="text" class="form-control col-sm-4 ${ not empty dataInicioMsg ? 'is-invalid': not empty periodoMsg ? 'is-invalid' : 'is-valid' }" id="dataInicioTermoEstagio"  name="dataInicioTermoEstagio" value="${ not empty termo ? termo.dataInicioTermoEstagio.toString().replaceAll('-','') : '' }" ${ not empty termo ? 'disabled' : '' } >
                            <c:if test="${ not empty dataInicioMsg }">
                                <div class="invalid-feedback">${ dataInicioMsg }</div>
                            </c:if>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="dataFimTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.dataTermino"/></label>
                            <input type="text" class="form-control col-sm-4 ${ not empty dataFimMsg ? 'is-invalid': not empty periodoMsg ? 'is-invalid' : 'is-valid' }" id="dataFimTermoEstagio"   name="dataFimTermoEstagio" value="${ not empty termo ? termo.dataFimTermoEstagio.toString().replaceAll('-','') : '' }" ${ empty termo ? '' : empty updVigencia ? 'disabled' : '' } >
                            <c:if test="${ not empty dataFimMsg }">
                                <div class="invalid-feedback">${ dataFimMsg }</div>
                            </c:if>
                        </div>
                    </div>
                </fieldset>


                <fieldset class="form-group" ${ isVisualizacao eq true ? 'disabled' :'' }>
                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.cargaHorariaAluno"/></legend>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="cargaHorariaTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.horasDia"/></label>
                            <input type="text" maxlength="1" class="form-control col-sm-2 ${ not empty cargaHorariaMsg ? 'is-invalid': 'is-valid' }" id="cargaHorariaTermoEstagio" name="cargaHorariaTermoEstagio" value="${ not empty termo ? termo.cargaHorariaTermoEstagio : '' }" ${ empty termo ? '' : empty updCargaHoraria ? 'disabled' : '' }>
                            <c:if test="${ not empty cargaHorariaMsg }">
                                <div class="invalid-feedback">${ cargaHorariaMsg }</div>
                            </c:if>
                        </div>
                    </div>
                </fieldset>


                <fieldset class="form-group" ${ isVisualizacao eq true ? 'disabled' :'' }>
                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.valorBolsaEstagio"/></legend>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="valorBolsa">Valor (R$)</label>
                            <input type="text" class="form-control col-sm-6 ${ not empty valorBolsaMsg ? 'is-invalid': 'is-valid' }" id="valorBolsa" name="valorBolsa" value="${ not empty termo ? termo.valorBolsa : ''  }" ${ empty termo ? '' : empty updValorBolsa ? 'disabled' : '' }>
                            <c:if test="${ not empty valorBolsaMsg }">
                                <div class="invalid-feedback">${ valorBolsaMsg }</div>
                            </c:if>
                        </div>
                    </div>
                </fieldset>


                <fieldset class="form-group" ${ isVisualizacao eq true ? 'disabled' :'' } ${ empty termoEstagio ? '' : empty updEndereco ? 'disabled' : '' }>
                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.localEstagio"/></legend>
                    <div class="form-row">
                        <div class="form-group col-md-12">

                            <label for="enderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.endereco"/></label>
                            <input type="text" maxlength="255" class="form-control ${ not empty enderecoMsg ? 'is-invalid': not empty enderecoMsg ? 'is-invalid' : 'is-valid' }" id="enderecoTermoEstagio" name="enderecoTermoEstagio" value="${ not empty termo ? termo.enderecoTermoEstagio : ''}">
                            <c:if test="${ not empty enderecoMsg }">
                                <div class="invalid-feedback">${ enderecoMsg }</div>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label for="numeroEnderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.numero"/></label>
                            <input type="text" maxlength="10" class="form-control ${ not empty numeroEnderecoMsg ? 'is-invalid': not empty numeroEnderecoMsg ? 'is-invalid' : 'is-valid' }" id="numeroEnderecoTermoEstagio" name="numeroEnderecoTermoEstagio" value="${ not empty termo ? termo.numeroEnderecoTermoEstagio : '' }">
                            <c:if test="${ not empty numeroEnderecoMsg }">
                                <div class="invalid-feedback">${ numeroEnderecoMsg }</div>
                            </c:if>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="complementoEnderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.complemento"/></label>
                            <input maxlength="150" type="text" class="form-control ${ not empty complementoEnderecoMsg ? 'is-invalid': not empty complementoEnderecoMsg ? 'is-invalid' : 'is-valid' }" id="complementoEnderecoTermoEstagio" name="complementoEnderecoTermoEstagio" value="${ not empty termo ? termo.complementoEnderecoTermoEstagio : '' }">
                            <c:if test="${ not empty complementoEnderecoMsg }">
                                <div class="invalid-feedback">${ complementoEnderecoMsg }</div>
                            </c:if>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="bairroEnderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.bairro"/></label>
                            <input type="text" maxlength="150" class="form-control ${ not empty bairroEnderecoMsg ? 'is-invalid': not empty bairroEnderecoMsg ? 'is-invalid' : 'is-valid' }" id="bairroEnderecoTermoEstagio" name="bairroEnderecoTermoEstagio" value="${ not empty termo ? termo.bairroEnderecoTermoEstagio : ''}">
                            <c:if test="${ not empty bairroEnderecoMsg }">
                                <div class="invalid-feedback">${ bairroEnderecoMsg }</div>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-row">					
                        <div class="form-group col-md-6">
                            <label for="cidadeEnderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.cidade"/></label>
                            <input type="text" maxlength="150" class="form-control ${ not empty cidadeEnderecoMsg ? 'is-invalid': not empty cidadeEnderecoMsg ? 'is-invalid' : 'is-valid' }" id="cidadeEnderecoTermoEstagio" name="cidadeEnderecoTermoEstagio" value="${ not empty termo ? termo.cidadeEnderecoTermoEstagio : '' }">
                            <c:if test="${ not empty cidadeEnderecoMsg }">
                                <div class="invalid-feedback">${ cidadeEnderecoMsg }</div>
                            </c:if>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="estadoEnderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.estado"/></label>
                            <select name = "estadoEnderecoTermoEstagio" id="estadoEnderecoTermoEstagio" class="form-control ${ not empty estadoEnderecoMsg ? 'is-invalid': not empty estadoEnderecoMsg ? 'is-invalid' : 'is-valid' }">
                                <option value="" selected>---</option>
                                <c:forEach items="${ uf }" var="uf">
                                    <option value="${ uf }">${ uf }</option>
                                </c:forEach>

                                <c:if test="${ not empty termo }">
                                    <option selected>${termo.estadoEnderecoTermoEstagio.toUpperCase()}</option>
                                </c:if>
                            </select>
                            <c:if test="${ not empty estadoEnderecoMsg }">
                                <div class="invalid-feedback">${ estadoEnderecoMsg }</div>
                            </c:if>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="cepEnderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.cep"/></label>
                            <input type="text" class="form-control ${ not empty cepEnderecoMsg ? 'is-invalid': not empty cepEnderecoMsg ? 'is-invalid' : 'is-valid' }" id="cepEnderecoTermoEstagio" name="cepEnderecoTermoEstagio" value="${ not empty termo ? termo.cepEnderecoTermoEstagio : '' }">
                            <c:if test="${ not empty cepEnderecoMsg }">
                                <div class="invalid-feedback">${ cepEnderecoMsg }</div>
                            </c:if>
                        </div>
                    </div>
                </fieldset>


                <div class="form-row" >
                    <div class="form-group col-md-3" ${ isVisualizacao eq true ? 'disabled' :'' } >
                        <label for="eEstagioObrigatorio"><fmt:message key = "br.cefetrj.sisgee.resources.form.estagioObrigatorio"/></label>
                    </div>

                    <div class="custom-controls-stacked d-block my-3" ${ isVisualizacao eq true ? 'disabled' :'' }>
                        <label class="custom-control custom-radio"> 
                            <input id="estagioSim" name="eEstagioObrigatorio" type="radio" class="custom-control-input ${ not empty eEstagioObrigatorioMsg ? 'is-invalid' : '' }" value = "sim" ${ not empty termo ? 'disabled' :'' } ${ not empty termo ? termo.eEstagioObrigatorio == true ? 'checked' : '' : ''  }> 
                            <span class="custom-control-indicator"></span> 
                            <span class="custom-control-description" ><fmt:message key = "br.cefetrj.sisgee.resources.form.sim"/></span>
                        </label> 
                        <label class="custom-control custom-radio"> 
                            <input id="estagioNao" name="eEstagioObrigatorio" type="radio" class="custom-control-input ${ not empty eEstagioObrigatorioMsg ? 'is-invalid' : '' }" value = "nao" ${ not empty termo ? 'disabled' :'' } ${ not empty termo ?  termo.eEstagioObrigatorio == false ? 'checked' : '' : '' }> 
                            <span class="custom-control-indicator"></span> 
                            <span class="custom-control-description"><fmt:message key = "br.cefetrj.sisgee.resources.form.nao"/></span>
                        </label>
                    </div>				
                </div>

                <fieldset ${ isVisualizacao eq true ? 'disabled' :'' }>
                    <div class="form-group col-md-8">
                        <label for="idProfessorOrientador"><fmt:message key = "br.cefetrj.sisgee.resources.form.professorOrientador"/></label>
                        <select name="idProfessorOrientador" id="idProfessorOrientador" class="form-control ${ not empty idProfessorMsg ? 'is-invalid': not empty idProfessorMsg ? 'is-invalid' : 'is-valid' }" ${ empty termo ? '' : empty updProfessor ? 'disabled' : '' }>
                            <option value="" selected>---</option>
                            <c:forEach items="${ professores }" var="professor">
                                <option value="${ professor.idProfessorOrientador }">${ professor.nomeProfessorOrientador }</option>
                            </c:forEach>
                            <c:if test="${ not empty termo }">
                                <option selected>${termo.professorOrientador.nomeProfessorOrientador}</option>
                            </c:if>    
                        </select>
                        <c:if test="${ not empty idProfessorMsg }">
                            <div class="invalid-feedback">${ idProfessorMsg }</div>
                        </c:if>				
                    </div>
                </fieldset>

                <c:if test="${ not empty termo }">
                    <input type="hidden" name="idTermoEstagio" value="${ termo.idTermoEstagio }" />
                    <input type="hidden" name="updVigencia" value="${ updVigencia }" />
                    <input type="hidden" name="updCargaHoraria" value="${ updCargaHoraria }" />
                    <input type="hidden" name="updProfessor" value="${ updProfessor }" />
                    <input type="hidden" name="updValorBolsa" value="${ updValorBolsa }" />
                    <input type="hidden" name="updEndereco" value="${ updEndereco }" />
                </c:if>

                <button type="submit" class="btn btn-primary" ${ isVisualizacao eq true ? 'disabled' :'' }><fmt:message key = "br.cefetrj.sisgee.resources.form.salvar"/></button>
                <c:choose>
                    <c:when test="${ not empty termo }">
                        <button type="button" class="btn btn-secondary" onclick="javascript:location.href = 'form_termo_aditivo.jsp'"><fmt:message key = "br.cefetrj.sisgee.resources.form.cancelar"/></button>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-secondary" onclick="javascript:location.href = 'index.jsp'"><fmt:message key = "br.cefetrj.sisgee.resources.form.cancelar"/></button>
                    </c:otherwise>
                </c:choose>	
            </form>

            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="myModalLabel"></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body"></div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message key = "br.cefetrj.sisgee.resources.form.fechar"/></button>
                        </div>
                    </div>
                </div>
            </div>

    </div>
    <%@include file="import_footer.jspf"%>
    <%@include file="import_finalbodyscripts.jspf"%>
    <%@include file="import_scripts.jspf"%>
    <script>
        $(document).ready(function () {
            $('#cargaHorariaTermoEstagio').mask('9');
            $('#valorBolsa').mask('000.000,00', {reverse: true});
            $('#dataInicioTermoEstagio').mask('99/99/9999');
            $('#dataFimTermoEstagio').mask('99/99/9999');
            $('#cnpjEmpresa1').mask('99.999.999/9999-99');
            $('#cnpjEmpresa2').mask('99.999.999/9999-99');
            $('#cepEnderecoTermoEstagio').mask('99.999-999');
        });
    </script>
</body>
</html>