<!DOCTYPE html>
<html lang="en">
<head>

<%@include file="import_head.jspf"%>

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

		<p class="tituloForm">
		<h5><fmt:message key="br.cefetrj.sisgee.form_empresa.msg_titulo" /></h5>		
		
		<form action="ValidaCadastroEmpresaServlet" method="post">
			<fieldset class="form-group">
				<div class="form-row" >
				<div class="form-group col-md-3" >
					<label for="agenteIntegracao"><fmt:message key="br.cefetrj.sisgee.form_empresa.msg_agente_integracao" /></label>
				</div>

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
				<div class="form-group col-md-6">
					<label for="cnpjEmpresa"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_cnpj"/></label>
					<input type="text" class="form-control ${ not empty cnpjEmpresaMsg ? 'is-invalid': 'is-valid' }" id="cnpjEmpresa" name="cnpjEmpresa" required value="${ param.cnpjEmpresa }">
						<c:if test="${ not empty cnpjEmpresaMsg }">
				    		<div class="invalid-feedback">${ cnpjEmpresaMsg }</div>
		        		</c:if>
				</div>
																			
				<div class="form-group col-md-6">
					<label for="nomeEmpresa"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_razao_social"/></label>
                                        <input type="text" class="form-control ${ not empty nomeEmpresaMsg ? 'is-invalid': 'is-valid' }" id="nomeEmpresa" name="nomeEmpresa" maxlength="100" required value="${ param.nomeEmpresa }">
						<c:if test="${ not empty nomeEmpresaMsg }">
				    		<div class="invalid-feedback">${ nomeEmpresaMsg }</div>
		        		</c:if>
				</div>
                                                
                                                
                                                
                               
                                                
                   
                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.vigenciaEstagio"/></legend>
                    
                        <div class="form-group col-md-6">

                            <label for="dataInicioTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.dataInicio"/></label>
                            <input type="text" class="form-control col-sm-4 ${ not empty dataInicioMsg ? 'is-invalid': not empty periodoMsg ? 'is-invalid' : 'is-valid' }" id="dataInicioTermoEstagio"  name="dataInicioTermoEstagio" value="${ param.dataInicioTermoEstagio }" ${ not empty termoEstagio ? 'disabled' : '' } >
                            <c:if test="${ not empty dataInicioMsg }">
                                <div class="invalid-feedback">${ dataInicioMsg }</div>
                            </c:if>
                        </div>
                        
                   
               
                            
                            
                            
                            
                            
		</fieldset>
					
			<button type="submit" class="btn btn-primary"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_salvar"/></button>
			<button type="button" class="btn btn-secondary" onclick="javascript:location.href='index.jsp'"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_cancelar"/></button>
								
		</form>
	</div>
	<%@include file="import_footer.jspf"%>
	<%@include file="import_finalbodyscripts.jspf"%>
        <script>
            $(document).ready(function(){
                $('#cnpjEmpresa').mask('99.999.999/9999-99');
            });
        </script>
</body>