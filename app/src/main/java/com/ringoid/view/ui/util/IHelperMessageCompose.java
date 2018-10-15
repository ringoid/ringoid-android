package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.view.ui.util.listener.IHelperMessageComposeListener;

public interface IHelperMessageCompose {
    void onClick(String userId);

    void addListener(IHelperMessageComposeListener listener);
}
