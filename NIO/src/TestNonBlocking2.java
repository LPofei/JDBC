import javafx.beans.binding.When;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Description: UDP,非阻塞IO通信
 * @ClassName: TestNonBlocking2
 * @Author: Mr.Liang
 * @Date: 2020/11/4 11:21
 * @Version: 1.0
 */
public class TestNonBlocking2 {
    //客户端
    @Test
    public void test() throws IOException {
        //1.获取通道
        DatagramChannel dc = DatagramChannel.open();

        //2.切换为非阻塞模式
        dc.configureBlocking(false);

        //3.分配缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4.发送数据
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String str = sc.next();
            buf.put((new Date().toString()+"\n"+str).getBytes());
            //切换到读模式
            buf.flip();
            dc.send(buf,new InetSocketAddress("127.0.0.1",3344));
            buf.clear();
        }
        sc.close();

    }
    //服务端
    @Test
    public void test1() throws IOException {
        //1.获取通道
        DatagramChannel dc = DatagramChannel.open();

        //2.切换为非阻塞模式
        dc.configureBlocking(false);

        //3.绑定端口号
        dc.bind(new InetSocketAddress(3344));

        //获取选择器
        Selector selector = Selector.open();

        //4.注册通道到选择器,读事件
        dc.register(selector, SelectionKey.OP_READ);

        //判断是否有准备就绪的通道
        while (selector.select()>0){
            //获取通道里面所有的事件，用迭代器进行迭代
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()){
                //获取通道事件
                SelectionKey sk = it.next();
                //判断时间是不是可读的事件
                if(sk.isReadable()){
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    //把通道获取到的数据写到buf中
                    dc.receive(buf);
                    buf.flip();
                    System.out.println(new String(buf.array(),0,buf.limit()));
                    buf.clear();

                }
            }
            it.remove();

        }

    }

}
