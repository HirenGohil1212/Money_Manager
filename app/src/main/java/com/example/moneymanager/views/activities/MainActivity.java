package com.example.moneymanager.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.Menu;
import com.example.moneymanager.adapters.TransactionsAdapter;
import com.example.moneymanager.models.Transaction;
import com.example.moneymanager.utils.Constants;
import com.example.moneymanager.utils.Helper;
import com.example.moneymanager.viewmodel.MainViewModel;
import com.example.moneymanager.views.fragments.AddTransactionFragment;
import com.example.moneymanager.R;

import java.util.Calendar;
import io.realm.Realm;
import io.realm.RealmResults;

    public class MainActivity extends AppCompatActivity {
    com.example.moneymanager.databinding.ActivityMainBinding binding;
    Calendar calendar;
    MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.moneymanager.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);



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


        binding.transactionsList.setLayoutManager(new LinearLayoutManager(this));

        viewModel.transactions.observe(this, new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionsAdapter transactionsAdapter = new TransactionsAdapter(MainActivity.this, transactions);
                binding.transactionsList.setAdapter(transactionsAdapter);
            }
        });

        viewModel.totalIncome.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomeLbl.setText(String.valueOf(aDouble));
            }
        });

        viewModel.totalExpense.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenseLbl.setText(String.valueOf(aDouble));
            }
        });

        viewModel.totalAmount.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totalLbl.setText(String.valueOf(aDouble));
            }
        });
        viewModel.getTransactions(calendar);

    }

    void setupDatabase()
    {
        Realm.init(this);
    }

    void updateDate() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        binding.currentDate.setText(Helper.formateDate(calendar.getTime()));
        viewModel.getTransactions(calendar);

    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
            return super.onCreateOptionsMenu(menu);
        }
    }