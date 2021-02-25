package com.example.action;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomBarActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNV;
    String email="";
    String token;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_activity);

        Intent intent = getIntent();
        if(intent!=null){
            token = intent.getStringExtra("token");
            email = intent.getStringExtra("email");
            user_id = intent.getStringExtra("user_id");
        }
        Log.e("token",token);
        Log.e("Email",email);

        mBottomNV = findViewById(R.id.nav_view);
        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { //NavigationItemSelecte
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());


                return true;
            }
        });
        mBottomNV.setSelectedItemId(R.id.main);
    }
    private void BottomNavigate(int id) {  //BottomNavigation 페이지 변경
        String tag = String.valueOf(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            if (id == R.id.main) {
                fragment = new MyInfoFragment();
                Bundle bundle=new Bundle();
                bundle.putString("token",token);
                bundle.putString("email",email);
                bundle.putString("user_id",user_id);
                fragment.setArguments(bundle);
                /*
                 Bundle bundle = new Bundle();
 String sendstr = 보낼 문자열;
 bundle.putString("send", sendstr );
 Fragment fragment = new Fragment();
 fragment.setArguments(bundle);
 tran.replace(R.id.fragment_test, fragment).commit();
                 */
            } else if (id == R.id.write){
                fragment = new CreateScriptFragment();
                Bundle bundle=new Bundle();
                bundle.putString("token",token);
                bundle.putString("email",email);
                bundle.putString("user_id",user_id);
                fragment.setArguments(bundle);
            } else if (id == R.id.feed){
                fragment = new FeedFragment();
                Bundle bundle=new Bundle();
                bundle.putString("token",token);
                bundle.putString("email",email);
                bundle.putString("user_id",user_id);
                fragment.setArguments(bundle);
            } else {
                fragment = new SettingFragment();
            }

            fragmentTransaction.add(R.id.framelayout, fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();


    }
}
