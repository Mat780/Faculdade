package interface_usuario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class SemVFXJButton extends JButton {

	private static final long serialVersionUID = 8823643705950042338L;

    private boolean estaDentro;
    private Color corBotao;
    private Color corQuandoMouseEntrarNoBotao;
    private Color corQuandoMouseClickar;
    private Color corBorda;
    private int arco;

    public SemVFXJButton(String mensagemLabel, Color corBotao, Color corBorda) {
        arco = 35;
        
        this.corBotao 				= corBotao; 
        this.corBorda 				= corBorda;
      	corQuandoMouseEntrarNoBotao = new Color(219, 219, 219);
      	corQuandoMouseClickar 		= new Color(138, 138, 138, 120);
        
        setText(mensagemLabel);
        setFocusable(false);
        setBorderPainted(false);
        setCorBotao(corBotao);
        setContentAreaFilled(false);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(corQuandoMouseEntrarNoBotao);
                estaDentro = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(corBotao);
                estaDentro = false;

            }

            @Override
            public void mousePressed(MouseEvent me) {
                setBackground(corQuandoMouseClickar);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (estaDentro) {
                    setBackground(corQuandoMouseEntrarNoBotao);
                } else {
                    setBackground(corBotao);
                }
            }
        });
    }
    
    public SemVFXJButton(String mensagemLabel, Color corBotao, Color corBorda, Color corQuandoMouseEntrar, Color corQuandoMouseClickar, int arco) {
        this.arco = arco;
        
        this.corBotao 					 = corBotao; 
        this.corBorda 					 = corBorda;
      	this.corQuandoMouseEntrarNoBotao = corQuandoMouseEntrar;
      	this.corQuandoMouseClickar 		 = corQuandoMouseClickar;
        
        setText(mensagemLabel);
        setFocusable(false);
        setBorderPainted(false);
        setCorBotao(corBotao);
        setContentAreaFilled(false);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(corQuandoMouseEntrarNoBotao);
                estaDentro = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(corBotao);
                estaDentro = false;

            }

            @Override
            public void mousePressed(MouseEvent me) {
                setBackground(corQuandoMouseClickar);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (estaDentro) {
                    setBackground(corQuandoMouseEntrarNoBotao);
                } else {
                    setBackground(corBotao);
                }
            }
        });
    }
    
    public boolean getEstaDentro() {
        return estaDentro;
    }

    public void setEstaDentro(boolean estaDentro) {
        this.estaDentro = estaDentro;
    }

    public void setCorBotao(Color cor) {
        this.corBotao = cor;
        setBackground(cor);
    }
    
    public void setCorQuandoMouseEntrar(Color corQuandoMouseEntrar) {
        this.corQuandoMouseEntrarNoBotao = corQuandoMouseEntrar;
    }
    
    public void setCorQuandoMouseClickar(Color corClick) {
        this.corQuandoMouseClickar = corClick;
    }
    
    public void setCorBorda(Color corBorda) {
        this.corBorda = corBorda;
    }
    
    public void setArco(int arco) {
        this.arco = arco;
    }
    
    public Color getCorBotao() {
        return corBotao;
    }

    public Color getCorQuandoMouseEntrarNoBotao() {
        return corQuandoMouseEntrarNoBotao;
    }

    public Color getCorQuandoMouseClickar() {
        return corQuandoMouseClickar;
    }

    public Color getCorBorda() {
        return corBorda;
    }

    public int getArco() {
        return arco;
    }

    @Override
    protected void paintComponent(Graphics graficos) {
        Graphics2D grafico2D = (Graphics2D) graficos;
        grafico2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        grafico2D.setColor(corBorda);
        grafico2D.fillRoundRect(0, 0, getWidth(), getHeight(), arco, arco);

        grafico2D.setColor(getBackground());
        grafico2D.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, arco, arco); // Mexer aqui muda o tamanho da borda acima
        super.paintComponent(graficos);
    }
	
}
