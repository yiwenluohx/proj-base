package com.study.snowflake;

import java.time.*;
import java.util.Date;

/**
 * 雪花算法 改进版本
 * <p>
 *    +---------+-----------------+-------------+----------------+---------------+  <br>
 *    |   sign  |   timestamp     |  clock lane |     sequence   |   worker id   |  <br>
 *    +---------+-----------------+-------------+----------------+---------------+  <br>
 *        1bit        33bits           1bit           21bits           8bits        <br>
 * <p>
 * 最高位符号位，始终为0<br>
 * 33位的时间序列，精确到秒，可以使用272年 (1L << 33) / (3600L*24*365)<br>
 * 1位时钟回拨位，发现有时钟回拨就将回拨位加1，达到最大位后再从0开始进行循环<br>
 * 21位的序列号，最大支持每个节点每秒产生 2,097,152 个ID<br>
 * 8位的机器标识，最多支持部署 256 个节点<br>
 *
 * @version 1.0
 */
public final class SnowflakeGenerator {

    // start time
    private static final long START_TIME = LocalDate.of(2022, 1, 1)
            .atStartOfDay(ZoneOffset.ofHours(8)).toInstant().getEpochSecond();

    private static final long CLOCK_LANE_BITS = 1L;
    private static final long SEQUENCE_BITS = 21L;
    private static final long WORKER_ID_BITS = 8L;

    // 时间戳左移位数
    private static final long TIME_LEFT_SHIFT_BITS = CLOCK_LANE_BITS + WORKER_ID_BITS + SEQUENCE_BITS;
    // 时钟回拨位左移位数
    private static final long CLOCK_LANE_LEFT_SHIFT_BITS = WORKER_ID_BITS + SEQUENCE_BITS;

    // 时钟回拨掩码
    private static final long CLOCK_LANE_MASK = (1L << CLOCK_LANE_BITS) - 1L;
    // 序列号掩码，限定序列值最大 < 2097152
    private static final long SEQUENCE_MASK = (1L << SEQUENCE_BITS) - 1L;
    // 最大支持机器编号 255
    public static final long MAX_WORKER_ID = (1L << WORKER_ID_BITS) - 1L;


    // 时钟车道
    private long clockLane = 0L;
    private long lastTime;
    private long workerId;
    private long sequence;
    private long currentId;

    protected SnowflakeGenerator(long workerId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId can't be greater than {} or less than 0",
                    MAX_WORKER_ID));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long currentTime = System.currentTimeMillis() / 1000L;

        if (currentTime > lastTime) {
            sequence = 0L;
        }
        else if (currentTime == lastTime) {
            sequence = (sequence + 1L) & SEQUENCE_MASK;
        }
        // 时钟回拨
        else {
            sequence = 0L;
            clockLane = (clockLane + 1L) & CLOCK_LANE_MASK;
        }
        lastTime = currentTime;

        long time = currentTime - START_TIME;
        currentId = (time << TIME_LEFT_SHIFT_BITS) | (clockLane << CLOCK_LANE_LEFT_SHIFT_BITS)
                | sequence << WORKER_ID_BITS | workerId;
        return currentId;
    }

    public long currentId() {
        return currentId;
    }


    /**
     * 获取生成ID 的 sequence
     *
     * @param id
     * @return
     */
    public static long getSequence(long id) {
        return id >> WORKER_ID_BITS & ~(-1L << SEQUENCE_BITS);
    }

    /**
     * 获取生成ID 的 workerId
     *
     * @param id
     * @return
     */
    public static long getWorkerId(long id) {
        return id & ~(-1L << WORKER_ID_BITS);
    }

    /**
     * 获取生成ID 的 clockLane
     *
     * @param id
     * @return
     */
    public static long getClockLane(long id) {
        return id >> CLOCK_LANE_LEFT_SHIFT_BITS & ~(-1L << CLOCK_LANE_BITS);
    }


    /**
     * 获取生成ID 的 Date
     *
     * @param id
     * @return
     */
    public static Date getDate(long id) {
        Instant instant = getInstant(id);
        return Date.from(instant);
    }

    public static Instant getInstant(long id) {
        return Instant.ofEpochSecond((id >> TIME_LEFT_SHIFT_BITS) + START_TIME);
    }

    /**
     * 获取生成ID 的 LocalDateTime
     *
     * @param id
     * @return
     */
    public static LocalDateTime getLocalDateTime(long id) {
        Instant instant = getInstant(id);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

}