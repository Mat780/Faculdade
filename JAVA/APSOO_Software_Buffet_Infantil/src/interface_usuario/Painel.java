package interface_usuario;

import java.awt.Color;
import java.awt.Font;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public abstract class Painel {
	
	private Color corDeFundo;
	private Color corJanelaInterna;
	private Color corJanelaErro;
	private Color corTitulo;
	private Color corTexto;
	private Color corInputs;
	private Color corTextoErro;
	private Color corValor;
	
	private Font fonteTitulo;
	private Font fonteLabelInterno;
	private Font fonteInputs;
	private Font fonteValor;
	
	private String urlDeConfig;
	
	protected Painel() {
		urlDeConfig = "config.txt";
		setCores();
		setFontes();
	}
	
	public abstract JPanel getPainel();
	public void limparCampos() {} 
	
	private void setCores() {
		Path caminhoDeConfig = Path.of(urlDeConfig);
		Properties properties = new Properties();
		
		try {
			String aux;
			String[] auxArray;
			int[] auxInt = new int[3];
			
			properties.load(Files.newBufferedReader(caminhoDeConfig));
			
			aux = properties.getProperty("COR_DE_FUNDO");
			auxArray = aux.split(",");
			for (int i = 0; i < auxArray.length; i++) auxInt[i] = Integer.valueOf(auxArray[i]);
			corDeFundo = new Color(auxInt[0], auxInt[1], auxInt[2]);
			
			aux = properties.getProperty("COR_DE_FUNDO_JANELA_INTERNA");
			auxArray = aux.split(",");
			for (int i = 0; i < auxArray.length; i++) auxInt[i] = Integer.valueOf(auxArray[i]);
			corJanelaInterna = new Color(auxInt[0], auxInt[1], auxInt[2]);
			
			aux = properties.getProperty("COR_DE_FUNDO_JANELA_ERRO");
			auxArray = aux.split(",");
			for (int i = 0; i < auxArray.length; i++) auxInt[i] = Integer.valueOf(auxArray[i]);
			corJanelaErro = new Color(auxInt[0], auxInt[1], auxInt[2]);
			
			aux = properties.getProperty("COR_LETRA_TITULO");
			auxArray = aux.split(",");
			for (int i = 0; i < auxArray.length; i++) auxInt[i] = Integer.valueOf(auxArray[i]);
			corTitulo = new Color(auxInt[0], auxInt[1], auxInt[2]);
			
			aux = properties.getProperty("COR_LETRA_JANELA_INTERNA");
			auxArray = aux.split(",");
			for (int i = 0; i < auxArray.length; i++) auxInt[i] = Integer.valueOf(auxArray[i]);
			corTexto = new Color(auxInt[0], auxInt[1], auxInt[2]);
			
			aux = properties.getProperty("COR_LETRA_INPUTS");
			auxArray = aux.split(",");
			for (int i = 0; i < auxArray.length; i++) auxInt[i] = Integer.valueOf(auxArray[i]);
			corInputs = new Color(auxInt[0], auxInt[1], auxInt[2]);
			
			aux = properties.getProperty("COR_LETRA_ERRO");
			auxArray = aux.split(",");
			for (int i = 0; i < auxArray.length; i++) auxInt[i] = Integer.valueOf(auxArray[i]);
			corTextoErro = new Color(auxInt[0], auxInt[1], auxInt[2]);
			
			aux = properties.getProperty("COR_LETRA_VALOR");
			auxArray = aux.split(",");
			for (int i = 0; i < auxArray.length; i++) auxInt[i] = Integer.valueOf(auxArray[i]);
			corValor = new Color(auxInt[0], auxInt[1], auxInt[2]);
			
		} catch (Exception e) {
			System.out.println("Erro: Ao tentar abrir o arquivo configs.txt");
		} 
		//Abaixo começa debug, retirar trecho de codigo na versao final
		corDeFundo = new Color(100, 183, 206);
		corTexto = new Color(240,240,240);
		corTitulo = new Color(34,45,48);
		corJanelaInterna = new Color(25,25,25);
		corInputs = new Color(0,0,0);
		corTextoErro = new Color(219, 19, 19);
		corValor = new Color(6, 198, 54);
		corJanelaErro = new Color(217, 217, 217);
		// Fim debug
	}
	
	private void setFontes() {
		Path caminhoDeConfig = Path.of(urlDeConfig);
		Properties properties = new Properties();
		
		try {
			String aux;
			String[] auxArray;
			
			properties.load(Files.newBufferedReader(caminhoDeConfig));
			
			aux = properties.getProperty("FONTE_TITULO");
			auxArray = aux.split(",");
			fonteTitulo = new Font(auxArray[0], Font.PLAIN, Integer.valueOf(auxArray[1]));
			
			aux = properties.getProperty("FONTE_LABEL_INTERNO");
			auxArray = aux.split(",");
			fonteLabelInterno = new Font(auxArray[0], Font.PLAIN, Integer.valueOf(auxArray[1]));
			
			aux = properties.getProperty("FONTE_INPUTS");
			auxArray = aux.split(",");
			fonteInputs = new Font(auxArray[0], Font.PLAIN, Integer.valueOf(auxArray[1]));
			
			aux = properties.getProperty("FONTE_LABEL_VALOR");
			auxArray = aux.split(",");
			fonteValor = new Font(auxArray[0], Font.BOLD, Integer.valueOf(auxArray[1]));
			
		} catch (Exception e) {
			System.out.println("Erro: Ao tentar abrir o arquivo configs.txt");
		} 
		
		// TODO Retirar Debug apos finalizar as telas
		fonteTitulo = new Font("Calibri Light", Font.PLAIN, 44);
		fonteLabelInterno = new Font("Arial", Font.PLAIN, 24);
		fonteInputs = new Font("Arial", Font.PLAIN, 24);
		fonteValor = new Font("Arial", Font.BOLD, 40);
		// Fim do Debug
	}
	
	protected ImageIcon getIconeRetornaTudo() {
		return new ImageIcon("imgs/retornaTudo.png");
	}
	
	protected ImageIcon getIconeProximaPagina() {
		return new ImageIcon("imgs/proximaPagina.png");
	}
	
	protected ImageIcon getIconeVoltarPagina() {
		return new ImageIcon("imgs/voltarPagina.png");
	}
	
	protected ImageIcon getIconeErro() {
		return new ImageIcon("imgs/simboloAlerta.png");
	}
	
	protected Color getCorDeFundo() {
		return corDeFundo;
	}
	
	protected Color getCorDeJanelaInterna() {
		return corJanelaInterna;
	}
	
	protected Color getCorJanelaErro() {
		return corJanelaErro;
	}
	
	protected Color getCorTitulo() {
		return corTitulo;
	}
	
	protected Color getCorTexto() {
		return corTexto;
	}
	
	protected Color getCorInputs() {
		return corInputs;
	}
	
	protected Color getCorTextoErro() {
		return corTextoErro;
	}
	
	protected Color getCorValor() {
		return corValor;
	}
	
	protected Font getFonteTitulo() {
		return fonteTitulo;
	}
	
	protected Font getFonteLabelInterno() {
		return fonteLabelInterno;
	}
	
	protected Font getFonteInputs() {
		return fonteInputs;
	}
	
	protected Font getFonteValor() {
		return fonteValor;
	}
	
}
