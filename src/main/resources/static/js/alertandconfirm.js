
	 
	 /**
	  * class=addform
	  * popup-form=/customtemplate/update
	  * home-index=/customtemplate/index
	  * option-id=0 or 1 // auto submit 0 or 1 refresh automaticly
	  * boxformwidth=20%
	  * form-title=Add Something
	  * 
	  */


function add_form(classname){
	$("."+classname).each(function(e){
		 var popupform = $(this).attr("popup-form");
		 var boxformwidth = $(this).attr("box-form-width");
		 var typeDirect = $(this).attr("type-redirect");
		 var titleForm = $(this).attr("form-title");
		 var singleWarning  = $(".single-warning");
		 var loadtarget = $(this).attr("load-target");
		 if(loadtarget == null){
			 loadtarget = "#providerContent"
		 }
		
		 var typeredirect = 0;
		 if(typeDirect != null){
			 typeredirect = typeDirect;
		 }
		 if(titleForm == null){
			 titleForm = "";
		 }
		 if(boxformwidth == null){
			 boxformwidth = '50%'
		 }
		 
		 
		 var redirectDialog = function(){
			 var backup = $(loadtarget).clone();
			 var currentProvider = $(loadtarget);
			 var form = currentProvider.find("#formupdate");
	    	 var currentContent = currentProvider.find("#form-content");
       	 var action= form.attr("action");
       	 
       	 
			 $.get(popupform,{},function(data){
			    	 currentProvider.html(data);
			   
			    	 currentProvider.find(":input:enabled:visible:first").focus();
			    	 var form = currentProvider.find("#formupdate");
	            	 var action= form.attr("action");
	            	 var singleWarning  = currentProvider.find(".single-warning");
	            	 var buttonSubmit = currentProvider.find(".button-content");
				     buttonSubmit.each(function(e){
							$(this).find('.button-cancel').each(function(e){
									$(this).click(function(event){
										event.preventDefault();
										$(location).attr("href",$(location).attr('pathname'));
										//currentprovider.html(backup);
										//reload_form();
										
									});
							}) 
							
							$(this).find('.button-submit').each(function(e){
								$(this).click(function(event){
									event.preventDefault();
									 form = currentProvider.find("#formupdate");
							    	 currentContent = currentProvider.find("#form-content");
							    	 action= form.attr("action");
									 
							    	 $.ajax({
					            		    url: action,
					            		    type: 'post',
					            		    data: form.serialize(),
					            		    success: function(response) {
					            		    	var r = $(response);
					            		    	var valueform = r.find("#formupdate").html();
					            		      if (r.find('.has-error').length) {
					            		    	  currentContent.find("#formupdate").html(valueform);
					            		     	  form = currentProvider.find("#formupdate");
											      currentContent = currentProvider.find("#form-content");
											      action= form.attr("action");
											      
											      
					            		      }else{
					            		    	    var htmls = [
					            		    	    	'<div class="alert alert-success"> <a href="#" class="close" data-dismiss="alert">×</a>',
					            		    	    	'<h4>Success</h4>',
					            		    	    	'<br />',
					            		    	    	'<div>All records were processed correctly!</div>',
					            		    	    	'</div>'
					            		    	    ];
					            		    	    singleWarning.html(htmls.join(""));
					            		    	    form.find("input[type=text], textarea").val("");
					            		    	    form.find(".has-error").each(function(e){
					            		    	    		$(this).removeClass();
					            		    	    		$(this).find(".help-block").remove();
					            		    	    });
					            		    	    
					            		    	    singleWarning.find(".alert-success").fadeTo(1000, 500).slideUp(500, function(){
					            		    	        $(".alert-success").alert('close');
					            		    	    });
					            		    	 	
					            		    	 	currentContent.close();
					            		      }
					            		      
					            		      }
					            			});
									
								});
							});
							
							
						 });
			    	 
			    });
			 
		 };
		 
		 var popupdialog = function(){
			 $.confirm({
			    	title: titleForm,
			    	type: 'blue',
			        content: 'url:'+popupform,
			        onContentReady: function () {
			        	 var self = this;
			        	 var content = this.$content;
			        	 content.find(".button-content").remove();
			        	 content.find(":input:enabled:visible:first").focus();
			        	

			        },
			        contentLoaded: function(data, status, xhr){
			        },
			        useBootstrap:false,
			        boxWidth:boxformwidth,
			      
			        buttons: {
			            submit: function(){
			            	 var self = this;
			            	 var content = this.$content;
			            	 var form = content.find("#formupdate");
			            	 var action= form.attr("action");
			            	 var currentContent = this;
			            	 $.ajax({
			            		    url: action,
			            		    type: 'post',
			            		    data: form.serialize(),
			            		    success: function(response) {
			            		    	var r = $(response);
			            		    	  r.find(".button-content").remove();
			            		      if (r.find('.has-error').length) {
			            		    	  currentContent.setContent(r);
			            		      }else{
			            		    	    var htmls = [
			            		    	    	'<div class="alert alert-success"> <a href="#" class="close" data-dismiss="alert">×</a>',
			            		    	    	'<h4>Success</h4>',
			            		    	    	'<br />',
			            		    	    	'<div>All records were processed correctly!</div>',
			            		    	    	'</div>'
			            		    	    ];
			            		    	    singleWarning.html(htmls.join(""));
			            		    	    
			            		    	    singleWarning.find(".alert-success").fadeTo(1000, 500).slideUp(500, function(){
			            		    	        $(".alert-success").alert('close');
			            		    	    });
			            		    	 	
			            		    	 	currentContent.close();
			            		      }
			            		      
			            		      }
			            			});
			            	 
			            	 
			            	 
			            	 return false;
			            },
			            close: {
			            		keys:['esc'],
			            		action : function(){
			            		}
			            }
			        }
			      
			        	
			    });
		 }
		 
		 
		 $(this).click(function(){
			 		if(typeredirect == 0){
			 			popupdialog();
			 		}else if(typeredirect == 1){
			 			redirectDialog();
			 		}
		 });
	 });
}


