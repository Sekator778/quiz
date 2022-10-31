package com.selator.quiz.command;

import com.selator.quiz.data.Answer;
import com.selator.quiz.icon.Icon;
import com.selator.quiz.reader.ReadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
public class ServiceBotCommand implements BotCommand {
    private final ReadFile readFile;

    @Autowired
    public ServiceBotCommand(ReadFile readFile){
        this.readFile = readFile;
    }

    @Override
    public Answer execute(Update update) {
        long telegramId;

        if (update.hasCallbackQuery()) {
            telegramId = update.getCallbackQuery().getFrom().getId();
        } else {
            telegramId = update.getMessage().getChatId();
        }
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null && callbackQuery.getData().equals("next")) {
            log.info("next page  ========== >");
        } else if (callbackQuery != null && callbackQuery.getData().equals("back")) {
            log.info("previous page  ========== >");
        }

            log.info("Choosing a city to search for a service");
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> citySelection = new ArrayList<>();
            List<InlineKeyboardButton> city = new ArrayList<>();
            city.add(InlineKeyboardButton.builder()
                    .text("user.getCity()")
                    .callbackData("user.getCity()")
                    .build());
            city.add(InlineKeyboardButton.builder()
                    .text("bundleLanguage.getValue(telegramId, search.byCityName.anotherCity")
                    .callbackData("another_city")
                    .build());
            citySelection.add(city);
            inlineKeyboardMarkup.setKeyboard(citySelection);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(telegramId);
            sendMessage.setText(Icon.MAG.get() + " " + "bundleLanguage.getValue(telegramId,search.byCityName.cityToSearch");
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//            sendBotMessageService.sendMessage(sendMessage);
        return new Answer(sendMessage, true);
    }

//    private boolean generateInlineKeyboard(long telegramId, String city, PrevPositionDTO prevPosition) {
//        List<ProviderEntity> providerEntityByCityList = providerRepository.findAllByProviderCity(city);
//        if (providerEntityByCityList.isEmpty()) {
//            log.info("No service providers were found for the user's city of registration");
//            sendBotMessageService.sendMessage(String.valueOf(telegramId),
//                    bundleLanguage.getValue(telegramId, "search.byCityName.providerNotFound"));
//            return false;
//        }
//
//        List<Long> telegramIdProviderByCityList = providerEntityByCityList.stream()
//                .map(ProviderEntity::getTelegramId)
//                .collect(Collectors.toList());
//
//        Pageable pageable = PageRequest.of(prevPosition.getPage(), SendBotMessageServiceImpl.PAGE_SIZE);
//        Page<ServiceEntity> serviceEntityByCityList = serviceRepository.findAllByTelegramIdIn(telegramIdProviderByCityList, pageable);
//        if (serviceEntityByCityList.isEmpty()) {
//            log.info("No services were found for the user's city of registration");
//            sendBotMessageService.sendMessage(String.valueOf(telegramId),
//                    bundleLanguage.getValue(telegramId, "search.byCityName.serviceNotFound"));
//            return false;
//        }
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        inlineKeyboardMarkup.setKeyboard(sendBotMessageService.createPageableKeyboard(serviceEntityByCityList, prevPosition, bundleLanguage));
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(telegramId);
//        sendMessage.setText(Icon.PAGE_WITH_CURL.get() + " " + bundleLanguage.getValue(telegramId, "search.byCityName.listOfServices"));
//        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//        sendBotMessageService.sendMessage(sendMessage);
//        return true;
//    }
}
