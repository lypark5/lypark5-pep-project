package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    
    private MessageDAO messageDAO;
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // GET ALL MESSAGES
    public List<Message> fetchAllMessages() {
        return messageDAO.getAllMessages();
    }
}
