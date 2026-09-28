package dev.xperiment.client;

public final class CpsCounter {
    private static final long[] LEFT = new long[32];
    private static final long[] RIGHT = new long[32];
    private static int leftCount = 0;
    private static int rightCount = 0;

    private CpsCounter() {}

    public static void recordLeftClick() {
        record(LEFT, true);
    }

    public static void recordRightClick() {
        record(RIGHT, false);
    }

    private static void record(long[] buffer, boolean left) {
        long now = System.currentTimeMillis();
        int count = left ? leftCount : rightCount;

        if (count < buffer.length) {
            buffer[count] = now;
            count++;
        } else {
            System.arraycopy(buffer, 1, buffer, 0, buffer.length - 1);
            buffer[buffer.length - 1] = now;
        }

        if (left) leftCount = count;
        else rightCount = count;
    }

    public static int getLeftCps() {
        return countRecent(LEFT, leftCount);
    }

    public static int getRightCps() {
        return countRecent(RIGHT, rightCount);
    }

    private static int countRecent(long[] buffer, int count) {
        long cutoff = System.currentTimeMillis() - 1000L;
        int recent = 0;

        for (int i = 0; i < count; i++) {
            if (buffer[i] >= cutoff) recent++;
        }

        return recent;
    }
}
