package edu.upc.eetac.dsa.oriol.llibreria.api.model;

import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.oriol.libreria.api.BookResource;
import edu.upc.eetac.dsa.oriol.libreria.api.MediaType;

public class Book {
	
	@InjectLinks({
		 @InjectLink(resource = BookResource.class, style = Style.ABSOLUTE, rel = "books", title = "Colección de libros", type = MediaType.LIBRERIA_API_BOOK),
		 @InjectLink(resource = BookResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Libro", type = MediaType.LIBRERIA_API_BOOK, method = "getBook", bindings = @Binding(name = "title", value = "${instance.title}")) })
		 
	
	private List<Link> links;
	
	private String title;
	private String author;
	private String language;
	private String edition;
	private String editonDate;
	private String printingDate;
	private String publisher;
	
	///////////////////////////////////////////
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
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
	public String getPrintingDate() {
		return printingDate;
	}
	public void setPrintingDate(String printingDate) {
		this.printingDate = printingDate;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
}
