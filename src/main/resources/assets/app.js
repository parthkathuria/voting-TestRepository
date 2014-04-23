$("#updatePoll").click(function() {
    var pollid = document.getElementBy
    var status = "{{status}}";
    //alert('About to report lost on ISBN ' + isbn);
    if(confirm('About to report lost on ISBN ' + isbn + '\nConfirm?')){
    	$.ajax({
        	url: "/library/v1/books/"+isbn+"?status=lost",
        	type: "PUT",
        	contentType: "application/json",
        });
	    window.location.reload(true);
    }
        
});