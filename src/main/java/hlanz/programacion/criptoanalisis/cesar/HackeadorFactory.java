package hlanz.programacion.criptoanalisis.cesar;

import hlanz.programacion.criptoanalisis.Hackeador;
import hlanz.programacion.criptografia.general.AlgoritmoCifrado;

public class HackeadorFactory {
    public static Hackeador getHackeador(AlgoritmoCifrado algoritmo, int pcTolerancia) {
        if (pcTolerancia < 0 || pcTolerancia > 100) {
            throw new IllegalArgumentException("El porcentaje de tolerancia debe estar entre [0,100]");
        }

        return switch (algoritmo) {
            case CESAR -> new CesarHacker(pcTolerancia);
            case ROT13 -> new ROT13Hacker(pcTolerancia);
        };
    }
}
