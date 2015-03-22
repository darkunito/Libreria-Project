package edu.upc.eetac.dsa.oriol.libreria.api;

public interface MediaType {//Quin tipus de dades por rebre i/o enviar el servidor
		
	public final static String LIBRERIA_API_USER = "application/vnd.libreria.api.user+json";
	public final static String LIBRERIA_API_USER_COLLECTION = "application/vnd.libreria.api.user.collection+json";
	public final static String LIBRERIA_API_BOOK = "application/vnd.libreria.api.book+json";
	public final static String LIBRERIA_API_BOOK_COLLECTION = "application/vnd.libreria.api.book.collection+json";
	public final static String LIBRERIA_API_AUTHOR = "application/vnd.libreria.api.author+json";
	public final static String LIBRERIA_API_REVIEW = "application/vnd.libreria.api.review+json";
	public final static String LIBRERIA_API_ERROR = "application/vnd.dsa.libreria.error+json";

} 
