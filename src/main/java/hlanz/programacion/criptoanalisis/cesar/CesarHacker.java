package hlanz.programacion.criptoanalisis.cesar;

import hlanz.programacion.criptoanalisis.*;
import hlanz.programacion.criptografia.cesar.CesarFactory;
import hlanz.programacion.criptografia.general.Descifrador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CesarHacker implements Hackeador {
    protected List<Idioma> idiomasPosibles;
    protected int porcentajeTolerancia;

    public CesarHacker(int porcentajeTolerancia) {
        this.porcentajeTolerancia = porcentajeTolerancia;
        this.idiomasPosibles = new ArrayList<>();
        try {
            this.idiomasPosibles.add(new Idioma("Español", "spanish.txt"));
            this.idiomasPosibles.add(new Idioma("Inglés", "english.txt"));
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar los archivos de idiomas");
        }
    }

    @Override
    public HackResult descifrar(String texto) {
        Descifrador descifrador = new CesarFactory().getDescifrador();
        HackResult resultado = new HackFracaso("Idioma desconocido");
        boolean encontrado = false;

        for (int i = 0; i <= 26 && !encontrado; i++) {
            String clave = String.valueOf(i);
            String textoDescifrado = descifrador.descifrar(texto, clave);
            for (Idioma idioma : this.idiomasPosibles) {
                if (!encontrado && idioma.contieneFrase(textoDescifrado, this.porcentajeTolerancia)) {
                    resultado = new HackExito(texto, textoDescifrado, clave, idioma);
                    encontrado = true;
                }
            }
        }
        return resultado;
    }
}
