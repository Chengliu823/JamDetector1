package com.example.semesterprojekt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.semesterprojekt.SQLiteHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import cz.msebera.android.httpclient.Header;


public class RegisterActivity extends AppCompatActivity {

    private EditText mEtAccount, mEtPassword, mEtPassword2;
    private Button mBtnRegister;

    private static SyncHttpClient client = new SyncHttpClient(); //library in build.gradle manuel hinzugefügt

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
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
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

        //new SQLiteHelper(RegisterActivity.this).initial_data(account,password);

        // SOAP

        String URL = "https://ieslamp.technikum-wien.at/bvu19sys5/jamlocal/registers.php?wsdl";
        String NAMESPACE = "https://ieslamp.technikum-wien.at/bvu19sys5/jamlocal/registers.php";
        String SOAP_ACTION = "https://ieslamp.technikum-wien.at/bvu19sys5/jamlocal/registers.php/register1";
        String METHOD_NAME = "register1";

        String result = "";

        SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);

        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName("username");
        propertyInfo.setValue(account);
        propertyInfo.setType(String.class);

        PropertyInfo propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("password");
        propertyInfo1.setValue(password);
        propertyInfo1.setType(String.class);

        soapObject.addProperty(propertyInfo);
        soapObject.addProperty(propertyInfo1);

        SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);

        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive)envelope.getResponse();
            result = soapPrimitive.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(RegisterActivity.this, "success!", Toast.LENGTH_SHORT).show(); //meldung
        //启动主界面
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class); //mit Intend zu MapsActivity wechseln
        startActivity(intent);
        finish();


        // SOAP ENDE

        /*
        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() { //neue instanz der kalsse ertsellt, behandelt ergebnis

            @Override //methode überschrien
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(RegisterActivity.this, "Could not call login service", Toast.LENGTH_SHORT).show();
                mBtnRegister.setEnabled(true);
                throwable.printStackTrace();
            }

            @Override //wenn der server retrun wert zurück schickt wird diese methode aufgerufen
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    boolean registerok = response.getBoolean("result"); //wenn {result:true} dann true sonst false
                    if (registerok){
                        Toast.makeText(RegisterActivity.this, "success!", Toast.LENGTH_SHORT).show(); //meldung
                        //启动主界面
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class); //mit Intend zu MapsActivity wechseln
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, "registration failed", Toast.LENGTH_SHORT).show();
                        mBtnRegister.setEnabled(true);
                    }
                } catch (JSONException e) {
                    mBtnRegister.setEnabled(true);
                    e.printStackTrace();
                }
            }

        };
        */

        //registerService(account, password, handler);

    }

    private void registerService(String account, String password, JsonHttpResponseHandler handler) {
        try {
            //daten werden in json format gebracht
            JSONObject user = new JSONObject();
            user.put("username", account);
            user.put("password", password);

            RequestParams rp = new RequestParams();
            rp.add("json", user.toString());


            client.post("https://ieslamp.technikum-wien.at/bvu19sys5/jamlocal/register.php", rp, handler); //json wird geschickt,
            // return wert wird von handler verarbeitet

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void hideKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }


}