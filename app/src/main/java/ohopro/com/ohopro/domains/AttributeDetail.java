package ohopro.com.ohopro.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by home on 12/30/2017.
 */

public class AttributeDetail {
    @SerializedName("field_name")
    @Expose
    private Object fieldName;
    @SerializedName("field_type")
    @Expose
    private String fieldType;
    @SerializedName("field_required")
    @Expose
    private Object fieldRequired;
    @SerializedName("field_title")
    @Expose
    private String fieldTitle;
    @SerializedName("field_url")
    @Expose
    private List<FieldUrl> fieldUrl = null;
    @SerializedName("field_value")
    @Expose
    private Object fieldValue;
    @SerializedName("field_mvalues")
    @Expose
    private Object fieldMvalues;
    @SerializedName("field_options")
    @Expose
    private Object fieldOptions;
    @SerializedName("field_regex")
    @Expose
    private Object fieldRegex;
    @SerializedName("field_id")
    @Expose
    private String fieldId;
    @SerializedName("field_subproductType")
    @Expose
    private Object fieldSubproductType;
    @SerializedName("field_subproductType_desc")
    @Expose
    private Object fieldSubproductTypeDesc;
    @SerializedName("field_subproducts")
    @Expose
    private Object fieldSubproducts;
    @SerializedName("field_rows")
    @Expose
    private Object fieldRows;
    @SerializedName("field_removable")
    @Expose
    private Object fieldRemovable;

    public Object getFieldName() {
        return fieldName;
    }

    public void setFieldName(Object fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Object getFieldRequired() {
        return fieldRequired;
    }

    public void setFieldRequired(Object fieldRequired) {
        this.fieldRequired = fieldRequired;
    }

    public String getFieldTitle() {
        return fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public List<FieldUrl> getFieldUrl() {
        return fieldUrl;
    }

    public void setFieldUrl(List<FieldUrl> fieldUrl) {
        this.fieldUrl = fieldUrl;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Object getFieldMvalues() {
        return fieldMvalues;
    }

    public void setFieldMvalues(Object fieldMvalues) {
        this.fieldMvalues = fieldMvalues;
    }

    public Object getFieldOptions() {
        return fieldOptions;
    }

    public void setFieldOptions(Object fieldOptions) {
        this.fieldOptions = fieldOptions;
    }

    public Object getFieldRegex() {
        return fieldRegex;
    }

    public void setFieldRegex(Object fieldRegex) {
        this.fieldRegex = fieldRegex;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public Object getFieldSubproductType() {
        return fieldSubproductType;
    }

    public void setFieldSubproductType(Object fieldSubproductType) {
        this.fieldSubproductType = fieldSubproductType;
    }

    public Object getFieldSubproductTypeDesc() {
        return fieldSubproductTypeDesc;
    }

    public void setFieldSubproductTypeDesc(Object fieldSubproductTypeDesc) {
        this.fieldSubproductTypeDesc = fieldSubproductTypeDesc;
    }

    public Object getFieldSubproducts() {
        return fieldSubproducts;
    }

    public void setFieldSubproducts(Object fieldSubproducts) {
        this.fieldSubproducts = fieldSubproducts;
    }

    public Object getFieldRows() {
        return fieldRows;
    }

    public void setFieldRows(Object fieldRows) {
        this.fieldRows = fieldRows;
    }

    public Object getFieldRemovable() {
        return fieldRemovable;
    }

    public void setFieldRemovable(Object fieldRemovable) {
        this.fieldRemovable = fieldRemovable;
    }

}
