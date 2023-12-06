package interface_usuario;

import javax.swing.JPanel;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PainelEscolhaDeOrcamento extends Painel {
	
	private JPanel painel;
	private SemVFXJButton botaoOrcamentoBuffetCompleto;
	private SemVFXJButton botaoOrcamentoLocacao;
	private JLabel labelRetornaTudo;
	private JLabel labelVoltar;
	
	public PainelEscolhaDeOrcamento(MouseListener voltarTelaInicial, ActionListener entrarOrcamentoBuffetCompleto, ActionListener entrarOrcamentoLocacaoDeEspaco) {
		super();
		
		painel = new JPanel();
		
		painel.setBackground(getCorDeFundo());
		
		JPanel painelDivisorBotoes = new JPanel();
		painelDivisorBotoes.setBackground(getCorDeFundo());
		
		JPanel painelRetornaTudo = new JPanel();
		painelRetornaTudo.setBackground(getCorDeFundo());
		
		JPanel painelVoltar = new JPanel();
		painelVoltar.setBackground(getCorDeFundo());
		
		JLabel labelTitulo = new JLabel("Qual o tipo de orçamento?");
		labelTitulo.setFont(getFonteTitulo());
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_painel = new GroupLayout(painel);
		gl_painel.setHorizontalGroup(
			gl_painel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painel.createSequentialGroup()
					.addGap(20)
					.addComponent(painelVoltar, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 1085, Short.MAX_VALUE)
					.addComponent(painelRetornaTudo, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(25))
				.addGroup(gl_painel.createSequentialGroup()
					.addGap(400)
					.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING)
						.addComponent(labelTitulo, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
						.addComponent(painelDivisorBotoes, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE))
					.addGap(400))
		);
		gl_painel.setVerticalGroup(
			gl_painel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painel.createSequentialGroup()
					.addGap(15)
					.addComponent(painelRetornaTudo, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(82)
					.addComponent(labelTitulo, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(painelDivisorBotoes, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
					.addGap(109)
					.addComponent(painelVoltar, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(20))
		);
		painelRetornaTudo.setLayout(new GridLayout(1, 0, 0, 0));
		
		labelRetornaTudo = new JLabel("");
		labelRetornaTudo.setIcon(getIconeRetornaTudo());
		painelRetornaTudo.add(labelRetornaTudo);
		painelVoltar.setLayout(new GridLayout(1, 0, 0, 0));
		
		labelVoltar = new JLabel("");
		labelVoltar.setIcon(getIconeVoltarPagina());
		painelVoltar.add(labelVoltar);
		painelDivisorBotoes.setLayout(new GridLayout(2, 0, 0, 20));
		
		RoundJPanel painelExternoOrcamentoBuffetCompleto = new RoundJPanel(35, 35, 35, 35);
		painelExternoOrcamentoBuffetCompleto.setBorder(new EmptyBorder(20, 20, 20, 20));
		painelExternoOrcamentoBuffetCompleto.setBackground(getCorDeJanelaInterna());
		painelDivisorBotoes.add(painelExternoOrcamentoBuffetCompleto);
		painelExternoOrcamentoBuffetCompleto.setLayout(new GridLayout(1, 0, 0, 0));
		
		botaoOrcamentoBuffetCompleto = new SemVFXJButton("Orçamento de buffet completo", Color.WHITE, Color.WHITE);
		botaoOrcamentoBuffetCompleto.setFont(getFonteLabelInterno());
		botaoOrcamentoBuffetCompleto.setFocusable(false);
		botaoOrcamentoBuffetCompleto.addActionListener(entrarOrcamentoBuffetCompleto);
		painelExternoOrcamentoBuffetCompleto.add(botaoOrcamentoBuffetCompleto);
		
		RoundJPanel painelExternoOrcamentoLocacao = new RoundJPanel(25, 25, 25, 25);
		painelExternoOrcamentoLocacao.setBorder(new EmptyBorder(20, 20, 20, 20));
		painelExternoOrcamentoLocacao.setBackground(getCorDeJanelaInterna());
		painelDivisorBotoes.add(painelExternoOrcamentoLocacao);
		painelExternoOrcamentoLocacao.setLayout(new GridLayout(0, 1, 0, 0));
		
		botaoOrcamentoLocacao = new SemVFXJButton("Orçamento de locação de espaço", Color.WHITE, Color.WHITE);
		botaoOrcamentoLocacao.setFont(getFonteLabelInterno());
		botaoOrcamentoLocacao.setFocusable(false);
		botaoOrcamentoLocacao.addActionListener(entrarOrcamentoLocacaoDeEspaco);
		painelExternoOrcamentoLocacao.add(botaoOrcamentoLocacao);
		painel.setLayout(gl_painel);
		
	}
	
	@Override
	public JPanel getPainel() {
		return painel;
	}

}
