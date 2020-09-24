/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author kailainathan
 */
public class Item {
    private String title;
    private String code;
    private String author;
    private String publisher;
    private String publishedDate;
    private String isbn;
    private int noOfCopiesToBorrow;
    private int noOfCopiesCurrentlyBorrowed;
    private RentType type;
    private Language language;
    private ItemType itemType;

    public RentType getType() {
        return type;
    }

    public void setType(RentType type) {
        this.type = type;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public RentType getRentType() {
        return type;
    }

    public void setRentType(RentType type) {
        this.type = type;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNoOfCopiesToBorrow() {
        return noOfCopiesToBorrow;
    }

    public void setNoOfCopiesToBorrow(int noOfCopiesToBorrow) {
        this.noOfCopiesToBorrow = noOfCopiesToBorrow;
    }

    public int getNoOfCopiesCurrentlyBorrowed() {
        return noOfCopiesCurrentlyBorrowed;
    }

    public void setNoOfCopiesCurrentlyBorrowed(int noOfCopiesCurrentlyBorrowed) {
        this.noOfCopiesCurrentlyBorrowed = noOfCopiesCurrentlyBorrowed;
    }
    
    
    
    
}
