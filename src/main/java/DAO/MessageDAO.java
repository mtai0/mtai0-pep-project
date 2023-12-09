package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;

import org.h2.command.Prepared;


public class MessageDAO {
    public Message postMessage(Message message) {
        //Pre-check message length
        if (message.getMessage_text().length() > 255 || message.getMessage_text().length() <= 0) return null;

        Connection connection = ConnectionUtil.getConnection();
        try {
            //Check if poster is real
            String userCheckQuery = "SELECT * FROM Account WHERE account_id = ?;"; {
                PreparedStatement preparedStatement = connection.prepareStatement(userCheckQuery);
                preparedStatement.setInt(1, message.getPosted_by());

                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) return null;
            }

            //Insert Message
            String insert = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);"; {
                PreparedStatement preparedStatement = connection.prepareStatement(insert);
                preparedStatement.setInt(1, message.getPosted_by());
                preparedStatement.setString(2, message.getMessage_text());
                preparedStatement.setLong(3, message.getTime_posted_epoch());
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected <= 0) return null;

                //Attempt to find message
                String query = "SELECT * FROM Message WHERE posted_by = ? AND message_text = ? AND time_posted_epoch = ?;";
                PreparedStatement queryStatement = connection.prepareStatement(query);
                queryStatement.setInt(1, message.getPosted_by());
                queryStatement.setString(2, message.getMessage_text());
                queryStatement.setLong(3, message.getTime_posted_epoch());
                ResultSet rs = queryStatement.executeQuery();
                if (rs.next())
                {
                    Message toReturn = new Message();
                    toReturn.setMessage_id(rs.getInt("message_id"));
                    toReturn.setPosted_by(rs.getInt("posted_by"));
                    toReturn.setMessage_text(rs.getString("message_text"));
                    toReturn.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                    return toReturn;
                }
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
