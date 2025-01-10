package org.example.nrp.visualLayer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.nrp.dataLayer.BddComm;
import org.example.nrp.dataLayer.FileComm;
import org.example.nrp.functionalLayer.Restaurant;


public class NRApplication extends Application {
    private static Restaurant restaurant;

    public static void InitRest(boolean session){
        restaurant = new Restaurant(session);
    }

    @Override
    public void start(Stage primaryStage) {
        // Login Page
        VBox loginLayout = new VBox(10);
        loginLayout.setAlignment(Pos.CENTER);
        Label instructionLabel = new Label("Enter your password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(300);
        CheckBox cb = new CheckBox();
        cb.setText("open new Session");
        cb.setIndeterminate(false);

        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(instructionLabel, passwordField);

        Button loginButton = new Button("Login");
        Label feedbackLabel = new Label();

        loginLayout.getChildren().addAll(hb, cb, loginButton, feedbackLabel);
        Scene loginScene = new Scene(loginLayout, 300, 200);

        // Handle Login Button Click
        loginButton.setOnAction(_ -> {
            String realH = FileComm.getPasswordHash();
            String enteredPassword = passwordField.getText();
            if (realH == null || realH.isEmpty() || Integer.parseInt(realH) == FileComm.HashIt(enteredPassword)) {
                Login(primaryStage, cb.selectedProperty().getValue());
            } else {
                feedbackLabel.setText("Incorrect password. Please try again.");
            }
        });

        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Password Check App");
        primaryStage.setOnCloseRequest(event -> {
            closeApp(primaryStage);
        });
        primaryStage.show();
    }

    public static void Login(Stage primaryStage, boolean session) {
        InitRest(session);
        Scene nextScene = new Scene(new VBox(), 800, 499);
        createNextScene(nextScene);
        primaryStage.setScene(nextScene);
    }

    public static void createNextScene(Scene nextScene) {
        TextField textField = new TextField();
        textField.setPromptText("Enter The card id...");

        Button button1 = new Button("validate");
        Button button2 = new Button("print");

        Label warningLabel = new Label(); // still empty until the System want to warn the user

        Label label = new Label("entrer le nom ou le code");
        label.setMaxWidth(300);
        label.setMaxWidth(400);

        button1.setOnAction(event -> {
            String input = textField.getText();
            String Message;
            try{
                int d = Integer.parseInt(input);
                Message = restaurant.submit(d);
            }
            catch (NumberFormatException e){
                Message = restaurant.submit(input);
            }

            warningLabel.setText(Message);
            textField.setText("");
        });

        button2.setOnAction(event -> {
            System.out.println(restaurant);
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, textField, button1, button2, warningLabel);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        nextScene.setRoot(layout);
    }

//    public static List<String> getDictLabel(){
//        List<String> list = new ArrayList<>();
//        list.add("Nom");
//        list.add("Prenom");
//        list.add("Filiere");
//        list.add("Niveau");
//        list.add("Date de pointage");
//        list.add("Type de repas");
//        list.add("Solde");
//        list.add("Prix");
//
//        return list;
//    }
//
//    public static Map<String, Label> ticketInterface(VBox vbox){
//        Label l = new Label("Ticket:");
//        Map<String, Label> dl = new HashMap<>();
//        for(String name: getDictLabel()){
//            dl.put(name, new Label());
//        }
//
//        Map<String, HBox> dh = new HashMap<>();
//        dh.put("Profil", new HBox());
//        dh.put("Status", new HBox());
//        dh.put("Price", new HBox());
//
//        dh.get("Profil").getChildren().addAll(dl.get("Nom"), dl.get("Prenom"));
//        dh.get("Status").getChildren().addAll(dl.get("Filiere"), dl.get("Niveau"));
//        dh.get("Price").getChildren().addAll(dl.get("Prix"), dl.get("Solde"));
//
//        vbox.getChildren().addAll(
//                l,
//                dh.get("Profil"),
//                dh.get("Status"),
//                dl.get("Type de repas"),
//                dl.get("Date de pointage"),
//                dh.get("Price")
//        );
//
//        return dl;
//
//
//
//
//    }

    public static void closeApp(Stage primaryStage) {
        FileComm.pickle(restaurant.AsedsUP(), FileComm.getSessionLocation(restaurant.sessionId));
        BddComm.close();
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
