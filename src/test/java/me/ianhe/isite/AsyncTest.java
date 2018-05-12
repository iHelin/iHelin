package me.ianhe.isite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author iHelin
 * @since 2018/5/9 11:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncTest {

    @Test
    public void test() {
        System.out.println(1 + Thread.currentThread().getName());
        asyncMethodWithVoidReturnType();
        System.out.println(2 + Thread.currentThread().getName());
    }

    @Async  //标注使用
    public void asyncMethodWithVoidReturnType() {
        System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
    }
}
