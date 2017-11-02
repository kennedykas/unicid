<%@page contentType="text/html" pageEncoding="UTF-8"%>

<footer class="footer">
	<div class="container">
		<div class="row">
			<div class="col-md-4 text-center">
				<div class="text-block">
					<h2>Está com dúvidas?</h2>
					<br>
					<p>
						Envie um email que em breve entraremos em contato e poderemos trocar um papo
					</p>
					<br>
					<a href="" class="btn btn-success btn-orcamento waves-effect" disabled>
						jadir.mendonca@unicid.edu.br
					</a>
				</div>
			</div>
			<div class="col-md-4 text-center">
				<div class="text-block">
					<h2>Localização</h2>
					<p>São Paulo - SP</p>
					<p>
						<small>R. Cesário Galero, 448/475 - Tatuapé, São Paulo</small>
					</p>
					<a href="https://www.google.com/maps/place/Universidade+Cidade+de+S%C3%A3o+Paulo+-+UNICID/@-23.536077,-46.559463,16z/data=!4m5!3m4!1s0x0:0xfc42f6294fa0f613!8m2!3d-23.5360771!4d-46.5594631?ll=-23.536077,-46.559463&z=16&t=m&hl=pt-BR&gl=BR&mapclient=embed&cid=18177361703310784019" target="_blank" class="btn btn-link btn-rounded waves-effect">
						Ver mapa <i class="fa fa-map-marker" aria-hidden="true"></i>
					</a>
				</div>
			</div>
			<div class="col-md-4 text-center">
				<div class="text-block">
					<h2>Social</h2>
					<p>
						Siga-nos na maior rede social do mundo
					</p>
					<h2>
						<a href="https://facebook.com/wecti3">
							<i class="ico-social fa fa-facebook" aria-hidden="true"></i>
						</a>
					</h2>
				</div>
			</div>
		</div>
	</div>
</footer>


<!-- MODAL -->

<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="modal">
  	<div class="modal-dialog" role="document">
    	<div class="modal-content">
    		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
    		</button>
	    	<div class="modal-header">
	    		<h4 class="subtitle text-center">PREENCHA O FORMUL&Aacute;RIO</strong></h4>
	    	</div>
	    	<form action="enviar-cotacao.php" method="POST">
	    		<div class="modal-body">
	    			<div class="form-group">
	    				<input type="text" class="form-control" id="plano" name="plano" readonly="" required="">
					</div>
					<div class="form-group">
						<input type="text" name="nome" placeholder="Nome" class="form-control input-lg" required="">
					</div>
				<div class="col-md-6" style="padding-left:0px;">
					<div class="form-group">
						<input type="text" name="telefone" placeholder="Telefone" class="form-control input-lg" required="">
					</div>
				</div>
				<div class="col-md-6" style="padding-right:0px;">
					<div class="form-group">
						<input type="text" name="celular" placeholder="Celular" class="form-control input-lg"  required="">
					</div>
				</div>
					<div class="form-group">
						<input type="text" name="email" placeholder="E-mail" class="form-control input-lg"  required="">
					</div>
					<div class="text-center">
						<button type="submit" class="btn-td text-center">
							ENVIAR
						</button>
					</div>
				</div>
	    	</form>
    	</div>
  	</div>
</div>



<!-- script whatever -->

<script type="text/javascript">
	$('#modal').on('show.bs.modal', function (event) {
		var button = $(event.relatedTarget); // Button that triggered the modal
		var recipient = button.attr('data-whatever'); // Extract info from data-* attributes
		// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
		// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
		var modal = $(this);
		modal.find('.modal-title').text('Plano ' + recipient);
		modal.find('.modal-body #plano').val(recipient);	  
	});
</script>