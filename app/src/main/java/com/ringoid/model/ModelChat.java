package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.BuildConfig;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelChat implements Serializable {

    private ArrayList<ChatInstance> data;

    public ModelChat() {
        addDebugData();
    }

    private void addDebugData() {
        if (!BuildConfig.DEBUG) throw new IllegalArgumentException();

        add(new DataMessage(true, "Hey :)"), "123");
        add(new DataMessage(false, "Hi"), "123");
        add(new DataMessage(true, "How are you doing?"), "123");
        add(new DataMessage(true, "I've never been better"), "123");
        add(new DataMessage(false, "I'm OK"), "123");
        add(new DataMessage(true, "Awesome. Look, You seem pretty cool. Do you want to meet"), "123");
        add(new DataMessage(false, "Not so fast. I do not know you"), "123");
        add(new DataMessage(true, "That's OK, we will meet and I can talk you through whatever you want to know. Just give me a chance"), "123");
        add(new DataMessage(false, "No, thank you. You look creepy"), "123");
        add(new DataMessage(true, "I'm not a creep. I really like your photos"), "123");
        add(new DataMessage(false, "Oh, you are also needy... So boring"), "123");
        add(new DataMessage(true, "Well, but what I'm supposed to do?"), "123");
        add(new DataMessage(true, "Give me a chance, please"), "123");
        add(new DataMessage(true, "Here is my address:\n" +
                "123 Home street\n" +
                "Luckyville\n" +
                "Manchester\n" +
                "M11 M22"), "123");
        add(new DataMessage(true, "And my phone number\n" +
                "072340820342"), "123");
        add(new DataMessage(true, "The moment I saw your photos I felt in love. Please let me meet you and I will do whatever you want. Just give me a chance!!! We can meet somewhere in the center so you can feel safe. And I will pay for everything so it won't cost you anything... I promise you will enjoy yourself!"), "123");
        add(new DataMessage(false, "Okey let's not waste time... what time shall I come to your place?"), "123");
        add(new DataMessage(false, "Ha ha, just joking!"), "123");
        add(new DataMessage(false, "On a serious note, do you tell this to every girl here?"), "123");
    }

    public void add(DataMessage dataMessage, String userSelectedID) {
        if (data == null) data = new ArrayList<>();

        ChatInstance item = getItem(userSelectedID);
        if (item == null) {
            item = new ChatInstance(userSelectedID);
            data.add(item);
        }
        item.add(dataMessage);
    }

    private ChatInstance getItem(String userSelectedID) {
        if (data == null) return null;
        for (ChatInstance item : data)
            if (item.isWithUser(userSelectedID))
                return item;
        return null;
    }

    public int size(String userId) {
        ChatInstance item = getItem(userId);
        if (item == null) return 0;
        return item.size();

    }

    public boolean isSelf(String userSelectedID, int position) {
        ChatInstance item = getItem(userSelectedID);
        if (item == null) return false;
        return item.isSelf(position);
    }

    public String getMessage(String userSelectedID, int position) {
        ChatInstance item = getItem(userSelectedID);
        if (item == null) return null;
        return item.getMessage(position);
    }

    public void resetCache(String userSelectedID) {
        if (data == null) return;
        ChatInstance item = getItem(userSelectedID);
        if (item == null) return;
        data.remove(item);
    }

    public boolean isReaded(String userId) {
        ChatInstance item = getItem(userId);
        if (item == null) return false;
        return item.isReaded();
    }

    public void setReaded(String userSelectedID) {
        ChatInstance item = getItem(userSelectedID);
        if (item == null) return;
        item.setReaded();
    }
}
