public class Piloto extends Pessoa {
    private int idade;
    private int tempoExp;

    public Piloto(String nome, String cpf, int idade, int tempoExp) 
    throws OnlyLetterException,
           InvalidCpfException,
           TooOldException,
           TooYoungException,
           ImpossibleTimeExperienceException,
           OnlyPositiveNumbersException
    {
        super(nome, cpf);
        setIdade(idade);
        setTempoExp(tempoExp);
    }

    public Piloto() throws OnlyLetterException, InvalidCpfException{
        super("Selecionar", "000.000.000-00");
    };
    
    public void setIdade(int idade) throws OnlyPositiveNumbersException, TooOldException, TooYoungException {
        if(idade < 0){
            throw new OnlyPositiveNumbersException("idade");

        } else if(idade >= 100){
            throw new TooOldException();

        } else if(idade < 18) {
            throw new TooYoungException();

        } else {
            this.idade = idade;

        }
    }

    public void setTempoExp(int tempoExp) throws ImpossibleTimeExperienceException{
        if(idade - 18 < tempoExp){
            throw new ImpossibleTimeExperienceException();

        } else {
            this.tempoExp = tempoExp;
        }
    }
    
    public int getTempoExp(){
        return tempoExp;
    }

    public int getIdade(){
        return idade;
    }

    public String toSelect(){
        return getNome() + " que possui CPF: " + getCpf() + " tem " + getIdade() + " anos e possui " + getTempoExp() + " anos de experiência como piloto"; 
    }
    
    @Override
    public String toString(){
        if(getCpf().equals("000.000.000-00")){
            return "Selecionar";

        } else {
            return "CPF: " + getCpf() + " Possui " + getTempoExp() + " anos de experiência";

        }
    }

    public Piloto clonar() throws OnlyLetterException,
        InvalidCpfException,
        TooOldException,
        TooYoungException,
        ImpossibleTimeExperienceException,
        OnlyPositiveNumbersException 
    {
        return new Piloto(getNome(), getCpf(), getIdade(), getTempoExp()); 
    }
}


