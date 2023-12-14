import java.time.Month;
import java.util.*;

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
        for (int i = 0; i < clients.size(); i++) {
            if (isPhoneActive(clients.get(i).getMobilePhone(), activePhones)) {
                result.add(clients.get(i));
            }
        }
        return result;

    }

    private boolean isPhoneActive(String phone, HashSet<String> activePhones) {
        for(int i = 0; i < activePhones.size(); i++) {
            if (activePhones.contains(phone)) {
                return true;
            }
        }
        return false;
    }

//    private boolean isPhoneActive(List<Client> clients, HashSet<String> activePhones, int index) {
//        if (index >= 0 && index < clients.size()) {
//            String clientPhone = clients.get(index).getMobilePhone();
//            return activePhones.contains(clientPhone);
//        } else return false;
//    }

}
