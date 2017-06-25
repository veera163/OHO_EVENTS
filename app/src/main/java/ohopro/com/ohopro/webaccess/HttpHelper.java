package ohopro.com.ohopro.webaccess;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.LoggerUtils;

/**
 * Created by Sai on 9/16/2015.
 */
public class HttpHelper {
    private long TIMEOUT_CONNECT_MILLIS = (60 * DateUtils.SECOND_IN_MILLIS);
    private long TIMEOUT_READ_MILLIS = TIMEOUT_CONNECT_MILLIS - 5000;

    public HttpHelper() {

    }

    public String sendPOSTRequest(String urltoPOST, String paramtoPOST) {
        LoggerUtils.info(HttpHelper.class.getSimpleName(), "on HttpHelper sendPOSTRequest");
        LoggerUtils.info(HttpHelper.class.getSimpleName(), "on HttpHelper Url POSTRequest" + urltoPOST);

        DataOutputStream outputstream;

        int statuscode = 0;

        String response = "NA";

        urltoPOST.replace(" ", "%20");
        try {
            URL Url = new URL(urltoPOST);

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) Url.openConnection();

                urlConnection.setRequestMethod("POST");

                urlConnection.setConnectTimeout((int) TIMEOUT_CONNECT_MILLIS);

                urlConnection.setDoInput(true);

                urlConnection.setDoOutput(true);

                urlConnection.setUseCaches(false);

                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("authorization", AppConstant.HEADER);
                LoggerUtils.info(HttpHelper.class.getSimpleName(), "header  " + AppConstant.HEADER);
                outputstream = new DataOutputStream(urlConnection.getOutputStream());

                outputstream.writeBytes(paramtoPOST);

                outputstream.flush();

                outputstream.close();

                urlConnection.connect();

                //Get Response

                statuscode = urlConnection.getResponseCode();

                LoggerUtils.debug(HttpHelper.class.getSimpleName(), "HTTP STATUS CODE is" + statuscode);

                if (statuscode == HttpURLConnection.HTTP_OK) {
                    InputStream responseStream = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = responseStreamReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    responseStreamReader.close();

                    response = stringBuilder.toString();
                    if (response.equalsIgnoreCase("")) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("statusCode", 200);
                        response = jsonObject.toString();
                    }
                } else if (statuscode == HttpURLConnection.HTTP_BAD_REQUEST) {
                    InputStream responseStream = new BufferedInputStream(urlConnection.getErrorStream());

                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = responseStreamReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    responseStreamReader.close();

                    response = stringBuilder.toString();
                    if (response.equalsIgnoreCase("")) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("statusCode", HttpURLConnection.HTTP_BAD_REQUEST);
                        response = jsonObject.toString();
                    }
                } else if (statuscode == HttpURLConnection.HTTP_UNAUTHORIZED ||
                        statuscode == HttpURLConnection.HTTP_FORBIDDEN ||
                        statuscode == HttpURLConnection.HTTP_UNSUPPORTED_TYPE) {
                    InputStream responseStream = new BufferedInputStream(urlConnection.getErrorStream());

                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = responseStreamReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    responseStreamReader.close();

                    response = stringBuilder.toString();
                    if (response.equalsIgnoreCase("")) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("statusCode", statuscode);
                        response = jsonObject.toString();
                    }
                } else if (statuscode == HttpURLConnection.HTTP_INTERNAL_ERROR ||
                        statuscode == HttpURLConnection.HTTP_NOT_FOUND) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("error", HttpURLConnection.HTTP_BAD_REQUEST);
                    jsonObject.put("error_description", "Internal Server error");
                    response = jsonObject.toString();
                } else {
                    response = AppConstant.NO_RESPONSE;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String sendPUTRequest(String urlTOPost, String paramtoPOST) {

        DataOutputStream outputstream;
        int statuscode = 0;
        HttpURLConnection urlConnection = null;
        String response = "NA";
        urlTOPost.replace(" ", "%20");

        try {
            URL Url = new URL(urlTOPost);
            LoggerUtils.error(HttpHelper.class.getSimpleName(), "Request url is:- " + urlTOPost);
            LoggerUtils.error(HttpHelper.class.getSimpleName(), "params is:- " + paramtoPOST);

            try {
                urlConnection = (HttpURLConnection) Url.openConnection();
                urlConnection.setRequestMethod("PUT");
                urlConnection.setConnectTimeout((int) TIMEOUT_CONNECT_MILLIS);
                urlConnection.setDoInput(true);
                //urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);
                urlConnection.setRequestProperty("authorization", AppConstant.HEADER);
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                outputstream = new DataOutputStream(urlConnection.getOutputStream());
                outputstream.writeBytes(paramtoPOST);
                outputstream.flush();
                outputstream.close();

                urlConnection.connect();

                //Get Response

                statuscode = urlConnection.getResponseCode();

                LoggerUtils.debug(HttpHelper.class.getSimpleName(), "HTTP STATUS CODE is" + statuscode);

                if (statuscode == HttpURLConnection.HTTP_OK) {
                    InputStream responseStream = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = responseStreamReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    responseStreamReader.close();

                    response = stringBuilder.toString();
                    if (response.equalsIgnoreCase("")) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("statusCode", 200);
                        response = jsonObject.toString();
                    }
                } else if (statuscode == HttpURLConnection.HTTP_BAD_REQUEST) {
                    InputStream responseStream = new BufferedInputStream(urlConnection.getErrorStream());

                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = responseStreamReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    responseStreamReader.close();

                    response = stringBuilder.toString();
                    if (response.equalsIgnoreCase("")) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("statusCode", HttpURLConnection.HTTP_BAD_REQUEST);
                        response = jsonObject.toString();
                    }
                } else if (statuscode == HttpURLConnection.HTTP_UNAUTHORIZED ||
                        statuscode == HttpURLConnection.HTTP_FORBIDDEN ||
                        statuscode == HttpURLConnection.HTTP_UNSUPPORTED_TYPE) {
                    InputStream responseStream = new BufferedInputStream(urlConnection.getErrorStream());

                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = responseStreamReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    responseStreamReader.close();

                    response = stringBuilder.toString();
                    if (response.equalsIgnoreCase("")) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("statusCode", statuscode);
                        response = jsonObject.toString();
                    }
                } else if (statuscode == HttpURLConnection.HTTP_INTERNAL_ERROR ||
                        statuscode == HttpURLConnection.HTTP_NOT_FOUND) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("error", HttpURLConnection.HTTP_BAD_REQUEST);
                    jsonObject.put("error_description", "Internal Server error");
                    response = jsonObject.toString();
                } else {
                    response = AppConstant.NO_RESPONSE;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
        return response;
    }

    public String sendGETRequest(String urltoPOST) {
        urltoPOST.replace(" ", "%20");
        LoggerUtils.info(HttpHelper.class.getSimpleName(), "on HttpHelper sendGETRequest");

        DataOutputStream outputstream;


        int statuscode = 0;

        String response = "NA";

        try {
            LoggerUtils.info(HttpHelper.class.getName(), urltoPOST);
            URL Url = new URL(urltoPOST);
            LoggerUtils.info(HttpHelper.class.getName(), "after url");
            try {
                HttpURLConnection connection = (HttpURLConnection) Url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout((int) TIMEOUT_CONNECT_MILLIS);
                connection.setDoOutput(false);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("authorization", AppConstant.HEADER);
                LoggerUtils.info(HttpHelper.class.getSimpleName(), "header  " + AppConstant.HEADER);

                connection.connect();

                //Get Response

                statuscode = connection.getResponseCode();

                LoggerUtils.debug(HttpHelper.class.getSimpleName(), "HTTP STATUS CODE is" + statuscode);

                if (statuscode == HttpURLConnection.HTTP_OK) {
                    InputStream responseStream = connection.getInputStream();

                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = responseStreamReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    responseStreamReader.close();

                    response = stringBuilder.toString();
                } else {
                    response = AppConstant.NO_RESPONSE;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String sendPOSTRequest(String url, String s, String header) {
        LoggerUtils.info(HttpHelper.class.getSimpleName(), "on HttpHelper sendPOSTRequest");
        LoggerUtils.info(HttpHelper.class.getSimpleName(), "on HttpHelper Url POSTRequest" + url);

        DataOutputStream outputstream;

        int statuscode = 0;

        String response = "NA";

        try {
            URL Url = new URL(url);

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) Url.openConnection();

                urlConnection.setRequestMethod("POST");

                urlConnection.setConnectTimeout((int) TIMEOUT_CONNECT_MILLIS);

                urlConnection.setDoInput(true);

                urlConnection.setDoOutput(true);

                urlConnection.setUseCaches(false);

                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("authorization", header);
                LoggerUtils.info(HttpHelper.class.getSimpleName(), "header  " + header);
                outputstream = new DataOutputStream(urlConnection.getOutputStream());

                outputstream.writeBytes(s);

                outputstream.flush();

                outputstream.close();

                urlConnection.connect();

                //Get Response

                statuscode = urlConnection.getResponseCode();

                LoggerUtils.debug(HttpHelper.class.getSimpleName(), "HTTP STATUS CODE is" + statuscode);

                if (statuscode == HttpURLConnection.HTTP_OK) {
                    InputStream responseStream = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = responseStreamReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    responseStreamReader.close();

                    response = stringBuilder.toString();
                    if (response.equalsIgnoreCase("")) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("statusCode", 200);
                        response = jsonObject.toString();
                    }
                } else if (statuscode == HttpURLConnection.HTTP_BAD_REQUEST) {
                    InputStream responseStream = new BufferedInputStream(urlConnection.getErrorStream());

                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = responseStreamReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    responseStreamReader.close();

                    response = stringBuilder.toString();
                    if (response.equalsIgnoreCase("")) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("statusCode", HttpURLConnection.HTTP_BAD_REQUEST);
                        response = jsonObject.toString();
                    }
                } else {
                    response = AppConstant.NO_RESPONSE;
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static class MultipartUtility {
        private final String boundary;
        private static final String LINE_FEED = "\r\n";
        private HttpURLConnection httpConn;
        private String charset;
        private OutputStream outputStream;
        private PrintWriter writer;

        /**
         * This constructor initializes a new HTTP POST request with content type
         * is set to multipart/form-data
         *
         * @param requestURL
         * @param charset
         * @throws IOException
         */
        public MultipartUtility(String requestURL, String charset)
                throws IOException {
            this.charset = charset;

            // creates a unique boundary based on time stamp
            boundary = "===" + System.currentTimeMillis() + "===";

            URL url = new URL(requestURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoOutput(true); // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            httpConn.setRequestProperty("authorization", AppConstant.HEADER);
            httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
            httpConn.setRequestProperty("Test", "Bonjour");
            outputStream = httpConn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                    true);
        }

        /**
         * Adds a form field to the request
         *
         * @param name  field name
         * @param value field value
         */
        public void addFormField(String name, String value) {
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(
                    LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a upload file section to the request
         *
         * @param fieldName  name attribute in <input type="file" name="..." />
         * @param uploadFile a File to be uploaded
         * @throws IOException
         */
        public void addFilePart(String fieldName, File uploadFile)
                throws IOException {
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append(
                    "Content-Disposition: form-data; name=\"" + fieldName
                            + "\"; filename=\"" + fileName + "\"")
                    .append(LINE_FEED);
            writer.append(
                    "Content-Type: "
                            + URLConnection.guessContentTypeFromName(fileName))
                    .append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();

            writer.append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a header field to the request.
         *
         * @param name  - name of the header field
         * @param value - value of the header field
         */
        public void addHeaderField(String name, String value) {
            writer.append(name + ": " + value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Completes the request and receives response from the server.
         *
         * @return a list of Strings as response in case the server returned
         * status OK, otherwise an exception is thrown.
         * @throws IOException
         */
        public List<String> finish() throws IOException {
            List<String> response = new ArrayList<String>();

            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            // checks server's status code first
            int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    response.add(line);
                }
                reader.close();
                httpConn.disconnect();
            } else {
                throw new IOException("Server returned non-OK status: " + status);
            }

            return response;
        }
    }


}
