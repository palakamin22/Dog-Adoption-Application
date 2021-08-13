package com.app.adoptme.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.app.adoptme.databinding.ActivityApplicationBinding;
import com.app.adoptme.firebase.database.ApplicationHelper;
import com.app.adoptme.model.Application;
import com.app.adoptme.model.Post;
import com.app.adoptme.utils.Loading;
import com.app.adoptme.utils.Utility;

import static com.app.adoptme.utils.Constant.POST_DATA;

public class ApplicationActivity extends AppCompatActivity {

    private ActivityApplicationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityApplicationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init() {
        Post post = (Post) getIntent().getSerializableExtra(POST_DATA);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFrom()) {
                    Loading.showDialog(ApplicationActivity.this);

                    Application application = new Application();
                    application.setPostId(post.getId());
                    application.setPostImage(post.getImage());
                    application.setPostName(post.getName());
                    application.setPostUserId(post.getUserId());
                    application.setUserId(Utility.getUser().getId());
                    application.setUserEmail(Utility.getUser().getEmail());
                    application.setUserName(Utility.getUser().getName());

                    application.setAns1(Utility.getText(binding.edt1));
                    application.setAns2(getRadioValue(binding.rg2));
                    application.setAns3(getRadioValue(binding.rg3));
                    application.setAns4(Utility.getText(binding.edt4));
                    application.setAns5(getRadioValue(binding.rg5));
                    application.setAns6(getRadioValue(binding.rg6));
                    application.setAns7(Utility.getText(binding.edt7));
                    application.setAns8(getRadioValue(binding.rg8));
                    application.setAns9(getRadioValue(binding.rg9));
                    application.setAns10(Utility.getText(binding.edt10));
                    application.setAns11(getRadioValue(binding.rg11));
                    application.setAns12(getRadioValue(binding.rg12));
                    application.setAns13(getRadioValue(binding.rg13));
                    application.setAns14(getRadioValue(binding.rg14));
                    application.setAns15(Utility.getText(binding.edt15));
                    application.setAns16(getRadioValue(binding.rg16));
                    application.setAns17(Utility.getText(binding.edt17));
                    application.setAns18(getRadioValue(binding.rg18));
                    application.setAns19(Utility.getText(binding.edt19));
                    application.setAns20(Utility.getText(binding.edt20));
                    application.setAns21(getRadioValue(binding.rg21));
                    application.setAns22(Utility.getText(binding.edt22));
                    application.setAns23(Utility.getText(binding.edt23));

                    ApplicationHelper.addApplication(application, documentReference -> {
                        Loading.hideDialog();
                        Utility.showAlert(ApplicationActivity.this, "Success", "Application added successfully", (dialogInterface, i) -> finish());
                    }, e -> {
                        Loading.hideDialog();
                        Utility.showAlert(ApplicationActivity.this, "Error", e.getMessage(), null);
                    });
                }
            }
        });
    }

    private boolean validateFrom() {
        boolean isValid = true;
        if (Utility.isEmpty(binding.edt1)) {
            Utility.toast("Please enter answer");
            isValid = false;
        } else if (Utility.isEmpty(binding.edt4)) {
            Utility.toast("Please enter answer");
            isValid = false;
        } else if (Utility.isEmpty(binding.edt7)) {
            Utility.toast("Please enter answer");
            isValid = false;
        } else if (Utility.isEmpty(binding.edt10)) {
            Utility.toast("Please enter answer");
            isValid = false;
        } else if (Utility.isEmpty(binding.edt15)) {
            Utility.toast("Please enter answer");
            isValid = false;
        } else if (Utility.isEmpty(binding.edt17)) {
            Utility.toast("Please enter answer");
            isValid = false;
        } else if (Utility.isEmpty(binding.edt19)) {
            Utility.toast("Please enter answer");
            isValid = false;
        } else if (Utility.isEmpty(binding.edt20)) {
            Utility.toast("Please enter answer");
            isValid = false;
        } else if (Utility.isEmpty(binding.edt22)) {
            Utility.toast("Please enter answer");
            isValid = false;
        } else if (Utility.isEmpty(binding.edt23)) {
            Utility.toast("Please enter answer");
            isValid = false;
        }
        return isValid;
    }

    private String getRadioValue(RadioGroup radioGroup) {
        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        return radioButton.getText().toString();
    }

}