package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addUser(Account user) {
        // Check to make sure username/password requirements are met
        if(user.getUsername().length() > 0 && user.getPassword().length() >= 4) {
            return accountDAO.insertUser(user);
        }
        else {
            return null;
        }
    }

    public Account login(Account user) {
        List<Account> users = getAllAccounts();
        for(Account u : users) {
            // Check to see if username exists
            if(user.getUsername().equals(u.getUsername())) {
                // Check to see if password is correct
                if(user.getPassword().equals(u.getPassword())) {
                    return u;
                }
            }
        }
        // If either username/password were incorrect, return null
        return null;
    }

    public boolean checkUserExists(int postedBy) {
        boolean result = false;
        List<Account> users = getAllAccounts();
        for(Account u : users) {
            if(u.getAccount_id() == postedBy) {
                result = true;
            }
        }
        return result;
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }
}
