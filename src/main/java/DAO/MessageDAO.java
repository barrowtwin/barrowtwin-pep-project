package DAO;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet pkeyResultSet = preparedStatement.executeQuery();
            if(pkeyResultSet.next()){
                return new Message(id, pkeyResultSet.getInt(2), pkeyResultSet.getString(3), pkeyResultSet.getLong(4));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet pkeyResultSet = preparedStatement.executeQuery();
            while(pkeyResultSet.next()){
                messages.add(new Message(pkeyResultSet.getInt(1), pkeyResultSet.getInt(2), pkeyResultSet.getString(3), pkeyResultSet.getLong(4)));
            }
            return messages;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet pkeyResultSet = preparedStatement.executeQuery();
            if(pkeyResultSet.next()){
                return new Message(id, pkeyResultSet.getInt(2), pkeyResultSet.getString(3), pkeyResultSet.getLong(4));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void updateMessage(int id, String messageText) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, messageText);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List<Message> getUserMessages(int id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet pkeyResultSet = preparedStatement.executeQuery();
            while(pkeyResultSet.next()){
                messages.add(new Message(pkeyResultSet.getInt(1), pkeyResultSet.getInt(2), pkeyResultSet.getString(3), pkeyResultSet.getLong(4)));
            }
            return messages;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
