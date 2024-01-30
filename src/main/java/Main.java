public class Main {

    public static void main(String[] args) {
        ClientService service = new ClientService();
        SmsSender smsSender = new SmsSender();
        smsSender.sendBirthdaySms(service.getTodayBirthdayActivePhones(AppConfig.CLIENT_SHEET_PATH, AppConfig.SALES_SHEET_PATH));
    }
}