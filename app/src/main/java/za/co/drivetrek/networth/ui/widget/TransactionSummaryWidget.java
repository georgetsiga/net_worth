package za.co.drivetrek.networth.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.drivetrek.networth.R;
import za.co.drivetrek.networth.base.TransactionSummary;

public class TransactionSummaryWidget extends LinearLayout {

    @BindView(R.id.credit_card_image) AppCompatImageView creditCardImage;
    @BindView(R.id.income_amount_text) TextView incomeAmount;
    @BindView(R.id.expenses_amount_text) TextView expensesAmount;
    @BindView(R.id.balance_amount_text) TextView balanceAmount;

    public TransactionSummaryWidget(Context context) {
        super(context);
        initViews(context);
    }

    public TransactionSummaryWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public TransactionSummaryWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        final LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_transaction_summary, this);
        setOrientation(VERTICAL);
        ButterKnife.bind(this);
    }

    public void display(TransactionSummary transactionSummary, Context context) {
        if (transactionSummary != null) {
            incomeAmount.setText(formatCurrency(transactionSummary.getIncome(), context));
           expensesAmount
                    .setText(formatCurrency(transactionSummary.getExpenses(), context));
            balanceAmount.setText(context.getString(R.string.amount, String.valueOf(transactionSummary.getBalance())));

            int creditIcon =
                    transactionSummary.getIncome() <= transactionSummary.getExpenses()
                            ? R.drawable.ic_miles_dark_gray : R.drawable.ic_miles_d;
            creditCardImage.setImageResource(creditIcon);
        } else {
            incomeAmount.setText(context.getString(R.string.no_amount));
            expensesAmount.setText(context.getString(R.string.no_amount));
            balanceAmount.setText(context.getString(R.string.no_amount));

            creditCardImage.setImageResource(R.drawable.ic_miles_dark_gray);
        }
    }

    private String formatCurrency(Double amount, Context context){
        return context.getString(R.string.amount, String.valueOf(amount));
    }
}
