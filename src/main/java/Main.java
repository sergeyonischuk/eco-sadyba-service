import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.Month;
import java.util.HashSet;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Моя програма");

        ChoiceBox<Month> monthChoiceBox = new ChoiceBox<>();
        monthChoiceBox.getItems().addAll(Month.values());
        monthChoiceBox.setValue(Month.JANUARY);

        Button createFilesButton = new Button("Створити файли");
        createFilesButton.setOnAction(e -> createFilesForCurrentMonth(monthChoiceBox.getValue()));

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(monthChoiceBox, createFilesButton);


        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createFilesForCurrentMonth(Month value) {
        try {
            FileUtils.cleanDirectory(new File("src/main/result"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelReader reader = new ExcelReader();

        ClientService service = new ClientService();
        PhoneNumberWriter phoneNumberWriter = new PhoneNumberWriter();
        List<Client> clientList = reader.getClientsFromExcelFile("src/main/resources/clients.xls");
        HashSet<String> ativePhones = reader.getUniquePhoneNumbersFromSells("src/main/resources/sales.xls");
        List<Client> activeClientList = service.getActiveClients(clientList, ativePhones);

        phoneNumberWriter.writePhoneNumbersByDay(service.getClientsByMonth(activeClientList, value), value, 2023);
        System.out.println("Ти подиви, працює!");
    }
}
