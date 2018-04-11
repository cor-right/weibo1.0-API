package org.nefu.softlab.weiboAPI.core.dao.shell;

import ch.ethz.ssh2.Connection;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * SSHDao测试类
 */
public class SSHDaoTest extends SSHDao{

    @Test
    public void test() {
        List<Connection> connections = getConnections();
        connections.stream()
                .forEach(connection -> {
                    System.out.println("IP : " + getIP(connection));
                    System.out.println("Total Memory : " + getTotalMemory(connection));
                    System.out.println("Available Memory : " + getAvailableMemory(connection));
                    System.out.println("Used Memeory : " + getUsedMemory(connection));
                    System.out.println("Used Disk : " + getUsedMemory(connection));
                    System.out.println("Used Memeory : " + getUsedMemory(connection));
                    System.out.println("Used Memeory : " + getUsedMemory(connection));
                });
    }

}
