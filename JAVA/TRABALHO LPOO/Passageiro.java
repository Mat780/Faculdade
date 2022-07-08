public class Passageiro extends Pessoa {
    private Viagem viagem;
    private boolean passaporteAtualizado;

    public Passageiro(String nome, String cpf, boolean passaporteAtualizado, Viagem viagem)
            throws OnlyLetterException, InvalidCpfException {
        super(nome, cpf);
        setPassaporteAtualizado(passaporteAtualizado);
        setViagem(viagem);
    }

    public Passageiro() throws OnlyLetterException, InvalidCpfException {
        super("Selecionar", "000.000.000-00");
    }

    public void setPassaporteAtualizado(boolean passaporteAtualizado) {
        this.passaporteAtualizado = passaporteAtualizado;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
        viagem.addPassageiro(this);
    }

    public boolean getPassaporteAtualizado() {
        return passaporteAtualizado;
    }

    public Viagem getViagem() {
        return viagem;
    }

    @Override
    public boolean equals(Object obj) {
        Pessoa castObj = (Pessoa) obj;

        if (castObj.getCpf().equals(this.getCpf())) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        if (getCpf().equals("000.000.000-00")) {
            return "Selecionar";

        } else {
            return "CPF: " + this.getCpf() + " Nome: " + this.getNome() + " Passaporte atualizado: "
                    + (this.getPassaporteAtualizado() ? "Sim" : "Não");

        }
    }

    public Passageiro clonar() throws OnlyLetterException, InvalidCpfException {
        return new Passageiro(getNome(), getCpf(), getPassaporteAtualizado(), getViagem());
    }
}
