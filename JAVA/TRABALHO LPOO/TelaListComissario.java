import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TelaListComissario implements Tela {
	private JMenuItem botaoOrigem;
	private JPanel tela;
	private ArrayList<Comissario> arrayComissarioAtual;
	private ArrayList<Comissario> ultimosComissariosListadas;
	
	public TelaListComissario(JMenuItem botaoOrigem, ArrayList<Comissario> arrayComissario) {
		this.botaoOrigem = botaoOrigem;
		this.arrayComissarioAtual = arrayComissario;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JPanel getTela() {
		
		ultimosComissariosListadas = (ArrayList<Comissario>)arrayComissarioAtual.clone();
		
		tela.removeAll();
		
		for(int i = 0 ; i < ultimosComissariosListadas.size() ; i++) {
			tela.add(new JLabel(ultimosComissariosListadas.get(i).toString()));

		}
		
		return tela;
	}

	@Override
	public JMenuItem getSource() {
		return botaoOrigem;
	}

	@Override
	public String getMensagemStatus() {
		return "Listando Comissarios...";
	}

}
