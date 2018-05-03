package com.example.hp.sociohub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void signup(View view) {


      /*  EditText name=findViewById(R.id.name);
        EditText phn=findViewById(R.id.number);
        EditText email=findViewById(R.id.email);
        EditText password=findViewById(R.id.password);
        EditText chngpassword=findViewById(R.id.chngpassword);



        final String n=name.getText().toString();
        final String p=phn.getText().toString();
        final String e=email.getText().toString();
        final String pass=password.getText().toString();
        final String chngp =chngpassword.getText().toString();

        final FirebaseAuth auth=FirebaseAuth.getInstance();
        final OnCompleteListener<AuthResult> listener= new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                 *//*   ProfileData data = new ProfileData(n ,p,e,pass,chngp);


                    FirebaseDatabase database = FirebaseDatabase.getInstance();


                    database.getReference().child(e.replace(".","")).setValue(data);*//*

                }
                else {

                }

            }
        };

        auth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(listener);*/
    }

    public void finish_activity(View view) {

        finish();
    }
}
