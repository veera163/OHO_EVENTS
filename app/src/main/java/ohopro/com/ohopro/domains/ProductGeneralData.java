package ohopro.com.ohopro.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by home on 12/27/2017.
 */

public class ProductGeneralData {

    @SerializedName("basePrice")
    @Expose
    private Object basePrice;
    @SerializedName("discount")
    @Expose
    private Object discount;
    @SerializedName("discountType")
    @Expose
    private Object discountType;
    @SerializedName("additionalDiscount")
    @Expose
    private Object additionalDiscount;
    @SerializedName("additionalDiscountType")
    @Expose
    private Object additionalDiscountType;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("unitOfMeasure")
    @Expose
    private String unitOfMeasure;
    @SerializedName("availableQuantity")
    @Expose
    private String availableQuantity;
    @SerializedName("availableQuantityUnitOfMeasure")
    @Expose
    private String availableQuantityUnitOfMeasure;

    public Object getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Object basePrice) {
        this.basePrice = basePrice;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public Object getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Object discountType) {
        this.discountType = discountType;
    }

    public Object getAdditionalDiscount() {
        return additionalDiscount;
    }

    public void setAdditionalDiscount(Object additionalDiscount) {
        this.additionalDiscount = additionalDiscount;
    }

    public Object getAdditionalDiscountType() {
        return additionalDiscountType;
    }

    public void setAdditionalDiscountType(Object additionalDiscountType) {
        this.additionalDiscountType = additionalDiscountType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(String availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getAvailableQuantityUnitOfMeasure() {
        return availableQuantityUnitOfMeasure;
    }

    public void setAvailableQuantityUnitOfMeasure(String availableQuantityUnitOfMeasure) {
        this.availableQuantityUnitOfMeasure = availableQuantityUnitOfMeasure;
    }


}
