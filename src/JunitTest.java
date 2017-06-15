//import org.junit.Test;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Created by fengjunming_t on 2017/6/15 0015.
// */
//public class JunitTest {
//
//    @Test
//    public void test2() {
//
//        CardModel cardModel = new CardModel();
//        Date date = new Date();
//        for (int i = 1; i <= 30; i++) {
//            date.setDate(i);
//            test3(cardModel, date.getTime(), i);
//        }
//    }
//
//    public void test3(CardModel cardModel, long currentTime, int i) {
//        System.out.println("----------------------------------------" + mYyyyMMdd.format(currentTime) + "------------------------------------");
//
//        cardModel.setCurrentTime(currentTime);
//
//        cardModel.setBillDay(28);
//        cardModel.setRepaymentDay(16);
//
//        cardModel.setCerditLine(10000D);
//        cardModel.setAvailableCredit(5000D);
//
//
//        if (i == 15) {
//            cardModel.setBill(5000D);
//            cardModel.setArrearage(0D);
//        }
//
//
//        cardModel.compute();
//
//
//        System.out.println(cardModel);
//    }
//
//
//    @Test
//    public void test1() {
//        CardModel cardModel = new CardModel();
//        cardModel.setCurrentTime(new Date().getTime());
//
//        cardModel.setBillDay(28);
//        cardModel.setRepaymentDay(16);
//
//        cardModel.setCerditLine(10000D);
//        cardModel.setAvailableCredit(5000D);
//
//        cardModel.setBill(5000D);
//
//
//        cardModel.setArrearage(100D);
//
//
//        cardModel.compute();
//
//
//        System.out.println(cardModel);
//
//    }
//
//    @Test
//    public void addition_isCorrect() throws Exception {
//
//
//        CardModel cardModel = new CardModel();
//
//        cardModel.setBillDay(28);
//        cardModel.setRepaymentDay(16);
//
//        cardModel.setCerditLine(10000D);
//        cardModel.setAvailableCredit(5000D);
//
//        cardModel.setBill(5000D);
//
//        cardModel.setArrearage(0D);
//        cardModel.setAvailableCredit(5000D);
//
//        long currentTime = new Date().getTime();
//
//
//        new Engine(cardModel).compute(currentTime, new Engine.Callback() {
//            @Override
//            public void billTime(int toBillDay, int toRepaymentDay, CardModel cardModel) {
//
//
//                System.out.println("出账期   " + "toBillDay = [" + toBillDay + "], toRepaymentDay = [" + toRepaymentDay + "]");
//
//
//                cardModel.setToAccountantBillDate(toBillDay);
//                cardModel.setRepaymentDay(toRepaymentDay);
//                System.out.println(cardModel.toString());
//
//
//            }
//
//            @Override
//            public void repaymentTime(int toBillDay, int toRepaymentDay, CardModel cardMode1) {
//
//
//                System.out.println("还款期   " + "toBillDay = [" + toBillDay + "], toRepaymentDay = [" + toRepaymentDay + "]");
//                cardMode1.setToAccountantBillDate(toBillDay);
//                cardMode1.setRepaymentDay(toRepaymentDay);
//                System.out.println(cardMode1.toString());
//            }
//        });
//
//
//        assertEquals(4, 2 + 2);
//    }
//
//    private SimpleDateFormat mYyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
//    private SimpleDateFormat mYyyyMM = new SimpleDateFormat("yyyyMM");
//    private SimpleDateFormat mYyyyMMdd = new SimpleDateFormat("yyyyMMdd");
//
//    @Test
//    public void testEngine() throws Exception {
////        Date date = new Date();
////        for (int i = 1; i < 30; i++) {
////
////            date.setDate(i);
////
////            compute(date.getTime());
////        }
//
//        compute(mYyyyMMdd.parse("20170710").getTime());
//
//
//    }
//
//    private void print(long time) {
//        System.out.println(mYyyyMMddHHmmss.format(time));
//    }
//
//
//    private void compute(long currentTime) {
//        System.out.println("----------------------------------------" + mYyyyMMddHHmmss.format(currentTime) + "------------------------------------");
//        CardModel cardModel = new CardModel();
//
//        cardModel.setBillDay(28);
//        cardModel.setRepaymentDay(25);
//
//
//        new Engine(cardModel).compute(currentTime, new Engine.Callback() {
//            @Override
//            public void billTime(int toBillDay, int toRepaymentDay, CardModel cardModel) {
//                //之前是否还清，没还清，提示逾期
//
//                System.out.println("出账期   " + "toBillDay = [" + toBillDay + "], toRepaymentDay = [" + toRepaymentDay + "]");
//
//            }
//
//            @Override
//            public void repaymentTime(int toBillDay, int toRepaymentDay, CardModel cardMode1) {
//                //之前是否输入账单，没输入，提示更新账单，
//
//                System.out.println("还款期   " + "toBillDay = [" + toBillDay + "], toRepaymentDay = [" + toRepaymentDay + "]");
//            }
//        });
//
//    }
//
//
//}
