(function($){
    $.fn.pagination = function(settings)
    {
        console.log('memanggil fancy string...');

        var opts = {
            mode : "",
            total: 35,
            visiblePages:7,
            useAttr:true,
            configpagingurl:'',
            redirectUrl:'',
            targetcontent:'table-content'           	
            	
        };
        
        var opts = $.extend(opts, settings);
       
        

        if(opts.useAttr){
        	var configpagingurl = $(this).attr('config-paging-url');
        	var targetcontent = $(this).attr("target-content");
            if(configpagingurl != null){
            	opts.configpagingurl = configpagingurl;
            };
            if(targetcontent != null ){
            	opts.targetcontent = targetcontent;
            }
            
         };
        
        var component = this;
        

      	var p = Cookies.get('page',{ path: "/" }) != null ? Cookies.get('page',{ path: "/" })  : 1;
      	
      	p = parseInt(p);
        
        $.get(opts.configpagingurl,{},function(data){
        	
        	
        	
        	
        	opts.total = data.total;
        	opts.visiblePages  = data.visiblePages;
        	opts.redirectUrl = data.redirectUrl;
        	$(component).twbsPagination({
      	        totalPages: opts.total,
      	        visiblePages: opts.visiblePages,
      	        startPage : p,
      	        onPageClick: function (event, page) {

      	        	Cookies.set('page', page, { path: "/" });
      	        	var cp = {};
      	        	cp['page'] = page;
      	        	$.get(opts.redirectUrl,cp,function(data){
      	        		
      	        		$("#"+opts.targetcontent).html(data);
      	        	     update_form("updateform");
      	        		 delete_me("delme");
      	        		 
      	        		
      	        		
						
      	        	});
      	        	
      	        }
      	    });
        	
        	

        }).fail(function(){
    		return false;
    	})
    	
    	
    
          
     
        
      
    };

})(jQuery);