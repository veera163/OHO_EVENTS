package ohopro.com.ohopro.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by home on 1/2/2018.
 */

public class ComboRes {
    @SerializedName("productComboId")
    @Expose
    private String productComboId;
    @SerializedName("productGroup")
    @Expose
    private String productGroup;
    @SerializedName("productOrServiceCategory")
    @Expose
    private String productOrServiceCategory;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("productComboName")
    @Expose
    private String productComboName;

    public String getProductComboId() {
        return productComboId;
    }

    public void setProductComboId(String productComboId) {
        this.productComboId = productComboId;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getProductOrServiceCategory() {
        return productOrServiceCategory;
    }

    public void setProductOrServiceCategory(String productOrServiceCategory) {
        this.productOrServiceCategory = productOrServiceCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductComboName() {
        return productComboName;
    }

    public void setProductComboName(String productComboName) {
        this.productComboName = productComboName;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("productImages")

    @Expose
    private List<ProductImage> productImages = null;
    @SerializedName("vendorId")
    @Expose
    private String vendorId;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("vendorPhoneNumber")
    @Expose
    private String vendorPhoneNumber;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("price")
    @Expose
    private Object price;
   @SerializedName("status")
    @Expose
    private String status;

}
