package com.example.bookexplorer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    private ArrayList<Book> bookList;
    private OnBookClickListener listener;

    public BookAdapter(ArrayList<Book> bookList,
                       OnBookClickListener listener) {

        this.bookList = bookList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull BookViewHolder holder,
            int position) {

        Book book = bookList.get(position);

        holder.title.setText(book.getTitle());

        holder.author.setText(
                "Author: " + book.getAuthor()
        );

        holder.date.setText(
                "Published: " + book.getPublishedDate()
        );

        Glide.with(holder.itemView.getContext())
                .load(book.getImageUrl())
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {

            SelectedBook.selectedBook = book;


            if(v.getContext() instanceof MainActivity){

                ((MainActivity)v.getContext()).openDetails();

            }

            if(v.getContext() instanceof MainActivity){

                ((MainActivity)v.getContext()).openDetails();

            }


        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView author;
        TextView date;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.bookImage);
            title = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.bookAuthor);
            date = itemView.findViewById(R.id.bookDate);
        }
    }
}