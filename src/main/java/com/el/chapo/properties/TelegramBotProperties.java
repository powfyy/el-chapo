package com.el.chapo.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "telegram-bot")
public class TelegramBotProperties {

  private String username;
  private String token;
}
