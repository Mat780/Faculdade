import java.util.ArrayList;

public class Viagem{
	private ArrayList<Aeroporto> listaAeroportos;
	private ArrayList<Passageiro> listaPassageiro;
	private Aviao aviao;
	private boolean isInternational;
	private double valorViagem;

	public Viagem(Aviao aviao, ArrayList<Aeroporto> listaAeroportos, double valorViagem) throws OnlyPositiveNumbersException, SameAirportException{
        setAviao(aviao);
		setListaAeroportos(listaAeroportos);
		setValorViagem(valorViagem);
		setListaPassageiros(new ArrayList<Passageiro>());
	}

	public Viagem(){
		this.aviao = null;
	}

    public void setAviao (Aviao aviao) {
        this.aviao = aviao;
    }

	private void setIsInternational(boolean isInternational) {
		this.isInternational = isInternational;
	
	}

    public void setValorViagem(double valorViagem)throws OnlyPositiveNumbersException{
		if(valorViagem < 0.0){
			throw new OnlyPositiveNumbersException("valor da viagem");
			
		} else {
			this.valorViagem = valorViagem;
		}
    }	

	public void setListaAeroportos(ArrayList<Aeroporto> listaAeroportos) throws SameAirportException{

		if( listaAeroportos.get(0).equals(listaAeroportos.get(1)) ){
			throw new SameAirportException();

		}
		
		if( listaAeroportos.get(0).getPais().equals(listaAeroportos.get(1).getPais()) ){
			setIsInternational(false);

		} else {
			setIsInternational(true);

		}

		this.listaAeroportos = listaAeroportos;
		
	}

	private void setListaPassageiros(ArrayList<Passageiro> arrayPassageiro){
		this.listaPassageiro = arrayPassageiro;
	}

    public Aviao getAviao(){
        return aviao;
    }

	public boolean getIsInternational(){
		return isInternational;
	}

	public double getValorViagem(){
		return valorViagem;
	}

	public ArrayList<Aeroporto> getListaAeroportos(){
		return listaAeroportos;
	}

	public ArrayList<Passageiro> getListaPassageiros(){
		return listaPassageiro;
	}
	
	public void addPassageiro(Passageiro passageiro){
		listaPassageiro.add(passageiro);
	}

	public ArrayList<Passageiro> getListaPassageiro(){
		return listaPassageiro;
	}

	public void removePassageiro(Passageiro passageiro){
		listaPassageiro.remove(passageiro);
	}

	@Override
	public String toString(){
		if(getAviao() == null){
			return "Selecionar";
		} else {
			return "Sai de " + listaAeroportos.get(0).getNomeAeroporto() + " para " + listaAeroportos.get(1).getNomeAeroporto() + ", " + (getIsInternational() ? "Internacional" : "Nacional");
		}
	}

	public Viagem clonar() throws OnlyPositiveNumbersException, SameAirportException{
		Viagem v = new Viagem(getAviao(), getListaAeroportos(), getValorViagem());
		v.setListaPassageiros(this.getListaPassageiros());
		return v;
	}

}