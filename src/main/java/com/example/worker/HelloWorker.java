package com.example.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by donghoon on 2016. 5. 21..
 */
@Component
@Scope("prototype")
public class HelloWorker implements Runnable {

    private Logger logger = LoggerFactory.getLogger(HelloWorker.class);

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        task(getName());
    }

    public void task(String name) {
        logger.info("Hello, I'm {}, Task start", name);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Good bye, Task end.");
    }
}
