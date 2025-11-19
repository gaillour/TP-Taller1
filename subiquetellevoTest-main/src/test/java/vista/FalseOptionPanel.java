package vista;

import java.awt.Component;


public class FalseOptionPanel extends DefaultOptionPane {
    private String mensaje = null;

    public FalseOptionPanel() {
        super();
    }

    public void ShowMessage(Component parent, String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

	@Override
	public void ShowMessage(String mensaje) {
		this.mensaje = mensaje;
		
	}
}