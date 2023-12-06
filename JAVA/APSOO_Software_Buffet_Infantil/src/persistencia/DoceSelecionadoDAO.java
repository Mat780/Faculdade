package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import excecoes.ExcecaoValorNaoSetado;
import negocio.Doce;
import negocio.DoceSelecionado;

public class DoceSelecionadoDAO implements DAO<DoceSelecionado, String> {

	@Override
	public DoceSelecionado get(String doceBuffet) {
		throw new IllegalAccessError();
	}

	public DoceSelecionado getDoce(int idDoce, int idBuffet) {
		// A string doceBuffet tem o seguinte formato: 'idDoce idBuffetCompleto'

		String sql = "SELECT * FROM doceselecionado WHERE fk_idDoce = ? AND fk_idOrcamentoBuffetCompleto = ?";

		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, idDoce);
			statement.setInt(2, idBuffet);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					DoceDAO doceDao = new DoceDAO();
					Doce doceEncontrado = doceDao.get(rs.getInt("fk_idDoce"));
					DoceSelecionado doceSelecionadoEncontrado = new DoceSelecionado(rs.getInt("quantidade"),
							doceEncontrado);
					return doceSelecionadoEncontrado;
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
	public ArrayList<DoceSelecionado> getAll() {
		String sql = "SELECT * FROM doceselecionado";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);

			try (ResultSet rs = statement.executeQuery()) {
				ArrayList<DoceSelecionado> docesSelecionadosEncontrados = new ArrayList<DoceSelecionado>();
				DoceDAO doceDao = new DoceDAO();
				while (rs.next()) {
					Doce doceEncontrado = doceDao.get(rs.getInt("fk_idDoce"));
					DoceSelecionado doceSelecionadoEncontrado = new DoceSelecionado(rs.getInt("quantidade"),
							doceEncontrado);
					docesSelecionadosEncontrados.add(doceSelecionadoEncontrado);
				}
				return docesSelecionadosEncontrados;
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	public ArrayList<Doce> getAllDoces() {
		DoceDAO doceDAO = new DoceDAO();
		return doceDAO.getAll();
	}

	public ArrayList<Doce> getAllBuffet(int idBuffet) {
		String sql = "SELECT doce.id,doce.descricao,doce.valorUnitario FROM doceselecionado, doce, orcamentobuffetcompleto WHERE fk_idOrcamentoBuffetCompleto = ? AND fk_idOrcamentoBuffetCompleto = orcamentobuffetcompleto.id AND doceselecionado.fk_idDoce = doce.id;";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, idBuffet);

			try (ResultSet rs = statement.executeQuery()) {
				ArrayList<Doce> docesSelecionadosEncontrados = new ArrayList<Doce>();

				while (rs.next()) {
					Doce doceEncontrado = new Doce(rs.getInt("id"), rs.getString("descricao"),
							rs.getDouble("valorUnitario"));
					docesSelecionadosEncontrados.add(doceEncontrado);
				}
				return docesSelecionadosEncontrados;
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean criar(DoceSelecionado doceSelecionado) {
		throw new IllegalAccessError();
	}

	public boolean criarBuffet(DoceSelecionado doceSelecionado, int idBuffet) {
		String sql = "INSERT INTO doceselecionado VALUES (?, ?, ?)";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, doceSelecionado.getQuantidade());
			statement.setInt(2, doceSelecionado.getDoce().getId());
			statement.setInt(3, idBuffet);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("DoceSelecionado selecionado inserido com sucesso!\n");
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
	public boolean atualizar(DoceSelecionado objeto) {
		throw new IllegalAccessError();
	}

	public boolean atualizarBuffet(DoceSelecionado doceSelecionado, int idBuffet) {
		String sql = "UPDATE doceselecionado SET quantidade = ? WHERE fk_idDoce = ? AND fk_idOrcamentoBuffetCompleto = ?";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, doceSelecionado.getQuantidade());
			statement.setInt(2, doceSelecionado.getDoce().getId());
			statement.setInt(3, idBuffet);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("DoceSelecionado selecionado atualizado com sucesso!\n");
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

	public boolean deletarDoce(int idDoce, int idBuffet) {
		String sql = "DELETE FROM doceselecionado WHERE fk_idOrcamentoBuffetCompleto= ? AND fk_idDoce = ?";
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, idBuffet);
			statement.setInt(2, idDoce);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("DoceSelecionado selecionado(s) deletado(s) com sucesso!\n");
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
