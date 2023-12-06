package interface_usuario;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import controladoras.ControladoraGerenciarCliente;
import controladoras.ControladoraOrcamentoDeBuffetCompleto;
import excecoes.ExcecaoDDDInvalido;
import negocio.Cliente;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.ParseException;

import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

public class PainelCriarOuAtualizarCliente extends Painel {

	private JPanel painel;
	
	private JLabel labelTituloTela;
	
	private RoundJTextField inputNomeCompleto;					// OBRIGATORIO
	private RoundJFormattedTextField inputCPF;					// OBRIGATORIO
	private RoundJTextField inputRG;							// OPCIONAL
	private RoundJTextField inputEmail;							// OBRIGATORIO
	private RoundJFormattedTextField inputAlternado;			// OBRIGATORIO
	private RoundJFormattedTextField inputCEP;					// OBRIGATORIO
	private RoundJFormattedTextField inputCelular;				// OBRIGATORIO
	private RoundJFormattedTextField inputTelefoneResidencial;	// OPCIONAL
	private RoundJFormattedTextField inputTelefoneComercial;	// OPCIONAL
	
	private RoundJPanel painelErro;
	
	private JPanel painelDelimitadorNomeCompleto;
	private JPanel painelDelimitadorCPF;
	private JPanel painelDelimitadorRG;
	private JPanel painelDelimitadorEmail;
	private JPanel painelDelimitadorCEP;
	private JPanel painelDelimitadorCelular;
	private JPanel painelDelimitadorTelefoneResidencial;
	private JPanel painelDelimitadorTelefoneComercial;
	
	private JLabel labelAlternado;
	private boolean usuarioJaPesquisado;
	private Cliente cliente;
	private JLabel labelNomeCompleto;
	private JLabel labelEmail;
	private JLabel labelCelular;
	private JLabel labelCPF;
	private JLabel labelCEP;
	
	private MaskFormatter mascaraCPF;
	
