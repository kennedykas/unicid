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

                                <h1>ERRO 404!</h1>
                                <h1>Página não encontrada.</h1>

                            </ul>

                            </br>
                            
                            <form name="erroform" action="LoginController" method="POST">
                            <input type = "hidden" name ="cmd" value="erro">
                            <div class= "lines">

                                <div class="more-btn">
                                     <a href="/wecti/index.jsp" class="btn btn-danger">Voltar ao Início</a>
                                </div>

                            </div>
                                
                            </form>    

                            </br></br>

                            <div class= "lines">

                                <div class="more-btn">
                                    <!--<a href="wecti/cadastro.jsp" class="btn btn-sucess">Cadastrar.</a>-->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
