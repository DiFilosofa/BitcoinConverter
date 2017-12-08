package vn.quankundeptrai.bitcoinconverter.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.quankundeptrai.bitcoinconverter.API.ApiInterface;
import vn.quankundeptrai.bitcoinconverter.Constants.MainConstants;

/**
 * Created by TQN on 12/8/2017.
 */

public class MainUtils {
    public static ApiInterface initRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(MainConstants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface.class);
    }
}
