package com.example.moneymanager.views.fragments;

import static com.example.moneymanager.utils.Constants.categories;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
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
import com.example.moneymanager.models.Transaction;
import com.example.moneymanager.utils.Constants;
import com.example.moneymanager.utils.Helper;
import com.example.moneymanager.views.activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
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
    Transaction transaction;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddTransactionBinding.inflate(inflater);


        transaction = new Transaction();
        binding.incomeBtn.setOnClickListener(v -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.redColor));

            transaction.setType(Constants.INCOME);
        });

        binding.expenseBtn.setOnClickListener(v -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.redColor));

            transaction.setType(Constants.EXPENSE);
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener((datePicker, year, month, dayOfMonth) -> {

                    //call calender obj
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                    calendar.set(Calendar.MONTH, datePicker.getMonth());
                    calendar.set(Calendar.YEAR, datePicker.getYear());

                    //format date
                    //SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
                    String dateToShow = Helper.formateDate(calendar.getTime());

                    //set on view
                    binding.date.setText(dateToShow);

                    transaction.setDate(calendar.getTime());
                    transaction.setId(calendar.getTime().getTime());


                });
                datePickerDialog.show();
            }
        });

        binding.category.setOnClickListener(c -> {
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogBinding.getRoot());




            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categories, new CategoryAdapter.CategoryClickListener() {
                @Override
                public void onCategoryClicked(Category category) {
                    binding.category.setText(category.getCategoryName());
                    transaction.setCategory(category.getCategoryName());
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
                    transaction.setAccount(account.getAccountName());
                    accountsDialog.dismiss();

                }
            });
            dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
           // dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            dialogBinding.recyclerView.setAdapter(adapter);

            accountsDialog.show();

        });

        binding.saveTransactionBtn.setOnClickListener(c -> {
            double amount = Double.parseDouble(binding.amount.getText().toString());
            String note = binding.note.getText().toString();

            if(transaction.getType().equals(Constants.EXPENSE))
            {
                transaction.setAmount(amount*-1);
            } else {
                transaction.setAmount(amount);
            }

            transaction.setNote(note);

            ((MainActivity)getActivity()).viewModel.addTransaction(transaction);
            ((MainActivity)getActivity()).getTransactions();

            dismiss();

        });
        return binding.getRoot();
    }
}