package org.example.structure.base;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DecimalFormat;

/**
 * @Name LearnJvm
 * @Description
 * @Author qfu1
 * @Date 2023-04-01
 */
public class LearnJvm implements Serializable {
    private static final long serialVersionUID = 7570121722455220036L;

    public static void main(String[] args) {
        // get name representing the running Java virtual machine.
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);
        // get pid
        String pid = name.split("@")[0];
        System.out.println("Pid is:" + pid);
    }

    /**
     * 单位转换
     *
     * @param byteNumber number
     * @return desc
     */
    public static String formatByte(long byteNumber) {
        //换算单位
        double FORMAT = 1024.0;
        double kbNumber = byteNumber / FORMAT;
        if (kbNumber < FORMAT) {
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber / FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }
}
