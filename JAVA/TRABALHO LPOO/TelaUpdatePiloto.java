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
import javax.swing.DefaultComboBoxModel;


public class TelaUpdatePiloto implements Tela, Update{
	private JPanel tela;
	private JPanel selecionarPiloto;
	private JPanel cadastrarNome;
	private JPanel cadastrarCpf;

	private JMenuItem botaoOrigem;

	DefaultComboBoxModel<Piloto> model = null;
	private JComboBox<Piloto> pilotoSelecionado;

	private JTextField nome;
	private JTextField cpf;

	private ArrayList<Piloto> arrayPilotoLocal;
	private ArrayList<Piloto> Antigo;
	private Piloto[] vetorPilotos;

	private Piloto pilotoAlterando;
	private Piloto clonePiloto;

	private JButton salvar;

	
	public TelaUpdatePiloto(JMenuItem botaoOrigem, ArrayList<Piloto> arrayPiloto) {
		this.botaoOrigem = botaoOrigem;
		this.Antigo = arrayPiloto;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

		atualizarTela();

		model = new DefaultComboBoxModel<Piloto>(vetorPilotos);
		pilotoSelecionado = new JComboBox<Piloto>(model);
		selecionarPiloto = new JPanel();
		selecionarPiloto.setLayout(new FlowLayout());
		selecionarPiloto.add(new JLabel("Piloto a ser atualizado"));
		selecionarPiloto.add(pilotoSelecionado);
		tela.add(selecionarPiloto);
		
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

		pilotoSelecionado.addActionListener((e) -> {
			if(pilotoSelecionado.getSelectedIndex() != 0){
				Piloto castPiloto = (Piloto) pilotoSelecionado.getSelectedItem();

				nome.setText(castPiloto.getNome());
				cpf.setText(castPiloto.getCpf());

			} else {
				nome.setText("");
				cpf.setText("");
			}
		});

		salvar = new JButton("Atualizar");
		salvar.addActionListener((e) -> {
			try {

				if(nome.getText().equals("") ||
					cpf.getText().equals("") ||
					pilotoSelecionado.getSelectedIndex() == 0){
					throw new EmptyFieldException();
				}

				arrayPiloto.forEach((Piloto obj) -> {
					if(obj.getCpf().equals(cpf.getText()) && obj.equals((Piloto)pilotoSelecionado.getSelectedItem()) == false)
					throw new EqualObjectException("piloto");
				});

				pilotoAlterando = (Piloto) pilotoSelecionado.getSelectedItem();
				clonePiloto = pilotoAlterando.clonar();

				pilotoAlterando.setNome(nome.getText());
				pilotoAlterando.setCpf(cpf.getText());

				atualizarTela();
				limparCampos();

				JOptionPane.showMessageDialog(null, "Piloto atualizado com sucesso");
			}
			catch(OnlyLetterException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				setInfoBack(clonePiloto, pilotoAlterando);

			}
			catch(InvalidCpfException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				setInfoBack(clonePiloto, pilotoAlterando);
			}
			catch(TooOldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);
				setInfoBack(clonePiloto, pilotoAlterando);
			}
			catch(TooYoungException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);
				setInfoBack(clonePiloto, pilotoAlterando);

			}
			catch(ImpossibleTimeExperienceException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);
				setInfoBack(clonePiloto, pilotoAlterando);

			}
			catch (OnlyPositiveNumbersException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				setInfoBack(clonePiloto, pilotoAlterando);

			} 
			catch (EqualObjectException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch (EmptyFieldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch(NumberFormatException event) {
				JOptionPane.showMessageDialog(null, "Os campos de idade e anos de experiência precisam ser compostos de numeros apenas", "Aviso", 2);
				setInfoBack(clonePiloto, pilotoAlterando);

			}
		});
		tela.add(salvar);
		
	}

	@Override
	public void setInfoBack(Object Antigo, Object Atual){
		try{
			Piloto pilotoAntigo = (Piloto) Antigo;
			Piloto pilotoAtual = (Piloto) Atual;

			pilotoAtual.setNome(pilotoAntigo.getNome());
			pilotoAtual.setCpf(pilotoAntigo.getCpf());
			pilotoAtual.setIdade(pilotoAntigo.getIdade());
			pilotoAtual.setTempoExp(pilotoAntigo.getTempoExp());

		} catch(Exception e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void atualizarTela(){

		arrayPilotoLocal = (ArrayList<Piloto>) Antigo.clone();

		if(arrayPilotoLocal.size() == 0) {
			this.vetorPilotos = new Piloto[1];
			
		} else {
			this.vetorPilotos = new Piloto[arrayPilotoLocal.size() + 1];
			for(int i = 0; i < arrayPilotoLocal.size(); i++){
				vetorPilotos[i+1] = arrayPilotoLocal.get(i);
			}
			
		}

		try {
			vetorPilotos[0] = new Piloto();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(model != null){
			for(int i = model.getSize() - 1; i > 0; i--){
				model.removeElementAt(i);
			}

			for(int i = 1; i < vetorPilotos.length; i++){
				model.addElement(vetorPilotos[i]);
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
		return "Atualizando um piloto...";
	}

	@Override
	public void limparCampos(){
		pilotoSelecionado.setSelectedIndex(0);
		nome.setText("");
		cpf.setText("");
	}

}
