
function onClickButtonLoading(t){
	var $this = $(t);
		$this.button('loading');
	    setTimeout(function() {
	       $this.button('reset');
	   }, 4000);
	   	
}
