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

    public Account RegisterAccount(Account account)
    {
        return forumDAO.registerAccount(account);
    }
}
