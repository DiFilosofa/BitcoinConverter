package vn.quankundeptrai.bitcoinconverter.UI.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import vn.quankundeptrai.bitcoinconverter.API.ApiConvertExecuter;
import vn.quankundeptrai.bitcoinconverter.API.ApiInterface;
import vn.quankundeptrai.bitcoinconverter.API.ApiConvertResults;
import vn.quankundeptrai.bitcoinconverter.R;
import vn.quankundeptrai.bitcoinconverter.UI.CurrencySelect.CurrencySelectActivity;

import static vn.quankundeptrai.bitcoinconverter.Constants.MainConstants.CODE_KEY;
import static vn.quankundeptrai.bitcoinconverter.Constants.MainConstants.INPUT_CODE;
import static vn.quankundeptrai.bitcoinconverter.Constants.MainConstants.NAME_KEY;
import static vn.quankundeptrai.bitcoinconverter.Constants.MainConstants.OUTPUT_CODE;
import static vn.quankundeptrai.bitcoinconverter.Utils.MainUtils.hideKeyboard;
import static vn.quankundeptrai.bitcoinconverter.Utils.MainUtils.initRetrofit;
import static vn.quankundeptrai.bitcoinconverter.Utils.MainUtils.isNetworkOnline;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ApiConvertResults {
    private TextView inputCurrencyCode, outputCurrencyCode, inputCurrencyName, outputCurrencyName;
    private EditText input, output;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private View convert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View selectInputCurrency, selectOutputCurrency, share;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.currencyInput);
        output = findViewById(R.id.currencyOutput);
        inputCurrencyCode = findViewById(R.id.currencyInputCode);
        inputCurrencyName = findViewById(R.id.currencyInputName);
        outputCurrencyCode = findViewById(R.id.currencyOutputCode);
        outputCurrencyName = findViewById(R.id.currencyOutputName);
        selectInputCurrency = findViewById(R.id.allCurrencyInput);
        selectOutputCurrency = findViewById(R.id.allCurrencyOutput);
        convert = findViewById(R.id.convert);
        share = findViewById(R.id.share);

        selectInputCurrency.setOnClickListener(this);
        selectOutputCurrency.setOnClickListener(this);
        convert.setOnClickListener(this);
        share.setOnClickListener(this);

        output.setEnabled(false);

        this.apiInterface = initRetrofit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.allCurrencyInput:
                if(!isNetworkOnline(this)){
                    Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent inputMoney = new Intent(this,CurrencySelectActivity.class);
                startActivityForResult(inputMoney, INPUT_CODE);
                break;

            case R.id.allCurrencyOutput:
                if(!isNetworkOnline(this)){
                    Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent outputMoney = new Intent(this, CurrencySelectActivity.class);
                startActivityForResult(outputMoney, OUTPUT_CODE);
                break;

            case R.id.share:
                Intent sendIntent = new Intent();
                String inputText = input.getText().toString();
                String outputText = output.getText().toString();
                if(inputText.isEmpty() || outputText.isEmpty()){
                    Toast.makeText(this, "Please convert something to share", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        String.format("Today %s %s equals %s %s",
                                inputText,
                                inputCurrencyCode.getText().toString(),
                                outputText,
                                outputCurrencyCode.getText().toString()));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;

            case R.id.convert:
                if(!isNetworkOnline(this)){
                    Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!input.getText().toString().isEmpty()) {
                    hideKeyboard(this);
                    progressDialog = ProgressDialog.show(this, "", "Converting...");
                    String currencyCodes = String.format(
                            Locale.US, "%s_%s", inputCurrencyCode.getText().toString(), outputCurrencyCode.getText().toString());

                    new ApiConvertExecuter(this, apiInterface).execute(currencyCodes);
                }
                break;
        }
    }

    @Override
    public void onFinishConvert(HashMap<String, Double> results) {
        if(results == null){
            Toast.makeText(this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            return;
        }

        String currencyCodes = String.format(
                Locale.US,"%s_%s",inputCurrencyCode.getText().toString(),outputCurrencyCode.getText().toString());

        output.setText(
                NumberFormat.getNumberInstance(Locale.US).format(
                        Double.parseDouble(input.getText().toString())*results.get(currencyCodes)));
        progressDialog.hide();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case INPUT_CODE:
                    inputCurrencyCode.setText(data.getStringExtra(CODE_KEY));
                    inputCurrencyName.setText(data.getStringExtra(NAME_KEY));
                    convert.performClick();
                    break;

                case OUTPUT_CODE:
                    outputCurrencyCode.setText(data.getStringExtra(CODE_KEY));
                    outputCurrencyName.setText(data.getStringExtra(NAME_KEY));
                    convert.performClick();
                    break;
            }
        }
    }
}
