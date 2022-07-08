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

public class TelaDeleteAviao implements Tela {
	private JPanel tela;

	private JPanel selecionarAviao;
	
	private JMenuItem botaoOrigem;

	private JComboBox<Aviao> aviao;

	private DefaultComboBoxModel<Aviao> modelAviao;

	private Aviao[] vetorAviao;
	private ArrayList<Aviao> arrayAviaoLocal;

	private JButton deletar;

	
	public TelaDeleteAviao(JMenuItem botaoOrigem, ArrayList<Aviao> arrayAviao) {
		this.botaoOrigem = botaoOrigem;
		this.arrayAviaoLocal = arrayAviao;
		
		atualizarTela();

		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
		
		modelAviao = new DefaultComboBoxModel<Aviao>(vetorAviao);
		aviao = new JComboBox<Aviao>(modelAviao);
		selecionarAviao = new JPanel();
		selecionarAviao.setLayout(new FlowLayout());
		selecionarAviao.add(new JLabel("Avião para deletar: "));
		selecionarAviao.add(aviao);
		tela.add(selecionarAviao);
		
		deletar = new JButton("Deletar");
		deletar.addActionListener((e) -> {
			try {
				if(aviao.getSelectedIndex() == 0) throw new EmptyFieldException();

				Aviao aviaoDeletar = (Aviao) aviao.getSelectedItem();

				if(aviaoDeletar.analalizarViagem() != 0){
					throw new ImpossibleDeleteException("aviao", "viagem");
				}

				arrayAviao.remove(aviaoDeletar);

				atualizarTela();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Avião deletado com sucesso");
			}
			catch (ImpossibleDeleteException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);

			}
			catch (EmptyFieldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				
			}

		});
		tela.add(deletar);
		
	}


	private void atualizarTela() {

		if(arrayAviaoLocal.size() == 0) {
			this.vetorAviao = new Aviao[1];
			
		} else {
			this.vetorAviao = new Aviao[arrayAviaoLocal.size() + 1];
			for(int i = 0; i < arrayAviaoLocal.size(); i++){
				vetorAviao[i+1] = arrayAviaoLocal.get(i);
			}
			
		}

		try {
			vetorAviao[0] = new Aviao();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(modelAviao != null){
			for(int i = modelAviao.getSize() - 1; i > 0; i--){
				modelAviao.removeElementAt(i);
			}

			for(int i = 1; i < vetorAviao.length; i++){
				modelAviao.addElement(vetorAviao[i]);
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
		return "Deletando um avião...";
	}

	@Override
	public void limparCampos(){
		aviao.setSelectedIndex(0);
		
	}

}
