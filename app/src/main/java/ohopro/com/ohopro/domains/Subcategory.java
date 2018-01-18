package ohopro.com.ohopro.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by home on 12/30/2017.
 */

public class Subcategory {

    @SerializedName("attributeName")
    @Expose
    private String attributeName;
    @SerializedName("attributeDetails")
    @Expose
    private List<AttributeDetail> attributeDetails = null;
    @SerializedName("attributeId")
    @Expose
    private String attributeId;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<AttributeDetail> getAttributeDetails() {
        return attributeDetails;
    }

    public void setAttributeDetails(List<AttributeDetail> attributeDetails) {
        this.attributeDetails = attributeDetails;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

}