/**
 * class=updateform
 * data-entity-id=100
 * id-to-del=id 
 * popup-form=/customtemplate/update
 * home-index=/customtemplate/index
 * option-id=0 or 1 // auto submit 0 or 1 refresh automaticly
 * boxformwidth=20%
 * form-title=Add Something
 * 
 * 
 * 
 */

function update_form(classname){
	$("."+classname).each(function(e){

		
		 var typeDirect = $(this).attr("type-redirect");
		 var popupform = $(this).attr("popup-form");
		 var dataEntiyId = $(this).attr("data-entity-id");
		 var boxformwidth = $(this).attr("box-form-width");
		 var titleForm = $(this).attr("form-title");
		 var singleWarning  = $(".single-warning");
		 var dataEntityId = $(this).attr("data-entity-id");
		 var idToUpdate = $(this).attr("id-to-update");
		 var optionId = $(this).attr("option-id");
		 var homeIndex = $(this).attr("home-index");
		 var option = 0;
		 if(optionId!= null){
				option = optionId;
		 }
			
		 if(homeIndex == null){
			 homeIndex = $(location).attr('pathname');
		 }
			
		 var typeredirect = 0;
		 if(typeDirect != null){
			 typeredirect = typeDirect;
		 }
		 
		 if(titleForm == null){
			 titleForm = "";
		 }
		 if(dataEntityId != null && idToUpdate != null){
			 popupform = popupform + "?"+idToUpdate+"="+dataEntityId;
		 }
		 
		 
		 if(boxformwidth == null){
			 boxformwidth = '50%'
		 }
		 
		 var popupdialog = function(){
			 $.confirm({
			    	title: titleForm,
			    	type: 'blue',
			        content: 'url:'+popupform,
			        onContentReady: function () {
			        	 var self = this;
			        	 var content = this.$content;
			        	 content.find(":input:enabled:visible:first").focus();
			        	 content.find(".button-content").remove();
			        	 
			        	 
			        

			        },
			        contentLoaded: function(data, status, xhr){
			        },
			        useBootstrap:false,
			        boxWidth:boxformwidth,
			      
			        buttons: {
			            submit: function(){
			            	 var self = this;
			            	 var content = this.$content;
			            	 var form = content.find("#formupdate");
			            	 var buttonSubmit = content.find(".button-submit");
			            	 buttonSubmit.remove();
			            	 var action= form.attr("action");
			            	 var currentContent = this;
			            	 $.ajax({
			            		    url: action,
			            		    type: 'post',
			            		    data: form.serialize(),
			            		    success: function(response) {
			            		    	var r = $(response);
			            		    	r.find(".button-content").remove();
			            		    	 
			            			  if (r.find('.has-error').length) {
			            				
			            				  currentContent.setContent(r);
			            			  }else{
			            				  if(option==0){
				            		    	    var htmls = [
				            		    	    	'<div class="alert alert-success"> <a href="#" class="close" data-dismiss="alert">×</a>',
				            		    	    	'<h4>Success</h4>',
				            		    	    	'<br />',
				            		    	    	'<div>All records were processed correctly!</div>',
				            		    	    	'</div>'
				            		    	    ];
				            		    	    singleWarning.html(htmls.join(""));
				            		    	    
				            		    	    singleWarning.find(".alert-success").fadeTo(1000, 500).slideUp(500, function(){
				            		    	        $(".alert-success").alert('close');
				            		    	    });
				            		    	 	
				            		    	 	currentContent.close();
			            				  }else{
			            						$(location).attr("href",homeIndex);
			            				  }
			            		      }
			            		      
			            		      }
			            			});
			            	 
			            	 
			            	 
			            	 return false;
			            },
			            close: {
		            		keys:['esc'],
		            		action : function(){
		            		}
			            }
			        }
			      
			        	
			    });
		 };
		 
		 
		 var redirectDialog = function(){
			 var backup = $("#providerContent").clone();
			 var currentProvider = $("#providerContent");
			 var form = currentProvider.find("#formupdate");
	    	 var currentContent = currentProvider.find("#form-content");
       	     var action= form.attr("action");
       	 
			 $.get(popupform,{},function(data){
			    	 currentProvider.html(data);
			    	 form = currentProvider.find("#formupdate");
			    	 currentContent = currentProvider.find("#form-content");
			    	 action= form.attr("action");
			    	 
			    	 var buttonSubmit = currentProvider.find(".button-content");
			
	       
	            	 var singleWarning  = currentProvider.find(".single-warning");
	            	 var buttonSubmit = currentProvider.find(".button-content");
				   
				     buttonSubmit.each(function(e){
							$(this).find('.button-cancel').each(function(e){
									$(this).click(function(event){
										event.preventDefault();
										//$(location).attr("href",$(location).attr('pathname'));
										currentProvider.html(backup);
										reload_form();
										
										
										 
									});
							});
							
							$(this).find('.button-submit').each(function(e){
								 
				            	 
								$(this).click(function(event){
									 event.preventDefault();
									 form = currentProvider.find("#formupdate");
							    	 currentContent = currentProvider.find("#form-content");
							    	 action= form.attr("action");
									 $.ajax({
					            		    url: action,
					            		    type: 'post',
					            		    data: form.serialize(),
					            		    success: function(response) {
					            		    	var r = $(response);
					            		    	var valueform = r.find("#formupdate").html();
					            		      if (r.find('.has-error').length) {
					            		    	  currentContent.find("#formupdate").html(valueform);
					            		     	  form = currentProvider.find("#formupdate");
											      currentContent = currentProvider.find("#form-content");
											      action= form.attr("action");
					            		      }else{
					            		    	  
					            		    	  if(option==0){
					            		    		    var htmls = [
						            		    	    	'<div class="alert alert-success"> <a href="#" class="close" data-dismiss="alert">×</a>',
						            		    	    	'<h4>Success</h4>',
						            		    	    	'<br />',
						            		    	    	'<div>All records were processed correctly!</div>',
						            		    	    	'</div>'
						            		    	    ];
						            		    	    singleWarning.html(htmls.join(""));
						            		    	    form.find("input[type=text], textarea").val("");
						            		    	    form.find(".has-error").each(function(e){
						            		    	    		$(this).removeClass();
						            		    	    		$(this).find(".help-block").remove();
						            		    	    });
						            		    	    
						            		    	    singleWarning.find(".alert-success").fadeTo(1000, 500).slideUp(500, function(){
						            		    	        $(".alert-success").alert('close');
						            		    	    });
					            		    	  
					            		    	   }else{
					            		    		 
								                		//$(location).attr("href",homeIndex);
								                		
								                		
								                		
								                	}
					            		    	  
					            		    	
					            		    	 	
					            		    	 	
					            		      }
					            		      
					            		      }
					            			});
									
								});
							});
							
							
							
						 });
			    	 
			    });
			 
		 };
		 
		 $(this).click(function(){
			 if(typeredirect == 0){
		 			popupdialog();
		 		}else if(typeredirect == 1){
		 			redirectDialog();
		 		}
		 });
		 
		 
		 
	 });
	 
}


