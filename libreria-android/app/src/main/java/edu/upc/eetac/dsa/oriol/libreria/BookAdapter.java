package edu.upc.eetac.dsa.oriol.libreria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.upc.eetac.dsa.oriol.libreria.api.Book;

/**
 * Created by Oriol on 10/05/2015.
 */

public class BookAdapter extends BaseAdapter {

    //Create two private attributes
    private final ArrayList<Book> data;
    private LayoutInflater inflater;

    //Create a public constructor with two parameters
    public BookAdapter(Context context, ArrayList<Book> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    //returns the total number of rows that would be in the list
    @Override
    public int getCount() {
        return data.size();
    }
    //returns the data model for a given position
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    //returns a unique long value for a given position
    @Override
    public long getItemId(int position) {
        return ((Book) getItem(position)).getBookid();
    }

    //Is used to hold the data in the View objects
    private static class ViewHolder {
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvEditionDate;
    }

    //A ListView shows as many Views fit in the screen
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        //Inflate the layout and Set data (ViewHolder) calling setTag
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_book, null);
            viewHolder = new ViewHolder();

            viewHolder.tvTitle = (TextView) convertView
                    .findViewById(R.id.tvTitle);

            viewHolder.tvAuthor = (TextView) convertView
                    .findViewById(R.id.tvAuthor);

            viewHolder.tvEditionDate = (TextView) convertView
                    .findViewById(R.id.tvEditionDate);

            convertView.setTag(viewHolder);

        //Android is reciclyng the view
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String title = data.get(position).getTitle();
        String author = data.get(position).getAuthor();
        String date = data.get(position).getPrintingDate();

        viewHolder.tvTitle.setText(title);
        viewHolder.tvAuthor.setText(author);
        viewHolder.tvEditionDate.setText(date);
        return convertView;
    }
}