import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.T;

import java.time.Month;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {
        ExcelReader excelReader = new ExcelReader();
        SmsSender smsSender = new SmsSender();
        TxtReader txtReader = new TxtReader();
        primaryStage.setTitle("Sadyba helper " + AppConfig.CURRENT_YEAR);

        ChoiceBox<Month> monthChoiceBox = new ChoiceBox<>();
        monthChoiceBox.getItems().addAll(Month.values());
        monthChoiceBox.setValue(Month.JANUARY);

        Button createFilesButton = new Button("Хєрак!");
        createFilesButton.setOnAction(e -> excelReader.createFilesForCurrentMonth(monthChoiceBox.getValue()));

        Button sendTodaySms = new Button("Відправити смс іменинникам");
        sendTodaySms.setOnAction(e -> smsSender.sendSms(txtReader.getCurrentDayBirthdayPhonesFromFile()));

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(monthChoiceBox, createFilesButton);


        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
