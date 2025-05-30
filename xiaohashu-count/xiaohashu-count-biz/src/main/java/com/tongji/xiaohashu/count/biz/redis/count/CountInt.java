package com.tongji.xiaohashu.count.biz.redis.count;

/**
 * @author tongji
 * @time 2025/5/20 13:37
 * @description 封装多个 Int5 字段对象
 */
public class CountInt {
    private final long[] fields;

    public CountInt(int fieldCount) {
        this.fields = new long[fieldCount];
    }

    public void set(int index, long value) {
        fields[index] = value;
    }

    public long get(int index) {
        return fields[index]; // [30, 20, 38]
    }

    public int size() {
        return fields.length;
    }

    public byte[] toBytes() {
        byte[] result = new byte[fields.length * 5];
        for (int i = 0; i < fields.length; i++) {
            byte[] encoded = Int5Codec.encode(fields[i]);
            System.arraycopy(encoded, 0, result, i * 5, 5);
        }
        return result;
    }

    public static CountInt fromBytes(byte[] data, int fieldCount) {
        CountInt obj = new CountInt(fieldCount);
        for (int i = 0; i < fieldCount; i++) {
            long value = Int5Codec.decode(data, i * 5);
            obj.set(i, value);
        }
        return obj;
    }
}
