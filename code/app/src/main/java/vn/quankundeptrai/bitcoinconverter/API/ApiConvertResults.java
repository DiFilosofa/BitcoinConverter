package vn.quankundeptrai.bitcoinconverter.API;

import java.util.HashMap;

/**
 * Created by TQN on 12/8/2017.
 */

public interface ApiConvertResults {
    void onFinishConvert(HashMap<String,Double> results);
}
