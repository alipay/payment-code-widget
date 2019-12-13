package com.alipay.iap.widget.payment.cgcp.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alipay.iap.widget.payment.cgcp.BarcodeView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BarcodeView barCodeView1 = findViewById(R.id.barcodeView1);
        barCodeView1.setCode("12345678901234567890");

        BarcodeView barCodeView2 = findViewById(R.id.barcodeView2);
        barCodeView2.setCode("12345678901234567890");


        BarcodeView barCodeView3 = findViewById(R.id.barcodeView3);
        barCodeView3.setCode("12345678901234567890");
    }
}
