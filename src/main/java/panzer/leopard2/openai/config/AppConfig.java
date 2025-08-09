package panzer.leopard2.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AppConfig {
    @Value("classpath:/prompt.txt")
    private Resource resource;

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder){
        return chatClientBuilder.build();
    }

    @Bean
    public ChatClient chatClient2(ChatClient.Builder chatClientBuilder){
        return chatClientBuilder.defaultSystem("당신은 교육 튜터입니다. 개념을 명확하고 간단하게 설명하세요").build();
    }

    @Bean
    public ChatClient chatClient3(ChatClient.Builder chatClientBuilder){
        return chatClientBuilder.defaultSystem(resource).build();
    }

    @Bean
    ChatMemory chatMemory(ChatMemoryRepository repository) {
        return MessageWindowChatMemory.builder().maxMessages(10).build();
    }

    @Bean
    public ChatClient chatClient4(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory){
        return chatClientBuilder.defaultAdvisors(
                MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
    }
}
