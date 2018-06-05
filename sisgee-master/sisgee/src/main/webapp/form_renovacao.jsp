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
		
                
                 <c:choose>
            <c:when test="${ not empty Resultado }">
                <form action="RenovaConvenioServlet" method="post">
                </c:when>
                <c:otherwise>
                    <form action="BuscaConvenioServlet" method="post">
                    </c:otherwise>
                </c:choose>
		
			<fieldset class="form-group">
                                
                             <!-- 
                            <div class="form-group col-md-4">
        <label for="numeroConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.numeroConvenio"/></label>
        <div class="input-group">						  
            <input type="text" class="form-control numeroConvenio" placeholder="Digite o Número" id="numeroConvenio" name="numeroConvenio" value="${ param.numeroConvenio }">
            <span class="input-group-btn">
                <button class="btn btn-primary" type="button" id="btnBuscarNumeroConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.buscar"/></button>
            </span>
        </div>
    </div>
    <div class="form-group col-md-6">
        <label for="nomeConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.nomeConvenio"/></label>
        <div class="input-group">						  
            <input type="text" class="form-control nomeConvenioNotAI nomeConvenio" placeholder="Digite o Nome" id="nomeConvenio" name="nomeConvenio" value="${ param.nomeConvenio }">
            <span class="input-group-btn">
                <button class="btn btn-primary" type="button" id="btnBuscarNomeConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.buscar"/></button>
            </span>
                            -->
                            
                            
                            
                            
                            
                                              
                                <div class="form-group col-md-6">
					<label for="buscaNumero"><fmt:message key = "br.cefetrj.sisgee.resources.form.busca.numeroConvenio"/></label>
                                        <input type="search" placeholder="Insira o Numero do Convênio a Ser Encontrado Sem o Ano" class="form-control ${ not empty buscaNumeroMsg ? 'is-invalid': 'is-valid' }" id="numeroConvenio" name="buscaNumero" maxlength="6" value="${ param.buscaNumero }">
						<c:if test="${ not empty buscaNumeroMsg }">
				    		<div class="invalid-feedback">${ buscaNumeroMsg }</div>
		        		</c:if>
				</div>
                                             
                                <div class="form-group col-md-6">
					<label for="buscaRazaoNome"><fmt:message key = "br.cefetrj.sisgee.resources.form.busca.razaoNome"/></label>
                                        <input type="search" placeholder="Insira o Nome ou Razão Social a Ser Encontrada" class="form-control  ${ not empty buscaRazaoNomeMsg ? 'is-invalid': 'is-valid' }" id="nomeConvenio" name="buscaRazaoNome" maxlength="100" value="${ param.buscaRazaoNome }">
						<c:if test="${ not empty buscaRazaoNomeMsg }">
				    		<div class="invalid-feedback">${ buscaRazaoNomeMsg }</div>
                                                
		        		</c:if>
				</div>  
                                                
                  
                            
		</fieldset>
					
			<button type="submit" class="btn btn-primary"  ><fmt:message key = "br.cefetrj.sisgee.resources.form.busca.buscar"/></button>
			<button type="button" class="btn btn-secondary" onclick="javascript:location.href='index.jsp'"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_cancelar"/></button>
								
		</form>
	</div>
	<%@include file="import_footer.jspf"%>
	<%@include file="import_finalbodyscripts.jspf"%>
        <%@include file="import_scripts.jspf"%>
        <script>
            $(document).ready(function(){

                $('#buscaNumero').mask('999999');
                
            });
        </script>