package hlanz.programacion.criptografia.general;

import hlanz.programacion.criptografia.cesar.CesarFactory;
import hlanz.programacion.criptografia.cesar.ROT13Factory;

public interface CriptografiaAbstactFactory{
    public static CriptografiaFactory getFactory(AlgoritmoCifrado algoritmo) {
        return switch (algoritmo) {
            case CESAR -> new CesarFactory();
            case ROT13 -> new ROT13Factory();
            default -> null;
        };
    }

}
