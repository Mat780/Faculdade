import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TelaListAviao implements Tela {
	private JMenuItem botaoOrigem;
	private JPanel tela;
	private ArrayList<Aviao> arrayAviaoAtual;
	private ArrayList<Aviao> ultimosAviaoListadas;
	
	public TelaListAviao(JMenuItem botaoOrigem, ArrayList<Aviao> arrayAviao) {
		this.botaoOrigem = botaoOrigem;
		this.arrayAviaoAtual = arrayAviao;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JPanel getTela() {
		ultimosAviaoListadas = (ArrayList<Aviao>)arrayAviaoAtual.clone();
		
		tela.removeAll();
		
		for(int i = 0 ; i < ultimosAviaoListadas.size() ; i++) {
			tela.add(new JLabel(ultimosAviaoListadas.get(i).toString()));

		}
		
		
		return tela;
	}

	@Override
	public JMenuItem getSource() {
		return botaoOrigem;
	}

	@Override
	public String getMensagemStatus() {
		return "Listando aviões...";
	}

}
