package com.demo.chat.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@CrossOrigin
public class ChatController {

    private AtomicInteger idProducer = new AtomicInteger();

    @RequestMapping("/getName")
    public String index() {
        return "user" +idProducer.getAndIncrement();
    }
}
