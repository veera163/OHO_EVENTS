package ohopro.com.ohopro.domains;

public class CountryCodeDomain{
	private String code;
	private String iso;
	private String name;

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setIso(String iso){
		this.iso = iso;
	}

	public String getIso(){
		return iso;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"CountryCodeDomain{" + 
			"code = '" + code + '\'' + 
			",iso = '" + iso + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}
