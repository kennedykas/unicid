
var Issue = {

	$counter               : $('#counter'),
	numberOfSelectedIssues : 0,

	init : function() {

		this.addListeners();
		this.fillTestDateAndTime();
		this.cleanFieldsForNewTest();
	},

	addListeners : function() {

		$('.check-answer')           .on('click',  function(event){ Issue.checkAnswer(event); });
		$('.filter-issues-btn')      .on('click',  function(){ Issue.filterIssuesByDiscipline(); });
		$('.clear-filter-btn')       .on('click',  function(){ Issue.clearFilter();       });
		$('.question-status')        .on('click',  function(){ Issue.updateCounter();     });
		$('.save-test-btn')          .on('click',  function(){ Issue.saveTest();          });
		$('.datepicker, .timepicker').on('change', function(){ Issue.updateDateAndTime(); });
	},

	checkAnswer : function(event) {

		var selectedAnswer = $('#questao\\:' + 'alternativa' + event.currentTarget.value).val();

		if (selectedAnswer) {
			$('#questao\\:' + "alternativaCorreta").val(event.currentTarget.value);
			return true;
		} else {
			M.toast({html: 'A alternativa selecionada está vazia.'});
			event.preventDefault();
		}
	},

	filterIssuesByDiscipline : function() {

		var $issues             = $('.issues-container');
		var selectedDisciplines = $('#disciplines').val();

		if(selectedDisciplines.length) {

			$issues.closest('td').hide();

			for (let i in selectedDisciplines)

				$('.' + selectedDisciplines[i]).closest('td').show();
		}
	},

	clearFilter : function() {

		$('.issues-container').closest('td').show();

		M.toast({html: 'Mostrando todas as questões'});
	},

	updateCounter : function() {

		this.numberOfSelectedIssues = $('.question-status input[type=checkbox]:checked').length;

		if (this.numberOfSelectedIssues)
			this.$counter.html(this.numberOfSelectedIssues).removeClass('scale-out').addClass('scale-in');
		else
			this.$counter.toggleClass('scale-out scale-in');
	},

	saveTest : function() {

		if (this.numberOfSelectedIssues) {
			$('#test-info\\:title').focus();
			$('#test-info').removeClass('scale-out').addClass('scale-in');
		} else {
			M.toast({html: 'Selecione pelo menos uma questão.'});
			$('#test-info').toggleClass('scale-out scale-in');
		}
	},

	updateDateAndTime : function() {

		$("#test-info\\:dateAndTime").val(
			$('.datepicker').val() + "-" + $('.timepicker').val());
	},

	fillTestDateAndTime : function() {

		if ($("#test-info\\:dateAndTime").val() !== '') {

			let dateAndTime = $("#test-info\\:dateAndTime").val().split("-");

			$('.datepicker').val(dateAndTime[0]);
			$('.timepicker').val(dateAndTime[1]);
		}
	},

	cleanFieldsForNewTest : function() {

		if (this.$counter.length)
			$('#test-info\\:value, #test-info\\:time').val(null);
	}
};