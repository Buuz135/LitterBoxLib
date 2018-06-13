package com.buuz135.litterboxlib.proxy.common.tile.container.handler;

public enum FaceMode {
    NONE(false), ENABLED(true), PUSH(true), PULL(true);


    private final boolean allowsConnection;

    private FaceMode(boolean allowsConnection) {
        this.allowsConnection = allowsConnection;
    }

    public boolean isAllowsConnection() {
        return allowsConnection;
    }
}
