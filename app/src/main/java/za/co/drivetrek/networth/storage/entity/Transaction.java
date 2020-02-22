package za.co.drivetrek.networth.storage.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "transaction")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "description")
    private String mDescription;

    @NonNull
    @ColumnInfo(name = "amount")
    private Double mAmount;

    @NonNull
    @ColumnInfo(name = "type")
    private String mType;

    @ColumnInfo(name = "processed")
    private boolean mProcessed;

    @NonNull
    @ColumnInfo(name = "transactionDate")
    private Date mTransactionDate;

    public Transaction(int id, @NonNull String description,
            @NonNull Double amount, @NonNull String type, boolean processed,
            @NonNull Date transactionDate) {
        this.mId = id;
        this.mDescription = description;
        this.mAmount = amount;
        this.mType = type;
        this.mProcessed = processed;
        this.mTransactionDate = transactionDate;
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    @NonNull
    public Double getAmount() {
        return mAmount;
    }

    @NonNull
    public String getType() {
        return mType;
    }

    public boolean isProcessed() {
        return mProcessed;
    }

    @NonNull
    public Date getTransactionDate() {
        return mTransactionDate;
    }

    @NonNull
    @Override
    public String toString() {
        return this.mType + ": " + mDescription + "(R " + mAmount + "). Processed";
    }
}
