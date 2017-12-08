package vn.quankundeptrai.bitcoinconverter.API;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;

/**
 * Created by TQN on 12/8/2017.
 */

public class ApiConvertExecuter extends AsyncTask<String, Void, HashMap<String,Double>> {
    private ApiConvertResults apiConvertResults;
    private ApiInterface apiInterface;

    public ApiConvertExecuter(ApiConvertResults apiConvertResults, ApiInterface apiInterface){
        this.apiConvertResults = apiConvertResults;
        this.apiInterface = apiInterface;
    }

    @Override
    protected HashMap<String, Double> doInBackground(String... strings) {
        Call<HashMap<String,Double>> call = apiInterface.convert(strings[0]);
        try {
            HashMap<String,Double> result = call.execute().body();
            Log.e("Convert result :", result.toString());
            return result;
        } catch (IOException e) {
            Log.e("API error",e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(HashMap<String, Double> hashMaps) {
        super.onPostExecute(hashMaps);
        apiConvertResults.onFinishConvert(hashMaps);
    }
}
