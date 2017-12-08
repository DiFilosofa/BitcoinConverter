package vn.quankundeptrai.bitcoinconverter.UI.CurrencySelect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

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

public class CurrencySelectActivity extends AppCompatActivity implements RecyclerViewItemSelectInterface,ApiGetCurrenciesResults {
    private ApiInterface apiInterface;
    private RecyclerView currenciesList;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_select);

        currenciesList = findViewById(R.id.currency_code_list);
        currenciesList.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = ProgressDialog.show(this,"","Loading currencies list");
        this.apiInterface = initRetrofit();

        new ApiGetCountryExecuter(this,apiInterface).execute("");
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
        currenciesList.setAdapter(new CurrencyCodeAdapter(results,this));
        progressDialog.hide();
    }
}
