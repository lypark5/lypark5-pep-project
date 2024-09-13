package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {

    private AccountDAO accountDAO;
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    
    public Account addAccount(Account account) {
        // validate account first
        validateAccount(account);

        // then make the account
        return accountDAO.createAccount(account);
    }

    private void validateAccount(Account account) {
        if (account.getUsername() == null) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }

        // check if username exists with DAO
        // if (accountDAO.)
    }
}
