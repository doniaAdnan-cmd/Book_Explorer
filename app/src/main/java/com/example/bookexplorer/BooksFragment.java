package com.example.bookexplorer;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class BooksFragment extends Fragment {


    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefresh;

    ProgressBar progressBar;

    EditText searchBox;

    Button searchButton;


    ArrayList<Book> bookList;

    BookAdapter adapter;



    public BooksFragment() {

        super(R.layout.fragment_books);

    }



    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);



        recyclerView = view.findViewById(R.id.recyclerBooks);

        progressBar = view.findViewById(R.id.progressBar);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);

        searchBox = view.findViewById(R.id.searchBox);

        searchButton = view.findViewById(R.id.searchButton);



        bookList = new ArrayList<>();



        adapter = new BookAdapter(bookList, book -> {

            SelectedBook.selectedBook = book;


            if(getActivity() instanceof MainActivity){

                ((MainActivity)getActivity()).openDetails();

            }

        });


        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );


        recyclerView.setAdapter(adapter);



        searchButton.setOnClickListener(v -> {


            String query =
                    searchBox.getText()
                            .toString()
                            .trim();



            if(!query.isEmpty()){

                loadBooks(query);

            }else{


                Toast.makeText(
                        getContext(),
                        "Enter book name",
                        Toast.LENGTH_SHORT
                ).show();

            }


        });




        swipeRefresh.setOnRefreshListener(() -> {


            String query =
                    searchBox.getText()
                            .toString()
                            .trim();



            if(query.isEmpty()){

                loadBooks("android");

            }else{

                loadBooks(query);

            }


        });




        loadBooks("android");


    }





    private void loadBooks(String query){


        progressBar.setVisibility(View.VISIBLE);



        String url =

                "https://openlibrary.org/search.json?q="
                        + query.replace(" ", "+");



        new FetchBooksTask(new FetchBooksTask.OnBooksLoaded() {


            @Override
            public void onSuccess(ArrayList<Book> books) {


                progressBar.setVisibility(View.GONE);



                if(swipeRefresh.isRefreshing()){

                    swipeRefresh.setRefreshing(false);

                }



                bookList.clear();


                bookList.addAll(books);



                // Default selected book
                if(!books.isEmpty()){

                    SelectedBook.selectedBook = books.get(0);

                }



                adapter.notifyDataSetChanged();



                Toast.makeText(
                        getContext(),
                        "Books loaded successfully",
                        Toast.LENGTH_SHORT
                ).show();

                NotificationHelper.showNotification(
                        requireContext(),
                        "Books Loaded",
                        "Books loaded successfully"
                );


            }





            @Override
            public void onError() {


                progressBar.setVisibility(View.GONE);



                if(swipeRefresh.isRefreshing()){

                    swipeRefresh.setRefreshing(false);

                }



                Toast.makeText(
                        getContext(),
                        "Failed to load books",
                        Toast.LENGTH_SHORT
                ).show();

                NotificationHelper.showNotification(
                        requireContext(),
                        "Loading Error",
                        "Failed to load books"
                );


            }



        }).execute(url);



    }



}