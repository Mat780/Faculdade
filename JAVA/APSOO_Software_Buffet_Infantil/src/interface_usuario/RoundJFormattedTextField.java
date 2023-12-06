package interface_usuario;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFormattedTextField;

public class RoundJFormattedTextField extends JFormattedTextField {
	
	private static final long serialVersionUID = 1329687227007735997L;
	
	private Shape formato;
	private int arco;
	
    public RoundJFormattedTextField(int arco) {
        super();
        setOpaque(false); 
        this.arco = arco;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, arco, arco);
         super.paintComponent(g);
    }
    
    @Override
    protected void paintBorder(Graphics g) {
         g.setColor(getForeground());
         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arco, arco);
    }
    
    @Override
    public boolean contains(int x, int y) {
         if (formato == null || !formato.getBounds().equals(getBounds())) {
             formato = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, arco, arco);
         }
         return formato.contains(x, y);
    }

}
