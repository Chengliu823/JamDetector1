package com.example.semesterprojekt;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    //definition für layout //instanz Variablen
    private EditText mEtAccount, mEtPassword;
    private ImageView mIvDeleteAccount, mIvDeletePassword;
    private Button mBtnLogin;
    private TextView mTvForgetPassword, mTvGoToRegister;
    private ProgressDialog progressDialog;
    private ImageButton btnSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_login);

        //绑定控件 //input wird übernommen
        mEtAccount = findViewById(R.id.et_user_account);
        mEtPassword = findViewById(R.id.et_user_password);
        mIvDeleteAccount = findViewById(R.id.iv_delete_account);
        mIvDeletePassword = findViewById(R.id.iv_delete_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mTvForgetPassword = findViewById(R.id.tv_forget_password);
        mTvGoToRegister = findViewById(R.id.tv_go_to_register);
        btnSetting = findViewById(R.id.btn_setting);

        //文字发生改变时的监听事件
        mEtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int visible = TextUtils.isEmpty(charSequence.toString()) ? View.GONE : View.VISIBLE;
                mIvDeleteAccount.setVisibility(visible);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int visible = TextUtils.isEmpty(charSequence.toString()) ? View.GONE : View.VISIBLE;
                mIvDeletePassword.setVisibility(visible);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //按钮监听 //button
        mIvDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEtAccount.setText("");
            }
        });
        mIvDeletePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEtPassword.setText("");
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() { //button für login
            @Override
            public void onClick(View view) {
                login();
            }
        });

        mTvGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入注册界面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入设置界面
                Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        checkPermission();
    }


    /**
     * 执行登录操作
     */
    private void login() { //login
        // 隐藏软键盘
        hideKeyBoard(this);

        // 验证用户名是否为空 //ließt textfeld für username und weißt varibalen zu
        final String account = mEtAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "username can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        // 验证密码是否为空 //ließt textfeld für pasowert und weißt varibalen zu
        final String password = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        // 禁用登录按钮,避免重复点击
        mBtnLogin.setEnabled(false); //wenn man zu oft drückt
        // 显示提示对话框 //kleine message
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("waiting...");
        progressDialog.setMessage("sign in...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //检查数据库用户信息 //überprüfung ob password oder userame falsch, wenn richtig dann zu mapsActivity
        //          Konstruktor         Parameter          Mathode (useranme,                   passwort)
        //boolean loginok = new SQLiteHelper(getApplicationContext()).login(mEtAccount.getText().toString(), mEtPassword.getText().toString());
        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    boolean loginok = response.getBoolean("result");
                    if (loginok){
                        Toast.makeText(LoginActivity.this, "success!", Toast.LENGTH_SHORT).show(); //meldung
                        //启动主界面
                        Intent intent = new Intent(LoginActivity.this, MapsActivity.class); //mit Intend zu MapsActivity wechseln
                        intent.putExtra("username", mEtAccount.getText().toString());
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "wrong username or password!", Toast.LENGTH_SHORT).show();
                        mBtnLogin.setEnabled(true);
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        };


        loginService(account, password, handler);
    }

    private boolean loginService(String account, String password, JsonHttpResponseHandler handler) {
        try {
            //daten werden in json format gebracht
            JSONObject user = new JSONObject();
            user.put("username", account);
            user.put("password", password);

            String jsonStr = user.toString();
            System.out.println(jsonStr);
            callService("https://ieslamp.technikum-wien.at/bvu19sys5/jamlocal/checklogin.php", user , handler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static SyncHttpClient client = new SyncHttpClient();

    private void callService(String urlstr, JSONObject user, JsonHttpResponseHandler handler) {
        RequestParams rp = new RequestParams();
        rp.add("user", user.toString());

        RequestHandle rh = client.post(urlstr, rp, handler);
    }

    private JSONObject callService1(String urlstr, JSONObject user) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(urlstr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            //set headers and method


            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            String name = URLEncoder.encode("user", "UTF-8");
            String wert = URLEncoder.encode(user.toString(), "UTF-8");
            String attribut = name + "=" + wert;
            writer.write(attribut);
            // json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
            //input stream
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");
            if (buffer.length() == 0) {
                System.out.println("Stream was empty. No point in parsing.");
                return null;
            }
            String resp = buffer.toString();
            //response data
            try {
                //send to post execute
                return new JSONObject(resp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * 隐藏系统键盘
     *
     * <br>
     * <b>警告</b> 必须是确定键盘显示时才能调用
     */
    //für tastatur
    public static void hideKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //检查APP是否具有拨号、存储权限 //überfrüfen
    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(LoginActivity.this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //未被授予权限，请求权限
                showDialogTipUserRequestPermission();
            }
        }
        //check_cookies();
    }

    // 提示用户该请求权限的弹出框 //für GPS
    private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this)
                .setTitle("Permission is not Granted")
                .setMessage("This APP needs to use your Location Permission")
                .setPositiveButton("grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 321);
    }

    // 用户权限申请的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        Toast.makeText(this, "PLEASE GRANT PERMISSION IN THE SYSTEM SETTING", Toast.LENGTH_SHORT).show();
                    } else
                        finish();
                } else {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}