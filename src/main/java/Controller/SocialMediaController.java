package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

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
        app.post("/login", this::login);
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
            if(addedUser != null) {
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

    private void login(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(context.body(), Account.class);
        List<Account> users = accountService.getAllAccounts();
        boolean userExists = false;
        boolean correctPassword = false;
        for(Account u : users) {
            if(user.getUsername().equals(u.getUsername())) {
                userExists = true;
                if(user.getPassword().equals(u.getPassword())) {
                    correctPassword = true;
                    // May need to change what is sent to this method
                    context.json(u);
                }
            }
        }
        if(userExists == false || correctPassword == false) {
            context.status(401);
        }
    }

    private void createMessage(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        List<Account> users = accountService.getAllAccounts();
        boolean userExists = false;
        for(Account u : users) {
            if(u.getAccount_id() == message.getPosted_by()) {
                userExists = true;
            }
        }
        if(message.getMessage_text().length() > 0 && message.getMessage_text().length() <= 255 && userExists) {
            Message createdMessage = messageService.addMessage(message);
            if(createdMessage != null) {
                context.json(mapper.writeValueAsString(createdMessage));
            }
            else {
                context.status(400);
            }
        }
        else {
            context.status(400);
        }
    }

    private void getMessages(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessage(Context context) {
        int id = Integer.valueOf(context.pathParam("message_id"));
        Message message = messageService.getMessage(id);
        context.json(message);
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