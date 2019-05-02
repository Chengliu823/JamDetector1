package com.example.semesterprojekt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEtAccount, mEtPassword, mEtPassword2;
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEtAccount = findViewById(R.id.et_user_account);
        mEtPassword = findViewById(R.id.et_user_password);
        mEtPassword2 = findViewById(R.id.et_user_password2);
        mBtnRegister = findViewById(R.id.btn_sign_up);

        mBtnRegister.setOnClickListener(new View.OnClickListener() { //button für register
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void register() { //register
        // 隐藏软键盘
        //hideKeyBoard(this);

        // 验证用户名是否为空
        final String account = mEtAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "username can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        // 验证密码是否为空
        final String password = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        // 禁用登录按钮,避免重复点击
        mBtnRegister.setEnabled(false); //wenn man zu oft drückt

        System.out.println("erfolg");

    }

    public static void hideKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }



}
