package com.tongji.xiaohashu.count.biz.redis.count;

/**
 * @author tongji
 * @time 2025/5/20 13:35
 * @description 编解码 5 字节整数
 */
public class Int5Codec {
    public static byte[] encode(long value) {
        if (value < 0 || value >= (1L << 40)) {
            throw new IllegalArgumentException("Value out of Int5 range");
        }
        byte[] result = new byte[5];
        for (int i = 4; i >= 0; i--) {
            result[i] = (byte) (value & 0xFF);
            value >>= 8;
        }
        return result;
    }

    public static long decode(byte[] bytes, int offset) {
        if (offset + 5 > bytes.length) {
            throw new IllegalArgumentException("Byte array too short");
        }
        long value = 0;
        for (int i = 0; i < 5; i++) {
            value <<= 8;
            value |= (bytes[offset + i] & 0xFF);
        }
        return value;
    }
}
