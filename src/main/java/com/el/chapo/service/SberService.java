package com.el.chapo.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class SberService {

    public SendMessage getTransferChoiceMessage(long chatId) {
      SendMessage sendMessage = new SendMessage();
      sendMessage.setChatId(chatId);
      sendMessage.setText("Какой перевод нужен?");
      sendMessage.setReplyMarkup(getChoiceMarkup());
      return sendMessage;
    }


    private InlineKeyboardMarkup getChoiceMarkup() {
      InlineKeyboardButton transferButton = new InlineKeyboardButton();
      transferButton.setText("Перевод клиенту сбера");
      transferButton.setCallbackData("sberTransfer");
      InlineKeyboardButton transferSBPButton = new InlineKeyboardButton();
      transferSBPButton.setText("Перевод в другой банк по СБП");
      transferSBPButton.setCallbackData("sberTransferSBP");

      List<InlineKeyboardButton> firstRow = new ArrayList<>(List.of(transferButton));
      List<InlineKeyboardButton> secondRow = new ArrayList<>(List.of(transferSBPButton));
      List<List<InlineKeyboardButton>> rows = new ArrayList<>(List.of(firstRow, secondRow));

      InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
      inlineKeyboardMarkup.setKeyboard(rows);
      return inlineKeyboardMarkup;
    }

    public SendMessage getJustTransferForm(Long chatId) {
    SendMessage sendMessage = new SendMessage(
      chatId.toString(),
      "Введите построчно необходимые данные:\n\n" +
        "1\uFE0F⃣ ФИО получателя\n2\uFE0F⃣ Сумма перевода\n3\uFE0F⃣ * Комиссия\n4\uFE0F⃣ * Время в статус-бар\n\n" +
        "* - необязательные строки, можно не вводить\n\n" +
        "Пример:\n" +
        "<b>Иван Иванович И</b>\n<b>50000</b>\n<b>0</b>\n<b>12:00</b>"
    );
    sendMessage.setParseMode(ParseMode.HTML);
    return sendMessage;
    }
}
