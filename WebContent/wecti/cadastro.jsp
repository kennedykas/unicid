<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Cadastro</title>

	<!-- Bootstrap local -->
	<link rel="stylesheet" href="assets/bootstrap-3.3.7/css/bootstrap.min.css" />
	
	<script src="assets/jquery/jquery-3.1.1.min.js"></script>
	
	<%
		if (session.getAttribute("status") != null
				&& (Integer) session.getAttribute("status") == 1) {
	%>
	<script>
		alert("Cadastrado Com Sucesso!");
	</script>
	<%
		session.setAttribute("status", 0);
		}
	
		String usercpf = (String) session.getAttribute("cpf");
		String username = (String) session.getAttribute("nome");
		String useremail = (String) session.getAttribute("email");
	
		session.setAttribute("cpf", "");
		session.setAttribute("nome", "");
		session.setAttribute("email", "");
	%>
	
	<!-- Regra de validaçao -->
	
	<script type="text/javascript">
		function validarSenha() {
			NovaSenha = document.getElementById('senha').value;
			CNovaSenha = document.getElementById('conSenha').value;
			if (NovaSenha !== CNovaSenha) {
				alert("SENHAS DIFERENTES!\nDIGITAR SENHAS IGUAIS");
				return false;
			}
		}
	</script>
	

</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">

			<a class="navbar-brand">Bem vindo!</a>

		</div>
	</nav>
	<div id="main" class="container-fluid">

		<br />

		</hr>

		<h3 class="page-header">Identificação</h3>

		<form name="cadastroUsuario" action="ServletUsuario" method="POST">
			<input type="hidden" name="cmd"
				value="${usuario.status ne '0' ? 'incluir':'alterar'}"> <br />
			<div class="row">

				<div class="form-group col-md-3">
					<label>CPF/RGM</label> <input
						${usuario.status eq '0' ? 'readonly':''} type="text"
						class="form-control" name="cpf" id="cpf"
						placeholder="Apenas números, Ex 00011122233" required="required"
						pattern="[0-9]+$" maxlength="11" value=<%=usercpf%>>
				</div>

				<div class="form-group col-md-3">
					<label>Nome Completo</label> <input type="text"
						class="form-control" name="nome" id="nome"
						placeholder="Digite o Seu Nome Completo" required="required"
						maxlength="35" value="<%=username%>">
				</div>

				<div class="form-group col-md-3">
					<label>E-mail</label> <input type="text" class="form-control"
						name="email" id="email" placeholder="exemplo@exemplo.com"
						required="required"
						pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" maxlength="35"
						value=<%=useremail%>>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-3">
					<label>Senha</label> <input pattern=".{3,10}" type="password"
						class="form-control" name="senha" id="senha"
						placeholder="Sua senha aqui, mínimo de 3 caracteres"
						required="required">
				</div>

				<div class="form-group col-md-3">
					<label>Confirma senha</label> <input pattern=".{3,10}"
						type="password" class="form-control" name="conSenha" id="conSenha"
						placeholder="Confirme sua senha aqui" required="required">
				</div>
			</div>
			<hr />
			<br />
			<br />
			<br />
			<div class="row">
				<div class="col-md-12">
					<button type="submit" value="Enviar" class="btn btn-success"
						onClick="return validarSenha()">Cadastrar</button>
					<a href="faces/index.jsp" class="btn btn-danger">Cancelar</a>
                  
				</div>
			</div>
		</form>
</body>
</html>
