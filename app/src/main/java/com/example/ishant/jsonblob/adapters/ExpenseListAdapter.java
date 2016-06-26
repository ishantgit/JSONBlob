package com.example.ishant.jsonblob.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ishant.jsonblob.R;
import com.example.ishant.jsonblob.models.entities.ExpenseModel;
import com.example.ishant.jsonblob.models.enums.StateType;


import java.util.List;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseItemHolder> {

    private Context context;
    private List<ExpenseModel> expenseList;

    // Interface for Activity to View Holder instance class
    private OnItemClickListener mItemclicklistener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position,StateType stateType);
    }

    public void onItemClickListener(final OnItemClickListener mItemclicklistener )
    {
        this.mItemclicklistener=mItemclicklistener;
    }


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
        holder.amount.setText(context.getString(R.string.Rs) + expenseModel.getAmount());
        holder.description.setText(expenseModel.getDescription());
        holder.state.setText(expenseModel.getState().getValue());
        holder.category.setText(expenseModel.getCategory());
        if(expenseModel.getState() == StateType.VERIFIED){
            holder.verifyButton.setVisibility(View.GONE);
            holder.unverifyButton.setVisibility(View.VISIBLE);
            holder.fraudButton.setVisibility(View.VISIBLE);
        }else if(expenseModel.getState() == StateType.UNVERIFIED){
            holder.verifyButton.setVisibility(View.VISIBLE);
            holder.unverifyButton.setVisibility(View.GONE);
            holder.fraudButton.setVisibility(View.VISIBLE);
        }
        else{
            holder.verifyButton.setVisibility(View.VISIBLE);
            holder.unverifyButton.setVisibility(View.VISIBLE);
            holder.fraudButton.setVisibility(View.GONE);
        }
    }

    public void setData(List<ExpenseModel> data) {
        this.expenseList = data;
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }


    public class ExpenseItemHolder extends RecyclerView.ViewHolder {
        private TextView amount,description,state,category;
        private Button verifyButton,unverifyButton,fraudButton;
        public ExpenseItemHolder(View itemView) {
            super(itemView);
            amount = (TextView)itemView.findViewById(R.id.expense_amount);
            description = (TextView)itemView.findViewById(R.id.expense_des);
            state = (TextView)itemView.findViewById(R.id.expense_state);
            category = (TextView)itemView.findViewById(R.id.expense_category);
            verifyButton = (Button)itemView.findViewById(R.id.verify_button);
            unverifyButton = (Button)itemView.findViewById(R.id.unverify_button);
            fraudButton = (Button)itemView.findViewById(R.id.fraud_button);
            verifyButton.setOnClickListener(verifyButtonClickListener());
            unverifyButton.setOnClickListener(unverifyButtonClickListener());
            fraudButton.setOnClickListener(fraudButtonClickListener());
        }

        private View.OnClickListener fraudButtonClickListener() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mItemclicklistener != null){
                        mItemclicklistener.onItemClick(v,getLayoutPosition(),StateType.FRAUDULENT);
                    }
                }
            };
        }

        private View.OnClickListener unverifyButtonClickListener() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mItemclicklistener != null){
                        mItemclicklistener.onItemClick(v,getLayoutPosition(),StateType.UNVERIFIED);
                    }
                }
            };
        }

        private View.OnClickListener verifyButtonClickListener() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mItemclicklistener != null){
                        mItemclicklistener.onItemClick(v,getLayoutPosition(),StateType.VERIFIED);
                    }
                }
            };
        }

    }
}
