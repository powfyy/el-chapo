package com.el.chapo.util;

import com.el.chapo.exception.LogicException;
import com.el.chapo.exception.helper.CommonErrorCode;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class DemoScreenUtil {

  public static SendPhoto getDemoScreenMessage(long chatId, String filePath) {
    try {
      InputFile photo = new InputFile(new File(new ClassPathResource(filePath).getURI()));
      SendPhoto sendPhoto = new SendPhoto(String.valueOf(chatId), photo);
      sendPhoto.setCaption("Это пример готового скриншота \n\n ⬇\uFE0F Инструкция ниже ⬇\uFE0F");
      return sendPhoto;
    } catch (IOException ex) {
      throw new LogicException(CommonErrorCode.JAVA_ERROR, ex);
    }
  }
}
