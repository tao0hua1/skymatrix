package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.task.GameTask;
import cn.seiua.skymatrix.task.skyblock.TaskBuyBzItem;
import cn.seiua.skymatrix.task.skyblock.TaskOpenBz;
import cn.seiua.skymatrix.task.skyblock.TaskSearchItemFromBz;
import com.google.common.collect.EvictingQueue;

public class Bazaar {

    private static Bazaar instance;
    private EvictingQueue<GameTask> queue = EvictingQueue.create(10);
    private GameTask task;
    private BazaarTask current;

    public static Bazaar getInstance() {
        return instance;
    }

    public boolean addTask(BazaarTask task) {
        if (current == null) {
            queue.clear();
            queue.add(new TaskOpenBz());
            queue.add(new TaskSearchItemFromBz(task.name));
            queue.add(new TaskBuyBzItem(task.amount));
            current = task;
            return true;
        }

        return false;
    }

    @EventTarget
    public void onTick() {

    }

    private enum FailType {
        // 无法访问 bz
        // 硬币不够
        // 没有cookie buff
        // 商品 无法找到
        // 数量不对 不合法
        ACCESS, NO_COINS, NO_COOKIE, NOTFOUND, AMOUNT,
    }

    public static class BazaarTask implements GameTask {
        private String name;
        private String amount;
        private boolean completed;
        private String reason;
        private String failType;
        private BazaarCallBack callBack;


        public BazaarTask(String name, String amount, BazaarCallBack callBack) {

            this.name = name;
            this.amount = amount;
            this.callBack = callBack;

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public BazaarCallBack getCallBack() {
            return callBack;
        }

        public void setCallBack(BazaarCallBack callBack) {
            this.callBack = callBack;
        }


        public String getFailType() {
            return failType;
        }

        public void setFailType(String failType) {
            this.failType = failType;
        }
    }

    public interface BazaarCallBack {

        void callBack(BazaarTask task);

    }


}
