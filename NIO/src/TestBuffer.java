import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * @Description: 缓冲区测试
 * @ClassName: TestBuffer
 * @Author: Mr.Liang
 * @Date: 2020/11/3 14:58
 * @Version: 1.0
 */
public class TestBuffer {
    @Test
    public void test(){
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        //查看是否是直接存储模式
        System.out.println(allocate.isDirect());
    }
    @Test
    public void test1(){
        String str = "abcde";
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        //添加数据
        allocate.put(str.getBytes());
        //查看指针位置
        System.out.println(allocate.position());
        //查看可操作数，因为现在是写模式，所以可操作数就是数组的长度
        System.out.println(allocate.limit());
        //切换到读模式
        allocate.flip();
        //因为只能读到五个数据，所以可操作数是5
        System.out.println(allocate.limit());
        //查看缓冲区容量，
        System.out.println(allocate.capacity());

    }

    @Test
    public void test2(){
    //测试mark
        String str  = "23123123";
        ByteBuffer buff = ByteBuffer.allocate(1024);
        buff.put(str.getBytes());
//        System.out.println(buff);
        buff.flip();//切换到读模式
        byte[] bytes = new byte[buff.limit()];//得到一个只包含存入的数据的byte数组
        buff.get(bytes,0,2);//获取从0-2的数据存到bytes数组中
        System.out.println(new String(bytes, 0, 2));//打印bytes数组数据
        System.out.println(buff.position());
        buff.mark(); //记录当前指针位置

        buff.get(bytes,2,5);
//        System.out.println(bytes);
            System.out.println(buff.position());
        System.out.println(new String(bytes, 0, 5));
        System.out.println(buff.position());
        buff.reset();
        System.out.println(buff.position());
        buff.rewind();
        System.out.println(buff.position());
        buff.clear();
        System.out.println(buff.limit());

    }
}
