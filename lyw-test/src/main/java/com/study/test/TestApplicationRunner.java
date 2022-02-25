package com.study.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName: TestApplicationRunner
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午11:10
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
@SpringBootApplication
public class TestApplicationRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(TestApplicationRunner.class);

    public static void main(String[] args) {
        SpringApplication.run(TestApplicationRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("app start");
    }
}
