package interface_usuario;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;

public class RoundJTextField extends JTextField {

	private static final long serialVersionUID = 7456855456976460093L;
	
	private transient Shape formato;
	private int arco;
    
	public RoundJTextField(int tamanho) {
        super(tamanho);
        setOpaque(false);
        arco = 35;
    }
    
    @Override
    protected void paintComponent(Graphics graficos) {
         graficos.setColor(getBackground());
         graficos.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, arco, arco);
         super.paintComponent(graficos);
    }
    
    @Override
    protected void paintBorder(Graphics graficos) {
         graficos.setColor(getForeground());
         graficos.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arco, arco);
    }
    
    @Override
    public boolean contains(int x, int y) {
         if (formato == null || !formato.getBounds().equals(getBounds())) {
             formato = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, arco, arco);
         }
         return formato.contains(x, y);
    }
}
