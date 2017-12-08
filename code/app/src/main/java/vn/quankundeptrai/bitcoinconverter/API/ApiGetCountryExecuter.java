package vn.quankundeptrai.bitcoinconverter.API;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import vn.quankundeptrai.bitcoinconverter.Models.CurrencyModel;
import vn.quankundeptrai.bitcoinconverter.Models.CurrencyModelParent;

/**
 * Created by TQN on 12/8/2017.
 */

public class ApiGetCountryExecuter extends AsyncTask<String, Void, CurrencyModelParent> {
    private ApiGetCurrenciesResults apiConvertResults;
    private ApiInterface apiInterface;

    public ApiGetCountryExecuter(ApiGetCurrenciesResults apiConvertResults, ApiInterface apiInterface){
        this.apiConvertResults = apiConvertResults;
        this.apiInterface = apiInterface;
    }

    @Override
    protected CurrencyModelParent doInBackground(String... strings) {
        Call<CurrencyModelParent> call = apiInterface.getCountry();
        try {
            CurrencyModelParent body = call.execute().body();
            Log.e("Convert result :", body.toString());
            return body;
        } catch (IOException e) {
            Log.e("API error",e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(CurrencyModelParent result) {
        super.onPostExecute(result);
        ArrayList<Pair<String,String>> res = new ArrayList<>();
        Collection<CurrencyModel> models = result.getBody().values();
        for(CurrencyModel item : models){
            res.add(new Pair<String, String>(item.getId(), item.getCurrencyName()));
        }
        Collections.sort(res, new Comparator<Pair<String, String>>() {
            @Override
            public int compare(Pair<String, String> o1, Pair<String, String> o2) {
                return o1.first.compareTo(o2.first);
            }
        });
        apiConvertResults.onFinishGetCurrencies(res);
    }
}
