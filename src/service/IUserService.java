/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Member;

/**
 *
 * @author kailainathan
 */
public interface IUserService {
    public Member findById(String id);
    public ArrayList<Member> findByType(String type);
    public String login(String userName, String password)  throws SQLException;
}
