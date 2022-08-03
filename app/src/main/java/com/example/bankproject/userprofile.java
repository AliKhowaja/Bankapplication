package com.example.bankproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class userprofile extends AppCompatActivity {
    TextView name, balance, email, phonenumber, accountno;
    Button deposit, withdraw, transfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        deposit = findViewById(R.id.deposit);
        withdraw = findViewById(R.id.withdraw);
        transfer = findViewById(R.id.transfer);
        showalluserdata();
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Deposit.class));
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Withdraw.class));
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Transfer.class));
            }
        });

    }

    private void showalluserdata() {
        name = findViewById(R.id.username);
        balance = findViewById(R.id.balance);
        email = findViewById(R.id.email);
        phonenumber = findViewById(R.id.phonenumber);
        accountno = findViewById(R.id.accountno);
        Intent intent = getIntent();
        String fullnamea = intent.getStringExtra("Fullname");
        String AccountN0 = intent.getStringExtra("AccountNO");
        String Email = intent.getStringExtra("Email");
        String Mobilephonea = intent.getStringExtra("Mobilephone");
        String bal = intent.getStringExtra("Balance");

        name.setText(fullnamea);
        email.setText(Email);
        accountno.setText(AccountN0);
        phonenumber.setText(Mobilephonea);
        balance.setText(bal);
    }
}