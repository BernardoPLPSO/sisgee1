<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <%@include file="import_head.jspf"%>

        <title>
            <fmt:message key="br.cefetrj.sisgee.relatorio.relatorio_consolidado.title"/>	
        </title>
    </head>
    <body>
            <%@include file="import_navbar.jspf"%>
            
            <button type="button" class="btn btn-primary"><fmt:message key="br.cefetrj.sisgee.resources.form.confirmar" /></button>
            <button type="button" class="btn btn-primary" onclick="javascript:location.href = 'index.jsp'"><fmt:message key="br.cefetrj.sisgee.resources.form.cancelar" /></button>
            
            <%@include file="import_footer.jspf"%>
            <%@include file="import_finalbodyscripts.jspf"%>
    </body>
</html>
