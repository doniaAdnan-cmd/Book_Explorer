package com.example.bookexplorer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;


public class BookDetailsFragment extends Fragment {


    ImageView detailImage;

    TextView detailTitle;
    TextView detailAuthor;
    TextView detailDate;

    Button favoriteButton;



    public BookDetailsFragment() {

        super(R.layout.fragment_book_details);

    }



    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);



        detailImage = view.findViewById(R.id.detailImage);

        detailTitle = view.findViewById(R.id.detailTitle);

        detailAuthor = view.findViewById(R.id.detailAuthor);

        detailDate = view.findViewById(R.id.detailDate);

        favoriteButton = view.findViewById(R.id.favoriteButton);



        showSelectedBook();



        favoriteButton.setOnClickListener(v -> {


            Book book = SelectedBook.selectedBook;


            if(book == null){

                return;

            }



            SharedPreferences preferences =
                    requireActivity()
                            .getSharedPreferences(
                                    "favorites",
                                    0
                            );



            boolean exists =
                    preferences.contains(
                            book.getTitle()
                    );



            if(exists){


                preferences.edit()
                        .remove(book.getTitle())
                        .apply();



                favoriteButton.setText(
                        "Add to Favorites"
                );


            }else{


                preferences.edit()
                        .putString(
                                book.getTitle(),
                                book.getAuthor()
                                        + "|"
                                        + book.getImageUrl()
                                        + "|"
                                        + book.getPublishedDate()
                        )
                        .apply();



                favoriteButton.setText(
                        "Remove from Favorites"
                );


            }


        });


    }





    private void showSelectedBook(){


        Book book = SelectedBook.selectedBook;



        if(book == null){


            detailTitle.setText("Select a book");

            detailAuthor.setText("Author: -");

            detailDate.setText("Published: -");


            return;

        }



        detailTitle.setText(book.getTitle());


        detailAuthor.setText(
                "Author: " + book.getAuthor()
        );


        detailDate.setText(
                "Published: " + book.getPublishedDate()
        );



        Glide.with(this)

                .load(book.getImageUrl())

                .into(detailImage);



        SharedPreferences preferences =
                requireActivity()
                        .getSharedPreferences(
                                "favorites",
                                0
                        );


        if(preferences.contains(book.getTitle())){

            favoriteButton.setText(
                    "Remove from Favorites"
            );

        }else{

            favoriteButton.setText(
                    "Add to Favorites"
            );

        }


    }



    @Override
    public void onResume() {

        super.onResume();

        showSelectedBook();

    }


}