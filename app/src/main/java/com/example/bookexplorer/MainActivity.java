package com.example.bookexplorer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager2 viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);



        tabLayout = findViewById(R.id.tabLayout);

        viewPager = findViewById(R.id.viewPager);



        ViewPagerAdapter adapter =
                new ViewPagerAdapter(this);



        viewPager.setAdapter(adapter);



        new TabLayoutMediator(
                tabLayout,
                viewPager,
                (tab, position) -> {


                    if(position == 0){

                        tab.setText("Books");

                    }else if(position == 1){

                        tab.setText("Details");

                    }else{

                        tab.setText("Favorites");

                    }


                }).attach();


    }



    public void openDetails(){

        viewPager.setCurrentItem(1, true);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(
                R.menu.menu,
                menu
        );


        return true;

    }




    // التعامل مع عناصر القائمة

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId() == R.id.menu_about){


            Toast.makeText(
                    this,
                    "Book Explorer App",
                    Toast.LENGTH_SHORT
            ).show();


            return true;

        }


        return super.onOptionsItemSelected(item);

    }


}