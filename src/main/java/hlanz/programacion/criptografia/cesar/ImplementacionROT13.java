package hlanz.programacion.criptografia.cesar;

public class ImplementacionROT13 extends ImplementacionCesar {

    public ImplementacionROT13() {
        super();
    }

    @Override
    public String cifrar(String mensaje, String clave) {
        return super.desplazarLetrasFrase(mensaje, 13);
    }

    @Override
    public String descifrar(String mensaje, String clave) {
        return super.desplazarLetrasFrase(mensaje, -13);
    }
}
