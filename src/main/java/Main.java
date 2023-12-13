import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.Month;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        FileUtils.cleanDirectory(new File("src/main/result"));
        ExcelReader reader = new ExcelReader();
        ClientService service = new ClientService();
        PhoneNumberWriter phoneNumberWriter = new PhoneNumberWriter();
        List<Client> clientList = reader.getClientsFromExcelFile("src/main/resources/clients.xls");
        HashSet<String> ativePhones = reader.getUniquePhoneNumbersFromSells("src/main/resources/sales.xls");
        List<Client> activeClientList = service.getActiveClients(clientList, ativePhones);

        phoneNumberWriter.writePhoneNumbersByDay(service.getClientsByMonth(activeClientList, Month.FEBRUARY), Month.FEBRUARY, 2023);
    }
}
