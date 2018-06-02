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
	<fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_titulo"/>
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
		<h5><fmt:message key="br.cefetrj.sisgee.form_empresa.msg_titulo" /></h5>		
		
		<form action="ValidaCadastroEmpresaServlet" method="post">
			<fieldset class="form-group">
				
                            

                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label for="tipoPessoa"><fmt:message key = "br.cefetrj.sisgee.resources.form.tipoPessoa"/></label>
                                </div>

                                <div class="custom-controls-stacked d-block my-3">							
                                    <label class="custom-control custom-radio">
                                        <input id="tipoPessoa" class="custom-control-input isAgenteChk ${ not empty tipoPessoaMsg ? 'is-invalid' : '' }" type="radio" name="tipoPessoa" value="cpf" ${ not empty tipoPessoaMsg ? '' : param.tipoPessoa == 'cpf' ? 'checked' : '' }> 
                                        <span class="custom-control-indicator"></span> 
                                        <span class="custom-control-description" ><fmt:message key = "br.cefetrj.sisgee.resources.form.cpf"/></span>
                                    </label>						

                                    <label class="custom-control custom-radio">
                                        <input id="tipoPessoa" class="custom-control-input isAgenteChk ${ not empty tipoPessoaMsg ? 'is-invalid' : '' }" type="radio" name="tipoPessoa" value="cnpj" ${ not empty tipoPessoaMsg ? '' : param.tipoPessoa == 'cnpj' ? 'checked' : '' }> 
                                        <span class="custom-control-indicator"></span> 
                                        <span class="custom-control-description"><fmt:message key = "br.cefetrj.sisgee.resources.form.cnpj"/></span>
                                    </label>
                                    <c:if test="${ not empty tipoPessoaMsg }">
                                        <div class="invalid-feedback">${ tipoPessoaMsg }</div>
                                    </c:if>
                                </div>
                                    
                                
                            </div>

                                
                            
                            
                            
                            
				

							
			<div class="form-row notAI AI" ${ empty param.tipoPessoa ? "style='display:none'" : param.tipoPessoa == "cpf" ? "style='display:none'" : "" }>
                            
				<div class="form-group col-md-6">
					<label for="cnpjConvenio"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_cnpj"/></label>
					<input type="text" class="form-control ${ not empty cnpjConvenioMsg ? 'is-invalid': 'is-valid' }" id="cnpjConvenio" name="cnpjConvenio" required value="${ param.cnpjConvenio }">
						<c:if test="${ not empty cnpjConvenioMsg }">
				    		<div class="invalid-feedback">${ cnpjConvenioMsg }</div>
		        		</c:if>
				</div>
																			
				<div class="form-group col-md-6">
					<label for="razaoSocial"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_razao_social"/></label>
                                        <input type="text" class="form-control ${ not empty razaoSocialMsg ? 'is-invalid': 'is-valid' }" id="razaoSocial" name="razaoSocial" maxlength="100" required value="${ param.razaoSocial }">
						<c:if test="${ not empty razaoSocialMsg }">
				    		<div class="invalid-feedback">${ razaoSocialMsg }</div>
		        		</c:if>
				</div>
                                                
                                <div class="form-group col-md-4" >
					<label for="agenteIntegracao"><fmt:message key="br.cefetrj.sisgee.form_empresa.msg_agente_integracao" /></label>
                                        
                                        <div class="custom-controls-stacked d-block my-3">
					<label class="custom-control custom-radio"> 
						<input id="agenteSim" name="agenteIntegracao" type="radio" class="custom-control-input" required value = "sim" > 
						<span class="custom-control-indicator"></span> 
						<span class="custom-control-description" ><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_sim"/></span>
					</label> 
					<label class="custom-control custom-radio"> 
						<input id="agenteNao" name="agenteIntegracao" type="radio" class="custom-control-input" required value = "nao" > 
						<span class="custom-control-indicator"></span> 
						<span class="custom-control-description"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_nao"/></span>
					</label>
				</div>		
				</div>
                                        
                        </div> 
                                        
                                        
                        <div class="form-row notAI AI" ${ empty param.tipoPessoa ? "style='display:none'" : param.tipoPessoa == "cnpj" ? "style='display:none'" : "" }>
                            
				<div class="form-group col-md-6">
					<label for="cnpjConvenio"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_cnpj"/></label>
					<input type="text" class="form-control ${ not empty cnpjConvenioMsg ? 'is-invalid': 'is-valid' }" id="cnpjConvenio" name="cnpjConvenio" required value="${ param.cnpjConvenio }">
						<c:if test="${ not empty cnpjConvenioMsg }">
				    		<div class="invalid-feedback">${ cnpjConvenioMsg }</div>
		        		</c:if>
				</div>
																			
				<div class="form-group col-md-6">
					<label for="razaoSocial"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_razao_social"/></label>
                                        <input type="text" class="form-control ${ not empty razaoSocialMsg ? 'is-invalid': 'is-valid' }" id="razaoSocial" name="razaoSocial" maxlength="100" required value="${ param.razaoSocial }">
						<c:if test="${ not empty razaoSocialMsg }">
				    		<div class="invalid-feedback">${ razaoSocialMsg }</div>
		        		</c:if>
				</div>
                                                
                                
                        </div>                
                                        
                                        
                                        
                                        
                                            
                                                
                                                
                                <div class="form-group col-md-6">

                                     <label for="dataAssinatura"><fmt:message key = "br.cefetrj.sisgee.resources.form.dataAssinatura"/></label>
                                     <input type="text" class="form-control col-sm-4 ${ not empty dataAssinaturaMsg ? 'is-invalid': not empty dataAssinaturaMsg ? 'is-invalid' : 'is-valid' }" id="dataAssinatura"  name="dataAssinatura" required  value="${ param.dataAssinatura }" ${ not empty termoEstagio ? 'disabled' : '' } >
                                         <c:if test="${ not empty dataAssinaturaMsg }">
                                              <div class="invalid-feedback">${ dataAssinaturaMsg }</div>
                                         </c:if>
                                </div> 
                                                
                                <div class="form-group col-md-6">
					<label for="email"><fmt:message key = "br.cefetrj.sisgee.resources.form.email"/></label>
                                        <input type="email" placeholder="nome@exemplo.com" class="form-control ${ not empty EmailMsg ? 'is-invalid': 'is-valid' }" id="email" name="email" maxlength="50" value="${ param.email }">
						<c:if test="${ not empty EmailMsg }">
				    		<div class="invalid-feedback">${ EmailMsg }</div>
		        		</c:if>
				</div>
                                             
                                <div class="form-group col-md-6">
					<label for="telefone"><fmt:message key = "br.cefetrj.sisgee.resources.form.telefone"/></label>
                                        <input type="tel"  class="form-control mask('(99) 99999-9999') ${ not empty telefoneMsg ? 'is-invalid': 'is-valid' }" id="telefone" name="telefone" maxlength="11" value="${ param.telefone }">
						<c:if test="${ not empty telefoneMsg }">
				    		<div class="invalid-feedback">${ telefoneMsg }</div>
		        		</c:if>
				</div>
                                                
                                                
                                                
                               
                                                
                   
                    
                    
                        
                        
                   
               
                            
                            
                            
                            
                            
		</fieldset>
					
			<button type="submit" class="btn btn-primary"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_salvar"/></button>
			<button type="button" class="btn btn-secondary" onclick="javascript:location.href='index.jsp'"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_cancelar"/></button>
								
		</form>
	</div>
	<%@include file="import_footer.jspf"%>
	<%@include file="import_finalbodyscripts.jspf"%>
        <%@include file="import_scripts.jspf"%>
        <script>
            $(document).ready(function(){
                $('#cnpjEmpresa').mask('99.999.999/9999-99');
                $('#dataAssinatura').mask('99/99/9999');
                $('#telefone').mask('(99) 99999-9999');
                
            });
        </script>