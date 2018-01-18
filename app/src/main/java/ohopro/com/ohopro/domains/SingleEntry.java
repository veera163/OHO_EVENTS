package ohopro.com.ohopro.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by home on 1/10/2018.
 */

public class SingleEntry {
    @SerializedName("productCalendarEntryId")
    @Expose
    private String productCalendarEntryId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("productQuantity")
    @Expose
    private String productQuantity;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("vendorId")
    @Expose
    private String vendorId;
    @SerializedName("productCalendarEntries")
    @Expose
    private List<SingleDateRes> productCalendarEntries = null;
    @SerializedName("draggable")
    @Expose
    private Boolean draggable;

    public String getProductCalendarEntryId() {
        return productCalendarEntryId;
    }

    public void setProductCalendarEntryId(String productCalendarEntryId) {
        this.productCalendarEntryId = productCalendarEntryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public List<SingleDateRes> getProductCalendarEntries() {
        return productCalendarEntries;
    }

    public void setProductCalendarEntries(List<SingleDateRes> productCalendarEntries) {
        this.productCalendarEntries = productCalendarEntries;
    }

    public Boolean getDraggable() {
        return draggable;
    }

    public void setDraggable(Boolean draggable) {
        this.draggable = draggable;
    }

    public Boolean getResizable() {
        return resizable;
    }

    public void setResizable(Boolean resizable) {
        this.resizable = resizable;
    }

    public List<NONAvailableDates> getNonAvailableDates() {
        return nonAvailableDates;
    }

    public void setNonAvailableDates(List<NONAvailableDates> nonAvailableDates) {
        this.nonAvailableDates = nonAvailableDates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("resizable")

    @Expose
    private Boolean resizable;
    @SerializedName("nonAvailableDates")
    @Expose
    private List<NONAvailableDates> nonAvailableDates = null;

    @SerializedName("type")
    @Expose
    private String type;
}
