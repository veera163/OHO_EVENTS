package ohopro.com.ohopro.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by home on 12/27/2017.
 */

public class ProductAvailabityRes {
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("productOrServiceCategory")
    @Expose
    private String productOrServiceCategory;
    @SerializedName("productGroup")
    @Expose
    private String productGroup;
    @SerializedName("productConfiguration")
    @Expose
    private ProductConfiguration productConfiguration;
    @SerializedName("productDetail")
    @Expose
    private List<ProductDetail> productDetail = null;
    @SerializedName("foodMenuGenaralData")
    @Expose
    private Object foodMenuGenaralData;
    @SerializedName("productGeneralData")
    @Expose
    private ProductGeneralData productGeneralData;
    @SerializedName("foodMenuImageUrl")
    @Expose
    private List<FoodMenuImageUrl> foodMenuImageUrl = null;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("productDescription")
    @Expose
    private Object productDescription;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("price")
    @Expose
    private Object price;
    @SerializedName("charges")
    @Expose
    private Object charges;
    @SerializedName("ohoEventcharges")
    @Expose
    private Object ohoEventcharges;
    @SerializedName("ohoEventDiscounts")
    @Expose
    private Object ohoEventDiscounts;
    @SerializedName("shipmentPriceHolder")
    @Expose
    private Object shipmentPriceHolder;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("vendorId")
    @Expose
    private Object vendorId;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("vendorPhoneNumber")
    @Expose
    private String vendorPhoneNumber;
    @SerializedName("productType")
    @Expose
    private Object productType;
    @SerializedName("bookingScheduleId")
    @Expose
    private Object bookingScheduleId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("productPriceId")
    @Expose
    private String productPriceId;
    @SerializedName("termsConds")
    @Expose
    private Object termsConds;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("locations")
    @Expose
    private Object locations;
    @SerializedName("productLocation")
    @Expose
    private List<ProductLocation> productLocation = null;
    @SerializedName("productLocations")
    @Expose
    private String productLocations;
    @SerializedName("linkedproducts")
    @Expose
    private Object linkedproducts;
    @SerializedName("linkedProductImageUrls")
    @Expose
    private Object linkedProductImageUrls;
    @SerializedName("linkedProductAttributeDetails")
    @Expose
    private Object linkedProductAttributeDetails;
    @SerializedName("reviewComments")
    @Expose
    private Object reviewComments;
    @SerializedName("cancelPolicy")
    @Expose
    private Object cancelPolicy;
    @SerializedName("returnPolicy")
    @Expose
    private Object returnPolicy;
    @SerializedName("organizationName")
    @Expose
    private String organizationName;
    @SerializedName("conversation")
    @Expose
    private Object conversation;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("subProductOrServiceType")
    @Expose
    private Object subProductOrServiceType;
    @SerializedName("productCalendarEntryId")
    @Expose
    private Object productCalendarEntryId;
    @SerializedName("productAvailability")
    @Expose
    private Object productAvailability;
    @SerializedName("nonAvailabilityReason")
    @Expose
    private Object nonAvailabilityReason;
    @SerializedName("updateOrigin")
    @Expose
    private Object updateOrigin;
    @SerializedName("foodMenuExtraFields")
    @Expose
    private Object foodMenuExtraFields;
    @SerializedName("saleOrServiceMode")
    @Expose
    private Object saleOrServiceMode;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductOrServiceCategory() {
        return productOrServiceCategory;
    }

