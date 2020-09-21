/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kailainathan
 */
public class LibraryMember extends User{
    
     String dob;
     String address;
     String contactNo;
     MemberStatus memberStatus;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }
    
    public MemberType getMemberType(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate dateOfBirth = LocalDate.parse(this.dob, formatter);
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        
        if(age > 6 && age < 16) {
        return MemberType.KIDS;
        } else if ( age > 18 && age < 55 ) {
            return MemberType.ADULT;
        } else {
            return MemberType.SENIOR;
        }
}
}
