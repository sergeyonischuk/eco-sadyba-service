import java.time.Month;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        ExcelReader reader = new ExcelReader();
        ClientService service = new ClientService();
        List<Client> clientList = reader.getClientsFromExcelFile("src/main/resources/klient.xls");

        service.getClientsByMonth(clientList, Month.APRIL);
    }
}
