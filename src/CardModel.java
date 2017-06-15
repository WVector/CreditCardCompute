/**
 * Created by fengjunming_t on 2017/6/15 0015.
 */
public class CardModel {
    //310 月底
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

    public void setToRepaymentDate(int toRepaymentDate) {
        this.toRepaymentDate = toRepaymentDate;
    }

    public int getToAccountantBillDate() {
        return toAccountantBillDate;
    }

    public void setToAccountantBillDate(int toAccountantBillDate) {
        this.toAccountantBillDate = toAccountantBillDate;
    }

    public boolean isUpdateBill() {
//        return getCurrentTime() >= getBillEndTime() || getCurrentTime() < getBillStartTime();
        return updateBill;
    }

    public void setUpdateBill(boolean updateBill) {
        this.updateBill = updateBill;
    }

    private String getBillValidity() {
        return TimeUtil.mYyyyMMdd.format(getBillStartTime()) + "-" + TimeUtil.mYyyyMMdd.format(getBillEndTime());
    }

    public long getBillStartTime() {
        return billStartTime;
    }

    public long getBillEndTime() {
        return billEndTime;
    }

    public double getArrearage() {
        return arrearage;
    }

    public void setArrearage(double arrearage) {
        this.arrearage = arrearage;
        if (getBill() > 0) {
            if (arrearage > 0) {
                repaymentTask = "未还清：" + getBillValidity();
            } else {
                repaymentTask = "已还清：" + getBillValidity();
            }
        }
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getRepaymentTask() {
        return repaymentTask;
    }

    public double getCerditLine() {
        return cerditLine;
    }

    public void setCerditLine(double cerditLine) {
        this.cerditLine = cerditLine;
    }

    public double getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(double availableCredit) {
        this.availableCredit = availableCredit;
    }

    public int getBillDay() {
        return mBillDay;
    }

    public void setBillDay(int billDay) {
        if (billDay < 1) {
            billDay = 1;
        }
        if (billDay > 28) {
            billDay = 28;
        }
        mBillDay = billDay;
    }

    public int getRepaymentDay() {
        return mRepaymentDay;
    }

    public void setRepaymentDay(int repaymentDay) {
        if (repaymentDay < 1) {
            repaymentDay = 1;
        }
        if (repaymentDay > 28) {
            repaymentDay = 28;

        }
        mRepaymentDay = repaymentDay;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
        //设置有效期
        //初始化账单有效期
        computeBillValidity();
        //初始化未还金额
        setArrearage(bill);
    }

    private void computeBillValidity() {

        long time = getCurrentTime();

        long billTime = TimeUtil.monthPlusDay(mBillDay, time);
        long preBillTime = TimeUtil.currentTimePlusMonth(billTime, -1);
        long pre2BillTime = TimeUtil.currentTimePlusMonth(billTime, -2);

        if (time < billTime) {
            billStartTime = pre2BillTime;
            billEndTime = preBillTime;

        } else {
            billStartTime = preBillTime;
            billEndTime = billTime;
        }

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

    public void compute() {
        new Engine(this).compute(getCurrentTime(), new Engine.Callback() {
            @Override
            public void billTime(int toBillDay, int toRepaymentDay, CardModel cardModel) {
                //出账期 之前是否还清，没还清，提示逾期
                cardModel.setToAccountantBillDate(toBillDay);
                cardModel.setToRepaymentDate(toRepaymentDay);
            }

            @Override
            public void repaymentTime(int toBillDay, int toRepaymentDay, CardModel cardModel) {
                //还款期   之前是否输入账单，没输入，提示更新账单，
                if (cardModel.bill <= 0) {
                    cardModel.setUpdateBill(true);
                } else {
                    long currentTime = cardModel.getCurrentTime();
                    //向前移动一个月
                    long preCurrentTime = TimeUtil.currentTimePlusMonth(currentTime, -1);
                    if (preCurrentTime < cardModel.getBillEndTime() && preCurrentTime >= cardModel.getBillStartTime()) {
                        //有效期之内，所以不更新
                        cardModel.setUpdateBill(false);
                    } else {
                        cardModel.setBill(0D);
                        cardModel.setUpdateBill(true);
                    }
                }
                cardModel.setToAccountantBillDate(toBillDay);
                cardModel.setToRepaymentDate(toRepaymentDay);
            }
        });

        System.out.println("-----");
    }
}
