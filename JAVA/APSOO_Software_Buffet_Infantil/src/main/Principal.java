package main;

import interface_usuario.JanelaPrincipal;
import persistencia.ConexaoBanco;

public class Principal {

	public static void main(String[] args) {
		ConexaoBanco.getConexao();
		System.out.println(ConexaoBanco.statusConection());
		JanelaPrincipal janelaPrincipal = new JanelaPrincipal();
		
	}

}
