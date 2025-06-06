package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        return messageDAO.insertMessage(message);
    }

    public Message getMessage(int id) {
        return messageDAO.getMessage(id);
    }

    public List<Message> getUserMessages(int id) {
        return messageDAO.getUserMessages(id);
    }

    public Message deleteMessage(int id) {
        return messageDAO.deleteMessage(id);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public void updateMessage(int id, String messageText) {
        messageDAO.updateMessage(id, messageText);
    }
}
