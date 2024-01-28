import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class SmsSender {

    public void sendBirthdaySms(String[] phoneNumbers) {
        try {
            sendSms(phoneNumbers, AppConfig.BIRTHDAY_MESSAGE_TEXT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCustomSms(String[] phoneNumbers, String message) {
        try {
            sendSms(phoneNumbers, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendSms(String[] phoneNumbers, String messageText) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(AppConfig.apiUrl);
        String xmlBody = buildXmlRequest(phoneNumbers, messageText);
        post.setHeader("Content-Type", "application/xml");
        post.setEntity(new StringEntity(xmlBody, "UTF-8"));
        HttpResponse response = client.execute(post);
        System.out.println("HTTP Response Code: " + response.getStatusLine().getStatusCode());

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("HTTP Response Code: " + statusCode);

        String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println("Response Body: " + responseBody);
        EntityUtils.consume(response.getEntity());
    }

    private static String buildXmlRequest(String[] phoneNumbers, String messageText) {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlBuilder.append("<request>");
        xmlBuilder.append("<auth>");
        xmlBuilder.append("<login>").append(AppConfig.login).append("</login>");
        xmlBuilder.append("<password>").append(AppConfig.password).append("</password>");
        xmlBuilder.append("</auth>");
        xmlBuilder.append("<message>");
        xmlBuilder.append("<from>ECO-SADYBA</from>");
        xmlBuilder.append("<text>").append(messageText).append("</text>");
        for (String phoneNumber : phoneNumbers) {
            xmlBuilder.append("<recipient>").append(phoneNumber).append("</recipient>");
        }
        xmlBuilder.append("</message>");
        xmlBuilder.append("</request>");

        return xmlBuilder.toString();
    }
}
