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

public class TelaUpdateAviao implements Tela, Update {
	private JPanel tela;

	private JPanel selecionarAviao;
	private JPanel cadastrarEquipeBordo;
	
	private JMenuItem botaoOrigem;

	private JComboBox<Aviao> aviao;
	private JComboBox<EquipeBordo> equipeBordo;

	private DefaultComboBoxModel<Aviao> modelAviao;
	private DefaultComboBoxModel<EquipeBordo> modelEquipeBordo;

	private Aviao[] vetorAviao;
	private ArrayList<Aviao> arrayAviaoLocal;

	private EquipeBordo[] vetorEquipeBordo;
	private ArrayList<EquipeBordo> arrayEquipeBordoLocal;

	private Aviao aviaoAlterando;
	private Aviao cloneAviao;

	private JButton salvar;

	
	public TelaUpdateAviao(JMenuItem botaoOrigem, ArrayList<Aviao> arrayAviao, ArrayList<EquipeBordo> arrayEquipeBordo) {
		this.botaoOrigem = botaoOrigem;
		this.arrayAviaoLocal = arrayAviao;
		this.arrayEquipeBordoLocal = arrayEquipeBordo;
		
		atualizarTela();

		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
		
		modelAviao = new DefaultComboBoxModel<Aviao>(vetorAviao);
		aviao = new JComboBox<Aviao>(modelAviao);
		selecionarAviao = new JPanel();
		selecionarAviao.setLayout(new FlowLayout());
		selecionarAviao.add(new JLabel("Avião para atualizar: "));
		selecionarAviao.add(aviao);
		tela.add(selecionarAviao);
		
		modelEquipeBordo = new DefaultComboBoxModel<EquipeBordo>(vetorEquipeBordo);
		equipeBordo = new JComboBox<EquipeBordo>(modelEquipeBordo);
		cadastrarEquipeBordo = new JPanel();
		cadastrarEquipeBordo.setLayout(new FlowLayout());
		cadastrarEquipeBordo.add(new JLabel("Equipe de bordo: "));
		cadastrarEquipeBordo.add(equipeBordo);
		tela.add(cadastrarEquipeBordo);

		aviao.addActionListener((e) -> {
			if(aviao.getSelectedIndex() != 0){
				Aviao castAviao = (Aviao)aviao.getSelectedItem();
				equipeBordo.setSelectedItem(castAviao.getEquipeBordo());

			} else {
				equipeBordo.setSelectedIndex(0);
			}
			
		});
		
		salvar = new JButton("Atualizar");
		salvar.addActionListener((e) -> {
			try {
				if(aviao.getSelectedIndex() == 0 &&
					equipeBordo.getSelectedIndex() == 0) throw new EmptyFieldException();

				EquipeBordo castEquipeBordo = (EquipeBordo)equipeBordo.getSelectedItem();
				aviaoAlterando = (Aviao)aviao.getSelectedItem();
				cloneAviao = aviaoAlterando.clonar();

				if(castEquipeBordo.getContratada() && aviaoAlterando.getEquipeBordo().getPiloto().equals(castEquipeBordo.getPiloto()) == false){
					throw new AlreadyContractedException();
				}

				aviaoAlterando.getEquipeBordo().setContratada(false);

				aviaoAlterando.setEquipeBordo(castEquipeBordo);
				
				aviaoAlterando.getEquipeBordo().setContratada(true);

				atualizarTela();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Avião atualizado com sucesso");
			}
			catch (AlreadyContractedException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Error", 0);
				setInfoBack(cloneAviao, aviaoAlterando);
			}
			catch (EmptyFieldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}

		});
		tela.add(salvar);
		
	}

	@Override
	public void setInfoBack(Object Antigo, Object Atual){
		try{
			Aviao aviaoAntigo = (Aviao) Antigo;
			Aviao aviaoAtual = (Aviao) Atual;

			aviaoAtual.getEquipeBordo().setContratada(false);
			aviaoAtual.setEquipeBordo(aviaoAntigo.getEquipeBordo());
			aviaoAtual.getEquipeBordo().setContratada(true);

		} catch(Exception e){
			e.printStackTrace();
		}
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

		if(arrayEquipeBordoLocal.size() == 0) {
			this.vetorEquipeBordo = new EquipeBordo[1];
			
		} else {
			this.vetorEquipeBordo = new EquipeBordo[arrayEquipeBordoLocal.size() + 1];
			for(int i = 0; i < arrayEquipeBordoLocal.size(); i++){
				vetorEquipeBordo[i+1] = arrayEquipeBordoLocal.get(i);
			}
			
		}

		try {
			vetorEquipeBordo[0] = new EquipeBordo();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(modelEquipeBordo != null){
			for(int i = modelEquipeBordo.getSize() - 1; i > 0; i--){
				modelEquipeBordo.removeElementAt(i);
			}

			for(int i = 1; i < vetorEquipeBordo.length; i++){
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
		return "Atualizando um avião...";
	}

	@Override
	public void limparCampos(){
		aviao.setSelectedIndex(0);
		equipeBordo.setSelectedIndex(0);
		
	}

}
