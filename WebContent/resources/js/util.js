//---------------------------------
//           DOM READY
//---------------------------------
$(document).ready(function(){
	init();
});


//   AFTER CONTENT LOAD RECALL INIT FOR MATERIALIZE COMPONENTS
//---------------------------------------------------------------
function loadContent(content) {
	$(".mainContent").load(content,function() {
	  init();
	});
}

function init(){

	$('.sidenav')   .sidenav();
	$('select')     .formSelect();
	$('.modal')     .modal();

	$('.loaderContainer').hide();

	$('.number') .val(null); // the null here is to prevent the default value 0

	$('.cpf')    .mask('999.999.999-99');
	$('.date')   .mask('99/99/9999-99:99',{placeholder:'DD/MM/AAAA-HH:MM'});
	$('.celular').mask('(99) 99999-9999');
	$('.money').on( "keyup", function( event ) {

		// When user select text in the document, also abort.
		var selection = window.getSelection().toString();
		if ( selection !== '' )
			return;

		// When the arrow keys are pressed, abort.
		if ( $.inArray( event.keyCode, [38,40,37,39] ) !== -1 )
			return;

		var $this = $( this );

		var input = $this.val();

		var input = input.replace(/[\D\s\._\-]+/g, "");
		input = input ? parseInt( input, 10 ) : 0;

		$this.val( function() {
			return ( input === 0 ) ? "" : input.toLocaleString( "pt-BR" );
		});
	});

	// facemessage links (making they work)
	$(".esc").each(function(i) {
		var h = $(this).html();
		h = h.replace(/&lt;/gi, "<");
		h = h.replace(/&gt;/gi, ">");
		$(this).html(h);
	});

	// email field (.emailField)
	var emailRegex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	$('.emailField').on('change', function() {
		if (emailRegex.test($(this).val())) {
			$(this).addClass('valid').removeClass('invalid');
			$('button.validate').prop("disabled", false);
		} else
			$(this).addClass('invalid').removeClass('valid');
	});

	// DATE PICKER
	var options = {
		format   : 'dd/mm/yyyy',
		onSelect : 'instance.close()',
		i18n     : {
			'cancel' : 'CANCELAR',
			'months' : [
				'Janeiro',
				'Fevereiro',
				'Março',
				'Abril',
				'Maio',
				'Junho',
				'Julho',
				'Agosto',
				'Setembro',
				'Outubro',
				'Novembro',
				'Dezembro'
			],
			'monthsShort' : [
				'Jan',
				'Fev',
				'Mar',
				'Abr',
				'Mai',
				'Jun',
				'Jul',
				'Ago',
				'Set',
				'Out',
				'Nov',
				'Dez'
			],
			'weekdaysShort' : [
				'Dom',
				'Seg',
				'Ter',
				'Qua',
				'Qui',
				'Sex',
				'Sab'
			],
			'weekdaysAbbrev' : ['D','S','T','Q','Q','S','S']
		}
	};
	var $dateElems = $('.datepicker');
	M.Datepicker.init($dateElems, options);


	// TIME PICKER
	var timerOptions = { 'twelveHour' : false };
	var $timerElems = $('.timepicker');
	M.Timepicker.init($timerElems, timerOptions);


	// SET THE CURRENT PAGE AS SELECTED ON NAVBAR
	$('.navItem').find('.active').removeClass('active');

	var activeWindow = $('.mainContent').data('content');

	$('.navItem').each(function(){

		if ($(this).data('page') == activeWindow)
			$(this).addClass('active');
	});

	// SUCCESS MESSAGES
	var toastMessage = new URL(window.location.href).searchParams.get("toast");
	M.toast({html: toastMessage});

	M.updateTextFields();

	M.ScrollSpy.init($('.scrollspy'));

	M.FloatingActionButton.init($('.fixed-action-btn'), {'direction' : 'left'});

    M.Tooltip.init($('.tooltipped'));
}

//        SHOW PAGE LOADING ANIMATION
//---------------------------------------------
$(".loading").on("submit", function() {
	$('.loaderContainer').fadeIn(2800);
});


/**
 * Valida CPF
 *
 */
