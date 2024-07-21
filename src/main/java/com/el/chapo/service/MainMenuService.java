package com.el.chapo.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenuService {

  public SendMessage getMainMenuMessage(long chatId, String textMessage) {
    return createMessageWithKeyboard(chatId, textMessage, getMainMenuKeyboard());
  }

  private ReplyKeyboardMarkup getMainMenuKeyboard() {

    final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setSelective(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setOneTimeKeyboard(false);

    List<KeyboardRow> keyboard = new ArrayList<>();

    KeyboardRow row = new KeyboardRow();
    row.add(new KeyboardButton("Сбербанк"));
    row.add(new KeyboardButton("Тинькофф"));

    keyboard.add(row);
    replyKeyboardMarkup.setKeyboard(keyboard);
    return replyKeyboardMarkup;
  }

  private SendMessage createMessageWithKeyboard(long chatId,
                                                String textMessage,
                                                ReplyKeyboardMarkup replyKeyboardMarkup) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(textMessage);
    if (replyKeyboardMarkup != null) {
      sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }
    return sendMessage;
  }
}
