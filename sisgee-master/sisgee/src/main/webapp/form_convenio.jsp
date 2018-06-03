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
           

           
		<h5><fmt:message key="br.cefetrj.sisgee.form_empresa.msg_titulo" /></h5>		
		
		<form action="ValidaCadastroEmpresaServlet" method="post">
			<fieldset class="form-group">

                            <div class="form-row">   
                                <div class="form-group col-md-4">  
                                    <label for="tipoPessoa"><fmt:message key = "br.cefetrj.sisgee.resources.form.tipoPessoa"/></label>
                                </div>

                                <div class="custom-controls-stacked d-block my-3">							
                                    <label class="custom-control custom-radio">
                                        <input id="tipoPessoa" class="custom-control-input isPessoaChk ${ not empty tipoPessoaMsg ? 'is-invalid' : '' }" ${ empty param.tipoPessoa ? 'required' :  "" }  type="radio" name="tipoPessoa"  value="cnpj" ${ not empty tipoPessoaMsg ? '' : param.tipoPessoa == 'cnpj' ? 'checked' : '' }> 
                                        <span class="custom-control-indicator"></span> 
                                        <span class="custom-control-description" ><fmt:message key = "br.cefetrj.sisgee.resources.form.cnpj"/></span>
                                    </label>						

                                    <label class="custom-control custom-radio">
                                        <input id="tipoPessoa" class="custom-control-input isPessoaChk ${ not empty tipoPessoaMsg ? 'is-invalid' : '' }" ${ empty param.tipoPessoa ? 'required' :  "" }  type="radio" name="tipoPessoa"  value="cpf" ${ not empty tipoPessoaMsg ? '' : param.tipoPessoa == 'cpf' ? 'checked' : '' }> 
                                        <span class="custom-control-indicator"></span> 
                                        <span class="custom-control-description"><fmt:message key = "br.cefetrj.sisgee.resources.form.cpf"/></span>
                                    </label>
                                    <c:if test="${ not empty tipoPessoaMsg }">
                                        <div class="invalid-feedback">${ tipoPessoaMsg }</div>
                                    </c:if>
                                </div>
                                    
                                
                            </div>
									
			<div class="form-row notAI AI" ${ empty param.tipoPessoa ? "style='display:none'" : param.tipoPessoa == "cpf" ? "style='display:none'" : "" }>
                            
				<div class="form-group col-md-6">
					<label for="cnpjConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.cnpj"/></label>
					<input type="text" class="form-control ${ not empty cnpjConvenioMsg ? 'is-invalid': 'is-valid' }"  ${ param.tipoPessoa == "cnpj" ? 'required' : "" } id="cnpjConvenio" name="cnpjConvenio"  value="${ param.cnpjConvenio }">
						<c:if test="${ not empty cnpjConvenioMsg }">
				    		<div class="invalid-feedback">${ cnpjConvenioMsg }</div>
		        		</c:if>
				</div>
				
                                <div class="form-group col-md-6">
					<label for="razaoSocial"><fmt:message key = "br.cefetrj.sisgee.resources.form.razaoSocial"/></label>
                                        <input type="text" class="form-control ${ not empty razaoSocialMsg ? 'is-invalid': 'is-valid' }" ${ param.tipoPessoa == "cnpj" ? 'required' : "" }  id="razaoSocial" name="razaoSocial" maxlength="100"  value="${ param.razaoSocial }">
						<c:if test="${ not empty razaoSocialMsg }">
				    		<div class="invalid-feedback">${ razaoSocialMsg }</div>
		        		</c:if>
				</div>
                                 
                                <div class="form-group col-md-6">
					<label for="pessoaContato"><fmt:message key = "br.cefetrj.sisgee.resources.form.pessoaContato"/></label>
                                        <input type="text" class="form-control ${ not empty pessoaContatoMsg ? 'is-invalid': 'is-valid' }" id="pessoaContato" name="pessoaContato" maxlength="100"  value="${ param.pessoaContato }">
						<c:if test="${ not empty pessoaContatoMsg }">
				    		<div class="invalid-feedback">${ pessoaContatoMsg }</div>
		        		</c:if>
				</div>                
                                                            
                            <div class="form-group col-md-4" >
					<label for="agenteIntegracao"><fmt:message key="br.cefetrj.sisgee.form_empresa.msg_agente_integracao" /></label>
                                        
                                        <div class="custom-controls-stacked d-block my-3">
					<label class="custom-control custom-radio"> 
						<input id="agenteSim" name="agenteIntegracao" type="radio" ${ param.tipoPessoa == "cnpj" ? 'required' : "" }  class="custom-control-input"  value = "sim" > 
						<span class="custom-control-indicator"></span> 
						<span class="custom-control-description" ><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_sim"/></span>
					</label> 
					<label class="custom-control custom-radio"> 
						<input id="agenteNao" name="agenteIntegracao" type="radio" class="custom-control-input"  value = "nao" > 
						<span class="custom-control-indicator"></span> 
						<span class="custom-control-description"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_nao"/></span>
					</label>
				</div>		
				</div>                                                        
                        </div> ${ param.tipoPessoa == "cnpj" ? "required" : "" }
                                     
                        <div class="form-row isAI AI" ${ empty param.tipoPessoa ? "style='display:none'" : param.tipoPessoa == "cnpj" ? "style='display:none'" : "" }>
                            
				<div class="form-group col-md-6">
					<label for="cpfConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.cpf"/></label>
					<input type="text" class="form-control ${ not empty cpfConvenioMsg ? 'is-invalid': 'is-valid' }"   ${ param.tipoPessoa == "cpf" ? 'required' : "" }  id="cpfConvenio" name="cpfConvenio"  value="${ param.cpfConvenio }">
						<c:if test="${ not empty cpfConvenioMsg }">
				    		<div class="invalid-feedback">${ cpfConvenioMsg }</div>
		        		</c:if>
				</div>
																			
				<div class="form-group col-md-6">
					<label for="nomePessoa"><fmt:message key = "br.cefetrj.sisgee.resources.form.nomePessoa"/></label>
                                        <input type="text" class="form-control ${ not empty nomePessoaMsg ? 'is-invalid': 'is-valid' }"  ${ param.tipoPessoa == "cpf" ? 'required' : "" }  id="nomePessoa" name="nomePessoa" maxlength="100"  value="${ param.nomePessoa }">
						<c:if test="${ not empty nomePessoaMsg }">
				    		<div class="invalid-feedback">${ nomePessoaMsg }</div>
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
					
			<button type="submit" class="btn btn-primary"  ><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_salvar"/></button>
			<button type="button" class="btn btn-secondary" onclick="javascript:location.href='index.jsp'"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_cancelar"/></button>
								
		</form>
	</div>
	<%@include file="import_footer.jspf"%>
	<%@include file="import_finalbodyscripts.jspf"%>
        <%@include file="import_scripts.jspf"%>
        <script>
            $(document).ready(function(){

                $('#telefone').mask('(99) 99999-9999');
                $('#cnpjConvenio').mask('99.999.999/9999-99');
                $('#cpfConvenio').mask('999.999.999-99');
                $('#dataAssinatura').mask('31/12/000');
                $('#dataAssinatura').mask('99/99/9999');
            });
        </script>