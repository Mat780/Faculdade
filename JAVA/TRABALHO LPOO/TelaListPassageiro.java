import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TelaListPassageiro implements Tela {
	private JMenuItem botaoOrigem;
	private JPanel tela;
	private ArrayList<Passageiro> arrayPassageiroAtual;
	private ArrayList<Passageiro> ultimosPassageiroListadas;
	
	public TelaListPassageiro(JMenuItem botaoOrigem, ArrayList<Passageiro> arrayPassageiro) {
		this.botaoOrigem = botaoOrigem;
		this.arrayPassageiroAtual = arrayPassageiro;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JPanel getTela() {
		ultimosPassageiroListadas = (ArrayList<Passageiro>)arrayPassageiroAtual.clone();
		
		tela.removeAll();
		
		for(int i = 0 ; i < ultimosPassageiroListadas.size() ; i++) {
			tela.add(new JLabel(ultimosPassageiroListadas.get(i).toString()));

		}
	
		return tela;
	}

	@Override
	public JMenuItem getSource() {
		return botaoOrigem;
	}

	@Override
	public String getMensagemStatus() {
		return "Listando passageiros...";
	}

}
