package hlanz.programacion.criptoanalisis;

public record HackExito(String textoCifrado, String textoDescifrado, String clave, Idioma idioma) implements HackResult{
}
