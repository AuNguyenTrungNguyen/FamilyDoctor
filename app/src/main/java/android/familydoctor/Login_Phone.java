package android.familydoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login_Phone extends AppCompatActivity  implements View.OnClickListener {

    private static final String TAG = "PhoneAuthActivity";

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;

    private FirebaseAuth mAuth;


    private boolean mVerificationInProgress = false;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;



    private EditText mPhoneNumberField;
    private EditText mVerificationField;

    private Button mStartButton;
    private Button mVerifyButton;
    private Button mResendButton;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login__phone);

        if (savedInstanceState != null) {

            onRestoreInstanceState(savedInstanceState);

        }
        mPhoneNumberField = (EditText) findViewById(R.id.field_phone_number);
        mVerificationField = (EditText) findViewById(R.id.field_verification_code);

        mStartButton = (Button) findViewById(R.id.button_start_verification);
        mVerifyButton = (Button) findViewById(R.id.button_verify_phone);
        mResendButton = (Button) findViewById(R.id.button_resend);

        mStartButton.setOnClickListener((View.OnClickListener) this);
        mVerifyButton.setOnClickListener((View.OnClickListener) this);
        mResendButton.setOnClickListener((View.OnClickListener) this);

        mAuth = FirebaseAuth.getInstance();



        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
                updateUI(STATE_VERIFY_SUCCESS, credential);
                signInWithPhoneAuthCredential(credential);
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {

                Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    mPhoneNumberField.setError("Invalid phone number.");

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",Snackbar.LENGTH_SHORT).show();
                }

                updateUI(STATE_VERIFY_FAILED);
            }

            @Override

            public void onCodeSent(String verificationId,PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
                updateUI(STATE_CODE_SENT);
            }

        };
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_start_verification:
                if (!validatePhoneNumber()) {
                    return;
                }
                startPhoneNumberVerification(mPhoneNumberField.getText().toString());
                break;
            case R.id.button_verify_phone:
                String code = mVerificationField.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    mVerificationField.setError("Cannot be empty.");
                    return;
                }
                verifyPhoneNumberWithCode(mVerificationId, code);
                break;
            case R.id.button_resend:

                resendVerificationCode(mPhoneNumberField.getText().toString(), mResendToken);
                break;
        }
    }

    @Override
    public void onStart() {

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        if (mVerificationInProgress && validatePhoneNumber()) {

            startPhoneNumberVerification(mPhoneNumberField.getText().toString());

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }
    @Override

    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }
    private void startPhoneNumberVerification(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(

                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

        mVerificationInProgress = true;

    }



    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,PhoneAuthProvider.ForceResendingToken token) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            updateUI(STATE_SIGNIN_SUCCESS, user);

                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                mVerificationField.setError("Invalid code.");
                            }
                            updateUI(STATE_SIGNIN_FAILED);
                        }

                    }

                });

    }

    private void updateUI(int uiState) {

        updateUI(uiState, mAuth.getCurrentUser(), null);

    }
    private void updateUI(FirebaseUser user) {

        if (user != null) {

            updateUI(STATE_SIGNIN_SUCCESS, user);

        } else {

            updateUI(STATE_INITIALIZED);

        }

    }

    private void updateUI(int uiState, FirebaseUser user) {

        updateUI(uiState, user, null);
    }
    private void updateUI(int uiState, PhoneAuthCredential cred) {

        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {

        switch (uiState) {

            case STATE_INITIALIZED:
                enableViews(mStartButton, mPhoneNumberField);
                disableViews(mVerifyButton, mResendButton, mVerificationField);
                break;

            case STATE_CODE_SENT:
                enableViews(mVerifyButton, mResendButton, mPhoneNumberField, mVerificationField);
                disableViews(mStartButton);
                break;

            case STATE_VERIFY_FAILED:

                enableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField, mVerificationField);
                break;

            case STATE_VERIFY_SUCCESS:

                disableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField, mVerificationField);
                if (cred != null) {

                    if (cred.getSmsCode() != null) {

                        mVerificationField.setText(cred.getSmsCode());

                    } else {

                    }

                }
                break;

            case STATE_SIGNIN_FAILED:
                break;

            case STATE_SIGNIN_SUCCESS:

                Intent intent = new Intent(Login_Phone.this,MainActivity.class);
                startActivity(intent);
                break;
        }


        if (user == null) {
            /// nope think in here haha
        } else {

            enableViews(mPhoneNumberField, mVerificationField);
            mPhoneNumberField.setText(null);
            mVerificationField.setText(null);

        }
    }

    private boolean validatePhoneNumber() {

        String phoneNumber = mPhoneNumberField.getText().toString();

        if (TextUtils.isEmpty(phoneNumber)) {

            mPhoneNumberField.setError("Invalid phone number.");
            return false;

        }

        return true;
    }

    private void enableViews(View... views) {

        for (View v : views) {

            v.setEnabled(true);

        }

    }

    private void disableViews(View... views) {

        for (View v : views) {
            v.setEnabled(false);
        }
    }
}
