package tmendes.com.analyticalbalancedroid;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Product> prodList;

    private EditText prodKgPrice;
    private EditText prodName;
    private EditText prodWeight;

    private TextView prodIndValue;
    private TextView totalValue;

    private TextWatcher kgTw;
    private TextWatcher wgTw;

    private LinearLayout llProdList;
    private ScrollView scrollView;

    private Float price;
    private Float weight;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prodList = new ArrayList<>();

        price = Float.valueOf(0);
        weight = Float.valueOf(0);

        prodKgPrice = (EditText) findViewById(R.id.editTextKgPrice);
        prodName = (EditText) findViewById(R.id.editTextProdName);
        prodWeight = (EditText) findViewById(R.id.editTextWeight);

        prodIndValue = (TextView) findViewById(R.id.textIndValueData);
        totalValue = (TextView) findViewById(R.id.textTotalValueData);

        llProdList = (LinearLayout) findViewById(R.id.llProdList);

        scrollView = (ScrollView) findViewById(R.id.scroll);

        kgTw = new TextWatcher() {
            public void afterTextChanged(Editable s){
                prodKgPrice.setSelection(s.length());
            }
            public void beforeTextChanged(CharSequence s,int start,int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateValues();
                prodKgPrice.removeTextChangedListener(kgTw);
                prodIndValue.setText(String.format("%.02f", doTheMath()));
                prodKgPrice.addTextChangedListener(kgTw);
            }
        };
        prodKgPrice.addTextChangedListener(kgTw);

        wgTw = new TextWatcher() {
            public void afterTextChanged(Editable s){
                prodWeight.setSelection(s.length());
            }
            public void beforeTextChanged(CharSequence s,int start,int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateValues();
                prodWeight.removeTextChangedListener(wgTw);
                prodIndValue.setText(String.format("%.02f", doTheMath()));
                prodWeight.addTextChangedListener(wgTw);
            }
        };
        prodWeight.addTextChangedListener(wgTw);

    }

    private void updateProductView() {
        if(llProdList.getChildCount() > 0) {
            llProdList.removeAllViews();
        }

        int i = 1;

        for (Product prod : prodList) {

            TextView valueTV = new TextView(this);
            StringBuilder sb = new StringBuilder();
            sb.append("#");
            sb.append(i);
            sb.append(" - ");
            sb.append(prod.toString());
            valueTV.setPadding(5, 3, 0, 3);
            valueTV.setText(sb.toString());

            if ( i % 2 == 0) {
                valueTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTextOne));
            } else {
                valueTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTextTwo));
            }

            llProdList.addView(valueTV);
            scrollView.fullScroll(View.FOCUS_DOWN);

            ++i;
        }
    }

    public void addNewProduct(View v) {
        int prodCounter = prodList.size() + 1;
        Float total;

        if (prodName.getText() != null) {
            if (prodName.length() > 0) {
                name = prodName.getText().toString();
            } else {
                name = "#" + prodCounter;
            }
        }

        float valueToAdd = doTheMath();

        if ((price > 0) && (weight > 0)) {
            Product newProd = new Product(getApplicationContext(), name, weight, price);
            prodList.add(newProd);

            total = Float.valueOf(totalValue.getText().toString()) + valueToAdd;

            totalValue.setText(String.format("%.02f", total));

            updateProductView();
        }
    }

    public void remLastProd(View v) {
        int lastProdPos = prodList.size() - 1;
        if (lastProdPos >= 0) {
            Product removedProd = prodList.remove(lastProdPos);
            float total = Float.valueOf(totalValue.getText().toString()) - removedProd.getInd_price();
            totalValue.setText(String.format("%.02f", total));
            updateProductView();
        }
    }

    public void cleanList(View v) {
        prodList.clear();
        totalValue.setText(getResources().getString(R.string.number));
        if(llProdList.getChildCount() > 0) {
            llProdList.removeAllViews();
        }
    }

    private void updateValues() {
        if (prodKgPrice.getText() != null) {
            if ((prodKgPrice.length() > 0 ) && (prodKgPrice.getText().toString().charAt(0) != '.')) {
                price = Float.valueOf(prodKgPrice.getText().toString());
            }
        }
        if (prodWeight.getText() != null) {
            if ((prodWeight.length()) > 0 && (prodWeight.getText().toString().charAt(0) != '.')) {
                weight = Float.valueOf(prodWeight.getText().toString());
            }
        }
    }

    private Float doTheMath() {
        Float res = Float.valueOf(0);
        if ((price > 0) && (weight > 0)) {
            res = price * weight;
        }
        return res;
    }
}
