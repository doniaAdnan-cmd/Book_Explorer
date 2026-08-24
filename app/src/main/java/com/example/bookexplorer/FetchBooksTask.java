package com.example.bookexplorer;


import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;



public class FetchBooksTask extends AsyncTask<String, Void, ArrayList<Book>> {


    public interface OnBooksLoaded {

        void onSuccess(ArrayList<Book> books);

        void onError();

    }



    private OnBooksLoaded listener;


    public FetchBooksTask(OnBooksLoaded listener){

        this.listener = listener;

    }



    @Override
    protected ArrayList<Book> doInBackground(String... strings) {


        ArrayList<Book> books = new ArrayList<>();


        try {


            URL url = new URL(strings[0]);


            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();


            connection.setRequestMethod("GET");


            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream()
                            )
                    );



            StringBuilder response = new StringBuilder();


            String line;


            while((line = reader.readLine()) != null){

                response.append(line);

            }


            reader.close();



            JSONObject jsonObject =
                    new JSONObject(response.toString());



            JSONArray docs =
                    jsonObject.getJSONArray("docs");




            for(int i = 0; i < docs.length(); i++){


                JSONObject bookObject =
                        docs.getJSONObject(i);



                String title =
                        bookObject.optString(
                                "title",
                                "Unknown Title"
                        );



                String author =
                        "Unknown Author";



                if(bookObject.has("author_name")){


                    JSONArray authors =
                            bookObject.getJSONArray("author_name");


                    author =
                            authors.getString(0);

                }



                String imageUrl = "";



                if(bookObject.has("cover_i")){


                    int coverId =
                            bookObject.getInt("cover_i");



                    imageUrl =
                            "https://covers.openlibrary.org/b/id/"
                                    + coverId
                                    + "-M.jpg";

                }




                String publishedDate =
                        bookObject.optString(
                                "first_publish_year",
                                "Unknown"
                        );


                books.add(
                        new Book(
                                title,
                                author,
                                imageUrl,
                                publishedDate
                        )
                );



            }



        } catch(Exception e){


            Log.e(
                    "BOOK_ERROR",
                    e.toString()
            );


        }



        return books;

    }




    @Override
    protected void onPostExecute(ArrayList<Book> books) {


        super.onPostExecute(books);



        if(books.size() > 0){


            listener.onSuccess(books);


        }else{


            listener.onError();


        }



    }



}