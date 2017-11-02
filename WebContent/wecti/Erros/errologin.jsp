<%@page isErrorPage="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
        <title>Erro de Cadastro</title>

		<!-- Bootstrap local -->
		<link rel="stylesheet" href="../assets/bootstrap-3.3.7/css/bootstrap.min.css" />
	
    </head>
    <body>

        <div class="container page-styling">
            <div class="header-wrapper">

                <div class="site-name">

                    <h1>ERRO</h1>

                </div>

                </br></br>

                <div class="banner">


                    <div class="item active">

                        <img src="<%=request.getContextPath()%>/imagem/internet.jpg" alt="" class="">

                    </div>

                    <div class="content-wrap">

                        <div class="main-title">

                            <ul class="grid effect-8">

                                <h1>Usuário ou senha inválidos!</h1>
                                <h1>Tente novamente ou cadastre-se.</h1>

                            </ul>

                            </br>

                            <div class= "lines">
                                <div class="more-btn">
                                    <a href="/wecti/loginAluno.jsp" class="btn btn-danger">Tentar Novamente.</a>
                                </div>
                            </div>

                            </br></br>

                            <div class= "lines">
                                <div class="more-btn">
                                    <a href="/wecti/cadastro.jsp" class="btn btn-success">Cadastrar.</a>
                                </div>
                            </div>

                        </div>
                    </div>

                </div>
            </div>
        </div>
<!--    
<center>
    <img src="<%=request.getContextPath()%>/imagem/internet.jpg" alt=""/>
    <h1>Usuário ou senha inválidos! Tente novamente ou cadastre-se.</h1>       
    <h2><a href="http://localhost:8084/Wecti/" class="btn btn-danger">Tentar Novamente.</a></h2>
    <h2><a href="http://localhost:8084/Wecti/cadastro.jsp" class="btn btn-danger">Cadastre-se.</a></h2>
    </hr>

    </hr>

</center>
-->
</body>
</html>
