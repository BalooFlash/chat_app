package com.kumar.mrdroid.chatapp;

/**
 * Created by mrdroid on 2/2/18.
 */

public class Message {
    private String mMessage;
    private String mSenderName;
    private String mPhotoUrl;

    public Message() {
    }

    public Message(String mMessage, String mSenderName, String mPhotoUrl) {
        this.mMessage = mMessage;
        this.mSenderName = mSenderName;
        this.mPhotoUrl = mPhotoUrl;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmSenderName() {
        return mSenderName;
    }

    public void setmSenderName(String mSenderName) {
        this.mSenderName = mSenderName;
    }

    public String getmPhotoUrl() {
        return mPhotoUrl;
    }

    public void setmPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }
}
