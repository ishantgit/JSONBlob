package com.example.ishant.jsonblob.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ishant.jsonblob.R;
import com.example.ishant.jsonblob.adapters.ExpenseListAdapter;
import com.example.ishant.jsonblob.models.entities.ExpenseModel;
import com.example.ishant.jsonblob.models.responses.ExpenseListResponse;
import com.example.ishant.jsonblob.presenters.MainActivityPresenter;
import com.example.ishant.jsonblob.presenters.MainActivityView;
import com.example.ishant.jsonblob.restApi.RestApiClient;
import com.example.ishant.jsonblob.restApi.RestApiService;
import com.example.ishant.jsonblob.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity implements MainActivityView {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ExpenseListAdapter expenseListAdapter;
    private List<ExpenseModel> expenseList=null;
    private MainActivityPresenter presenter;
    private LinearLayout progressBarLayout;
    private SwipeRefreshLayout swipeContainer;
    private LinearLayout noConnectionLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = setUpToolbar("LIST");
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

    @Override
    public void showExpenseList(List<ExpenseModel> list) {
        expenseList = list;
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
        }
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
}
