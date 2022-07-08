import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TelaDeleteEquipeBordo implements Tela {
	private JPanel tela;

	private JPanel selecionarEquipeBordo;

	private JMenuItem botaoOrigem;

	private DefaultComboBoxModel<EquipeBordo> modelEquipeBordo;

	private JComboBox<EquipeBordo> equipeBordo;

	private EquipeBordo[] vetorEquipeBordo;

	private ArrayList<EquipeBordo> arrayEquipeBordoLocal;

	private EquipeBordo equipeBordoDeletar;

	private JButton deletar;

	public TelaDeleteEquipeBordo(JMenuItem botaoOrigem, ArrayList<EquipeBordo> arrayEquipeBordo) {
		this.botaoOrigem = botaoOrigem;
		this.arrayEquipeBordoLocal = arrayEquipeBordo;

		atualizarTela();

		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

		modelEquipeBordo = new DefaultComboBoxModel<EquipeBordo>(vetorEquipeBordo);

		equipeBordo = new JComboBox<EquipeBordo>(modelEquipeBordo);

		selecionarEquipeBordo = new JPanel();
		selecionarEquipeBordo.setLayout(new FlowLayout());
		selecionarEquipeBordo.add(new JLabel("Equipe de bordo a ser deletada:"));
		selecionarEquipeBordo.add(equipeBordo);
		tela.add(selecionarEquipeBordo);


		deletar = new JButton("Deletar");
		deletar.addActionListener((e) -> {
			try {
				if (equipeBordo.getSelectedIndex() == 0) throw new EmptyFieldException();

				equipeBordoDeletar = (EquipeBordo) equipeBordo.getSelectedItem();

				if(equipeBordoDeletar.getContratada()){
					throw new ImpossibleDeleteException("equipe de bordo", "avião");
				}

				equipeBordoDeletar.setTodosAbordo(false);

				arrayEquipeBordo.remove(equipeBordoDeletar);

				atualizarTela();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Equipe de bordo deletada com sucesso");
			}
			catch (ImpossibleDeleteException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);

			}
			catch (EmptyFieldException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}

		});
		tela.add(deletar);

	}

	private void atualizarTela() {

		if (arrayEquipeBordoLocal.size() == 0) {
			this.vetorEquipeBordo = new EquipeBordo[1];

		} else {
			this.vetorEquipeBordo = new EquipeBordo[arrayEquipeBordoLocal.size() + 1];
			for (int i = 0; i < arrayEquipeBordoLocal.size(); i++) {
				vetorEquipeBordo[i + 1] = arrayEquipeBordoLocal.get(i);
			}

		}

		try {
			vetorEquipeBordo[0] = new EquipeBordo();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (modelEquipeBordo != null) {
			for (int i = modelEquipeBordo.getSize() - 1; i > 0; i--) {
				modelEquipeBordo.removeElementAt(i);
			}

			for (int i = 1; i < vetorEquipeBordo.length; i++) {
				modelEquipeBordo.addElement(vetorEquipeBordo[i]);
			}
		}

	}

	@Override
	public JPanel getTela() {
		atualizarTela();

		return tela;
	}

	@Override
	public JMenuItem getSource() {
		return botaoOrigem;
	}

	@Override
	public String getMensagemStatus() {
		return "Deletando uma equipe de bordo...";
	}

	@Override
	public void limparCampos() {
		equipeBordo.setSelectedIndex(0);
	}

}
