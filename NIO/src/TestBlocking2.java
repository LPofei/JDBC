import com.sun.org.glassfish.gmbal.Description;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Description: 客户端服务端通信并返回信息
 * @ClassName: TestBlocking2
 * @Author: Mr.Liang
 * @Date: 2020/11/3 19:37
 * @Version: 1.0
 */
public class TestBlocking2 {


    @Test
    public void test() throws IOException {
        //1、获取一个通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 3366));
        FileChannel fChannel = FileChannel.open(Paths.get("zy.jpg"), StandardOpenOption.READ);

        //2.分配指定缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3.读取发送数据
        while (fChannel.read(buf)!=-1){
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }
        sChannel.shutdownOutput();
        //4.接收返回信息,
        // int len=0;
         //while ((len = sChannel.read(buf))!=-1){
        while( sChannel.read(buf)!= -1){
             buf.flip();
             System.out.println(new String(buf.array()));
             buf.clear();
        }

         fChannel.close();
         sChannel.close();
    }

//    @Test
//    public void client() throws IOException{
//        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 3366));
//
//        FileChannel inChannel = FileChannel.open(Paths.get("zy.jpg"), StandardOpenOption.READ);
//
//        ByteBuffer buf = ByteBuffer.allocate(1024);
//
//        while(inChannel.read(buf) != -1){
//            buf.flip();
//            sChannel.write(buf);
//            buf.clear();
//        }
//
//        sChannel.shutdownOutput();
//
//        //接收服务端的反馈
//        int len = 0;
//        while((len = sChannel.read(buf)) != -1){
//            buf.flip();
//            System.out.println(new String(buf.array(), 0, len));
//            buf.clear();
//        }
//
//        inChannel.close();
//        sChannel.close();
//    }
//
//    @Test
//    public void server() throws IOException{
//        ServerSocketChannel ssChannel = ServerSocketChannel.open();
//
//        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
//
//        ssChannel.bind(new InetSocketAddress(3366));
//
//        SocketChannel sChannel = ssChannel.accept();
//
//        ByteBuffer buf = ByteBuffer.allocate(1024);
//
//        while(sChannel.read(buf) != -1){
//            buf.flip();
//            outChannel.write(buf);
//            buf.clear();
//        }
//
//        //发送反馈给客户端
//        buf.put("服务端接收数据成功".getBytes());
//        buf.flip();
//        sChannel.write(buf);
//
//        sChannel.close();
//        outChannel.close();
//        ssChannel.close();
//    }


    //服务端
    @Test
    public void test1() throws IOException {
        //获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        FileChannel outChannel = FileChannel.open(Paths.get("有返回信息的朱茵7.jpg"),StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        //绑定端口号
        ssChannel.bind(new InetSocketAddress(3366));
        //接收一个通道
        SocketChannel aChannel = ssChannel.accept();

        //分配缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);


        while (aChannel.read(buf)!=-1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }
        //System.out.println("fdafadfas");
        //发送接收完成信息
        buf.put("服务端接收数据成功".getBytes());
        buf.flip();
        aChannel.write(buf);

        ssChannel.close();
        outChannel.close();
        ssChannel.close();

    }

}
