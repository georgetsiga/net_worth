package za.co.drivetrek.networth.storage.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import za.co.drivetrek.networth.storage.entity.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void insert(Transaction transaction);

    @Query("DELETE FROM `transaction`")
    void deleteAll();

    @Query("DELETE FROM `transaction` where id = :transactionId")
    void deleteById(int transactionId);

    @Query("SELECT * FROM `transaction` ORDER BY transactionDate DESC")
    LiveData<List<Transaction>> getAllTransactions();
}
