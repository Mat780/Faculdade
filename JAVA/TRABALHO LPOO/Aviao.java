import java.util.ArrayList;

public class Aviao{
    private String modeloAviao;
	private boolean voarInternacional;
	private EquipeBordo equipeBordo;
	private ArrayList<Viagem> listaViagem;

    public Aviao (String modeloAviao, boolean voarInternacional, EquipeBordo equipeBordo){
		setModelo(modeloAviao);
		setVoarInternacional(voarInternacional);
		setEquipeBordo(equipeBordo);
		listaViagem = new ArrayList<Viagem>();
	}

	public Aviao (){
		setModelo(null);
		this.equipeBordo = null;
	}

	public void setModelo(String modeloAviao){
		this.modeloAviao = modeloAviao;
	}
	public void setVoarInternacional(boolean voarInternacional) {
		this.voarInternacional = voarInternacional;
	}
	public void setEquipeBordo(EquipeBordo equipeBordo){
		this.equipeBordo = equipeBordo;
	}
    public String getModelo(){
		return modeloAviao;
	}
	public boolean getVoarInternacional(){
		return voarInternacional;
	}
	public EquipeBordo getEquipeBordo(){
		return equipeBordo;
	}

	public void addViagem(Viagem viagem){
		listaViagem.add(viagem);
	}

	public int analalizarViagem(){
		return listaViagem.size();
	}

	public void removeViagem(Viagem viagem){
		listaViagem.remove(viagem);
	}

	@Override
	public String toString(){
		if(getModelo() == null){
			return "Selecionar";
			
		} else {
			return "Modelo: " + getModelo() + " voa " + (getVoarInternacional() ? "internacionalmente " : "nacionalmente") + " Piloto: " + getEquipeBordo().getPiloto().getNome() + " de CPF: " + getEquipeBordo().getPiloto().getCpf();

		}
	}

	public Aviao clonar(){
		return new Aviao(getModelo(), getVoarInternacional(), this.equipeBordo);
	}
}
 