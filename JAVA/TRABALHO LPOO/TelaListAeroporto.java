import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TelaListAeroporto implements Tela {
	private JMenuItem botaoOrigem;
	private JPanel tela;
	private ArrayList<Aeroporto> arrayAeroportoAtual;
	private ArrayList<Aeroporto> ultimosAeroportoListadas;
	
	public TelaListAeroporto(JMenuItem botaoOrigem, ArrayList<Aeroporto> arrayAeroporto) {
		this.botaoOrigem = botaoOrigem;
		this.arrayAeroportoAtual = arrayAeroporto;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JPanel getTela() {
		ultimosAeroportoListadas = (ArrayList<Aeroporto>)arrayAeroportoAtual.clone();
		
		tela.removeAll();
		
		for(int i = 0 ; i < ultimosAeroportoListadas.size() ; i++) {
			tela.add(new JLabel(ultimosAeroportoListadas.get(i).toString()));

		}
	
	return tela;
	}

	@Override
	public JMenuItem getSource() {
		return botaoOrigem;
	}

	@Override
	public String getMensagemStatus() {
		return "Listando aeroportos...";
	}

}
