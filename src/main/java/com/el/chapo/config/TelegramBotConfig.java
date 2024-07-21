package com.el.chapo.config;

import com.el.chapo.exception.LogicException;
import com.el.chapo.exception.helper.CommonErrorCode;
import com.el.chapo.properties.BankProperties;
import com.el.chapo.properties.TelegramBotProperties;
import com.el.chapo.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({TelegramBotProperties.class, BankProperties.class})
public class TelegramBotConfig {

  private final TelegramBot telegramBot;

  @EventListener(ContextRefreshedEvent.class)
  public void init() {
    try {
      TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
      telegramBotsApi.registerBot(telegramBot);
    } catch (TelegramApiException e) {
      throw new LogicException(CommonErrorCode.JAVA_ERROR, e);
    }
  }
}
