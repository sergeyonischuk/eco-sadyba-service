import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

public class PhoneNumberWriter {
    public void writePhoneNumbersByDay(List<Client> clients, Month targetMonth, int year) {
        YearMonth yearMonth = YearMonth.of(year, targetMonth);
        int daysInMonth = yearMonth.lengthOfMonth();

        // Створення директорії, якщо її не існує
        String directoryPath = "src/main/result";
        Path directory = Paths.get(directoryPath);
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int day = 1; day <= YearMonth.of(year, targetMonth).lengthOfMonth(); day++) {
            String fileName = directoryPath + "/" + day + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (Client client : clients) {
                    if (client.getDateOfBirth().getMonth() == targetMonth && client.getDateOfBirth().getDayOfMonth() == day) {
                        String phoneNumber = getValidPhoneNumber(client);
                        if (phoneNumber != null) {
                            writer.write(phoneNumber);
                            writer.newLine();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getValidPhoneNumber(Client client) {
        String homePhone = client.getHomePhone();
        String mobilePhone = client.getMobilePhone();
        String workPhone = client.getWorkPhone();

        String validPhone = null;

        if (isValidPhoneNumber(homePhone)) {
            validPhone = homePhone;
        } else if (isValidPhoneNumber(mobilePhone)) {
            validPhone = mobilePhone;
        } else if (isValidPhoneNumber(workPhone)) {
            validPhone = workPhone;
        }

        return validPhone;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression to match a valid Ukrainian phone number
        String regex = "^(\\+38|0)(50|63|66|67|68|73|91|92|93|94|95|96|97|98|99)\\d{7}$";

        return phoneNumber != null && phoneNumber.matches(regex);
    }
}
