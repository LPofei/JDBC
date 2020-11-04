import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @Description: 管道测试练习
 * @ClassName: TestPipe
 * @Author: Mr.Liang
 * @Date: 2020/11/4 12:07
 * @Version: 1.0
 */
public class TestPipe {
    @Test
    public void test() throws IOException {
        //获取管道
        Pipe pipe = Pipe.open();

        //2.将缓冲区中的数据写入管道
        ByteBuffer buf = ByteBuffer.allocate(1024);

        Pipe.SinkChannel sinkChannel = pipe.sink();
        buf.put("通过单向管道发送数据".getBytes());
        buf.flip();
        sinkChannel.write(buf);

        //3.缓冲区中的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buf.flip();
        int len = sourceChannel.read(buf);
        System.out.println(new String(buf.array(),0,len));

        sourceChannel.close();
        sinkChannel.close();
    }
}
