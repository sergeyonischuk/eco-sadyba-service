import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class TxtReader {
    public String[] getCurrentDayBirthdayPhonesFromFile() {
        ExcelReader reader = new ExcelReader();


        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");

        int day = Integer.parseInt(today.format(formatter).substring(0, 2));
        int month = Integer.parseInt(today.format(formatter).substring(4, 5));

        reader.createFilesForCurrentMonth(Month.of(month));

        return getPhonesFromFile("src/main/result" + "/" + day + ".txt");
    }

    public String[] getPhonesFromFile(String filePath) {
        ArrayList<String> phoneList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Додавання кожного номера телефону до списку
                phoneList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Обробка можливих винятків (помилок) читання файлу
        }
        return phoneList.toArray(new String[0]);
    }
}
