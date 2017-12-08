package vn.quankundeptrai.bitcoinconverter.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by TQN on 12/8/2017.
 */

public class CurrencyModelParent {
    @SerializedName("results")
    @Expose
    private HashMap<String,CurrencyModel> body;

    public HashMap<String, CurrencyModel> getBody() {
        return body;
    }
}
