package edu.upc.eetac.dsa.oriol.llibreria.api.model;

import edu.upc.eetac.dsa.oriol.libreria.api.MediaType;

import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.oriol.libreria.api.BookResource;

public class BookCollection {
	
	@InjectLinks({
	@InjectLink(resource = BookResource.class, style = Style.ABSOLUTE, rel = "create-book", title = "Crear libro", type = MediaType.LIBRERIA_API_BOOK),
	@InjectLink(value = "/books?before={before}", style = Style.ABSOLUTE, rel = "previous", title = "Libros anteriores", type = MediaType.LIBRERIA_API_BOOK_COLLECTION, bindings = { @Binding(name = "before", value = "${instance.firstBook}") }),
	@InjectLink(value = "/books?after={after}", style = Style.ABSOLUTE, rel = "following", title = "Libros posteriores", type = MediaType.LIBRERIA_API_BOOK_COLLECTION, bindings = { @Binding(name = "after", value = "${instance.lastBook}") }) })
	
	
	private List<Link> links;
	private int firstBook;
	private int lastBook;
	
	public void addBook(Book book) { books.add(book); }
	//////////////////////////////////Setters and Getters
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public int getFirstBook() {
		return firstBook;
	}
	public void setFirstBook(int firstBook) {
		this.firstBook = firstBook;
	}
	public int getLastBook() {
		return lastBook;
	}
	public void setLastBook(int lastBook) {
		this.lastBook = lastBook;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	private List<Book> books;

}
