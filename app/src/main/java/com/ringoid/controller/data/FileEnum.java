/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data;

import com.ringoid.model.DataBlacklistPhones;
import com.ringoid.model.DataTutorial;
import com.ringoid.model.DataUser;
import com.ringoid.model.ModelChat;
import com.ringoid.model.ModelFeedExplore;
import com.ringoid.model.ModelFeedLikes;
import com.ringoid.model.ModelFeedMessages;
import com.ringoid.model.ModelInterfaceState;
import com.ringoid.model.ModelProfilePhotos;
import com.ringoid.model.ModelUserRegister;

public enum FileEnum {
    TOKEN("token", String.class),
    USER("user", DataUser.class),
    TUTORIAL("tutorial", DataTutorial.class),
    BLACKLIST("blacklist", DataBlacklistPhones.class),
    CACHE_PROFILE("profile_photos", ModelProfilePhotos.class),
    CHAT_CACHE("chat_cache", ModelChat.class),
    CACHE_INTERFACE("cache_interface", ModelInterfaceState.class),
    CACHE_FEED_LIKES("cache_feed_likes", ModelFeedLikes.class),
    CACHE_FEED_MESSAGES("cache_feed_messages", ModelFeedMessages.class),
    CACHE_FEED_EXPLORE("cache_feed_explore", ModelFeedExplore.class),
    CACHE_USER_REGISTER("cache_user_register", ModelUserRegister.class);

    private Class className;
    private String filename;

    FileEnum(String filename, Class classname) {
        this.filename = filename;
        this.className = classname;
    }

    public String getFilename() {
        return filename;
    }

    public Class getClassname() {
        return className;
    }
}
