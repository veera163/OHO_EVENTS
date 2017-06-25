package ohopro.com.ohopro.domains;

import java.util.List;

public class UserDomain{
	private String firstName;
	private String phoneNumber;
	private List<UserOperationItem> userOperation;
	private String location;
	private String emailId;
	private String id;
	private String type;
	private String userName;

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setUserOperation(List<UserOperationItem> userOperation){
		this.userOperation = userOperation;
	}

	public List<UserOperationItem> getUserOperation(){
		return userOperation;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setEmailId(String emailId){
		this.emailId = emailId;
	}

	public String getEmailId(){
		return emailId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}
}