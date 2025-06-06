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
        if(message.getMessage_text().length() > 0 && message.getMessage_text().length() <= 255) {
            return messageDAO.insertMessage(message);
        }
        else {
            return null;
        }
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

    public Message updateMessage(Message message) {

        messageDAO.updateMessage(id, messageText);
        return
    }
}
