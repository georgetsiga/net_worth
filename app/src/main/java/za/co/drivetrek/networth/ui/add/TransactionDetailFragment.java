package za.co.drivetrek.networth.ui.add;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.drivetrek.networth.R;
import za.co.drivetrek.networth.base.TransactionSummary;
import za.co.drivetrek.networth.notifications.utils.Notification;
import za.co.drivetrek.networth.scheduler.NotificationJobService;
import za.co.drivetrek.networth.storage.entity.Transaction;
import za.co.drivetrek.networth.storage.viewmodel.TransactionViewModel;
import za.co.drivetrek.networth.ui.SettingsActivity;
import za.co.drivetrek.networth.utils.Constants;

import java.util.Date;
import java.util.Objects;

import static android.content.Context.JOB_SCHEDULER_SERVICE;
import static za.co.drivetrek.networth.utils.Constants.ACTION_UPDATE_NOTIFICATION;

public class TransactionDetailFragment extends Fragment {

    private TransactionViewModel transactionViewModel;
    private Notification mNotification;
    private JobScheduler mScheduler;
    private NavController mNavController;
    @BindView(R.id.transaction_type_radio) RadioGroup transactionTypeRadio;
    @BindView(R.id.transaction_description) EditText descriptionText;
    @BindView(R.id.transaction_amount) EditText amountText;
    @BindView(R.id.process_button) Button process;

    public static TransactionDetailFragment newInstance() {
        return new TransactionDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        /*mNotification = new Notification(Objects.requireNonNull(getContext()));
        mNotification.createNotificationChannel();
        getContext().registerReceiver(mNotification.getReceiver(), new IntentFilter(ACTION_UPDATE_NOTIFICATION));*/
        mNavController =
                Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.nav_host_fragment);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.transaction_detail_fragment, container, false);
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick(R.id.process_button)
    public void processTransaction(){
        transact();
        amountText.setText("");
        descriptionText.setText("");
       mNavController.navigate(R.id.nav_transactions);
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
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void transact() {
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
            String contentText = transaction.toString();
            scheduleNotification(contentText);
        }
    }

    private void scheduleNotification(String contentText){
        mScheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        PersistableBundle extras = new PersistableBundle();
        extras.putString(Constants.CONTENT_TEXT_EXTRA, contentText);
        extras.putString(Constants.CONTENT_TITLE_EXTRA, "Transaction Details");
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        boolean deviceCharging =
                defaultSharedPreferences.
                        getBoolean(getContext().getString(R.string.pref_key_device_charging), false);
        boolean deviceIdle =
                defaultSharedPreferences.
                        getBoolean(getContext().getString(R.string.pref_key_device_idle), false);

        String networkType =
                defaultSharedPreferences.
                        getString(getContext().getString(R.string.pref_key_network_type), "None");

        int overrideDeadline = defaultSharedPreferences.
                getInt(getContext().getString(R.string.pref_key_override_deadline), 0);


        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
        switch (networkType){
            case "1":
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
            case "2":
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case "3":
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }

        ComponentName serviceName =
                new ComponentName(getActivity().getPackageName(), NotificationJobService.class.getCanonicalName());
        JobInfo.Builder builder = new JobInfo.Builder(Constants.JOB_ID, serviceName);
        builder.setRequiredNetworkType(selectedNetworkOption)
        .setExtras(extras);

        JobInfo jobInfo = builder.build();
        mScheduler.schedule(jobInfo);


        //mNotification.sendNotification(TransactionDetailFragment.class, "Transaction Details", contentText);
    }

    private void cancelNotification(){
        if (mScheduler != null){
            mScheduler.cancelAll();
            mScheduler = null;
            Toast.makeText(getActivity(), "Jobs cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
