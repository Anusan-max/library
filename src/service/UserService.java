/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.UserDao;
import model.Item;
import model.User;

/**
 *
 * @author kailainathan
 */
public class UserService {
    
    public final UserDao userDao;
    
     public UserService() {
        userDao = new UserDao(); 
    }

    // findById method needs a string as a parameter 
    // return a member object
    public User findById(String id) {
        return userDao.findById(id);
    }

  
    
}
