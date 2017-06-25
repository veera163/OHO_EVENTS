package ohopro.com.ohopro.domains;

public class ServiceGroupDomain {
	private String lastUpdatedBy;
	private String eventItemId;
	private String createdBy;
	private String imageUrl;
	private String description;
	private String lastUpdatedOn;
	private String createdOn;
	private String eventItemName;

	public void setLastUpdatedBy(String lastUpdatedBy){
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedBy(){
		return lastUpdatedBy;
	}

	public void setEventItemId(String eventItemId){
		this.eventItemId = eventItemId;
	}

	public String getEventItemId(){
		return eventItemId;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
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

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}

	public void setEventItemName(String eventItemName){
		this.eventItemName = eventItemName;
	}

	public String getEventItemName(){
		return eventItemName;
	}
}
