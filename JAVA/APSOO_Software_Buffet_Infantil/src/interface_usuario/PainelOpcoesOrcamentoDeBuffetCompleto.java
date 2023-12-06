package interface_usuario;

import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import negocio.Bolo;
import negocio.Doce;
import negocio.Salgado;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JRadioButton;

public class PainelOpcoesOrcamentoDeBuffetCompleto extends Painel {
	
	private JPanel painel;
	
	private JRadioButton botaoSalgadoCoxinhaDeFrango;
	private JRadioButton botaoSalgadoCoxinhaDeFrangoComCatupiry;
	private JRadioButton botaoSalgadoRisolesDeCarne;
	private JRadioButton botaoSalgadoQuibe;
	private JRadioButton botaoSalgadoEsfihaDeCarne;
	private JRadioButton botaoSalgadoEmpadinhaDePalmito;
	private JRadioButton botaoSalgadoEnroladinhoDeSalsicha;
	private JRadioButton botaoSalgadoBolinhaDeQueijo;
	private JRadioButton botaoSalgadoBolinhaDeQueijoComMilho;
	private JRadioButton botaoSalgadoRisolesDeFrango;
	private JRadioButton botaoSalgadoRisolesDePresuntoQueijo;
	private JRadioButton botaoSalgadoEnroladinhoPresuntoQueijo;
	
	private boolean[] salgadosSelecionados;
	private ArrayList<Salgado> arraySalgados;
	
	private JRadioButton botaoDoceBrigadeiro;
	private JRadioButton botaoDoceBrigadeiroBranco;
	private JRadioButton botaoDoceBrigadeiroColorido;
	private JRadioButton botaoDoceBeijinhoDeCoco;
	private JRadioButton botaoDoceCasadinho;
	private JRadioButton botaoDoceCajuzinho;
	private boolean[] docesSelecionados;
	private ArrayList<Doce> arrayDoces;
	
	private JRadioButton botaoBoloBrigadeiro;
	private JRadioButton botaoBoloPrestigio;
	private JRadioButton botaoBoloAbacaxi;
	private JRadioButton botaoBoloPessego;
	private JRadioButton botaoBoloDoisAmores;
	private JRadioButton botaoBoloViuvaNegra;
	private Bolo boloSelecionado;
	private ArrayList<Bolo> arrayBolo;
	
	private JRadioButton botaoComCerveja;
	private JRadioButton botaoSemCerveja;
	private boolean teraCerveja;
	
