package edu.upc.eetac.dsa.oriol.libreria.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.oriol.llibreria.api.model.Review;

@Path("/reviews")
public class ReviewResource {

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	// ////////////////////////////////////////////////////////
	// Crear review, para enviarle al usuario el resultado hay que crear el
	// recurso getReviewFromDatabase

	// Verificamos que el usuario no ha escrito antes una review del libro

	private String GET_REVIEW_BY_USER = "select * from reviews where username = ? and book = ?";

	private void validateNoPreviousReview(Review review) {

		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_REVIEW_BY_USER);
			stmt.setString(1, review.getUsername());
			stmt.setInt(2, review.getBook());

			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				throw new BadRequestException(
						"Ya has publicado una review para este libro");

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	// Añadimos la review a la bbdd
	private String INSERT_REVIEW_QUERY = "insert into reviews (username, name, book, content) values(?, ?, ?, ?)";

	@POST
	@Consumes(MediaType.LIBRERIA_API_REVIEW)
	@Produces(MediaType.LIBRERIA_API_REVIEW)
	public Review createReview(Review review) {

		validateNoPreviousReview(review); // Miramos que el usuario no haya
											// creado antes una review del mismo
											// libro
		validateReview(review); // Miramos que el texto sea menor a 500
								// caracteres

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_REVIEW_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, review.getUsername());
			stmt.setString(2, review.getName());
			stmt.setInt(3, review.getBook());
			stmt.setString(4, review.getContent());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int reviewid = rs.getInt(1);
				review = getReviewFromDatabase(reviewid);
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return review;
	}

	// ///////////////////////////////////////////////////////////////
	// Método para conseguir review de la bbdd
	//

	private String GET_REVIEW_BY_ID = "select * from reviews where reviewid = ?";

	private Review getReviewFromDatabase(int reviewid) {
		Review review = new Review();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_REVIEW_BY_ID);
			stmt.setInt(1, reviewid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				review.setReviewid(rs.getInt("reviewid"));
				review.setBook(rs.getInt("book"));
				review.setUsername(rs.getString("username"));
				review.setContent(rs.getString("content"));
				review.setName(rs.getString("name"));
				review.setCreationTimestamp(rs.getTimestamp(
						"creation_timestamp").getTime());
				review.setLastModified(rs.getTimestamp("last_modified")
						.getTime());
			} else {
				throw new NotFoundException("There's no review with reviewid="
						+ reviewid);
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return review;
	}

	// //////////////////////////////////////////////////////////////////////////////
	// Actualizar review
	//
	// Validamos que el usuario es el autor de la review (AUTENTICACIÓN)
	private void validateUser(int reviewid) {

		Review review = getReviewFromDatabase(reviewid);
		String username = review.getUsername();

		if (!security.getUserPrincipal().getName().equals(username))
			throw new ForbiddenException(
					"You are not allowed to modify this review.");
	}

	// Validamos la review

	private void validateReview(Review review) {
		if (review.getContent() != null && review.getContent().length() > 500)
			throw new BadRequestException(
					"Content can't be greater than 500 characters.");
	}

	// ///////
	private String UPDATE_REVIEW = "update reviews set content=ifnull(?, content) where reviewid=?";

	@PUT
	@Path("/{reviewid}")
	@Consumes(MediaType.LIBRERIA_API_REVIEW)
	@Produces(MediaType.LIBRERIA_API_REVIEW)
	public Review updateReview(@PathParam("reviewid") int reviewid,
			Review review) {

		validateUser(reviewid);

		validateReview(review);

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_REVIEW);
			stmt.setString(1, review.getContent());
			stmt.setInt(2, Integer.valueOf(reviewid));

			int rows = stmt.executeUpdate();
			if (rows == 1)
				review = getReviewFromDatabase(reviewid);
			else {
				throw new NotFoundException("There's no review with id="
						+ reviewid);
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return review;
	}

	// /////////////////////////////////////////////////////////////
	// Eliminar review
	
	private String DELETE_REVIEW = "delete from reviews where reviewid=?";
	
	@DELETE
	@Path("/{reviewid}")
	public void deleteSting(@PathParam("reviewid") int reviewid) {
		validateUser(reviewid);

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_REVIEW);
			stmt.setInt(1, reviewid);

			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no review with id="
						+ reviewid);
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	// /////////////////////////////////////////////////////////////
	@Context
	private SecurityContext security;
}