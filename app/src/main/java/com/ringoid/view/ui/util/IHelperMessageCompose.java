package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.model.DataProfile;
import com.ringoid.view.ui.util.listener.IHelperMessageComposeListener;

public interface IHelperMessageCompose {
    void onClick(DataProfile user);

    void addListener(IHelperMessageComposeListener listener);

    void onCloseDialog();
}
