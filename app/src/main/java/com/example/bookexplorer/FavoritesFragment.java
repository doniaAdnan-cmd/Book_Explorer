package com.example.bookexplorer;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;



public class FavoritesFragment extends Fragment {


    RecyclerView recyclerView;

    ArrayList<Book> favoriteList;

    BookAdapter adapter;



    public FavoritesFragment(){

        super(R.layout.fragment_favorites);

    }



    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);



        recyclerView =
                view.findViewById(R.id.favoriteRecycler);



        favoriteList = new ArrayList<>();


        adapter = new BookAdapter(
                favoriteList,
                book -> {


                    SelectedBook.selectedBook = book;


                    if(getActivity() instanceof MainActivity){


                        ((MainActivity)getActivity())
                                .openDetails();


                    }


                }
        );
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );



        recyclerView.setAdapter(adapter);



        loadFavorites();


    }





    private void loadFavorites(){


        SharedPreferences preferences =
                requireActivity()
                        .getSharedPreferences(
                                "favorites",
                                0
                        );



        Map<String, ?> favorites =
                preferences.getAll();



        favoriteList.clear();



        for(String title : favorites.keySet()){



            String data =
                    preferences.getString(
                            title,
                            ""
                    );



            String[] parts =
                    data.split("\\|");



            if(parts.length == 3){


                favoriteList.add(
                        new Book(
                                title,
                                parts[0],
                                parts[1],
                                parts[2]
                        )
                );


            }


        }



        adapter.notifyDataSetChanged();


    }




    @Override
    public void onResume() {

        super.onResume();


        loadFavorites();

    }


}