/**
 * Created by fengjunming_t on 2017/6/15 0015.
 */
public class CardModel {
    //
    private int mBillDay;
    private int mRepaymentDay;

//    private String cardId;
//    private String name;
//    private String bankImg;
//    private String cardNumber;

    //总额度
    private double cerditLine;
    //可用额度
    private double availableCredit;
    //账单额
    private double bill;

    private long currentTime;

    //还款任务
    private String repaymentTask = "没有任务";
    //未还金额
    private double arrearage;
    //是否更新账单
    private boolean updateBill;
    private long billStartTime;
    private long billEndTime;
    private int toRepaymentDate;
    private int toAccountantBillDate;
    public CardModel() {
    }

    public int getToRepaymentDate() {
        return toRepaymentDate;
    }

    public CardModel setToRepaymentDate(int toRepaymentDate) {
        this.toRepaymentDate = toRepaymentDate;
        return this;
    }

    public int getToAccountantBillDate() {
        return toAccountantBillDate;
    }

    public CardModel setToAccountantBillDate(int toAccountantBillDate) {
        this.toAccountantBillDate = toAccountantBillDate;
        return this;
    }

    public boolean isUpdateBill() {
        return updateBill;
    }

    public CardModel setUpdateBill(boolean updateBill) {
        this.updateBill = updateBill;
        return this;
    }

    private String getBillValidity() {
        return TimeUtil.mMd.format(getBillStartTime()) + "-" + TimeUtil.mMd.format(getBillEndTime());
    }

    public long getBillStartTime() {
        return billStartTime;
    }

    public CardModel setBillStartTime(long billStartTime) {
        this.billStartTime = billStartTime;
        return this;
    }

    public long getBillEndTime() {
        return billEndTime;
    }

    public CardModel setBillEndTime(long billEndTime) {
        this.billEndTime = billEndTime;
        return this;
    }

    public double getArrearage() {
        return arrearage;
    }


    /*******************账单，未还款  处理********************/
    public CardModel setArrearage(double arrearage) {
        this.arrearage = arrearage;
        //之前，用户输入过账单，
        if (getBill() > 0) {
            if (arrearage > 0) {

                repaymentTask = "未还清：" + getBillValidity();
            } else {
                repaymentTask = "已还清：" + getBillValidity();
            }
        }
        return this;
    }

    private void computeBillValidity() {

        long time = getCurrentTime();

        long billTime = TimeUtil.monthPlusDay2(mBillDay, time);
        long preBillTime = TimeUtil.currentTimePlusMonth(billTime, -1);
        long pre2BillTime = TimeUtil.currentTimePlusMonth(billTime, -2);

        if (time < billTime) {
            billStartTime = pre2BillTime;
            billEndTime = preBillTime;

        } else {
            billStartTime = preBillTime;
            billEndTime = billTime;
        }

        billEndTime = billEndTime - TimeConstants.MSEC;


        System.out.println(TimeUtil.mYyyyMMddHHmmssSSS.format(billStartTime));
        System.out.println(TimeUtil.mYyyyMMddHHmmssSSS.format(billEndTime));


    }

    /***************************************/

    public long getCurrentTime() {
        return currentTime;
    }

    public CardModel setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
        return this;
    }

    public String getRepaymentTask() {
        return repaymentTask;
    }

    public double getCerditLine() {
        return cerditLine;
    }

    public CardModel setCerditLine(double cerditLine) {
        this.cerditLine = cerditLine;
        return this;
    }

    public double getAvailableCredit() {
        return availableCredit;
    }

    public CardModel setAvailableCredit(double availableCredit) {
        this.availableCredit = availableCredit;
        return this;
    }

    public int getBillDay() {
        return mBillDay;
    }

    public CardModel setBillDay(int billDay) {
        if (billDay < 1) {
            billDay = 1;
        }
        if (billDay > 28) {
            billDay = 28;
        }
        mBillDay = billDay;
        return this;
    }

    public int getRepaymentDay() {
        return mRepaymentDay;
    }

    public CardModel setRepaymentDay(int repaymentDay) {
        if (repaymentDay < 1) {
            repaymentDay = 1;
        }
        if (repaymentDay > 28) {
            repaymentDay = 28;

        }
        mRepaymentDay = repaymentDay;
        return this;
    }

    public double getBill() {
        return bill;
    }

    public CardModel setBill(double bill) {

        this.bill = bill;

        //当初始化时，不变
        if (bill > 0) {
            //设置有效期
            //初始化账单有效期
            computeBillValidity();
            //初始化未还金额
            setArrearage(bill);
        }

        return this;
    }

    @Override
    public String toString() {
        return "CardModel{" +
                "   \n mBillDay=" + getBillDay() +
                ",  \n mRepaymentDay=" + getRepaymentDay() +
                ",  \n cerditLine=" + getCerditLine() +
                ",  \n availableCredit=" + getAvailableCredit() +
                ",  \n bill=" + getBill() +
                ",  \n repaymentTask='" + getRepaymentTask() + '\'' +
                ",  \n arrearage=" + getArrearage() +
                ",  \n updateBill=" + isUpdateBill() +
                ",  \n billStartTime=" + TimeUtil.mYyyyMMdd.format(getBillStartTime()) +
                ",  \n billEndTime=" + TimeUtil.mYyyyMMdd.format(getBillEndTime()) +
                ",  \n toRepaymentDate=" + getToRepaymentDate() +
                ",  \n toAccountantBillDate=" + getToAccountantBillDate() +
                '}';
    }

    public CardModel compute() {
        new Engine(this).compute(getCurrentTime(), new Engine.Callback() {
            @Override
            public void billTime(CardModel cardModel) {
                //出账期 之前是否还清，没还清，提示逾期

            }

            @Override
            public void repaymentTime(CardModel cardModel) {
                //还款期   之前是否输入账单，没输入，提示更新账单，
                //设置是否更新账单
                if (cardModel.getBill() <= 0) {
                    cardModel.setUpdateBill(true);
                } else {
                    long currentTime = cardModel.getCurrentTime();
                    //向前移动一个月
                    long preCurrentTime = TimeUtil.currentTimePlusMonth(currentTime, -1);
                    if (preCurrentTime <= cardModel.getBillEndTime() && preCurrentTime >= cardModel.getBillStartTime()) {
                        //有效期之内，所以不更新
                        cardModel.setUpdateBill(false);
                    } else {
                        //账单初始化
//                        cardModel.setBill(0D);
                        cardModel.setUpdateBill(true);
                    }
                }

            }
        });

        return this;
    }
}
