package com.utile;

/**
 * Created by guom on 2020/2/28.
 */
public class IPS {
    // 校验 IPv4 地址
    public static boolean isIp(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        String[] parts = text.trim().split("\\.", -1);
        if (parts.length != 4) {
            return false;
        }
        for (String part : parts) {
            if (part.isEmpty()) {
                return false;
            }
            if (part.length() > 1) {
                if (part.charAt(0) == '0') {
                    return false;
                }
            }
            for (int i = 0; i < part.length(); i++) {
                if (!Character.isDigit(part.charAt(i))) {
                    return false;
                }
            }
            try {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    //true 是ip地址+端口号( IP:端口 )
    public static boolean isPairAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return false;
        }
        //兼容从手机上复制时出现的中文冒号或空格
        address = address.trim().replace('：', ':').replaceAll("\\s+", "");
        int separator = address.lastIndexOf(':');
        if (separator <= 0 || separator == address.length() - 1) {
            return false;
        }
        String ip = address.substring(0, separator);
        String portText = address.substring(separator + 1);
        if (!IPS.isIp(ip)) {
            return false;
        }
        try {
            int port = Integer.parseInt(portText);
            return port > 0 && port <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
