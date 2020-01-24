package za.co.drivetrek.networth.storage.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import za.co.drivetrek.networth.base.RoomDateConverter;
import za.co.drivetrek.networth.storage.dao.TransactionDao;
import za.co.drivetrek.networth.storage.entity.Transaction;

@Database(entities = {Transaction.class}, version = 1, exportSchema = false)
@TypeConverters({RoomDateConverter.class})
public abstract class TransactionsDataBase extends RoomDatabase {
    public abstract TransactionDao transactionDao();
    private static TransactionsDataBase INSTANCE;

    public static TransactionsDataBase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (TransactionsDataBase.class){
                if (INSTANCE == null){
                    INSTANCE =
                            Room
                                .databaseBuilder(context.getApplicationContext(),
                                    TransactionsDataBase.class, "transactions_database")
                                .fallbackToDestructiveMigration()
                                .build();
                }
            }
        }
        return INSTANCE;
    }
}
