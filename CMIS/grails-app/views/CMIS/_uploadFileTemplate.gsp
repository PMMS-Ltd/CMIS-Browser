<div class="modal fade" id="uploadFile" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Upload File</h4>
			</div>
			<g:uploadForm action="uploadDocument" class="form">
				<div class="modal-body">
					<input type="hidden" name="Id" value="${folder.'cmis:objectId'}" />
					<div class="form-group">
						<input type="file" name="myFile" class="form-control" />
					</div>
				</div>
				<div class="modal-footer">
					<div class="form-group">
						<button type="submit" name="upload" id="upload"
							class="btn btn-sm btn-info">
							<i class="fa fa-upload"></i> Upload
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					</div>
				</div>
			</g:uploadForm>
		</div>
	</div>
</div>