package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO forumDAO;
    
    public AccountService()
    {
        forumDAO = new AccountDAO();
    }

    public AccountService(AccountDAO forumDAO)
    {
        this.forumDAO = forumDAO;
    }

    public Account registerAccount(Account account)
    {
        return forumDAO.registerAccount(account);
    }

    public Account loginAccount(Account account)
    {
        return forumDAO.loginAccount(account);
    }
}
