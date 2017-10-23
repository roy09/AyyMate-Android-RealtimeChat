package com.google.firebase.udacity.friendlychat;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {

    private static final String TAG = "MainActivity";

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    public static final int RC_SIGN_IN = 1; // to readSignIn Result
    private static final int RC_PHOTO_PICKER =  2; // to readPhotoPicker Result

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;
    private RelativeLayout rl;

    private String mUsername;
    private String userGroup;

    View mMainView;


    private FirebaseDatabase mFirebaseDatabase; // this references to the firebase db
    private DatabaseReference mMessagesDatabaseReference; // reference to particular section of db
    private DatabaseReference mUserDatabaseReference;
    private ChildEventListener mChildEventListener; // a listener to listen to a particular section of db
    private FirebaseStorage mFirebaseStorage; // reference to firebase storage

    private FirebaseAuth mFirebaseAUth; // reference to firebase auth

    private StorageReference mChatPhotoStorageReference;
    private FirebaseAuth.AuthStateListener mAuthStateListener; // reference to authstatelistener

    public ChatsFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_chats, container, false);

        //setContentView(R.layout.activity_main);
       // Groups g=new Groups("gg","hh")  ;
        //itemsArrayList=new ArrayList<>();
        //Groups g=new Groups("gg","hh");
       //itemsArrayList.add(g);





        return mMainView;
    }


    @Override
    public void onStart() {
        super.onStart();



            mUsername = ANONYMOUS;

            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAUth = FirebaseAuth.getInstance();
            mFirebaseStorage = FirebaseStorage.getInstance();

            mChatPhotoStorageReference = mFirebaseStorage.getReference().child("chat_photos");

            // Initialize references to views
            mProgressBar = (ProgressBar) mMainView.findViewById(R.id.progressBar);
            mMessageListView = (ListView) mMainView.findViewById(R.id.messageListView);
            mPhotoPickerButton = (ImageButton) mMainView.findViewById(R.id.photoPickerButton);
            mMessageEditText = (EditText) mMainView.findViewById(R.id.messageEditText);
            mSendButton = (Button) mMainView.findViewById(R.id.sendButton);
            rl=(RelativeLayout) mMainView.findViewById(R.id.layoutid);

            // Initialize message ListView and its adapter
            List<FriendlyMessage> friendlyMessages = new ArrayList<>();
            mMessageAdapter = new MessageAdapter(getActivity(), R.layout.item_message, friendlyMessages);
            mMessageListView.setAdapter(mMessageAdapter);

            // Initialize progress bar
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
             //-----------------------


            // Enable Send button when there's text to send
            mMessageEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().trim().length() > 0) {
                        mSendButton.setEnabled(true);
                    } else {
                        mSendButton.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

            // Send button sends a message and clears the EditText
            mSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: Send messages on click
                    FriendlyMessage friendlyMessage = new FriendlyMessage(mMessageEditText.getText().toString(), mUsername, null);

                    mMessagesDatabaseReference.push().setValue(friendlyMessage);
                    // Clear input box
                    mMessageEditText.setText("");
                }
            });


            // ImagePickerButton shows an image picker to upload a image for a message
            mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                }
            });


            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null){
                        // user is logged in
                        String uid = user.getUid();


                        // setting user name in the app


                        // connect to group chat
                        DatabaseReference userGroupReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("group");
                        userGroupReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.e("OH", "WE ARE HERE");
//                            userGroup = "-1";
//                            Log.e("OH PASA",(String) dataSnapshot.getValue());
                                userGroup = (String) dataSnapshot.getValue();
                                if(userGroup != null && !userGroup.isEmpty()){
                                    mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("groups").child(userGroup).child("messages");
                                   // rl.setVisibility(RelativeLayout.VISIBLE);
                                } else{
                                    Toast.makeText(getActivity(), "You're not currently assigned to any group", Toast.LENGTH_SHORT).show();
                                   // Intent intent = new Intent(getActivity(), TempActivity.class);
                                    //startActivity(intent);
                                    rl.setVisibility(RelativeLayout.GONE);
                                }
                            }
                            //
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

//                    Log.e("USER GROUP", userGroup);




                        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("name");
                        mUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String userDisplayName = (String) dataSnapshot.getValue();
                                if (mMessagesDatabaseReference != null){
                                    onSignedInInitialize(userDisplayName);
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        Toast.makeText(getActivity(), "You're signed in", Toast.LENGTH_SHORT).show();
                    } else {
                        // user is logged out, so initate login activity
                        onSignedOutCleanup();
                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setAvailableProviders(
                                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                                        .build(),
                                RC_SIGN_IN);
                    }
                }
            };
        }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                Toast.makeText(getActivity(), "Sign in successful", Toast.LENGTH_SHORT).show();
            } else if (requestCode == RESULT_CANCELED){
                Toast.makeText(getActivity(), "Sign in cancelled", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        } else if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
            Uri selectedImageUri = data.getData();
            StorageReference photoRef = mChatPhotoStorageReference.child(selectedImageUri.getLastPathSegment());
            photoRef.putFile(selectedImageUri).addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    FriendlyMessage friendlyMessage = new FriendlyMessage(null, mUsername, downloadUrl.toString());
                    mMessagesDatabaseReference.push().setValue(friendlyMessage);
                }
            });
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                // sign out
                AuthUI.getInstance().signOut(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        // during pause, we don't need the login state
        if (mAuthStateListener != null){
            mFirebaseAUth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListner();
        mMessageAdapter.clear();

    }

    @Override
    public void onResume(){
        super.onResume();
        // when it resumes, we want the login state
        mFirebaseAUth.addAuthStateListener(mAuthStateListener);
    }

    private void onSignedInInitialize(String username){
        mUsername = username;
        attachDatabaseReadListener();
    }

    private void onSignedOutCleanup(){
        mUsername = ANONYMOUS;
        mMessageAdapter.clear();
    }
    private void attachDatabaseReadListener() {
        if (mChildEventListener == null){
            // Event listener to detect changes to subscribed messages in the db
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
                    mMessageAdapter.add(friendlyMessage);
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

            // Subscribing the listener to the 'messages' in firebase db
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }

    }

    private void detachDatabaseReadListner(){
        if (mChildEventListener != null){
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }

    }

    public void groupList(View v){
        Intent intent = new Intent(getActivity(), GroupMemberList.class);
        startActivity(intent);
    }




}

