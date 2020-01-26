package za.co.drivetrek.networth.ui.add;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.drivetrek.networth.R;
import za.co.drivetrek.networth.base.TransactionSummary;
import za.co.drivetrek.networth.storage.entity.Transaction;
import za.co.drivetrek.networth.storage.viewmodel.TransactionViewModel;

import java.util.Date;

public class TransactionDetailFragment extends Fragment {

    private TransactionViewModel transactionViewModel;
    @BindView(R.id.transaction_type_radio) RadioGroup transactionTypeRadio;
    @BindView(R.id.transaction_description) EditText descriptionText;
    @BindView(R.id.transaction_amount) EditText amountText;

    public static TransactionDetailFragment newInstance() {
        return new TransactionDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.transaction_detail_fragment, container, false);
        transactionViewModel =
                ViewModelProviders.of(this).get(TransactionViewModel.class);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.transactions_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add){
            addTransaction();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addTransaction() {
        int selectedTRanType = transactionTypeRadio.getCheckedRadioButtonId();
        String tranType;
        switch (selectedTRanType) {
            case R.id.credit_radio:
                tranType = TransactionSummary.CREDIT;
                break;
            case R.id.debit_radio:
                tranType = TransactionSummary.DEBIT;
                break;
            default:
                tranType = TransactionSummary.NONE;
                break;
        }

        if (tranType.equals(TransactionSummary.NONE) ||
                TextUtils.isEmpty(descriptionText.getText().toString()) ||
                TextUtils.isEmpty(amountText.getText().toString())){
            Toast.makeText(getContext(), "Please make a valid submission", Toast.LENGTH_SHORT).show();
        } else{

            Transaction transaction =
                    new Transaction(0, descriptionText.getText().toString(),
                            Double.valueOf(amountText.getText().toString()),
                            tranType, false, new Date());

            transactionViewModel.insert(transaction);
            Toast.makeText(getContext(), "Transaction Completed", Toast.LENGTH_SHORT).show();
        }
    }
}