    public void setProductOrServiceCategory(String productOrServiceCategory) {
        this.productOrServiceCategory = productOrServiceCategory;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public ProductConfiguration getProductConfiguration() {
        return productConfiguration;
    }

    public void setProductConfiguration(ProductConfiguration productConfiguration) {
        this.productConfiguration = productConfiguration;
    }

    public List<ProductDetail> getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(List<ProductDetail> productDetail) {
        this.productDetail = productDetail;
    }

    public Object getFoodMenuGenaralData() {
        return foodMenuGenaralData;
    }

    public void setFoodMenuGenaralData(Object foodMenuGenaralData) {
        this.foodMenuGenaralData = foodMenuGenaralData;
    }

    public ProductGeneralData getProductGeneralData() {
        return productGeneralData;
    }

    public void setProductGeneralData(ProductGeneralData productGeneralData) {
        this.productGeneralData = productGeneralData;
    }

    public List<FoodMenuImageUrl> getFoodMenuImageUrl() {
        return foodMenuImageUrl;
    }

    public void setFoodMenuImageUrl(List<FoodMenuImageUrl> foodMenuImageUrl) {
        this.foodMenuImageUrl = foodMenuImageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(Object productDescription) {
        this.productDescription = productDescription;
    }



    public Object getPrice() {
        return price;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setPrice(Object price) {

        this.price = price;
    }

    public Object getCharges() {
        return charges;
    }

    public void setCharges(Object charges) {
        this.charges = charges;
    }

    public Object getOhoEventcharges() {
        return ohoEventcharges;
    }

    public void setOhoEventcharges(Object ohoEventcharges) {
        this.ohoEventcharges = ohoEventcharges;
    }

    public Object getOhoEventDiscounts() {
        return ohoEventDiscounts;
    }

    public void setOhoEventDiscounts(Object ohoEventDiscounts) {
        this.ohoEventDiscounts = ohoEventDiscounts;
    }

    public Object getShipmentPriceHolder() {
        return shipmentPriceHolder;
    }

    public void setShipmentPriceHolder(Object shipmentPriceHolder) {
        this.shipmentPriceHolder = shipmentPriceHolder;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Object getVendorId() {
        return vendorId;
    }

    public void setVendorId(Object vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorPhoneNumber() {
        return vendorPhoneNumber;
    }

    public void setVendorPhoneNumber(String vendorPhoneNumber) {
        this.vendorPhoneNumber = vendorPhoneNumber;
    }

    public Object getProductType() {
        return productType;
    }

    public void setProductType(Object productType) {
        this.productType = productType;
    }

    public Object getBookingScheduleId() {
        return bookingScheduleId;
    }

    public void setBookingScheduleId(Object bookingScheduleId) {
        this.bookingScheduleId = bookingScheduleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductPriceId() {
        return productPriceId;
    }

    public void setProductPriceId(String productPriceId) {
        this.productPriceId = productPriceId;
    }

    public Object getTermsConds() {
        return termsConds;
    }

    public void setTermsConds(Object termsConds) {
        this.termsConds = termsConds;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Object getLocations() {
        return locations;
    }

    public void setLocations(Object locations) {
        this.locations = locations;
    }

    public List<ProductLocation> getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(List<ProductLocation> productLocation) {
        this.productLocation = productLocation;
    }

    public String getProductLocations() {
        return productLocations;
    }

    public void setProductLocations(String productLocations) {
        this.productLocations = productLocations;
    }

    public Object getLinkedproducts() {
        return linkedproducts;
    }

    public void setLinkedproducts(Object linkedproducts) {
        this.linkedproducts = linkedproducts;
    }

    public Object getLinkedProductImageUrls() {
        return linkedProductImageUrls;
    }

    public void setLinkedProductImageUrls(Object linkedProductImageUrls) {
        this.linkedProductImageUrls = linkedProductImageUrls;
    }

    public Object getLinkedProductAttributeDetails() {
        return linkedProductAttributeDetails;
    }

    public void setLinkedProductAttributeDetails(Object linkedProductAttributeDetails) {
        this.linkedProductAttributeDetails = linkedProductAttributeDetails;
    }

    public Object getReviewComments() {
        return reviewComments;
    }

    public void setReviewComments(Object reviewComments) {
        this.reviewComments = reviewComments;
    }

    public Object getCancelPolicy() {
        return cancelPolicy;
    }

    public void setCancelPolicy(Object cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
    }

    public Object getReturnPolicy() {
        return returnPolicy;
    }

    public void setReturnPolicy(Object returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Object getConversation() {
        return conversation;
    }

    public void setConversation(Object conversation) {
        this.conversation = conversation;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Object getSubProductOrServiceType() {
        return subProductOrServiceType;
    }

    public void setSubProductOrServiceType(Object subProductOrServiceType) {
        this.subProductOrServiceType = subProductOrServiceType;
    }

    public Object getProductCalendarEntryId() {
        return productCalendarEntryId;
    }

    public void setProductCalendarEntryId(Object productCalendarEntryId) {
        this.productCalendarEntryId = productCalendarEntryId;
    }

    public Object getProductAvailability() {
        return productAvailability;
    }

    public void setProductAvailability(Object productAvailability) {
        this.productAvailability = productAvailability;
    }

    public Object getNonAvailabilityReason() {
        return nonAvailabilityReason;
    }

    public void setNonAvailabilityReason(Object nonAvailabilityReason) {
        this.nonAvailabilityReason = nonAvailabilityReason;
    }

    public Object getUpdateOrigin() {
        return updateOrigin;
    }

    public void setUpdateOrigin(Object updateOrigin) {
        this.updateOrigin = updateOrigin;
    }

    public Object getFoodMenuExtraFields() {
        return foodMenuExtraFields;
    }

    public void setFoodMenuExtraFields(Object foodMenuExtraFields) {
        this.foodMenuExtraFields = foodMenuExtraFields;
    }

    public Object getSaleOrServiceMode() {
        return saleOrServiceMode;
    }

    public void setSaleOrServiceMode(Object saleOrServiceMode) {
        this.saleOrServiceMode = saleOrServiceMode;
    }


}
