package hlanz.programacion.criptografia.gui;


import hlanz.programacion.criptoanalisis.HackExito;
import hlanz.programacion.criptoanalisis.HackFracaso;
import hlanz.programacion.criptoanalisis.HackResult;
import hlanz.programacion.criptoanalisis.Hackeador;
import hlanz.programacion.criptoanalisis.cesar.HackeadorFactory;
import hlanz.programacion.criptografia.general.AlgoritmoCifrado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class frmCriptoanalisis {

    private ComboBox<AlgoritmoCifrado> cmbCifrado;
    private Spinner<Integer> spTolerancia;
    private TextArea txtCifrado;
    private Button btnHackear;
    private TextField txtResultado;
    private TextArea txtDescifrado;
    private TextField txtClave;
    private TextField txtIdioma;
    private Button btnElegirArchivo;

    public void mostrar(Stage owner) {
        inicializarControles();
        configurarEventos(owner);

        GridPane panelSuperior = new GridPane();
        panelSuperior.setHgap(10);
        panelSuperior.setVgap(10);

        panelSuperior.add(new Label("Algoritmo:"), 0, 0);
        panelSuperior.add(this.cmbCifrado, 1, 0);

        panelSuperior.add(new Label("% Tolerancia:"), 2, 0);
        panelSuperior.add(this.spTolerancia, 3, 0);

        HBox panelArchivo = new HBox(10, this.btnElegirArchivo);
        panelArchivo.setAlignment(Pos.CENTER_LEFT);

        GridPane panelInferior = new GridPane();
        panelInferior.setHgap(10);
        panelInferior.setVgap(10);

        panelInferior.add(new Label("Resultado del hackeo:"), 0, 0);
        panelInferior.add(this.txtResultado, 1, 0);

        panelInferior.add(new Label("Texto descifrado:"), 0, 1);
        panelInferior.add(this.txtDescifrado, 1, 1);

        panelInferior.add(new Label("Clave:"), 0, 2);
        panelInferior.add(this.txtClave, 1, 2);

        panelInferior.add(new Label("Idioma:"), 0, 3);
        panelInferior.add(this.txtIdioma, 1, 3);

        VBox.setVgrow(this.txtCifrado, Priority.ALWAYS);
        VBox.setVgrow(this.txtDescifrado, Priority.ALWAYS);
        HBox.setHgrow(this.txtResultado, Priority.ALWAYS);

        VBox root = new VBox(12);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                panelSuperior,
                panelArchivo,
                new Label("Texto cifrado:"),
                this.txtCifrado,
                this.btnHackear,
                panelInferior
        );

        Scene scene = new Scene(root, 760, 620);

        Stage ventana = new Stage();
        ventana.initOwner(owner);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle("Criptoanálisis");
        ventana.setScene(scene);
        ventana.setResizable(false);
        ventana.showAndWait();
    }

    private void inicializarControles() {
        this.cmbCifrado = new ComboBox<>();
        this.cmbCifrado.getItems().addAll(AlgoritmoCifrado.values());
        this.cmbCifrado.getSelectionModel().selectFirst();

        this.spTolerancia = new Spinner<>(0, 100, 70);
        this.spTolerancia.setEditable(true);

        this.txtCifrado = new TextArea();
        this.txtCifrado.setWrapText(true);
        this.txtCifrado.setPromptText("Escribe aquí el texto cifrado");

        this.btnHackear = new Button("Hackear");
        this.btnHackear.setMaxWidth(Double.MAX_VALUE);

        this.txtResultado = new TextField();
        this.txtResultado.setEditable(false);

        this.txtDescifrado = new TextArea();
        this.txtDescifrado.setWrapText(true);
        this.txtDescifrado.setEditable(false);

        this.txtClave = new TextField();
        this.txtClave.setEditable(false);

        this.txtIdioma = new TextField();
        this.txtIdioma.setEditable(false);

        this.btnElegirArchivo = new Button("Elegir archivo");

        this.cmbCifrado.setPrefWidth(160);
        this.spTolerancia.setPrefWidth(100);
        this.txtResultado.setPrefWidth(450);
        this.txtDescifrado.setPrefRowCount(6);
    }

    private void configurarEventos(Stage owner) {
        this.btnHackear.setOnAction(e -> hackearTexto());
        this.btnElegirArchivo.setOnAction(e -> cargarArchivo(owner));
    }

    private void hackearTexto() {
        try {
            limpiarResultados();

            AlgoritmoCifrado algoritmo = this.cmbCifrado.getValue();
            int tolerancia = this.spTolerancia.getValue();
            String textoCifrado = this.txtCifrado.getText();

            HackeadorFactory factory = new HackeadorFactory();
            Hackeador hackeador = factory.getHackeador(algoritmo, tolerancia);

            HackResult resultado = hackeador.descifrar(textoCifrado);

            if (resultado instanceof HackFracaso hackFracaso) {
                this.txtResultado.setText("No se ha podido hackear el texto");
                this.txtIdioma.setText(hackFracaso.motivo());
            } else if (resultado instanceof HackExito hackExito) {
                this.txtResultado.setText("Mensaje descifrado correctamente");
                this.txtDescifrado.setText(hackExito.textoDescifrado());
                this.txtClave.setText(hackExito.clave());
                this.txtIdioma.setText(hackExito.idioma().getNombre());
            }

        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        } catch (Exception ex) {
            mostrarError("Se ha producido un error durante el proceso de hackeo.");
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
                this.txtCifrado.setText(contenido);
            } catch (IOException ex) {
                mostrarError("No se ha podido leer el archivo seleccionado.");
            }
        }
    }

    private void limpiarResultados() {
        this.txtResultado.clear();
        this.txtDescifrado.clear();
        this.txtClave.clear();
        this.txtIdioma.clear();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}