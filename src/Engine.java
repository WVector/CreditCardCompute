/**
 * Created by Vector
 * on 2017/6/14.
 */

public class Engine {


    private CardModel mCardModel;


    public Engine(CardModel cardModel) {
        mCardModel = cardModel;
    }

    public void compute(long currentTime, Callback callback) {

        if (callback == null) {
            return;
        }

        long billTime = TimeUtil.monthPlusDay(mCardModel.getBillDay(), currentTime);
        long repaymentTime = TimeUtil.monthPlusDay(mCardModel.getRepaymentDay(), currentTime);

        long monthEnd = TimeUtil.currentTimePlusMonth(TimeUtil.currentTimeMonth(currentTime), 1);
        long monthStart = TimeUtil.currentTimeMonth(currentTime);


        long preBillTime = TimeUtil.currentTimePlusMonth(billTime, -1);
        long nextBillTime = TimeUtil.currentTimePlusMonth(billTime, 1);


        long nextRepaymentTime = TimeUtil.currentTimePlusMonth(repaymentTime, 1);
        long preRepaymentTime = TimeUtil.currentTimePlusMonth(repaymentTime, -1);


        System.out.println("账单日：" + TimeUtil.mYyyyMMddHHmmss.format(billTime));
        System.out.println("还款日：" + TimeUtil.mYyyyMMddHHmmss.format(repaymentTime));


        System.out.println("月初：" + TimeUtil.mYyyyMMddHHmmss.format(monthStart));
        System.out.println("月末：" + TimeUtil.mYyyyMMddHHmmss.format(monthEnd));

        //隔月
        if (mCardModel.getBillDay() >= mCardModel.getRepaymentDay()) {

            //当前大于  上个月的还款日


            if (currentTime >= repaymentTime && currentTime < billTime) {
                //过了还款日  没有过出账日  出账期

                int toRepaymentDay = TimeUtil.diffDay(currentTime, repaymentTime);

                int toBillDay = TimeUtil.diffDay(currentTime, billTime);

                callback.billTime(mCardModel.setToAccountantBillDate(toBillDay).setToRepaymentDate(toRepaymentDay));


            } else if (currentTime >= billTime && currentTime < monthEnd) {
                //过了出账日  没有过 下一次的还款日  也就是还款期


                int toRepaymentDay = TimeUtil.diffDay(currentTime, nextRepaymentTime);
                int toBillDay = TimeUtil.diffDay(currentTime, billTime);

                callback.repaymentTime(mCardModel.setToAccountantBillDate(toBillDay).setToRepaymentDate(toRepaymentDay));

            } else {

                //提前换完，
                int toRepaymentDay = TimeUtil.diffDay(currentTime, repaymentTime);

                if (mCardModel.getBill() > 0 && mCardModel.getArrearage() <= 0) {


                    int toBillDay = TimeUtil.diffDay(currentTime, billTime);


                    callback.repaymentTime(mCardModel.setToAccountantBillDate(toBillDay).setToRepaymentDate(toRepaymentDay));

                } else {

                    int toBillDay = TimeUtil.diffDay(currentTime, preBillTime);


                    callback.repaymentTime(mCardModel.setToAccountantBillDate(toBillDay).setToRepaymentDate(toRepaymentDay));
                }


            }


        } else {


            //一个月内


            if (currentTime >= billTime && currentTime < repaymentTime) {
                //过了出账日  没有过 下一次的还款日  也就是还款期
                int toRepaymentDay = TimeUtil.diffDay(currentTime, repaymentTime);

                if (mCardModel.getBill() > 0 && mCardModel.getArrearage() <= 0) {

                    int toBillDay = TimeUtil.diffDay(currentTime, nextBillTime);

                    callback.repaymentTime(mCardModel.setToAccountantBillDate(toBillDay).setToRepaymentDate(toRepaymentDay));
                } else {

                    int toBillDay = TimeUtil.diffDay(currentTime, billTime);

                    callback.repaymentTime(mCardModel.setToAccountantBillDate(toBillDay).setToRepaymentDate(toRepaymentDay));
                }


            } else if (currentTime >= repaymentTime && currentTime < monthEnd) {

                //过了还款日  没有过出账日  出账期

                int toRepaymentDay = TimeUtil.diffDay(currentTime, repaymentTime);

                int toBillDay = TimeUtil.diffDay(currentTime, nextBillTime);

                callback.billTime(mCardModel.setToAccountantBillDate(toBillDay).setToRepaymentDate(toRepaymentDay));

            } else {
                int toRepaymentDay = TimeUtil.diffDay(currentTime, preRepaymentTime);

                int toBillDay = TimeUtil.diffDay(currentTime, billTime);

                callback.billTime(mCardModel.setToAccountantBillDate(toBillDay).setToRepaymentDate(toRepaymentDay));
            }


        }

    }


    interface Callback {

        void billTime(CardModel cardModel);

        void repaymentTime(CardModel cardModel);
    }

}