function delete_me(classname){
	$("."+classname).each(function(e){
		var dataEntiyId = $(this).attr("data-entity-id");
		var urlToDel = $(this).attr("url-entiy-del");
		var idToDel = $(this).attr("id-to-del");
		
		var homeIndex = $(this).attr("home-index");
		var optionId = $(this).attr("option-id");
		var option = 0;
		var currentTr=$(this).parents("tr");
		if(optionId!= null){
			option = optionId;
		}
		var warning = "";
		if(dataEntiyId == null){
			warning = warning + " data-entity-id is not available";
		}

		if(urlToDel == null){
			warning = warning + " url-entiy-del is not available";
		}
		if(idToDel == null){
			warning = warning + " id-to-del is not available";
		}
		
		if(option != 0){
			if(homeIndex == null){
				warning = warning + " home-index is not available";
			}
		}
		$(this).click(function(){
			
			if(warning.length>0){
				$.alert(warning);
				return;
			}
			var getObj = {};
			
			var label = idToDel.split(",");
			var value = dataEntiyId.split(",");
			if(label.length >0 && value.length>0){
				if(label.length == value.length ){
					for(var i = 0; i< label.length ;i++){
						getObj[""+label[i]]=value[i];
					}
				}
			}else{
 			
			getObj[""+idToDel]=dataEntiyId;
			}
			
			$.confirm({
		        title: 'Warning',
		        content: 'Are you sure want to delete this?',
		        type: 'red',
		        typeAnimated: true,
		        buttons: {
		            tryAgain: {
		                text: 'Ok',
		                btnClass: 'btn-red',
		                action: function(){
		                
		                	
		                $.get(urlToDel,getObj,function(data){
		                    	if(option==0){
			                		currentTr.remove();
			                	}else{
			                		$(location).attr("href",homeIndex);
			                	}
		                	}).fail(function(){
		                		 $.alert('There is a problem or fail connection, please contact your administrator.');
		                		return false;
		                	})
		                	
		                	return true;
		                }
		            },
		            close: function () {
		            }
		        }
		    });
		})
	   
	})
 
}

function reload_form(){
	 add_form("addform");
 	 update_form("updateform");
	 delete_me("delme");
	 $('.pagination').pagination();
}