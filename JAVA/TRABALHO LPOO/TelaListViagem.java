import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TelaListViagem implements Tela {
	private JMenuItem botaoOrigem;
	private JPanel tela;
	private ArrayList<Viagem> arrayPilotoAtual;
	private ArrayList<Viagem> ultimosPilotosListadas;
	
	public TelaListViagem(JMenuItem botaoOrigem, ArrayList<Viagem> arrayViagem) {
		this.botaoOrigem = botaoOrigem;
		this.arrayPilotoAtual = arrayViagem;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JPanel getTela() {
		ultimosPilotosListadas = (ArrayList<Viagem>)arrayPilotoAtual.clone();
		
		tela.removeAll();
		
		for(int i = 0 ; i < ultimosPilotosListadas.size() ; i++) {
			tela.add(new JLabel(ultimosPilotosListadas.get(i).toString()));

		}
	
		return tela;
	}

	@Override
	public JMenuItem getSource() {
		return botaoOrigem;
	}

	@Override
	public String getMensagemStatus() {
		return "Listando viagens...";
	}

}
