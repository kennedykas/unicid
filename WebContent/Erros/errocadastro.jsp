<%-- 
    Document   : errocadastro
    Created on : 24/03/2017, 18:31:23
    Author     : davis
--%>

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

                        <img src="<%=request.getContextPath()%>/images/internet.jpg" alt="" class="">

                    </div>

                    <div class="content-wrap">

                        <div class="main-title">

                            <ul class="grid effect-8">

                                <h1>Usuário já cadastrado!</h1>
                                <h1>Tente novamente com outro CPF ou RGM.</h1>

                            </ul>

                            </br>

                            <div class= "lines">

                                <div class="more-btn">
                                    <a href="/wecti/cadastro.jsp" class="btn btn-danger">Tentar Novamente.</a>
                                </div>

                            </div>

                            </br></br>

                            <div class= "lines">

                                <div class="more-btn">
                                    <!-- <a href="/cadastro.jsp" class="btn btn-sucess">Cadastrar.</a>-->
                                </div>

                            </div>

                        </div>
                    </div>

                </div>
            </div>

        </div>
</body>
</html>