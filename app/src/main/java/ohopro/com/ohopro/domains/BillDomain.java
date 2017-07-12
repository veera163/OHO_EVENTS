package ohopro.com.ohopro.domains;

import android.os.Parcel;
import android.os.Parcelable;

public class BillDomain implements Parcelable {
    private String billImgaeUrl;
    private String comments;
    private String billImage;
    private String approvedBy;
    private String emailId;
    private String billDate;
    private String billImageName;
    private String userId;
    private String approvedOn;
    private String billAmount;
    private String phone;
    private String billId;
    private String billNumber;
    private String billImageUrl;
    private String billProvider;
    private String status;
    private String purpose;

    protected BillDomain(Parcel in) {
        billImgaeUrl = in.readString();
        comments = in.readString();
        billImage = in.readString();
        approvedBy = in.readString();
        emailId = in.readString();
        billDate = in.readString();
        billImageName = in.readString();
        userId = in.readString();
        approvedOn = in.readString();
        billAmount = in.readString();
        phone = in.readString();
        billId = in.readString();
        billNumber = in.readString();
        billImageUrl = in.readString();
        billProvider = in.readString();
        status = in.readString();
        purpose = in.readString();
    }

    public static final Creator<BillDomain> CREATOR = new Creator<BillDomain>() {
        @Override
        public BillDomain createFromParcel(Parcel in) {
            return new BillDomain(in);
        }

        @Override
        public BillDomain[] newArray(int size) {
            return new BillDomain[size];
        }
    };

    public void setBillImgaeUrl(String billImgaeUrl) {
        this.billImgaeUrl = billImgaeUrl;
    }

    public String getBillImgaeUrl() {
        return billImgaeUrl;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setBillImage(String billImage) {
        this.billImage = billImage;
    }

    public String getBillImage() {
        return billImage;
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

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillImageName(String billImageName) {
        this.billImageName = billImageName;
    }

    public String getBillImageName() {
        return billImageName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setApprovedOn(String approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getApprovedOn() {
        return approvedOn;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillImageUrl(String billImageUrl) {
        this.billImageUrl = billImageUrl;
    }

    public String getBillImageUrl() {
        return billImageUrl;
    }

    public void setBillProvider(String billProvider) {
        this.billProvider = billProvider;
    }

    public String getBillProvider() {
        return billProvider;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(billImgaeUrl);
        parcel.writeString(comments);
        parcel.writeString(billImage);
        parcel.writeString(approvedBy);
        parcel.writeString(emailId);
        parcel.writeString(billDate);
        parcel.writeString(billImageName);
        parcel.writeString(userId);
        parcel.writeString(approvedOn);
        parcel.writeString(billAmount);
        parcel.writeString(phone);
        parcel.writeString(billId);
        parcel.writeString(billNumber);
        parcel.writeString(billImageUrl);
        parcel.writeString(billProvider);
        parcel.writeString(status);
        parcel.writeString(purpose);
    }
}
