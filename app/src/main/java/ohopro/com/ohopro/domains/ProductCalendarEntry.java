package ohopro.com.ohopro.domains;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by home on 1/2/2018.
 */

public class ProductCalendarEntry {

    @SerializedName("calendarEntryId")
    @Expose
    private String calendarEntryId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("startsAt")
    @Expose
    private Long startsAt;
    @SerializedName("endsAt")
    @Expose
    private Long endsAt;

    @SerializedName("orderId")
    @Expose
    private Object orderId;

    public CalColor getColor() {
        return color;
    }

    public void setColor(CalColor color) {
        this.color = color;
    }

    @SerializedName("color")
    @Expose

    private CalColor color;

    public String getCalendarEntryId() {
        return calendarEntryId;
    }

    public void setCalendarEntryId(String calendarEntryId) {
        this.calendarEntryId = calendarEntryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Long startsAt) {
        this.startsAt = startsAt;
    }

    public Long getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Long endsAt) {
        this.endsAt = endsAt;
    }


    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }
}
