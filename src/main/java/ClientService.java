import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClientService {
    ExcelReader reader = new ExcelReader();

    public String[] getTodayBirthdayActivePhones(String clientbasePath, String salesbasePath) {
        List<Client> clientList = reader.getClientsFromExcelFile(clientbasePath);
        HashSet<String> activePhones = reader.getUniquePhoneNumbersFromSells(salesbasePath);
        List<Client> activeClientList = getActiveClients(clientList, activePhones);
        List<Client> todayBirthdayClients = getClientsByDay(activeClientList, LocalDate.now());
        return getClientsPhoneNumbers(todayBirthdayClients);
    }

    public List<Client> getClientsByDay(List<Client> clients, LocalDate date) {
        List<Client> result = new ArrayList<>();
        for (Client client : clients) {
            if (client.getDateOfBirth() != null) {
                if (client.getDateOfBirth().getMonth().equals(date.getMonth())) {
                    if (client.getDateOfBirth().getDayOfMonth() == date.getDayOfMonth()) {
                        result.add(client);
                    }
                }
            }
        }
        return result;
    }

    public String[] getClientsPhoneNumbers(List<Client> clients) {
        ArrayList<String> result = new ArrayList<>();

        for (Client client : clients) {
            if (getValidPhoneNumber(client) != null) {
                result.add(getValidPhoneNumber(client));
            }

        }
        return result.toArray(new String[0]);
    }

    private String getValidPhoneNumber(Client client) {
        String homePhone = client.getHomePhone();
        String mobilePhone = client.getMobilePhone();
        String workPhone = client.getWorkPhone();

        String validPhone = null;

        if (isPhoneValid(homePhone)) {
            validPhone = homePhone;
        } else if (isPhoneValid(mobilePhone)) {
            validPhone = mobilePhone;
        } else if (isPhoneValid(workPhone)) {
            validPhone = workPhone;
        }

        if (validPhone != null) {
            validPhone = "38" + validPhone;
        }

        return validPhone;
    }

    public List<Client> getActiveClients(List<Client> clients, HashSet<String> activePhones) {
        List<Client> result = new ArrayList<>();
        for (Client client : clients) {
            if (isPhoneActive(client.getMobilePhone(), activePhones)) {
                result.add(client);
            }
        }
        return result;

    }

    private boolean isPhoneActive(String phone, HashSet<String> activePhones) {
        for (int i = 0; i < activePhones.size(); i++) {
            if (activePhones.contains(phone)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPhoneValid(String phoneNumber) {
        String regex = "^0(50|63|66|67|68|73|91|92|93|94|95|96|97|98|99)\\d{7}$";

        return phoneNumber != null && phoneNumber.matches(regex);
    }
}
