package hlanz.programacion.criptografia.cesar;

import hlanz.programacion.criptografia.general.Cifrador;
import hlanz.programacion.criptografia.general.Descifrador;

public class ImplementacionCesar implements Cifrador, Descifrador {

    public ImplementacionCesar() {
    }

    protected char getLetraDesplazada(char letra, int desplazamiento) {
        if (letra < 'A' || letra > 'Z') {
            throw new IllegalArgumentException("Solo se pueden desplazar letras mayúsculas");
        }
        int base = 'A';
        int nuevaPos = (letra - base + desplazamiento) % 26;

        if (nuevaPos < 0) {
            nuevaPos += 26;
        }
        return (char) (base + nuevaPos);
    }

    protected String desplazarPalabra(String palabra, int desplazamiento) {
        palabra = palabra.toUpperCase();
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < palabra.length(); i++) {
            resultado.append(getLetraDesplazada(palabra.charAt(i), desplazamiento));
        }

        return resultado.toString();
    }

    protected static String desplazarLetrasFrase(String frase, int desplazamiento) {
        AnalizadorFrase analizador = new AnalizadorFrase(frase);
        StringBuilder resultado = new StringBuilder();

        for (String palabra : analizador.getPalabras()) {
            resultado.append(desplazarPalabra(palabra, desplazamiento)).append(" ");
        }

        return resultado.toString().trim();
    }

    @Override
    public String cifrar(String mensaje, String clave) {
        int desplazamiento;

        try {
            desplazamiento = Integer.parseInt(clave);
            if (desplazamiento < 0) {
                throw new IllegalArgumentException("La clave debe ser un número positivo");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La clave debe ser un número positivo");
        }

        return desplazarLetrasFrase(mensaje, desplazamiento);
    }

    @Override
    public String descifrar(String mensaje, String clave) {
        int desplazamiento;

        try {
            desplazamiento = Integer.parseInt(clave);
            if (desplazamiento < 0) {
                throw new IllegalArgumentException("La clave debe ser un número positivo");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La clave debe ser un número positivo");
        }

        return desplazarLetrasFrase(mensaje, -desplazamiento);
    }
}
