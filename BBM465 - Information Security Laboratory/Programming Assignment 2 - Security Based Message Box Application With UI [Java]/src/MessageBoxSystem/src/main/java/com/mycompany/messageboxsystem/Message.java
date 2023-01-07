package com.mycompany.messageboxsystem;

public class Message {
    // Variables
    private String message_id;
    private String content;
    private String sender;
    private String receiver;
    private String password;

    // Constructor
    public Message(String message_id, String content, String sender, String receiver, String password) {
        this.message_id = message_id;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.password = password;
    }
    
    // Getter and Setter Methods
    public String getMessageId(){
        return message_id;
    }
    
    public void setMessageId(String newMessageId){
        this.message_id = newMessageId;
    }
    
    public String getContent(){
        return content;
    }
    
    public void setContent(String newContent){
        this.content = newContent;
    }
    
    public String getSender(){
        return sender;
    }
    
    public void setSender(String newSender){
        this.sender = newSender;
    }
    
    public String getReceiver(){
        return receiver;
    }
    
    public void setReceiver(String newReceiver){
        this.receiver = newReceiver;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String newPassword){
        this.password = SecurityFunctions.getMD5Hash(newPassword);
    }
}
