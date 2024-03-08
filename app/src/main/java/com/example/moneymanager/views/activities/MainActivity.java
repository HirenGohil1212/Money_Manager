    package com.example.moneymanager.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;

import com.example.moneymanager.adapters.TransactionsAdapter;
import com.example.moneymanager.models.Transaction;
import com.example.moneymanager.utils.Constants;
import com.example.moneymanager.utils.Helper;
import com.example.moneymanager.views.fragments.AddTransactionFragment;
import com.example.moneymanager.R;
import com.example.moneymanager.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

        Constants.setCategories();
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

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(Constants.INCOME,"Business","Cash","Some note here", new Date(),500,1));
        transactions.add(new Transaction(Constants.EXPENSE,"Investment","Bank","Some note here", new Date(),900,2));
        transactions.add(new Transaction(Constants.INCOME,"Rent","Cash","Some note here", new Date(),500,3));
        transactions.add(new Transaction(Constants.INCOME,"Business","Cash","Some note here", new Date(),500,4));

        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(this, transactions);
        binding.transactionsList.setLayoutManager(new LinearLayoutManager(this));
        binding.transactionsList.setAdapter(transactionsAdapter);

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