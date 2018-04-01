;
var unicid   = unicid || {} ;
unicid.Issue = {};

(function($, ns) {

	"use strict";

	ns.version = "1.0";

	ns.controller = {

		init : function init() {

		 	this.addListeners();
		},
		
		addListeners : function addListeners() {
			
			$('.check-answer').on('click', function(event) {
				unicid.Issue.controller.checkIfSelectedAnswerHasText(event);
			});
		},
		
		checkIfSelectedAnswerHasText : function checkIfSelectedAnswerHasText(event) {
			var selectedAnswerText = ($('#questao\\:' + "alternativa" + $(event.currentTarget).val()).val());
			if(selectedAnswerText) {
				$('#questao\\:' + "alternativaCorreta").val($(event.currentTarget).val());
				return true;
			} else {
				M.toast({html: 'A alternativa selecionada está vazia!'});
				event.preventDefault();				
			}
		}
	};
})(jQuery, unicid.Issue);