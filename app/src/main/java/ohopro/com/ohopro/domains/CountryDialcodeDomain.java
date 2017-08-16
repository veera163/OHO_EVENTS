package ohopro.com.ohopro.domains;

public class CountryDialcodeDomain{
	private String code;
	private String dial_code;
	private String name;
	private String id;

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setDial_code(String dial_code){
		this.dial_code = dial_code;
	}

	public String getDial_code(){
		return dial_code;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"CountryDialcodeDomain{" + 
			"code = '" + code + '\'' + 
			",dial_code = '" + dial_code + '\'' +
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
