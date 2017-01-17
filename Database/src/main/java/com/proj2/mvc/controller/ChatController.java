package com.proj2.mvc.controller;
import java.util.Date;
import org.slf4j.*;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.proj2.mvc.model.Message;
import com.proj2.mvc.model.OutputMessage;
@Controller
@RequestMapping("/")
public class ChatController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	  @MessageMapping("/chat")
	  @SendTo("/topic/message")
	  public OutputMessage sendMessage(Message message) {
	    logger.info("Message sent");
	    return new OutputMessage(message ,new Date());
	  }
}
