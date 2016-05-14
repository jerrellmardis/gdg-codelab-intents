package com.contactsmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setupEmailListeners();
    }

    private void setupEmailListeners() {
        final EditText email = (EditText) findViewById(R.id.email_edit_text);
        final View emailButton = findViewById(R.id.email_button);

        if (emailButton != null && email != null) {
            // set up a click listener to open an email app when a valid email address is provided
            emailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // verify that the email address entered is valid
                    if (TextUtils.isEmpty(email.getText()) || !Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
                        email.setError(getString(R.string.email_error_text));
                    } else {
                        email.setError(null);
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setType("text/plain");
                        intent.setData(Uri.parse("mailto:" + email.getText()));
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
