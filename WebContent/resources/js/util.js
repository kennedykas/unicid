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
	$('.tooltipped').tooltip();
	 
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
	var elem = document.querySelector('.datepicker');
	var instance = M.Datepicker.init(elem, options);
	
	// TIME PICKER
	$('.timepicker').timepicker({
	    default      : 'now',
	    fromnow      : 0,         // set default time to * milliseconds from now (using with default = 'now')
	    twelvehour   : false,     // Use AM/PM or 24-hour format
	    donetext     : 'OK',
	    cleartext    : 'Limpar',
	    canceltext   : 'Cancelar',
	    autoclose    : true,
	    ampmclickable: true
	});
	
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
}

//        SHOW PAGE LOADING ANIMATION
//---------------------------------------------
$(".loading").on("submit", function() {
	$('.loaderContainer').fadeIn(2800);
});


//          UPDATE QUESTIONS COUNTER
//
//	@usedIn listaQuestoes.xhtml
//---------------------------------------------
function updateCounter() {
	
	// get all selected questions
	var numberOfSelectedQuestions = 
		document.getElementsByClassName('bordered')[0]
			.querySelectorAll('input[type=checkbox]:checked').length;
	
	// get the counter element
	var counter = document.getElementById('selectedQuestionsCounter');
	
	counter.innerHTML = numberOfSelectedQuestions;
	
	if(numberOfSelectedQuestions > 0)
		counter.style.display = 'block';
	else
		counter.style.display = 'none';
	
}

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


/**
 * Set the selected date and time on the hidden input
 */
var data;
var horario;

$('#date').on('change', function() {
	
	var dateAux = $(this).val().replace(" ", " ").replace(", ", " ").split(" ");
	
	data = dateAux[0] + "/" + getMonthFromString(dateAux[1]) + "/" +dateAux[2];
	
	if(horario != undefined)
		$("#testInfo\\:dateAndTime").val(data + "-" + horario);
});

$('#schedule').on('change', function() {
	
	horario = $(this).val();
	
	if(data != undefined)
		$("#testInfo\\:dateAndTime").val(data + "-" + horario);
});

function fillDateAndTime() {
	
	var dateAndTime = $("#testInfo\\:dateAndTime").val().split("-");
	
	data = dateAndTime[0];
	horario = dateAndTime[1];
	
	$("#date").val(dateAndTime[0]);
	$("#schedule").val(dateAndTime[1]);
}

function getMonthFromString(mon){

   var d = Date.parse(mon + "1, 2018");
   
   if(!isNaN(d))
      return new Date(d).getMonth() + 1;
   
   return -1;
}


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

//     CHECKING IF AT LEAST ONE ALTERNATIVE WAS SELECTED (listaQuestoes.xhtml)
//---------------------------------------------------------------------------------
function checkQuestions() {
	if($('#questionList input[type=checkbox]:checked').length) 
		return true;
	else {
		alert("É necessário selecionar pelo menos uma questão para a sua prova!");
		return false;
	}
}

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

//        FILTRO DISCIPLINAS (listaQuestoes.xhtml)
//----------------------------------------------------------------------
$(".filterQuestionsByDiscipline").on('click', function(){

	var questions = document.getElementsByClassName('questionContainer');
	var disciplinesAvailableToChoose = 
		document.getElementById('disciplines').getElementsByTagName('input'); 
	
	if(disciplinesAvailableToChoose.length) {

		for(var j = 0; j < questions.length; j++) {

			$(questions[j]).closest('td').css('display', 'none');
			
			for(var k = 0; k < disciplinesAvailableToChoose.length; k++) {
				
				if(disciplinesAvailableToChoose[k].checked && 
						questions[j].classList.contains(disciplinesAvailableToChoose[k].value))
	
					$(questions[j]).closest('td').css('display', 'table-cell');
			}
		}
	}
});

$('.clearFilter').on('click', function(){
	
	var questions = document.getElementsByClassName('questionContainer')
	var disciplinesAvailableToChoose = 
		document.getElementById('disciplines').getElementsByTagName('input') 
	
	for (var i = 0; i < questions.length; i++)  
		$(questions[i]).closest('td').css('display', 'table-cell')
	
	for (var i = 0; i < disciplinesAvailableToChoose.length; i++)
		disciplinesAvailableToChoose[i].checked = false
});

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
