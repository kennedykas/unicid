<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Login</title>
	
	<!-- Bootstrap local -->
	<link rel="stylesheet" href="assets/bootstrap-3.3.7/css/bootstrap.min.css" />
	
	<%
		if (session.getAttribute("sucesso") != null
				&& (Integer) session.getAttribute("sucesso") == 1) {
	%>
	<script>
		alert("Acesse Novamente sempre que desejar, para ver suas palestras cadastradas!");
	</script>
	<%
		session.setAttribute("sucesso", 0);
		}
	%>

</head>
<body>
	<div class="container">

		<div id="loginbox" style="margin-top: 50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">Entrar (1º acesso a senha é o seu RGM)</div>
				</div>

				<div style="padding-top: 30px" class="panel-body">

					<div style="display: none" id="login-alert" class="alert alert-danger col-sm-12">
					</div>

					<form name="autenticacao" action="/wecti/LoginController" method="POST">
						<input type="hidden" name="cmd" value="autenticar"> <br />
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-user"></i>
							</span> 
							<input id="login" type="text" class="form-control" name="login"	placeholder="RGM ou CPF" required="required">
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-lock"></i>
								</span> 
								<input id="senhalogin" type="password" class="form-control" name="senhalogin" placeholder="Senha" required="required">
						</div>
						<div style="margin-top: 10px" class="form-group">
							<!-- Button -->
							<div class="col-sm-12 controls">
								<button type="submit" value="Enviar" class="btn btn-success">Login</button>
								<a href="faces/index.jsp" class="btn btn-danger">Voltar</a>
							</div>

							<hr />
							<hr />
						</div>
					</form>
						
						
						<div style="margin-top: 10px" class="form-group">
							<div class="col-sm-12 controls">
								<!-- <a id="btn-login" href="" class="btn btn-success">Login  </a> -->
								<a href="cadastro.jsp"><span class="glyphicon glyphicon-plus"></span> Sou visitante ou ex-aluno.</a> 
							</div>
						</div>
				</div>
			</div>
</body>
</html>
