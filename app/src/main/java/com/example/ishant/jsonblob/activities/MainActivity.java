package com.example.ishant.jsonblob.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ishant.jsonblob.MyApplication;
import com.example.ishant.jsonblob.R;
import com.example.ishant.jsonblob.adapters.ExpenseListAdapter;
import com.example.ishant.jsonblob.models.entities.ExpenseModel;
import com.example.ishant.jsonblob.models.enums.StateType;
import com.example.ishant.jsonblob.models.responses.ExpenseListResponse;
import com.example.ishant.jsonblob.presenters.MainActivityPresenter;
import com.example.ishant.jsonblob.presenters.MainActivityView;
import com.example.ishant.jsonblob.receivers.TaskListener;
import com.example.ishant.jsonblob.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends BaseActivity implements MainActivityView {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ExpenseListAdapter expenseListAdapter;
    private List<ExpenseModel> expenseList=null;
    private ExpenseListResponse expenseResponse = null;
    private MainActivityPresenter presenter;
    private LinearLayout progressBarLayout;
    private SwipeRefreshLayout swipeContainer;
    private LinearLayout noConnectionLayout;
    private TaskListener.OnPostTokenReceivedListener onPostTokenReceivedListener;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = setUpToolbar("Expenses");

        if(this.onPostTokenReceivedListener == null) {
            this.onPostTokenReceivedListener = new TaskListener.OnPostTokenReceivedListener() {
                @Override
                public void onTokenReceived() {
                    presenter.taskRequest();
                }
            };
        }
        TaskListener.onPostTokenReceivedListener = onPostTokenReceivedListener;
        initialiseUI();
    }

    protected void initialiseUI(){
        progressBarLayout = (LinearLayout)findViewById(R.id.progress_bar_layout);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        noConnectionLayout = (LinearLayout)findViewById(R.id.no_connection_layout);
        Button retry = (Button) noConnectionLayout.findViewById(R.id.retry_button);
        retry.setOnClickListener(retryConnectionListener());
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.retryRequest();
            }
        });
        presenter = new MainActivityPresenter(this);
        progressDialog = new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("VERIFYING");
        progressDialog.setCancelable(false);
        presenter.onResume();
    }

    private View.OnClickListener retryConnectionListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onResume();
            }
        };
    }

    public void scheduleAlarm() {

        Intent intent = new Intent(getApplicationContext(), TaskListener.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, TaskListener.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        //task scheduled for every 30 seconds
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                1000*30, pIntent);
    }

    @Override
    public void showExpenseList(ExpenseListResponse expenseListResponse) {
        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        expenseList = expenseListResponse.getExpenseList();
        expenseResponse = expenseListResponse;
        if(expenseListAdapter!=null){
            expenseListAdapter.setData(expenseList);
            expenseListAdapter.notifyDataSetChanged();
            if(swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
        }else{
            if(expenseList == null){
                expenseList = new ArrayList<>();
            }
            expenseListAdapter = new ExpenseListAdapter(this,expenseList);
            recyclerView.setAdapter(expenseListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this));
            expenseListAdapter.onItemClickListener(getExpenseVerifyClickListener());
        }
    }



    private ExpenseListAdapter.OnItemClickListener getExpenseVerifyClickListener() {
        final Activity activity = this;
        return new ExpenseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position,StateType state) {
                if(expenseResponse != null){
                    ExpenseListResponse responseToSend = new ExpenseListResponse();
                    if(progressDialog == null){
                        progressDialog= new ProgressDialog(activity,ProgressDialog.STYLE_SPINNER);
                        progressDialog.setCancelable(false);
                    }
                    progressDialog.setMessage(state.getValue());
                    progressDialog.show();
                    List<ExpenseModel> list = expenseResponse.getExpenseList();
                    ExpenseModel model = list.get(position);
                    model.setState(state);
                    list.remove(position);
                    list.add(position,model);
                    responseToSend.setExpenseList(list);
                    presenter.verifyExpenseObject(responseToSend);
                }
            }
        };
    }

    @Override
    public void showProgress() {
        progressBarLayout.setVisibility(View.VISIBLE);
        noConnectionLayout.setVisibility(View.GONE);
        swipeContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBarLayout.setVisibility(View.GONE);
        swipeContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoConnectionView() {
        progressBarLayout.setVisibility(View.GONE);
        noConnectionLayout.setVisibility(View.VISIBLE);
        swipeContainer.setVisibility(View.GONE);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.i("state","onResume");
        MyApplication.enableReceiver();
        scheduleAlarm();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("state","onPause");
        MyApplication.disableReceiver();
        cancelAlarm();
    }



    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), TaskListener.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, TaskListener.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }

}
