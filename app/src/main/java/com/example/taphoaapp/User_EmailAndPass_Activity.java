package com.example.taphoaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taphoaapp.DetailProduct.DetailProductActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class User_EmailAndPass_Activity extends AppCompatActivity {
    TextView tvName, tvEmail;
    FirebaseFirestore firebaseFirestore;
    Button buttonLogout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String userID;
    GoogleSignIn googleSignIn;
    GoogleSignInAccount googleSignInAccount;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_email_and_pass);
        tvName = findViewById(R.id.tvShowName);
        tvEmail = findViewById(R.id.tvShowEmail);
        buttonLogout = findViewById(R.id.logout);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        googleSignInClient = googleSignIn.getClient(this,googleSignInOptions);

        //Mở firebase Auth và Store
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Lấy id của tài khoản trong auth
        userID = firebaseAuth.getCurrentUser().getUid();
        googleSignInAccount = googleSignIn.getLastSignedInAccount(this);

        // Nếu userID không rỗng thì lấy dữ liệu từ Auth và Store
        if(userID != null)
        {
            //Kiếm dữ liệu document trong store có id = userID
            DocumentReference documentReference = firebaseFirestore.collection("User").document(userID);

            //Lấy dữ liệu truyền vào view
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    tvName.setText(value.getString("fullName"));
                    tvEmail.setText(value.getString("email"));
                }
            });
        }

        if(googleSignInAccount != null){
            String Name = googleSignInAccount.getDisplayName();
            String Mail = googleSignInAccount.getEmail();

            tvName.setText(Name);
            tvEmail.setText(Mail);
        }


        //Nút đăng xuất
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID != null)
                {
                    firebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }else if(googleSignInAccount != null) {
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();
                        }
                    });
                }else { Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show(); }
            }
        });

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(User_EmailAndPass_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        },3000);*/
    }
}