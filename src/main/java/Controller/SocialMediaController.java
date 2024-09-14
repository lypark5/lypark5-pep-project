// handlers should have void return type
// JsonProcessingException is usually for serialization of json objects, so 
// usually for post, put, patch, (delete if involves parsing), usually not for get

package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        // app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginUserHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{messageId}", this::getMessageByIdHandler);
        app.get("/accounts/{userId}/messages", this::getMessagesOfUserHandler);
        app.post("/messages", this::addMessageHandler);
        app.delete("/messages/{messageId}", this::removeMessageHandler);


        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }
    private void registerUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // parsing the req body into an Account obj
            Account account = mapper.readValue(ctx.body(), Account.class);
            // validate and add the account
            Account addedAccount = accountService.addAccount(account);
    
            // return the added account as JSON if successful
            if (addedAccount != null) {
                ctx.json(mapper.writeValueAsString(addedAccount));
            } else {
                ctx.status(400);
            }
        } catch (IllegalArgumentException e) {      // must explicitly catch IllegalArgumentExceptions
            ctx.status(400);
        }
    } 


    private void loginUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // parsing the req body into an Account obj
            Account reqAccount = mapper.readValue(ctx.body(), Account.class);
            // validate and get account from service
            Account resAccount = accountService.login(reqAccount.getUsername(), reqAccount.getPassword());

            // return account as JSON if login successful
            if (resAccount != null) {
                ctx.json(resAccount);
            } else {
                ctx.status(401);
            }
        } catch (IllegalArgumentException e) {
            ctx.status(401);
        }
    }


    private void getAllMessagesHandler(Context ctx) {
        ctx.json(messageService.fetchAllMessages());
    }


    private void getMessageByIdHandler(Context ctx) {
        // parse the messageId from the param
        int messageId = Integer.parseInt(ctx.pathParam("messageId"));

        // fetch message using service
        Message chosenMessage = messageService.fetchMessageById(messageId);

        if (chosenMessage != null) {
            ctx.status(200).json(chosenMessage);
        } else {
            ctx.status(200);
        } 
    }

    private void getMessagesOfUserHandler(Context ctx) {
        // parse userId from param
        int userId = Integer.parseInt(ctx.pathParam("userId"));

        // just return the list as is, whether empty or not.
        ctx.json(messageService.fetchMessagesOfUser(userId));
    }

    private void addMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // parsing the req body into a Message obj
            Message message = mapper.readValue(ctx.body(), Message.class);
            // create message
            Message newMessage = messageService.addMessage(message);
            ctx.json(newMessage);
        } catch (IllegalArgumentException e) {
            ctx.status(400);
        }
    }

    private void removeMessageHandler(Context ctx) throws JsonProcessingException {
        // parse messageId from param
        int messageId = Integer.parseInt(ctx.pathParam("messageId"));

        // delete message
        Message deletedMessage = messageService.removeMessage(messageId);

        if (deletedMessage != null) {
            ctx.status(200).json(deletedMessage);
        } else {
            ctx.status(200);
        } 
    }


}