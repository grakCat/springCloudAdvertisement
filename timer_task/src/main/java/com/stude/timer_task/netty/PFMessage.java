package com.stude.timer_task.netty;

/**
 * Created on 2018/12/4.
 *
 * @author grayCat
 * @since 1.0
 */
public class PFMessage {

    /* 消息类型 */
    public int messageType;
    /* 子命令字*/
    public int cmd;
    /* 数据*/
    public byte[] data;

    public PFMessage() {
    }

    public PFMessage(int messageType, int cmd, byte[] data) {
        this.messageType = messageType;
        this.cmd = cmd;
        this.data = data;
    }

    @Override
    public String toString() {
        return "PFMessage{" +
                "messageType=" + messageType +
                ", cmd=" + cmd +
                '}';
    }
}
