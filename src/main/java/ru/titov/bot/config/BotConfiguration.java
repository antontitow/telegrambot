package ru.titov.bot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @autor : Anton Titov {@literal antontitow@bk.ru}
 * @created : 28.10.2023, 22:52
 **/
@Configuration
@PropertySource("application.properties")
@Data
public class BotConfiguration {

    @Value("${telegram.botName}")
    String botName;

    @Value("${telegram.token}")
    String token;
}
