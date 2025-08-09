package panzer.leopard2.openai.controller;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import panzer.leopard2.openai.entity.Answer;
import panzer.leopard2.openai.service.ChatService;

import java.util.List;
import java.util.Map;

@RestController
public class ChatController {
    private final ChatService chatService;
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // LLM(gpt-4o)와 통신할수있는 객체 : ChatClient
    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message){
        return chatService.chat(message);
    }

    @GetMapping("/chatmessage")
    public String chatmessage(@RequestParam("message") String message){
        return chatService.chatmessage(message);
    }

    @GetMapping("/chatplace")
    public String chatplace(String subject, String tone, String message){
        return chatService.chatplace(subject, tone, message);
    }

    @GetMapping("/chatjson")
    public ChatResponse chatjson(String message){
        return chatService.chatjson(message);
    }

    @GetMapping("/chatobject")
    public Answer chatobject(String message){
        return chatService.chatobject(message);
    }

    @GetMapping("/chatrecipe")
    public Answer chatrecipe(String foodName, String question){
        return chatService.chatrecipe(foodName, question);
    }

    @GetMapping("/chatlist")
    public List<String> chatlist(String message){
        return chatService.chatlist(message);
    }

    @GetMapping("/chatmap")
    public Map<String, String> chatmap(String message){
        return chatService.chatmap(message);
    }
}
