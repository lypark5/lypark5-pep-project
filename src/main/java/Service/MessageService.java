package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;      // for userIdExists method


import java.util.List;

public class MessageService {
    
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;      // for userIdExists method

    // need to add accountDAO for userIdExists method
    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();    
    }

    // need to add accountDAO for userIdExists method
    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
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

    // VALIDATE MESSAGE
    private void validateMessage(Message message) {
        if (message.getMessage_text().length() == 0) {
            throw new IllegalArgumentException("Message text cannot be empty");
        }

        if (message.getMessage_text().length() > 255) {
            throw new IllegalArgumentException("Message text cannot be more than 255 characters");
        }

        if (accountDAO.userIdExists(message.getPosted_by()) == false) {
            throw new IllegalArgumentException("UserId not found");
        } 
    }

    // CREATE MESSAGE
    public Message addMessage(Message message) {
        // validate message first
        validateMessage(message);

        // then create the message
        return messageDAO.createMessage(message);
    }

}
