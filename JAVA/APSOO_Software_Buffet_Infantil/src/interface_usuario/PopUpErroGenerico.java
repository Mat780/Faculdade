package interface_usuario;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PopUpErroGenerico extends Painel {

	private JPanel painel;
	
	public PopUpErroGenerico(ActionListener listenerRetorno, String mensagemPrincipal, String mensagemInstrucao, String mensagemDoBotao) {
		
		painel = new JPanel();
		painel.setBackground(getCorDeFundo());
		
		RoundJPanel painelExterno = new RoundJPanel(25,25,25,25);
		painelExterno.setBackground(getCorJanelaErro());
		
		GroupLayout gl_painel = new GroupLayout(painel);
		gl_painel.setHorizontalGroup(
			gl_painel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painel.createSequentialGroup()
					.addGap(380)
					.addComponent(painelExterno, GroupLayout.PREFERRED_SIZE, 510, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(380, Short.MAX_VALUE))
		);
		gl_painel.setVerticalGroup(
			gl_painel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painel.createSequentialGroup()
					.addContainerGap(220, Short.MAX_VALUE)
					.addComponent(painelExterno, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					.addGap(240))
		);
		painelExterno.setBackground(getCorJanelaErro());
		
		JLabel aviso = new JLabel("");
		aviso.setIcon(getIconeErro());
		
		JLabel labelMensagemPrincipal = new JLabel("Mensagem de erro");
		labelMensagemPrincipal.setText(mensagemPrincipal);
		labelMensagemPrincipal.setFont(new Font("Arial", Font.PLAIN, 20));
		labelMensagemPrincipal.setHorizontalAlignment(SwingConstants.CENTER);
		
		SemVFXJButton botao = new SemVFXJButton("Mensagem", getCorDeJanelaInterna(), getCorJanelaErro(), new Color(25, 25, 25, 220), new Color(25, 25, 25, 180), 15);
		botao.setText(mensagemDoBotao);
		botao.setForeground(new Color(240, 240, 240));
		botao.setBackground(new Color(25, 25, 25));
		botao.setFont(new Font("Arial", Font.PLAIN, 14));
		botao.addActionListener(listenerRetorno);
		
		JLabel labelMensagemInstrucao = new JLabel("Mensagem de erro secundária");
		labelMensagemInstrucao.setText(mensagemInstrucao);
		labelMensagemInstrucao.setHorizontalAlignment(SwingConstants.CENTER);
		labelMensagemInstrucao.setFont(new Font("Arial", Font.PLAIN, 18));
		GroupLayout gl_painelExterno = new GroupLayout(painelExterno);
		gl_painelExterno.setHorizontalGroup(
			gl_painelExterno.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painelExterno.createSequentialGroup()
					.addGroup(gl_painelExterno.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_painelExterno.createSequentialGroup()
							.addGap(218)
							.addComponent(aviso, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_painelExterno.createSequentialGroup()
							.addGap(125)
							.addComponent(botao, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_painelExterno.createSequentialGroup()
							.addGap(50)
							.addComponent(labelMensagemPrincipal, GroupLayout.PREFERRED_SIZE, 416, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_painelExterno.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(labelMensagemInstrucao, GroupLayout.PREFERRED_SIZE, 490, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_painelExterno.setVerticalGroup(
			gl_painelExterno.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painelExterno.createSequentialGroup()
					.addContainerGap()
					.addComponent(aviso, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(labelMensagemPrincipal, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelMensagemInstrucao, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addComponent(botao, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
					.addGap(21))
		);
		painelExterno.setLayout(gl_painelExterno);
		painel.setLayout(gl_painel);
		painel.setVisible(true);
		
	}

	@Override
	public JPanel getPainel() {
		return painel;
	}

}
