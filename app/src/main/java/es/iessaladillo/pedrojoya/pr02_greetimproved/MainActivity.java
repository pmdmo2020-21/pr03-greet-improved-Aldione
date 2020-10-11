package es.iessaladillo.pedrojoya.pr02_greetimproved;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        binding.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                changeChar();
            }
        });
        binding.txtSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                changeChar();
            }
        });
        binding.txtSurname.setOnEditorActionListener((textView, i, keyEvent) -> {
            incrementAndShow();
            return true;
        });
    }

    private void changeCharViewColorOnFocus(TextView textView, boolean b) {
        if (b) {
            textView.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            textView.setTextColor(getResources().getColor(R.color.textPrimary));
        }
    }

    private void changeChar() {
        int countCharName = 20;

        binding.lblCharName.setText(getResources().getQuantityString(R.plurals.main_char, countCharName - binding.txtName.getText().length(), countCharName - binding.txtName.getText().length()));
        binding.lblCharSurName.setText(getResources().getQuantityString(R.plurals.main_char, countCharName - binding.txtSurname.getText().length(), countCharName - binding.txtSurname.getText().length()));

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

    private boolean validateTxt() {
        if(binding.txtName.getText().length() == 0 || binding.txtSurname.getText().length() == 0 || !binding.txtName.getText().toString().matches("[a-zA-Z][a-zA-Z ]+") || !binding.txtSurname.getText().toString().matches("[a-zA-Z][a-zA-Z ]+")){
            showAlert();
            return false;
        } else {
            return true;
        }
    }

    private void buyPremium() {
        showMessage("Buy premium subscription to go on greeting!!");
    }

    private void showTimes() {
        binding.lblCount.setText(String.valueOf(times+" of 10"));
        binding.progressBar.setProgress(times);
    }

    private void showGreet() {
        if(binding.chkPolitely.isChecked()){
            showMessage("Good morning "+treatmentSelected+" "+binding.txtName.getText()+" "+binding.txtSurname.getText()+".\nPleased to meet you");
        } else {
            showMessage("Hello "+binding.txtName.getText()+" "+binding.txtSurname.getText()+ ". What's up?");

        }

    }

    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showAlert() {
        if(binding.txtName.getText().length()==0 || !binding.txtName.getText().toString().matches("[a-zA-Z][a-zA-Z ]+")){
            binding.txtName.setError(getString(R.string.RequiredLetter));
        } else if (binding.txtSurname.getText().length()==0 || !binding.txtSurname.getText().toString().matches("[a-zA-Z][a-zA-Z ]+")){
            binding.txtSurname.setError(getString(R.string.RequiredLetter));
        }
    }



}