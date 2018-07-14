package me.ianhe.isite.entity;

import java.io.Serializable;

/**
 * @author iHelin
 * @since 2018/6/5 14:07
 */
public class MenuMeta implements Serializable {

    private static final long serialVersionUID = -8333672448797281982L;

    private boolean keepAlive;
    private boolean requireAuth;

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}
