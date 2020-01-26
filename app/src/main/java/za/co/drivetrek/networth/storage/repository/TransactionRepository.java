package za.co.drivetrek.networth.storage.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import za.co.drivetrek.networth.storage.dao.TransactionDao;
import za.co.drivetrek.networth.storage.database.TransactionsDataBase;
import za.co.drivetrek.networth.storage.entity.Transaction;

import java.util.List;

public class TransactionRepository {
    private TransactionDao mTransactionDao;
    private LiveData<List<Transaction>> mTransactionList;

    public TransactionRepository(Application application) {
        TransactionsDataBase db = TransactionsDataBase.getDatabase(application);
        mTransactionDao = db.transactionDao();
        mTransactionList = mTransactionDao.getAllTransactions();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return mTransactionList;
    }

    public void insert(Transaction transaction) {
        new insertAsyncTask(mTransactionDao).execute(transaction);
    }

    public void delete(int id) {
        new deleteAsyncTask(mTransactionDao).execute(id);
    }

    private static class insertAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDao mAsyncTaskDao;

        insertAsyncTask(TransactionDao mTransactionDao) {
            mAsyncTaskDao = mTransactionDao;
        }

        @Override
        protected Void doInBackground(final Transaction... transactions) {
            mAsyncTaskDao.insert(transactions[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private TransactionDao mAsyncTaskDao;

        deleteAsyncTask(TransactionDao mTransactionDao) {
            mAsyncTaskDao = mTransactionDao;
        }

        @Override
        protected Void doInBackground(Integer... ids) {
            int transactionId = ids[0];
            if (transactionId > 0) {
                mAsyncTaskDao.deleteById(transactionId);
            } else {
                mAsyncTaskDao.deleteAll();
            }
            return null;
        }
    }
}
