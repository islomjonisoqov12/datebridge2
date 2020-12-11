$(function () {

	$("a.b1.toggle").on("click",function(e){				
		e.preventDefault();
		var $that = $($(this).attr('href'));	
		$that.toggle();		
	});   

	// 달력보기
	$( ".calendar" ).datepicker(); 

});


/* 달력보기 */
$.datepicker.setDefaults({
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'], 
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    changeMonth: true,
    changeYear : true,  
    dateFormat: "yy-mm-dd",  
    yearSuffix: '년'
});  