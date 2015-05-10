package edu.upc.eetac.dsa.oriol.libreria;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsa.oriol.libreria.api.AppException;
import edu.upc.eetac.dsa.oriol.libreria.api.Book;
import edu.upc.eetac.dsa.oriol.libreria.api.BookCollection;
import edu.upc.eetac.dsa.oriol.libreria.api.LibreriaAPI;


public class LibreriaMainActivity extends ListActivity {

    private final static String TAG = LibreriaMainActivity.class.toString();

    /*private static final String[] items = { "lorem", "ipsum", "dolor", "sit",
            "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel",
            "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel",
            "erat", "placerat", "ante", "porttitor", "sodales", "pellentesque",
            "augue", "purus" };
    private ArrayAdapter<String> adapter;*/

    ArrayList<Book> booksList;
    BookAdapter adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libreria_main);

        //Instanciación del modelo y del adaptador
        booksList = new ArrayList<Book>();
        adapter = new BookAdapter(this, booksList);
        setListAdapter(adapter);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("oriol", "oriol"
                        .toCharArray());
            }
        });

        /*adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);*/
        (new FetchBooksTask()).execute();
    }
    ///////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_libreria_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    private class FetchBooksTask extends AsyncTask<Void, Void, BookCollection> {
        private ProgressDialog pd;

        @Override
        protected BookCollection doInBackground(Void... params) {
            BookCollection books = null;
            try {
                books = LibreriaAPI.getInstance(LibreriaMainActivity.this)
                        .getBooks();
            } catch (AppException e) {
                e.printStackTrace();
            }
            return books;
        }

        @Override
        /*protected void onPostExecute(BookCollection result) {
            ArrayList<Book> books = new ArrayList<Book>(result.getBooks());
            for (Book s : books) {
                Log.d(TAG, s.getAuthor() + " - " + s.getTitle());
            }
            if (pd != null) {
                pd.dismiss();
            }
        }*/
        protected void onPostExecute(BookCollection result) {
            addBooks(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(LibreriaMainActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }
    ///////////////////////////////////////////////////////////////////////
    private void addBooks(BookCollection books){
        booksList.addAll(books.getBooks());
        adapter.notifyDataSetChanged();
    }
    ///////////////////////////////////////////////////////////////////////
    //Mostrar Detalles del Libro en nueva pantalla
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Book book = booksList.get(position);
        Log.d(TAG, book.getLinks().get("self").getTarget());

        //Arranque de BookDetailActivity
        Intent intent = new Intent(this, BookDetailActivity.class);
        //Pass data through Intent extras
        intent.putExtra("url", book.getLinks().get("self").getTarget());
        startActivity(intent);

    }
    ///////////////////////////////////////////////////////////////////////



}
