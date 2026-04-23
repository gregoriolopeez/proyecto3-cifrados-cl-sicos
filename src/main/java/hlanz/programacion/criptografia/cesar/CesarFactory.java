package hlanz.programacion.criptografia.cesar;

import hlanz.programacion.criptografia.general.Cifrador;
import hlanz.programacion.criptografia.general.CriptografiaFactory;
import hlanz.programacion.criptografia.general.Descifrador;

public class CesarFactory implements CriptografiaFactory {
    private ImplementacionCesar cesar;

    public CesarFactory() {
       this.cesar = new ImplementacionCesar();
    }

    @Override
    public Cifrador getCifrador() {
        return this.cesar;
    }

    @Override
    public Descifrador getDescifrador() {
        return this.cesar;
    }
}
