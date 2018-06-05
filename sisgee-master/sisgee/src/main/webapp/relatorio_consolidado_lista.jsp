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

        <c:forEach items="${ relatorioIm }" var="relatorioIm">
            <table class="table table-hover" style="width: 100%; margin-top: 20px;">
                <thead>
                    <tr>
                        <th>${ relatorioIm.nomeCurso }</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>		
                    <tr>
                        <td><fmt:message key="br.cefetrj.sisgee.relatorio.relatorio_consolidado.tabela_resultado.qnt_termoestagio"/></td>
                        <td>${ relatorioIm.qntTermoEstagio }</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="br.cefetrj.sisgee.relatorio.relatorio_consolidado.tabela_resultado.qnt_termoaditivo"/></td>
                        <td>${ relatorioIm.qntTermoAditivo }</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="br.cefetrj.sisgee.relatorio.relatorio_consolidado.tabela_resultado.qnt_rescisao" /></td>
                        <td>${ relatorioIm.qntRescReg }</td>
                    </tr>
                </tbody>
            </table>
        </c:forEach>

        <button type="button" class="btn btn-primary"><fmt:message key="br.cefetrj.sisgee.resources.form.confirmar" /></button>
        <button type="button" class="btn btn-primary" onclick="javascript:location.href = 'index.jsp'"><fmt:message key="br.cefetrj.sisgee.resources.form.cancelar" /></button>

        <%@include file="import_footer.jspf"%>
        <%@include file="import_finalbodyscripts.jspf"%>
    </body>
</html>
