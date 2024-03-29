package com.example.android_hinofi_prototype.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.android_hinofi_prototype.R;
import com.example.android_hinofi_prototype.adapters.DatabaseAdapter;
import com.example.android_hinofi_prototype.models.User;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    private EditText editTextUserName;
    private EditText editTextUserPassword;
    public String username;
    private String password;
    String storedPassword;
    Context context=this;

    User user;
    DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        //Creates an instance of the databases
        databaseAdapter = new DatabaseAdapter(getApplicationContext());
        databaseAdapter = databaseAdapter.createDatabase();
        editTextUserName = findViewById(R.id.textEditUsernameLogin);
        editTextUserPassword = findViewById(R.id.textEditPassword);
    }


    public void  SignIn(View view){

        try {
            databaseAdapter = databaseAdapter.open();
            username = editTextUserName.getText().toString();
            password = editTextUserPassword.getText().toString();

            if (username.equals("") || password.equals(""))  {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("ALERT!");
                alertDialog.setMessage("Fill All Fields");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){}
                });
                alertDialog.show();
            }

            //fetch the password from the databases for respective username
            if(!username.equals(""))
            {
                storedPassword = databaseAdapter.getSingleUser(username);
            }
            if (password.equals(storedPassword)) {
                //Goes to the home page
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("Username",editTextUserName.getText().toString());
                startActivity(intent);
                //finish
                finish();
            }
            else
            {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("ALERT!");
                alertDialog.setMessage("Incorrect Login Please Try Again");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){}
                });
                alertDialog.show();
            }
        }
        catch (Exception ex)
        {
            Log.e("Error", "error login");
        }
        }

        public void SignUp(View view)
        {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //links to the help page
        if(id == R.id.action_help)
        {
            Intent helpIntent = new Intent(LoginActivity.this, HelpActivity.class);
            startActivity(helpIntent);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Close the databases
        databaseAdapter.close();
    }

}



