package edu.upc.eetac.dsa.oriol.libreria;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import edu.upc.eetac.dsa.oriol.libreria.api.AppException;
import edu.upc.eetac.dsa.oriol.libreria.api.Book;
import edu.upc.eetac.dsa.oriol.libreria.api.LibreriaAPI;

/**
 * Created by Oriol on 10/05/2015.
 */
public class BookDetailActivity extends Activity{

    private final static String TAG = BookDetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_layout);
        //Ejectuar la tarea en segundo plano para obtener el Sting
        String urlSting = (String) getIntent().getExtras().get("url");
        (new FetchStingTask()).execute(urlSting);
    }

    ////////////////////////////////////////////////////////////////////
    //Método para cargar los datos en la vista
    private void loadBook(Book book) {

        TextView tvDetailTitle = (TextView) findViewById(R.id.tvDetailTitle);
        TextView tvDetailAuthor = (TextView) findViewById(R.id.tvDetailAuthor);
        TextView tvDetailLanguage = (TextView) findViewById(R.id.tvDetailLanguage);
        TextView tvDetailEditionDate = (TextView) findViewById(R.id.tvDetailEditionDate);
        TextView tvDetailId = (TextView) findViewById(R.id.tvDetailId);
        TextView tvDetailEdition = (TextView) findViewById(R.id.tvDetailEdition);
        TextView tvDetailPublisher = (TextView) findViewById(R.id.tvDetailPublisher);
        TextView tvDetailPrintingDate = (TextView) findViewById(R.id.tvDetailPrintingDate);

        tvDetailTitle.setText("Titulo: " + book.getTitle());
        tvDetailAuthor.setText("Autor: " + book.getAuthor());
        tvDetailLanguage.setText("Lenguaje: " + book.getLanguage());
        tvDetailEditionDate.setText("Fecha de edicion: " + book.getEditonDate());
        tvDetailId.setText("ID: " + book.getBookid());
        tvDetailEdition.setText("Edicion: " + book.getEdition());
        tvDetailPublisher.setText("Editorial: " + book.getPublisher());
        tvDetailPrintingDate.setText("Fecha de impresion: " + book.getPrintingDate());
    }
    /////////////////////////////////////////////////////////////////////
    private class FetchStingTask extends AsyncTask<String, Void, Book> {
        private ProgressDialog pd;

        @Override
        protected Book doInBackground(String... params) {
            Book book = null;
            try {
                book = LibreriaAPI.getInstance(BookDetailActivity.this)
                        .getBook(params[0]);
            } catch (AppException e) {
                Log.d(TAG, e.getMessage(), e);
            }
            return book;
        }

        @Override
        protected void onPostExecute(Book result) {
            loadBook(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(BookDetailActivity.this);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }
}
