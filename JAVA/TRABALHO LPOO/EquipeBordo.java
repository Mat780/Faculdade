public class EquipeBordo {
    private Piloto piloto;
    private Piloto coPiloto;
    private Comissario[] listaComissarios;
    private boolean contratada;

    public EquipeBordo(Piloto piloto, Piloto coPiloto, Comissario[] listaComissarios)
            throws InsufficientTaskTypeException, LessExperienceException {
        setPiloto(piloto);
        setCoPiloto(coPiloto);
        setListaComissarios(listaComissarios);
        setContratada(false);
        setTodosAbordo(true);
    }

    public EquipeBordo() {
        piloto = null;
        coPiloto = null;
        listaComissarios = null;
    }

    public void setContratada(boolean b) {
        this.contratada = b;
    }

    public void setListaComissarios(Comissario[] listaComissarios) throws InsufficientTaskTypeException {
        if (listaComissarios.length >= 4) {
            boolean hasLimpeza = false;
            boolean hasServico = false;
            boolean hasInstrutor = false;
            boolean hasRecepcao = false;
            for (int i = 0; i < listaComissarios.length && listaComissarios[i] != null; i++) {
                if (listaComissarios[i].getTarefa().equals("Limpeza"))
                    hasLimpeza = true;
                else if (listaComissarios[i].getTarefa().equals("Serviço"))
                    hasServico = true;
                else if (listaComissarios[i].getTarefa().equals("Instruções"))
                    hasInstrutor = true;
                else if (listaComissarios[i].getTarefa().equals("Recepção"))
                    hasRecepcao = true;
            }

            if (hasLimpeza && hasServico && hasInstrutor && hasRecepcao) {
                this.listaComissarios = listaComissarios;

            } else { // TRATAMENTO DE ERRO! (Execeção : membros insuficientes para cada função, ou
                     // inexistentes)
                throw new InsufficientTaskTypeException();
            }

        } else { // TRATAMENTO DE ERRO!
            throw new InsufficientTaskTypeException();
        }
    }

    public void setPiloto(Piloto piloto) throws LessExperienceException { // ERRO: Experiencia >= 1 ano
        if (piloto.getTempoExp() >= 1) {
            this.piloto = piloto;
        } else {
            throw new LessExperienceException();
        }
    }

    public void setCoPiloto(Piloto coPiloto) throws LessExperienceException {
        if (piloto.getTempoExp() > coPiloto.getTempoExp()) {
            this.coPiloto = coPiloto;
        } else { // TRATAMENTO DE ERRO! (Execeção: O copiloto tem menos exp do que o piloto)
            throw new LessExperienceException();
        }
    }

    public void setTodosAbordo(boolean bo) {
        piloto.setAbordo(bo);
        coPiloto.setAbordo(bo);
        for (int i = 0; i < listaComissarios.length; i++) {
            listaComissarios[i].setAbordo(bo);
        }
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public Comissario[] getListaComissarios() {
        return listaComissarios;

    }

    public Piloto getCoPiloto() {
        return coPiloto;
    }

    public boolean getContratada() {
        return contratada;
    }

    @Override
    public String toString() {
        if (piloto == null) {
            return "Selecionar";
        } else {
            return "Piloto: " + getPiloto().getNome() + ", Co-piloto: " + getCoPiloto().getNome()
                    + ", juntamente aos(as) comissarios(as): " + getListaComissarios()[0].getNome() + ", "
                    + getListaComissarios()[1].getNome() + ", " + getListaComissarios()[2].getNome() + " e "
                    + getListaComissarios()[3].getNome();
        }

    }

    public EquipeBordo clonar() throws InsufficientTaskTypeException, LessExperienceException {
        return new EquipeBordo(getPiloto(), getCoPiloto(), getListaComissarios());
    }

}
