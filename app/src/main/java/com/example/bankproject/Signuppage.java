package com.example.bankproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;
import java.util.regex.Pattern;

public class Signuppage extends AppCompatActivity {

    EditText email, newpass, newname, newage, newphone, newaddree, newcountry;
    Button register;
    private FirebaseAuth mAuth;
    private DatabaseReference Ref;
    long AccountN = 15001;
    String AccountNO;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        newname = findViewById(R.id.fullname);
        newpass = findViewById(R.id.pas);
        newage = findViewById(R.id.age);
        newphone = findViewById(R.id.PN);
        newaddree = findViewById(R.id.add);
        newcountry = findViewById(R.id.cty);
        register = findViewById(R.id.regbutt);
        Ref = FirebaseDatabase.getInstance().getReference().child("Users");



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = email.getText().toString().trim();
                String pass = newpass.getText().toString().trim();
                String name = newname.getText().toString().trim();
                String age  = newage.getText().toString().trim();
                String phone = newphone.getText().toString().trim();
                String add = newaddree.getText().toString().trim();
                String country = newcountry.getText().toString().trim();




                if(mail.isEmpty()){
                    email.setError("Email is Required");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.setError("Invalid Email Address");
                    email.requestFocus();
                }
                if(pass.isEmpty()){
                    newpass.setError("Password is Required");
                    newpass.requestFocus();
                    return;
                }
                if(pass.length() < 6){
                    newpass.setError("There Should be 6 Characters in Password");
                    newpass.requestFocus();
                    return;
                }
                if(name.isEmpty()){
                    newname.setError("Enter Full Name");
                    newname.requestFocus();
                    return;
                }
                if(age.isEmpty()){
                    newage.setError("Age is Required");
                    newage.requestFocus();
                    return;
                }
                if(phone.isEmpty()){
                    newphone.setError("Phone is Required");
                    newphone.requestFocus();
                    return;
                }
                if(phone.length() > 11 && phone.length() < 11){
                    newphone.setError("Invalid Phone Number");
                    newphone.requestFocus();
                    return;
                }
                if(add.isEmpty()){
                    newaddree.setError("Address is Required");
                    newaddree.requestFocus();
                    return;
                }
                if(country.isEmpty()){
                    newcountry.setError("Country Name is Required");
                    newcountry.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(mail,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String Balance = "0";
                                    Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            long A = snapshot.getChildrenCount()-1;
                                            AccountN += A;
                                            AccountNO = Long.toString(AccountN);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                      User user = new User(mail,pass,name,age,phone,add,country,Balance,AccountNO);
                                     FirebaseDatabase.getInstance().getReference( "Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful()){
                                                Toast.makeText(Signuppage.this,"User Register Successful!", Toast.LENGTH_LONG).show();
                                             }else{
                                                Toast.makeText(Signuppage.this,"Not Successful!", Toast.LENGTH_LONG).show();
                                             }
                                         }
                                      });
                                }else{
                                    Toast.makeText(Signuppage.this,"Not Successful!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });
    }
}