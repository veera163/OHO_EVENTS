package ohopro.com.ohopro.busnesslayer;

/**
 * Created by Sai on 9/16/2015.
 */
import ohopro.com.ohopro.webaccess.Response;
/**
 * interface to recieve the Retreived data
 */
public interface DataListener {
    /**
     * Method to respond when data got received from web-service.
     * @param data
     */
    void dataRetreived(Response data);
    /**
     * Method to move to network setting
     */
    void opennetworksetting();
}
