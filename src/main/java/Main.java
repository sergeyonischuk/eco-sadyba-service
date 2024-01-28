import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ClientService service = new ClientService();

        SmsSender smsSender = new SmsSender();
        primaryStage.setTitle("Eco Sadyba SMS sender");

        Button sendTodaySms = new Button("Відправити смс іменинникам");
        sendTodaySms.setOnAction(e -> smsSender.sendBirthdaySms(service.getTodayBirthdayActivePhones(AppConfig.CLIENT_SHEET_PATH, AppConfig.SALES_SHEET_PATH)));

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(sendTodaySms);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}