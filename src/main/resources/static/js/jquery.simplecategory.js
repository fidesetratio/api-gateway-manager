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
       	var remove = mainUrl+"/"+"remove";
       	var uploadFileUrl = mainUrl+"/"+"uploadFile";
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
       	    		html = html+'</div>';
       	    		html = html+'<div class="right col-xs-5 text-center">';
       	    		html = html+'<img src="/images/'+value.photos+'" alt="" class="img-responsive">';
       	    		html = html+'</div>';
       	    		html = html+'</div>';
       	    		html = html+'<div class="col-xs-12 bottom ">';
       	    		html = html+'<div class="col-xs-12 col-sm-3 emphasis">';
       	    		html = html+'<button type="button" class="btn btn-primary btn-xs">';
       	    		html = html+'<i class="fa fa-user"> </i> Modify';
       	    		html = html+'</button>';
       	    		html = html+'</div>';
       	    		html = html+'<div class="col-xs-12 col-sm-3 emphasis">';
       	    		html = html+'<button type="button"   class="removethem btn btn-primary btn-xs" attr="'+value.appId+'">';
       	    		html = html+'<i class="fa fa-user" > </i> Remove';
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
      	    		var div = $(html);
      	    		var d = container.find("#container").append(div);
      	    		
      	    		
      	    		
      	    		div.find(".removethem").click(function(){
      	    			
      	    			var id = $(this).attr("attr");
      	    			
      	    			
      	    			
      	    			
      	    			$.confirm({
	                        title: 'Are you want to delete this Item?',
	                        content: 'Are you want to delete this Item?.',
	                        type: 'red',
	                        typeAnimated: true,
	                        buttons: {
	                            tryAgain: {
	                                text: 'Ok',
	                                btnClass: 'btn-red',
	                                action: function(){
	                                	var data = id;
	                                	 $.ajax({
	                   	    	       		url: remove+"?removeId="+data,
	                   	    	       	    type: 'get',
	                   	    	       	    success: function(response) {
	                   	    	       	    	reload("");
	                   	    	       	    }
	                   	    			 });
	                                	
	                                	
	                                	
	                                }
	                            },
	                            close: function () {
	                            }
	                        }
	                    });
      	    			
      	    			
      	    			
      	    			
      	    			
      	    		});
      	    		
      	    		
      	    		
      	    		
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
				            	 var uploadFile = content.find("#upload-file-input");
				            	
				            	// var formUpload = $(uploadFile).appendTo(form);
				            	 uploadFile.on("change", function(){
				            		 	var data;
					            	    data = new FormData();
					           		    data.append( 'file', uploadFile[0].files[0]);
					            	    $.ajax({
					            	        url: uploadFileUrl,
					            	        type: "POST",
					            	        data: data,
					            	        enctype: 'multipart/form-data',
					            	        processData: false,
					            	        contentType: false,
					            	        cache: false,
					            	        success: function () {
					            	         	content.find("#viewer").prop("src","/media/"+uploadFile[0].files[0].name);
					            	        	
					            	        },
					            	        error: function () {
					            	          // Handle upload error
					            	          // ...
					            	        }
					            	      });
				            	 });
				            	 
  			        	
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