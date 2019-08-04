/**
 * Created by Patar Timotius
 * Purpose : Only Generate Simple view of crud
 * 16 Jun 2019
 */


(function ( $ ) {
 
    $.fn.simplecategory = function( options ) {
 
        // Default options
        var settings = $.extend({
            name: 'John Doe'
        }, options );
 
        // Apply options
        return this.each(function(){
        	
        	
        var container = $(this);
        	
        var mainUrl = $(this).attr("application-url");
       	var url = mainUrl+"/"+"addForm";
       	var list = mainUrl+"/"+"lists";
       	var reload = function(search){
       		container.find("#container").empty();
       	 $.ajax({
       		url: list+"?search="+search,
       	    type: 'get',
       	    success: function(response) {
       	    	
       	    	$.each(response, function (index, value) {
       	    		var html = '<div class="col-md-4 col-sm-4 col-xs-12 profile_details">';
       	    		html = html+'<div class="well profile_view">';
       	    		html = html+'<div class="col-sm-12">';
       	    		html = html+'<h4 class="brief"><i>'+value.applicationName+'</i></h4>';
       	    		html = html+'<div class="left col-xs-7">';
       	    		html = html+'<p><strong>About: </strong>'+value.description+'</p>';
       	    		html = html+'<ul class="list-unstyled">';
       	    		html = html+'<li><i class="fa fa-phone"></i> Provider Authorization: Application </li>';
       	    		html = html+'</ul>';
       	    		html = html+'</div>';
       	    		html = html+'<div class="right col-xs-5 text-center">';
       	    		html = html+'<img src="https://statik.tempo.co/data/2019/05/09/id_840655/840655_720.jpg" alt="" class="img-responsive">';
       	    		html = html+'</div>';
       	    		html = html+'</div>';
       	    		html = html+'<div class="col-xs-12 bottom ">';
       	    		html = html+'<div class="col-xs-12 col-sm-3 emphasis">';
       	    		html = html+'<button type="button" class="btn btn-primary btn-xs">';
       	    		html = html+'<i class="fa fa-user"> </i> Modify';
       	    		html = html+'</button>';
       	    		html = html+'</div>';
       	    		html = html+'<div class="col-xs-12 col-sm-3 emphasis">';
       	    		html = html+'<button type="button" class="btn btn-primary btn-xs">';
       	    		html = html+'<i class="fa fa-user"> </i> Remove';
      	    		html = html+'</button>';
      	    		html = html+'</div>';
      	    		html = html+'<div class="col-xs-12 col-sm-3 emphasis">';
      	    		html = html+'<button type="button" class="btn btn-primary btn-xs">';
      	    		html = html+'<i class="fa fa-user"> </i> Add Links';
      	    		html = html+'</button>';
      	    		html = html+'</div>';
      	    		html = html+'</div>';
      	    		html = html+'</div>';
      	    		html = html+'</div>';  	    	
      	    		
      	    		container.find("#container").append(html);
       	    	});
       	    	
       	    }
       	
       	});
       	 
       	};
        	 reload("");
        	 var t = $(this);
        	 t.find("#add").click(function(e){

        		 $.confirm({
        			 title: 'Add',
        			 type: 'blue',
        			 content: 'url:'+url,
        			 onContentReady: function () {
  			        	     	 var content = this.$content;
				            	 var form = content.find("form");
				            	 form.find("input:first").focus();
  			        	
  			        }, 
  			        boxWidth: '80%',
  			        useBootstrap: false,
  			        buttons: {
  			        	submit:function(){
  			        		var self = this;
			            	 var content = this.$content;
			            	 var form = content.find("form");
			            	 var action= form.attr("action");
			            	 var currentContent = this;
			            	
			            	 $.ajax({
			            		url: action,
			            	    type: 'post',
			            	    data: form.serialize(),
			            	    success: function(response) {
			            	    	var ct = $(response);
			            	    	if (ct.find('.has-warning').length) {
				            	    	currentContent.setContent(ct);
				            	    }else{
				            	    	
				            	    	currentContent.close();
				            	    	 reload("");
				            	    }
			            	    }
			            	
			            	});
			            	 
  			        		return false;
  			        		
  			        	},
 			        	close:{
			            		keys:['esc'],
 			            		action : function(){
 			            			 
 			            		}
  			        	
  			        }
  			        }
        		 });
        		 
        		 e.stopPropagation();
        	 });
        	 
        });
        
        
        
        
        
        
 
    };
 
}( jQuery ));