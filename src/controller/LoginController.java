/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDao;
import service.IUserService;
import service.UserService;

/**
 *
 * @author kailainathan
 */
public class LoginController {
     private final IUserService userService;
     
     public LoginController() {
             this.userService = new UserService(new UserDao());
     }
     
     public IUserService getUserService() {
         return userService;
     }
}
