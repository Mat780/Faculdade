package interface_usuario;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class RoundJPanel extends JPanel{

	private static final long serialVersionUID = 5658930683579565919L;
	
	private int arcoSuperiorEsquerdo = 0;
    private int arcoSuperiorDireito = 0;
    private int arcoInferiorEsquerdo = 0;
    private int arcoInferiorDireito = 0;

    public RoundJPanel(int arcoInferiorEsquerdo, int arcoInferiorDireito, int arcoSuperiorEsquerdo, int arcoSuperiorDireito) {
        setOpaque(false);
        this.arcoSuperiorEsquerdo = arcoSuperiorEsquerdo;
        this.arcoSuperiorDireito = arcoSuperiorDireito;
        this.arcoInferiorEsquerdo = arcoInferiorEsquerdo;
        this.arcoInferiorDireito = arcoInferiorDireito;
    }

    @Override
    protected void paintComponent(Graphics graficos) {
        Graphics2D graficoEm2D = (Graphics2D) graficos.create();
        graficoEm2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graficoEm2D.setColor(getBackground());
        Area area = new Area(criarArcoSuperiorEsquerdo());
        if (arcoSuperiorDireito > 0) {
            area.intersect(new Area(criarArcoSuperiorDireito()));
        }
        if (arcoInferiorEsquerdo > 0) {
            area.intersect(new Area(criarArcoInferiorEsquerdo()));
        }
        if (arcoInferiorDireito > 0) {
            area.intersect(new Area(criarArcoInferiorDireito()));
        }
        graficoEm2D.fill(area);
        graficoEm2D.dispose();
        super.paintComponent(graficos);
    }

    private Shape criarArcoSuperiorEsquerdo() {
        int comprimento = getWidth();
        int altura = getHeight();
        int arcoNoEixoX = Math.min(comprimento, arcoSuperiorEsquerdo);
        int arcoNoEixoY = Math.min(altura, arcoSuperiorEsquerdo);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, comprimento, altura, arcoNoEixoX, arcoNoEixoY));
        area.add(new Area(new Rectangle2D.Double(arcoNoEixoX / 2, 0, comprimento - arcoNoEixoX / 2, altura)));
        area.add(new Area(new Rectangle2D.Double(0, arcoNoEixoY / 2, comprimento, altura - arcoNoEixoY / 2)));
        return area;
    }

    private Shape criarArcoSuperiorDireito() {
        int comprimento = getWidth();
        int altura = getHeight();
        int arcoNoEixoX = Math.min(comprimento, arcoSuperiorDireito);
        int arcoNoEixoY = Math.min(altura, arcoSuperiorDireito);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, comprimento, altura, arcoNoEixoX, arcoNoEixoY));
        area.add(new Area(new Rectangle2D.Double(0, 0, comprimento - arcoNoEixoX / 2, altura)));
        area.add(new Area(new Rectangle2D.Double(0, arcoNoEixoY / 2, comprimento, altura - arcoNoEixoY / 2)));
        return area;
    }

    private Shape criarArcoInferiorEsquerdo() {
        int comprimento = getWidth();
        int altura = getHeight();
        int arcoNoEixoX = Math.min(comprimento, arcoInferiorEsquerdo);
        int arcoNoEixoY = Math.min(altura, arcoInferiorEsquerdo);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, comprimento, altura, arcoNoEixoX, arcoNoEixoY));
        area.add(new Area(new Rectangle2D.Double(arcoNoEixoX / 2, 0, comprimento - arcoNoEixoX / 2, altura)));
        area.add(new Area(new Rectangle2D.Double(0, 0, comprimento, altura - arcoNoEixoY / 2)));
        return area;
    }

    private Shape criarArcoInferiorDireito() {
        int comprimento = getWidth();
        int altura = getHeight();
        int arcoNoEixoX = Math.min(comprimento, arcoInferiorDireito);
        int arcoNoEixoY = Math.min(altura, arcoInferiorDireito);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, comprimento, altura, arcoNoEixoX, arcoNoEixoY));
        area.add(new Area(new Rectangle2D.Double(0, 0, comprimento - arcoNoEixoX / 2, altura)));
        area.add(new Area(new Rectangle2D.Double(0, 0, comprimento, altura - arcoNoEixoY / 2)));
        return area;
    }


}
