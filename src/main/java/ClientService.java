import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ClientService {

    public List<Client> getClientsByMonth(List<Client> clients, Month targetMonth) {
        List<Client> result = new ArrayList<>();

        for (Client client : clients) {
            if (client.getDateOfBirth() != null) {
                if (client.getDateOfBirth().getMonth().equals(targetMonth)) {
                    result.add(client);
                }
            }
        }
        sortByDayOfBirth(result);
        return result;
    }

    private void sortByDayOfBirth(List<Client> clientList) {
        Comparator<Client> dateComparator = Comparator.comparing(client -> client.getDateOfBirth().getDayOfYear());
        clientList.sort(dateComparator);
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
}
