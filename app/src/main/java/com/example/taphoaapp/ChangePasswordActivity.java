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
    FirebaseUser firebaseUser;
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

        confirmUserID = firebaseUser.getUid();

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Boolean Run = true;
                    if(editTextNewPassword.getText().toString().trim().isEmpty()||(editTextCNewPassword.getText().toString().trim().isEmpty()||editTextOldPassword.getText().toString().isEmpty()))
                    {
                        editTextNewPassword.setError("Không được để trống khung mật khẩu");
                        editTextNewPassword.requestFocus();
                        editTextCNewPassword.requestFocus();
                        editTextOldPassword.requestFocus();
                        Run = false;
                    }
                    if(!editTextOldPassword.getText().toString().equalsIgnoreCase(PassPassword)){
                        editTextOldPassword.setError("Mật khẩu cũ nhập sai");
                        editTextOldPassword.requestFocus();
                        Run = false;
                    }
                if(editTextNewPassword.getText().toString().trim().length() < 6)
                {
                    editTextNewPassword.setError("Mật khẩu mới phải ít nhất 6 ký tự");
                    editTextNewPassword.requestFocus();
                    Run = false;
                }
                if(!(editTextNewPassword.getText().toString().trim()).equalsIgnoreCase(editTextCNewPassword.getText().toString().trim()))
                {
                    editTextCNewPassword.setError("Mật khẩu nhập lại chưa trùng với mật khẩu mới");
                    editTextCNewPassword.requestFocus();
                    Run = false;
                }
                if(Run==true) {
                    String newpassword = editTextNewPassword.getText().toString().trim();
                    firebaseUser.updatePassword(newpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ChangePasswordActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(ChangePasswordActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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
