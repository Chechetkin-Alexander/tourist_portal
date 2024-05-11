package org.portal.schedulers;

import org.portal.models.Card;
import org.portal.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.List;

@Service
public class CardsScheduler {
    private final static int TWO_WEEKS_MS = 1209600000;
    private final static int DAY_MS = 86400000;
    @Autowired
    private CardService cardService;

    @Scheduled(fixedDelayString = "PT01H")
    private void checkExpiration() {
        List<Card> cards = cardService.findCompleted();
        long currentTime = Calendar.getInstance().getTime().getTime();

        for (Card card : cards) {
            if (currentTime - card.getCompleteDate().getTime() > TWO_WEEKS_MS) {
                cardService.delete(card);
            }
        }
    }

    public static Integer getDaysRemaining(Card card) {
        if (card.getCompleteDate() == null) {
            return null;
        }

        long currentTime = Calendar.getInstance().getTime().getTime();
        return Math.max(0, 14 - (int) ((currentTime - card.getCompleteDate().getTime()) / DAY_MS));
    }
}