	public PainelCriarOuAtualizarCliente(MouseListener retornaTelaInicial, MouseListener avancaPagina, MouseListener voltaPagina) {
		
		painel = new JPanel();
		painel.setBackground(getCorDeFundo());
		
		labelTituloTela = new JLabel("Cliente");
		labelTituloTela.setFont(getFonteTitulo());
		
		JPanel painelPaginaAnterior = new JPanel();
		JPanel painelProximaPagina = new JPanel();
		JPanel painelRetornaTudo = new JPanel();
		painelErro = new RoundJPanel(25, 25, 25, 25);
		usuarioJaPesquisado = false;
		cliente = null;
		
		try {
			mascaraCPF = new MaskFormatter("###.###.###-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		painelPaginaAnterior.setBackground(getCorDeFundo());
		painelProximaPagina.setBackground(getCorDeFundo());
		painelRetornaTudo.setBackground(getCorDeFundo());
		painelErro.setBackground(getCorDeJanelaInterna());
		
		JPanel painelDelimitadorCampos = new JPanel();
		painelDelimitadorCampos.setBackground(getCorDeFundo());
		painelDelimitadorCampos.setLayout(new GridLayout(3, 3, 20, 20));
		
		painelDelimitadorNomeCompleto = new JPanel();
		painelDelimitadorNomeCompleto.setBorder(new EmptyBorder(0, 0, 0, 0));
		painelDelimitadorNomeCompleto.setBackground(getCorDeFundo());
		painelDelimitadorCampos.add(painelDelimitadorNomeCompleto);
		painelDelimitadorNomeCompleto.setLayout(new GridLayout(1, 0, 0, 0));
		painelDelimitadorNomeCompleto.setVisible(false);
		
		RoundJPanel painelNomeCompleto = new RoundJPanel(25, 25, 25, 25);
		painelNomeCompleto.setBorder(new EmptyBorder(20, 20, 20, 20));
		painelNomeCompleto.setBackground(getCorDeJanelaInterna());
		painelDelimitadorNomeCompleto.add(painelNomeCompleto);
		
		labelNomeCompleto = new JLabel("Nome completo");
		labelNomeCompleto.setFont(getFonteLabelInterno());
		labelNomeCompleto.setForeground(getCorTexto());
		inputNomeCompleto = new RoundJTextField(25);
		inputNomeCompleto.setColumns(10);
		inputNomeCompleto.setFont(getFonteInputs());
		GroupLayout gl_painelNomeCompleto = new GroupLayout(painelNomeCompleto);
		gl_painelNomeCompleto.setHorizontalGroup(
			gl_painelNomeCompleto.createParallelGroup(Alignment.TRAILING)
				.addComponent(inputNomeCompleto, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
				.addGroup(gl_painelNomeCompleto.createSequentialGroup()
					.addGap(1)
					.addComponent(labelNomeCompleto, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_painelNomeCompleto.setVerticalGroup(
			gl_painelNomeCompleto.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelNomeCompleto.createSequentialGroup()
					.addComponent(labelNomeCompleto, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(inputNomeCompleto, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
		);
		painelNomeCompleto.setLayout(gl_painelNomeCompleto);
		
		painelDelimitadorCPF = new JPanel();
		painelDelimitadorCPF.setBackground(getCorDeFundo());
		painelDelimitadorCampos.add(painelDelimitadorCPF);
		painelDelimitadorCPF.setLayout(new GridLayout(1, 0, 0, 0));
		painelDelimitadorCPF.setVisible(false);
		
		RoundJPanel painelCPF = new RoundJPanel(25, 25, 25, 25);
		painelCPF.setBorder(new EmptyBorder(20, 15, 20, 15));
		painelCPF.setBackground(getCorDeJanelaInterna());
		painelDelimitadorCPF.add(painelCPF);
		
		labelCPF = new JLabel("CPF");
		labelCPF.setFont(getFonteLabelInterno());
		labelCPF.setForeground(getCorTexto());
		
		inputCPF = new RoundJFormattedTextField(25);
		inputCPF.setColumns(10);
		inputCPF.setFont(getFonteInputs());
		
		try {
			MaskFormatter mascara;
			mascara = new MaskFormatter("###.###.###-##");
			mascara.install(inputCPF);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		GroupLayout gl_painelCPF = new GroupLayout(painelCPF);
		gl_painelCPF.setHorizontalGroup(
			gl_painelCPF.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelCPF.createSequentialGroup()
					.addGroup(gl_painelCPF.createParallelGroup(Alignment.LEADING)
						.addComponent(labelCPF, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
						.addComponent(inputCPF, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
					.addGap(0))
		);
		gl_painelCPF.setVerticalGroup(
			gl_painelCPF.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelCPF.createSequentialGroup()
					.addComponent(labelCPF, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(inputCPF, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
		);
		painelCPF.setLayout(gl_painelCPF);
		
		painelDelimitadorRG = new JPanel();
		painelDelimitadorRG.setBackground(getCorDeFundo());
		painelDelimitadorCampos.add(painelDelimitadorRG);
		painelDelimitadorRG.setLayout(new GridLayout(1, 0, 0, 0));
		painelDelimitadorRG.setVisible(false);
		
		RoundJPanel painelRG = new RoundJPanel(25, 25, 25, 25);
		painelRG.setBorder(new EmptyBorder(20, 15, 20, 15));
		painelRG.setBackground(getCorDeJanelaInterna());
		painelDelimitadorRG.add(painelRG);
		
		JLabel labelRG = new JLabel("RG");
		labelRG.setFont(getFonteLabelInterno());
		labelRG.setForeground(getCorTexto());
		
		inputRG = new RoundJTextField(25);
		inputRG.setColumns(10);
		inputRG.setFont(getFonteInputs());
		
		GroupLayout gl_painelRG = new GroupLayout(painelRG);
		gl_painelRG.setHorizontalGroup(
			gl_painelRG.createParallelGroup(Alignment.LEADING)
				.addComponent(labelRG, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
				.addComponent(inputRG, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
		);
		gl_painelRG.setVerticalGroup(
			gl_painelRG.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelRG.createSequentialGroup()
					.addComponent(labelRG, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(inputRG, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
		);
		painelRG.setLayout(gl_painelRG);
		
		
		painelDelimitadorEmail = new JPanel();
		painelDelimitadorEmail.setBackground(getCorDeFundo());
		painelDelimitadorCampos.add(painelDelimitadorEmail);
		painelDelimitadorEmail.setLayout(new GridLayout(1, 0, 0, 0));
		painelDelimitadorEmail.setVisible(false);
		
		RoundJPanel painelEmail = new RoundJPanel(25, 25, 25, 25);
		painelEmail.setBorder(new EmptyBorder(20, 15, 20, 15));
		painelEmail.setBackground(getCorDeJanelaInterna());
		painelDelimitadorEmail.add(painelEmail);
		
		labelEmail = new JLabel("Email");
		labelEmail.setFont(getFonteLabelInterno());
		labelEmail.setForeground(getCorTexto());
		
		inputEmail = new RoundJTextField(25);
		inputEmail.setColumns(10);
		inputEmail.setFont(getFonteInputs());
		
		GroupLayout gl_painelEmail = new GroupLayout(painelEmail);
		gl_painelEmail.setHorizontalGroup(
			gl_painelEmail.createParallelGroup(Alignment.LEADING)
				.addComponent(labelEmail, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
				.addComponent(inputEmail, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
		);
		gl_painelEmail.setVerticalGroup(
			gl_painelEmail.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelEmail.createSequentialGroup()
					.addComponent(labelEmail, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(inputEmail, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
		);
		painelEmail.setLayout(gl_painelEmail);
		
		JPanel painelDelimitadorAlternado = new JPanel();
		painelDelimitadorAlternado.setBackground(getCorDeFundo());
		painelDelimitadorCampos.add(painelDelimitadorAlternado);
		painelDelimitadorAlternado.setLayout(new GridLayout(1, 0, 0, 0));
		
		RoundJPanel painelAlternado = new RoundJPanel(25, 25, 25, 25);
		painelAlternado.setBorder(new EmptyBorder(20, 15, 20, 15));
		painelAlternado.setBackground(getCorDeJanelaInterna());
		painelDelimitadorAlternado.add(painelAlternado);
		
		labelAlternado = new JLabel("CPF");
		labelAlternado.setFont(getFonteLabelInterno());
		labelAlternado.setForeground(getCorTexto());
		
		inputAlternado = new RoundJFormattedTextField(25);
		inputAlternado.setColumns(10);
		inputAlternado.setFont(getFonteInputs());
		
		mascaraCPF.install(inputAlternado);
		
		GroupLayout gl_painelAlternado = new GroupLayout(painelAlternado);
		gl_painelAlternado.setHorizontalGroup(
			gl_painelAlternado.createParallelGroup(Alignment.LEADING)
				.addComponent(labelAlternado, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
				.addComponent(inputAlternado, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
		);
		gl_painelAlternado.setVerticalGroup(
			gl_painelAlternado.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelAlternado.createSequentialGroup()
					.addComponent(labelAlternado, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(inputAlternado, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
		);
		painelAlternado.setLayout(gl_painelAlternado);
		
		painelDelimitadorCEP = new JPanel();
		painelDelimitadorCEP.setBackground(getCorDeFundo());
		painelDelimitadorCampos.add(painelDelimitadorCEP);
		painelDelimitadorCEP.setLayout(new GridLayout(1, 0, 0, 0));
		painelDelimitadorCEP.setVisible(false);
		
		RoundJPanel painelCEP = new RoundJPanel(25, 25, 25, 25);
		painelCEP.setBorder(new EmptyBorder(20, 15, 20, 15));
		painelCEP.setBackground(getCorDeJanelaInterna());
		painelDelimitadorCEP.add(painelCEP);
		
		labelCEP = new JLabel("CEP");
		labelCEP.setFont(getFonteLabelInterno());
		labelCEP.setForeground(getCorTexto());
		
		inputCEP = new RoundJFormattedTextField(25);
		inputCEP.setColumns(10);
		inputCEP.setFont(getFonteInputs());
		
		try {
			MaskFormatter mascara;
			mascara = new MaskFormatter("#####-###");
			mascara.install(inputCEP);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		GroupLayout gl_painelCEP = new GroupLayout(painelCEP);
		gl_painelCEP.setHorizontalGroup(
			gl_painelCEP.createParallelGroup(Alignment.LEADING)
				.addComponent(labelCEP, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
				.addComponent(inputCEP, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
		);
		gl_painelCEP.setVerticalGroup(
			gl_painelCEP.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelCEP.createSequentialGroup()
					.addComponent(labelCEP, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(inputCEP, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
		);
		painelCEP.setLayout(gl_painelCEP);
		
		painelDelimitadorCelular = new JPanel();
		painelDelimitadorCelular.setBackground(getCorDeFundo());
		painelDelimitadorCampos.add(painelDelimitadorCelular);
		painelDelimitadorCelular.setLayout(new GridLayout(1, 0, 0, 0));
		painelDelimitadorCelular.setVisible(false);
		
		RoundJPanel painelCelular = new RoundJPanel(25, 25, 25, 25);
		painelCelular.setBorder(new EmptyBorder(20, 15, 20, 15));
		painelCelular.setBackground(getCorDeJanelaInterna());
		painelDelimitadorCelular.add(painelCelular);
		
		labelCelular = new JLabel("Celular");
		labelCelular.setFont(getFonteLabelInterno());
		labelCelular.setForeground(getCorTexto());
		
		inputCelular = new RoundJFormattedTextField(25);
		inputCelular.setColumns(10);
		inputCelular.setFont(getFonteInputs());
		
		try {
			MaskFormatter mascara;
			mascara = new MaskFormatter("## #####-####");
			mascara.install(inputCelular);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		GroupLayout gl_painelCelular = new GroupLayout(painelCelular);
		gl_painelCelular.setHorizontalGroup(
			gl_painelCelular.createParallelGroup(Alignment.LEADING)
				.addComponent(labelCelular, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
				.addComponent(inputCelular, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
		);
		gl_painelCelular.setVerticalGroup(
			gl_painelCelular.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelCelular.createSequentialGroup()
					.addComponent(labelCelular, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(inputCelular, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
		);
		painelCelular.setLayout(gl_painelCelular);
		
		painelDelimitadorTelefoneResidencial = new JPanel();
		painelDelimitadorTelefoneResidencial.setBackground(getCorDeFundo());
		painelDelimitadorCampos.add(painelDelimitadorTelefoneResidencial);
		painelDelimitadorTelefoneResidencial.setLayout(new GridLayout(1, 0, 0, 0));
		painelDelimitadorTelefoneResidencial.setVisible(false);
		
		RoundJPanel painelTelefoneResidencial = new RoundJPanel(25, 25, 25, 25);
		painelTelefoneResidencial.setBorder(new EmptyBorder(20, 15, 20, 15));
		painelTelefoneResidencial.setBackground(getCorDeJanelaInterna());
		painelDelimitadorTelefoneResidencial.add(painelTelefoneResidencial);
		
		JLabel labelTelefoneResidencial = new JLabel("Telefone residencial");
		labelTelefoneResidencial.setFont(getFonteLabelInterno());
		labelTelefoneResidencial.setForeground(getCorTexto());
		
		inputTelefoneResidencial = new RoundJFormattedTextField(25);
		inputTelefoneResidencial.setColumns(10);
		inputTelefoneResidencial.setFont(getFonteInputs());
		
		try {
			MaskFormatter mascara;
			mascara = new MaskFormatter("## ####-####");
			mascara.install(inputTelefoneResidencial);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		GroupLayout gl_painelTelefoneResidencial = new GroupLayout(painelTelefoneResidencial);
		gl_painelTelefoneResidencial.setHorizontalGroup(
			gl_painelTelefoneResidencial.createParallelGroup(Alignment.LEADING)
				.addComponent(labelTelefoneResidencial, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
				.addComponent(inputTelefoneResidencial, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
		);
		gl_painelTelefoneResidencial.setVerticalGroup(
			gl_painelTelefoneResidencial.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelTelefoneResidencial.createSequentialGroup()
					.addComponent(labelTelefoneResidencial, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(inputTelefoneResidencial, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
		);
		painelTelefoneResidencial.setLayout(gl_painelTelefoneResidencial);
		
		painelDelimitadorTelefoneComercial = new JPanel();
		painelDelimitadorTelefoneComercial.setBackground(getCorDeFundo());
		painelDelimitadorCampos.add(painelDelimitadorTelefoneComercial);
		painelDelimitadorTelefoneComercial.setLayout(new GridLayout(1, 0, 0, 0));
		painelDelimitadorTelefoneComercial.setVisible(false);
		
		RoundJPanel painelTelefoneComercial = new RoundJPanel(25, 25, 25, 25);
		painelTelefoneComercial.setBorder(new EmptyBorder(20, 15, 20, 15));
		painelTelefoneComercial.setBackground(getCorDeJanelaInterna());
		painelDelimitadorTelefoneComercial.add(painelTelefoneComercial);
		
		JLabel labelTelefoneComercial = new JLabel("Telefone comercial");
		labelTelefoneComercial.setFont(getFonteLabelInterno());
		labelTelefoneComercial.setForeground(getCorTexto());
		
		inputTelefoneComercial = new RoundJFormattedTextField(25);
		inputTelefoneComercial.setColumns(10);
		inputTelefoneComercial.setFont(getFonteInputs());
		
		try {
			MaskFormatter mascara;
			mascara = new MaskFormatter("## #####-####");
			mascara.install(inputTelefoneComercial);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		GroupLayout gl_painelTelefoneComercial = new GroupLayout(painelTelefoneComercial);
		gl_painelTelefoneComercial.setHorizontalGroup(
			gl_painelTelefoneComercial.createParallelGroup(Alignment.LEADING)
				.addComponent(labelTelefoneComercial, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
				.addComponent(inputTelefoneComercial, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
		);
		gl_painelTelefoneComercial.setVerticalGroup(
			gl_painelTelefoneComercial.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelTelefoneComercial.createSequentialGroup()
					.addComponent(labelTelefoneComercial, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(inputTelefoneComercial, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
		);
		
		painelTelefoneComercial.setLayout(gl_painelTelefoneComercial);
		painelPaginaAnterior.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel labelPaginaAnterior = new JLabel("");
		labelPaginaAnterior.addMouseListener(voltaPagina);
		
		labelPaginaAnterior.setIcon(getIconeVoltarPagina());
		painelPaginaAnterior.add(labelPaginaAnterior);
		painelProximaPagina.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel labelProximaPagina = new JLabel("");
		
		labelProximaPagina.addMouseListener(avancaPagina);
		labelProximaPagina.setIcon(getIconeProximaPagina());
		painelProximaPagina.add(labelProximaPagina);
		painelRetornaTudo.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel labelRetornaTudo = new JLabel("");
		labelRetornaTudo.addMouseListener(retornaTelaInicial);
		labelRetornaTudo.setIcon(getIconeRetornaTudo());
		painelRetornaTudo.add(labelRetornaTudo);
		
		JLabel iconeErro = new JLabel("");
		iconeErro.setIcon(new ImageIcon("imgs/erroPequeno.png"));
		
		JLabel labelErro = new JLabel("Preencha todos os campos");
		labelErro.setFont(new Font("Arial", Font.PLAIN, 20));
		labelErro.setForeground(getCorTextoErro());
		
		GroupLayout gl_painelErro = new GroupLayout(painelErro);
		gl_painelErro.setHorizontalGroup(
			gl_painelErro.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelErro.createSequentialGroup()
					.addContainerGap()
					.addComponent(iconeErro, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(labelErro, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
					.addGap(14))
		);
		gl_painelErro.setVerticalGroup(
			gl_painelErro.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelErro.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_painelErro.createParallelGroup(Alignment.LEADING)
						.addComponent(labelErro, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
						.addComponent(iconeErro, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		painelErro.setLayout(gl_painelErro);
		GroupLayout gl_painel = new GroupLayout(painel);
		gl_painel.setHorizontalGroup(
			gl_painel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_painel.createSequentialGroup()
					.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_painel.createSequentialGroup()
							.addGap(67)
							.addComponent(labelTituloTela, GroupLayout.PREFERRED_SIZE, 555, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 550, Short.MAX_VALUE)
							.addComponent(painelRetornaTudo, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_painel.createSequentialGroup()
							.addGap(30)
							.addComponent(painelDelimitadorCampos, GroupLayout.PREFERRED_SIZE, 1157, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE))
						.addGroup(gl_painel.createSequentialGroup()
							.addGap(20)
							.addComponent(painelPaginaAnterior, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addGap(328)
							.addComponent(painelErro, GroupLayout.PREFERRED_SIZE, 380, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 382, Short.MAX_VALUE)
							.addComponent(painelProximaPagina, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
					.addGap(20))
		);
		gl_painel.setVerticalGroup(
			gl_painel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painel.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING)
						.addComponent(labelTituloTela, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(painelRetornaTudo, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(painelDelimitadorCampos, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
					.addGap(47)
					.addGroup(gl_painel.createParallelGroup(Alignment.LEADING)
						.addComponent(painelProximaPagina, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addComponent(painelPaginaAnterior, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addComponent(painelErro, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
		);
		painel.setLayout(gl_painel);
		painelErro.setVisible(false);
	}

	private void verificarSeTemCamposVazios() {
		painelErro.setVisible(true);
		
		if (inputNomeCompleto.getText().isEmpty() || inputNomeCompleto.getText().isBlank()) labelNomeCompleto.setForeground(getCorTextoErro());
		else labelNomeCompleto.setForeground(getCorTexto());
		
		if (inputCPF.getText().matches("\\d{3}.\\d{3}.\\d{3}-\\d{2}") == false) labelCPF.setForeground(getCorTextoErro());
		else labelCPF.setForeground(getCorTexto());
		
		if (inputEmail.getText().isEmpty() || inputEmail.getText().isBlank()) labelEmail.setForeground(getCorTextoErro());
		else labelEmail.setForeground(getCorTexto());
		
		if (inputAlternado.getText().isEmpty() || inputAlternado.getText().isBlank()) labelAlternado.setForeground(getCorTextoErro());
		else labelAlternado.setForeground(getCorTexto());
	
		if (inputCEP.getText().equals("     -   ")) labelCEP.setForeground(getCorTextoErro());
		else labelCEP.setForeground(getCorTexto());
		
		if (inputCelular.getText().equals("        -    ")) labelCelular.setForeground(getCorTextoErro());
		else labelCelular.setForeground(getCorTexto());
		
	}

	public PopUpErroGenerico verificarPreenchimento(ActionListener listenerVoltar) {
		
		if (ControladoraOrcamentoDeBuffetCompleto.verificarNomeCompleto(inputNomeCompleto.getText()) == false) {
			verificarSeTemCamposVazios();
			return new PopUpErroGenerico(listenerVoltar, "Nome informado está inválido", "Para continuar, forneça um nome válido", "Preencher Nome");
		}
		
		if (ControladoraOrcamentoDeBuffetCompleto.verificarCPF(inputCPF.getText()) == false) {
			verificarSeTemCamposVazios();
			return new PopUpErroGenerico(listenerVoltar, "CPF informado está inválido", "Para continuar, forneça um CPF válido", "Preencher CPF");
		}

		if (ControladoraOrcamentoDeBuffetCompleto.verificarEmail(inputEmail.getText()) == false) {
			verificarSeTemCamposVazios();
			return new PopUpErroGenerico(listenerVoltar, "Email está inválido", "Para continuar, forneça um Email válido", "Preencher Email");
		}

		if (inputAlternado.getText().isBlank() || inputAlternado.getText().isEmpty()) {
			verificarSeTemCamposVazios();
			return new PopUpErroGenerico(listenerVoltar, "Endereço não está preenchido", "Para continuar, preencha o campo Endereço", "Preencher Endereço");
		}
		
		if (ControladoraOrcamentoDeBuffetCompleto.verificarCEP(inputCEP.getText()) == false) {
			verificarSeTemCamposVazios();
			return new PopUpErroGenerico(listenerVoltar, "CEP está inválido", "Para continuar, forneça um CEP válido", "Preencher CEP");
		}
		
		try {
			if (ControladoraOrcamentoDeBuffetCompleto.verificarCelular(inputCelular.getText()) == false) {
				verificarSeTemCamposVazios();
				return new PopUpErroGenerico(listenerVoltar, "Celular está inválido", "Para continuar, forneça um Celular válido", "Preencher Celular");
			}					
		} catch (ExcecaoDDDInvalido eDDD) {
			verificarSeTemCamposVazios();
			return new PopUpErroGenerico(listenerVoltar, "DDD informado está inválido", "Para continuar, forneça um DDD válido", "Preencher DDD");
		}
		
		return null;
	}

	public PopUpErroGenerico pesquisarCliente(ActionListener listenerVoltar) {
		PopUpErroGenerico popUp = null;

		if (ControladoraOrcamentoDeBuffetCompleto.verificarCPF(inputAlternado.getText())) {
			usuarioJaPesquisado = true;
			
			cliente = ControladoraGerenciarCliente.getCliente(inputAlternado.getText());
			mascaraCPF.uninstall();

			if (cliente == null) { // Cliente não cadastrado
				popUp = new PopUpErroGenerico(listenerVoltar, "Cliente não cadastrado", "Para continuar, efetue o cadastro do cliente", "Cadastrar Cliente");
				inputCPF.setText(inputAlternado.getText());
				inputAlternado.setText("");
				labelTituloTela.setText("Cadastrar Cliente");
				
			} else { // Cliente cadastrado
				JOptionPane.showMessageDialog(null, "Cliente já cadastrado, carregando dados...");
				labelTituloTela.setText("Atualizar Cliente");
				
				inputNomeCompleto.setText(cliente.getNome());
				inputCPF.setText(cliente.getCpf());
				inputRG.setText(cliente.getRg() == null ? "" : cliente.getRg());
				inputEmail.setText(cliente.getEmail());
				inputAlternado.setText(cliente.getEndereco());
				inputCEP.setText(cliente.getCep());
				inputCelular.setText(cliente.getCelular());
				inputTelefoneResidencial.setText(cliente.getTelefoneResidencial() == null ? "" : cliente.getTelefoneResidencial());
				inputTelefoneComercial.setText(cliente.getTelefoneComercial() == null ? "" : cliente.getTelefoneComercial());
			}
			
			labelAlternado.setText("Endereço");
			painelDelimitadorNomeCompleto.setVisible(true);
			painelDelimitadorCPF.setVisible(true);
			painelDelimitadorRG.setVisible(true);
			painelDelimitadorEmail.setVisible(true);
			painelDelimitadorCEP.setVisible(true);
			painelDelimitadorCelular.setVisible(true);
			painelDelimitadorTelefoneResidencial.setVisible(true);
			painelDelimitadorTelefoneComercial.setVisible(true);
			
			painel.repaint();
		} else {
			popUp = new PopUpErroGenerico(listenerVoltar, "CPF informado está inválido", "Para continuar, forneça um CPF válido", "Preencher CPF");
		}
		
		return popUp;
	}

	public boolean getUsuarioJaPesquisado() {
		return usuarioJaPesquisado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getAlternado() {
		return inputAlternado.getText();
	}

	public String getCPF() {
		String cpf = inputCPF.getText();
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		
		return cpf;
	}

	public String getNome() {
		return inputNomeCompleto.getText();
	}

	public String getRG() {
		String rg = inputRG.getText();
		return rg;
	}
	
	public String getCEP() {
		String cep = inputCEP.getText();
		return cep;
	}

	public String getEmail() {
		String email = inputEmail.getText();
		return email;
	}
	
	public String getCelular() {
		String celular = inputCelular.getText();
		return celular;
	}
	
	public String getTelefoneResidencial() {
		String telefoneResidencial = inputTelefoneResidencial.getText();
		return telefoneResidencial;
	}

	public String getTelefoneComercial() {
		String telefoneComercial = inputTelefoneComercial.getText();
		return telefoneComercial;
	}
	
	@Override
	public JPanel getPainel() {
		return painel;
	}

	@Override
	public void limparCampos() {
		labelTituloTela.setText("Cliente");
		labelAlternado.setText("CPF");
		usuarioJaPesquisado = false;
		cliente = null;
		
		labelNomeCompleto.setForeground(getCorTexto());
		labelCPF.setForeground(getCorTexto());
		labelEmail.setForeground(getCorTexto());
		labelAlternado.setForeground(getCorTexto());
		labelCEP.setForeground(getCorTexto());
		labelCelular.setForeground(getCorTexto());
		
		inputNomeCompleto.setText("");
		inputCPF.setText("");
		inputRG.setText("");
		inputEmail.setText("");
		inputAlternado.setText("");
		inputCEP.setText("");
		inputCelular.setText("");
		inputTelefoneResidencial.setText("");
		inputTelefoneComercial.setText("");
		
		painelDelimitadorNomeCompleto.setVisible(false);
		painelDelimitadorCPF.setVisible(false);
		painelDelimitadorRG.setVisible(false);
		painelDelimitadorEmail.setVisible(false);
		painelDelimitadorCEP.setVisible(false);
		painelDelimitadorCelular.setVisible(false);
		painelDelimitadorTelefoneResidencial.setVisible(false);
		painelDelimitadorTelefoneComercial.setVisible(false);
		
		mascaraCPF.install(inputAlternado);

		painelErro.setVisible(false);
		
	}
}
