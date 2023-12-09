package Service;
import Model.Message;

import java.util.List;

import DAO.MessageDAO;

public class MessageService {
    private MessageDAO messageDAO;
    
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message postMessage(Message message) {
        return messageDAO.postMessage(message);
    }
    
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessagesForUser(String idString) {
        return messageDAO.getAllMessagesForUser(idString);
    }

    public Message getMessageWithID(String idString) {
        return messageDAO.getMessageWithID(idString);
    }

    public Message deleteMessageWithID(String idString) {
        return messageDAO.deleteMessageWithID(idString);
    }

    public Message patchMessage(String idString, String newText) {
        return messageDAO.patchMessage(idString, newText);
    }
}
