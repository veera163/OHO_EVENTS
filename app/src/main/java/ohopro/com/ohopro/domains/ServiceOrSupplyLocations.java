package ohopro.com.ohopro.domains;

public class ServiceOrSupplyLocations {
    private String text;

    public ServiceOrSupplyLocations(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return
                "ServiceOrSupplyLocations{" +
                        "text = '" + text + '\'' +
                        "}";
    }
}
