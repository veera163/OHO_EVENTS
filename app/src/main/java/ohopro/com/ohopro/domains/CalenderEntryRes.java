package ohopro.com.ohopro.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by home on 1/2/2018.
 */

public class CalenderEntryRes {
    @SerializedName("productCalendarEntryId")
    @Expose
    private String productCalendarEntryId;
    @SerializedName("productName")
    @Expose
    private Object productName;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("productQuantity")
    @Expose
    private String productQuantity;

    public String getProductCalendarEntryId() {
        return productCalendarEntryId;
    }

    public void setProductCalendarEntryId(String productCalendarEntryId) {
        this.productCalendarEntryId = productCalendarEntryId;
    }

    public Object getProductName() {
        return productName;
    }

    public void setProductName(Object productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Object getVendorName() {
        return vendorName;
    }

    public void setVendorName(Object vendorName) {
        this.vendorName = vendorName;
    }

    public Object getVendorId() {
        return vendorId;
    }

    public void setVendorId(Object vendorId) {
        this.vendorId = vendorId;
    }

    @SerializedName("vendorName")
    @Expose

    private Object vendorName;
    @SerializedName("vendorId")
    @Expose
    private Object vendorId;

    @SerializedName("productCalendarEntries")
    @Expose
    private List<ProductCalendarEntry> productCalendarEntries = null;


    public List<ProductCalendarEntry> getProductCalendarEntries() {
        return productCalendarEntries;
    }

    public void setProductCalendarEntries(List<ProductCalendarEntry> productCalendarEntries) {
        this.productCalendarEntries = productCalendarEntries;
    }

}
