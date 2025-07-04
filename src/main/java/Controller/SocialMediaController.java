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
        Account createdUser = accountService.addUser(user);
        if(createdUser != null) {
            context.json(mapper.writeValueAsString(createdUser));
        }
        else {
            context.status(400);
        }
    }

    private void login(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(context.body(), Account.class);
        Account loggedInUser = accountService.login(user);
        if(loggedInUser != null) {
            context.json(loggedInUser);
        }
        else {
            context.status(401);
        }
    }

    private void createMessage(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int postedBy = message.getPosted_by();
        boolean userExists = accountService.checkUserExists(postedBy);
        if(userExists) {
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
        if(messages != null) {
            context.json(messages);
        }
        else {
            context.json("");
        }
    }

    private void getMessage(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.valueOf(context.pathParam("message_id"));
        Message message = messageService.getMessage(id);
        if(message != null) {
            context.json(mapper.writeValueAsString(message));
        }
        else {
            context.json("");
        }
    }

    private void deleteMessage(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.valueOf(context.pathParam("message_id"));
        Message message = messageService.getMessage(id);
        if(message != null) {
            context.json(mapper.writeValueAsString(message));
        }
        else {
            context.json("");
        }
    }

    private void updateMessage(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.valueOf(context.pathParam("message_id"));
        Message message = mapper.readValue(context.body(), Message.class);
        String messageText = message.getMessage_text();
        if(messageText.length() > 0 && messageText.length() <= 255 && messageService.getMessage(id) != null) {
            messageService.updateMessage(id, messageText);
            Message updatedMessage = messageService.getMessage(id);
            context.json(mapper.writeValueAsString(updatedMessage));
        }
        else {
            context.status(400);
        }
    }

    private void getUserMessages(Context context) {
        int id = Integer.valueOf(context.pathParam("account_id"));
        List<Message> messages = messageService.getUserMessages(id);
        if(messages != null) {
            context.json(messages);
        }
        else {
            context.json("");
        }
    }
}