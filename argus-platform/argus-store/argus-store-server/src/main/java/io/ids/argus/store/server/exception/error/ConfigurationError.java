package io.ids.argus.store.server.exception.error;


import io.ids.argus.core.conf.exception.IError;

public enum ConfigurationError implements IError {
    CONF_HOST_ERROR(30000, "host配置有误，未配置[argus.store.host]"),
    CONF_DATABASE_ERROR(30001, "database配置有误，未配置[argus.store.database]"),
    CONF_USERNAME_ERROR(30001, "username配置有误，未配置[argus.store.username]"),
    CONF_AUTH_ERROR(30001, "auth配置有误，未配置[argus.store.auth]"),

    ;
    private final int code;
    private final String msg;

    ConfigurationError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public int getCode() {
        return code;
    }
}