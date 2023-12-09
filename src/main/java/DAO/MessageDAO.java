package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MessageDAO {
    public Message postMessage(Message message) {
        //Pre-check message length
        int textLength = message.getMessage_text().length();
        if (textLength> 255 || textLength <= 0) return null;

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
                if (rs.next()) {
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

    public List<Message> getAllMessages() {
        List<Message> toReturn = new ArrayList<Message>();

        Connection connection = ConnectionUtil.getConnection();
        try {
            String query = "SELECT * FROM Message;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                Message m = new Message();
                m.setMessage_id(rs.getInt("message_id"));
                m.setPosted_by(rs.getInt("posted_by"));
                m.setMessage_text(rs.getString("message_text"));
                m.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                toReturn.add(m);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return toReturn;
    }

    public Message getMessageWithID(String idString) {

        //Make sure the id is actually an int
        int messageID = 0;
        try {
            messageID = Integer.parseInt(idString);
        } catch(NumberFormatException e) {
            System.out.println(e.getMessage());
            return null;
        }

        Connection connection = ConnectionUtil.getConnection();
        try {
            String query = "SELECT * FROM Message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, messageID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
            {
                Message m = new Message();
                m.setMessage_id(rs.getInt("message_id"));
                m.setPosted_by(rs.getInt("posted_by"));
                m.setMessage_text(rs.getString("message_text"));
                m.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                return m;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message deleteMessageWithID(String idString) {

        //Make sure the id is actually an int
        int messageID = 0;
        try {
            messageID = Integer.parseInt(idString);
        } catch(NumberFormatException e) {
            System.out.println(e.getMessage());
            return null;
        }

        Connection connection = ConnectionUtil.getConnection();
        try {
            String query = "SELECT * FROM Message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, messageID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
            {
                Message m = new Message();
                m.setMessage_id(rs.getInt("message_id"));
                m.setPosted_by(rs.getInt("posted_by"));
                m.setMessage_text(rs.getString("message_text"));
                m.setTime_posted_epoch(rs.getLong("time_posted_epoch"));

                //Delete the message from the database before returning it
                String deleteString = "DELETE FROM Message WHERE message_id = ?;";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteString);
                deleteStatement.setInt(1, m.getMessage_id());
                deleteStatement.execute();

                return m;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message patchMessage(String idString, String newText)
    {
        //Make sure text length is within limits
        int textLength = newText.length();
        if (textLength > 255 || textLength <= 0) return null;

        //Make sure the id is actually an int
        int messageID = 0;
        try {
            messageID = Integer.parseInt(idString);
        } catch(NumberFormatException e) {
            System.out.println(e.getMessage());
            return null;
        }

        Connection connection = ConnectionUtil.getConnection();
        try {
            String query = "SELECT * FROM Message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, messageID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String update = "UPDATE Message SET message_text = ? WHERE message_id = ?;";
                PreparedStatement updateStatement = connection.prepareStatement(update);
                updateStatement.setString(1, newText);
                updateStatement.setInt(2, messageID);
                int rowsAffected = updateStatement.executeUpdate();
                if (rowsAffected <= 0) return null;

                String query2 = "SELECT * FROM Message WHERE message_id = ?;";
                PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                preparedStatement2.setInt(1, messageID);
                ResultSet rs2 = preparedStatement2.executeQuery();
                if (rs2.next())
                {
                    Message m2 = new Message();
                    m2.setMessage_id(rs2.getInt("message_id"));
                    m2.setPosted_by(rs2.getInt("posted_by"));
                    m2.setMessage_text(rs2.getString("message_text"));
                    m2.setTime_posted_epoch(rs2.getLong("time_posted_epoch"));

                    return m2;
                }
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }


        return null;
    }
}
