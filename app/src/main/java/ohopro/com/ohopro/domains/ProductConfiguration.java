package ohopro.com.ohopro.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by home on 12/27/2017.
 */

public class ProductConfiguration {

    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("productListPosition")
    @Expose
    private String productListPosition;
    @SerializedName("listingLocation")
    @Expose
    private Object listingLocation;
    @SerializedName("productDetailsPositions")
    @Expose
    private Object productDetailsPositions;
    @SerializedName("displayDetailsPositions")
    @Expose
    private Object displayDetailsPositions;
    @SerializedName("sellingType")
    @Expose
    private String sellingType;
    @SerializedName("fromDate")
    @Expose
    private Object fromDate;
    @SerializedName("toDate")
    @Expose
    private Object toDate;
    @SerializedName("productListLocation")
    @Expose
    private String productListLocation;

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getProductListPosition() {
        return productListPosition;
    }

    public void setProductListPosition(String productListPosition) {
        this.productListPosition = productListPosition;
    }

    public Object getListingLocation() {
        return listingLocation;
    }

    public void setListingLocation(Object listingLocation) {
        this.listingLocation = listingLocation;
    }

    public Object getProductDetailsPositions() {
        return productDetailsPositions;
    }

    public void setProductDetailsPositions(Object productDetailsPositions) {
        this.productDetailsPositions = productDetailsPositions;
    }

    public Object getDisplayDetailsPositions() {
        return displayDetailsPositions;
    }

    public void setDisplayDetailsPositions(Object displayDetailsPositions) {
        this.displayDetailsPositions = displayDetailsPositions;
    }

    public String getSellingType() {
        return sellingType;
    }

    public void setSellingType(String sellingType) {
        this.sellingType = sellingType;
    }

    public Object getFromDate() {
        return fromDate;
    }

    public void setFromDate(Object fromDate) {
        this.fromDate = fromDate;
    }

    public Object getToDate() {
        return toDate;
    }

    public void setToDate(Object toDate) {
        this.toDate = toDate;
    }

    public String getProductListLocation() {
        return productListLocation;
    }

    public void setProductListLocation(String productListLocation) {
        this.productListLocation = productListLocation;
    }

}
