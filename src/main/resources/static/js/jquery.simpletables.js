/**
 * Created by Patar Timotius
 * Purpose : Only Generate Simple view of crud
 * 16 Jun 2019
 */


(function ( $ ) {
 
    $.fn.simpletable = function( options ) {
 
        // Default options
        var settings = $.extend({
            name: 'John Doe'
        }, options );
 
        // Apply options
        return this.each(function(){
        	var $this = $(this);
        
        	
        	var parent = $(this).parent();

    		
    		var content = $(this);
        	var columns = [];
        	var url = $(this).attr("table-url");
        	var typeForm = $(this).attr("typeForm");
        	var domv = 'Bfrtip';
        	if(typeForm == 1){
        		domv = 'Blrtp';
        	}
        	var buttonFlag = "false";
        	
        	if($(this).attr("button")){
        		buttonFlag = $(this).attr("button");
        	}
        	var urllist = url+"/lists";
        	var urladd = url+"/add";
        	var urlremove = url+"/remove";
        	var urlmodify = url+"/modify";
        	
        	content.find(".pick").prop('checked',false);
        
        	
        	$(this).find("thead > tr > th").each(function(){
        		var name = $(this).text();
        		name = name.charAt(0).toLowerCase()+name.slice(1);
        		name = name.replace(" ","");
        		var temp = {};
        		if($(this).hasClass("pick")){
        	
        				var obj = {
        						data : name
        					};
        				
        				columns.push(obj);
        		
        		}else{
        			temp["data"] = name;
            		columns.push(temp);
        		};

        	});
        	
        	
        	function alertmessage(message){
        		var a = $('<div id="successmessage" class="alert alert-success">'+'<button type="button" class="close" data-dismiss="alert" aria-hidden="true">'
        	        	  +'           ×</button>'
        	              +'      <span class="glyphicon glyphicon-ok"></span> <strong>'+message+'</strong>'
        	              +'       <hr class="message-inner-separator">'
        	              +'      <p>'
        	              +'           You successfully Add Item.</p>'
        	              +'   </div>')
        	              
        	              setTimeout(function () {
            if ($(".alert").is(":visible")){
                 //you may add animate.css class for fancy fadeout
                $(".alert").fadeOut("fast");
            }
        }, 1000)
        	   return a;           
        	    
        	}
           
        	var showForm = function(table,ket,url,data = []){
        		
        		var title = "Add Form";
        		if (ket == 0){
        			title = "Add Form";
        		}else if(ket == 1){
        			title = "Modify Form";
        		}
        		
        		var content ='url:'+url;
        		if(ket == 0){
        			content ='url:'+url;
        			if(typeForm == 2){
        				var input=$("#categorychange");
	            		content = 'url:'+url+"?"+input.attr("name")+"="+input.val();
	            	}
        			if(typeForm == 3){
        				var input=$("#hiddenCategory");
	            		content = 'url:'+url+"?"+input.attr("name")+"="+input.val();
        			}
	            	
	            	
        		}else if(ket == 1){
        			if(typeForm==2){
        				var input=$("#categorychange");
        				url = 	url+"?"+input.attr("name")+"="+input.val();
        			};
        			if(typeForm == 3){
        				var input=$("#hiddenCategory");
	            		content = 'url:'+url+"?"+input.attr("name")+"="+input.val();
        			}
        			
        			
        			content	= function(){
        				var self = this;
        				return 	$.ajax({
			        			    url: url,
			        			    method: 'POST',
			      				    contentType: 'application/json',
			      				    data: data			      				   
			      				}).done(function (response){
			      					table.ajax.reload();
			      					self.setContent(response);
			        			}).fail(function(xhr, textStatus, errorThrown){
			        				self.setContent(textStatus);
			        	        });        				
        			};
      			
        		}
        		
        		
        		$.confirm(
             			{
             				title: title,
         			    	type: 'blue',
         			        content: content,
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
    					            	    	alertmessage("success yo").prependTo(parent);
    					      					table.ajax.reload();

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
             			}   			
             		);
        		
        		
        	};
        	
        	var table = $(this).DataTable(
        			
        			{	
        				 columnDefs: [
        			         {
        				            targets: 0,
        				            render: function(data, type, row, meta){
        				               if(type === 'display'){
        				                  data = '<div class="checkbox"><input type="checkbox" class="dt-checkboxes"><label></label></div>';
        				               }

        				               return data;
        				            },
        				            checkboxes: {
        				               selectRow: true,
        				               selectAllRender: '<div class="checkbox"><input type="checkbox" class="dt-checkboxes"><label></label></div>'
        				            }
        				         }
        				      ],
        				      
        				      select: {
        				          style: 'multi'
        				       },
        				       order: [[1, 'asc']],
        				"processing" : true, 
        		        "serverSide" : true,
        		        "columns" : columns,
        		        "ajax": {
        		            "url": urllist,
        		            "type": "POST",
        		            "contentType" : "application/json; charset=utf-8",			    
        		            "data": function ( d ) {
        		            	d.searchcomplex = [];
        		            	if(typeForm == 1){
        		            		$("#formsearch input,#formsearch select").each(function(index){
        		            			var input = $(this);
        		            			d.searchcomplex.push({label:input.attr("name"),value:input.val()});
        		            		});
        		            	}else if(typeForm == 2){
        		            			var input=$("#categorychange");
        		            			d.selectcategory =  {label:input.attr("name"),value:input.val()};
        		            	}else if(typeForm == 3){
        		            		
        	        					var input=$("#hiddenCategory");
        	        					d.hiddenCategory =  {label:input.attr("name"),value:input.val()};
        	        			}
        		            
        		            	
        		            	
        		                return JSON.stringify(d); // NOTE: you also need to stringify POST payload
        		            }
        		        },       			
        		        fixedHeader: {
        		            header: true,
        		            footer: true
        		        },
        		       dom: domv,
        		   
        		        buttons: [
        		        	 {
        		                 text: 'Add',                
        		                 action: function ( e, dt, node, config ) {
        		                	 showForm(table,0,urladd);
        		                 }
        		             },
        		             {
        		                 text: 'Modify',                
        		                 action: function ( e, dt, node, config ) {
        		                   	 var modify = dt.rows( { selected: true });
        		                	 var length = modify.data().length;
        		                	
        		                	 if(length>=2){
        		                		    $.alert({
        		                		        title: 'Warning!',
        		                		        content: 'You can not pick more than one',
        		                		        type: 'red'
        		                		    });
        		                	 }else{
        		                			var selectedData = JSON.stringify(modify.data().toArray()[0]);
        		                		 	showForm(table,1,urlmodify,selectedData);
        		                		 
        		                		 
        		                		 
        		                	 }
        		                 
        		                 }
        		             },
        		             {
        		                 text: 'Remove',                
        		                 action: function ( e, dt, node, config ) {
        		                	var deleteRemove = dt.rows( { selected: true });
        		               
        		                	var selectedData = JSON.stringify(deleteRemove.data().toArray());
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
        		                                	var data = selectedData;
        		                                	
        		                                	$.ajax({
        		                      				    url: urlremove,
        		                      				    type: 'POST',
        		                      				    dataType: 'json',
        		                      				    contentType: 'application/json',
        		                      				    data: data,
        		                      				    processData: false,
        		                      				    success: function( data, textStatus, jQxhr) {
        		                      				    		if(data.response==0){
        		                      				    			deleteRemove.remove().draw(false);	
        		                      				    		}
        		                      				    }
        		                      				});
        		                      			
        		                                	
        		                                	
        		                                	
        		                                }
        		                            },
        		                            close: function () {
        		                            }
        		                        }
        		                    });
        		              
        		                	 
        		                	 
        		                 }
        		             },
        		             
        		        ]
        		        
        			
        			},
        		    
        	);
        	
        	
        	if(typeForm == 1){
        		$("#formsearch").click(function(e){
        			table.draw(false);
        		});

        		$("#formclear").click(function(e){
        			$("#formsearch input").val('');
        			e.stopPropagation();
        			
        		});
        		$("#formsearch input,#formsearch select").each(function(index){
        			var input = $(this);
        			input.click(function(e){
        				e.stopPropagation();
        			});
        		});
        	}else if(typeForm==2){
        		$("#categorychange").change(function(e){
        			table.draw(false);
        			e.stopPropagation();
        		});
        	}
            	
        		
        	if(buttonFlag=="false"){   
        		table.buttons().remove();
        	};

        	table.buttons(1).disable();
        	table.buttons(2).disable();

        		
        
        	
        	 table
             .on( 'select', function ( e, dt, type, indexes ) {
                 var rowData = table.rows( indexes ).data().toArray();
               
            	 table.buttons(1).enable();
            	 table.buttons(2).enable();
            
             	
            
             } )
             .on( 'deselect', function ( e, dt, type, indexes ) {
                 var rowData = table.rows( indexes ).data().toArray();
            	 table.buttons(1).disable();
            	 table.buttons(2).disable();
             } );
     	  
     	  
     	  
        	 
        	 
        	 
        });
        
        
        
        
        
        
 
    };
 
}( jQuery ));