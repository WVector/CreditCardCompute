import org.junit.Test;

import java.util.Date;

/**
 * Created by fengjunming_t on 2017/6/15 0015.
 */
public class NewJunitTest {
    @Test
    public void test2() {

        CardModel cardModel = new CardModel();
        Date date = new Date();
        for (int i = 1; i <= 100; i++) {
            test3(cardModel, TimeUtil.monthPlusDay(i,date.getTime()), i);
        }
    }

    @Test
    public void test4(){
        Date date = new Date();
        for (int i = 1; i < 100; i++) {
            System.out.println(TimeUtil.mYyyyMMdd.format(TimeUtil.monthPlusDay(i, date.getTime())));
        }
    }

    public void test3(CardModel cardModel, long currentTime, int i) {
        System.out.println("----------------------------------------" + TimeUtil.mYyyyMMdd.format(currentTime) + "------------------------------------");

        cardModel.setCurrentTime(currentTime);

        cardModel.setBillDay(28);
        cardModel.setRepaymentDay(16);

        cardModel.setCerditLine(10000D);
        cardModel.setAvailableCredit(5000D);


//        if (i == 15) {
//            cardModel.setBill(5000D);
//            cardModel.setArrearage(0D);
//        }


        cardModel.compute();


        System.out.println(cardModel);
    }
}