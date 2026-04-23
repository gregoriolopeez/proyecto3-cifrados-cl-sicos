package hlanz.programacion.criptoanalisis.cesar;

import hlanz.programacion.criptoanalisis.HackExito;
import hlanz.programacion.criptoanalisis.HackFracaso;
import hlanz.programacion.criptoanalisis.HackResult;
import hlanz.programacion.criptoanalisis.Idioma;
import hlanz.programacion.criptografia.cesar.ROT13Factory;
import hlanz.programacion.criptografia.general.Descifrador;

public class ROT13Hacker extends CesarHacker {

    public ROT13Hacker(int porcentajeTolerancia) {
        super(porcentajeTolerancia);
    }

    @Override
    public HackResult descifrar(String texto) {
        Descifrador descifrador = new ROT13Factory().getDescifrador();
        String textoDescifrado = descifrador.descifrar(texto, "");

        HackResult resultado = new HackFracaso("Idioma desconocido");
        boolean encontrado = false;

        for (Idioma idioma: this.idiomasPosibles) {
            if (!encontrado && idioma.contieneFrase(textoDescifrado, this.porcentajeTolerancia)) {
                resultado = new HackExito(texto, textoDescifrado, "13", idioma);
                encontrado = true;
            }
        }
        return resultado;
    }
}