$(".cpf").on("keyup", function(){
	// EXTRAI APENAS OS NUMEROS
	cpf = $(".cpf").val().match(/\d/g).join("");

	// CHECA OS CPFs INVALIDOS MAIS COMUNS
    if (cpf == '' || cpf.length != 11 || cpf == "00000000000" || cpf == "11111111111" || cpf == "22222222222" || cpf == "33333333333" ||	cpf == "44444444444" || cpf == "55555555555" ||	cpf == "66666666666" || cpf == "77777777777" ||	cpf == "88888888888" || cpf == "99999999999") {
    	$('.cpf').addClass('invalid');
    	return false;
    }
    // VALIDA O PRIMEIRO DIGITO
    add = 0;
    for (i=0; i < 9; i ++)
        add += parseInt(cpf.charAt(i)) * (10 - i);
        rev = 11 - (add % 11);
        if (rev == 10 || rev == 11)
            rev = 0;
        if (rev != parseInt(cpf.charAt(9))) {
        	$('.cpf').addClass('invalid');
        	return false;
        }
    // VALIDA O SEGUNDO DIGITO
    add = 0;
    for (i = 0; i < 10; i ++)
        add += parseInt(cpf.charAt(i)) * (11 - i);
    rev = 11 - (add % 11);
    if (rev == 10 || rev == 11)
        rev = 0;
    if (rev != parseInt(cpf.charAt(10))) {
    	$('.cpf').addClass('invalid');
    	return false;
    }
    // CASO ESTEJA TUDO CORRETO
    $('.cpf').removeClass('invalid').addClass('valid');
    return true;
});


//     CHECA SE PASSWORDS CORRESPONDEM NOS DOIS CAMPOS (newUsers)
//---------------------------------------------------------------------------
$(".password").on("keyup", function(){
	if($('.password1').val() != $('.password2').val()) {
		$('.password1, .password2').addClass('invalid');
		$('.submit').prop("disabled",true);
	} else {
		$('.password1, .password2').removeClass('invalid').addClass('valid');
		$('.submit').prop("disabled",false);
	}
});


//         MOSTRAR CAMPO INTEGRANTE (novoGrupo.xhtml)
//--------------------------------------------------------------
$(".showNextMemberField").on("click", function(){

	// make the input take all available space
	$("." + $(this).data("next") + " > input").removeClass('s10');

	// store the next input class in the button
	$(this).data("next", $(this).data("next") + 1);

	// show the next field
	$("." + $(this).data("next")).show();
});


//             TIMER (listaProvaAluno.xhtml)
//---------------------------------------------------------
var seconds = 0;
var minutes = 0;
var minutes = $("#showProva\\:minutes").val();
$("#showProva\\:countdown").text(minutes);
function display() {
	if (seconds <= 0) {
		seconds = 60;
		minutes -= 1;
	}
	if (minutes <= -1) {
		seconds = 0;
		minutes += 1;
		alert("Seu tempo acabou as suas respostas foram salvas!")
		window.location.href="gabaritoAluno.xhtml";
	}
	else
		seconds -= 1;
	$("#showProva\\:countdown").text(minutes + ":" + seconds);
	setTimeout("display()",1000);
}
display();

//        PAGE AUTO RELOAD (listaProvasAluno.xhtml)
//---------------------------------------------------------
setInterval(function() {
    	$('#listaProvasAluno\\:refreshTable').click();
	}, 55000
);

//               FILTRO CURSOS (listaDisciplinas.xhtml)               MANUTENCAO
//----------------------------------------------------------------------
$(".filtrarCurso").on("click", function(){
	var cursoSelecionado = $("select option:selected").text(); // OBTENHO CURSO SELECIONADO
	$("tbody tr").hide(300); // ESCONDO TODOS OS CURSOS
	$("td").each(function() {
	    if ($(this).text().indexOf(cursoSelecionado) >= 0)  // MOSTRO APENAS AS LINHAS QUE CONTEM O CURSO SELECIONADO
	    	$(this).parents("tr").show(300);
	});
});

//      LIMPAR FILTRO (listaDisciplinas.xhtml, listaCursos.xhtml)
//----------------------------------------------------------------------
$(".limparFiltros").on("click", function(){
	$("tr").show(300);
});
