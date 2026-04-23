package hlanz.programacion.criptografia.gui;

import hlanz.programacion.criptografia.general.AlgoritmoCifrado;
import hlanz.programacion.criptografia.general.Cifrador;
import hlanz.programacion.criptografia.general.CriptografiaFactory;
import hlanz.programacion.criptografia.general.Descifrador;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class frmCriptografia extends Application {
    private ComboBox<AlgoritmoCifrado> cmbCifrado;
    private TextField txtClave;
    private RadioButton optCifrar;
    private RadioButton optDescifrar;
    private ToggleGroup grupoOperacion;
    private TextArea txtEntrada;
    private Button btnAccion;
    private TextArea txtSalida;
    private Button btnElegirArchivo;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle("Criptografía");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(new Label("Ventana frmCriptografia"));

        Scene scene = new Scene(root, 500, 350);
        ventana.setScene(scene);
        ventana.showAndWait();
    }
    private void inicializarControles() {
        this.cmbCifrado = new ComboBox<>();
        this.cmbCifrado.getItems().addAll(AlgoritmoCifrado.values());
        this.cmbCifrado.getSelectionModel().selectFirst();

        this.txtClave = new TextField();
        this.txtClave.setPromptText("Introduce la clave");

        this.optCifrar = new RadioButton("Cifrar");
        this.optDescifrar = new RadioButton("Descifrar");

        this.grupoOperacion = new ToggleGroup();
        this.optCifrar.setToggleGroup(grupoOperacion);
        this.optDescifrar.setToggleGroup(grupoOperacion);
        this.optCifrar.setSelected(true);

        this.txtEntrada = new TextArea();
        this.txtEntrada.setWrapText(true);
        this.txtEntrada.setPromptText("Escribe aquí el texto de entrada");

        this.btnAccion = new Button("Cifrar");
        this.btnAccion.setMaxWidth(Double.MAX_VALUE);

        this.txtSalida = new TextArea();
        this.txtSalida.setWrapText(true);
        this.txtSalida.setEditable(false);

        this.btnElegirArchivo = new Button("Elegir archivo");

        this.cmbCifrado.setPrefWidth(160);
        this.txtClave.setPrefWidth(120);
    }

    private void configurarEventos(Stage owner) {
        this.optCifrar.setOnAction(e -> this.btnAccion.setText("Cifrar"));
        this.optDescifrar.setOnAction(e -> this.btnAccion.setText("Descifrar"));

        this.btnAccion.setOnAction(e -> ejecutarOperacion());

        this.btnElegirArchivo.setOnAction(e -> cargarArchivo(owner));
    }

    private void ejecutarOperacion() {
        try {
            AlgoritmoCifrado algoritmo = cmbCifrado.getValue();
            String clave = txtClave.getText();
            String textoEntrada = txtEntrada.getText();

            CriptografiaAbstractFactory abstractFactory = new CriptografiaAbstractFactory();
            CriptografiaFactory factory = abstractFactory.getFactory(algoritmo);

            if (factory == null) {
                mostrarError("No se ha podido obtener la factoría de criptografía.");
                return;
            }

            String resultado;

            if (optCifrar.isSelected()) {
                Cifrador cifrador = factory.getCifrador();
                resultado = cifrador.cifrar(textoEntrada, clave);
            } else {
                Descifrador descifrador = factory.getDescifrador();
                resultado = descifrador.descifrar(textoEntrada, clave);
            }

            txtSalida.setText(resultado);

        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        } catch (Exception ex) {
            mostrarError("Se ha producido un error al procesar el texto.");
        }
    }

    private void cargarArchivo(Stage owner) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona un archivo de texto");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de texto", "*.txt")
        );

        File archivo = fileChooser.showOpenDialog(owner);

        if (archivo != null) {
            try {
                String contenido = Files.readString(archivo.toPath());
                txtEntrada.setText(contenido);
            } catch (IOException ex) {
                mostrarError("No se ha podido leer el archivo seleccionado.");
            }
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
