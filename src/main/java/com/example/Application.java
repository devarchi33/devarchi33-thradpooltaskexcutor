package com.example;

import com.example.worker.HelloWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private HelloWorker worker1;
    @Autowired
    private HelloWorker worker2;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        worker1.setName("worker1");
        executor.execute(worker1);
        worker2.setName("worker2");
        executor.execute(worker2);


        for (; ; ) {
            int count = executor.getActiveCount();
            logger.info("Active thread: {}", count);

            Thread.sleep(1000);

            if (count == 0) {
                executor.shutdown();
                System.exit(0);
            }
        }

    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }
}
