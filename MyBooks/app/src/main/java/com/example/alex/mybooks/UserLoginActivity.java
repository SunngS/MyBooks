package com.example.alex.mybooks;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.alex.mybooks.entities.LoginResult;
import com.example.alex.mybooks.entities.User;
import com.example.alex.mybooks.utilities.AlertMessageHelper;
import com.example.alex.mybooks.utilities.LocalStorageHelper;
import com.example.alex.mybooks.utilities.NetworkStorageHelper;

public class UserLoginActivity extends ActionBarActivity {

    private AlertMessageHelper messageHelper;
    private NetworkStorageHelper networkHelper;
    private LocalStorageHelper localStorageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        setTitle(getString(R.string.login_title));
        messageHelper = new AlertMessageHelper(this);
        networkHelper = new NetworkStorageHelper(this);
        localStorageHelper = new LocalStorageHelper(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public  void Login(View view){
        boolean isNetworkConnected = networkHelper.IsNetworkAvailable();
        if (isNetworkConnected){
            EditText usernameTextBox = (EditText)findViewById(R.id.username_editor);
            EditText passwordTextBox = (EditText)findViewById(R.id.password_editor);
            User user = new User();
            user.setUsername(usernameTextBox.getText().toString());
            user.setPassword(passwordTextBox.getText().toString());
            new AuthenticateUserFromServerTask().execute(user);
        }
        else{
            messageHelper.ShowAlertMessage("Cannot connect to network","Network Error");
        }
    }

    private class AuthenticateUserFromServerTask extends AsyncTask<User,Void,LoginResult>{


        @Override
        protected LoginResult doInBackground(User... params) {
            User user = params[0];
            LoginResult result = null;
            result = networkHelper.AuthenticateUser(user);
            if (result != null && result.getSucceed() == true){
                localStorageHelper.SaveUserLoginRecord(result.getLoginUser());
            }
            return result;
        }

        protected void onPostExecute(LoginResult loginResult){
            if (loginResult.getSucceed()){
                GoToListPage();
            }
            else{
                ShowLoginFailedMessage(loginResult.getResultCode());
            }
        }
    }

    private void GoToListPage(){
        Intent gotoListPageIntent = new Intent(this,BookListActivity.class);
        startActivity(gotoListPageIntent);
    }

    private void ShowLoginFailedMessage(String errorCode){
        if (errorCode.equals(LoginResult.USER_NOT_EXISTS)){
            messageHelper.ShowAlertMessage("Login failed, user name does not exists","Login Failed");
        }
        else if (errorCode.equals(LoginResult.PASSWORD_NOT_CORRECT)){
            messageHelper.ShowAlertMessage("Login failed, password and username are not match","Login Failed");
        }
        else if (errorCode.equals(LoginResult.DEFAULT_RESULT_CODE)){
            messageHelper.ShowAlertMessage("Login failed, server internal error, please try again later","Login Failed");
        }

    }
}
