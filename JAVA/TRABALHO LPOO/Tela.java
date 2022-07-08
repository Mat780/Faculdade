import javax.swing.JMenuItem;
import javax.swing.JPanel;

public interface Tela {
	public JPanel getTela();
	public JMenuItem getSource();
	public String getMensagemStatus();
	default public void limparCampos(){}
}
