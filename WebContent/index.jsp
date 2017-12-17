<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="template/header.jsp" />
<div class="intro-header">
	<div class="container">
		<div class="hgroup wow lightSpeedIn text-center" data-wow-duration="2s">
				<h1 class="title"><span>Pós Graduação e Extensão</span></h1>
			<h2 class="subtitle"><span>Destaque-se</span> no mundo do trabalho!</h2>
			<a class="hvr-sweep-to-right wow pulse btn btn-default" data-wow-delay="2.5s" href="create/novaEntrevista.xhtml" target="_blank">		Pós Graduação: Agende uma Entrevista
			</a>
			<a class="hvr-sweep-to-right wow pulse btn btn-default" data-wow-delay="2.5s" href="create/novoCursoExtensao.xhtml" target="_blank">		Cursos de Extensão: Promoção Black Friday
			</a>
		</div>
		<div class="text-center">
			<img src="images/mouse.png" title="Desca!" alt="Desca!" class="floating mouse">
		</div>
	</div>
</div>

<!-- Pós Graduacao -->
<div id="pos" class="section-palestrantes">
	<div class="container">
		<h2 class="s-title text-center wow shake" data-wow-duration="2s">
			Pós Graduação
		</h2>
		<hr class="line">

		<ul class="palestrantes-group owl-carousel owl-theme">	
		<li class="item">
				<a href="big-data.jsp">
					<div class="thumbnail">
						<img src="images/palestrantes/big-data.png" class="img-circle img-thumbnail" width="150px">
						<h3 class="item-title">
							Big Data<br />&nbsp;
						</h3>

					</div>
				</a>
			</li>
			<li class="item">
				<a href="iot.jsp">
					<div class="thumbnail">
						<img src="images/palestrantes/iot.png" class="img-circle img-thumbnail" width="150px">
						<h3 class="item-title">
							Aplicações Corporativas JAVA e Internet das Coisas
						</h3>

					</div>
				</a>
			</li>
			<li class="item">
				<a href="engenharia.jsp">
					<div class="thumbnail">
						<img src="images/palestrantes/es.jpg" class="img-circle img-thumbnail" width="150px">
						<h3 class="item-title">
							Engenharia de Software<br />&nbsp;
						</h3>
					</div>
				</a>
			</li>
			<li class="item">
				<a href="governanca.jsp">
					<div class="thumbnail">
						<img src="images/palestrantes/gov.jpg" class="img-circle img-thumbnail" width="150px">
						<h3 class="item-title">
							Governança de TI<br />&nbsp;
						</h3>
					</div>
				</a>
			</li>
		</ul>
		<script type="text/javascript">
			$('.palestrantes-group').owlCarousel({
				loop:true,
				margin:10,
				nav:false,
				responsive:{
					0:{
						items:1
					},
					600:{
						items:3
					}
				}
			});
		</script>
	</div>
</div>

