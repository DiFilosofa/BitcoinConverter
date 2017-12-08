package vn.quankundeptrai.bitcoinconverter.API;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.quankundeptrai.bitcoinconverter.Constants.MainConstants;
import vn.quankundeptrai.bitcoinconverter.Models.CurrencyModelParent;

/**
 * Created by TQN on 12/8/2017.
 */

public interface ApiInterface {
    @GET(MainConstants.API_CONVERT)
    Call<HashMap<String,Double>> convert(@Query("q") String currenciesCodes);

    @GET(MainConstants.API_ALL_CURRENCIES)
    Call<CurrencyModelParent> getCountry();
}
