package DAO;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;

public class ForumDAO {
    public Account registerAccount(Account account) {
        //Validate username/password meets criteria
        if (account.getUsername().length() <= 0 || account.getPassword().length() < 4) return null;

        Connection connection = ConnectionUtil.getConnection();
        try {

            String selectString = "SELECT * FROM Account WHERE username = ?;"; {
                PreparedStatement preparedStatement = connection.prepareStatement(selectString);
                preparedStatement.setString(1, account.getUsername());
    
                //If there is ANYTHING in the query for the given username, do not register the account and immediately stop.
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) return null;
            }

            //Continue and register the account otherwise.
            String insertString = "INSERT INTO Account (username, password) VALUES (?,?);"; {
                PreparedStatement preparedStatement = connection.prepareStatement(insertString);
                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());
                preparedStatement.executeUpdate();

                //Return the newly-added account
                String findAccount = "SELECT * FROM Account WHERE username = ?;";
                PreparedStatement findStatement = connection.prepareStatement(findAccount);
                findStatement.setString(1, account.getUsername());
                ResultSet rs = findStatement.executeQuery();
                if (rs.next()) {
                    Account toReturn = new Account();
                    toReturn.setAccount_id(rs.getInt("account_id"));
                    toReturn.setUsername(rs.getString("username"));
                    toReturn.setPassword(rs.getString("password"));
                    return toReturn;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account loginAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String selectString = "SELECT * FROM Account WHERE username = ? AND password = ?;"; {
                PreparedStatement preparedStatement = connection.prepareStatement(selectString);
                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());
    
                //If there is ANYTHING in the query for the given username, do not register the account and immediately stop.
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next())
                {
                    Account toReturn = new Account();
                    toReturn.setAccount_id(rs.getInt("account_id"));
                    toReturn.setUsername(rs.getString("username"));
                    toReturn.setPassword(rs.getString("password"));
                    return toReturn;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
