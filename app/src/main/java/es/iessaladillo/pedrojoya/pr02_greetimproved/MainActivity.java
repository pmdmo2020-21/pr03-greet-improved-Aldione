package es.iessaladillo.pedrojoya.pr02_greetimproved;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr02_greetimproved.databinding.MainActivityBinding;
import es.iessaladillo.pedrojoya.pr02_greetimproved.utils.SoftInputUtils;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;
    private int times;
    private String treatmentSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        treatmentSelected = (String) binding.treatmentMr.getText();
        setupViews();
    }

    //SetUp premium swift botton
    private void setupViewsPremium() {
        if(binding.swtPremium.isChecked()){
            binding.progressBar.setVisibility(View.GONE);
            binding.lblCount.setVisibility(View.GONE);
        } else {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.lblCount.setVisibility(View.VISIBLE);
            times=0;
            showTimes();
        }
    }

    //Setup on views
    private void setupViews() {
        showTimes();
        changeChar();
        binding.swtPremium.setOnClickListener(v -> setupViewsPremium());
        binding.btnGreet.setOnClickListener(v -> incrementAndShow());
        binding.treatmentSelect.setOnCheckedChangeListener((group, checkedId) -> selectTreatment(checkedId));
        binding.txtName.setOnFocusChangeListener((view, b) -> {
            changeCharViewColorOnFocus(binding.lblCharName, b);
        });
        binding.txtSurname.setOnFocusChangeListener((view, b) -> {
            changeCharViewColorOnFocus(binding.lblCharSurName, b);
        });
        binding.txtName.setOnKeyListener((view, i, keyEvent) -> {
            changeChar();
            return false;
        });
        binding.txtSurname.setOnKeyListener((view, i, keyEvent) -> {
            changeChar();
            return false;
        });
        binding.txtSurname.setOnEditorActionListener((textView, i, keyEvent) -> {
            incrementAndShow();
            return true;
        });
    }

    //Change colors on focus text
    private void changeCharViewColorOnFocus(TextView textView, boolean b) {
        if (b) {
            textView.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            textView.setTextColor(getResources().getColor(R.color.textPrimary));
        }
    }

    //Modify text of chars
    private void changeChar() {
        int countCharName = 20;
        binding.lblCharName.setText(getResources().getQuantityString(R.plurals.main_char, countCharName -binding.txtName.getText().length(), countCharName -binding.txtName.getText().length()));
        binding.lblCharSurName.setText(getResources().getQuantityString(R.plurals.main_char, countCharName -binding.txtSurname.getText().length(), countCharName -binding.txtSurname.getText().length()));
    }

    //Change the icon of treatment
    private void selectTreatment(int checkedId) {
        if(checkedId == binding.treatmentMr.getId()){
            binding.imgPhoto.setImageResource(R.drawable.ic_mr);
            treatmentSelected = (String) binding.treatmentMr.getText();
        } else if (checkedId == binding.treatmentMrs.getId()){
            binding.imgPhoto.setImageResource(R.drawable.ic_mrs);
            treatmentSelected = (String) binding.treatmentMrs.getText();
        } else {
            binding.imgPhoto.setImageResource(R.drawable.ic_ms);
            treatmentSelected = (String) binding.treatmentMs.getText();
        }
    }

    //Check the count and see if is premium or the max time to use the buttom
    private void incrementAndShow() {
        SoftInputUtils.hideSoftKeyboard(binding.lblCharName);
        if(times < 10 && !binding.swtPremium.isChecked() && validateTxt()){
            times++;
            showTimes();
            showGreet();
        } else if (binding.swtPremium.isChecked() && validateTxt()){
            showGreet();
        } else if(binding.txtName.getText().length() != 0 && validateTxt()){
            buyPremium();
        }

    }

    //Check text and cant be empty and need to write a valid name (minimum 2 char and cant be numbers)
    private boolean validateTxt() {
        if(binding.txtName.getText().length() == 0 || binding.txtSurname.getText().length() == 0 || !binding.txtName.getText().toString().matches("[a-zA-Z][a-zA-Z ]+") || !binding.txtSurname.getText().toString().matches("[a-zA-Z][a-zA-Z ]+")){
            return false;
        } else {
            return true;
        }
    }

    //Send message Buy Premium
    private void buyPremium() {
        binding.lblGreet.setText(String.valueOf("Buy premium subscription to go on greeting!!"));
    }

    //Show times and bar progress
    private void showTimes() {
        binding.lblCount.setText(String.valueOf(times+" of 10"));
        binding.progressBar.setProgress(times);
    }

    //Check if is polite or not.
    private void showGreet() {
        if(binding.chkPolitely.isChecked()){
            binding.lblGreet.setText(String.valueOf("Good morning "+treatmentSelected+" "+binding.txtName.getText()+" "+binding.txtSurname.getText()+".\nPleased to meet you"));
        } else {
            binding.lblGreet.setText(String.valueOf("Hello "+binding.txtName.getText()+" "+binding.txtSurname.getText()+ ". What's up?"));
        }

    }

}