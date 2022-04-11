package nl.bd.sdbackendopdracht.models;


import org.springframework.context.annotation.Bean;

public class Mail {
    @Bean
    public String getMail(String name){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Mail</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Welkom " + name + "!</h1>\n" +
                "</body>\n" +
                "</html>";
    }
}
