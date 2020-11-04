import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Description: 通道练习
 * @ClassName: TestChannel
 * @Author: Mr.Liang
 * @Date: 2020/11/3 16:28
 * @Version: 1.0
 */
public class TestChannel {

    //利用通道完成文件的赋值
    @Test
    public void test() throws Exception {
        //1、获取通道
        FileInputStream fis = new FileInputStream(new File("zy.jpg"));
        FileOutputStream fos = new FileOutputStream("朱茵的复制品.jpg");
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        //2.分配指定大小的缓冲区
        ByteBuffer buff = ByteBuffer.allocate(1024);

        //3、将通道中的数据存入缓冲区中
        while (inChannel.read(buff)!=-1){
            buff.flip();  //切换读取数据的模式
            //4、将缓冲区中的数据写入通道中
            outChannel.write(buff);
            buff.clear();//清除缓冲区
        }
        fis.close();
        fos.close();
        inChannel.close();
        outChannel.close();
    }
    //使用直接缓冲区完成对文件的复制
    @Test
    public void test1() throws IOException {
        //1.获取通道
        FileChannel inChannel = FileChannel.open(Paths.get("F:\\zy.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("F:\\直接复制朱茵.jpg"),
                StandardOpenOption.CREATE,
                StandardOpenOption.READ,
                StandardOpenOption.WRITE);

        //2.内存映射文件
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        
        //3.直接对缓冲区进行数据的读写操作
        byte[] dst = new byte[inMappedBuf.limit()];

        inMappedBuf.get(dst);//把inMappedBuf里面的数据写到dst字节数组中
        outMappedBuf.put(dst);//把字节数组中的数据写到outMappedBuf中

        inChannel.close();
        outChannel.close();
    }

    //通道之间的数据传输
    @Test
    public void test2() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("F:\\zy.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("F:\\通道复制朱茵.jpg"), StandardOpenOption.CREATE,
                StandardOpenOption.READ,
                StandardOpenOption.WRITE);

        inChannel.transferTo(0,inChannel.size(), outChannel);

        inChannel.close();
        outChannel.close();

    }

    //分散和聚集写入
    @Test
    public void test3() throws Exception {

        RandomAccessFile raf1 = new RandomAccessFile("zy.jpg", "rw");
        //1.获取通道
        FileChannel channel1 = raf1.getChannel();

        //2.分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(10240);
        ByteBuffer buf2 = ByteBuffer.allocate(16480);

        //3.分散读取
        ByteBuffer[] bufs = {buf1, buf2};
            channel1.read(bufs);  //channel1的数据读取后写到bufs中，数据分为buf1和buf2两部分

            for (ByteBuffer buf : bufs) {
                buf.flip();//给每个缓冲区切换成读取状态
            }

            //4、聚集写入
            RandomAccessFile raf2 = new RandomAccessFile("分散读取朱茵2.jpg", "rw");
            FileChannel channel2 = raf2.getChannel();

            channel2.write(bufs);

            channel1.close();
            channel2.close();

        }

    //字符集
    @Test
    public void test4() throws CharacterCodingException {
        Charset cs1 = Charset.forName("GBK");

        //获取编码器
        CharsetEncoder ce = cs1.newEncoder();

        //获取解码器
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cbuf = CharBuffer.allocate(1024);
        cbuf.put("晚风吹动着竹林");
        cbuf.flip();//切换到读取模式

        //编码
        ByteBuffer bbuf = ce.encode(cbuf);

//        for (int i = 0; i < 14; i++) {
//            System.out.println(bbuf.get());
//        }

        //System.out.println(bbuf.position());
        //解码
       // bbuf.flip();//切换到读取模式,此时postion的位置就是limit的位置
        System.out.println(bbuf.position());
        System.out.println(bbuf.limit());
//        System.out.println(bbuf.limit());
        CharBuffer dbuf = cd.decode(bbuf);
        System.out.println(dbuf.toString());

        Charset cs2 = Charset.forName("UTF-8");
        bbuf.flip();
        CharBuffer cBuf3 = cs2.decode(bbuf);
        System.out.println(cBuf3.toString());

    }
    

}
