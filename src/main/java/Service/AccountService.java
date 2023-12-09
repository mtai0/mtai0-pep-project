package Service;

import DAO.ForumDAO;
import Model.Account;

public class AccountService {
    private ForumDAO forumDAO;
    
    public AccountService()
    {
        forumDAO = new ForumDAO();
    }

    public AccountService(ForumDAO forumDAO)
    {
        this.forumDAO = forumDAO;
    }

    public Account register(Account account)
    {
        return forumDAO.registerAccount(account);
    }

    public Account login(Account account)
    {
        return forumDAO.loginAccount(account);
    }
}
