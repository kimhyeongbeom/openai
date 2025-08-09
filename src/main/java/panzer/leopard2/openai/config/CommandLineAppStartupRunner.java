package panzer.leopard2.openai.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import panzer.leopard2.openai.service.ChatService;

@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final ChatService chatService;

    @Override
    public void run(String... args) throws Exception {
        chatService.startChat();
    }
}
