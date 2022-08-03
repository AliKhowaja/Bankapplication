package com.example.bankproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button signin, signup;
    private FirebaseAuth mAuth;
    private DatabaseReference Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.usr);
        password = findViewById(R.id.pass);
        signin = findViewById(R.id.sign);
        signup = findViewById(R.id.up);
        Ref = FirebaseDatabase.getInstance().getReference().child("Users");
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userlogin();
            }

            private void userlogin() {
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(mail.isEmpty()){
                    email.setError("Email is Required");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.setError("Invalid Email Address");
                    email.requestFocus();
                }
                if(pass.isEmpty()) {
                    password.setError("Password is Required");
                    password.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    String namefromDB = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Fullname").getValue(String.class);
                                    String actnofromDB = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("AccountNO").getValue(String.class);
                                    String mailfromDB = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Email").getValue(String.class);
                                    String phonefromDB = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Mobilephone").getValue(String.class);
                                    String BalancefromDB = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Balance").getValue(String.class);
                                    Intent intent = new Intent(getApplicationContext(),userprofile.class);
                                    intent.putExtra("Fullname",namefromDB);
                                    intent.putExtra("AccountNO",actnofromDB);
                                    intent.putExtra("Email",mailfromDB);
                                    intent.putExtra("Mobilephone",phonefromDB);
                                    intent.putExtra("Balance",BalancefromDB);

                                    startActivity(intent);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else{
                            Toast.makeText(MainActivity.this,"Failed to Login! Please Check Your Credentials", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Signuppage.class));
            }
        });
    }
}