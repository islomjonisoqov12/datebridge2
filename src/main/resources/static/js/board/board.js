
var handleEvent = function(){
	
	$('#insert').on('click',function(){
		$('#method').val("");
		$("#form").attr("action","/board/write")
		$("#form").submit();
	});
	
	$('#delete').on('click',function(){
		
	 	$('#method').val('delete');
		
		$("#form").attr("action","/board/write/"+$("#seq").val());
		
		$("#form").submit();
	});
	
	//confirm('수정 하시겠습니까?','거래처가 수정됩니다.','update',updateCustCode);
	
};



var board = function () {
	
	return {
		//main function
		init: function () {
					
			handleEvent();
			
		}
	};
}();


$(document).ready(function() {
	board.init();
	
});
