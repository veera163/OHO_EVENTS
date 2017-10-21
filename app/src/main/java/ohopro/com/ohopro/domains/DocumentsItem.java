package ohopro.com.ohopro.domains;

public class DocumentsItem{
	private String docName;
	private Object id;
	private String url;

	public void setDocName(String docName){
		this.docName = docName;
	}

	public String getDocName(){
		return docName;
	}

	public void setId(Object id){
		this.id = id;
	}

	public Object getId(){
		return id;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"DocumentsItem{" + 
			"docName = '" + docName + '\'' + 
			",id = '" + id + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}
