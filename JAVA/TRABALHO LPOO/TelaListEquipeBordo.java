import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TelaListEquipeBordo implements Tela {
	private JMenuItem botaoOrigem;
	private JPanel tela;
	private ArrayList<EquipeBordo> arrayEquipeBordoAtual;
	private ArrayList<EquipeBordo> ultimoasEquipesListadas;
	
	public TelaListEquipeBordo(JMenuItem botaoOrigem, ArrayList<EquipeBordo> arrayEquipeBordo) {
		this.botaoOrigem = botaoOrigem;
		this.arrayEquipeBordoAtual = arrayEquipeBordo;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JPanel getTela() {
		ultimoasEquipesListadas = (ArrayList<EquipeBordo>)arrayEquipeBordoAtual.clone();
		
		tela.removeAll();
		
		for(int i = 0 ; i < ultimoasEquipesListadas.size() ; i++) {
			tela.add(new JLabel(ultimoasEquipesListadas.get(i).toString()));

		}
		
		return tela;
	}

	@Override
	public JMenuItem getSource() {
		return botaoOrigem;
	}

	@Override
	public String getMensagemStatus() {
		return "Listando as equipes de bordo...";
	}

}
