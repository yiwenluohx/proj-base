package com.study.snowflake;

/**
 * ClassName: SnowflakeWorker
 * Description:
 * @Author: luohx
 * Date: 2022/3/1 下午4:03
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
class SnowflakeWorker {

    private static final String TABLE_NAME = "snowflake_worker";
    private static final String PK_NAME = "group_name";
    public static final String WORKER_ID_FIELD = "latest_worker_id";
    public static final String DATA_VERSION_FIELD = "data_version";

    public static final String QUERY_SQL = String.format("select %s, %s from %s where %s = ?",
            WORKER_ID_FIELD, DATA_VERSION_FIELD, TABLE_NAME, PK_NAME);

    public static final String UPDATE_SQL = String.format("update %s set %s = ?, %s = ? where %s = ? and %s = ?",
            TABLE_NAME, WORKER_ID_FIELD, DATA_VERSION_FIELD, PK_NAME, DATA_VERSION_FIELD);
}