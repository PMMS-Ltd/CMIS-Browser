<div class="modal fade" id="addFolder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Add New Folder</h4>
      </div>
      <g:form name="myForm" url="[action:'createFolder',controller:'CMIS']" class="form">
      <div class="modal-body">
    	<div class="form-group">
			<label for="folderName" class="control-label">Folder Name</label>
			<input name="folderName" id="folderName" type="text" class="form-control" />
		</div>
		<div class="form-group">
			<label for="folderTitle" class="control-label">Folder Title</label>
			<input name="folderTitle" id="folderTitle" type="text" class="form-control"/>
		</div>
		<input type="hidden" id="parentFolder" name="parentFolder" value="${params.Id}"/>
      </div>
      <div class="modal-footer">
		<button type="submit" name="add" class="btn btn-sm btn-success">
			<span class="glyphicon glyphicon-plus"></span> Add
		</button>
		<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
      </div>
      </g:form>
    </div>
  </div>
</div>