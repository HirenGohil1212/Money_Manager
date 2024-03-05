package com.example.moneymanager.views.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.AccountsAdapter;
import com.example.moneymanager.adapters.CategoryAdapter;
import com.example.moneymanager.databinding.FragmentAddTransactionBinding;
import com.example.moneymanager.databinding.ListDialogBinding;
import com.example.moneymanager.models.Account;
import com.example.moneymanager.models.Category;
import com.example.moneymanager.utils.Helper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AddTransactionFragment extends BottomSheetDialogFragment {



    public AddTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAddTransactionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddTransactionBinding.inflate(inflater);

        binding.incomeBtn.setOnClickListener(v -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.redColor));
        });

        binding.expenseBtn.setOnClickListener(v -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.redColor));
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener((datePicker, year, month, dayOfMonth) -> {

                    //call calnder obj
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                    calendar.set(Calendar.MONTH, datePicker.getMonth());
                    calendar.set(Calendar.YEAR, datePicker.getYear());

                    //format date
                    //SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
                    String dateToShow = Helper.formateDate(calendar.getTime());

                    //set on view
                    binding.date.setText(dateToShow);


                });
                datePickerDialog.show();
            }
        });

        binding.category.setOnClickListener(c -> {
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogBinding.getRoot());


            ArrayList<Category> categories = new ArrayList<>();
            categories.add(new Category("Salary",R.drawable.ic_salary, R.color.category1));
            categories.add(new Category("Business",R.drawable.ic_business, R.color.category2));
            categories.add(new Category("Investment",R.drawable.ic_investment, R.color.category3));
            categories.add(new Category("Loan",R.drawable.ic_loan, R.color.category4));
            categories.add(new Category("Rent",R.drawable.ic_rent, R.color.category5));
            categories.add(new Category("Other",R.drawable.ic_other, R.color.category6));

            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categories, new CategoryAdapter.CategoryClickListener() {
                @Override
                public void onCategoryClicked(Category category) {
                    binding.category.setText(category.getCategoryName());
                    categoryDialog.dismiss();

                }
            });
            dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            dialogBinding.recyclerView.setAdapter(categoryAdapter);

            categoryDialog.show();
        });

        binding.account.setOnClickListener(c->{
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog accountsDialog = new AlertDialog.Builder(getContext()).create();
            accountsDialog.setView(dialogBinding.getRoot());

            ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account(0, "Cash"));
            accounts.add(new Account(0, "Bank"));
            accounts.add(new Account(0, "PayTM"));
            accounts.add(new Account(0, "EasyPaisa"));
            accounts.add(new Account(0, "Other"));

            AccountsAdapter adapter = new AccountsAdapter(getContext(), accounts, new AccountsAdapter.AccountClickListener() {
                @Override
                public void onAccountSelected(Account account) {

                    binding.account.setText(account.getAccountName());
                    accountsDialog.dismiss();

                }
            });
            dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
           // dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            dialogBinding.recyclerView.setAdapter(adapter);


            accountsDialog.show();




        });



        return binding.getRoot();
    }
}