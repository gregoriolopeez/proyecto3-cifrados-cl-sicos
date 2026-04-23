package hlanz.programacion.criptoanalisis;

import hlanz.programacion.criptoanalisis.util.AnalizadorFrase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Idioma {
    private String nombre;
    private Set<String> palabras;

    public Idioma(String nombre, String ruta) throws IOException{
        this.nombre = nombre;
        this.palabras = new HashSet<>();
        FileReader fileReader = new FileReader(ruta);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String linea;
        while ((linea = bufferedReader.readLine()) != null) {
            this.palabras.add(linea.toUpperCase());
        }
        bufferedReader.close();
    }

    public String getNombre(){
        return this.nombre;
    }

    public boolean contienePalabra(String p){
        return this.palabras.contains(p);
    }

    public boolean contieneFrase(String frase, int porcentajeTolerancia){
        if (porcentajeTolerancia < 0 || porcentajeTolerancia > 100) {
            throw new IllegalArgumentException("El porcentaje de tolerancia debe estar entre 0 y 100");
        }
        AnalizadorFrase analizador = new AnalizadorFrase(frase);
        int totalPalabras = analizador.getNumeroPalabras();
        int necesarias = (totalPalabras*porcentajeTolerancia+99)/100;
        int contador = 0;
        for (String palabra: analizador.getPalabras()) {
            if (contienePalabra(palabra)) {
                contador++;
            }
        }
        boolean verdad = false;
        if (contador >= necesarias){
            verdad = true;
        }else {
            verdad = false;
        }
        return verdad;
    }
}
