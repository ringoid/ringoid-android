package org.byters.ringoid.controller.data.memorycache;

public interface ICacheToken {
    boolean isTokenExist();

    void setToken(String token);

    void resetCache();
}
