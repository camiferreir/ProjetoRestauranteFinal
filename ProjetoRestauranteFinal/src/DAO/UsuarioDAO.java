package DAO;

import DTO.UsuarioDTO;
import VIEWS.TelaPrincipal;
import VIEWS.TelaUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UsuarioDAO {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void logar(UsuarioDTO objusuarioDTO) {
        String sql = "select * from tb_usuarios where login=? and senha=?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, objusuarioDTO.getLogin_usuario());
            pst.setString(2, objusuarioDTO.getSenha_usuario());

            rs = pst.executeQuery();
            if (rs.next()) {
                String perfil = rs.getString(5);
                System.out.println(perfil);

                if (perfil.equals("conexao")) {
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);

                    conexao.close();

                } else {
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);

                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario e/ou senha invalidos");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "** tela login **" + e);

        }
    }

    public void InserirUsuario(UsuarioDTO objUsuarioDTO) {
        String sql = "insert into tb_usuarios (id_usuario, usuario, login, senha, perfil)"
                + " values (?, ?, ?, ?, ?)";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_usuario());
            pst.setString(2, objUsuarioDTO.getNome_usuario());
            pst.setString(3, objUsuarioDTO.getLogin_usuario());
            pst.setString(4, objUsuarioDTO.getSenha_usuario());
            pst.setString(5, objUsuarioDTO.getPerfil_usuario());
            int add = pst.executeUpdate();
            if (add > 0) {
                pesquisarAuto();
                pst.close();
                limparCampos();
                JOptionPane.showMessageDialog(null, "Usuario inserido com sucesso!");

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Método inserir " + e);

        }
    }

    public void pesquisar(UsuarioDTO objUsuarioDTO) {
        String sql = "select * from tb_usuarios where id_usuario =?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_usuario());
            rs = pst.executeQuery();
            if (rs.next()) {

                TelaUsuario.txtNomeUsu.setText(rs.getString(2));
                TelaUsuario.txtLoginUsu.setText(rs.getString(3));
                TelaUsuario.txtSenhaUsu.setText(rs.getString(4));
                TelaUsuario.txtPerfilUsu.setText(rs.getString(5));
                conexao.close();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario não cadastrado!");
                limparCampos();

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método Pesquisar" + e);
        }
    }

    public void pesquisarAuto()  {
        String sql = "select * from tb_usuarios";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
           DefaultTableModel model = (DefaultTableModel) TelaUsuario.TbUsuarios.getModel();
           model.setNumRows(0);

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nome = rs.getString("usuario");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                String perfil = rs.getString("perfil");
               model.addRow(new Object[]{id, nome, login, senha, perfil});
            }
            conexao.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método Pesquisar Automático " + e.getMessage());
        }
    }

    public void editar(UsuarioDTO objUsuarioDTO) {
        String sql = "update tb_usuarios set usuario = ?, login = ?, senha = ?, perfil = ? where id_usuario = ?";
        conexao = ConexaoDAO.conector();
        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(5, objUsuarioDTO.getId_usuario());
            pst.setString(4, objUsuarioDTO.getPerfil_usuario());
            pst.setString(3, objUsuarioDTO.getSenha_usuario());
            pst.setString(2, objUsuarioDTO.getLogin_usuario());
            pst.setString(1, objUsuarioDTO.getNome_usuario());
            int add = pst.executeUpdate();
            if (add > 0) {
                JOptionPane.showMessageDialog(null, "Usuário editado com sucesso!");
                pesquisarAuto();
                conexao.close();
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método editar " + e);
        }
    }

    public void deletar(UsuarioDTO objUsuarioDTO) {
        String sql = "delete from tb_usuarios where id_usuario = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_usuario());
            int del = pst.executeUpdate();
            if (del > 0) {
                JOptionPane.showMessageDialog(null, " Usuário deletado com sucesso!");
                pesquisarAuto();
                conexao.close();
                limparCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método deletar " + e);
        }
    }

    public void limparCampos() {
        TelaUsuario.txtIdUsu.setText(null);
        TelaUsuario.txtLoginUsu.setText(null);
        TelaUsuario.txtNomeUsu.setText(null);
        TelaUsuario.txtSenhaUsu.setText(null);
        TelaUsuario.txtPerfilUsu.setText(null);
    }
}
