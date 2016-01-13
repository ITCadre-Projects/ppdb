$(document).ready(function(){
	$("#documents_link").click(function(){
		$("#main_menu").hide();
		$("#add_documents_to_project").show();
	});
	
	$("#add_document_to_main_link").click(function(){
		$("#add_documents_to_project").hide();
		$("#main_menu").show();
	});
})