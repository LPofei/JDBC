import javax.xml.transform.Source;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

/**
 * @Description: 测试闭锁
 * @ClassName: CountDownLatchTest
 * @Author: Mr.Liang
 * @Date: 2020/11/3 9:55
 * @Version: 1.0
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        CountDownLatch cd = new CountDownLatch(50);
        Instant start = Instant.now();
        LatchDemo ld = new LatchDemo(cd);
        for (int i = 0; i < 50; i++) {
            new Thread(ld).start();
        }
        try {
            cd.await();
        } catch (InterruptedException e) {
        }
        Instant end = Instant.now();
        System.out.println("耗费时间为：" + Duration.between(start, end));
    }

}

class LatchDemo implements Runnable {

    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {


        try {
            for (int i = 0; i < 500; i++) {
                if (i % 2 == 0) {

//                    String sr r = "fdsf dsfdf";
                    System.out.println(i);

                }

            }
        } finally {
            latch.countDown();
        }
    }
}