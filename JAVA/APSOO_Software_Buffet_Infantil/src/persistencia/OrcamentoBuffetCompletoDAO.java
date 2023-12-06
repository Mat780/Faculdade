package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import excecoes.ExcecaoValorNaoSetado;
import negocio.Bolo;
import negocio.Data;
import negocio.Doce;
import negocio.OrcamentoBuffetCompleto;
import negocio.Salgado;

public class OrcamentoBuffetCompletoDAO implements DAO<OrcamentoBuffetCompleto, Integer> {

	@Override
	public OrcamentoBuffetCompleto get(Integer objetoInteger) {
		int id = objetoInteger.intValue();
		String sql = "SELECT * FROM orcamentobuffetcompleto WHERE id = ?";

		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, id);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					String cpfCliente = rs.getString("fk_cpfCliente");
					int idBolo = rs.getInt("fk_idBolo");
					int idPagamento = rs.getInt("fk_idBolo");
					// atributos OrcamentoBuffetCompleto no banco
					// id numeroDeConvidados numeroDeColaboradores horaDeInicio data teraCerveja
					// fk_cpfCliente fk_idBolo fk_idPagamento

					// formato data: dd/mm/aaaa
					String dataString = rs.getString("data");
					int dia = Integer.parseInt(dataString.split("/")[0]);
					int mes = Integer.parseInt(dataString.split("/")[1]);
					int ano = Integer.parseInt(dataString.split("/")[2]);
					Data dataOrcamento = new Data(dia, mes, ano);

					boolean teraCerveja = rs.getInt("teraCerveja") == 1;

					SalgadoSelecionadoDAO salgadoSelecionadoDao = new SalgadoSelecionadoDAO();
					ArrayList<Salgado> salgadosSelecionados = salgadoSelecionadoDao.getAllBuffet(rs.getInt("id"));
					ArrayList<Salgado> salgados = new ArrayList<Salgado>();

					for (int i = 0; i < salgadosSelecionados.size(); i++)
						salgados.add(salgadosSelecionados.get(i));

					DoceSelecionadoDAO doceSelecionadoDao = new DoceSelecionadoDAO();
					ArrayList<Doce> docesSelecionados = doceSelecionadoDao.getAllBuffet(rs.getInt("id"));
					ArrayList<Doce> doces = new ArrayList<Doce>();

					for (int i = 0; i < docesSelecionados.size(); i++)
						doces.add(docesSelecionados.get(i));

					BoloDAO boloDao = new BoloDAO();
					Bolo bolo = boloDao.get(idBolo);

//					Quando houver cliente:
					ClienteDAO clienteDAO = new ClienteDAO();

//					Quando houver pagamento:
//					PagamentoDAO pagamentoDao = new PagamentoDAO();
//					Pagamento pagamento = pagamentoDao.get(idPagamento);

