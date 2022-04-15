
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for snowflake_worker
-- ----------------------------
DROP TABLE IF EXISTS `snowflake_worker`;
CREATE TABLE `snowflake_worker` (
  `group_name` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '分组名称',
  `latest_worker_id` int NOT NULL DEFAULT '0' COMMENT '最新的workerId',
  `data_version` bigint NOT NULL DEFAULT '0' COMMENT '数据版本',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='工作机器分配';

-- ----------------------------
-- Records of snowflake_worker
-- ----------------------------
BEGIN;
INSERT INTO `snowflake_worker` VALUES ('DefaultGroup', 212, 3028, '2021-09-16 17:21:28', '2022-03-03 13:58:00');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;



DROP TABLE IF EXISTS `snowflake_worker_record`;
CREATE TABLE `snowflake_worker_record` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分配记录ID',
  `worker_id` int NOT NULL DEFAULT '0' COMMENT '工作机器ID',
  `host_address` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '主机IP',
  `host_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '主机名',
  `server_port` int NOT NULL COMMENT '服务端口',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2194 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='工作机器分配记录';
