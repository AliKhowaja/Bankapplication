package com.example.bankproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Withdraw extends AppCompatActivity {

    EditText amount;
    Button withdraw;
    private DatabaseReference Ref;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        amount = findViewById(R.id.withdrawamount);
        withdraw = findViewById(R.id.withdrawbut);
        mAuth = FirebaseAuth.getInstance();
        Ref = FirebaseDatabase.getInstance().getReference().child("Users");
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int amt = Integer.parseInt(amount.getText().toString());
                        String id = mAuth.getCurrentUser().getUid();
                        String prebal = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Balance").getValue(String.class);
                        int prebala = Integer.parseInt(prebal);
                        int finalbal = prebala - amt;
                        String Finalbal = String.valueOf(finalbal);
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("Balance",Finalbal);
                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Users/"+id);
                        ref1.updateChildren(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Withdraw.this,"You have Successfully withdrew, Thankyou For Choosing US!.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
}
