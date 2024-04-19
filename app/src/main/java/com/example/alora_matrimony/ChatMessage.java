package com.example.alora_matrimony;

public class ChatMessage {
    private String messageText;
    private String senderUid;
    private String recieverUid;
    private long timestamp;
    private String senderProfileImageUrl;
    private  String messageId;// New field for sender's profile image URL

    public ChatMessage() {
        // Default constructor required for calls to DataSnapshot.getValue(ChatMessage.class)

    }

    public ChatMessage(String messageText, String senderUid, String recieverUid, String senderProfileImageUrl,String messageId) {
        this.messageText = messageText;
        this.senderUid = senderUid;
        this.recieverUid = recieverUid;
        this.timestamp = System.currentTimeMillis();
        this.senderProfileImageUrl = senderProfileImageUrl;
        this.messageId=messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }
    public String getRecieverUid() {
        return recieverUid;
    }

    public void setRecieverUid(String recieverUid) {
        this.recieverUid = recieverUid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderProfileImageUrl() {
        return senderProfileImageUrl;
    }

    public void setSenderProfileImageUrl(String senderProfileImageUrl) {
        this.senderProfileImageUrl = senderProfileImageUrl;
    }
}
