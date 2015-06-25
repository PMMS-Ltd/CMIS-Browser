package cmis

import grails.converters.JSON
import org.springframework.web.multipart.commons.CommonsMultipartFile

class CMISController {
	def CMISService
    def grailsApplication
	
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
		redirect(action: "list", params: [Id: params.Id])810
	}
	def deleteDocument(){
		CMISService.deleteDocument(params.Id)
		redirect(action: "list", params: [Id: params.folder])
	}
	def downloadDocument(){
		def output = CMISService.getDocument(params.documentId)
		def settings = CMISService.getQueryResults("select cmis:name, cmis:contentStreamMimeType from cmis:document where cmis:objectId='"+params.documentId+"'")
		def filename = (String)settings.name.toString().substring(1,settings.name.toString().length()-1)
		if (output) {
			response.setHeader("Content-disposition", "attachment; filename=" + filename);
		 response.setContentType(settings.contentStreamMimeType)
		 response.outputStream << output
		}
	}
	def viewDocument(){
		def output = CMISService.getDocument(params.id)
		def settings = CMISService.getQueryResults("select cmis:name, cmis:contentStreamMimeType, cmis:contentStreamLength from cmis:document where cmis:objectId='"+params.id+"'")
		if (output) {
			response.setHeader("Content-Disposition", "inline")
			response.setContentLength(settings['contentStreamLength'][0]);
		 response.setContentType(settings['contentStreamMimeType'][0])
		 response.outputStream << output
		 response.outputStream.flush()
		}
	}
	def query() {
		params.max ? params.max : 25
		params.offset ? params.offset : 0
		//render request.JSON?.q
		//def query = request.JSON?.q
		def query = params.q
		render CMISService.getQueryResults(query, 50, 0) as JSON
	}
	def getImageMetadata() {
		def query = "select cmis:description, cmis:versionSeriesId, cmis:name from cmis:document where cmis:contentStreamMimeType='image/jpeg' and in_folder('" + params.id + "')"
		def imageList = CMISService.getQueryResults(query,200,0)
		imageList.each(){
			it['url'] = '' + grailsApplication.config.grails.serverURL + '/CMIS/viewDocument/' + it.versionSeriesId	
		}
		render imageList as JSON
	}
}
