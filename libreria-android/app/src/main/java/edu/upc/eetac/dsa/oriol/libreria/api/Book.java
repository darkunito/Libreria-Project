package edu.upc.eetac.dsa.oriol.libreria.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oriol on 10/05/2015.
 */
public class Book {

    private String title;
    private int bookid;
    private String author;
    private String language;
    private String edition;
    private String editonDate;
    private String printingDate;
    private String publisher;
    private Map<String, Link> links = new HashMap<String, Link>();

    private String eTag;

    //////////////////////////////////////////////////////

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getEditonDate() {
        return editonDate;
    }

    public void setEditonDate(String editonDate) {
        this.editonDate = editonDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public String getPrintingDate() {
        return printingDate;
    }

    public void setPrintingDate(String printingDate) {
        this.printingDate = printingDate;
    }
}
