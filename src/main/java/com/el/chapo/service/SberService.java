package com.el.chapo.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class SberService {

    public SendMessage getTransferChoice(long chatId) {
      SendMessage sendMessage = new SendMessage();
      sendMessage.setChatId(chatId);
      sendMessage.setText("Какой перевод нужен?");
      sendMessage.setReplyMarkup(getChoiceMarkup());
      return sendMessage;
    }


    private InlineKeyboardMarkup getChoiceMarkup() {
      InlineKeyboardButton transferButton = new InlineKeyboardButton();
      transferButton.setText("Перевод клиенту сбера");
      transferButton.setCallbackData("transfer");
      InlineKeyboardButton transferSBPButton = new InlineKeyboardButton();
      transferSBPButton.setText("Перевод в другой банк по СБП");
      transferSBPButton.setCallbackData("transferSBP");

      List<InlineKeyboardButton> firstRow = new ArrayList<>(List.of(transferButton));
      List<InlineKeyboardButton> secondRow = new ArrayList<>(List.of(transferSBPButton));
      List<List<InlineKeyboardButton>> rows = new ArrayList<>(List.of(firstRow, secondRow));

      InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
      inlineKeyboardMarkup.setKeyboard(rows);
      return inlineKeyboardMarkup;
    }
}
