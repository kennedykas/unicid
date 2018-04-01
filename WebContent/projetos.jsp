<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="template/header.jsp" />

<style>
	.page-projetos .hgroup .title{ font-family: 'Space Mono'; text-transform: uppercase;} 
	.page-projetos .hgroup hr{
    margin: 6px 0 auto auto;
    border-top: 2px solid #1ABC9C;
	}
	.page-projetos .hgroup hr.down{
    margin: 0 auto auto 0;
    width: 100px;
    border-top: 5px solid #1ABC9C;
}
.page-projetos .hgroup{
	margin-bottom:30px;
}
.btn-options{
	background:transparent;
	border:4px solid #1ABC9C;
	width:300px;
	height:100px;
	margin-bottom:30px;
	font-size:30px;
}
</style>

<div class="page-wrapper page-projetos">
	<div class="page-header">
		<h1 class="title">Nossos projetos</h1>
		<hr class="line">
	</div>

	<div class="container">
		<div class="hgroup">
			<h2 class="title">O que você é?</h2>
			<hr>
			<hr class="down">
		</div>
		<div class="option text-center">
			<div class="center-block">
				<button type="button" class="btn-options btn btn-secondary" onclick="window.location.href='faces/login/loginAluno.xhtml'">Aluno</button>
			</div>
			<div class="center-block">
				<button type="button" class="btn-options btn btn-secondary" onclick="window.location.href='faces/login/loginProfessor.xhtml'">Professor</button>
			</div>
		</div>
	</div>

</div>

<jsp:include page="template/footer.jsp" />