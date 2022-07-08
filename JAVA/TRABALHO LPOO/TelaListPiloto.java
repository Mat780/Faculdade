import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TelaListPiloto implements Tela {
	private JMenuItem botaoOrigem;
	private JPanel tela;
	private ArrayList<Piloto> arrayPilotoAtual;
	private ArrayList<Piloto> ultimosPilotosListadas;
	
	public TelaListPiloto(JMenuItem botaoOrigem, ArrayList<Piloto> arrayPiloto) {
		this.botaoOrigem = botaoOrigem;
		this.arrayPilotoAtual = arrayPiloto;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JPanel getTela() {
		ultimosPilotosListadas = (ArrayList<Piloto>)arrayPilotoAtual.clone();
		
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
		return "Listando pilotos...";
	}

}