					OrcamentoBuffetCompleto orcamentoBuffetCompletoEncontrado = new OrcamentoBuffetCompleto(
							rs.getInt("numeroDeConvidados"), rs.getInt("numeroDeColaboradores"),
							rs.getString("horaDeInicio"), dataOrcamento, null,
							clienteDAO.get(rs.getString("fk_cpfCliente")), rs.getInt("id"), teraCerveja, salgados,
							doces, bolo);
					return orcamentoBuffetCompletoEncontrado;
				}
			}
			return null;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		} catch (ExcecaoValorNaoSetado e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public ArrayList<OrcamentoBuffetCompleto> getAll() {
		String sql = "SELECT * FROM orcamentobuffetcompleto";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);

			try (ResultSet rs = statement.executeQuery()) {
				ArrayList<OrcamentoBuffetCompleto> orcamentoBuffetCompletoEncontrados = new ArrayList<OrcamentoBuffetCompleto>();
				BoloDAO boloDao = new BoloDAO();
				SalgadoSelecionadoDAO salgadoSelecionadoDao = new SalgadoSelecionadoDAO();
				DoceSelecionadoDAO doceSelecionadoDao = new DoceSelecionadoDAO();
//				ClienteDAO clienteDao = new ClienteDAO();
//				PagamentoDAO pagamentoDao = new PagamentoDAO();
				while (rs.next()) {
					String cpfCliente = rs.getString("fk_cpfCliente");
					int idBolo = rs.getInt("fk_idBolo");
					int idPagamento = rs.getInt("fk_idBolo");

					String dataString = rs.getString("data");
					int dia = Integer.parseInt(dataString.split("/")[0]);
					int mes = Integer.parseInt(dataString.split("/")[1]);
					int ano = Integer.parseInt(dataString.split("/")[2]);
					Data dataOrcamento = new Data(dia, mes, ano);

					boolean teraCerveja = rs.getInt("teraCerveja") == 1;

					ArrayList<Salgado> salgadosSelecionados = salgadoSelecionadoDao.getAllBuffet(rs.getInt("id"));
					ArrayList<Salgado> salgados = new ArrayList<Salgado>();

					for (int i = 0; i < salgadosSelecionados.size(); i++)
						salgados.add(salgadosSelecionados.get(i));

					ArrayList<Doce> docesSelecionados = doceSelecionadoDao.getAllBuffet(rs.getInt("id"));
					ArrayList<Doce> doces = new ArrayList<Doce>();

					for (int i = 0; i < docesSelecionados.size(); i++)
						doces.add(docesSelecionados.get(i));

					Bolo bolo = boloDao.get(idBolo);

//					Quando houver cliente:
					ClienteDAO clienteDAO = new ClienteDAO();

					OrcamentoBuffetCompleto orcamentoBuffetCompletoEncontrado = new OrcamentoBuffetCompleto(
							rs.getInt("numeroDeConvidados"), rs.getInt("numeroDeColaboradores"),
							rs.getString("horaDeInicio"), dataOrcamento, null, clienteDAO.get(cpfCliente),
							rs.getInt("id"), teraCerveja, salgados, doces, bolo);
					orcamentoBuffetCompletoEncontrados.add(orcamentoBuffetCompletoEncontrado);
				}
				return orcamentoBuffetCompletoEncontrados;
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean criar(OrcamentoBuffetCompleto orcamentoBuffet) {
		String sql = "INSERT INTO orcamentobuffetcompleto VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			// atributos OrcamentoBuffetCompleto no banco
			// id numeroDeConvidados numeroDeColaboradores horaDeInicio data teraCerveja
			// fk_cpfCliente fk_idBolo fk_idPagamento
			int teraCervejaInt;
			if (orcamentoBuffet.getTeraCerveja()) {
				teraCervejaInt = 1;
			} else {
				teraCervejaInt = 0;
			}
			Random random = new Random();
			int id = random.nextInt(2147483646);
			while (existeEssaChavePrimaria(id)) {
				id = random.nextInt(2147483646);
			}
			
			orcamentoBuffet.setId(id);
			
			statement.setInt(1, id);
			statement.setInt(2, orcamentoBuffet.getNumeroDeConvidados());
			statement.setInt(3, orcamentoBuffet.getNumeroDeColaboradores());
			statement.setString(4, orcamentoBuffet.getHoraDeInicio());
			statement.setString(5, orcamentoBuffet.getData().getData());
			statement.setInt(6, teraCervejaInt);
			statement.setString(7, orcamentoBuffet.getCliente().getCpf());
			statement.setInt(8, orcamentoBuffet.getBolo().getId());
			statement.setInt(9, -1);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("OrcamentoBuffetCompleto inserido com sucesso!");
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		} catch (ExcecaoValorNaoSetado e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean atualizar(OrcamentoBuffetCompleto orcamentoBuffet) {
		String sql = "UPDATE orcamentobuffetcompleto SET numeroDeConvidados = ?, numeroDeColaboradores = ?, horaDeInicio = ?, data = ?, teraCerveja = ?, fk_cpfCliente = ?, fk_idBolo = ?, fk_idPagamento = ? WHERE id = ?";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			// atributos OrcamentoBuffetCompleto no banco
			// id numeroDeConvidados numeroDeColaboradores horaDeInicio data teraCerveja
			// fk_cpfCliente fk_idBolo fk_idPagamento
			int teraCervejaInt;
			if (orcamentoBuffet.getTeraCerveja()) {
				teraCervejaInt = 1;
			} else {
				teraCervejaInt = 0;
			}
			statement.setInt(1, orcamentoBuffet.getNumeroDeConvidados());
			statement.setInt(2, orcamentoBuffet.getNumeroDeColaboradores());
			statement.setString(3, orcamentoBuffet.getHoraDeInicio());
			statement.setString(4, orcamentoBuffet.getData().getData());
			statement.setInt(5, teraCervejaInt);
			statement.setString(6, orcamentoBuffet.getCliente().getCpf());
//			statement.setString(6, "");
			statement.setInt(7, orcamentoBuffet.getBolo().getId());
//			statement.setInt(8, orcamentoBuffet.getPagamento().getId());
			statement.setInt(8, -1);
			statement.setInt(9, orcamentoBuffet.getId());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("OrcamentoBuffetCompleto atualizado com sucesso!");
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		} catch (ExcecaoValorNaoSetado e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean deletar(Integer objetoInteger) {
		int id = objetoInteger.intValue();

		DoceSelecionadoDAO doceDAO = new DoceSelecionadoDAO();
		SalgadoSelecionadoDAO salgadoDAO = new SalgadoSelecionadoDAO();

		doceDAO.deletar(Integer.toString(objetoInteger));
		salgadoDAO.deletar(Integer.toString(objetoInteger));

		String sql = "DELETE FROM orcamentobuffetcompleto WHERE id = ?";
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, id);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("OrcamentoBuffetCompleto deletado com sucesso!");
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean verificarData(Data data) {
		String dataString = data.getData();
		
		String sql = "SELECT * FROM orcamentobuffetcompleto WHERE data = ?";
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setString(1, dataString);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return false;
				} else {
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean existeEssaChavePrimaria(Integer chavePrimaria) {
		int chavePrimariaInt = chavePrimaria.intValue();
		String sql = "SELECT * FROM orcamentobuffetcompleto WHERE id = ?";
		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, chavePrimariaInt);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
			return true;
		} catch (ExcecaoValorNaoSetado e) {
			System.out.println(e);
			return true;
		}
	}
}
