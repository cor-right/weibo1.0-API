package org.nefu.softlab.weiboAPI.core.DAO.shell;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Jiaxu_Zou on 2018-4-7
 *
 *
 */
public abstract class BaseDao {

    /**
     * 获取shell输出
     * @param session
     * @return
     */
    protected BufferedReader getSout(Session session) {
        return new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout())));
    }

    /**
     * 获取IP地址
     * @param session
     * @return
     */
    protected String getIP(Session session, BufferedReader br) {
        try {
            session.execCommand("ifconfig | grep \"inet 地址:222\" | awk '{print $2}'");  // 获取ip地址
            return br.readLine().substring(3);
        } catch (IOException e) {
            return null;
        } finally {
            try {
                br.close();
            } catch (IOException e) {}
        }
    }

    /**
     * 获取操作系统总内存大小
     * 返回值单位是字节
     * @param session
     * @return
     */
    protected long getTotalMemory(Session session, BufferedReader br){
        try {
            session.execCommand("cat /proc/meminfo | grep MemTotal | awk '{print $2}'");  // 获取总内存大小
            return Long.parseLong(br.readLine()) * 1024;
        } catch (IOException e) {
            return 0L;
        } finally {
            try {
                br.close();
            } catch (IOException e) {}
        }
    }

    /**
     * 获取可占用的内存大小
     * 返回值单位是字节
     * @param session
     * @return
     */
    protected long getAvailableMemory(Session session, BufferedReader br) {
        try {
            session.execCommand("cat /proc/meminfo | grep MemAva | awk '{print $2}'");  // 获取可用内存大小
            return Long.parseLong(br.readLine()) * 1024;
        } catch (IOException e) {
            return 0L;
        } finally {
            try {
                br.close();
            } catch (IOException e) {}
        }
    }

    /**
     * 获取已被占用的内存大小
     * @param session
     * @return
     */
    protected Long getUsedMemory(Session session, BufferedReader br) {
        return getTotalMemory(session,br) - getAvailableMemory(session, br);
    }



}
