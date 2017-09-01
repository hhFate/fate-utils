package cn.reinforce.plugin.util;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author 幻幻Fate
 * @create 2017-02-06
 * @since 1.0.1
 */
public class SerializeUtil {

    private static final Logger LOG = Logger.getLogger(SerializeUtil.class);

    private SerializeUtil() {
        super();
    }

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        // 序列化
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return baos.toByteArray();
    }

    public static Object unserialize(byte[] bytes) {
        // 反序列化
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (ClassNotFoundException|IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}
