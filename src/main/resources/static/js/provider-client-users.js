
function submitclient(id){
	$form = $("#"+id);
	$.ajax({
	    url: $form.attr('action'),
	    type: 'post',
	    data: $form.serialize(),
	    success: function(response) {
	    	var r = $(response);
	    	$form.replaceWith(r);
	    }
		});
}


function deleteItem(item){
	var i = $(item);
    var entityId = i.attr('data-entity-id');
    $('.remove_item').attr('data-entity-id', entityId);
}


function confirmDelete(item){
	var i = $(item);
    var entityId = i.attr('data-entity-id');

    $.get("/provider/deleteProvider",{clientId:entityId},function(data){
    	var w = $("#confirmDeleteModal");
    	w.on("hidden.bs.modal",function(e){
    		$(location).attr("href",'/provider/providerlist');
    	})
    	w.modal("hide");
    	
    });

}