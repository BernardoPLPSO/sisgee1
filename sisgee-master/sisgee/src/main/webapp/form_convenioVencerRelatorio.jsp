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
		
								
		
                
                         
                <c:if test="${ not empty Resultado }">
                <table class="table table-hover" style="width: 100%; margin-top: 20px;">
  			<thead>
  				<tr>
                                        <th>Vigencia</th>
  					<th>Numero do Convênio</th>
  					<th>Nome / Razão Social</th>
                                        <th>CPF / CNPJ</th>
                                        <th>Email</th>
                                        <th>Telefone</th>
                                        <th>Pessoa de Contato</th>
  				</tr>
  			</thead> 
                        <tbody>
		<c:forEach items="${ Resultado }" var="Resultados" varStatus="status" >
                    
                    <tr>
                        <th>${Resultados.dataFormatada}</th>
			<th>${Resultados.numeroConvenio}</th>
                        <th>${Resultados.nomeConvenio}</th>
                        <th>${Resultados.idConvenio}</th>
                        <th>${Resultados.email}</th>
                        <th>${Resultados.telefone}</th>
                        <th>${Resultados.pessoaContato}</th>
                    </tr>                                                                                                                                         
	  				
                </c:forEach>                          
  			</tbody>
  		</table>
                <button type="button" class="btn btn-secondary" onclick="javascript:location.href='index.jsp'"><fmt:message key = "br.cefetrj.sisgee.form_empresa.msg_cancelar"/></button>
  		   </c:if> 
                
	</div>
	<%@include file="import_footer.jspf"%>
	<%@include file="import_finalbodyscripts.jspf"%>
        <%@include file="import_scripts.jspf"%>
        <script>
            $(document).ready(function(){

                $('#buscaNumero').mask('999999');
                
            });
        </script>