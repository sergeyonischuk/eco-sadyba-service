import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ExcelReader {

    public HashSet<String> getUniquePhoneNumbersFromSells(String path) {
        HashSet<String> result = new HashSet<>();
        try (FileInputStream inputStream = new FileInputStream(path);
             Workbook workbook = new HSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            //rowIndex - index of the first row with data
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                result.add(row.getCell(AppConfig.PHONE_ROW_IN_SELLS_SHEET).getStringCellValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Client> getClientsFromExcelFile(String path) {
        List<Client> result = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(path);
             Workbook workbook = new HSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                Client client = new Client();

                client.setCardCode(row.getCell(AppConfig.CARD_CODE_COLUMN).getStringCellValue());
                client.setFullName(row.getCell(AppConfig.FULL_NAME_COLUMN).getStringCellValue());
                if (!row.getCell(AppConfig.DATE_OF_BIRTH_COLUMN).getStringCellValue().equals("")) {
                    client.setDateOfBirth(LocalDate.parse(row.getCell(AppConfig.DATE_OF_BIRTH_COLUMN).getStringCellValue(), formatter));
                }
                client.setHomePhone(row.getCell(AppConfig.HOME_PHONE_COLUMN).getStringCellValue());
                client.setMobilePhone(row.getCell(AppConfig.MOBILE_PHONE_COLUMN).getStringCellValue());
                client.setWorkPhone(row.getCell(AppConfig.WORK_PHONE_COLUMN).getStringCellValue());
                client.setDiscount(row.getCell(AppConfig.DISCOUNT_COLUMN).getNumericCellValue());

                result.add(client);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void createFilesForCurrentMonth(Month value) {
        try {
            FileUtils.cleanDirectory(new File("src/main/result"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelReader reader = new ExcelReader();

        ClientService service = new ClientService();
        PhoneNumberWriter phoneNumberWriter = new PhoneNumberWriter();
        List<Client> clientList = reader.getClientsFromExcelFile(AppConfig.CLIENT_SHEET_PATH);
        HashSet<String> activePhones = reader.getUniquePhoneNumbersFromSells(AppConfig.SALES_SHEET_PATH);
        List<Client> activeClientList = service.getActiveClients(clientList, activePhones);

        phoneNumberWriter.writePhoneNumbersByDay(service.getClientsByMonth(activeClientList, value), value, AppConfig.CURRENT_YEAR);
        System.out.println("Ти подиви, працює!");
    }
}
