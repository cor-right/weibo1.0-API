package org.nefu.softlab.weiboAPI.core.DAO.shell;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Jiaxu_Zou on 2018-4-7
 *
 *
 */
public abstract class BaseDao {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

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
     * @param connection
     * @return
     */
    protected String getIP(Connection connection) {
        Session session = null;
        BufferedReader br = null;
        try {
            session = connection.openSession();
            br = getSout(session);
            session.execCommand("ifconfig | grep \"inet 地址:222\" | awk '{print $2}'");  // 获取ip地址
            return br.readLine().substring(3);
        } catch (IOException e) {
            logger.error("Get server IP failed .");
            return null;
        }   finally {
            try {
                br.close();
                session.close();
            } catch (IOException e) {
                logger.warn("Connection of BufferedReader or Session close failed .");
            }
        }
    }

    /**
     * 获取操作系统总内存大小
     * 返回值单位是字节
     * @param connection
     * @return
     */
    protected long getTotalMemory(Connection connection){
        Session session = null;
        BufferedReader br = null;
        try {
            session = connection.openSession();
            br = getSout(session);
            session.execCommand("cat /proc/meminfo | grep MemTotal | awk '{print $2}'");  // 获取总内存大小
            return Long.parseLong(br.readLine()) * 1024;
        } catch (IOException e) {
            logger.error("Get server Total Memory failed .");
            return 0L;
        }   finally {
            try {
                br.close();
                session.close();
            } catch (IOException e) {
                logger.warn("Connection of BufferedReader or Session close failed .");
            }
        }
    }

    /**
     * 获取可占用的内存大小
     * 返回值单位是字节
     * @param connection
     * @return
     */
    protected long getAvailableMemory(Connection connection) {
        Session session = null;
        BufferedReader br = null;
        try {
            session = connection.openSession();
            br = getSout(session);
            session.execCommand("cat /proc/meminfo | grep MemAva | awk '{print $2}'");  // 获取可用内存大小
            return Long.parseLong(br.readLine()) * 1024;
        } catch (IOException e) {
            logger.error("Get server Available Memory failed .");
            return 0;
        }   finally {
            try {
                br.close();
                session.close();
            } catch (IOException e) {
                logger.warn("Connection of BufferedReader or Session close failed .");
            }
        }
    }

    /**
     * 获取已被占用的内存大小
     * @param connection
     * @return
     */
    protected Long getUsedMemory(Connection connection) {
        return getTotalMemory(connection) - getAvailableMemory(connection);
    }

    /**
     * 获取磁盘总空间
     * @param connection
     * @return
     */
    protected long getTotalDiskSpace(Connection connection) {
        Session session = null;
        BufferedReader br = null;
        try {
            session = connection.openSession();
            br = getSout(session);
            session.execCommand("df  | grep /dev/sda2 | awk '{print $2}'");  // 获取可用内存大小
            return Long.parseLong(br.readLine()) * 1024;
        } catch (IOException e) {
            logger.error("Get server IP failed .");
            return 0L;
        }   finally {
            try {
                br.close();
                session.close();
            } catch (IOException e) {
                logger.warn("Connection of BufferedReader or Session close failed .");
            }
        }
    }

    /**
     * 获取磁盘已占用空间
     * @param connection
     * @return
     */
    protected long getUsedDiskSpace(Connection connection) {
        Session session = null;
        BufferedReader br = null;
        try {
            session = connection.openSession();
            br = getSout(session);
            session.execCommand("df  | grep /dev/sda2 | awk '{print $3}'");  // 获取可用内存大小
            return Long.parseLong(br.readLine()) * 1024;
        } catch (IOException e) {
            logger.error("Get server IP failed .");
            return 0;
        }   finally {
            try {
                br.close();
                session.close();
            } catch (IOException e) {
                logger.warn("Connection of BufferedReader or Session close failed .");
            }
        }
    }

    /**
     * 获取磁盘剩余可用空间
     * @param connection
     * @return
     */
    protected long getAvailableDiskSpace(Connection connection) {
        Session session = null;
        BufferedReader br = null;
        try {
            session = connection.openSession();
            br = getSout(session);
            session.execCommand("df  | grep /dev/sda2 | awk '{print $4}'");  // 获取可用内存大小
            return Long.parseLong(br.readLine()) * 1024;
        } catch (IOException e) {
            logger.error("Get server IP failed .");
            return 0;
        }   finally {
            try {
                br.close();
                session.close();
            } catch (IOException e) {
                logger.warn("Connection of BufferedReader or Session close failed .");
            }
        }
    }


}
