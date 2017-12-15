package vn.quankundeptrai.bitcoinconverter.UI.CurrencySelect;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vn.quankundeptrai.bitcoinconverter.R;

/**
 * Created by TQN on 12/8/2017.
 */

public class CurrencyCodeAdapter extends RecyclerView.Adapter<CurrencyCodeAdapter.CurrencyCodeViewHolder> {
    private ArrayList<Pair<String,String>> currencyCodes;
    private RecyclerViewItemSelectInterface itemSelectInterface;

    public CurrencyCodeAdapter(ArrayList<Pair<String,String>> currencyCodes, RecyclerViewItemSelectInterface itemSelectInterface){
        this.currencyCodes = currencyCodes;
        this.itemSelectInterface = itemSelectInterface;
    }

    @Override
    public CurrencyCodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CurrencyCodeViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_currency_code,parent,false));
    }

    @Override
    public void onBindViewHolder(CurrencyCodeViewHolder holder, int position) {
        final Pair<String,String> currencyCode = currencyCodes.get(position);
        holder.currencyCode.setText(currencyCode.first);
        holder.currencyName.setText(currencyCode.second);

        holder.currencyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSelectInterface.onItemSelected(new Pair<String, String>(currencyCode.first,currencyCode.second));
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyCodes.size();
    }

    class CurrencyCodeViewHolder extends RecyclerView.ViewHolder {
        View currencyItem;
        TextView currencyCode , currencyName;
        CurrencyCodeViewHolder(View itemView) {
            super(itemView);
            currencyCode = itemView.findViewById(R.id.currency_code);
            currencyName = itemView.findViewById(R.id.currency_name);
            currencyItem = itemView.findViewById(R.id.currency_item);
        }
    }

    public void updateList(ArrayList<Pair<String,String>> newList){
        this.currencyCodes = newList;
        notifyDataSetChanged();
    }

    public ArrayList<Pair<String,String>> getCodeList(){
        return this.currencyCodes;
    }
}