<div class="section-programacao">
	<h2 class="s-title text-center wow flipInX">
		Informações
		<hr class="line">
		<br>
	</h2>
	<div class="row">
		<div class="card-programacao col-md-3">
			<div class="card-image" style="background-image: url(images/calendar.jpg);">
			</div>

			<div class="card-content">
				<div class="card-content-wrapper">
					<h2 class="wow rubberBand card-title" data-wow-delay="1s">
						Agenda 
					</h2>

					<!-- <p class="card-desc"></p> -->

					<a href="agenda.jsp" class="hvr-bounce-to-top btn btn-default">
						Ver mais
					</a>
				</div>
			</div>
		</div>
		<div class="card-programacao col-md-3">
			<div class="card-image" style="background-image: url(images/palestras.jpg);">
			</div>

			<div class="card-content">
				<div class="card-content-wrapper">
					<h2 class="wow rubberBand card-title" data-wow-delay="1.5s">
						Certificações
					</h2>

					<!-- <p class="card-desc"></p> -->

					<a href="certificacao.jsp" class="hvr-bounce-to-top btn btn-default">
						Ver mais
					</a>
				</div>
			</div>
		</div>
		<div class="card-programacao col-md-3">
			<div class="card-image" style="background-image: url(images/custo.jpg);">
			</div>

			<div class="card-content">
				<div class="card-content-wrapper">
					<h2 class="wow rubberBand card-title" data-wow-delay="2s">
						Valores
					</h2>

					<!-- <p class="card-desc"></p> -->

					<a href="valores.jsp" class="hvr-bounce-to-top btn btn-default">
						Ver mais
					</a>
				</div>
			</div>
		</div>
		<div class="card-programacao col-md-3">
			<div class="card-image" style="background-image: url(images/hand-on.jpg);">
			</div>

			<div class="card-content">
				<div class="card-content-wrapper">
					<h2 class="wow rubberBand card-title" data-wow-delay="2.5s">
						Coordenação
					</h2>

					<!-- <p class="card-desc"></p> -->

					<a href="coordenador.jsp" class="hvr-bounce-to-top btn btn-default">
						Ver mais
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Extensao -->
<div id="extensao" class="section-palestrantes">
	<div class="container">
		<h2 class="s-title text-center wow shake" data-wow-duration="2s">
			Cursos de Extensão
		</h2>
		<hr class="line">

		<ul class="palestrantes-group owl-carousel owl-theme">	
			<li class="item">
				<a href="itil.jsp">
					<div class="thumbnail">
						<img src="images/palestrantes/itil.jpg" class="img-circle img-thumbnail" width="150px">
						<h3 class="item-title">
							ITIL<br />&nbsp;
						</h3>

					</div>
				</a>
			</li>
			<li class="item">
				<a href="scrum.jsp">
					<div class="thumbnail">
						<img src="images/palestrantes/scrum.png" class="img-circle img-thumbnail" width="150px">
						<h3 class="item-title">
							SCRUM<br />&nbsp;
						</h3>

					</div>
				</a>
			</li>
			<li class="item">
				<a href="seguranca.jsp">
					<div class="thumbnail">
						<img src="images/palestrantes/si.jpg" class="img-circle img-thumbnail" width="150px">
						<h3 class="item-title">
							ISO 27002   Seg.Informação
						</h3>

					</div>
				</a>
			</li>
			<li class="item">
				<a href="cobit.jsp">
					<div class="thumbnail">
						<img src="images/palestrantes/cobit.jpg" class="img-circle img-thumbnail" width="150px">
						<h3 class="item-title">
							COBIT 5<br />&nbsp;
						</h3>

					</div>
				</a>
			</li>
			<li class="item">
				<a href="java-web.jsp">
					<div class="thumbnail">
						<img src="images/palestrantes/java.png" class="img-circle img-thumbnail" width="150px">
						<h3 class="item-title">
							Java WEB<br />&nbsp;
						</h3>
					</div>
				</a>
			</li>
			<li class="item">
				<a href="jsf.jsp">
					<div class="thumbnail">
						<img src="images/palestrantes/jsf.jpg" class="img-circle img-thumbnail" width="150px">
						<h3 class="item-title">
							Java Server Faces<br />&nbsp;
						</h3>
					</div>
				</a>
			</li>
			<li class="item">
				<a href="prime.jsp">
					<div class="thumbnail">
						<img src="images/palestrantes/prime.jpg" class="img-circle img-thumbnail" width="150px">
						<h3 class="item-title">
							PrimeFaces<br />&nbsp;
						</h3>
	
					</div>
				</a>
			</li>

		</ul>
		<script type="text/javascript">
			$('.palestrantes-group').owlCarousel({
				loop:true,
				margin:10,
				nav:false,
				responsive:{
					0:{
						items:1
					},
					600:{
						items:3
					},
					1000:{
						items:4
					}
				}
			});
		</script>
	</div>
</div>


<div id="sobre" class="section-sobre">
	<div class="container">
		<div class="col-md-5">
			<div class="box-sobre wow fadeInUp col-md-12">
				<h2 class="s-title"><span>Pós Graduação e Extensão</span></h2>
				<h3>É assim que você se destaca no<span> Mundo do trabalho</span></h3>
				<p align="justify">Você está convidado a participar de um dos nossos cursos em tecnologia da Informação. 
				Nossa meta é contribuir com o desenvolvimento de profissionais de Ti e estudantes que buscam crescimento profissional.</p>
			</div>
		</div>
		<div class="col-md-7">
			<img src="images/jadir.jpg" width="840px">
		</div>
	</div>
</div>


<jsp:include page="template/footer.jsp" />