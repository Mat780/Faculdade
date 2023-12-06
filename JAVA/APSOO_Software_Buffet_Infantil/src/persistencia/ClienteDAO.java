package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import excecoes.ExcecaoValorNaoSetado;
import negocio.Cliente;

public class ClienteDAO implements DAO<Cliente, String> {

	@Override
	public Cliente get(String cpf) {
		String sql = "SELECT * FROM cliente WHERE cpf = ?";

		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setString(1, cpf);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					String rg = rs.getString("RG");
					String telefoneResidencial = rs.getString("telefoneResidencial");
					String telefoneComercial = rs.getString("telefoneComercial");
					
					Cliente clienteEncontrado = new Cliente(rs.getString("nome"), rs.getString("cpf"),
							rs.getString("endereco"), rs.getString("CEP"), rs.getString("celular"),
							rs.getString("email"));

					if (rg != null) clienteEncontrado.setRg(rg);
					if (telefoneResidencial != null) clienteEncontrado.setTelefoneResidencial(telefoneResidencial);
					if (telefoneComercial != null) clienteEncontrado.setTelefoneComercial(telefoneComercial);
					
					return clienteEncontrado;
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
	public ArrayList<Cliente> getAll() {
		String sql = "SELECT * FROM cliente";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);

			try (ResultSet rs = statement.executeQuery()) {
				ArrayList<Cliente> clientesEncontrados = new ArrayList<Cliente>();
				while (rs.next()) {
					Cliente clienteEncontrado = new Cliente(rs.getString("nome"), rs.getString("cpf"),
							rs.getString("endereco"), rs.getString("CEP"), rs.getString("celular"),
							rs.getString("email"), rs.getString("RG"), rs.getString("telefoneResidencial"),
							rs.getString("telefoneComercial"));
					clientesEncontrados.add(clienteEncontrado);
				}
				return clientesEncontrados;
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean criar(Cliente cliente) {
		String sql = "INSERT INTO cliente VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		if (existeEssaChavePrimaria(cliente.getCpf())) {
			System.out.println("Cliente com mesmo cpf ja cadastrado");
			return false;
		}
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setString(1, cliente.getCpf());
			statement.setString(2, cliente.getNome());
			statement.setString(3, cliente.getRg());
			statement.setString(4, cliente.getEndereco());
			statement.setString(5, cliente.getCep());
			statement.setString(6, cliente.getCelular());
			statement.setString(7, cliente.getTelefoneResidencial());
			statement.setString(8, cliente.getTelefoneComercial());
			statement.setString(9, cliente.getEmail());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Cliente inserido com sucesso!");
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
	public boolean atualizar(Cliente cliente) {
		String sql = "UPDATE cliente SET nome = ?, RG = ?, endereco = ?, CEP = ?, celular = ?, telefoneResidencial = ?, telefoneComercial = ?, email = ? WHERE CPF = ?";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setString(1, cliente.getNome());
			statement.setString(2, cliente.getRg());
			statement.setString(3, cliente.getEndereco());
			statement.setString(4, cliente.getCep());
			statement.setString(5, cliente.getCelular());
			statement.setString(6, cliente.getTelefoneResidencial());
			statement.setString(7, cliente.getTelefoneComercial());
			statement.setString(8, cliente.getEmail());
			statement.setString(9, cliente.getCpf());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Cliente atualizado com sucesso!");
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
	public boolean deletar(String cpf) {
		String sql = "DELETE FROM cliente WHERE cpf = ?";
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setString(1, cpf);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Cliente deletado com sucesso!");
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean existeEssaChavePrimaria(String chavePrimaria) {
		String sql = "SELECT * FROM cliente WHERE cpf = ?";
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setString(1, chavePrimaria);

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
