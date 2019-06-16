 $(document).ready(function() {
	 
	  $('#mnu-category').find('a').click(function(e) {
	        e.preventDefault();
	        var cat = $(this).text();
	        $('#srch-category').text(cat);
	        $('#txt-category').val(cat);
	    });
	 Cookies.set('page', 1, { path: "/" });
	 reload_form();
	 
			/*
			 var table = $('#productsTable').DataTable({          
		         "sAjaxSource": "/customtemplate/products",
		         "sAjaxDataProp": "",
		         "order": [[ 0, "asc" ]],
		         "columns": [
		             { "mData": "id"},
		             { "mData": "name" },
		             { "mData": "price" },
		             { "mData": "productId" },
		             { "mData": "version" }                
		         ]
		  });*/
	 var table = $('#productsTable').DataTable({
         "processing" : true,
         "serverSide" : true,
         "columns": [ // NOTE: you need to specify names of fields as ids for the columns
        	 { "data": "productId"},
             { "data": "name" },
             { "data": "price" },
             { "data": "productId" },
             { "data": "version" }      
             
         ],
         "ajax": {
             "url": "/customtemplate/products",
             "type": "POST",
             "contentType" : "application/json; charset=utf-8",			    
             "data": function ( d ) {
                 return JSON.stringify(d); // NOTE: you also need to stringify POST payload
             }
         }
     });
	
	 
 });