package ohopro.com.ohopro.domains;

import java.util.ArrayList;

public class VendorReqDomain {
    private String title;
    private String firstName;
    private String surname;
    private String designation;
    private String companyName;
    private String email;
    private String website;
    private String telephone;
    private String addressLine1;
    private String addressLine2;
    private String town;
    private String city;
    private String postalCode;
    ;
    private String country;
    private String productOrServiceGroup;
    private String productOrServiceType;
    private String productsOrServices;
    private ArrayList<ServiceOrSupplyLocations> serviceOrSupplyLocations;
    private String enquiryType;
    private String followUpdate;
    private String followAssignedTo;
    private String detailsEnquiry;
    private String dateOfEnquiry;
    private String timeOfEnquiry;
    private String enquiredEmployee;
    private String enquiredEmployeePhone;
    private String createdOn;
    private String enquiryFormId;
    private String updatedBy;
    private String updatedOn;
    private String createdBy;
    private String status;
    private String state;
    private String otherEnquiryType;

    public String getOtherEnquiryType() {
        return otherEnquiryType;
    }

    public void setOtherEnquiryType(String otherEnquiryType) {
        this.otherEnquiryType = otherEnquiryType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setTimeOfEnquiry(String timeOfEnquiry) {
        this.timeOfEnquiry = timeOfEnquiry;
    }

    public String getTimeOfEnquiry() {
        return timeOfEnquiry;
    }

    public void setFollowUpdate(String followUpdate) {
        this.followUpdate = followUpdate;
    }

    public String getFollowUpdate() {
        return followUpdate;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setProductsOrServices(String productsOrServices) {
        this.productsOrServices = productsOrServices;
    }

    public String getProductsOrServices() {
        return productsOrServices;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setServiceOrSupplyLocations(ArrayList<ServiceOrSupplyLocations> serviceOrSupplyLocations) {
        this.serviceOrSupplyLocations = serviceOrSupplyLocations;
    }

    public ArrayList<ServiceOrSupplyLocations> getServiceOrSupplyLocations() {
        return serviceOrSupplyLocations;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setFollowAssignedTo(String followAssignedTo) {
        this.followAssignedTo = followAssignedTo;
    }

    public String getFollowAssignedTo() {
        return followAssignedTo;
    }

    public void setProductOrServiceGroup(String productOrServiceGroup) {
        this.productOrServiceGroup = productOrServiceGroup;
    }

    public String getProductOrServiceGroup() {
        return productOrServiceGroup;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setEnquiryFormId(String enquiryFormId) {
        this.enquiryFormId = enquiryFormId;
    }

    public String getEnquiryFormId() {
        return enquiryFormId;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEnquiredEmployee(String enquiredEmployee) {
        this.enquiredEmployee = enquiredEmployee;
    }

    public String getEnquiredEmployee() {
        return enquiredEmployee;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getTown() {
        return town;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setProductOrServiceType(String productOrServiceType) {
        this.productOrServiceType = productOrServiceType;
    }

    public String getProductOrServiceType() {
        return productOrServiceType;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEnquiryType(String enquiryType) {
        this.enquiryType = enquiryType;
    }

    public String getEnquiryType() {
        return enquiryType;
    }

    public void setDetailsEnquiry(String detailsEnquiry) {
        this.detailsEnquiry = detailsEnquiry;
    }

    public String getDetailsEnquiry() {
        return detailsEnquiry;
    }

    public void setEnquiredEmployeePhone(String enquiredEmployeePhone) {
        this.enquiredEmployeePhone = enquiredEmployeePhone;
    }

    public String getEnquiredEmployeePhone() {
        return enquiredEmployeePhone;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDateOfEnquiry(String dateOfEnquiry) {
        this.dateOfEnquiry = dateOfEnquiry;
    }

    public String getDateOfEnquiry() {
        return dateOfEnquiry;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


}
