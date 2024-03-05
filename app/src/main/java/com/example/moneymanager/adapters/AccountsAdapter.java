package com.example.moneymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.databinding.RowAccountBinding;
import com.example.moneymanager.models.Account;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>{

    Context context;
    ArrayList<Account> accountArrayList;

    public interface AccountClickListener {
        void onAccountSelected(Account account);
    }

    AccountClickListener accountClickListener;



    public AccountsAdapter(Context context, ArrayList<Account> accountArrayList, AccountClickListener accountClickListener){
            this.context = context;
            this.accountArrayList = accountArrayList;
            this.accountClickListener = accountClickListener;
    }


    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.row_account, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accountArrayList.get(position);
        holder.binding.acountName.setText(account.getAccountName());
        holder.itemView.setOnClickListener(c-> {
            accountClickListener.onAccountSelected(account);
        });

    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder{



        RowAccountBinding binding;
        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowAccountBinding.bind(itemView);

        }


    }

}
