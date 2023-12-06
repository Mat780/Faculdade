package persistencia;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import excecoes.ExcecaoValorNaoSetado;
import negocio.Usuario;

public class UsuarioDAO implements DAO<Usuario, String> {

	@Override
	public Usuario get(String login) {
		String sql = "SELECT * FROM usuario WHERE login = ?";

		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setString(1, login);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					// Usuario possui os seguintes atributos no banco de dados:
					Usuario UsuarioEncontrado = new Usuario(rs.getString("login"), rs.getString("senha"),
							rs.getString("nome"), rs.getString("tipoDeUsuario"));
					return UsuarioEncontrado;
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
	public ArrayList<Usuario> getAll() {
		String sql = "SELECT * FROM usuario";

		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);

			try (ResultSet rs = statement.executeQuery()) {
				ArrayList<Usuario> UsuariosEncontrados = new ArrayList<Usuario>();
				while (rs.next()) {
					Usuario UsuarioEncontrado = new Usuario(rs.getString("login"), rs.getString("senha"),
							rs.getString("nome"), rs.getString("tipoDeUsuario"));
					UsuariosEncontrados.add(UsuarioEncontrado);
				}
				return UsuariosEncontrados;
			}

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		} catch (ExcecaoValorNaoSetado e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean criar(Usuario usuario) {
		// cpf nome RG endereco CEP celular telefoneResidencial telefoneComercial email
		String sql = "INSERT INTO usuario VALUES (?, ?, ?, ?)";
		String hashed = sha256Hash(usuario.getSenha());
		if (existeEssaChavePrimaria(usuario.getLogin())) {
			System.out.println("Cliente com mesmo cpf ja cadastrado");
			return false;
		}
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setString(1, usuario.getLogin());
			statement.setString(2, hashed);
			statement.setString(3, usuario.getNome());
			statement.setString(4, usuario.getTipoDeUsuario());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Usuario inserido com sucesso!");
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
	public boolean atualizar(Usuario usuario) {
		// Não altera a senha

		String sql = "UPDATE usuario SET nome = ?, tipoDeUsuario = ? WHERE login = ?";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);

			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getTipoDeUsuario());
			statement.setString(3, usuario.getLogin());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Usuario atualizado com sucesso!");
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

	public boolean atualizarSenha(String login, String senha) {
		String sql = "UPDATE usuario SET senha = ? WHERE login = ?";
		String hashed = sha256Hash(senha);

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);

			statement.setString(1, hashed);
			statement.setString(2, login);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Usuario atualizado com sucesso!");
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
	public boolean deletar(String login) {
		String sql = "DELETE FROM usuario WHERE login = ?";
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setString(1, login);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Usuario deletado com sucesso!");
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	// hashing the passwd function
	public static String sha256Hash(String input) {
		try {
			// Create a MessageDigest object with the SHA-256 algorithm
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			// Update the digest with the input bytes
			byte[] hash = digest.digest(input.getBytes());

			// Convert the byte array to a hexadecimal string
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				hexString.append(String.format("%02x", b));
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean existeEssaChavePrimaria(String chavePrimaria) {
		String sql = "SELECT * FROM usuario WHERE login = ?";
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
