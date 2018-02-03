package com.kumar.mrdroid.chatapp;

import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    List<AuthUI.IdpConfig> providers;

    private static final String TAG = "MainActivity";
    private static final String ANYNOMOUS = "anynomous";
    private static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private static final int RC_SIGN_IN= 1;

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

        /***
         * Firebase Realtime Database
         */
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        providers = new ArrayList<>();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("messages");

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

                // add functionality of send button to real time database
                Message message = new Message(mMessage.getText().toString().trim(), mUserName, null);
                mMessageDatabaseReference.push().setValue(message);


                // clear edittext on send button click
                mMessage.setText("");
            }
        });

        /***
         * Read from Realtime Database
         */
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // get message from Realtime Database with position
                Message message = dataSnapshot.getValue(Message.class);
                // add message to adapter
                mAdapter.add(message);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        // listener to mMessagedatabseLisener
        mMessageDatabaseReference.addChildEventListener(mChildEventListener);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                
                if(user != null){
                    //Already Sign In
                    Toast.makeText(MainActivity.this, "You Are Signing in, Welcome to mChat",
                            Toast.LENGTH_SHORT).show();
                    
                }else{
                    //user is Sign out
                    providers.add(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
                    providers.add(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                    
                }

            }
        };


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

    @Override
    protected void onPause() {
        super.onPause();
        /***
         * Attach AuthStatelistener to FirebaseAuth
         */
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /***
         * Attach AuthStatelistener to FirebaseAuth
         */
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }
}
