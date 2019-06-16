
function openAddLink(){
	var form = $("#addForm");
	form.find(".help-block").remove();
	form.find(".has-error").removeClass('.input-icon right has-error');
	var roleCategory=$("#roleCategory");
	var roleText=$("#roleText");
	
	var pickup = form.find("#rolePickup");

	
	roleCategory.show();
	roleText.hide();

	pickup.change(function(){
		
		if($(this).val()==1){
			roleCategory.show();
			roleText.hide();
		}
		if($(this).val()==2){
			roleCategory.hide();
			roleText.show();
		}
	});
	
	
	
	
	
	
	var w = $("#addModal");
	w.modal("toggle");
}



function submitajax(id){
	$form = $("#"+id);

$.ajax({
    url: $form.attr('action'),
    type: 'post',
    data: $form.serialize(),
    success: function(response) {
    	var r = $(response);
    	
    	// if the response contains any errors, replace the form
      if (r.find('.has-error').length) {
    	  	var roleCategory=r.find("#roleCategory");
    		var roleText=r.find("#roleText");
    		
    		var pickup = r.find("#rolePickup");

    		
    		
    		
    		roleCategory.show();
    		roleText.hide();

    		pickup.change(function(){
    			if($(this).val()==1){
    				roleCategory.show();
    				roleText.hide();
    			}
    			if($(this).val()==2){
    				roleCategory.hide();
    				roleText.show();
    			}
    		});

    		$form.replaceWith(r);
      	  
    	  
      } else {
      		$form.find("input[type=text], textarea").val("");
      		$form.find(".help-block").remove();
      		$form.find(".has-error").removeClass('.input-icon right has-error');
      		$("#addModal").modal("hide");
      		
      		$.get("/links/list-links",function(data){
      			$("#table-panel").html(data);
      		});
     	
      		      	
      	}
      }
	});
	return false;
}



function submitupdateajax(id){$
	$form = $("#"+id);

$.ajax({
    url: $form.attr('action'),
    type: 'post',
    data: $form.serialize(),
    success: function(response) {
    	var r = $(response);
    	
    	// if the response contains any errors, replace the form
      if (r.find('.has-error').length) {
    	  
    	  
    	  var roleCategory=r.find("#roleCategory");
    		var roleText=r.find("#roleText");
    		
    		var pickup = r.find("#rolePickup");

    		
    		
    		
    		roleCategory.show();
    		roleText.hide();

    		pickup.change(function(){
    			if($(this).val()==1){
    				roleCategory.show();
    				roleText.hide();
    			}
    			if($(this).val()==2){
    				roleCategory.hide();
    				roleText.show();
    			}
    		});

    		$form.replaceWith(r);
      	  
    	  
      } else {
      		$form.find("input[type=text], textarea").val("");
      		$form.find(".help-block").remove();
      		$form.find(".has-error").removeClass('.input-icon right has-error');
      		$("#updateModel").modal("hide");
      		
      		
      		      	
      	}
      }
	});
	return false;
}



function onClickButtonLoading(t){
	
	var $this = $("#"+t);

	$this.button('loading');
	    setTimeout(function() {
	       $this.button('reset');
     		$.get("/links/reload-apigateway",function(data){
     			if(data=="ok"){
     				$("#alert").hide();     				
     			}else if(data=="error"){
     				$("#alert").show();
     			}
     		});

	    
	    }, 4000);
	   	
}


function deleteItem(item){
	var i = $(item);
    var entityId = i.attr('data-entity-id');
    $('.remove_item').attr('data-entity-id', entityId);
 
}

function confirmDelete(item){
	var i = $(item);
    var entityId = i.attr('data-entity-id');
    $.get("/links/delete-link",{linkId:entityId},function(data){
    	var w = $("#confirmDeleteModal");
    	w.on("hidden.bs.modal",function(e){
    		 $.get("/links/list-links",function(data){
    			 $("#table-panel").html(data);
    		 });
    		 
    	})
    	w.modal("hide");
    	
    });
	
    
    
    return false;
}



// update


function updateLinks(id){
	
	 $.get("/links/viewupdate",{linkId:id},function(data){
		 		
		 		if(data != "error"){
		 			$("#form-panel-update").html(data);
		 			var w = $("#updateModel");
		 			w.modal("toggle");

		 		}
		 	
		 
	    	})
	   
}

