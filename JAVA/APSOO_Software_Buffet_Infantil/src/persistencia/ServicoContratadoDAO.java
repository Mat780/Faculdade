package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import excecoes.ExcecaoValorNaoSetado;
import negocio.Salgado;
import negocio.SalgadoSelecionado;
import negocio.ServicoAdicional;
import negocio.ServicoContratado;

public class ServicoContratadoDAO implements DAO<ServicoContratado, Integer> {

	@Override
	public ServicoContratado get(Integer chavePrimaria) {
		throw new IllegalAccessError();
	}

	@Override
	public boolean existeEssaChavePrimaria(Integer chavePrimaria) {
		throw new IllegalAccessError();
	}

	@Override
	public ArrayList<ServicoContratado> getAll() {
		String sql = "SELECT * FROM servicocontratado";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);

			try (ResultSet rs = statement.executeQuery()) {
				ArrayList<ServicoContratado> servicoContratadoEncontrados = new ArrayList<ServicoContratado>();
				ServicoAdicionalDAO servicoAdicionalDAO = new ServicoAdicionalDAO();
				while (rs.next()) {
					ServicoAdicional servicoAdicionalEncontrado = servicoAdicionalDAO
							.get(rs.getInt("fk_idServicoAdicional"));
					ServicoContratado servicoContratadoEncontrado = new ServicoContratado(rs.getInt("quantidade"),
							servicoAdicionalEncontrado);
					servicoContratadoEncontrados.add(servicoContratadoEncontrado);
				}
				return servicoContratadoEncontrados;
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	public ArrayList<ServicoContratado> getAllServCon(int idLocacao) {
		String sql = "SELECT * FROM servicocontratado WHERE fk_idOrcamentoLocacaoDeEspaco = ?";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, idLocacao);

			try (ResultSet rs = statement.executeQuery()) {
				ArrayList<ServicoContratado> servicoContratadoEncontrados = new ArrayList<ServicoContratado>();
				ServicoAdicionalDAO servicoAdicionalDAO = new ServicoAdicionalDAO();
				while (rs.next()) {
					ServicoAdicional servicoAdicionalEncontrado = servicoAdicionalDAO
							.get(rs.getInt("fk_idServicoAdicional"));
					ServicoContratado servicoContratadoEncontrado = new ServicoContratado(rs.getInt("quantidade"),
							servicoAdicionalEncontrado);
					servicoContratadoEncontrados.add(servicoContratadoEncontrado);
				}
				return servicoContratadoEncontrados;
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean criar(ServicoContratado objeto) {
		throw new IllegalAccessError();
	}

	public boolean criarServCon(ServicoContratado objeto, int idLocacao) {
		String sql = "INSERT INTO servicocontratado VALUES (?, ?, ?)";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, objeto.getServicoAdicional().getId());
			statement.setInt(2, idLocacao);
			statement.setInt(3, objeto.getQuantidade());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("ServicoContratado inserido com sucesso!\n");
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
	public boolean atualizar(ServicoContratado objeto) {
		throw new IllegalAccessError();
	}

	public boolean atualizarServCon(ServicoContratado objeto, int idLocacao) {
		String sql = "UPDATE servicocontratado SET quantidade = ? WHERE fk_idServicoAdicional = ? AND fk_idOrcamentoLocacaoDeEspaco = ?";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, objeto.getQuantidade());
			statement.setInt(2, objeto.getServicoAdicional().getId());
			statement.setInt(3, idLocacao);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("ServicoContratado inserido com sucesso!\n");
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
	public boolean deletar(Integer chavePrimaria) {
		throw new IllegalAccessError();
	}

	public boolean deletarServCon(int idLocacao) {
		String sql = "DELETE FROM servicocontratado WHERE fk_idOrcamentoLocacaoDeEspaco = ?";
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, idLocacao);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("ServicoContratado(s) deletado(s) com sucesso!\n");
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

}
