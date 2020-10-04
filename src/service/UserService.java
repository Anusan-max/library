/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.UserDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Item;
import model.Member;
import model.User;

/**
 *
 * @author kailainathan
 */
public class UserService implements IUserService{
    
    private final UserDao userDao;
    
     public UserService(UserDao userDao) {
        this.userDao = userDao; 
    }

    // findById method needs a string as a parameter 
    // return a member object
    public Member findById(String id) {
        return userDao.findById(id);
    }
    
     public ArrayList<Member> findByType(String type) {
        return userDao.findByType(type);
    }

   
  public String login(String userName, String password) throws SQLException{
         return userDao.login(userName, password);
    }
}
