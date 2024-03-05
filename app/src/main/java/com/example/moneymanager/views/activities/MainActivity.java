    package com.example.moneymanager.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.moneymanager.utils.Helper;
import com.example.moneymanager.views.fragments.AddTransactionFragment;
import com.example.moneymanager.R;
import com.example.moneymanager.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

    public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Calendar calendar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("Transactions");

        calendar =  Calendar.getInstance();
        updateDate();

        binding.nextDateBtn.setOnClickListener(c-> {
            calendar.add(Calendar.DATE, 1);
            updateDate();
        });

        binding.previousDateBtn.setOnClickListener(c-> {
            calendar.add(Calendar.DATE, -1);
            updateDate();
        });




        binding.floatingActionButton.setOnClickListener(c -> {
            new AddTransactionFragment().show(getSupportFragmentManager(), null);
        });

    }

    void updateDate() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        binding.currentDate.setText(Helper.formateDate(calendar.getTime()));

    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
            return super.onCreateOptionsMenu(menu);
        }
    }