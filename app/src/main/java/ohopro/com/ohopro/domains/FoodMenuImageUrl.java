package ohopro.com.ohopro.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by home on 12/28/2017.
 */

public class FoodMenuImageUrl {
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("docName")
    @Expose
    private Object docName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getDocName() {
        return docName;
    }

    public void setDocName(Object docName) {
        this.docName = docName;
    }
}
