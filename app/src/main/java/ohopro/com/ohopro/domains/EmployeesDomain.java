package ohopro.com.ohopro.domains;

public class EmployeesDomain{
	private Object lastPasswordResetDate;
	private Object password;
	private String firstname;
	private Object phoneNumber;
	private Object emailId;
	private String id;
	private Object enabled;
	private Object authorities;
	private Object username;
	private String lastname;

	public void setLastPasswordResetDate(Object lastPasswordResetDate){
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public Object getLastPasswordResetDate(){
		return lastPasswordResetDate;
	}

	public void setPassword(Object password){
		this.password = password;
	}

	public Object getPassword(){
		return password;
	}

	public void setFirstname(String firstname){
		this.firstname = firstname;
	}

	public String getFirstname(){
		return firstname;
	}

	public void setPhoneNumber(Object phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public Object getPhoneNumber(){
		return phoneNumber;
	}

	public void setEmailId(Object emailId){
		this.emailId = emailId;
	}

	public Object getEmailId(){
		return emailId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEnabled(Object enabled){
		this.enabled = enabled;
	}

	public Object getEnabled(){
		return enabled;
	}

	public void setAuthorities(Object authorities){
		this.authorities = authorities;
	}

	public Object getAuthorities(){
		return authorities;
	}

	public void setUsername(Object username){
		this.username = username;
	}

	public Object getUsername(){
		return username;
	}

	public void setLastname(String lastname){
		this.lastname = lastname;
	}

	public String getLastname(){
		return lastname;
	}
}
