package com.lancep.domain;

import java.util.Arrays;
import java.util.List;

public class DateTimeDataPointStats {

    private String uniqueKey;
    private String startingDelay;
    private String endingDelay;
    private String totalActualDelta;

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getStartingDelay() {
        return startingDelay;
    }

    public void setStartingDelay(String startingDelay) {
        this.startingDelay = startingDelay;
    }

    public String getEndingDelay() {
        return endingDelay;
    }

    public void setEndingDelay(String endingDelay) {
        this.endingDelay = endingDelay;
    }

    public String getTotalActualDelta() {
        return totalActualDelta;
    }

    public void setTotalActualDelta(String totalActualDelta) {
        this.totalActualDelta = totalActualDelta;
    }

    public List<String> getAsList() {
        return Arrays.asList(
                getUniqueKey(),
                getStartingDelay(),
                getEndingDelay(),
                getTotalActualDelta());
    }
}
