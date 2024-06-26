package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
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

    public SocialMediaController()
    {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registerPostHandler);
        app.post("/login", this::loginPostHandler);
        
        app.post("/messages", this::messagesPostHandler);
        app.get("/messages", this::messagesGetHandler);
        app.get("/messages/{message_id}", this::messageIDGetHandler);
        app.delete("/messages/{message_id}", this::messageIDDeleteHandler);
        app.patch("/messages/{message_id}", this::messageIDPatchHandler);
        
        app.get("/accounts/{account_id}/messages", this::accountIDMessagesGetHandler);

        return app;
    }

    private void registerPostHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account newAccount = mapper.readValue(context.body(), Account.class);

        Account returnedAccount = accountService.registerAccount(newAccount);

        if (returnedAccount != null) {
            context.json(returnedAccount);
            context.status(200);
        }
        else {
            context.status(400);
        }
    }
    
    private void loginPostHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account newAccount = mapper.readValue(context.body(), Account.class);

        Account returnedAccount = accountService.loginAccount(newAccount);
        if (returnedAccount != null)
        {
            context.json(returnedAccount);
            context.status(200);
        }
        else
        {
            context.status(401);
        }
    }

    private void messagesPostHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue(context.body(), Message.class);

        Message returnedMessage = messageService.postMessage(newMessage);
        if (returnedMessage != null)
        {
            context.json(returnedMessage);
            context.status(200);
        }
        else
        {
            context.status(400);
        }
    }

    private void messagesGetHandler(Context context) throws JsonProcessingException {
        List<Message> allMessages = messageService.getAllMessages();
        context.json(allMessages);
        context.status(200);
    }
    
    private void messageIDGetHandler(Context context) throws JsonProcessingException {
        Message message = messageService.getMessageWithID(context.pathParam("message_id"));
        if (message != null) {
            context.json(message);
        }
        context.status(200);
    }
    
    private void messageIDDeleteHandler(Context context) throws JsonProcessingException {
        Message message = messageService.deleteMessageWithID(context.pathParam("message_id"));
        if (message != null) {
            context.json(message);
        }
        context.status(200);
    }
    
    private void messageIDPatchHandler(Context context) throws JsonProcessingException {
        String pathParam = context.pathParam("message_id");
        String body = context.body();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        JsonNode textField = node.get("message_text");
        String messageText = textField != null ? textField.asText("") : "";

        Message message = messageService.patchMessage(pathParam, messageText);
        if (message != null) {
            context.json(message);
            context.status(200);
        }
        else {
            context.status(400);
        }
    }
    
    private void accountIDMessagesGetHandler(Context context){
        //context.pathParam("account_id");
        List<Message> messages = messageService.getAllMessagesForUser(context.pathParam("account_id"));
        context.json(messages);
        context.status(200);
    }
}