package ru.titov.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.titov.bot.config.BotConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor : Anton Titov {@literal antontitow@bk.ru}
 * @created : 28.10.2023, 23:00
 **/
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    private final BotConfiguration configuration;

    @Autowired
    public Bot(BotConfiguration configuration) {
        this.configuration = configuration;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("take message");
            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText().toLowerCase();
            switch (text) {
                case "/start":
                    getStarted(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(chatId, "oy");
            }

        }
    }

    private void getStarted(Long chatId, String firstName) {
        String responceMessage = "Привет " + firstName;

        sendMessage(chatId, responceMessage.toString());
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = new
                ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("Команда 1");
        keyboardFirstRow.add(KeyboardButton.builder().text("ty").build());
        keyboardFirstRow.add("Команда 2");
        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException telegramApiException) {
        }
    }

    @Override
    public String getBotUsername() {
        log.info("name" + configuration.getBotName());
        return configuration.getBotName();
    }

    @Override
    public String getBotToken() {
        log.info("token" + configuration.getToken());
        return configuration.getToken();
    }
}
