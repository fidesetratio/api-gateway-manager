


function openRoleDialog(){
	var form = $("#addrolesform");
	form.find("input[type=text], textarea").val("");
	form.find(".help-block").remove();
	form.find(".has-error").removeClass('.input-icon right has-error');
	
	var w = $("#addRoleModal");
	w.modal("toggle");
}

function submitajax(id){$
	$form = $("#"+id);
	$groupId = 	 $("#groupId").text();
	$categoryId =  $("#categoryId").val();
$.ajax({
    url: $form.attr('action'),
    type: 'post',
    data: $form.serialize(),
    success: function(response) {
    	var r = $(response);
    	if(r.find("#groupId").length){
    		r.find("#groupId").text($groupId);
    	}
    	if(r.find("#categoryId").length){
    		r.find("#categoryId").val($categoryId);
    	}
    	
    	// if the response contains any errors, replace the form
      if (r.find('.has-error').length) {
    	  $form.replaceWith(r);
      	} else {
      		$form.find("input[type=text], textarea").val("");
      		$form.find(".help-block").remove();
      		$form.find(".has-error").removeClass('.input-icon right has-error');
      		$("#addRoleModal").modal("hide");
      		$.get("/role/list-roles",{cat:$categoryId},function(data){
      			$("#table-panel").html(data);
      		});
     		
      	
      	}
      }
	});
	return false;
}

function oncategoryselect(id){
	$select = $("#"+id);
	 var category = $select.val();
	 var display = $select.find("option:selected").text();
	 if(category!=0){
		 $("#openAddRole").prop( "disabled", false );

		 $("#groupId").text(display);
		 $("#categoryId").val(category);
		 $.get("/role/list-roles",{cat:category},function(data){
			 $("#table-panel").html(data);
		 });
		 
	 }else if(category == 0){
		 $("#openAddRole").prop( "disabled", true );
	 }




}


function deleteItem(item){
	var i = $(item);
    var entityId = i.attr('data-entity-id');
    var catid = i.attr('data-cat-entityid');
    
    $('.remove_item').attr('data-entity-id', entityId);
    $('.remove_item').attr('data-cat-entityid', catid);

}

function confirmDelete(item){
	var i = $(item);
    var entityId = i.attr('data-entity-id');
    var catid = i.attr('data-cat-entityid');
    $.get("/role/delete-role",{roleid:entityId},function(data){
    	var w = $("#confirmDeleteModal");
    	w.on("hidden.bs.modal",function(e){
    		 $.get("/role/list-roles",{cat:catid},function(data){
    			 $("#table-panel").html(data);
    		 });
    		 
    	})
    	w.modal("hide");
    	
    });
	
    
	
    
    /*
    */
    
  
	return false;
}