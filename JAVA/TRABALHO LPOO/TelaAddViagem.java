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

public class TelaAddViagem implements Tela {
	private JPanel tela;

	private JPanel cadastrarAviao;
	private JPanel cadastrarAeroportoRemetente;
	private JPanel cadastrarAeroportoDestino;
	private JPanel cadastrarValorPassagem;
	
	private JMenuItem botaoOrigem;

	private JComboBox<Aviao> aviao;
	private JComboBox<Aeroporto> aeroportoRemetente;
	private JComboBox<Aeroporto> aeroportoDestino;
	private JTextField valorPassagem;

	private DefaultComboBoxModel<Aviao> modelAviao;
	private DefaultComboBoxModel<Aeroporto> modelRemetente;
	private DefaultComboBoxModel<Aeroporto> modelDestino;
	
	private Aviao[] vetorAviao;
	private ArrayList<Aviao> arrayAviaoLocal;

	private Aeroporto[] vetorAeroporto;
	private ArrayList<Aeroporto> arrayAeroportoLocal;


	private JButton salvar;

	
	public TelaAddViagem(JMenuItem botaoOrigem, ArrayList<Viagem> arrayViagem, ArrayList<Aviao> arrayAviao, ArrayList<Aeroporto> arrayAeroporto) {
		this.botaoOrigem = botaoOrigem;
		this.arrayAviaoLocal = arrayAviao;
		this.arrayAeroportoLocal = arrayAeroporto;
		
		atualizarTela();

		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

		modelAviao = new DefaultComboBoxModel<Aviao>(vetorAviao);
		aviao = new JComboBox<Aviao>(modelAviao);
		cadastrarAviao = new JPanel();
		cadastrarAviao.setLayout(new FlowLayout());
		cadastrarAviao.add(new JLabel("Avião: "));
		cadastrarAviao.add(aviao);
		tela.add(cadastrarAviao);

		modelRemetente = new DefaultComboBoxModel<Aeroporto> (vetorAeroporto);
		aeroportoRemetente = new JComboBox<Aeroporto>(modelRemetente);
		cadastrarAeroportoRemetente = new JPanel();
		cadastrarAeroportoRemetente.setLayout(new FlowLayout());
		cadastrarAeroportoRemetente.add(new JLabel("Aeroporto de origem: "));
		cadastrarAeroportoRemetente.add(aeroportoRemetente);
		tela.add(cadastrarAeroportoRemetente);
		
		modelDestino = new DefaultComboBoxModel<Aeroporto>(vetorAeroporto);
		aeroportoDestino = new JComboBox<Aeroporto>(modelDestino);
		cadastrarAeroportoDestino = new JPanel();
		cadastrarAeroportoDestino.setLayout(new FlowLayout());
		cadastrarAeroportoDestino.add(new JLabel("Aeroporto do destino: "));
		cadastrarAeroportoDestino.add(aeroportoDestino);
		cadastrarAeroportoDestino.add(aeroportoDestino);
		tela.add(cadastrarAeroportoDestino);
		
		valorPassagem = new JTextField(20);
		cadastrarValorPassagem = new JPanel();
		cadastrarValorPassagem.setLayout(new FlowLayout());
		cadastrarValorPassagem.add(new JLabel("Valor da passagem: "));
		cadastrarValorPassagem.add(valorPassagem);
		tela.add(cadastrarValorPassagem);
		
		salvar = new JButton("Cadastrar");
		salvar.addActionListener((e) -> {
			try {
				if(aviao.getSelectedIndex() == 0 || 
					aeroportoRemetente.getSelectedIndex() == 0 || 
					aeroportoDestino.getSelectedIndex() == 0 || 
					valorPassagem.getText().equals("")) throw new EmptyFieldException();

				Aviao castAviao = (Aviao)aviao.getSelectedItem();
				Aeroporto castAeroportoRemetente = (Aeroporto)aeroportoRemetente.getSelectedItem();
				Aeroporto castAeroportoDestino = (Aeroporto)aeroportoDestino.getSelectedItem();
				
				ArrayList<Aeroporto> vetorAux = new ArrayList<Aeroporto>();
				vetorAux.add(castAeroportoRemetente);
				vetorAux.add(castAeroportoDestino);

				Viagem viagem = new Viagem(castAviao, vetorAux, Integer.parseInt(valorPassagem.getText()));
				
				castAeroportoRemetente.addViagem(viagem);
				castAeroportoDestino.addViagem(viagem);
				castAviao.addViagem(viagem);

				arrayViagem.add(viagem);
				limparCampos();
				JOptionPane.showMessageDialog(null, "Avião salvo com sucesso");
			}
			catch (OnlyPositiveNumbersException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				
			} 
			catch (SameAirportException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				
			}
			catch (EmptyFieldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch(NumberFormatException event) {
				JOptionPane.showMessageDialog(null, "O campo de valor da passagem precisa ser composto de numeros apenas", "Aviso", 2);

			}

		});
		tela.add(salvar);
		
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
		vetorAviao[0] = new Aviao();

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

		if(modelAviao != null){
			for(int i = modelAviao.getSize() - 1; i > 0; i--){
				modelAviao.removeElementAt(i);
			}

			for(int i = 1; i < vetorAviao.length; i++){
				modelAviao.addElement(vetorAviao[i]);
			}
		}

		if(modelRemetente != null){
			for(int i = modelRemetente.getSize() - 1; i > 0; i--){
				modelRemetente.removeElementAt(i);
				modelDestino.removeElementAt(i);
			}

			for(int i = 1; i < vetorAeroporto.length; i++){
				modelRemetente.addElement(vetorAeroporto[i]);
				modelDestino.addElement(vetorAeroporto[i]);
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
		aviao.setSelectedIndex(0);
		aeroportoRemetente.setSelectedIndex(0);
		aeroportoDestino.setSelectedIndex(0);
		valorPassagem.setText("");
	}

}
