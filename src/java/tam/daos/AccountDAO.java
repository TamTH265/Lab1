/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import tam.db.MyConnection;
import tam.supportMethods.EmailSending;

/**
 *
 * @author hoang
 */
public class AccountDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public AccountDAO() {
    }

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public String createAccount(String email, String name, String password, int randomVerifyingCode) throws Exception {
        String role = null;
        try {
            String sql = "insert into Account(Email, Name, Password, Role, Status) values(?, ?, ?, 'Member', 'New')";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setString(2, name);
            preStm.setString(3, password);
            EmailSending es = new EmailSending(email);
            if (preStm.executeUpdate() > 0 && es.sendMail(randomVerifyingCode)) {
                role = "Member";
            }
        } finally {
            closeConnection();
        }

        return role;
    }

    public String handleLogin(String email, String password) throws Exception {
        String role = "failed";
        try {
            String sql = "select Role from Account where Email = ? and Password = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            if (rs.next()) {
                role = rs.getString("Role");
            }
        } finally {
            closeConnection();
        }
        return role;
    }

    public String getLoginName(String email, String password) throws Exception {
        String name = null;

        try {
            String sql = "select Name from Account where Email = ? and Password = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            if (rs.next()) {
                name = rs.getString("Name");
            }
        } finally {
            closeConnection();
        }

        return name;
    }

    public boolean activeAccount(String activateEmail) throws Exception {
        boolean isSuccess = false;

        try {
            String sql = "update Account set Status = 'Activated' where Email = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, activateEmail);
            isSuccess = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return isSuccess;
    }
}
