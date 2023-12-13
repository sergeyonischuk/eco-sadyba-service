import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExcelReader {
    private final Integer CARD_CODE_COLUMN = 0;
    private final Integer FULL_NAME_COLUMN = 1;
    private final Integer DATE_OF_BIRTH_COLUMN = 2;
    private final Integer HOME_PHONE_COLUMN = 3;
    private final Integer MOBILE_PHONE_COLUMN = 4;
    private final Integer WORK_PHONE_COLUMN = 5;
    private final Integer DISCOUNT_COLUMN = 10;

    private final Integer PHONE_ROW_IN_SELLS_SHEET = 11;

    public HashSet<String> getUniquePhoneNumbersFromSells(String path) {
        HashSet<String> result = new HashSet<>();
        try (FileInputStream inputStream = new FileInputStream(path);
             Workbook workbook = new HSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                result.add(row.getCell(PHONE_ROW_IN_SELLS_SHEET).getStringCellValue());
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
            //rowIndex - index of the first row with data
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                Client client = new Client();

                client.setCardCode(row.getCell(CARD_CODE_COLUMN).getStringCellValue());
                client.setFullName(row.getCell(FULL_NAME_COLUMN).getStringCellValue());
                if (!row.getCell(DATE_OF_BIRTH_COLUMN).getStringCellValue().equals("")) {
                    client.setDateOfBirth(LocalDate.parse(row.getCell(DATE_OF_BIRTH_COLUMN).getStringCellValue(), formatter));
                }
                client.setHomePhone(row.getCell(HOME_PHONE_COLUMN).getStringCellValue());
                client.setMobilePhone(row.getCell(MOBILE_PHONE_COLUMN).getStringCellValue());
                client.setWorkPhone(row.getCell(WORK_PHONE_COLUMN).getStringCellValue());
                client.setDiscount(row.getCell(DISCOUNT_COLUMN).getNumericCellValue());

                result.add(client);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
