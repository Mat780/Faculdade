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

public class TelaDeleteAeroporto implements Tela {
	private JPanel tela;
	private JPanel selecionarAeroporto;

	private JMenuItem botaoOrigem;

	private DefaultComboBoxModel<Aeroporto> modelAeroporto;

	private JComboBox<Aeroporto> aeroporto;

	private ArrayList<Aeroporto> arrayAeroportoLocal;
	private Aeroporto[] vetorAeroporto;

	private JButton deletar;

	
	public TelaDeleteAeroporto(JMenuItem botaoOrigem, ArrayList<Aeroporto> arrayAeroporto) {
		this.botaoOrigem = botaoOrigem;
		this.arrayAeroportoLocal = arrayAeroporto;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

		atualizarTela();

		modelAeroporto = new DefaultComboBoxModel<Aeroporto>(vetorAeroporto);
		aeroporto = new JComboBox<Aeroporto>(modelAeroporto);
		selecionarAeroporto = new JPanel();
		selecionarAeroporto.setLayout(new FlowLayout());
		selecionarAeroporto.add(new JLabel("Aeroporto: "));
		selecionarAeroporto.add(aeroporto);
		tela.add(selecionarAeroporto);

		deletar = new JButton("Deletar");
		deletar.addActionListener((e) -> {
			try {
				if(aeroporto.getSelectedIndex() == 0) {
					throw new EmptyFieldException();
				}

				Aeroporto aeroportoDeletar = (Aeroporto)aeroporto.getSelectedItem();

				if(aeroportoDeletar.verificarViagem() != 0) {
					throw new ImpossibleDeleteException("aeroporto", "viagem");
				}

				arrayAeroporto.remove(aeroportoDeletar);
				
				atualizarTela();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Aeroporto deletado com sucesso");
			}
			catch (EmptyFieldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			
		});
		tela.add(deletar);
		
	}


	private void atualizarTela(){

		if(arrayAeroportoLocal.size() == 0) {
			this.vetorAeroporto = new Aeroporto[1];
			
		} else {
			this.vetorAeroporto = new Aeroporto[arrayAeroportoLocal.size() + 1];
			for(int i = 0; i < arrayAeroportoLocal.size(); i++){
				vetorAeroporto[i+1] = arrayAeroportoLocal.get(i);
			}
			
		}

		try {
			vetorAeroporto[0] = new Aeroporto();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(modelAeroporto != null){
			for(int i = modelAeroporto.getSize() - 1; i > 0; i--){
				modelAeroporto.removeElementAt(i);
			}

			for(int i = 1; i < vetorAeroporto.length; i++){
				modelAeroporto.addElement(vetorAeroporto[i]);
			}
		}
	}
	
	@Override
	public JPanel getTela() {
		return tela;
	}

	@Override
	public JMenuItem getSource() {
		return botaoOrigem;
	}

	@Override
	public String getMensagemStatus() {
		return "Atualizando um aeroporto...";
	}

	@Override
	public void limparCampos(){
		aeroporto.setSelectedIndex(0);
	}

}
