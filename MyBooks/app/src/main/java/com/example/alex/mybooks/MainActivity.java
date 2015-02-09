package com.example.alex.mybooks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.alex.mybooks.utilities.LocalStorageHelper;


public class MainActivity extends ActionBarActivity {

    private LocalStorageHelper storageHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        storageHelper = new LocalStorageHelper(this);
//        boolean hasLoginRecord = storageHelper.HasLocalLoginRecord();
//        if (hasLoginRecord){
//            Intent gotoListPage = new Intent(this,BookListActivity.class);
//            startActivity(gotoListPage);
//        }

        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.welcome_page_title));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public  void GoToLoginPage(View view){

        Intent gotoLoginPageIntent = new Intent(this,UserLoginActivity.class);
        startActivity(gotoLoginPageIntent);
    }

    public void GoToRegisterPage(View view){
        Intent gotoRegisterPageIntent = new Intent(this,UserRegisterActivity.class);
        startActivity(gotoRegisterPageIntent);
    }
}
