package com.chenyacheng.androidutils;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * 枚举单例模式
 * 线程安全
 * 实现延迟加载
 *
 * @author chenyacheng
 * @date 2020/04/22
 */
public class GlobalHandler extends Handler {

    private HandleMsgListener listener;

    private GlobalHandler() {
    }

    /**
     * 对外暴露一个获取GlobalHandler对象的静态方法
     *
     * @return GlobalHandler对象
     */
    static GlobalHandler getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (null != getHandleMsgListener()) {
            getHandleMsgListener().handleMsg(msg);
        }
    }

    private HandleMsgListener getHandleMsgListener() {
        return listener;
    }

    void setHandleMsgListener(HandleMsgListener listener) {
        this.listener = listener;
    }

    enum SingletonEnum {
        // 创建一个枚举对象，该对象天生为单例
        INSTANCE;
        private final GlobalHandler globalHandler;

        SingletonEnum() {
            globalHandler = new GlobalHandler();
        }

        GlobalHandler getInstance() {
            return globalHandler;
        }
    }

    public interface HandleMsgListener {
        /**
         * 返回handler消息对象
         *
         * @param msg Message对象
         */
        void handleMsg(Message msg);
    }
}
