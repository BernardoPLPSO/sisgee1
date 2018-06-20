<!DOCTYPE html>
<html lang="en">
    <head>

        <%@include file="import_head.jspf"%>

        <title>
            <fmt:message key = "br.cefetrj.sisgee.resources.form.registroTermoAditivo"/>
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
            <h5>		
                <fmt:message key = "br.cefetrj.sisgee.resources.form.registroTermoAditivo"/>
            </h5>		

            <form action=BuscaTermoAditivoServlet method="post">

                <fieldset class="form-group dadosAluno" >

                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.dadosAluno"/></legend>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="matricula"><fmt:message key = "br.cefetrj.sisgee.resources.form.matricula"/></label>
                            <div class="input-group">
                                <input type="hidden" id="idAluno" name="idAluno" value="${ param.idAluno }">
                                <input type="text" maxlength="100" class="form-control ${ not empty idAlunoMsg ? 'is-invalid': 'is-valid' }" placeholder="<fmt:message key = "br.cefetrj.sisgee.import_busca_aluno.placeholder_matricula"/>" id="matricula" name="matricula" value="${ param.matricula }">
                                <span class="input-group-btn"> 
                                    <button class="btn btn-primary" type="button" id="btnBuscarMatriculaAditivo"><fmt:message key = "br.cefetrj.sisgee.resources.form.buscar"/></button>
                                </span>

                                <c:if test="${ not empty idAlunoMsg }">
                                    <div class="invalid-feedback">${ idAlunoMsg }</div>
                                </c:if>

                            </div>					    
                        </div>
                        <div class="form-group col-md">
                            <label for="nome"><fmt:message key = "br.cefetrj.sisgee.resources.form.nome"/></label>
                            <input type="text" class="form-control" id="nome" name="nome" value="${ param.nome }" readonly>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="nomeCurso"><fmt:message key = "br.cefetrj.sisgee.resources.form.curso"/></label>
                            <input type="text" class="form-control" id="nomeCurso"  name="nomeCurso" value="${ param.nomeCurso }" readonly>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="nomeCampus"><fmt:message key = "br.cefetrj.sisgee.resources.form.unidade"/></label>
                            <input type="text" class="form-control" id="nomeCampus"  name="nomeCampus" value="${ param.nomeCampus }" readonly>
                        </div>
                    </div>
                </fieldset>
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
        <script type="text/javascript">
            function hablitarButoes() {
                $("#btnNovoAditivo").prop("disabled", false);
                $("#btnNovoAditivo").removeClass("btn-secondary");
                $("#btnNovoAditivo").addClass("btn-primary");
                $("#btnListarAditivo").prop("disabled", false);
                $("#btnListarAditivo").removeClass("btn-secondary");
                $("#btnListarAditivo").addClass("btn-primary");
            }
            var buscarAlunoCallback = function myCallback(json) {
                if (json != null) {
                    if (json.idTermoEstagioAtivo != null && json.idTermoEstagioAtivo != "") {
                        //atribui o id do termo de estágio para o campo hidden
                        //tem termo de estágio, ativa os botões
                        hablitarButoes();
                    } else {
                        //não tem termo de estágio
                    }
                }
            }
        </script>
        <%@include file="import_scripts.jspf"%>
        <script type="text/javascript">
            $(document).ready(function () {
                $(".form-check-input").change(function () {
                    $('#idAlunoAdt').val($("#idAluno").val());
                });
                if ($("#idAluno").val() != "") {
                }
            });
        </script>

    </body>
</html>