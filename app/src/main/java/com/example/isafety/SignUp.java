package com.example.isafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    EditText editTextEmail, editTextPassword;
    String first_name,last_name,phn;
    String user_type="0";
    EditText fname, lname, phonenumber;
    private String uid;
    private RadioGroup usertype;
    private RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        fname = (EditText) findViewById(R.id.firstName);
        lname = (EditText) findViewById(R.id.LastName);
        phonenumber = (EditText) findViewById(R.id.mobile);
        usertype = (RadioGroup)findViewById(R.id.usertype);
        usertype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton =(RadioButton)findViewById(i);
                switch (radioButton.getId()){
                    case R.id.user:
                        user_type="0";
                        break;
                    case R.id.admin:
                        user_type=null;
                        break;
                }
            }
        });


        findViewById(R.id.signUp).setOnClickListener(this);
        findViewById(R.id.login_page).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signUp:
                registerUser();
                break;
            case R.id.login_page:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
        }
    }
    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        first_name = fname.getText().toString().trim();
        last_name = lname.getText().toString().trim();
        phn = phonenumber.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (first_name.isEmpty()) {
            fname.setError("First name is required");
            fname.requestFocus();
            return;
        }

        if (last_name.isEmpty()) {
            lname.setError("Last name is required");
            lname.requestFocus();
            return;
        }

        if (phn.isEmpty()) {
            phonenumber.setError("Phone number is required");
            phonenumber.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextPassword.setError("Minimum length of password is 6");
            editTextPassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User Registered Successful",Toast.LENGTH_SHORT).show();
                    data();
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"You are already registered",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    private void data(){
        uid = FirebaseAuth.getInstance().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        SignUpData signUpData = new SignUpData(first_name,last_name,user_type,phn);
        databaseReference.child("Profile").child(uid).setValue(signUpData);
        if(user_type.equals("0")) {
            Intent home = new Intent(getApplicationContext(), HomePage.class);
            startActivity(home);
            finish();
        }
        if(user_type.equals("1")) {
            Intent home = new Intent(getApplicationContext(), AdminHomePage.class);
            startActivity(home);
            finish();
        }

    }
}
