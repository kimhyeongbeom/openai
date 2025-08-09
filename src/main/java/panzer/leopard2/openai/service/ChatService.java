package panzer.leopard2.openai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;
import panzer.leopard2.openai.entity.Answer;
import panzer.leopard2.openai.entity.Movie;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ChatService {
    private final ChatClient chatClient;
    private final ChatClient chatClient2;
    private final ChatClient chatClient3;

    public ChatService(ChatClient chatClient,ChatClient chatClient2,ChatClient chatClient3) {
        this.chatClient = chatClient;
        this.chatClient2 = chatClient2;
        this.chatClient3 = chatClient3;
    }
    public String chat(String message) {
        return chatClient.prompt() // 프롬프트 생성
                .user(message) // 사용자 메세지
                .call() // 호출
                .content(); // ChatResponse--> 요청정보를 받는 부분(문자열)
    }

    public String chatmessage(String message) {
        return chatClient2.prompt()
                .user(message) // 뉴턴의 운동 제2법칙을 간단하게 설명하세요
                .call()
                .chatResponse() // ChatResponse
                .getResult()
                .getOutput()
                .getText();
    }
    public String chatplace(String subject, String tone, String message) {
        log.info("subject: {}, tone: {}, message : {}",subject, tone, message);
        return chatClient3.prompt()
                .user(message)
                .system(sp->sp
                        .param("subject",subject)
                        .param("tone", tone)
                )
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();
    }

    public ChatResponse chatjson(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .chatResponse();
    }

    public Answer chatobject(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(Answer.class);
    }

    private final String recipeTemplate= """
           Answer for {foodName} for {question} ?
           """;

    public Answer chatrecipe(String foodName, String question) {
        return chatClient.prompt()
                .user(userSpec->userSpec.text(recipeTemplate)
                        .param("foodName", foodName)
                        .param("question", question)
                )
                .call()
                .entity(Answer.class);
    }

    public List<String> chatlist(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(new ListOutputConverter(new DefaultConversionService()));
    }

    public Map<String, String> chatmap(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<Map<String, String>>(){ });
    }

    public List<Movie> chatmovie(String directorName) {
        String template = """
            Generate a list of movies directed by {directorName}. If the director is unknown, return null.
            한국영화는 한글로 표기해줘.
            Each movie should include a title and release year.{format}
            """;
        List<Movie> movieList = chatClient.prompt()
                .user(userSpec -> userSpec.text(template)
                        .param("directorName", directorName)
                        .param("format", "json"))
                .call()
                .entity(new ParameterizedTypeReference<List<Movie>>() {});
        return movieList;
    }
}
