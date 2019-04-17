package itg8.com.labtestingapp.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.webkit.WebView;

import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.databinding.ActivityTestDetailBinding;
import itg8.com.labtestingapp.db.tables.Test;
import itg8.com.labtestingapp.test.mvvm.TestDetailViewModel;

public class TestDetailActivity extends AppCompatActivity {

    ActivityTestDetailBinding binding;
    private TestDetailViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_detail);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setViewModel();
        WebView webview = binding.content.description;
        if (getIntent().hasExtra(CommonMethod.DESCRIPTION)) {
            Test test = getIntent().getParcelableExtra(CommonMethod.DESCRIPTION);
            if (MyApplication.getInstance().isTestInCart(test)) {
                test.setItemCartSize(MyApplication.getInstance().getTestCartSize(test));
            } else {
                test.setItemCartSize(0);
            }
            model.setTest(test);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.loadDataWithBaseURL("", test.getDescription(), "text/html", "UTF-8", "");
            String title = test.getName();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        }
    }

    private void setViewModel() {
         model=new TestDetailViewModel(this);
         binding.setModel(model);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }
}
