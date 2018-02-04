package com.kumar.mrdroid.chatapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by mrdroid on 2/2/18.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater()
                    .inflate(R.layout.item_message, parent, false);
        }

        ImageView mImage = convertView.findViewById(R.id.photoImageView);
        TextView mMessage = convertView.findViewById(R.id.messageTextView);
        TextView mSenderName = convertView.findViewById(R.id.nameTextView);

        // get position of message
        Message messageObject  = getItem(position);
        boolean hasImage = messageObject.getmPhotoUrl() != null;

        /***
         *    If  Image is send as the message
         */
        if(hasImage){
            mImage.setVisibility(View.VISIBLE);
            mMessage.setVisibility(View.GONE);

            //render image using glide library in mImage
            Glide.with(mImage.getContext())
                    .load(messageObject.getmPhotoUrl())
                    .into(mImage);

        }else{
            /***
             *    If  text is send as the message
             */
            mMessage.setVisibility(View.VISIBLE);
            mImage.setVisibility(View.GONE);
            mMessage.setText(messageObject.getmMessage());
        }

        mSenderName.setText(messageObject.getmSenderName());
        return convertView;
    }

}
