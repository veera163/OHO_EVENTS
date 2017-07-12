package ohopro.com.ohopro.domains;

import android.os.Parcel;
import android.os.Parcelable;

public class LeaveResponse implements Parcelable {
    private String employeeName;
    private String reason;
    private String comments;
    private String endDate;
    private String emergencyContact;
    private long submittedOn;
    private String approvedBy;
    private String emailId;
    private String numberOfDays;
    private String updatedOn;
    private String phoneNumber;
    private String leaveRequestId;
    private String startDate;
    private String status;

    protected LeaveResponse(Parcel in) {
        employeeName = in.readString();
        reason = in.readString();
        comments = in.readString();
        endDate = in.readString();
        emergencyContact = in.readString();
        submittedOn = in.readLong();
        approvedBy = in.readString();
        emailId = in.readString();
        numberOfDays = in.readString();
        updatedOn = in.readString();
        phoneNumber = in.readString();
        leaveRequestId = in.readString();
        startDate = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(employeeName);
        dest.writeString(reason);
        dest.writeString(comments);
        dest.writeString(endDate);
        dest.writeString(emergencyContact);
        dest.writeLong(submittedOn);
        dest.writeString(approvedBy);
        dest.writeString(emailId);
        dest.writeString(numberOfDays);
        dest.writeString(updatedOn);
        dest.writeString(phoneNumber);
        dest.writeString(leaveRequestId);
        dest.writeString(startDate);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LeaveResponse> CREATOR = new Creator<LeaveResponse>() {
        @Override
        public LeaveResponse createFromParcel(Parcel in) {
            return new LeaveResponse(in);
        }

        @Override
        public LeaveResponse[] newArray(int size) {
            return new LeaveResponse[size];
        }
    };

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setSubmittedOn(long submittedOn) {
        this.submittedOn = submittedOn;
    }

    public long getSubmittedOn() {
        return submittedOn;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setNumberOfDays(String numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getNumberOfDays() {
        return numberOfDays;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setLeaveRequestId(String leaveRequestId) {
        this.leaveRequestId = leaveRequestId;
    }

    public String getLeaveRequestId() {
        return leaveRequestId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
