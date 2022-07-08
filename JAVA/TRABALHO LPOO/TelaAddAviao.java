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
import javax.swing.JTextField;

public class TelaAddAviao implements Tela {
	private JPanel tela;

	private JPanel cadastrarModeloAviao;
	private JPanel cadastrarEquipeBordo;
	private JPanel cadastrarInternacional;
	
	private JMenuItem botaoOrigem;

	private JTextField modeloAviao;
	private JComboBox<EquipeBordo> equipeBordo;
	private JComboBox<String> internacional;

	private DefaultComboBoxModel<EquipeBordo> model;

	private EquipeBordo[] vetorEquipeBordo;
	private ArrayList<EquipeBordo> arrayEquipeBordoLocal;

	private JButton salvar;

	
	public TelaAddAviao(JMenuItem botaoOrigem, ArrayList<Aviao> arrayAviao, ArrayList<EquipeBordo> arrayEquipeBordo) {
		this.botaoOrigem = botaoOrigem;
		this.arrayEquipeBordoLocal = arrayEquipeBordo;
		
		atualizarTela();

		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

		modeloAviao = new JTextField(20);
		cadastrarModeloAviao = new JPanel();
		cadastrarModeloAviao.setLayout(new FlowLayout());
		cadastrarModeloAviao.add(new JLabel("Modelo do avião: "));
		cadastrarModeloAviao.add(modeloAviao);
		tela.add(cadastrarModeloAviao);

		model = new DefaultComboBoxModel<EquipeBordo>(vetorEquipeBordo);
		equipeBordo = new JComboBox<EquipeBordo>(model);
		cadastrarEquipeBordo = new JPanel();
		cadastrarEquipeBordo.setLayout(new FlowLayout());
		cadastrarEquipeBordo.add(new JLabel("Equipe de bordo: "));
		cadastrarEquipeBordo.add(equipeBordo);
		tela.add(cadastrarEquipeBordo);

		String[] vetorAux = {"Selecionar", "Sim", "Não"};
		internacional = new JComboBox<String>(vetorAux);
		cadastrarInternacional = new JPanel();
		cadastrarInternacional.setLayout(new FlowLayout());
		cadastrarInternacional.add(new JLabel("Faz viagens internacionais: "));
		cadastrarInternacional.add(internacional);
		tela.add(cadastrarInternacional);
		
		salvar = new JButton("Cadastrar");
		salvar.addActionListener((e) -> {
			try {
				if(modeloAviao.getText().equals("") || 
				equipeBordo.getSelectedIndex() == 0 || 
				internacional.getSelectedIndex() == 0) throw new EmptyFieldException();

				EquipeBordo castEquipeBordo = (EquipeBordo)equipeBordo.getSelectedItem();

				if(castEquipeBordo.getContratada()){
					throw new AlreadyContractedException();
				}

				Aviao aviao = new Aviao(modeloAviao.getText(), 
				(internacional.getSelectedItem().equals("Sim") ? true : false),
				castEquipeBordo);
				
				castEquipeBordo.setContratada(true);

				arrayAviao.add(aviao);
				limparCampos();
				JOptionPane.showMessageDialog(null, "Avião salvo com sucesso");
			}
			catch (EmptyFieldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch (AlreadyContractedException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);

			}

		});
		tela.add(salvar);
		
	}

	private void atualizarTela() {
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

		if(model != null){
			for(int i = model.getSize() - 1; i > 0; i--){
				model.removeElementAt(i);
			}

			for(int i = 1; i < vetorEquipeBordo.length; i++){
				model.addElement(vetorEquipeBordo[i]);
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
		return "Cadastrando um novo avião...";
	}

	@Override
	public void limparCampos(){
		modeloAviao.setText("");
		equipeBordo.setSelectedIndex(0);
		internacional.setSelectedIndex(0);
	}

}
