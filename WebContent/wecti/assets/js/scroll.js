$(document).ready(function () {

	$(".scroll").click(function(event){
		if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
			event.preventDefault();
			jQuery('html,body').animate({scrollTop:jQuery(this.hash).offset().top}, 1000);
		}
			$('.scroll').removeClass('active');
			$(this).addClass('active');
	});
	
	// ou 
	var windowWidth=$(window).width();
	if(windowWidth>767){
		$(window).on('scroll',function(){if($(window).scrollTop()>405){
			$('.main-nav').addClass('fixed-menu animated slideInDown');
			}else{
				$('.main-nav').removeClass('fixed-menu animated slideInDown');}});
				}else{
					$('.main-nav').addClass('fixed-menu animated slideInDown');
					}
	
	
	
});
