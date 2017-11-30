package ohopro.com.ohopro.domains;

public class AddressDomain{
	private String country;
	private String pincode;
	private String phoneNumber;
	private String city;
	private String contactName;
	private String otherCity;
	private String addressLine1;
	private String addressLine2;
	private String state;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setPincode(String pincode){
		this.pincode = pincode;
	}

	public String getPincode(){
		return pincode;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setContactName(String contactName){
		this.contactName = contactName;
	}

	public String getContactName(){
		return contactName;
	}

	public void setOtherCity(String otherCity){
		this.otherCity = otherCity;
	}

	public String getOtherCity(){
		return otherCity;
	}

	public void setAddressLine1(String addressLine1){
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine1(){
		return addressLine1;
	}

	public void setAddressLine2(String addressLine2){
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine2(){
		return addressLine2;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	@Override
 	public String toString(){
		return 
			"AddressDomain{" + 
			"country = '" + country + '\'' + 
			",pincode = '" + pincode + '\'' + 
			",phoneNumber = '" + phoneNumber + '\'' + 
			",city = '" + city + '\'' + 
			",contactName = '" + contactName + '\'' + 
			",otherCity = '" + otherCity + '\'' + 
			",addressLine1 = '" + addressLine1 + '\'' + 
			",addressLine2 = '" + addressLine2 + '\'' + 
			",state = '" + state + '\'' + 
			"}";
		}
}
