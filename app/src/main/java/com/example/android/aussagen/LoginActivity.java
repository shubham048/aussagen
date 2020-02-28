package com.example.android.aussagen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button LoginButton, PhoneLoginButton;
    private TextView UserEmail, UserPassword;
    private TextView NeedNewAccountLink,ForgotPasswordLink;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();

        InitializeFields();

        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToRegisterActivity();
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUsertoLogin();
            }
        });
    }

    private void AllowUsertoLogin() {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please Enter your Email",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please Enter your Password",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingbar.setTitle("Signing In");
            loadingbar.setMessage("Please wait.....");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        SendUserToMainActivity();
                        Toast.makeText(LoginActivity.this, "Logged in Successfully...", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                    else {
                        String message =task.getException().toString();
                        Toast.makeText(LoginActivity.this,"Error :" + message,Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }

    }

    private void InitializeFields() {
        LoginButton = (Button) findViewById(R.id.login_button);
        PhoneLoginButton = (Button) findViewById(R.id.phone_login_button);
        UserEmail = (TextView) findViewById(R.id.login_email);
        UserPassword = (TextView) findViewById(R.id.login_password);
        NeedNewAccountLink =(TextView) findViewById(R.id.need_new_account_link);
        ForgotPasswordLink = (TextView) findViewById(R.id.forget_password_link);
        loadingbar = new ProgressDialog(this);
    }



    private void SendUserToMainActivity() {
        Intent mainIntent =new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    private void SendUserToRegisterActivity() {
        Intent registerIntent =new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(registerIntent);
    }
}
