package ohopro.com.ohopro.domains;

public class LeaveResponse{
	private String employeeName;
	private String reason;
	private Object comments;
	private String endDate;
	private String emergencyContact;
	private long submittedOn;
	private Object approvedBy;
	private String emailId;
	private String numberOfDays;
	private Object updatedOn;
	private String phoneNumber;
	private String leaveRequestId;
	private String startDate;
	private String status;

	public void setEmployeeName(String employeeName){
		this.employeeName = employeeName;
	}

	public String getEmployeeName(){
		return employeeName;
	}

	public void setReason(String reason){
		this.reason = reason;
	}

	public String getReason(){
		return reason;
	}

	public void setComments(Object comments){
		this.comments = comments;
	}

	public Object getComments(){
		return comments;
	}

	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return endDate;
	}

	public void setEmergencyContact(String emergencyContact){
		this.emergencyContact = emergencyContact;
	}

	public String getEmergencyContact(){
		return emergencyContact;
	}

	public void setSubmittedOn(long submittedOn){
		this.submittedOn = submittedOn;
	}

	public long getSubmittedOn(){
		return submittedOn;
	}

	public void setApprovedBy(Object approvedBy){
		this.approvedBy = approvedBy;
	}

	public Object getApprovedBy(){
		return approvedBy;
	}

	public void setEmailId(String emailId){
		this.emailId = emailId;
	}

	public String getEmailId(){
		return emailId;
	}

	public void setNumberOfDays(String numberOfDays){
		this.numberOfDays = numberOfDays;
	}

	public String getNumberOfDays(){
		return numberOfDays;
	}

	public void setUpdatedOn(Object updatedOn){
		this.updatedOn = updatedOn;
	}

	public Object getUpdatedOn(){
		return updatedOn;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setLeaveRequestId(String leaveRequestId){
		this.leaveRequestId = leaveRequestId;
	}

	public String getLeaveRequestId(){
		return leaveRequestId;
	}

	public void setStartDate(String startDate){
		this.startDate = startDate;
	}

	public String getStartDate(){
		return startDate;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}
