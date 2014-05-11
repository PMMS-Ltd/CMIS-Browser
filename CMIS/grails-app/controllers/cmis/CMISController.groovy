package cmis

import grails.converters.JSON
import org.springframework.web.multipart.commons.CommonsMultipartFile

class CMISController {
	def CMISService
    
	def index() {
		redirect(action: "list")
	}
	
	def list(){
		def folderId
		if (!params.Id){
			 folderId = CMISService.getRootFolder()
		}else{
			folderId = params.Id
		}
		String query = "select cmis:name, cmis:objectId, cmis:contentStreamLength, cmis:contentStreamMimeType from cmis:document where in_folder('" + folderId + "')"
		[files : CMISService.getQueryResults(query, 10, 0), subFolders : CMISService.getChildren(folderId), parent : CMISService.getFolderParent(folderId), tree : CMISService.getFolderHierarchy(folderId), folder: CMISService.getFolder(folderId)]
	}
	
	def createFolder(){
		CMISService.createFolder(params.folderName,params.parentFolder, params.folderTitle)
		
		redirect(action: "list", params: [Id: params.parentFolder])
		//render params as JSON
	}
	def uploadDocument(){
		
		def CommonsMultipartFile uploadedFile = params.myFile
				
		CMISService.createDocument(params.Id, uploadedFile)
		redirect(action: "list", params: [Id: params.Id])
	}
}
