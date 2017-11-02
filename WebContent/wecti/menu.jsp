<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

	<!-- Bootstrap local -->
	<link rel="stylesheet" href="assets/bootstrap-3.3.7/css/bootstrap.min.css" />
	
	<script src="assets/jquery/jquery-3.1.1.min.js"></script>
	
    <%
	    if (session.getAttribute("status1") != null && (Integer) session.getAttribute("status1") == 1) {
    %>
    <script>
        alert("Cadastrado nas palestras e oficinas Com Sucesso!")
    </script>
    <%
            session.setAttribute("status1", 0);
        }
    %>
</head>
<body>
    <form id="mainform" action="CadastraPalestra" class="form-horizontal" method="POST" onsubmit="return confirm('Após Confirma não será possível alterar, você tem certeza?');" >
        <div class="container">
            <nav class="navbar navbar-light" style="background-color: #00BFFF;">
                <h4>Bem Vindo! ${usuario.nome} , Escolha Sua Palestra e Oficina!</h4>   
                <h6><a href="/Wecti/index.jsp" style="color: #f00"> Finalizar Sessão</a></h6>   
            </nav>
                
            <div id="loginbox" style="margin-top:50px" class="mainbox col-md-4 col-md-offset-4 col-sm-8 col-sm-offset-2">
                <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title">Palestra dia 02/05</div>
                    </div>
                    <div style="padding-top:0px" class="panel-body" >
                        <div class="input-group">
                            <div class="checkbox">
                                <div>
                                    <h2>Período Manhã</h2>
                                    <div>
                                    <label class="checkbox-inline"><input ${mapTemVaga.get("16") ? '' : 'disabled'} type="checkbox" name="palestraGeralM" value="16">Criatividade e Empreendedorismo Social / Cultura Maker -  8:00h às 11:00h  ${mapTemVaga.get("16")?'':'Sem Vagas'}</label>
                                    </div>
                                    <hr>
                                    <h2>Período Noite</h2>
                                    <div>
                                    	<label class="radio"><input ${mapTemVaga.get("17") ? '' : 'disabled'} type="radio" name="palestraGeralN" value="17">Internet das coisas - IOT -  19:45h às 21:00h ${mapTemVaga.get("17")?'':'Sem Vagas'}</label>
                                    	<label class="radio"><input ${mapTemVaga.get("18") ? '' : 'disabled'} type="radio" name="palestraGeralN" value="18">Internet das coisas - IOT -  21:15h às 22:30h ${mapTemVaga.get("18")?'':'Sem Vagas'}</label>
                                    </div>
                                 </div>
                                </div>
                            </div>
                        </div>
                    </div>  
                </div>
            </div>


            <div id="loginbox" style="margin-top:50px" class="mainbox col-md-4 col-md-offset-4 col-sm-8 col-sm-offset-2">
                <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title">Palestra dia 03/05</div>
                    </div>     

                    <div style="padding-top:0px" class="panel-body" >
                        <div class="input-group">
                            <div class="checkbox">
                                <div>
                                    <h2>Período Manhã</h2>
                                    <label class="checkbox-inline"><input ${mapTemVaga.get("1") ? '' : 'disabled'}  type="checkbox" name="palestra" value="1">Francisco Isidro - Quer aprender Arduino mas tem medo de programar? -  8:00 às 10:00  ${mapTemVaga.get("1")?'':'Sem Vagas'}</label>
                                </div>
                                <div>
                                    <label class="radio"><input ${mapTemVaga.get("2") ? '' : 'disabled'} type="radio" name="palestraM" value="2" >Arduladies - Mulheres em tech e wearables -  10:00 às 11:30   ${mapTemVaga.get("2")?'':'Sem Vagas'}</label>
                                </div>
                            </div>
                            <hr/>
                            <div>
                                <div class="radio">
                                    <h2>Período Noite</h2>
                                    <label class="radio"><input ${mapTemVaga.get("3") ? '' : 'disabled'} type="radio" name="palestraN" value="3">André Rovai - Big Data - 19:30 às 22:30 ${mapTemVaga.get("3")?'':'Sem Vagas'} </label>
                                    <label class="radio"><input ${mapTemVaga.get("4") ? '' : 'disabled'} type="radio" name="palestraN" value="4">Antonio Arimateia - Cidades Inteligentes - 19:30 às 22:30 ${mapTemVaga.get("4")?'':'Sem Vagas'} </label>
                                    <label class="radio"><input ${mapTemVaga.get("5") ? '' : 'disabled'} type="radio" name="palestraN" value="5">Rodrigo Dias - Machine Learning e Microsoft Azure  - 19:30 às 22:30 ${mapTemVaga.get("5")?'':'Sem Vagas'} </label>
                                    <label class="radio"><input ${mapTemVaga.get("6") ? '' : 'disabled'} type="radio" name="palestraN" value="6">Eduardo Hernacki - Gestão de Logs- 19:30 às 22:30 ${mapTemVaga.get("6")?'':'Sem Vagas'} </label>
                                    <label class="radio"><input ${mapTemVaga.get("7") ? '' : 'disabled'} type="radio" name="palestraN" value="7">Sandro Melo - Pentest - Teste de Intrusão  - 20:00 às 22:30 ${mapTemVaga.get("7")?'':'Sem Vagas'} </label>
                                </div>
                            </div>
                        </div>
                    </div>  
                </div>
            </div>

            <div id="loginbox" style="margin-top:30px" class="mainbox col-md-4 col-md-offset-4 col-sm-8 col-sm-offset-2">                    
                <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title">Oficina dia 04/05</div>
                    </div>     
                    <div style="padding-top:5px" class="panel-body" > <div class="input-group">
                            <div class="checkbox">
                                <div>
                                    <h2>Período Manhã</h2>
                                    <label class="radio"><input ${mapTemVaga.get("8") ? '' : 'disabled'} type="radio" name="oficinaM"value="8">Desenvolvendo Jogos com Unity - 08:00 às 11:00      ${mapTemVaga.get("8")?'':'Sem Vagas'}</label>
                                    <label class="radio"><input ${mapTemVaga.get("9") ? '' : 'disabled'} type="radio" name="oficinaM"value="9">Programando Páginas com Bootstrap - 08:00 às 11:00  ${mapTemVaga.get("9")?'':'Sem Vagas'} </label>                
                                    <label class="radio"><input ${mapTemVaga.get("10") ? '' : 'disabled'} type="radio" name="oficinaM"value="10">Robótica com Arduino - 08:00 às 11:00               ${mapTemVaga.get("10")?'':'Sem Vagas'} </label>
                                    <label class="radio"><input ${mapTemVaga.get("11") ? '' : 'disabled'} type="radio" name="oficinaM"value="11">Lógica de Programação com Scratch - 08:00 às 11:00  ${mapTemVaga.get("11")?'':'Sem Vagas'}</label>
                                </div>
                            </div>
                            <hr/>
                            <div class="radio">
                                <div>
                                    <h2>Período Noite</h2>
                                    <label class="radio"><input ${mapTemVaga.get("12") ? '' : 'disabled'} type="radio" name="oficinaN"value="12">Desenvolvendo Jogos com Unity - 19:30 às 22:30 ${mapTemVaga.get("12")?'':'Sem Vagas'} </label>
                                    <label class="radio"><input ${mapTemVaga.get("13") ? '' : 'disabled'} type="radio" name="oficinaN"value="13">Programando Páginas com Bootstrap - 19:30 às 22:30 ${mapTemVaga.get("13")?'':'Sem Vagas'} </label>
                                    <label class="radio"><input ${mapTemVaga.get("14") ? '' : 'disabled'} type="radio" name="oficinaN"value="14">Robótica com Arduino - 19:30 às 22:30 ${mapTemVaga.get("14")?'':'Sem Vagas'} </label>
                                    <label class="radio"><input ${mapTemVaga.get("15") ? '' : 'disabled'} type="radio" name="oficinaN"value="15">Lógica de Programação com Scratch - 19:30 às 22:30 ${mapTemVaga.get("15")?'':'Sem Vagas'} </label>
                                    
                                </div>
                            </div>
                        </div>  
                    </div> 
                </div>  
            </div>
        </div>

        <!-- fim 01 -->

        <!------------------------------------------------------------------------------------------------------------>

        <center>
            <div class="col-sm-12 controls">
                <button type="submit" class="btn btn-success" style="zoom: 2;">Confirmar</button>
            </div>
        </center>    

    </form>  


    <div style="padding-top:15px" class="form-group">
        <!-- Button -->
    </div>

    <!--<script>  
    var inputs = document.getElementById("mainform").getElementsByTagName("input");
    
    for(var i=0;i<inputs.length;i++){
        inputs[i].disabled = true;
    }
    </script>-->

</body>
</html>