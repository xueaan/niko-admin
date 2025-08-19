/*
 Navicat Premium Dump SQL

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80043 (8.0.43)
 Source Host           : localhost:3309
 Source Schema         : wmsdb

 Target Server Type    : MySQL
 Target Server Version : 80043 (8.0.43)
 File Encoding         : 65001

 Date: 16/08/2025 23:25:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wms_inventory_transaction
-- ----------------------------
DROP TABLE IF EXISTS `wms_inventory_transaction`;
CREATE TABLE `wms_inventory_transaction`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `material_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物资编码',
  `batch_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '批次号',
  `business_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务类型(stock_in:入库 stock_out:出库 requisition:请领 return:退库)',
  `business_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务单号',
  `business_detail_id` bigint NULL DEFAULT NULL COMMENT '业务明细ID',
  `quantity_change` bigint NOT NULL COMMENT '数量变动(正数入库，负数出库)',
  `unit_price` decimal(15, 4) NULL DEFAULT NULL COMMENT '单价',
  `amount_change` decimal(15, 2) NULL DEFAULT NULL COMMENT '金额变动',
  `transaction_date` datetime NOT NULL COMMENT '操作时间',
  `operator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_warehouse_material`(`warehouse_id` ASC, `material_code` ASC) USING BTREE,
  INDEX `idx_business`(`business_type` ASC, `business_no` ASC) USING BTREE,
  INDEX `idx_transaction_date`(`transaction_date` ASC) USING BTREE,
  INDEX `idx_material_batch`(`material_code` ASC, `batch_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1956738828429475843 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '库存变动流水表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
