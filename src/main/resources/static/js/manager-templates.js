$(document).ready(function(){
	
	
	
	
	
	$(".simpletable").simpletable();
	
	
	var table = $('#datatable-fixed-header').DataTable({
        "processing" : true,
        "serverSide" : true,
        "columns": [ // NOTE: you need to specify names of fields as ids for the columns
        	{ "data": "productId"},
            { "data": "name" },
            { "data": "price" },
            { "data": "productId" },
            { "data": "version",
               render: function(data,type,row){
            	   return '<input type="checkbox" class="editor-active">';
               }
            }
            
        ],
        
        rowCallback: function ( row, data ) {
            // Set the checked state of the checkbox in the table
        	//	$('input.editor-active', row).prop( 'checked',1 );
        	
        	
        },
        select: {
            style: 'os',
            selector: 'td:not(:last-child)' // no row selection on last column
        },
        "ajax": {
            "url": "/customsecondtemplate/lists",
            "type": "POST",
            "contentType" : "application/json; charset=utf-8",			    
            "data": function ( d ) {
                return JSON.stringify(d); // NOTE: you also need to stringify POST payload
            }
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
            			        content: 'url:/customtemplate/add',
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
            },
            {
            	text:'Modify',
            	action:function(e,dt,node,config){
            		
            	}
            },
            {
                text: 'Remove',
                action: function ( e, dt, node, config ) {
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
                }
            }
        ],
        
        createdRow:function(row,data,dataIndex){
        	
        }
    });
	
	
	table.button( 0 ).enable(true );
    table.button( 1 ).enable(false);
	
	table.on( 'change', 'input.editor-active', function () {
	     // 	alert("onchange");
		
		table.button(1).enable($(this).prop( 'checked'));

		
		
	    } );
	 /* table.on( 'select deselect', function () {
	        var selectedRows = table.rows( { selected: true } ).count();
	        table.button( 0 ).enable( selectedRows === 1 );
	        table.button( 1 ).enable( selectedRows > 0 );
	    } );
	*/
	
});