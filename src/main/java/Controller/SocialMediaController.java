package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import kotlin.NotImplementedError;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;

    public SocialMediaController()
    {
        accountService = new AccountService();
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

    private void registerPostHandler(Context context) throws JsonProcessingException{
        //Parse JSON from context

        ObjectMapper mapper = new ObjectMapper();
        Account newAccount = mapper.readValue(context.body(), Account.class);

        Account returnedAccount = accountService.RegisterAccount(newAccount);

        if (returnedAccount != null)
        {
            context.json(mapper.writeValueAsString(returnedAccount));
            context.status(200);
        }
        else
        {
            context.status(400);
        }
    }
    
    private void loginPostHandler(Context context){
        throw new NotImplementedError();
    }

    private void messagesPostHandler(Context context){
        throw new NotImplementedError();
    }

    private void messagesGetHandler(Context context){
        throw new NotImplementedError();
    }
    
    private void messageIDGetHandler(Context context){
        //context.pathParam("message_id");
        throw new NotImplementedError();
    }
    
    private void messageIDDeleteHandler(Context context){
        //context.pathParam("message_id");
        throw new NotImplementedError();
    }
    
    private void messageIDPatchHandler(Context context){
        //context.pathParam("message_id");
        throw new NotImplementedError();
    }
    
    private void accountIDMessagesGetHandler(Context context){
        //context.pathParam("account_id");
        throw new NotImplementedError();
    }
}