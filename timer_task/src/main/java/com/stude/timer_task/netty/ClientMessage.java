package com.stude.timer_task.netty;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;

/**
 * Created on 2018/12/5.
 *
 * @author grayCat
 * @since 1.0
 */
class ClientMessage {

    public String ip;
    /*总使用率*/
    public String combined;
    /*用户使用率*/
    public String user;
    /*系统使用率*/
    public String sys;
    /*等待率*/
    public String wait;
    /*空闲率*/
    public String idle;
    /*内存总量*/
    public long total;
    /*当前内存使用量*/
    public long used;
    /*当前内存剩余量*/
    public String free;

    public static ClientMessage sigarInfo() {
        ClientMessage sigarMessage = new ClientMessage();
        try {
            sigarMessage.ip = InetAddress.getLocalHost().getHostAddress();
            Sigar sigar = new Sigar();
            //cpu prec
            CpuPerc cpuPerc = sigar.getCpuPerc();
            sigarMessage.combined = parsePercent(cpuPerc.getCombined());
            sigarMessage.user = parsePercent(cpuPerc.getUser());
            sigarMessage.sys = parsePercent(cpuPerc.getSys());
            sigarMessage.wait = parsePercent(cpuPerc.getWait());
            sigarMessage.idle = parsePercent(cpuPerc.getIdle());
            // memory
            Mem mem = sigar.getMem();
            double free = mem.getFree() / 1024L;
            sigarMessage.total = mem.getTotal() / 1024L;
            sigarMessage.used = mem.getUsed() / 1024L;
            sigarMessage.free = parsePercent(1 - (free / sigarMessage.total));
        } catch (UnknownHostException | SigarException e) {
            e.printStackTrace();
        }
        return sigarMessage;
    }

    private static String parsePercent(double nums) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留两位小数
        nf.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);
        String paser = nf.format(nums * 100) + "%";
        return paser;
    }

    @Override
    public String toString() {
        return "ClientMessage{" +
                "ip='" + ip + '\'' +
                ", combined=" + combined +
                ", user=" + user +
                ", sys=" + sys +
                ", wait=" + wait +
                ", idle=" + idle +
                ", total=" + total +
                ", used=" + used +
                ", free=" + free +
                '}';
    }
}
