package com.haemeta.common.interfaces;

public interface TokenInterface<User> {

    String token();
    String token(String key);
    String token(Long duration);
    String token(String key,Long duration);

    void duration(Long duration);
    Long duration();

    User getUser();

}
