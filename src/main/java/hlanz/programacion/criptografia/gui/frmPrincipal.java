package hlanz.programacion.criptografia.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class frmPrincipal extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Radios
        RadioButton optCriptografia = new RadioButton("Criptografía");
        RadioButton optCriptoanalisis = new RadioButton("Criptoanálisis");

        // Grupo
        ToggleGroup grupo = new ToggleGroup();
        optCriptografia.setToggleGroup(grupo);
        optCriptoanalisis.setToggleGroup(grupo);

        optCriptografia.setSelected(true);

        // Botón
        Button btnAceptar = new Button("Aceptar");

        btnAceptar.setOnAction(e -> {
            if (optCriptografia.isSelected()) {
                abrirFrmCriptografia(primaryStage);
            } else {
                abrirFrmCriptoanalisis(primaryStage);
            }
        });

        // Layout
        VBox root = new VBox(15, optCriptografia, optCriptoanalisis, btnAceptar);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 200);

        primaryStage.setTitle("Programa de cifrados clásicos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Ventana Criptografía
    private void abrirFrmCriptografia(Stage parent) {
        Stage ventana = new Stage();
        ventana.initOwner(parent);
        ventana.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(new Label("Ventana de Criptografía"));

        Scene scene = new Scene(root, 400, 300);
        ventana.setScene(scene);
        ventana.setTitle("Criptografía");
        ventana.showAndWait();
    }

    // Ventana Criptoanálisis
    private void abrirFrmCriptoanalisis(Stage parent) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(new javafx.scene.control.Label("Ventana de Criptoanálisis"));

        Scene scene = new Scene(root, 400, 300);
        ventana.setScene(scene);
        ventana.setTitle("Criptoanálisis");
        ventana.showAndWait();
    }
}
