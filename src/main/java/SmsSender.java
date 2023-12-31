import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class SmsSender {
    public static void main(String[] args) {
        // Дані для аутентифікації
        String login = AppConfig.login;
        String password = AppConfig.password;
        String apiUrl = AppConfig.apiUrl;

        // Зчитуємо номери телефонів та повідомлення з файлу або іншого джерела
        String[] phoneNumbers = {"380992132410"};
        String messageText = "Якщо ти читаєш це смс, то моя програма працює!";

        try {
            // Викликаємо метод для відправки SMS
            sendSms(login, password, apiUrl, phoneNumbers, messageText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendSms(String login, String password, String apiUrl, String[] phoneNumbers, String messageText) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(apiUrl);
        String xmlBody = buildXmlRequest(login, password, phoneNumbers, messageText);
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

    private static String buildXmlRequest(String login, String password, String[] phoneNumbers, String messageText) {
        // Формуємо XML-запит
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlBuilder.append("<request>");
        xmlBuilder.append("<auth>");
        xmlBuilder.append("<login>").append(login).append("</login>");
        xmlBuilder.append("<password>").append(password).append("</password>");
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
