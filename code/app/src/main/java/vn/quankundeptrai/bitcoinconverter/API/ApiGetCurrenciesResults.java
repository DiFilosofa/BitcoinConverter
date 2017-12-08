package vn.quankundeptrai.bitcoinconverter.API;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by TQN on 12/8/2017.
 */

public interface ApiGetCurrenciesResults {
    void onFinishGetCurrencies(ArrayList<Pair<String,String>> results);
}
