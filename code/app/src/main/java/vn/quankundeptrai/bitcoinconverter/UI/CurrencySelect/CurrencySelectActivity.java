package vn.quankundeptrai.bitcoinconverter.UI.CurrencySelect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;

import java.util.ArrayList;

import vn.quankundeptrai.bitcoinconverter.API.ApiGetCountryExecuter;
import vn.quankundeptrai.bitcoinconverter.API.ApiGetCurrenciesResults;
import vn.quankundeptrai.bitcoinconverter.API.ApiInterface;
import vn.quankundeptrai.bitcoinconverter.R;

import static vn.quankundeptrai.bitcoinconverter.Constants.MainConstants.CODE_KEY;
import static vn.quankundeptrai.bitcoinconverter.Constants.MainConstants.NAME_KEY;
import static vn.quankundeptrai.bitcoinconverter.Utils.MainUtils.initRetrofit;

/**
 * Created by TQN on 12/8/2017.
 */

public class CurrencySelectActivity extends AppCompatActivity implements RecyclerViewItemSelectInterface,ApiGetCurrenciesResults, TextWatcher {
    private RecyclerView currenciesList;
    private ProgressDialog progressDialog;
    private CurrencyCodeAdapter currencyCodeAdapter;
    private ArrayList<Pair<String,String>> currencyCodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_select);

        currenciesList = findViewById(R.id.currency_code_list);
        EditText codeSearch = findViewById(R.id.code_search);
        codeSearch.addTextChangedListener(this);
        currenciesList.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = ProgressDialog.show(this,"","Loading currencies list");
        ApiInterface apiInterface = initRetrofit();

        new ApiGetCountryExecuter(this, apiInterface).execute("");
    }

    @Override
    public void onItemSelected(Pair<String, String> currencyItem) {
        Intent intent = getIntent();
        intent.putExtra(CODE_KEY,currencyItem.first);
        intent.putExtra(NAME_KEY, currencyItem.second);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onFinishGetCurrencies(ArrayList<Pair<String,String>> results) {
        currencyCodes = results;
        currencyCodeAdapter = new CurrencyCodeAdapter(results,this);
        currenciesList.setAdapter(currencyCodeAdapter);
        progressDialog.hide();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        currencyCodeAdapter.updateList(queryCodes(s));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private ArrayList<Pair<String,String>> queryCodes(CharSequence input){
        ArrayList<Pair<String,String>> result = new ArrayList<>();

        for (Pair<String,String> item : currencyCodes){
            if(item.first.contains(input.toString().toUpperCase())){
                result.add(item);
            }
        }
        return result;
    }
}
