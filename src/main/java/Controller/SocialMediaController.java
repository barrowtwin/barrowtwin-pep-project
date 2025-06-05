package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        app.post("/register", this::registerUser);
        app.get("/login", this::login);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getMessages);
        app.get("/messages/{message_id}", this::getMessage);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getUserMessages);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerUser(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(context.body(), Account.class);
        if(user.getUsername().length() > 0 && user.getPassword().length() >= 4) {
            Account addedUser = accountService.addUser(user);
            if(addedUser!=null){
                context.json(mapper.writeValueAsString(addedUser));
            }
            else {
                context.status(400);
            }
        }
        else {
            context.status(400);
        }
    }

    private void login(Context context) {
        context.json("sample text");
    }

    private void createMessage(Context context) {
        context.json("sample text");
    }

    private void getMessages(Context context) {
        context.json("sample text");
    }

    private void getMessage(Context context) {
        context.json("sample text");
    }

    private void deleteMessage(Context context) {
        context.json("sample text");
    }

    private void updateMessage(Context context) {
        context.json("sample text");
    }

    private void getUserMessages(Context context) {
        context.json("sample text");
    }
}