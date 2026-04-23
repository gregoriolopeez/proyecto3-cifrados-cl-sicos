package hlanz.programacion.criptografia.general;

public interface CriptografiaFactory {
    public Cifrador getCifrador();
    public Descifrador getDescifrador();
}
