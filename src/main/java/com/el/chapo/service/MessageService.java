package com.el.chapo.service;

import com.el.chapo.util.MainMenuUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final SberService sberService;

  public SendMessage send(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      String text = update.getMessage().getText();
      Long chatId = update.getMessage().getChatId();
      String responseText;
      switch (text) {
        case "/start" -> {
          return MainMenuUtil.getMainMenuMessage(chatId, "Здарова, лысый");
        }
        case "Сбербанк" -> {
          return sberService.getTransferChoice(chatId);
        }
        default -> {
          return MainMenuUtil.getMainMenuMessage(chatId, "Неизвестная команда. Воспользуйся меню управления.");
        }
      }

    }

    return null;
  }
}
