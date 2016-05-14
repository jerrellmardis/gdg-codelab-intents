package com.contactsmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
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

        setupPhoneListeners();
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

    private void setupPhoneListeners() {
        final EditText phone = (EditText) findViewById(R.id.phone_edit_text);
        final View phoneButton = findViewById(R.id.phone_button);

        // set up a listener to format the phone number text as the user types
        if (phone != null) {
            phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        }

        if (phoneButton != null && phone != null) {
            // set up a click listener to open the phone app with the number provided
            phoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(phone.getText())) {
                        phone.setError(getString(R.string.phone_error_text));
                    } else {
                        phone.setError(null);
                        // use the ACTION_DIAL action
                        // using this action doesn't require any additional permissions unlike Intent.ACTION_CALL
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phone.getText()));
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
