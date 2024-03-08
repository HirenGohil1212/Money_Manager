package com.example.moneymanager.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.moneymanager.models.Transaction;
import com.example.moneymanager.utils.Constants;
import java.util.Calendar;
import java.util.Date;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {

    public MutableLiveData<RealmResults<Transaction>> transactions = new MutableLiveData<>();

    public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
    public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
    public MutableLiveData<Double> totalAmount = new MutableLiveData<>();

    Realm realm;
    public MainViewModel(@NonNull Application application) {

        super(application);
        Realm.init(application);
        setupDatabase();
    }



    public void addTransactions(){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE,"Investment","Bank","Some note here", new Date(),-900,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE,"Investment","Bank","Some note here", new Date(),-900,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Rent","Cash","Some note here", new Date(),500,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Business","Cash","Some note here", new Date(),500,new Date().getTime()));

        //some code here
        realm.commitTransaction();
    }

    public void getTransactions(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        RealmResults<Transaction> newTransactions = realm.where(Transaction.class)
                .greaterThanOrEqualTo("date", calendar.getTime())
                .lessThan("date", new Date(calendar.getTime().getTime()+ (24*60*60*1000)))
                .findAll();

        double income = realm.where(Transaction.class)
                .greaterThanOrEqualTo("date", calendar.getTime())
                .lessThan("date", new Date(calendar.getTime().getTime()+ (24*60*60*1000)))
                .equalTo("type",Constants.INCOME)
                        .sum("amount")
                                .doubleValue();

        double expense = realm.where(Transaction.class)
                .greaterThanOrEqualTo("date", calendar.getTime())
                .lessThan("date", new Date(calendar.getTime().getTime()+ (24*60*60*1000)))
                .sum("amount")
                .doubleValue();

        double total = realm.where(Transaction.class)
                .greaterThanOrEqualTo("date", calendar.getTime())
                .lessThan("date", new Date(calendar.getTime().getTime()+ (24*60*60*1000)))
                .equalTo("type",Constants.INCOME)
                .sum("amount")
                .doubleValue();

        totalIncome.setValue(income);
        totalExpense.setValue(expense);
        totalAmount.setValue(total);



//        RealmResults<Transaction> newTransactions = realm.where(Transaction.class)
//                .equalTo("date", calendar.getTime())
//                .findAll();
        transactions.setValue(newTransactions);


    }

    void setupDatabase(){

        realm = Realm.getDefaultInstance();

    }


}