	public PainelOpcoesOrcamentoDeBuffetCompleto(MouseListener retornaTelaInicial, 
			MouseListener proximaPagina, MouseListener voltaPagina,
			ArrayList<Salgado> salgados, ArrayList<Doce> doces, ArrayList<Bolo> bolos) {
		super();
		
		painel = new JPanel();
		painel.setBackground(getCorDeFundo());
		
		salgadosSelecionados = new boolean[12];
		docesSelecionados = new boolean[6];
		boloSelecionado = null;
		teraCerveja = false;
		
		arraySalgados = salgados;
		arrayDoces = doces;
		arrayBolo = bolos;
		
		Font fonteRadioButton = new Font("Arial", Font.BOLD, 14);
		Font fonteRadioButtonSalgados = new Font("Arial", Font.BOLD, 13);
		
		JLabel labelTitulo = new JLabel("Opções de buffet completo");
		labelTitulo.setBounds(44, 30, 494, 58);
		labelTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		labelTitulo.setFont(getFonteTitulo());
		
		RoundJPanel painelDelimitadorSalgado = new RoundJPanel(25, 25, 25, 25);
		painelDelimitadorSalgado.setBounds(40, 109, 1191, 144);
		painelDelimitadorSalgado.setBorder(new EmptyBorder(10, 10, 10, 10));
		painelDelimitadorSalgado.setBackground(getCorDeJanelaInterna());
		
		JPanel painelDelimitadorDoceBoloCerveja = new JPanel();
		painelDelimitadorDoceBoloCerveja.setBounds(40, 268, 1191, 314);
		painelDelimitadorDoceBoloCerveja.setBackground(getCorDeFundo());
		
		JPanel painelVoltaPagina = new JPanel();
		painelVoltaPagina.setBackground(getCorDeFundo());
		painelVoltaPagina.setBounds(10, 600, 70, 70);
		
		JPanel painelAvancaPagina = new JPanel();
		painelAvancaPagina.setBackground(getCorDeFundo());
		painelAvancaPagina.setBounds(1186, 600, 70, 70);
		painelAvancaPagina.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel painelRetornaTudo = new JPanel();
		painelRetornaTudo.setBackground(getCorDeFundo());
		painelRetornaTudo.setBounds(1186, 18, 70, 70);
		painelVoltaPagina.setLayout(new GridLayout(1, 0, 0, 0));
		painelDelimitadorDoceBoloCerveja.setLayout(new GridLayout(3, 1, 0, 20));
		
		RoundJPanel painelDelimitadorDoce = new RoundJPanel(25, 25, 25, 25);
		painelDelimitadorDoce.setBorder(new EmptyBorder(10, 10, 5, 10));
		painelDelimitadorDoce.setBackground(getCorDeJanelaInterna());
		painelDelimitadorDoceBoloCerveja.add(painelDelimitadorDoce);
		
		JLabel labelOpcoesDoce = new JLabel("Opções de doce");
		labelOpcoesDoce.setForeground(getCorTexto());
		labelOpcoesDoce.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JPanel painelBotoesDoce = new JPanel();
		painelBotoesDoce.setBackground(getCorDeJanelaInterna());
		GroupLayout gl_painelDelimitadorDoce = new GroupLayout(painelDelimitadorDoce);
		gl_painelDelimitadorDoce.setHorizontalGroup(
			gl_painelDelimitadorDoce.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitadorDoce.createSequentialGroup()
					.addGroup(gl_painelDelimitadorDoce.createParallelGroup(Alignment.LEADING)
						.addComponent(labelOpcoesDoce, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_painelDelimitadorDoce.createSequentialGroup()
							.addGap(1)
							.addComponent(painelBotoesDoce, GroupLayout.DEFAULT_SIZE, 1169, Short.MAX_VALUE)))
					.addGap(1))
		);
		gl_painelDelimitadorDoce.setVerticalGroup(
			gl_painelDelimitadorDoce.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitadorDoce.createSequentialGroup()
					.addComponent(labelOpcoesDoce, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
					.addGap(1)
					.addComponent(painelBotoesDoce, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
					.addGap(1))
		);
		
		botaoDoceBrigadeiro = new JRadioButton("Brigadeiro");
		botaoDoceBrigadeiro.setFont(fonteRadioButton);
		botaoDoceBrigadeiro.setForeground(getCorTexto());
		botaoDoceBrigadeiro.setBackground(getCorDeJanelaInterna());
		botaoDoceBrigadeiro.setFocusable(false);
		botaoDoceBrigadeiro.addActionListener(new ListenerDoce());
		
		botaoDoceBrigadeiroBranco = new JRadioButton("Brigadeiro branco");
		botaoDoceBrigadeiroBranco.setFont(fonteRadioButton);
		botaoDoceBrigadeiroBranco.setForeground(getCorTexto());
		botaoDoceBrigadeiroBranco.setBackground(getCorDeJanelaInterna());
		botaoDoceBrigadeiroBranco.setFocusable(false);
		botaoDoceBrigadeiroBranco.addActionListener(new ListenerDoce());
		
		botaoDoceBrigadeiroColorido = new JRadioButton("Brigadeiro colorido");
		botaoDoceBrigadeiroColorido.setFont(fonteRadioButton);
		botaoDoceBrigadeiroColorido.setForeground(getCorTexto());
		botaoDoceBrigadeiroColorido.setBackground(getCorDeJanelaInterna());
		botaoDoceBrigadeiroColorido.setFocusable(false);
		botaoDoceBrigadeiroColorido.addActionListener(new ListenerDoce());
		
		botaoDoceBeijinhoDeCoco = new JRadioButton("Beijinho de coco");
		botaoDoceBeijinhoDeCoco.setFont(fonteRadioButton);
		botaoDoceBeijinhoDeCoco.setForeground(getCorTexto());
		botaoDoceBeijinhoDeCoco.setBackground(getCorDeJanelaInterna());
		botaoDoceBeijinhoDeCoco.setFocusable(false);
		botaoDoceBeijinhoDeCoco.addActionListener(new ListenerDoce());
		
		botaoDoceCasadinho = new JRadioButton("Casadinho");
		botaoDoceCasadinho.setFont(fonteRadioButton);
		botaoDoceCasadinho.setForeground(getCorTexto());
		botaoDoceCasadinho.setBackground(getCorDeJanelaInterna());
		botaoDoceCasadinho.setFocusable(false);
		botaoDoceCasadinho.addActionListener(new ListenerDoce());
		
		botaoDoceCajuzinho = new JRadioButton("Cajuzinho");
		botaoDoceCajuzinho.setFont(fonteRadioButton);
		botaoDoceCajuzinho.setForeground(getCorTexto());
		botaoDoceCajuzinho.setBackground(getCorDeJanelaInterna());
		botaoDoceCajuzinho.setFocusable(false);
		botaoDoceCajuzinho.addActionListener(new ListenerDoce());
		
		GroupLayout gl_painelBotoesDoce = new GroupLayout(painelBotoesDoce);
		gl_painelBotoesDoce.setHorizontalGroup(
			gl_painelBotoesDoce.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelBotoesDoce.createSequentialGroup()
					.addGap(1)
					.addComponent(botaoDoceBrigadeiro, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(botaoDoceBrigadeiroBranco, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(botaoDoceBrigadeiroColorido, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(botaoDoceBeijinhoDeCoco, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(botaoDoceCasadinho, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(botaoDoceCajuzinho, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
					.addGap(347))
		);
		gl_painelBotoesDoce.setVerticalGroup(
			gl_painelBotoesDoce.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painelBotoesDoce.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_painelBotoesDoce.createParallelGroup(Alignment.LEADING)
						.addComponent(botaoDoceBeijinhoDeCoco, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(botaoDoceCajuzinho, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(botaoDoceCasadinho, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(botaoDoceBrigadeiroColorido, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(botaoDoceBrigadeiroBranco, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(botaoDoceBrigadeiro, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(10))
		);
		painelBotoesDoce.setLayout(gl_painelBotoesDoce);
		painelDelimitadorDoce.setLayout(gl_painelDelimitadorDoce);
		
		RoundJPanel painelDelimitadorBolo = new RoundJPanel(25, 25, 25, 25);
		painelDelimitadorBolo.setBorder(new EmptyBorder(15, 10, 5, 10));
		painelDelimitadorBolo.setBackground(getCorDeJanelaInterna());
		painelDelimitadorDoceBoloCerveja.add(painelDelimitadorBolo);
		
		JLabel labelOpcoesBolo = new JLabel("Opções de bolo");
		labelOpcoesBolo.setForeground(getCorTexto());
		labelOpcoesBolo.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JPanel painelBotoesBolo = new JPanel();
		painelBotoesBolo.setBackground(getCorDeJanelaInterna());
		GroupLayout gl_painelDelimitadorBolo = new GroupLayout(painelDelimitadorBolo);
		gl_painelDelimitadorBolo.setHorizontalGroup(
			gl_painelDelimitadorBolo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitadorBolo.createSequentialGroup()
					.addComponent(labelOpcoesBolo, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
					.addGap(44))
				.addGroup(gl_painelDelimitadorBolo.createSequentialGroup()
					.addGap(1)
					.addComponent(painelBotoesBolo, GroupLayout.PREFERRED_SIZE, 1160, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_painelDelimitadorBolo.setVerticalGroup(
			gl_painelDelimitadorBolo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitadorBolo.createSequentialGroup()
					.addComponent(labelOpcoesBolo, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
					.addGap(1)
					.addComponent(painelBotoesBolo, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
					.addGap(1))
		);
		
		ButtonGroup grupoBolos = new ButtonGroup();
		
		botaoBoloBrigadeiro = new JRadioButton("Brigadeiro");
		botaoBoloBrigadeiro.setFont(fonteRadioButton);
		botaoBoloBrigadeiro.setForeground(getCorTexto());
		botaoBoloBrigadeiro.setBackground(getCorDeJanelaInterna());
		botaoBoloBrigadeiro.setFocusable(false);
		botaoBoloBrigadeiro.addActionListener(new ListenerBolo());
		grupoBolos.add(botaoBoloBrigadeiro);
		
		botaoBoloPrestigio = new JRadioButton("Prestigio");
		botaoBoloPrestigio.setFont(fonteRadioButton);
		botaoBoloPrestigio.setForeground(getCorTexto());
		botaoBoloPrestigio.setBackground(getCorDeJanelaInterna());
		botaoBoloPrestigio.setFocusable(false);
		botaoBoloPrestigio.addActionListener(new ListenerBolo());
		grupoBolos.add(botaoBoloPrestigio);
		
		botaoBoloAbacaxi = new JRadioButton("Abacaxi");
		botaoBoloAbacaxi.setFont(fonteRadioButton);
		botaoBoloAbacaxi.setForeground(getCorTexto());
		botaoBoloAbacaxi.setBackground(getCorDeJanelaInterna());
		botaoBoloAbacaxi.setFocusable(false);
		botaoBoloAbacaxi.addActionListener(new ListenerBolo());
		grupoBolos.add(botaoBoloAbacaxi);
		
		botaoBoloPessego = new JRadioButton("Pêssego");
		botaoBoloPessego.setFont(fonteRadioButton);
		botaoBoloPessego.setForeground(getCorTexto());
		botaoBoloPessego.setBackground(getCorDeJanelaInterna());
		botaoBoloPessego.setFocusable(false);
		botaoBoloPessego.addActionListener(new ListenerBolo());
		grupoBolos.add(botaoBoloPessego);
		
		botaoBoloDoisAmores = new JRadioButton("Dois amores");
		botaoBoloDoisAmores.setFont(fonteRadioButton);
		botaoBoloDoisAmores.setForeground(getCorTexto());
		botaoBoloDoisAmores.setBackground(getCorDeJanelaInterna());
		botaoBoloDoisAmores.setFocusable(false);
		botaoBoloDoisAmores.addActionListener(new ListenerBolo());
		grupoBolos.add(botaoBoloDoisAmores);
		
		botaoBoloViuvaNegra = new JRadioButton("Viúva negra");
		botaoBoloViuvaNegra.setFont(fonteRadioButton);
		botaoBoloViuvaNegra.setForeground(getCorTexto());
		botaoBoloViuvaNegra.setBackground(getCorDeJanelaInterna());
		botaoBoloViuvaNegra.setFocusable(false);
		botaoBoloViuvaNegra.addActionListener(new ListenerBolo());
		grupoBolos.add(botaoBoloViuvaNegra);
		
		GroupLayout gl_painelBotoesBolo = new GroupLayout(painelBotoesBolo);
		gl_painelBotoesBolo.setHorizontalGroup(
			gl_painelBotoesBolo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelBotoesBolo.createSequentialGroup()
					.addGap(1)
					.addComponent(botaoBoloBrigadeiro, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(botaoBoloPrestigio, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(botaoBoloAbacaxi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(botaoBoloPessego, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(botaoBoloDoisAmores, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(botaoBoloViuvaNegra, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(549))
		);
		gl_painelBotoesBolo.setVerticalGroup(
			gl_painelBotoesBolo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelBotoesBolo.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_painelBotoesBolo.createParallelGroup(Alignment.LEADING)
						.addComponent(botaoBoloViuvaNegra, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(botaoBoloDoisAmores, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(botaoBoloPessego, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(botaoBoloAbacaxi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(botaoBoloPrestigio, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(botaoBoloBrigadeiro, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(10))
		);
		painelBotoesBolo.setLayout(gl_painelBotoesBolo);
		painelDelimitadorBolo.setLayout(gl_painelDelimitadorBolo);
		
		RoundJPanel painelDelimitadorCerveja = new RoundJPanel(25, 25, 25, 25);
		painelDelimitadorCerveja.setBorder(new EmptyBorder(5, 10, 20, 10));
		painelDelimitadorCerveja.setBackground(getCorDeJanelaInterna());
		painelDelimitadorDoceBoloCerveja.add(painelDelimitadorCerveja);
		
		JLabel labelOpcionalCerveja = new JLabel("Opcional de cerveja");
		labelOpcionalCerveja.setForeground(getCorTexto());
		labelOpcionalCerveja.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JPanel painelBotoesCerveja = new JPanel();
		painelBotoesCerveja.setBackground(getCorDeJanelaInterna());
		GroupLayout gl_painelDelimitadorCerveja = new GroupLayout(painelDelimitadorCerveja);
		gl_painelDelimitadorCerveja.setHorizontalGroup(
			gl_painelDelimitadorCerveja.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_painelDelimitadorCerveja.createSequentialGroup()
					.addComponent(labelOpcionalCerveja, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
					.addGap(839))
				.addComponent(painelBotoesCerveja, GroupLayout.DEFAULT_SIZE, 1170, Short.MAX_VALUE)
		);
		gl_painelDelimitadorCerveja.setVerticalGroup(
			gl_painelDelimitadorCerveja.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitadorCerveja.createSequentialGroup()
					.addComponent(labelOpcionalCerveja, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(painelBotoesCerveja, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
					.addGap(0))
		);
		
		botaoComCerveja = new JRadioButton("Com cerveja");
		botaoComCerveja.setFont(fonteRadioButton);
		botaoComCerveja.setBackground(getCorDeJanelaInterna());
		botaoComCerveja.setForeground(getCorTexto());
		botaoComCerveja.setFocusable(false);
		
		botaoSemCerveja = new JRadioButton("Sem cerveja");
		botaoSemCerveja.setFont(fonteRadioButton);
		botaoSemCerveja.setBackground(getCorDeJanelaInterna());
		botaoSemCerveja.setForeground(getCorTexto());
		botaoSemCerveja.setFocusable(false);
		botaoSemCerveja.setSelected(true);
		
		GroupLayout gl_painelBotoesCerveja = new GroupLayout(painelBotoesCerveja);
		gl_painelBotoesCerveja.setHorizontalGroup(
			gl_painelBotoesCerveja.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painelBotoesCerveja.createSequentialGroup()
					.addGap(1)
					.addComponent(botaoComCerveja, GroupLayout.PREFERRED_SIZE, 117, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(botaoSemCerveja, GroupLayout.PREFERRED_SIZE, 124, Short.MAX_VALUE)
					.addGap(915))
		);
		gl_painelBotoesCerveja.setVerticalGroup(
			gl_painelBotoesCerveja.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painelBotoesCerveja.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_painelBotoesCerveja.createParallelGroup(Alignment.TRAILING)
						.addComponent(botaoComCerveja, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(botaoSemCerveja, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(10))
		);
		painelBotoesCerveja.setLayout(gl_painelBotoesCerveja);
		painelDelimitadorCerveja.setLayout(gl_painelDelimitadorCerveja);
		
		JLabel labelSalgados = new JLabel("Opções de salgado");
		labelSalgados.setFont(new Font("Arial", Font.PLAIN, 18));
		labelSalgados.setForeground(getCorTexto());
		
		JPanel painelBotoesSalgado = new JPanel();
		painelBotoesSalgado.setBackground(getCorDeJanelaInterna());
		painelBotoesSalgado.setBackground(getCorDeJanelaInterna());
		GroupLayout gl_painelDelimitadorSalgado = new GroupLayout(painelDelimitadorSalgado);
		gl_painelDelimitadorSalgado.setHorizontalGroup(
			gl_painelDelimitadorSalgado.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitadorSalgado.createSequentialGroup()
					.addGroup(gl_painelDelimitadorSalgado.createParallelGroup(Alignment.LEADING)
						.addComponent(labelSalgados, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
						.addComponent(painelBotoesSalgado, GroupLayout.PREFERRED_SIZE, 1116, Short.MAX_VALUE))
					.addGap(1))
		);
		gl_painelDelimitadorSalgado.setVerticalGroup(
			gl_painelDelimitadorSalgado.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitadorSalgado.createSequentialGroup()
					.addComponent(labelSalgados, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(painelBotoesSalgado, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
		);
		
		botaoSalgadoBolinhaDeQueijo = new JRadioButton("Bolinha de queijo");
		botaoSalgadoBolinhaDeQueijo.setForeground(getCorTexto());
		botaoSalgadoBolinhaDeQueijo.setFont(fonteRadioButtonSalgados);
		botaoSalgadoBolinhaDeQueijo.setBackground(getCorDeJanelaInterna());
		botaoSalgadoBolinhaDeQueijo.setFocusable(false);
		botaoSalgadoBolinhaDeQueijo.addActionListener(new ListenerSalgados());
		
		botaoSalgadoBolinhaDeQueijoComMilho = new JRadioButton("Bolinha de queijo com milho");
		botaoSalgadoBolinhaDeQueijoComMilho.setForeground(getCorTexto());
		botaoSalgadoBolinhaDeQueijoComMilho.setFont(fonteRadioButtonSalgados);
		botaoSalgadoBolinhaDeQueijoComMilho.setBackground(getCorDeJanelaInterna());
		botaoSalgadoBolinhaDeQueijoComMilho.setFocusable(false);
		botaoSalgadoBolinhaDeQueijoComMilho.addActionListener(new ListenerSalgados());
		
		botaoSalgadoRisolesDeFrango = new JRadioButton("Risoles de frango");
		botaoSalgadoRisolesDeFrango.setForeground(getCorTexto());
		botaoSalgadoRisolesDeFrango.setFont(fonteRadioButtonSalgados);
		botaoSalgadoRisolesDeFrango.setBackground(getCorDeJanelaInterna());
		botaoSalgadoRisolesDeFrango.setFocusable(false);
		botaoSalgadoRisolesDeFrango.addActionListener(new ListenerSalgados());
		
		botaoSalgadoRisolesDePresuntoQueijo = new JRadioButton("Risoles de presunto e queijo");
		botaoSalgadoRisolesDePresuntoQueijo.setForeground(getCorTexto());
		botaoSalgadoRisolesDePresuntoQueijo.setFont(fonteRadioButtonSalgados);
		botaoSalgadoRisolesDePresuntoQueijo.setBackground(getCorDeJanelaInterna());
		botaoSalgadoRisolesDePresuntoQueijo.setFocusable(false);
		botaoSalgadoRisolesDePresuntoQueijo.addActionListener(new ListenerSalgados());
		
		botaoSalgadoEnroladinhoPresuntoQueijo = new JRadioButton("Enroladinho de presunto e queijo");
		botaoSalgadoEnroladinhoPresuntoQueijo.setForeground(getCorTexto());
		botaoSalgadoEnroladinhoPresuntoQueijo.setFont(fonteRadioButtonSalgados);
		botaoSalgadoEnroladinhoPresuntoQueijo.setBackground(getCorDeJanelaInterna());
		botaoSalgadoEnroladinhoPresuntoQueijo.setFocusable(false);
		botaoSalgadoEnroladinhoPresuntoQueijo.addActionListener(new ListenerSalgados());
		
		botaoSalgadoEnroladinhoDeSalsicha = new JRadioButton("Enroladinho de salsicha");
		botaoSalgadoEnroladinhoDeSalsicha.setForeground(getCorTexto());
		botaoSalgadoEnroladinhoDeSalsicha.setFont(fonteRadioButtonSalgados);
		botaoSalgadoEnroladinhoDeSalsicha.setBackground(getCorDeJanelaInterna());
		botaoSalgadoEnroladinhoDeSalsicha.setFocusable(false);
		botaoSalgadoEnroladinhoDeSalsicha.addActionListener(new ListenerSalgados());
		
		botaoSalgadoCoxinhaDeFrango = new JRadioButton("Coxinha de frango");
		botaoSalgadoCoxinhaDeFrango.setForeground(getCorTexto());
		botaoSalgadoCoxinhaDeFrango.setFont(fonteRadioButtonSalgados);
		botaoSalgadoCoxinhaDeFrango.setBackground(getCorDeJanelaInterna());
		botaoSalgadoCoxinhaDeFrango.setFocusable(false);
		botaoSalgadoCoxinhaDeFrango.addActionListener(new ListenerSalgados());
		
		botaoSalgadoCoxinhaDeFrangoComCatupiry = new JRadioButton("Coxinha de frango com catupiry");
		botaoSalgadoCoxinhaDeFrangoComCatupiry.setForeground(getCorTexto());
		botaoSalgadoCoxinhaDeFrangoComCatupiry.setFont(fonteRadioButtonSalgados);
		botaoSalgadoCoxinhaDeFrangoComCatupiry.setBackground(getCorDeJanelaInterna());
		botaoSalgadoCoxinhaDeFrangoComCatupiry.setFocusable(false);
		botaoSalgadoCoxinhaDeFrangoComCatupiry.addActionListener(new ListenerSalgados());
		
		botaoSalgadoRisolesDeCarne = new JRadioButton("Risoles de carne");
		botaoSalgadoRisolesDeCarne.setForeground(getCorTexto());
		botaoSalgadoRisolesDeCarne.setFont(fonteRadioButtonSalgados);
		botaoSalgadoRisolesDeCarne.setBackground(getCorDeJanelaInterna());
		botaoSalgadoRisolesDeCarne.setFocusable(false);
		botaoSalgadoRisolesDeCarne.addActionListener(new ListenerSalgados());
		
		botaoSalgadoQuibe = new JRadioButton("Quibe");
		botaoSalgadoQuibe.setForeground(getCorTexto());
		botaoSalgadoQuibe.setFont(fonteRadioButtonSalgados);
		botaoSalgadoQuibe.setBackground(getCorDeJanelaInterna());
		botaoSalgadoQuibe.setFocusable(false);
		botaoSalgadoQuibe.addActionListener(new ListenerSalgados());
		
		botaoSalgadoEsfihaDeCarne = new JRadioButton("Esfiha de carne");
		botaoSalgadoEsfihaDeCarne.setForeground(getCorTexto());
		botaoSalgadoEsfihaDeCarne.setFont(fonteRadioButtonSalgados);
		botaoSalgadoEsfihaDeCarne.setBackground(getCorDeJanelaInterna());
		botaoSalgadoEsfihaDeCarne.setFocusable(false);
		botaoSalgadoEsfihaDeCarne.addActionListener(new ListenerSalgados());
		
		botaoSalgadoEmpadinhaDePalmito = new JRadioButton("Empadinha de palmito");
		botaoSalgadoEmpadinhaDePalmito.setForeground(getCorTexto());
		botaoSalgadoEmpadinhaDePalmito.setFont(fonteRadioButtonSalgados);
		botaoSalgadoEmpadinhaDePalmito.setBackground(getCorDeJanelaInterna());
		botaoSalgadoEmpadinhaDePalmito.setFocusable(false);
		botaoSalgadoEmpadinhaDePalmito.addActionListener(new ListenerSalgados());
		
		GroupLayout gl_painelBotoesSalgado = new GroupLayout(painelBotoesSalgado);
		gl_painelBotoesSalgado.setHorizontalGroup(
			gl_painelBotoesSalgado.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelBotoesSalgado.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_painelBotoesSalgado.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_painelBotoesSalgado.createSequentialGroup()
							.addComponent(botaoSalgadoBolinhaDeQueijo)
							.addGap(5)
							.addComponent(botaoSalgadoBolinhaDeQueijoComMilho, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(botaoSalgadoRisolesDeFrango, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(botaoSalgadoRisolesDePresuntoQueijo, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(botaoSalgadoEnroladinhoPresuntoQueijo, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_painelBotoesSalgado.createSequentialGroup()
							.addComponent(botaoSalgadoCoxinhaDeFrango, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(botaoSalgadoCoxinhaDeFrangoComCatupiry, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(botaoSalgadoRisolesDeCarne, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(botaoSalgadoQuibe, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(botaoSalgadoEsfihaDeCarne, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(botaoSalgadoEmpadinhaDePalmito, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(botaoSalgadoEnroladinhoDeSalsicha, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(80, Short.MAX_VALUE))
		);
		gl_painelBotoesSalgado.setVerticalGroup(
			gl_painelBotoesSalgado.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelBotoesSalgado.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_painelBotoesSalgado.createParallelGroup(Alignment.LEADING)
						.addComponent(botaoSalgadoCoxinhaDeFrango, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(botaoSalgadoCoxinhaDeFrangoComCatupiry, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(botaoSalgadoRisolesDeCarne, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(botaoSalgadoQuibe, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(botaoSalgadoEsfihaDeCarne, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_painelBotoesSalgado.createParallelGroup(Alignment.BASELINE)
							.addComponent(botaoSalgadoEmpadinhaDePalmito, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addComponent(botaoSalgadoEnroladinhoDeSalsicha, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
					.addGroup(gl_painelBotoesSalgado.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(botaoSalgadoEnroladinhoPresuntoQueijo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_painelBotoesSalgado.createParallelGroup(Alignment.LEADING, false)
							.addComponent(botaoSalgadoRisolesDeFrango, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addComponent(botaoSalgadoRisolesDePresuntoQueijo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addComponent(botaoSalgadoBolinhaDeQueijo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(botaoSalgadoBolinhaDeQueijoComMilho, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(10))
		);
		
		painelBotoesSalgado.setLayout(gl_painelBotoesSalgado);
		painelDelimitadorSalgado.setLayout(gl_painelDelimitadorSalgado);
		
		ButtonGroup grupoBotaoCerveja = new ButtonGroup();
		
		grupoBotaoCerveja.add(botaoComCerveja);
		grupoBotaoCerveja.add(botaoSemCerveja);
		
		painel.setLayout(null);
		painel.add(painelVoltaPagina);
		
		JLabel labelVoltaPagina = new JLabel("");
		labelVoltaPagina.addMouseListener(voltaPagina);
		labelVoltaPagina.setIcon(getIconeVoltarPagina());
		painelVoltaPagina.add(labelVoltaPagina);
		painel.add(painelAvancaPagina);
		
		JLabel labelAvancaPagina = new JLabel("");
		labelAvancaPagina.addMouseListener(proximaPagina);
		labelAvancaPagina.setIcon(getIconeProximaPagina());
		
		painelAvancaPagina.add(labelAvancaPagina);
		painel.add(labelTitulo);
		painel.add(painelRetornaTudo);
		painelRetornaTudo.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel labelRetornaTudo = new JLabel("");
		labelRetornaTudo.addMouseListener(retornaTelaInicial);
		labelRetornaTudo.setIcon(getIconeRetornaTudo());
		painelRetornaTudo.add(labelRetornaTudo);
		painel.add(painelDelimitadorDoceBoloCerveja);
		painel.add(painelDelimitadorSalgado);
		
	}
	
	private class ListenerSalgados implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JRadioButton botaoPressionado = (JRadioButton) e.getSource();
			int index = -1;
			
			if (botaoPressionado.equals(botaoSalgadoCoxinhaDeFrango)) 				  index = 0;
			else if (botaoPressionado.equals(botaoSalgadoCoxinhaDeFrangoComCatupiry)) index = 1;
			else if (botaoPressionado.equals(botaoSalgadoRisolesDeCarne)) 			  index = 2;
			else if (botaoPressionado.equals(botaoSalgadoQuibe)) 					  index = 3;
			else if (botaoPressionado.equals(botaoSalgadoEsfihaDeCarne)) 			  index = 4;
			else if (botaoPressionado.equals(botaoSalgadoEmpadinhaDePalmito)) 		  index = 5;
			else if (botaoPressionado.equals(botaoSalgadoEnroladinhoDeSalsicha)) 	  index = 6;
			else if (botaoPressionado.equals(botaoSalgadoBolinhaDeQueijo)) 			  index = 7;
			else if (botaoPressionado.equals(botaoSalgadoBolinhaDeQueijoComMilho)) 	  index = 8;
			else if (botaoPressionado.equals(botaoSalgadoRisolesDeFrango)) 			  index = 9;
			else if (botaoPressionado.equals(botaoSalgadoRisolesDePresuntoQueijo)) 	  index = 10;
			else if (botaoPressionado.equals(botaoSalgadoEnroladinhoPresuntoQueijo))  index = 11;
			else throw new IllegalCallerException("Erro: Listener Salgados foi implementado em um botão não relacionado a salgado");
			
			salgadosSelecionados[index] = botaoPressionado.isSelected();
		}
	}
	
	private class ListenerDoce implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton botaoPressionado = (JRadioButton) e.getSource();
			int index = -1;
			
			if (botaoPressionado.equals(botaoDoceBrigadeiro)) 				index = 0;
			else if (botaoPressionado.equals(botaoDoceBrigadeiroBranco)) 	index = 1;
			else if (botaoPressionado.equals(botaoDoceBrigadeiroColorido)) 	index = 2;
			else if (botaoPressionado.equals(botaoDoceBeijinhoDeCoco)) 		index = 3;
			else if (botaoPressionado.equals(botaoDoceCasadinho))			index = 4;
			else if (botaoPressionado.equals(botaoDoceCajuzinho)) 			index = 5;
			else throw new IllegalCallerException("Erro: Listener Doces foi implementado em um botão não relacionado a doce");
			
			docesSelecionados[index] = botaoPressionado.isSelected();
		}
		
	}

	private class ListenerBolo implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton botaoPressionado = (JRadioButton) e.getSource();
			int index = -1;
			
			if (botaoPressionado.equals(botaoBoloBrigadeiro)) 		index = 0;
			else if (botaoPressionado.equals(botaoBoloPrestigio)) 	index = 1;
			else if (botaoPressionado.equals(botaoBoloAbacaxi)) 	index = 2;
			else if (botaoPressionado.equals(botaoBoloPessego)) 	index = 3;
			else if (botaoPressionado.equals(botaoBoloDoisAmores)) 	index = 4;
			else if (botaoPressionado.equals(botaoBoloViuvaNegra)) 	index = 5;
			else throw new IllegalCallerException("Erro: Listener Bolos foi implementado em um botão não relacionado a bolo");
			
			if (botaoPressionado.isSelected()) {
				boloSelecionado = arrayBolo.get(index);
			}
		}
	}
	
	public boolean getTeraCerveja(){
		return teraCerveja;
	}
	
	public ArrayList<Salgado> getSalgadosEscolhidos() {
		ArrayList<Salgado> listaParaRetorno = new ArrayList<>();
		
		for(int i = 0; i < arraySalgados.size(); i++) {
			if (salgadosSelecionados[i]) {
				listaParaRetorno.add(arraySalgados.get(i));
			}
		}
		
		return listaParaRetorno;
	}
	
	public ArrayList<Doce> getDocesEscolhidos() {
		ArrayList<Doce> listaParaRetorno = new ArrayList<>();
		
		for(int i = 0; i < arrayDoces.size(); i++) {
			if (docesSelecionados[i]) {
				listaParaRetorno.add(arrayDoces.get(i));
			}
		}
		
		return listaParaRetorno;
	}
	
	public Bolo getBoloEscolhido() {
		return boloSelecionado;
	}

	public PopUpErroGenerico verificarPreenchimento(ActionListener listenerVoltar) {
		try {
			int qtdSalgados =  getSalgadosEscolhidos().size();
			int qtdDoces = getDocesEscolhidos().size();

			if (qtdSalgados == 0) {
				return new PopUpErroGenerico(listenerVoltar, "Número de Opções de Salgados Inválido", "Para continuar, selecione pelo menos 7 opções de salgado", "Selecionar Salgados");
			}
			
			if (qtdSalgados < 7) { // Quantidade salgado menor
				return new PopUpErroGenerico(listenerVoltar, "Número de Opções de Salgados Inválido", "Para continuar, selecione mais opções de salgado", "Selecionar Salgados");

			}
			
			if (qtdSalgados > 9) { // Quantidade salgado errada
				return new PopUpErroGenerico(listenerVoltar, "Número de Opções de Salgados Inválido", "Para continuar, selecione menos opções de salgado", "Selecionar Salgados");

			}
			
			if (qtdDoces == 0) {
				return new PopUpErroGenerico(listenerVoltar, "Número de Opções de Doces Inválido", "Para continuar, selecione pelo menos 3 opções de doce", "Selecionar Doces");
			}
			
			if (qtdDoces != 3) { // Quantidade doce errada
				return new PopUpErroGenerico(listenerVoltar, "Número de Opções de Doces Inválido", "Para continuar, selecione apenas 3 opções de doces", "Selecionar Doces");

			}
			
			if (boloSelecionado == null) { // Bolo não está selecionado
				return new PopUpErroGenerico(listenerVoltar,"Número de Opções de Bolo Inválido", "Para continuar, selecione apenas uma opção de bolo", "Selecionar Bolo");
			}
			
			return null;
			
		} catch (Exception e) {
			return new PopUpErroGenerico(listenerVoltar, "Excecao encontrada", "Foi lançada uma exceção em verificarPreenchimento", "Retornar");
		}
	}

	@Override
	public JPanel getPainel() {
		return painel;
	}

	@Override
	public void limparCampos() {
		botaoSalgadoCoxinhaDeFrango.setSelected(false);
		botaoSalgadoCoxinhaDeFrangoComCatupiry.setSelected(false);
		botaoSalgadoRisolesDeCarne.setSelected(false);
		botaoSalgadoQuibe.setSelected(false);
		botaoSalgadoEsfihaDeCarne.setSelected(false);
		botaoSalgadoEmpadinhaDePalmito.setSelected(false);
		botaoSalgadoEnroladinhoDeSalsicha.setSelected(false);
		botaoSalgadoBolinhaDeQueijo.setSelected(false);
	 	botaoSalgadoBolinhaDeQueijoComMilho.setSelected(false);
		botaoSalgadoRisolesDeFrango.setSelected(false);
		botaoSalgadoRisolesDePresuntoQueijo.setSelected(false);
		botaoSalgadoEnroladinhoPresuntoQueijo.setSelected(false);
		for (int i = 0; i < salgadosSelecionados.length; i++) salgadosSelecionados[i] = false;
		
		botaoDoceBrigadeiro.setSelected(false);
		botaoDoceBrigadeiroBranco.setSelected(false);
		botaoDoceBrigadeiroColorido.setSelected(false);
		botaoDoceBeijinhoDeCoco.setSelected(false);
		botaoDoceCasadinho.setSelected(false);
		botaoDoceCajuzinho.setSelected(false);
		for (int i = 0; i < docesSelecionados.length; i++) docesSelecionados[i] = false;
	
		botaoBoloBrigadeiro.setSelected(false);
		botaoBoloPrestigio.setSelected(false);
		botaoBoloAbacaxi.setSelected(false);
		botaoBoloPessego.setSelected(false);
		botaoBoloDoisAmores.setSelected(false);
		botaoBoloViuvaNegra.setSelected(false);
		boloSelecionado = null;
		
		botaoComCerveja.setSelected(false);
		botaoSemCerveja.setSelected(true);
		teraCerveja = false;

		painel.repaint();
	}
}
