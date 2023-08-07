import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private final Integer CARD_CODE_COLUMN = 0;
    private final Integer FULL_NAME_COLUMN = 1;
    private final Integer DATE_OF_BIRTH_COLUMN = 3;
    private final Integer HOME_PHONE_COLUMN = 4;
    private final Integer MOBILE_PHONE_COLUMN = 5;
    private final Integer WORK_PHONE_COLUMN = 7;
    private final Integer DISCOUNT_COLUMN = 13;

    public List<Client> getClientsFromExcelFile(String path) {
        List<Client> result = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(path);
             Workbook workbook = new HSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
