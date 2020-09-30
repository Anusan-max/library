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
import model.LibraryMember;
import model.User;

/**
 *
 * @author kailainathan
 */
public class UserService implements IUserService{
    
    public final UserDao userDao;
    
     public UserService(UserDao userDao) {
        this.userDao = userDao; 
    }

    // findById method needs a string as a parameter 
    // return a member object
    public LibraryMember findById(String id) {
        return userDao.findById(id);
    }
    
     public ArrayList<LibraryMember> findByType(String type) {
        return userDao.findByType(type);
    }

   
  public String login(String userName, String password) throws SQLException{
         return userDao.login(userName, password);
    }
}
