import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaAddComissario implements Tela {
	private JPanel tela;
	private JPanel cadastrarNome;
	private JPanel cadastrarCpf;
	private JPanel cadastrarTarefa;

	private JMenuItem botaoOrigem;

	private JTextField nome;
	private JTextField cpf;

	private JComboBox<String> tarefa;

	private JButton salvar;

	
	public TelaAddComissario(JMenuItem botaoOrigem, ArrayList<Comissario> arrayComissario) {
		this.botaoOrigem = botaoOrigem;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
		
		nome = new JTextField(20);
		cadastrarNome = new JPanel();
		cadastrarNome.setLayout(new FlowLayout());
		cadastrarNome.add(new JLabel("Nome:"));
		cadastrarNome.add(nome);
		tela.add(cadastrarNome);

		cpf = new JTextField(20);
		cadastrarCpf = new JPanel();
		cadastrarCpf.setLayout(new FlowLayout());
		cadastrarCpf.add(new JLabel("CPF:"));
		cadastrarCpf.add(cpf);
		tela.add(cadastrarCpf);

		String[] vetorTarefas = {"Selecionar", "Limpeza", "Instruções", "Serviço", "Recepção"};
		tarefa = new JComboBox<String>(vetorTarefas);

		cadastrarTarefa = new JPanel();
		cadastrarTarefa.setLayout(new FlowLayout());
		cadastrarTarefa.add(new JLabel("Tarefa:"));
		cadastrarTarefa.add(tarefa);
		tela.add(cadastrarTarefa);

		
		salvar = new JButton("Cadastrar");
		salvar.addActionListener((e) -> {
			try {
				if(nome.getText().equals("") || cpf.getText().equals("") || tarefa.getSelectedItem().toString().equals("Selecionar")){
					throw new EmptyFieldException();
				}

				Comissario comissario = new Comissario(nome.getText(), cpf.getText(), tarefa.getSelectedItem().toString());
				
				arrayComissario.forEach((Comissario obj) -> {
					if(comissario.equals(obj)) throw new EqualObjectException("comissario");
				});

				arrayComissario.add(comissario);
				limparCampos();
				JOptionPane.showMessageDialog(null, "Comissario salvo com sucesso");
			}
			catch (InvalidTaskException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);
			}
			catch (OnlyLetterException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch (InvalidCpfException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch (EmptyFieldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}

		});
		tela.add(salvar);
		
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
		return "Cadastrando um novo comissario...";
	}

	@Override
	public void limparCampos(){
		nome.setText("");
		cpf.setText("");
		tarefa.setSelectedIndex(0);
	}

}
