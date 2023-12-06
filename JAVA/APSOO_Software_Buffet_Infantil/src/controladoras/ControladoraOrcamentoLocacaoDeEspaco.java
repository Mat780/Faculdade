package controladoras;

import negocio.OrcamentoLocacaoDeEspaco;
import negocio.ServicoContratado;
import negocio.Cliente;
import negocio.Data;

import java.util.ArrayList;

public class ControladoraOrcamentoLocacaoDeEspaco {

    private ControladoraOrcamentoLocacaoDeEspaco() throws IllegalAccessException {
		throw new IllegalAccessException("Instanciamento inválido, esta classe é estática");
	}

    public static boolean verificarData(Data data) {
        return OrcamentoLocacaoDeEspaco.verificarData(data);
    }

    public static OrcamentoLocacaoDeEspaco criarOrcamento (int numeroDeConvidados, int numeroDeColaboradores, String horaDeInicio, 
            Data data, Cliente cliente) {
        return new OrcamentoLocacaoDeEspaco(numeroDeConvidados, numeroDeColaboradores, horaDeInicio, data, cliente);
    }

    public static void anexarServicos (OrcamentoLocacaoDeEspaco orcamento, ArrayList<ServicoContratado> servicosContratados) {
        for (int i = 0; i < servicosContratados.size(); i++) {
            orcamento.anexarServico(servicosContratados.get(i));
        }
    }

    public static boolean cadastrarOrcamento (OrcamentoLocacaoDeEspaco orcamento) {
        return orcamento.cadastrarOrcamento();
    }
}