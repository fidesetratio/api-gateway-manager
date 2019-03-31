
function openAddCategory(){
	var form = $("#addForm");
	form.find("input[type=text], textarea").val("");
	form.find(".help-block").remove();
	form.find(".has-error").removeClass('.input-icon right has-error');
	var w = $("#addModal");
	w.modal("toggle");
}



function submitajax(id){$
	$form = $("#"+id);
$.ajax({
    url: $form.attr('action'),
    type: 'post',
    data: $form.serialize(),
    success: function(response) {
    	var r = $(response);
    	// if the response contains any errors, replace the form
      if (r.find('.has-error').length) {
    	
    		$form.replaceWith(r);
      	  
    	  
      } else {
      		$form.find("input[type=text], textarea").val("");
      		$form.find(".help-block").remove();
      		$form.find(".has-error").removeClass('.input-icon right has-error');
      		$("#addModal").modal("hide");
      		$.get("/category/list-categories",function(data){
      			$("#table-panel").html(data);
      		});
     	

      		      	
      	}
      }
	});
	
return false;
	
}

function deleteItem(item){
	var i = $(item);
    var entityId = i.attr('data-entity-id');
    $('.remove_item').attr('data-entity-id', entityId);
 
}

function confirmDelete(item){
	var i = $(item);
    var entityId = i.attr('data-entity-id');
    $.get("/category/delete-category",{categoryId:entityId},function(data){
    	var w = $("#confirmDeleteModal");
    	w.on("hidden.bs.modal",function(e){
    		$.get("/category/list-categories",function(data){
      			$("#table-panel").html(data);
      		});
     	 
    	})
    	w.modal("hide");
    	
    });
	
    
    
    return false;
}