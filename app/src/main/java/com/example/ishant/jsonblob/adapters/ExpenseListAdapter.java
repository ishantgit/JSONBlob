package com.example.ishant.jsonblob.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ishant.jsonblob.R;
import com.example.ishant.jsonblob.models.entities.ExpenseModel;

import java.util.List;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseItemHolder> {

    private Context context;
    private List<ExpenseModel> expenseList;

    public ExpenseListAdapter(Context context, List<ExpenseModel> expenseList){
        this.context = context;
        this.expenseList = expenseList;
    }
    @Override
    public ExpenseItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.expense_item, parent, false);
        ExpenseItemHolder expenseItemHolder = new ExpenseItemHolder(v);
        return expenseItemHolder;
    }

    @Override
    public void onBindViewHolder(ExpenseItemHolder holder, int position) {
        ExpenseModel expenseModel = expenseList.get(position);
        holder.amount.setText("" + expenseModel.getAmount());
        holder.description.setText(expenseModel.getDescription());
        holder.state.setText(expenseModel.getState());
    }

    public void setData(List<ExpenseModel> data) {
        this.expenseList = data;
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ExpenseItemHolder extends RecyclerView.ViewHolder {
        private TextView amount,description,state;
        public ExpenseItemHolder(View itemView) {
            super(itemView);
            amount = (TextView)itemView.findViewById(R.id.expense_amount);
            description = (TextView)itemView.findViewById(R.id.expense_des);
            state = (TextView)itemView.findViewById(R.id.expense_state);
        }
    }
}
