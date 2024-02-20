import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
            AppConfig.login = properties.getProperty("login");
            AppConfig.password = properties.getProperty("password");
        } catch (
                IOException e) {
            System.err.println("reading configuration file error");
            e.printStackTrace();
        }

        ClientService service = new ClientService();
        SmsSender smsSender = new SmsSender();
        smsSender.sendBirthdaySms(service.getTodayBirthdayActivePhones(AppConfig.CLIENT_SHEET_PATH, AppConfig.SALES_SHEET_PATH));
    }
}