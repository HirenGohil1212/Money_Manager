package com.example.moneymanager.views.activities;

import static com.example.moneymanager.utils.Constants.SELECTED_TAB;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.moneymanager.adapters.TransactionsAdapter;
import com.example.moneymanager.models.Transaction;
import com.example.moneymanager.utils.Constants;
import com.example.moneymanager.utils.Helper;
import com.example.moneymanager.viewmodel.MainViewModel;
import com.example.moneymanager.views.fragments.AddTransactionFragment;
import com.example.moneymanager.R;
import com.example.moneymanager.views.fragments.TransactionFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import io.realm.Realm;
import io.realm.RealmResults;

    public class MainActivity extends AppCompatActivity {
    com.example.moneymanager.databinding.ActivityMainBinding binding;
    Calendar calendar;

    /*
    0 = Daily
    1 = Monthly
    2 = Calendar
    3 = Summary
    4 = Notes
    */

    public MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.moneymanager.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("Transactions");

        Constants.setCategories();

        calendar = Calendar.getInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new TransactionFragment());
        transaction.commit();


    }

    public void getTransactions() {
        viewModel.getTransactions(calendar);
    }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
            return super.onCreateOptionsMenu(menu);
        }
    }