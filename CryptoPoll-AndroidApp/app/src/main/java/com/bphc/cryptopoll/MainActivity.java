package com.bphc.cryptopoll;

import static com.bphc.cryptopoll.prefs.SharedPrefsConstants.EMAIL;
import static com.bphc.cryptopoll.prefs.SharedPrefsConstants.JWTS_TOKEN;
import static com.bphc.cryptopoll.prefs.SharedPrefsConstants.SESSION;
import static com.bphc.cryptopoll.prefs.SharedPrefsConstants.USER;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bphc.cryptopoll.helper.APIClient;
import com.bphc.cryptopoll.helper.Progress;
import com.bphc.cryptopoll.helper.Webservices;
import com.bphc.cryptopoll.models.LoginResponse;
import com.bphc.cryptopoll.prefs.SharedPrefs;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private String idToken;
    private Gson gson = new Gson();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefs.getBooleanParams(this, SESSION)) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        progressDialog = Progress.getProgressDialog(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onStart() {
        super.onStart();
        String authToken = SharedPrefs.getStringParams(this, JWTS_TOKEN, "");
        if (!authToken.isEmpty()) {
           // updateUI();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {
            signIn();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Progress.showProgress(true, "Signing In...");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            idToken = account.getIdToken();
            Log.d("ID_TOKEN", idToken);
            //SharedPrefs.setStringParams(this, USER_PHOTO, account.getPhotoUrl().toString());

            sendTokenToServer();
        }
    }

    /* private void sendTokenToServer() {

        Retrofit retrofit = APIClient.getRetrofitInstance();
        Webservices webservices = retrofit.create(Webservices.class);

        Call<ServerResponse> call = webservices.authWithGoogle(idToken);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse mServerResponse = response.body();
                if (mServerResponse != null) {
                    Result result = mServerResponse.getResult();
                    SharedPrefs.setStringParams(getContext(), JWTS_TOKEN, result.authToken);
                    Progress.dismissProgress(progressDialog);
                    if (result.phoneVerified && result.professionGiven) {
                        SharedPrefs.setBooleanParams(getContext(), PHONE_VERIFIED + result.email, true);
                        SharedPrefs.setBooleanParams(getContext(), PROFESSION_GIVEN + result.email, true);
                        User user = new User(result.email, result.name, result.username, result.profession, result.phone);
                        toDashboard(user);
                    } else {
                        User user = new User(result.email, result.name, "", "", "");
                        toPhoneAuth(user);
                    }

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                if (t.getMessage().equals("timeout")) {
                    sendTokenToServer();
                }
            }
        });

    }*/

    private void sendTokenToServer() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        Webservices webservices = retrofit.create(Webservices.class);

        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", idToken);

        Call<LoginResponse> call = webservices.authWithGoogle(map);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                SharedPrefs.setStringParams(MainActivity.this, JWTS_TOKEN, loginResponse.getAccessToken());
                Log.d("ACCESS_TOKEN", loginResponse.getAccessToken());
                SharedPrefs.setBooleanParams(MainActivity.this, SESSION, true);
                SharedPrefs.setStringParams(MainActivity.this, EMAIL, loginResponse.getUser().getEmail());
                SharedPrefs.setStringParams(
                        MainActivity.this, USER,
                        loginResponse.getUser().getFirstName() + " " + loginResponse.getUser().getLastName());

                Progress.dismissProgress(progressDialog);
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}