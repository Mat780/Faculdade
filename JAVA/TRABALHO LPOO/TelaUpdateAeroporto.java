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

public class TelaUpdateAeroporto implements Tela, Update {
	private JPanel tela;
	private JPanel selecionarAeroporto;
	private JPanel cadastrarNome;

	private JMenuItem botaoOrigem;

	private DefaultComboBoxModel<Aeroporto> modelAeroporto;

	private JComboBox<Aeroporto> aeroporto;
	private JTextField nome;

	private ArrayList<Aeroporto> arrayAeroportoLocal;
	private Aeroporto[] vetorAeroporto;

	private Aeroporto aeroportoAlterando;
	private Aeroporto cloneAeroporto;

	private JButton salvar;

	
	public TelaUpdateAeroporto(JMenuItem botaoOrigem, ArrayList<Aeroporto> arrayAeroporto) {
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
		
		nome = new JTextField(20);
		cadastrarNome = new JPanel();
		cadastrarNome.setLayout(new FlowLayout());
		cadastrarNome.add(new JLabel("Nome: "));
		cadastrarNome.add(nome);
		tela.add(cadastrarNome);

		aeroporto.addActionListener((e) -> {
			if(aeroporto.getSelectedIndex() != 0) {
				Aeroporto castAeroporto = (Aeroporto) aeroporto.getSelectedItem();
				nome.setText(castAeroporto.getNomeAeroporto());

			} else {
				nome.setText("");
			}
		});
		
		salvar = new JButton("Atualizar");
		salvar.addActionListener((e) -> {
			try {
				if(aeroporto.getSelectedIndex() == 0 || nome.getText().equals("")) {
					throw new EmptyFieldException();
				}

				aeroportoAlterando = (Aeroporto)aeroporto.getSelectedItem();
				
				arrayAeroporto.forEach((Aeroporto obj) -> {
					if(
					nome.getText().equals(obj.getNomeAeroporto()) &&
					obj.equals(aeroportoAlterando) == false) 

					throw new EqualObjectException("aeroporto");
				});
				
				cloneAeroporto = aeroportoAlterando.clonar();

				aeroportoAlterando.setNomeAeroporto(nome.getText());
				
				atualizarTela();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Aeroporto atualizado com sucesso");
			}
			catch(OnlyLetterException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				setInfoBack(cloneAeroporto, aeroportoAlterando);
			}
			catch (EqualObjectException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
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
			Aeroporto aeroportoAntigo = (Aeroporto) Antigo;
			Aeroporto aeroportoAtual = (Aeroporto) Atual;

			aeroportoAtual.setNomeAeroporto(aeroportoAntigo.getNomeAeroporto());

		} catch(Exception e){
			e.printStackTrace();
		}
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
		nome.setText("");
	}

}
