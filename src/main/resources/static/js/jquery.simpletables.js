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
        	var urllist = url+"/lists";
        	var urladd = url+"/add";
        	content.find(".pick").prop('checked',false);
        	
        	
        	$(this).find("thead > tr > th").each(function(){
        		var name = $(this).text();
        		name = name.charAt(0).toLowerCase()+name.slice(1);
        		name = name.replace(" ","");
        		var temp = {};
        		if($(this).hasClass("pick")){
        	
        				var obj = {
        						data : name,
        						render:function(data,type,row){
        							 return '<input type="checkbox" class="editor-active">';
        							}
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
           
        	
        	var table = $(this).DataTable(
        			
        			{	
        				 columnDefs: [ {
        			            orderable: false,
        			            className: 'select-checkbox',
        			            targets:   0
        			        } ],
        			        
        				"processing" : true, 
        		        "serverSide" : true,
        		        "columns" : columns,
        		        "ajax": {
        		            "url": urllist,
        		            "type": "POST",
        		            "contentType" : "application/json; charset=utf-8",			    
        		            "data": function ( d ) {
        		                return JSON.stringify(d); // NOTE: you also need to stringify POST payload
        		            }
        		        },       			
        		        fixedHeader: {
        		            header: true,
        		            footer: true
        		        },
        		        dom: 'Bfrtip',
        		        buttons: [
        		        	 {
        		                 text: 'Add',                
        		                 action: function ( e, dt, node, config ) {
        		                	$.confirm(
        		                 			{
        		                 				title: "Add form",
        		             			    	type: 'blue',
        		             			        content: 'url:'+urladd,
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
        		                 	
        		                 	
        		                 }
        		             },
        		             {
        		                 text: 'Remove',                
        		                 action: function ( e, dt, node, config ) {
        		                	 	
        		                	 var data = content.find('input.editor-active[type="checkbox"]:checked');
        		                	 var length = data.length;
        		                	 var d = [];
        		                	 var parent = data.parent().parent();
        		                	
        		                /*	 if(length>=1){
        		                		 parent.each(function(){
        		                			 $(this).find('td').each(function(){
        		                				 alert($(this).text());
        		                			 })
        		                		 })
        		                		  
        		                	 }*/
        		                //	 table.row('.selected').remove().draw(false);
        		                	 
        		                	 //alert(table.row('.selected').remove());
        		                	 
        		                	 
        		                 }
        		             },
        		             
        		        ]
        		        
        			
        			},
        		    
        	);
        	
        	

        	

        	
        	table.on( 'draw', function () {
        	    //alert( 'Redraw occurred at: '+new Date().getTime() );
        		table.buttons(1).disable();
        	
 /*       		content.find(".pick").prop('checked',false);
            	content.find(".pick").on('change', function() {
            		var status;
            		//alert($(this).prop('checked'));
            		
            		if($(this).prop("checked")==false){
            			
            			content.find(".pick").prop('checked',true);
            		}else{
            			content.find(".pick").prop('checked',false);
                			
            		}
        			status =  $(this).prop("checked");
            		content.find('input.editor-active[type="checkbox"]').prop('checked',status);
            		if(status){
            			
            			table.buttons(1).enable();
            		}else{
            			table.buttons(1).disable();
            		}
            	});
            	*/
            	
        		
        		$(this).find('.editor-active').click(function(){
        				var length = content.find('input.editor-active[type="checkbox"]:checked').length;
        				if(length>=1){
        					table.buttons(1).enable();
        				}else{
        					table.buttons(1).disable()
        				}
        				
        			
        		});
        		
        		content.find("tbody").on( 'click', 'tr', function () {
        		        if ( $(this).hasClass('selected') ) {
        		            $(this).removeClass('selected');
        		        }
        		        else {
        		            table.$('tr.selected').removeClass('selected');
        		            $(this).addClass('selected');
        		        }
        		    } );
        		 
        		
        	    
        	} );
        	
        	/*
        	//$(this).find('editor-active').click([''])
        	//table.buttons().disable();
            table.on( 'select deselect', function () {
            	 var selectedRows = table.rows( { selected: true } ).count();
            	 alert(selectedRows);
            });*/
        });
        
        
        
        
        
        
 
    };
 
}( jQuery ));