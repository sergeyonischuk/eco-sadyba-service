import java.time.Month;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        ExcelReader reader = new ExcelReader();
        NotepadReader notepadReader = new NotepadReader();
        ClientService service = new ClientService();
        List<Client> clientList = reader.getClientsFromExcelFile("src/main/resources/klient.xls");
        List<String> phones = notepadReader.readLinesFromFile("src/main/resources/unique.txt");
        List<Client> activeClientList = service.getActiveClients(clientList,phones);

//        service.getClientsByMonth(clientList, Month.APRIL);

        PhoneNumberWriter phoneNumberWriter = new PhoneNumberWriter();

        phoneNumberWriter.writePhoneNumbersByDay(service.getClientsByMonth(activeClientList, Month.DECEMBER), Month.DECEMBER, 2023);
    }
}
