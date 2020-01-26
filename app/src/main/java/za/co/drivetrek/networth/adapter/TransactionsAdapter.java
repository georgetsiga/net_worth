package za.co.drivetrek.networth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.drivetrek.networth.R;
import za.co.drivetrek.networth.base.TransactionSummary;
import za.co.drivetrek.networth.storage.entity.Transaction;
import za.co.drivetrek.networth.utils.DateUtil;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private List<Transaction> mTransactions;

    public TransactionsAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.transaction_item_list, parent, false);
        return new TransactionsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        if (!mTransactions.isEmpty()) {
            Transaction transaction = mTransactions.get(position);
            int walletColor =
                    transaction.getType().equals(TransactionSummary.CREDIT) ? R.color.colorRed : R.color.colorGreen;

            holder.wallet.setColorFilter(ContextCompat.getColor(mContext, walletColor),
                    android.graphics.PorterDuff.Mode.SRC_IN);

            holder.amount.setText(mContext.getString(R.string.amount, String.valueOf(transaction.getAmount())));
            holder.transactionDate.setText(DateUtil.getStringDate(transaction.getTransactionDate()));
            holder.description.setText(transaction.getDescription());
        } else {
            holder.wallet.setColorFilter(ContextCompat.getColor(mContext, R.color.colorRed),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public int getItemCount() {
        return (null == mTransactions || mTransactions.isEmpty()) ? 0 : mTransactions.size();
    }

    public void setTransactions(List<Transaction> transactions) {
        mTransactions = transactions;
        notifyDataSetChanged();
    }

    class TransactionsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.wallet_image) AppCompatImageView wallet;
        @BindView(R.id.amount_text) TextView amount;
        @BindView(R.id.transaction_date_text) TextView transactionDate;
        @BindView(R.id.description_text) TextView description;

        public TransactionsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
