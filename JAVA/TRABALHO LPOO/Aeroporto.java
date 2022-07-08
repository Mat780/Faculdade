import java.util.ArrayList;

public class Aeroporto{
    private String nomeAeroporto;
	private String pais;
    private boolean recebeInternacional; 
	private ArrayList<Viagem> listaViagem;
	
	public Aeroporto (String nomeAeroporto, String pais, boolean recebeInternacional) throws OnlyLetterException {
		setNomeAeroporto(nomeAeroporto);
        setPais(pais);
		setRecebeInternacional(recebeInternacional);
		listaViagem = new ArrayList<Viagem>();
	}

	public Aeroporto () throws OnlyLetterException, InvalidCpfException{
		this.nomeAeroporto = null;
		this.pais = null;
	}

    public void setNomeAeroporto(String nomeAeroporto) throws OnlyLetterException {  // ERRO: Somente letras são permitidas
		if(nomeAeroporto.matches("[a-zA-Z ]+")){ // Se conter só letras
			this.nomeAeroporto = nomeAeroporto;
		} else { // Senão = ERRO
			throw new OnlyLetterException("nome");
		}
        
    }
	
	private void setPais(String pais) throws OnlyLetterException{ // ERRO: Somente letras são permitidas//
		if(pais.matches("[a-zA-Z ]+")){ //APENAS LETRAS ACEITAS
			this.pais = pais;
		} else {
			throw new OnlyLetterException("pais");
		}
	}

	private void setRecebeInternacional(boolean recebeInternacional){
		this.recebeInternacional = recebeInternacional;
	}
    
    public String getNomeAeroporto(){
        return nomeAeroporto;
    }
    
	public String getPais(){
		return pais;
	}

	public boolean getRecebeInternacional(){
		return recebeInternacional;
	}	

	public void addViagem(Viagem viagem){
		listaViagem.add(viagem);
	}

	public int verificarViagem(){
		return listaViagem.size();
	}

	public void removeViagem(Viagem viagem){
		listaViagem.remove(viagem);
	}

	@Override
	public String toString(){
		if(getNomeAeroporto() == null){
			return "Selecionar";
			
		} else {
			return "Nome: " + getNomeAeroporto() + " no pais: " + getPais() + " e" + (getRecebeInternacional() ? " " : " não ") + "recebe vôos internacionais";

		}
	}

	@Override
	public boolean equals(Object obj) {
		Aeroporto castObj = (Aeroporto) obj;

		if(castObj.getNomeAeroporto() == getNomeAeroporto()){
			return true;
			
		} else {
			return false;
		}
	}

	public Aeroporto clonar() throws OnlyLetterException{
		return new Aeroporto(getNomeAeroporto(), getPais(), getRecebeInternacional());
	}

}
