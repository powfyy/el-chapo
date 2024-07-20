package com.el.chapo.service;

import com.el.chapo.exception.LogicException;
import com.el.chapo.exception.helper.CommonErrorCode;
import com.el.chapo.properties.TelegramBotProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

  private final TelegramBotProperties properties;
  private final MessageService messageService;

  @Override
  public String getBotUsername() {
    return properties.getUsername();
  }

  @Override
  public String getBotToken() {
    return properties.getToken();
  }

  @Override
  public void onUpdateReceived(Update update) {
    try {
      SendMessage sendMessage = messageService.send(update);
      execute(sendMessage);
    } catch (TelegramApiException e) {
      throw new LogicException(CommonErrorCode.JAVA_ERROR, e);
    }
  }
}
