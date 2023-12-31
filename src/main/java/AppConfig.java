public class AppConfig {
    public final static Integer CURRENT_YEAR = 2023;
    public final static String CLIENT_SHEET_PATH = "src/main/resources/clients.xls";
    public final static String SALES_SHEET_PATH = "src/main/resources/sales.xls";
    public final static String RESULT_FILES_DIRECTORY = "src/main/result";

    //letsads API config
    public final static String login = "";
    public final static String password = "";
    public final static String apiUrl = "https://api.letsads.com";

    //clients.xls config
    public final static Integer CARD_CODE_COLUMN = 0;
    public final static Integer FULL_NAME_COLUMN = 1;
    public final static Integer DATE_OF_BIRTH_COLUMN = 2;
    public final static Integer HOME_PHONE_COLUMN = 3;
    public final static Integer MOBILE_PHONE_COLUMN = 4;
    public final static Integer WORK_PHONE_COLUMN = 5;
    public final static Integer DISCOUNT_COLUMN = 10;

    //sales.xls config
    public final static Integer PHONE_ROW_IN_SELLS_SHEET = 11;

}
