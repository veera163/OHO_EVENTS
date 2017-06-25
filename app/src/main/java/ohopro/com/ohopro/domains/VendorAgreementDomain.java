package ohopro.com.ohopro.domains;

public class VendorAgreementDomain{
	private String lastUpdatedBy;
	private String docCreatedBy;
	private String agreementStartDate;
	private String agreementDocUrl;
	private String vendorId;
	private String description;
	private String lastUpdatedOn;
	private String agreementExpirationDate;
	private String vendorName;
	private String createdOn;

	public void setLastUpdatedBy(String lastUpdatedBy){
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedBy(){
		return lastUpdatedBy;
	}

	public void setDocCreatedBy(String docCreatedBy){
		this.docCreatedBy = docCreatedBy;
	}

	public String getDocCreatedBy(){
		return docCreatedBy;
	}

	public void setAgreementStartDate(String agreementStartDate){
		this.agreementStartDate = agreementStartDate;
	}

	public String getAgreementStartDate(){
		return agreementStartDate;
	}

	public void setAgreementDocUrl(String agreementDocUrl){
		this.agreementDocUrl = agreementDocUrl;
	}

	public String getAgreementDocUrl(){
		return agreementDocUrl;
	}

	public void setVendorId(String vendorId){
		this.vendorId = vendorId;
	}

	public String getVendorId(){
		return vendorId;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setLastUpdatedOn(String lastUpdatedOn){
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastUpdatedOn(){
		return lastUpdatedOn;
	}

	public void setAgreementExpirationDate(String agreementExpirationDate){
		this.agreementExpirationDate = agreementExpirationDate;
	}

	public String getAgreementExpirationDate(){
		return agreementExpirationDate;
	}

	public void setVendorName(String vendorName){
		this.vendorName = vendorName;
	}

	public String getVendorName(){
		return vendorName;
	}

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}
}
