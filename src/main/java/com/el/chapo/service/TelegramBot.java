package com.el.chapo.service;

import com.el.chapo.exception.LogicException;
import com.el.chapo.exception.helper.CommonErrorCode;
import com.el.chapo.properties.BankProperties;
import com.el.chapo.properties.TelegramBotProperties;
import com.el.chapo.util.DemoScreenUtil;
import com.el.chapo.util.MainMenuUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

  private final SberService sberService;

  private final TelegramBotProperties properties;
  private final BankProperties bankProperties;

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
      serveCommand(update);
    } catch (TelegramApiException e) {
      throw new LogicException(CommonErrorCode.JAVA_ERROR, e);
    }
  }

  private void serveCommand(Update update) throws TelegramApiException {
    if (update.hasMessage()) {
      String text = update.getMessage().getText();
      Long chatId = update.getMessage().getChatId();
      switch (text) {
        case "/start" -> {
          execute(MainMenuUtil.getMainMenuMessage(chatId, "Здарова, лысый"));
        }
        case "Сбербанк" -> {
          execute(sberService.getTransferChoiceMessage(chatId));
        }
        case "Тинькофф" -> {

        }
        default -> {
          execute(MainMenuUtil.getMainMenuMessage(chatId, "Неизвестная команда. Воспользуйся меню управления."));
        }
      }

    }
    if (update.hasCallbackQuery()) {
      Long chatId = update.getCallbackQuery().getMessage().getChatId();
      String text = update.getCallbackQuery().getData();
      switch (text) {
        case "sberTransfer" -> {
          execute(DemoScreenUtil.getDemoScreenMessage(chatId, bankProperties.getSber().getJustTransferDemoPath()));
          execute(sberService.getJustTransferForm(chatId));
        }
      }
    }
  }
}
