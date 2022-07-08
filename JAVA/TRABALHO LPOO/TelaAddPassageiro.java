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

public class TelaAddPassageiro implements Tela {
	private JPanel tela;
	private JPanel cadastrarNome;
	private JPanel cadastrarCpf;
	private JPanel cadastrarViagem;
	private JPanel cadastrarPassaporte;

	private JMenuItem botaoOrigem;

	private JTextField nome;
	private JTextField cpf;
	private JComboBox<Viagem> viagem;
	private JComboBox<String> passaporte;

	private DefaultComboBoxModel<Viagem> model;

	private ArrayList<Viagem> arrayViagemLocal;
	private Viagem[] vetorViagem;

	private JButton salvar;

	
	public TelaAddPassageiro(JMenuItem botaoOrigem, ArrayList<Passageiro> arrayPassageiro, ArrayList<Viagem> arrayViagem) {
		this.botaoOrigem = botaoOrigem;
		this.arrayViagemLocal = arrayViagem;

		atualizarTela();

		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
		
		nome = new JTextField(20);
		cadastrarNome = new JPanel();
		cadastrarNome.setLayout(new FlowLayout());
		cadastrarNome.add(new JLabel("Nome: "));
		cadastrarNome.add(nome);
		tela.add(cadastrarNome);

		cpf = new JTextField(20);
		cadastrarCpf = new JPanel();
		cadastrarCpf.setLayout(new FlowLayout());
		cadastrarCpf.add(new JLabel("CPF: "));
		cadastrarCpf.add(cpf);
		tela.add(cadastrarCpf);

		model = new DefaultComboBoxModel<Viagem>(vetorViagem);
		viagem = new JComboBox<Viagem>(model);
		cadastrarViagem = new JPanel();
		cadastrarViagem.setLayout(new FlowLayout());
		cadastrarViagem.add(new JLabel("Viagem: "));
		cadastrarViagem.add(viagem);
		tela.add(cadastrarViagem);
		
		
		String[] vetorAux = {"Selecionar", "Sim", "Não"};
		passaporte = new JComboBox<String>(vetorAux);
		cadastrarPassaporte = new JPanel();
		cadastrarPassaporte.setLayout(new FlowLayout());
		cadastrarPassaporte.add(new JLabel("Passaporte está valido: "));
		cadastrarPassaporte.add(passaporte);
		tela.add(cadastrarPassaporte);
		
		salvar = new JButton("Cadastrar");
		salvar.addActionListener((e) -> {
			try {
				if(nome.getText().equals("") ||
				  cpf.getText().equals("") ||
				  viagem.getSelectedIndex() == 0 ||
				  passaporte.getSelectedIndex() == 0) {
					throw new EmptyFieldException();
				}

				Viagem castViagem = (Viagem) viagem.getSelectedItem();

				Passageiro passageiro = new Passageiro(nome.getText(), cpf.getText(), passaporte.getSelectedItem().equals("Sim") ? true : false, castViagem);

				if(castViagem.getIsInternational()){
					if(passageiro.getPassaporteAtualizado() == false) {
						throw new InvalidPassportException();
					}
				}

				arrayPassageiro.forEach((Passageiro obj) -> {
					if(passageiro.equals(obj)) throw new EqualObjectException("passageiro");
				});

				castViagem.addPassageiro(passageiro);

				arrayPassageiro.add(passageiro);

				limparCampos();
				JOptionPane.showMessageDialog(null, "Passageiro salvo com sucesso");
			}
			catch(OnlyLetterException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch(InvalidCpfException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch (EqualObjectException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch (InvalidPassportException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);
			}
			catch (EmptyFieldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			
		});
		tela.add(salvar);
		
	}
	
	private void atualizarTela() {
		if(arrayViagemLocal.size() == 0) {
			this.vetorViagem = new Viagem[1];
			
		} else {
			this.vetorViagem = new Viagem[arrayViagemLocal.size() + 1];
			for(int i = 0; i < arrayViagemLocal.size(); i++){
				vetorViagem[i+1] = arrayViagemLocal.get(i);
			}
			
		}

		try {
			vetorViagem[0] = new Viagem();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(model != null){
			for(int i = model.getSize() - 1; i > 0; i--){
				model.removeElementAt(i);
			}

			for(int i = 1; i < vetorViagem.length; i++){
				model.addElement(vetorViagem[i]);
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
		return "Cadastrando um novo passageiro...";
	}

	@Override
	public void limparCampos(){
		nome.setText("");
		cpf.setText("");
		viagem.setSelectedIndex(0);
		passaporte.setSelectedIndex(0);
	}

}
