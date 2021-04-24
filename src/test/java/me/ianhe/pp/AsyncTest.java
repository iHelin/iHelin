package me.ianhe.pp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

/**
 * @author iHelin
 * @since 2018/5/9 11:06
 */
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
