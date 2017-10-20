package ohopro.com.ohopro.domains;

import java.util.List;

public class VendorAgreementDomain {
    private String lastUpdatedBy;
    private List<DocumentsItem> documents;
    private String docId;
    private boolean docAttached;
    private String vendorId;
    private String description;
    private String agreementExpirationDate;
    private String vendorName;
    private long createdOn;
    private String createdBy;
    private String docCreatedBy;
    private String agreementStartDate;
    private String agreementDocUrl;
    private String lastUpdatedOn;
    private String location;

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setDocuments(List<DocumentsItem> documents) {
        this.documents = documents;
    }

    public List<DocumentsItem> getDocuments() {
        return documents;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocAttached(boolean docAttached) {
        this.docAttached = docAttached;
    }

    public boolean isDocAttached() {
        return docAttached;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAgreementExpirationDate(String agreementExpirationDate) {
        this.agreementExpirationDate = agreementExpirationDate;
    }

    public String getAgreementExpirationDate() {
        return agreementExpirationDate;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setDocCreatedBy(String docCreatedBy) {
        this.docCreatedBy = docCreatedBy;
    }

    public String getDocCreatedBy() {
        return docCreatedBy;
    }

    public void setAgreementStartDate(String agreementStartDate) {
        this.agreementStartDate = agreementStartDate;
    }

    public String getAgreementStartDate() {
        return agreementStartDate;
    }

    public void setAgreementDocUrl(String agreementDocUrl) {
        this.agreementDocUrl = agreementDocUrl;
    }

    public String getAgreementDocUrl() {
        return agreementDocUrl;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return
                "VendorAgreementDomain{" +
                        "lastUpdatedBy = '" + lastUpdatedBy + '\'' +
                        ",documents = '" + documents + '\'' +
                        ",docId = '" + docId + '\'' +
                        ",docAttached = '" + docAttached + '\'' +
                        ",vendorId = '" + vendorId + '\'' +
                        ",description = '" + description + '\'' +
                        ",agreementExpirationDate = '" + agreementExpirationDate + '\'' +
                        ",vendorName = '" + vendorName + '\'' +
                        ",createdOn = '" + createdOn + '\'' +
                        ",createdBy = '" + createdBy + '\'' +
                        ",docCreatedBy = '" + docCreatedBy + '\'' +
                        ",agreementStartDate = '" + agreementStartDate + '\'' +
                        ",agreementDocUrl = '" + agreementDocUrl + '\'' +
                        ",lastUpdatedOn = '" + lastUpdatedOn + '\'' +
                        ",location = '" + location + '\'' +
                        "}";
    }
}