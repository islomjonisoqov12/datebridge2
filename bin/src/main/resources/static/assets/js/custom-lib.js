$(function () {

	$("a.b1.toggle").on("click",function(e){				
		e.preventDefault();
		var $that = $($(this).attr('href'));	
		$that.toggle();		
	});   

});


