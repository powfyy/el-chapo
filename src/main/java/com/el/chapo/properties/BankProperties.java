package com.el.chapo.properties;

import com.el.chapo.dto.properties.Sber;
import com.el.chapo.dto.properties.Tinkoff;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "bank")
public class BankProperties {

  private Sber sber;
  private Tinkoff tinkoff;
}
