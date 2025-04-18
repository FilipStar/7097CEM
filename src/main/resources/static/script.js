var currentMaterialId = 0;

function openForm(materialId, materialTitle, materialDesc, materialContent) {
  	document.getElementById("materialFormContainer").style.display = "flex";
  	document.getElementById("materialTitle").value = materialTitle;
  	document.getElementById("materialDescription").value = materialDesc;
  	document.getElementById("materialContent").value = materialContent;
  	
  	currentMaterialId = materialId;
}

function closeForm() {
  	document.getElementById("materialFormContainer ").style.display = "none";
  	console.log("test");
}