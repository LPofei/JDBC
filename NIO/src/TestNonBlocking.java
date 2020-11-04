
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Description: 测试非阻塞IO\
 * @ClassName: TestNonBlocking
 * @Author: Mr.Liang
 * @Date: 2020/11/3 20:21
 * @Version: 1.0
 */
public class TestNonBlocking {

    //客户端
    @Test
    public void test() throws IOException {
        //1、获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 3344));

        //2、切换非阻塞模式
        sChannel.configureBlocking(false);

        //3、分配指定大小的内存
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4.发送数据给服务器端

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String str = sc.next();
            buf.put((new Date().toString()+"\n"+str).getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //5.关闭通道
        sChannel.close();
    }

    //服务端
    @Test
    public void test1() throws IOException {
        //1、获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        //2.切换非阻塞模式
        ssChannel.configureBlocking(false);

        //3.绑定连接
        ssChannel.bind(new InetSocketAddress(3344));

        //4.获取选择器
        Selector selector = Selector.open();

        //5.将通道注册在选择器上

        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        //6.轮询式的获取选择器上已就绪的监听事件”
        while (selector.select()>0) {

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            //7.获取当前选择器中所有注册的选择键（已就绪的监听事件）
            while (it.hasNext()) {
                //8.获取准备就绪的是事件
                SelectionKey sk = it.next();

                //9.判断具体是 什么事件准备就绪

                if (sk.isAcceptable()) {
                    //10.若接收就绪，获取客户端连接

                    SocketChannel sChannel = ssChannel.accept();

                    //11、切换非阻塞模式
                    sChannel.configureBlocking(false);

                    //12.将该通道注册到选择器上
                    sChannel.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    //13.获取当前选择器上“读就绪”状态的通道
                    SocketChannel sChannel = (SocketChannel) sk.channel();

                    //14、读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    int len = 0;
                    while ((len = sChannel.read(buf)) >0) {
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }

                }
                //15.取消选择见SlectionKey
                it.remove();
            }
        }

    }

}
