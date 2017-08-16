package ohopro.com.ohopro.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.busnesslayer.CommonBL;
import ohopro.com.ohopro.busnesslayer.DataListener;
import ohopro.com.ohopro.domains.AuthenticationDomain;
import ohopro.com.ohopro.domains.ErrorDomain;
import ohopro.com.ohopro.domains.UserDomain;
import ohopro.com.ohopro.utility.CustomAlertDialogSimple;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.views.CustomProgressLoader;
import ohopro.com.ohopro.webaccess.Response;
import ohopro.com.ohopro.webaccess.ServiceMethods;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
        implements DataListener {
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    Toolbar toolbar;
    Button mEmailSignInButton;
    TextInputLayout ttl_email, ttl_password;

    CustomProgressLoader customProgressLoader;

    Context context;

    PreferenceUtils preferenceUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        initViewController();
        setSupportActionBar(toolbar);
        context = LoginActivity.this;
        customProgressLoader = new CustomProgressLoader(context);
        preferenceUtils = new PreferenceUtils(context);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUserName(ttl_email.getEditText().getText().toString())) {
                    Toast.makeText(context, "enter valid user name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ttl_password.getEditText().getText().toString().length() < 4) {
                    Toast.makeText(context, "enter valid password", Toast.LENGTH_SHORT).show();
                    return;
                }
                doLogin();
                // gotoDashBoardActivity();
            }
        });

    }

    private void doLogin() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_AUTHENTICATION));
        stringBuilder.append(ttl_password.getEditText().getText().toString());
        stringBuilder.append("&username=");
        stringBuilder.append(ttl_email.getEditText().getText().toString());

        if (new CommonBL(this, context).doLogin(stringBuilder.toString())

                ) {
            customProgressLoader.showProgressDialog();
        }
    }

    private boolean validateUserName(String s) {
        Pattern MOBILEPATTERN = Pattern.compile("^[7-9][0-9]{9}$");
        Pattern USERPATTERN = Pattern.compile("^[a-z0-9_-]{3,15}$");

        return Patterns.EMAIL_ADDRESS.matcher(s).matches() || MOBILEPATTERN.matcher(s).matches() || s.length() > 3;
    }

    private void gotoDashBoardActivity() {
        Intent intent = new Intent(context, DashBoardActivity.class);
        startActivity(intent);
        finish();

    }

    private void initViewController() {
        ttl_password = (TextInputLayout) findViewById(R.id.ttl_password);
        ttl_email = (TextInputLayout) findViewById(R.id.ttl_email);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 8;
    }


    @Override
    public void dataRetreived(Response data) {
        if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_AUTHENTICATION)) {
            if (!data.isError) {
                customProgressLoader.dismissProgressDialog();
                if (data.data instanceof AuthenticationDomain) {
                    AuthenticationDomain authenticationDomain = (AuthenticationDomain) data.data;
                    preferenceUtils.saveAccessTokenRefreshToken(authenticationDomain.getAccess_token(),
                            authenticationDomain.getRefresh_token(),
                            authenticationDomain.getExpires_in());
                    getUserDetails();
                    //gotoDashBoardActivity();
                } else if (data.data instanceof ErrorDomain) {
                    new CustomAlertDialogSimple(context).showAlertDialog(((ErrorDomain) data.data).getError_description());
                } else {
                    Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }

            }
        } else if (data.servicemethod.equalsIgnoreCase(ServiceMethods.WS_APP_GET_USERDET)) {
            customProgressLoader.dismissProgressDialog();
            if (!data.isError) {
                if (data.data instanceof UserDomain) {
                    UserDomain userDetDomain = (UserDomain) data.data;
                    preferenceUtils.saveUserDetails(userDetDomain);
                    preferenceUtils.setUserState(true);
                    preferenceUtils.setUserResponseJson(userDetDomain.getRespInJson());
                    gotoDashBoardActivity();
                } else if (data.data instanceof ErrorDomain) {
                    new CustomAlertDialogSimple(context).showAlertDialog(((ErrorDomain) data.data).getError_description());
                }
            }
        }
    }


    private void getUserDetails() {
        StringBuilder requrl = new StringBuilder();
        requrl.append(ServiceURL.getRequestUrl(ServiceMethods.WS_APP_GET_USERDET));
        requrl.append("/");
        requrl.append(ttl_email.getEditText().getText().toString());
        //requrl.append(preferenceUtils.getAccessToken());
        if (new CommonBL(this, context).getUserDetails(requrl.toString())) {
            customProgressLoader.showProgressDialog();
        }
    }

    @Override
    public void opennetworksetting() {

    }
}

