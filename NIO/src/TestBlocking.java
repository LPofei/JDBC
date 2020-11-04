import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Description: 测试阻塞IO
 * @ClassName: TestBlocking
 * @Author: Mr.Liang
 * @Date: 2020/11/3 19:19
 * @Version: 1.0
 */

//用通道实现一个客户端和服务端的通信
public class TestBlocking {

    //客户端
    @Test
    public void test() throws IOException {
        //1.获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 4545));
        FileChannel inChannel = FileChannel.open(Paths.get("zy.jpg"), StandardOpenOption.READ);

        //2.分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3.读取本地文件，并发送到服务器

        while (inChannel.read(buf)!=-1){
            buf.flip();//切换读取模式；
            sChannel.write(buf);
            buf.clear();
        }

        inChannel.close();
        sChannel.close();
    }

    //服务端
    @Test
    public void test1() throws IOException {
        //1.获取通道
        ServerSocketChannel sChannel = ServerSocketChannel.open();
        FileChannel outChannl = FileChannel.open(Paths.get("接收到的朱茵.jpg"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //2.绑定连接
        sChannel.bind(new InetSocketAddress(4545));

        //2、分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3.接受数据
        SocketChannel aChannel = sChannel.accept();

        while (aChannel.read(buf)!=-1){
            buf.flip();
            outChannl.write(buf);
            buf.clear();
        }
        sChannel.close();
        outChannl.close();
        aChannel.close();
    }





}
