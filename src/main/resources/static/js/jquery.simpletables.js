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
        	
        	var columns = [];
        	var url = $(this).attr("table-url");
        	var urllist = url+"/lists";
        	var urladd = url+"/add";
        	
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
        		
        		//alert(JSON.stringify(temp));

        	});
        	
        	
        	
        	
        	
        	var table = $(this).DataTable(
        			
        			{	
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
        		             			        	
        		             			        },
        		             			        useBootstrap:false,
        		             			        buttons: {
        		             			            submit:function(){
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
        		             }
        		        ]
        		        
        			
        			},
        		    
        	);    
        	
        });
        
        
        
        
        
        
 
    };
 
}( jQuery ));