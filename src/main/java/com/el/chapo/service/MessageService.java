package com.el.chapo.service;

import com.el.chapo.exception.LogicException;
import com.el.chapo.exception.helper.CommonErrorCode;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MessageService {

  public SendMessage send(Update update) {
    if (!update.hasMessage() || !update.getMessage().hasText()) {
      throw new LogicException(CommonErrorCode.JAVA_ERROR, "Обновление не содержит сообщения или текста сообщения");
    }
    String text = update.getMessage().getText();
    Long chatId = update.getMessage().getChatId();
    String responseText;
    switch (text) {
      case "/start" -> responseText = "Здарова, лысый";
      case "/stop" -> responseText = "Прощай, хлопчик";
      default -> responseText = "Неизвестная команда";
    }
    return new SendMessage(chatId.toString(), responseText);
  }
}
