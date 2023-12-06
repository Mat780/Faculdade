package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import excecoes.ExcecaoValorNaoSetado;
import negocio.Salgado;
import negocio.SalgadoSelecionado;

public class SalgadoSelecionadoDAO implements DAO<SalgadoSelecionado, String> {

	@Override
	public SalgadoSelecionado get(String salgado) {
		throw new IllegalAccessError();
	}

	public SalgadoSelecionado getSalgado(int idSalgado, int idBuffet) {

		String sql = "SELECT * FROM salgadoselecionado WHERE fk_idSalg = ? AND fk_idOrcamentoBuffetComp = ?";

		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, idSalgado);
			statement.setInt(2, idBuffet);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					SalgadoDAO salgadoDAO = new SalgadoDAO();
					Salgado salgadoEncontrado = salgadoDAO.get(rs.getInt("fk_idSalg"));
					SalgadoSelecionado salgadoSelecionadoEncontrado = new SalgadoSelecionado(rs.getInt("quantidade"),
							salgadoEncontrado);
					return salgadoSelecionadoEncontrado;
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
	public ArrayList<SalgadoSelecionado> getAll() {
		String sql = "SELECT * FROM salgadoselecionado";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);

			try (ResultSet rs = statement.executeQuery()) {
				ArrayList<SalgadoSelecionado> salgadosSelecionadosEncontrados = new ArrayList<SalgadoSelecionado>();
				SalgadoDAO salgadoDao = new SalgadoDAO();
				while (rs.next()) {
					Salgado salgadoEncontrado = salgadoDao.get(rs.getInt("fk_idSalg"));
					SalgadoSelecionado salgadoSelecionadoEncontrado = new SalgadoSelecionado(rs.getInt("quantidade"),
							salgadoEncontrado);
					salgadosSelecionadosEncontrados.add(salgadoSelecionadoEncontrado);
				}
				return salgadosSelecionadosEncontrados;
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	public ArrayList<Salgado> getAllSalgados() {
		SalgadoDAO salgDAO = new SalgadoDAO();
		return salgDAO.getAll();
	}

	public ArrayList<Salgado> getAllBuffet(int idBuffet) {
		String sql = "SELECT salgado.id, salgado.descricao, salgado.valorUnitario FROM salgadoselecionado, salgado, orcamentobuffetcompleto WHERE fk_idOrcamentoBuffetComp = ? AND fk_idOrcamentoBuffetComp = orcamentobuffetcompleto.id AND salgadoselecionado.fk_idSalg = salgado.id;";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, idBuffet);

			try (ResultSet rs = statement.executeQuery()) {
				ArrayList<Salgado> salgadosSelecionadosEncontrados = new ArrayList<Salgado>();
				while (rs.next()) {

					Salgado salgadoEcontrado = new Salgado(rs.getInt("id"), rs.getString("descricao"),
							rs.getDouble("valorUnitario"));
					salgadosSelecionadosEncontrados.add(salgadoEcontrado);
				}
				return salgadosSelecionadosEncontrados;
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean criar(SalgadoSelecionado objeto) {
		throw new IllegalAccessError();
	}

	public boolean criarBuffet(SalgadoSelecionado salgadoSelecionado, int idBuffet) {
		String sql = "INSERT INTO salgadoselecionado VALUES (?, ?, ?)";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, salgadoSelecionado.getQuantidade());
			statement.setInt(2, salgadoSelecionado.getSalgado().getId());
			statement.setInt(3, idBuffet);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("SalgadoSelecionado inserido com sucesso!\n");
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
	public boolean atualizar(SalgadoSelecionado objeto) {
		throw new IllegalAccessError();
	}

	public boolean atualizarBuffet(SalgadoSelecionado salgadoSelecionado, int idBuffet) {
		String sql = "UPDATE salgadoselecionado SET quantidade = ? WHERE fk_idSalg = ? AND fk_idOrcamentoBuffetComp = ?";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, salgadoSelecionado.getQuantidade());
			statement.setInt(2, salgadoSelecionado.getSalgado().getId());
			statement.setInt(3, idBuffet);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("SalgadoSelecionado atualizado com sucesso!\n");
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
	public boolean deletar(String idBuffetParam) {
		throw new IllegalAccessError();
	}

	public boolean deletarSalgado(int idSalgado, int idBuffet) {
		String sql = "DELETE FROM salgadoselecionado WHERE fk_idOrcamentoBuffetComp= ? AND fk_idSalg = ?";
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, idBuffet);
			statement.setInt(2, idSalgado);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("SalgadoSelecionado(s) deletado(s) com sucesso!\n");
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean existeEssaChavePrimaria(String chavePrimaria) {
		throw new IllegalAccessError();
	}

}
