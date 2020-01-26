package za.co.drivetrek.networth.storage.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import za.co.drivetrek.networth.storage.entity.Transaction;
import za.co.drivetrek.networth.storage.repository.TransactionRepository;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepository mTransactionRepository;
    private LiveData<List<Transaction>> mTransactionList;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        mTransactionRepository = new TransactionRepository(application);
        mTransactionList = mTransactionRepository.getAllTransactions();
    }

    public LiveData<List<Transaction>> getAllTransactions(){
        return mTransactionList;
    }

    public void insert(Transaction transaction){
        mTransactionRepository.insert(transaction);
    }

    public void deleteAll(int transactionId){
        mTransactionRepository.delete(transactionId);
    }
}
