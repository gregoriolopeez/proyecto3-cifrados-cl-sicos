package hlanz.programacion.criptografia.cesar;

import hlanz.programacion.criptografia.general.Cifrador;
import hlanz.programacion.criptografia.general.CriptografiaFactory;
import hlanz.programacion.criptografia.general.Descifrador;

public class ROT13Factory implements CriptografiaFactory {

    private ImplementacionROT13 rot13;

    public ROT13Factory() {
        this.rot13 = new ImplementacionROT13();
    }

    @Override
    public Cifrador getCifrador() {
        return this.rot13;
    }

    @Override
    public Descifrador getDescifrador() {
        return this.rot13;
    }
}
