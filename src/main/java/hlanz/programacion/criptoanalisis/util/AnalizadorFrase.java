package hlanz.programacion.criptoanalisis.util;

import java.util.ArrayList;
import java.util.List;

public class AnalizadorFrase {
    private String[] palabras;

    public AnalizadorFrase(String frase){
        this.palabras = frase.toUpperCase().split(" ");
    }

    public int getNumeroPalabras(){
        return this.palabras.length;
    }

    public List<String> getPalabras(){
        List<String> lista = new ArrayList<>();
        for (String palabra: this.palabras) {
            lista.add(palabra);
        }
        return lista;
    }
}
