package ohopro.com.ohopro.domains;

public class ErrorDomain{
	private String error_description;
	private String error;

	public void setError_description(String error_description){
		this.error_description = error_description;
	}

	public String getError_description(){
		return error_description;
	}

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}
}
