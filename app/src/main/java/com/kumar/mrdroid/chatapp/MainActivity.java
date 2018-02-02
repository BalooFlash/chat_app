package com.kumar.mrdroid.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String ANYNOMOUS = "anynomous";
    private static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private ListView mMessageList;
    private MessageAdapter mAdapter;
    private ProgressBar mProgressbar;
    private ImageButton mImagePickerButton;
    private EditText mMessage;
    private Button mSendButton;

    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = ANYNOMOUS;

        mProgressbar = findViewById(R.id.progressBar);
        mMessageList = findViewById(R.id.messageListView);
        mImagePickerButton = findViewById(R.id.imagePickerbutton);
        mMessage = findViewById(R.id.et_message);
        mSendButton = findViewById(R.id.button_send);

        //set adapter to listview
        List<Message>  mMessageArrayList = new ArrayList<>();
        mAdapter = new MessageAdapter(this, R.layout.item_message, mMessageArrayList);
        mMessageList.setAdapter(mAdapter);

        mProgressbar.setVisibility(View.INVISIBLE);

        /***
         *  Define click event of mImagePickerButton
         */
        mImagePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // define click event of mImagePickerButton
            }
        });

        /***
         * Enable send button when there is text available to send
         */
        mMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() > 0){
                    mSendButton.setEnabled(true);
                }else{
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        /***
         * set filter for character limit
         */
        mMessage.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        /***
         * Send Button Sends a message and clear a edit text
         */
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // add functionality of send button


                // clear edittext on send button click
                mMessage.setText("");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
