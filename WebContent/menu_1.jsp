<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<!-- Bootstrap local -->
	<link rel="stylesheet" href="assets/bootstrap-3.3.7/css/bootstrap.min.css" />
	
	<script src="assets/jquery/jquery-3.1.1.min.js"></script>
</head>
<body>
	<form id="mainform" action="CadastraPalestra" class="form-horizontal" method="POST">
		<div class="container">
			<nav class="navbar navbar-light" style="background-color: #00BFFF;">
				<h4>Bem Vindo! ${usuario.nome} , Escolha sua palestra e	oficina!</h4>
				<h6>
					<a href="/wecti/index.jsp" style="color: #f00"> 
						Finalizar Sessão
					</a>
				</h6>
			</nav>

			<div id="loginbox" style="margin-top: 50px" class="mainbox col-md-12 col-md-offset-0 col-sm-8 col-sm-offset-2">
				<div class="panel panel-info">
					<div class="panel-heading">
						<div class="panel-title">Minhas palestras cadastradas</div>
					</div>
					<div style="padding-top: 0px" class="panel-body">
						<div class="input-group">
							<div class="checkbox">
								<div>
									<h2>Você está cadastrado para:</h2>
									<div>
										<table>
											<tr bgcolor="#00BFFF">
												<th>Palestrante</th><th>Tema</th><th>Data e Horário</th>
											</tr>
											<c:forEach items="${listPalestra}" var="palestra">
											<tr>
												<td>${palestra.titulo}&nbsp;&nbsp;&nbsp;</td>
												<td>${palestra.descricao}&nbsp;&nbsp;&nbsp;</td>
												<td>${palestra.horario}</td>
											</tr>
										</c:forEach>
											<tr>
												<td colspan="3"><a href="faces/index.jsp" class="btn btn-danger">Voltar</a></td>
											</tr>

										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<!--<script>  
            var inputs = document.getElementById("mainform").getElementsByTagName("input");
            
            for(var i=0;i<inputs.length;i++){
                inputs[i].disabled = true;
            }
            </script>-->
</body>
</html>