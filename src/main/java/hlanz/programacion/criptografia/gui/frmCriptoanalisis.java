package hlanz.programacion.criptografia.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class frmCriptoanalisis extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle("Criptoanálisis");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(new Label("Ventana frmCriptoanalisis"));

        Scene scene = new Scene(root, 500, 350);
        ventana.setScene(scene);
        ventana.setResizable(false);
        ventana.showAndWait();
    }
}
