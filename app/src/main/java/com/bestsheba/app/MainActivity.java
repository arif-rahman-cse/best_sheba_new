package com.bestsheba.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bestsheba.app.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener{

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.drawerMenuBtn.setOnClickListener(this);
        binding.navigationDrawer.setNavigationItemSelectedListener(this);

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.setVerticalScrollBarEnabled(false);
        binding.webView.loadUrl("https://bestsheba.com/");
        binding.webView.setWebViewClient(new MyWebClient());
    }

    public class MyWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            binding.webViewPb.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            binding.webViewPb.setVisibility(View.GONE);

        }


        public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
            try {
                webView.stopLoading();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (webView.canGoBack()) {
                webView.goBack();
            }

            webView.loadUrl("about:blank");

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("No Internet Connection Found!")
                    .setCancelable(false).setPositiveButton("Try Again", (dialog, which) -> {
                finish();
                startActivity(getIntent());
            })
                    .setNegativeButton("Exit", (dialog, which) -> MainActivity.super.onBackPressed());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.show();
            super.onReceivedError(webView, errorCode, description, failingUrl);

        }
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.drawer_menu_btn) {
            openDrawer();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about_us) {
            startNewActivity(MainActivity.this, AboutUs.class);
            return true;

        }else if (id == R.id.contact_us){

            startNewActivity(MainActivity.this, ContactUs.class);
            return true;
        }
        return false;
    }

    private void openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    private void startNewActivity(Context packageContext, Class<?> cls) {
        startActivity(new Intent(packageContext, cls));
        finish();
    }


}