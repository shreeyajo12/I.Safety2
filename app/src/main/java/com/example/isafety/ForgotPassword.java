package com.example.isafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {
    EditText email;
    TextView logIn;
    String emailreset;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = (EditText) findViewById(R.id.editTextEmail);

        findViewById(R.id.resetPassword).setOnClickListener(this);
        findViewById(R.id.signUp).setOnClickListener(this);
        findViewById(R.id.logIn).setOnClickListener(this);

        logIn = (TextView) findViewById(R.id.logIn);

        mAuth = FirebaseAuth.getInstance();

    }
    private void reset(){
        emailreset = email.getText().toString().trim();
        if(emailreset.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(emailreset);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signUp:
                startActivity(new Intent(getApplicationContext(),SignUp.class));
                finish();
                break;
            case R.id.resetPassword:
                reset();
                Toast.makeText(getApplicationContext(),"Email Sent",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logIn:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
        }
    }
}
