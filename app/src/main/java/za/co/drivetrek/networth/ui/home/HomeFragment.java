package za.co.drivetrek.networth.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.drivetrek.networth.R;
import za.co.drivetrek.networth.adapter.TransactionsAdapter;
import za.co.drivetrek.networth.base.TransactionSummary;
import za.co.drivetrek.networth.storage.entity.Transaction;
import za.co.drivetrek.networth.storage.viewmodel.TransactionViewModel;
import za.co.drivetrek.networth.ui.widget.TransactionSummaryWidget;

import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private TransactionViewModel transactionViewModel;
    @BindView(R.id.transaction_widget) TransactionSummaryWidget transactionSummaryWidget;
    @BindView(R.id.transaction_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.no_transaction_records) TextView noRecordsText;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        transactionViewModel =
                ViewModelProviders.of(this).get(TransactionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        final TransactionsAdapter adapter = new TransactionsAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        transactionViewModel.getAllTransactions().observe(Objects.requireNonNull(getActivity()), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                adapter.setTransactions(transactions);
                TransactionSummary transactionSummary = new TransactionSummary(transactions);
                transactionSummaryWidget.display(transactionSummary, getContext());

                if (transactions.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    noRecordsText.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noRecordsText.setVisibility(View.GONE);
                }
            }
        });
        return root;
    }
}