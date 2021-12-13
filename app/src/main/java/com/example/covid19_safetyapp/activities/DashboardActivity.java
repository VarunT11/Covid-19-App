package com.example.covid19_safetyapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covid19_safetyapp.R;
import com.example.covid19_safetyapp.api.FetchApi;
import com.example.covid19_safetyapp.interfaces.ActivityFragmentInterface;
import com.example.covid19_safetyapp.viewmodel.MainViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONException;

public class DashboardActivity extends AppCompatActivity implements ActivityFragmentInterface {

    private MainViewModel mainViewModel;
    private NavController navController;
    private ProgressDialog progressDialog;

    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findViewsAndAttachListeners(null);
        setupViewModelAndNavController();

        fetchData();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Updating the Data");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        toolbar = findViewById(R.id.toolbarMain);
        toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.actionRefresh){
                fetchData();
            }
            return false;
        });
    }

    @Override
    public void setupViewModelAndNavController() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        navController = Navigation.findNavController(this, R.id.navHostMain);
    }

    private void fetchData() {
        progressDialog.show();
        new FetchApi(this)
                .fetchApi(getString(R.string.data_url), (success, result) -> {
                    progressDialog.dismiss();
                    if (success) {
                        try {
                            mainViewModel.updateData(result);
                        } catch (JSONException e) {
                            Toast.makeText(this, "Error in Processing Data", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Error in Loading Data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}