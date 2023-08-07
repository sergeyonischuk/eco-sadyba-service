import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
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
}
