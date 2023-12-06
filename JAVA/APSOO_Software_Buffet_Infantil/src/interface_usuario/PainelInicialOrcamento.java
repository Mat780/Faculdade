package interface_usuario;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.ParseException;

import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controladoras.ControladoraOrcamentoDeBuffetCompleto;
import controladoras.ControladoraOrcamentoLocacaoDeEspaco;
import excecoes.ExcecaoDataEmUmFuturoDistante;
import excecoes.ExcecaoDiaInvalido;
import excecoes.ExcecaoMesInvalido;
import excecoes.ExcecaoNaoPreenchido;
import excecoes.ExcecaoOrcamentoParaHoje;
import negocio.Data;
import utilitaria.Utilitaria;

import javax.swing.JFormattedTextField;

public class PainelInicialOrcamento extends Painel {

	private JPanel painel;
	
	private RoundJTextField inputNumeroDeConvidados;
	private RoundJTextField inputNumeroDeColaboradores;
	private RoundJFormattedTextField inputData;
	private RoundJFormattedTextField inputHoraDeInicio;
	
	private JLabel labelRetornarTudo;
	private JLabel labelAvancar;
	
	public PainelInicialOrcamento(MouseListener retornaTelaInicial, MouseListener proximaPagina) {
		super();
		
		painel = new JPanel();
		painel.setBackground(getCorDeFundo());
		
		JLabel labelTitulo = new JLabel("Informações do evento");
		labelTitulo.setBounds(40, 34, 571, 85);
		labelTitulo.setForeground(getCorTitulo());
		labelTitulo.setFont(getFonteTitulo());
		
		JPanel painelDivisor = new JPanel();
		painelDivisor.setBounds(40, 125, 1180, 344);
		painelDivisor.setBackground(getCorDeFundo());
		
		JPanel painelAvancar = new JPanel();
		painelAvancar.setBounds(1177, 577, 70, 70);
		painelAvancar.setBackground(getCorDeFundo());
		
		JPanel painelVoltarTudo = new JPanel();
		painelVoltarTudo.setBounds(1177, 20, 70, 70);
		painelVoltarTudo.setBackground(getCorDeFundo());
		painelVoltarTudo.setLayout(new GridLayout(0, 1, 0, 0));
		
		setLabelRetornarTudo(retornaTelaInicial);
		painelVoltarTudo.add(labelRetornarTudo);
		painelAvancar.setLayout(new GridLayout(0, 1, 0, 0));
		
		setLabelAvancar(proximaPagina);
		painelAvancar.add(labelAvancar);
		painelDivisor.setLayout(new GridLayout(2, 2, 0, 0));
		
		JPanel painelNumeroDeConvidados = new JPanel();
		painelNumeroDeConvidados.setBackground(getCorDeFundo());
		painelDivisor.add(painelNumeroDeConvidados);
		
		RoundJPanel painelInternoNumeroDeConvidados = new RoundJPanel(25, 25, 25, 25);
		painelInternoNumeroDeConvidados.setBackground(getCorDeJanelaInterna());
		painelInternoNumeroDeConvidados.setBorder(new EmptyBorder(30, 20, 30, 20));
		painelInternoNumeroDeConvidados.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel labelNumeroDeConvidados = new JLabel(" Nº de convidados");
		labelNumeroDeConvidados.setForeground(getCorTexto());
		labelNumeroDeConvidados.setFont(getFonteLabelInterno());
		painelInternoNumeroDeConvidados.add(labelNumeroDeConvidados);
		
		setInputNumeroDeConvidados();
		painelInternoNumeroDeConvidados.add(inputNumeroDeConvidados);
		GroupLayout gl_painelNumeroDeConvidados = new GroupLayout(painelNumeroDeConvidados);
		gl_painelNumeroDeConvidados.setHorizontalGroup(
			gl_painelNumeroDeConvidados.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_painelNumeroDeConvidados.createSequentialGroup()
					.addGap(15)
					.addComponent(painelInternoNumeroDeConvidados, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
					.addGap(30))
		);
		gl_painelNumeroDeConvidados.setVerticalGroup(
			gl_painelNumeroDeConvidados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelNumeroDeConvidados.createSequentialGroup()
					.addGap(12)
					.addComponent(painelInternoNumeroDeConvidados, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
					.addGap(12))
		);
		painelNumeroDeConvidados.setLayout(gl_painelNumeroDeConvidados);
		
		JPanel painelNumeroDeColaboradores = new JPanel();
		painelNumeroDeColaboradores.setBackground(getCorDeFundo());
		painelDivisor.add(painelNumeroDeColaboradores);
		
		RoundJPanel painelInternoNumeroDeColaboradores = new RoundJPanel(25, 25, 25, 25);
		painelInternoNumeroDeColaboradores.setBackground(getCorDeJanelaInterna());
		painelInternoNumeroDeColaboradores.setBorder(new EmptyBorder(30, 20, 30, 20));
		painelInternoNumeroDeColaboradores.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel labelNumeroDeColaboradores = new JLabel(" Nº de colaboradores");
		labelNumeroDeColaboradores.setForeground(getCorTexto());
		labelNumeroDeColaboradores.setFont(getFonteLabelInterno());
		painelInternoNumeroDeColaboradores.add(labelNumeroDeColaboradores);
		
		setInputNumeroDeColaboradores();
		painelInternoNumeroDeColaboradores.add(inputNumeroDeColaboradores);
		GroupLayout gl_painelNumeroDeColaboradores = new GroupLayout(painelNumeroDeColaboradores);
		gl_painelNumeroDeColaboradores.setHorizontalGroup(
			gl_painelNumeroDeColaboradores.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelNumeroDeColaboradores.createSequentialGroup()
					.addGap(30)
					.addComponent(painelInternoNumeroDeColaboradores, GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
					.addGap(15))
		);
		gl_painelNumeroDeColaboradores.setVerticalGroup(
			gl_painelNumeroDeColaboradores.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelNumeroDeColaboradores.createSequentialGroup()
					.addGap(12)
					.addComponent(painelInternoNumeroDeColaboradores, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
					.addGap(12))
		);
		painelNumeroDeColaboradores.setLayout(gl_painelNumeroDeColaboradores);
		
		JPanel painelData = new JPanel();
		painelData.setBackground(getCorDeFundo());
		painelDivisor.add(painelData);
		
		RoundJPanel painelInternoData = new RoundJPanel(25, 25, 25, 25);
		painelInternoData.setBackground(getCorDeJanelaInterna());
		painelInternoData.setBorder(new EmptyBorder(30, 20, 30, 20));
		painelInternoData.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel labelData = new JLabel(" Data");
		labelData.setForeground(getCorTexto());
		labelData.setFont(getFonteLabelInterno());
		painelInternoData.add(labelData);
		
		setInputData();
		painelInternoData.add(inputData);
		GroupLayout gl_painelData = new GroupLayout(painelData);
		gl_painelData.setHorizontalGroup(
			gl_painelData.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelData.createSequentialGroup()
					.addGap(15)
					.addComponent(painelInternoData, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
					.addGap(30))
		);
		gl_painelData.setVerticalGroup(
			gl_painelData.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelData.createSequentialGroup()
					.addGap(12)
					.addComponent(painelInternoData, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
					.addGap(12))
		);
		painelData.setLayout(gl_painelData);
		
		JPanel painelHoraDeInicio = new JPanel();
		painelHoraDeInicio.setBackground(getCorDeFundo());
		painelDivisor.add(painelHoraDeInicio);
		
		RoundJPanel painelInternoHoraDeInicio = new RoundJPanel(25, 25, 25, 25);
		painelInternoHoraDeInicio.setBackground(getCorDeJanelaInterna());
		painelInternoHoraDeInicio.setBorder(new EmptyBorder(30, 20, 30, 20));
		painelInternoHoraDeInicio.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel labelHoraDeInicio = new JLabel(" Hora de início do evento");
		labelHoraDeInicio.setForeground(getCorTexto());
		labelHoraDeInicio.setFont(getFonteLabelInterno());
		painelInternoHoraDeInicio.add(labelHoraDeInicio);
		
		setInputHoraDeInicio();
		painelInternoHoraDeInicio.add(inputHoraDeInicio);
		GroupLayout gl_painelHoraDeInicio = new GroupLayout(painelHoraDeInicio);
		gl_painelHoraDeInicio.setHorizontalGroup(
			gl_painelHoraDeInicio.createParallelGroup(Alignment.LEADING)
				.addGap(0, 600, Short.MAX_VALUE)
				.addGroup(gl_painelHoraDeInicio.createSequentialGroup()
					.addGap(30)
					.addComponent(painelInternoHoraDeInicio, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
					.addGap(15))
		);
		gl_painelHoraDeInicio.setVerticalGroup(
			gl_painelHoraDeInicio.createParallelGroup(Alignment.LEADING)
				.addGap(0, 172, Short.MAX_VALUE)
				.addGroup(gl_painelHoraDeInicio.createSequentialGroup()
					.addGap(12)
					.addComponent(painelInternoHoraDeInicio, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
					.addGap(12))
		);
		painelHoraDeInicio.setLayout(gl_painelHoraDeInicio);
		painel.setLayout(null);
		painel.add(labelTitulo);
		painel.add(painelVoltarTudo);
		painel.add(painelDivisor);
		painel.add(painelAvancar);
		
	}
	
	private void setInputNumeroDeConvidados() {
		inputNumeroDeConvidados = new RoundJTextField(35);
		inputNumeroDeConvidados.setFont(getFonteInputs());

	}
	
	private void setInputNumeroDeColaboradores() {
		inputNumeroDeColaboradores = new RoundJTextField(35);
		inputNumeroDeColaboradores.setFont(getFonteInputs());

	}
	
	private void setInputData() {
		inputData = new RoundJFormattedTextField(35);
		inputData.setFocusLostBehavior(JFormattedTextField.COMMIT);
		inputData.setFont(getFonteInputs());

		try {
			MaskFormatter mascara;
			mascara = new MaskFormatter("##/##/####");
			mascara.install(inputData);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	private void setInputHoraDeInicio() {
		inputHoraDeInicio = new RoundJFormattedTextField(35);
		inputHoraDeInicio.setFocusLostBehavior(JFormattedTextField.COMMIT);
		inputHoraDeInicio.setFont(getFonteInputs());

		try {
			MaskFormatter mascara;
			mascara = new MaskFormatter("##:##");
			mascara.install(inputHoraDeInicio);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void setLabelRetornarTudo(MouseListener listenerRetornaTudo) {
		labelRetornarTudo = new JLabel("");
		labelRetornarTudo.setIcon(getIconeRetornaTudo());
		labelRetornarTudo.addMouseListener(listenerRetornaTudo);
	}
	
	private void setLabelAvancar(MouseListener proximaPagina) {
		labelAvancar = new JLabel("");
		labelAvancar.setIcon(getIconeProximaPagina());
		labelAvancar.addMouseListener(proximaPagina);
	}
	
	public int getInputNumeroDeConvidados(){
		
		if (inputNumeroDeConvidados.getText().isBlank() || inputNumeroDeConvidados.getText().isEmpty()) {
			return -1;

		} else if(inputNumeroDeConvidados.getText().strip().matches("\\d+") == false || Utilitaria.verificarNumeroConvidados(Integer.parseInt(inputNumeroDeConvidados.getText())) == false) {
			return -2;
		}

		return Integer.parseInt(inputNumeroDeConvidados.getText().strip());
	}
	public int getInputNumeroDeColaboradores() {
		if (inputNumeroDeColaboradores.getText().isBlank() == false && inputNumeroDeColaboradores.getText().isEmpty() == false && inputNumeroDeColaboradores.getText().strip().matches("\\d+") == false) {
			return -1;
		}

		return Integer.parseInt(inputNumeroDeColaboradores.getText().strip());
	}
	public Data getData() throws ExcecaoNaoPreenchido, ExcecaoDiaInvalido, ExcecaoMesInvalido{
		
		String[] aux = inputData.getText().split("/");

		if (aux[0].isEmpty() || aux[1].isEmpty() || aux[2].isEmpty() || aux[0].isBlank() || aux[1].isBlank() || aux[2].isBlank()) { 
			return null;
		}
		
		return new Data(aux[0].strip(), aux[1].strip(), aux[2].strip());
		
	}

	public String getInputHoraDeInicio(){

		if (inputHoraDeInicio.getText().matches("\\d{2}:\\d{2}") == false) {
			return null;
		}

		return inputHoraDeInicio.getText();
	}

	public PopUpErroGenerico verificarPreenchimento(ActionListener listenerVoltar) {

		if (getInputNumeroDeConvidados() == -1) {
			PopUpErroGenerico popUp = new PopUpErroGenerico(listenerVoltar, "Numero de convidados não está preenchido", "Para continuar, preencha o campo número de convidados", "Preencher Número de Convidados");

			return popUp;

		} else if (getInputNumeroDeConvidados() == -2) {
			PopUpErroGenerico popUp = new PopUpErroGenerico(listenerVoltar, "Número de convidados inválido", "Para continuar, insira entre 50 e 180 convidados", "Mudar Número de Convidados");

			return popUp;
		}
		
		Data data;

		try {
			// Caso ele consiga criar a data, então ele passa no teste, caso contrário é invalido
			data = getData();
			
			if (data == null) {
				return new PopUpErroGenerico(listenerVoltar, "Data não está preenchida", "Para continuar, preencha o campo data", "Preencher Data");
			}

			if (data.verificarSeDataEstaNoFuturo() == false) {
				return new PopUpErroGenerico(listenerVoltar, "Data está no passado", "Para continuar, insira uma data no futuro", "Alterar data");
			}
			
			if (ControladoraOrcamentoDeBuffetCompleto.verificarData(data) == false) {
				return new PopUpErroGenerico(listenerVoltar, "Festa coincide com outra festa agendada", "Para continuar, mude a data da festa", "Mudar data");

			}

			if (ControladoraOrcamentoLocacaoDeEspaco.verificarData(data) == false) {
				return new PopUpErroGenerico(listenerVoltar, "Festa coincide com outra festa agendada", "Para continuar, mude a data da festa", "Mudar data");
			}
			
		} catch (ExcecaoNaoPreenchido | ExcecaoDiaInvalido | ExcecaoMesInvalido | NumberFormatException e) {
			return new PopUpErroGenerico(listenerVoltar, "Data Inválida", "Para continuar, insira uma data válida", "Mudar Data");
			
		} catch (ExcecaoDataEmUmFuturoDistante e) {
			return new PopUpErroGenerico(listenerVoltar,"Intervalo muito grande de data", "Para continuar, mude  a data da sua festa", "Mudar Data");
			
		} catch (ExcecaoOrcamentoParaHoje e) {
			return new PopUpErroGenerico(listenerVoltar, "A festa não pode ser realizada hoje", "Para continuar, mude a data da sua festa", "Mudar Data");
		}

		if (getInputHoraDeInicio() == null) {
			PopUpErroGenerico popUp = new PopUpErroGenerico(listenerVoltar, "Hora de inicio do evento não está preenchida", "Para continuar, preencha o campo hora de inicio do evento", "Preencher Hora de Inicio");

			return popUp;
			
		} else if (Utilitaria.verificarValidezHorario(getInputHoraDeInicio()) == false) {
			PopUpErroGenerico popUp = new PopUpErroGenerico(listenerVoltar, "Hora de Inicio do Evento está Inválida", "Para continuar, insira uma hora de inicio válida", "Mudar Hora de Início");

			return popUp;
		}
		
		if (getInputNumeroDeColaboradores() == -1) {
			PopUpErroGenerico popUp = new PopUpErroGenerico(listenerVoltar, "Número de Colaboradores Inválido", "Para continuar, insira um número de colaboradores válido", "Mudar número de colaboradores");

			return popUp;
		}
		
		return null;
		
	
	}

	public void mudarTipoOrcamento(boolean eBuffetCompleto) {
		if (eBuffetCompleto) {
			inputNumeroDeColaboradores.setEnabled(true);
			inputNumeroDeColaboradores.setText("");

		} else {
			inputNumeroDeColaboradores.setEnabled(false);
			inputNumeroDeColaboradores.setText("3");
		}
	}

	@Override
	public JPanel getPainel() {
		return painel;
	}
	
	@Override
	public void limparCampos() {
		inputNumeroDeConvidados.setText("");
		inputNumeroDeColaboradores.setText("");
		inputData.setText("");
		inputHoraDeInicio.setText("");
		painel.repaint();
	}
	
}
