package com.example.taphoaapp;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.example.taphoaapp.profile.changeInfo;
import com.example.taphoaapp.profile.changePassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;

public class ChangePasswordActivity extends AppCompatActivity {
    private String order, userID, confirmUserID, userPass, userMail;
    Button btnChangePass;
    EditText editTextNewPassword, editTextCNewPassword, editTextOldPassword;
//    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    AuthCredential authCredential;
    String PassPassword,ActiPrev,PrevActive;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        editTextOldPassword = findViewById(R.id.tvPassword);
        editTextNewPassword = findViewById(R.id.tvNewPassword);
        editTextCNewPassword = findViewById(R.id.tvConfirmPassword);
        btnChangePass = findViewById(R.id.btnChangePassword);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thay đổi mật khẩu tài khoản");

        Intent i = getIntent();
        extras = getIntent().getExtras();
        if ( i!= null &&extras != null) {
            order = extras.getString("Order");
            userID = i.getStringExtra("userID");
//           Toast.makeText(getApplicationContext(),)

//            Toast toast =Toast.makeText(this, PassPassword, Toast.LENGTH_LONG);
//            toast.show();
            PassPassword = i.getStringExtra("password");
            Log.e("ChangePass,password : ", PassPassword);
            if(PassPassword !=null) {
                Log.e("ChangePass,password : ", PassPassword);
            }

            ActiPrev = i.getStringExtra("PrevActive");
            if(ActiPrev !=null) {
                ActiPrev = i.getStringExtra("PrevActive");
            }
        }
//        confirmUserID = firebaseAuth.getCurrentUser().getUid();
        confirmUserID = firebaseUser.getUid();

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (userID == confirmUserID)
//                {
                    Boolean Run = true;
                    userMail = firebaseUser.getEmail().trim();
                    userPass = "987654321";
                    String newpassword = editTextNewPassword.getText().toString().trim();
                    String newcpassword = editTextCNewPassword.getText().toString().trim();
                    if(editTextNewPassword.getText().toString().trim().isEmpty()||(editTextCNewPassword.getText().toString().trim().isEmpty()||editTextOldPassword.getText().toString().isEmpty()))
                    {
                        editTextNewPassword.setError("Không được để trống khung mật khẩu");
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
//                        builder.setMessage("Không được để trống khung mật khẩu")
//                                .setTitle("Thông báo");
//                        builder.show();
                        editTextNewPassword.requestFocus();
                        Run = false;
//                        return;
                    }
//                    if(newcpassword.isEmpty())
//                    {
//                        editTextCNewPassword.setError("Password must match");
//                        editTextCNewPassword.requestFocus();
//                        Run = false;
//                        return;
//                    }
                    if(!editTextOldPassword.getText().toString().equalsIgnoreCase(PassPassword)){
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
//                        builder.setMessage("Mật khẩu cũ nhập sai ")
//                                .setTitle("Thông báo");
//                        builder.show();

                        editTextOldPassword.setError("Mật khẩu cũ nhập sai");
                        editTextOldPassword.requestFocus();
                        Run = false;
//                        return;
                    }
                if(editTextNewPassword.getText().toString().trim().length() < 6)
                {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
//                        builder.setMessage("Mật khẩu nhập lại chưa trùng với mật khẩu mới")
//                                .setTitle("Thông báo");
//                        builder.show();
//                        editTextNewPassword.setError("Password must not empty");
//                        editTextNewPassword.requestFocus();
                    editTextNewPassword.setError("Mật khẩu mới phải ít nhất 6 ký tự");
                    editTextNewPassword.requestFocus();
                    Run = false;
//                        return;
                }

                    if(!(editTextNewPassword.getText().toString().trim()).equalsIgnoreCase(editTextCNewPassword.getText().toString().trim()))
                    {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
//                        builder.setMessage("Mật khẩu nhập lại chưa trùng với mật khẩu mới")
//                                .setTitle("Thông báo");
//                        builder.show();
//                        editTextNewPassword.setError("Password must not empty");
//                        editTextNewPassword.requestFocus();
                        editTextCNewPassword.setError("Mật khẩu nhập lại chưa trùng với mật khẩu mới");
                        editTextCNewPassword.requestFocus();
                        Run = false;
//                        return;
                    }
                    if(Run==true) {
                        authCredential = EmailAuthProvider.getCredential(userMail,editTextOldPassword.getText().toString());
                        firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    firebaseUser.updatePassword(newpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                                                builder.setMessage("Mật khẩu mới đổi thành công")
                                                        .setTitle("Thông báo");
                                                builder.show();

                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Intent intent = new Intent( ChangePasswordActivity.this, MainActivity.class);
//                intent.putExtra("userID", UID);
                                                        intent.putExtra("prevActive", "changePassword");
                                                        intent.putExtra("password", newpassword);
                                                        startActivity(intent);
                                                    }
                                                }, 2000);

                                                Log.d(TAG, "Password updated");
                                            }else { Log.d(TAG, "Error password not updated"); }
                                        }
                                    });
                                }
                            }
                        });
                    }
//                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    private Intent getParentActivityIntentImpl() {
        Intent i = null;
        if (extras != null) {
            PrevActive = extras.getString("prevActive");
            //The key argument here must match that used in the other activity
        }

        // Here you need to do some logic to determine from which Activity you came.
        // example: you could pass a variable through your Intent extras and check that.
        if (PrevActive == "MainActivity") {
            i = new Intent(this, MainActivity.class);
            // set any flags or extras that you need.
            // If you are reusing the previous Activity (i.e. bringing it to the top
            // without re-creating a new instance) set these flags:
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            // if you are re-using the parent Activity you may not need to set any extras
            i.putExtra("prevActive", "DetailProduct");
        } else {
            i = new Intent(this, BasketFragment.class);
            // same comments as above
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.putExtra("prevActive", "DetailProduct");
        }

        return i;
    }
}
