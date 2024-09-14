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

    // GET MESSAGE BY ID
    // this messageId arg is passed in from the uri path in controller layer.
    public Message fetchMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    // GET MESSAGES OF USER
    public List<Message> fetchMessagesOfUser(int userId) {
        return messageDAO.getMessagesOfUser(userId);
    }

}
