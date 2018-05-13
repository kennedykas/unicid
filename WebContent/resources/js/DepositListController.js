;
var unicid = unicid || {} ;
unicid.DepositList = {};

(function($, ns) {

	"use strict";

	ns.version = "1.0";

	ns.controller = {

		init : function init() {

			this.setupTable();
		 	this.addListeners();
		 	this.calcBalance();
		},
					
		setupTable : function setupTable() {
			
			var lastStudent = "";
			
			$('#deposits-form\\:deposits-table > tbody > tr').each(function() { 
				
				var currentStudent = $(this).find('td:first-of-type').text();
				
				if (lastStudent !== currentStudent) {
					
					$(this).before(
							'<tr class="details-toggler pointer"><td>'+ currentStudent +
							'</td><td></td><td></td><td></td><td><i class="material-icons right">expand_more</i></td></tr>'
					);
					lastStudent = currentStudent;
				} 
				
				$(this).hide();
			});
			
			$('#deposits-form\\:deposits-table > tbody > .details-toggler').each(function() { 
				
				var balance = 0;
				
				$(this).nextUntil('.details-toggler').each(function() {
					balance += parseFloat($(this).find('td:nth-child(3)').text().replace(/[R$ ]/g,''));
				});
			
				$(this).find('td:nth-child(3)').html("Saldo " + balance.toFixed(2).replace('.', ','));
			});
		},
		
		addListeners : function addListeners() {
			
			$('.details-toggler').on('click', function(event) {
				unicid.DepositList.controller.toggleDetails(event);
			});
		},
		
		toggleDetails : function toggleDetails(event) {
			
			$(event.currentTarget).nextUntil('.details-toggler').toggle(0);
			
			var $icon = $(event.currentTarget).find('td:last-child > i');
			
			$icon.html(($icon.html() === 'expand_more') ? 'expand_less': 'expand_more');
		},
		
		calcBalance : function calcBalance() {
			
			var balance = 0;
			
			$('#student-deposits-form\\:deposits-table > tbody > tr > td:nth-child(3)').each(function() {
				balance += parseFloat($(this).text().replace(/[R$ ]/g,''));
			});
			
			$('.balance').html("Saldo "+ balance.toFixed(2).replace('.', ','));
		}
	};
})(jQuery, unicid.DepositList);