/*
Navicat MySQL Data Transfer

Source Server         : 16docker
Source Server Version : 50722
Source Host           : 192.168.0.16:3306
Source Database       : pascloud

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-09-07 09:38:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `csb_dmms`
-- ----------------------------
DROP TABLE IF EXISTS `csb_dmms`;
CREATE TABLE `csb_dmms` (
  `DMMC` varchar(15) NOT NULL,
  `DMZ` varchar(30) NOT NULL,
  `DMMS` varchar(200) DEFAULT NULL,
  `DMSM` varchar(500) DEFAULT NULL,
  `XH` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of csb_dmms
-- ----------------------------
INSERT INTO `csb_dmms` VALUES ('RZCZLX', '03', '删除', '日志记录标志', '3');
INSERT INTO `csb_dmms` VALUES ('SFQY', '0', '停用', '任务管理启用标志', '1');
INSERT INTO `csb_dmms` VALUES ('SFQY', '1', '启用', '任务管理启用标志', '2');
INSERT INTO `csb_dmms` VALUES ('SFXS', '0', '是', '是否显示', '1');
INSERT INTO `csb_dmms` VALUES ('SFXS', '1', '否', '是否显示', '2');
INSERT INTO `csb_dmms` VALUES ('SJFL', '1', '正常', '四级分类', '1');
INSERT INTO `csb_dmms` VALUES ('SJFL', '2', '逾期', '四级分类', '2');
INSERT INTO `csb_dmms` VALUES ('SJFL', '3', '呆滞', '四级分类', '3');
INSERT INTO `csb_dmms` VALUES ('XL', '00', '其他', '学历', '1');
INSERT INTO `csb_dmms` VALUES ('XL', '01', '高中', '学历', '2');
INSERT INTO `csb_dmms` VALUES ('XL', '02', '中专', '学历', '3');
INSERT INTO `csb_dmms` VALUES ('XL', '03', '大专', '学历', '4');
INSERT INTO `csb_dmms` VALUES ('XL', '04', '本科', '学历', '5');
INSERT INTO `csb_dmms` VALUES ('XL', '05', '硕士', '学历', '6');
INSERT INTO `csb_dmms` VALUES ('XL', '06', '研究生', '学历', '7');
INSERT INTO `csb_dmms` VALUES ('DJPDLB', '2', '机构等级评定', '等级评定', '2');
INSERT INTO `csb_dmms` VALUES ('JSLX', '0', '超级管理员', '角色类型', '1');
INSERT INTO `csb_dmms` VALUES ('JSLX', '1', '普通角色', '角色类型', '1');
INSERT INTO `csb_dmms` VALUES ('JSLX', '2', '分行管理员', '角色类型', '1');
INSERT INTO `csb_dmms` VALUES ('JSLX', '3', '支行管理员', '角色类型', '1');
INSERT INTO `csb_dmms` VALUES ('JSLX', '4', '总行管理员', '角色类型', '1');
INSERT INTO `csb_dmms` VALUES ('JSLX', '5', '支行行长', '角色类型', '1');
INSERT INTO `csb_dmms` VALUES ('JSLX', '6', '网点主任', '角色类型', '1');
INSERT INTO `csb_dmms` VALUES ('AQJSLX', '1', '普通角色', '安全角色类型', '1');
INSERT INTO `csb_dmms` VALUES ('AQJSLX', '2', '支行系统管理员', '安全角色类型', '2');
INSERT INTO `csb_dmms` VALUES ('AQJSLX', '3', '分行系统管理员', '安全角色类型', '3');
INSERT INTO `csb_dmms` VALUES ('DJPDXMLX', 'KHFA', '考核方案', '等级评定', '1');
INSERT INTO `csb_dmms` VALUES ('DJPDXMLX', 'KHZB', '考核指标', '等级评定', '2');
INSERT INTO `csb_dmms` VALUES ('DJPDXMLX', 'RZZG', '任职资格', '等级评定', '3');
INSERT INTO `csb_dmms` VALUES ('DJPDXMLX', 'SJY', '数据源', '等级评定', '4');
INSERT INTO `csb_dmms` VALUES ('DKFS', '0', '其他', '贷款方式', '1');
INSERT INTO `csb_dmms` VALUES ('DKFS', '1', '质押', '贷款方式', '2');
INSERT INTO `csb_dmms` VALUES ('DKFS', '2', '抵押', '贷款方式', '3');
INSERT INTO `csb_dmms` VALUES ('DKFS', '3', '信用', '贷款方式', '4');
INSERT INTO `csb_dmms` VALUES ('DKFS', '4', '保证', '贷款方式', '5');
INSERT INTO `csb_dmms` VALUES ('DKWJFL', '1', '正常', '贷款五级分类', '1');
INSERT INTO `csb_dmms` VALUES ('DKWJFL', '2', '关注', '贷款五级分类', '2');
INSERT INTO `csb_dmms` VALUES ('DKWJFL', '3', '次级', '贷款五级分类', '3');
INSERT INTO `csb_dmms` VALUES ('DKWJFL', '4', '可疑', '贷款五级分类', '4');
INSERT INTO `csb_dmms` VALUES ('DKWJFL', '5', '损失', '贷款五级分类', '5');
INSERT INTO `csb_dmms` VALUES ('DZLX', '1', 'asp/java页面地址', '地址类型', '1');
INSERT INTO `csb_dmms` VALUES ('DZLX', '2', 'hps地址', '地址类型', '2');
INSERT INTO `csb_dmms` VALUES ('GXHSLX', '0', '百分百分配给一个客户经理', '关系函数类型', '1');
INSERT INTO `csb_dmms` VALUES ('GXHSLX', '1', '按百分比分配', '关系函数类型', '2');
INSERT INTO `csb_dmms` VALUES ('GXHSLX', '2', '以阈值分隔后按百分比分配', '关系函数类型', '3');
INSERT INTO `csb_dmms` VALUES ('HXJYDM', '1', '个人存单、折账户类', '核心交易代码', '1');
INSERT INTO `csb_dmms` VALUES ('HXJYDM', '2', '对公或内部账户类', '核心交易代码', '2');
INSERT INTO `csb_dmms` VALUES ('HXJYDM', '3', '贷款类', '核心交易代码', '3');
INSERT INTO `csb_dmms` VALUES ('HXJYDM', '4', '信合卡类', '核心交易代码', '4');
INSERT INTO `csb_dmms` VALUES ('HXJYDM', '5', '重要空白凭证管理', '核心交易代码', '5');
INSERT INTO `csb_dmms` VALUES ('HXJYDM', '6', 'ATM交易', '核心交易代码', '6');
INSERT INTO `csb_dmms` VALUES ('HXJYDM', '7', '大小额支付', '核心交易代码', '7');
INSERT INTO `csb_dmms` VALUES ('HXJYDM', '8', '农信银业务', '核心交易代码', '8');
INSERT INTO `csb_dmms` VALUES ('HYGW', '1', '销售经理', '行员岗位', '1');
INSERT INTO `csb_dmms` VALUES ('HYGW', '2', '前台柜员', '行员岗位', '2');
INSERT INTO `csb_dmms` VALUES ('HYPMFX', 'asc', '后', '行员排名方向', '1');
INSERT INTO `csb_dmms` VALUES ('HYPMFX', 'desc', '前', '行员排名方向', '2');
INSERT INTO `csb_dmms` VALUES ('JGJB', '0', '总行', '机构级别', '1');
INSERT INTO `csb_dmms` VALUES ('JGJB', '1', '一级分行', '机构级别', '2');
INSERT INTO `csb_dmms` VALUES ('JGJB', '3', '支行', '机构级别', '3');
INSERT INTO `csb_dmms` VALUES ('JHLX', '1', '月计划', '计划类型', '1');
INSERT INTO `csb_dmms` VALUES ('JHLX', '2', '季计划', '计划类型', '2');
INSERT INTO `csb_dmms` VALUES ('JHLX', '3', '年计划', '计划类型', '3');
INSERT INTO `csb_dmms` VALUES ('JHWCQK', 'and zbz>=jhz', '完成计划', '计划完成情况', '1');
INSERT INTO `csb_dmms` VALUES ('JHWCQK', 'and zbz<jhz', '未完成计划', '计划完成情况', '2');
INSERT INTO `csb_dmms` VALUES ('JHWCQK', 'and zbz>lzz', '超额力争', '计划完成情况', '3');
INSERT INTO `csb_dmms` VALUES ('JSBS', '0', '否', '计算标识', '1');
INSERT INTO `csb_dmms` VALUES ('JSBS', '1', '是', '计算标识', '2');
INSERT INTO `csb_dmms` VALUES ('JSFALX', '1', '账户时点', '计算方案类型', '1');
INSERT INTO `csb_dmms` VALUES ('JSFALX', '2', '帐户时间段日均', '计算方案类型', '2');
INSERT INTO `csb_dmms` VALUES ('JSFALX', '3', '手工导入业绩', '计算方案类型', '3');
INSERT INTO `csb_dmms` VALUES ('JSFALX', '4', '业绩结果时点', '计算方案类型', '4');
INSERT INTO `csb_dmms` VALUES ('JSFALX', '5', '业绩结果区间段日均', '计算方案类型', '5');
INSERT INTO `csb_dmms` VALUES ('JSTJ', '<', '小于', '计算条件', '1');
INSERT INTO `csb_dmms` VALUES ('JSTJ', '<=', '小于等于', '计算条件', '2');
INSERT INTO `csb_dmms` VALUES ('JSTJ', '<>', '不等于', '计算条件', '3');
INSERT INTO `csb_dmms` VALUES ('JSTJ', '=', '等于', '计算条件', '4');
INSERT INTO `csb_dmms` VALUES ('JSTJ', '>', '大于', '计算条件', '5');
INSERT INTO `csb_dmms` VALUES ('JSTJ', '>=', '大于等于', '计算条件', '6');
INSERT INTO `csb_dmms` VALUES ('JSTJ', 'BETWEEN', '区间', '计算条件', '7');
INSERT INTO `csb_dmms` VALUES ('JSTJ', 'IN', '包含', '计算条件', '8');
INSERT INTO `csb_dmms` VALUES ('JSTJ', 'LIKE', 'Like(%)', '计算条件', '9');
INSERT INTO `csb_dmms` VALUES ('JSTJ', 'NOT IN', '不包含', '计算条件', '10');
INSERT INTO `csb_dmms` VALUES ('XJJSTJ', '<', '小于', '客户星级计算条件', '1');
INSERT INTO `csb_dmms` VALUES ('XJJSTJ', '<=', '小于等于', '客户星级计算条件', '2');
INSERT INTO `csb_dmms` VALUES ('XJJSTJ', '<>', '不等于', '客户星级计算条件', '3');
INSERT INTO `csb_dmms` VALUES ('XJJSTJ', '=', '等于', '客户星级计算条件', '4');
INSERT INTO `csb_dmms` VALUES ('XJJSTJ', '>', '大于', '客户星级计算条件', '5');
INSERT INTO `csb_dmms` VALUES ('XJJSTJ', '>=', '大于等于', '客户星级计算条件', '6');
INSERT INTO `csb_dmms` VALUES ('XJJSTJ', 'BETWEEN', '区间', '客户星级计算条件', '7');
INSERT INTO `csb_dmms` VALUES ('XJJSTJ', ' ', ' ', '客户星级计算条件', '8');
INSERT INTO `csb_dmms` VALUES ('JSZHLX', '1', '全部账户', '计算帐户类型', '1');
INSERT INTO `csb_dmms` VALUES ('JSZHLX', '2', '基数账户', '计算帐户类型', '2');
INSERT INTO `csb_dmms` VALUES ('JYJGBZ', 'N', '非经营机构', '经营机构标志', '1');
INSERT INTO `csb_dmms` VALUES ('JYJGBZ', 'Y', '经营机构', '经营机构标志', '2');
INSERT INTO `csb_dmms` VALUES ('JYLX', '1', '个人存单、折账户类', '交易类型', '1');
INSERT INTO `csb_dmms` VALUES ('JYLX', '10', '存款类', '交易类型', '2');
INSERT INTO `csb_dmms` VALUES ('JYLX', '11', '取款类', '交易类型', '3');
INSERT INTO `csb_dmms` VALUES ('JYLX', '12', '消费类', '交易类型', '4');
INSERT INTO `csb_dmms` VALUES ('JYLX', '13', '代收代付类', '交易类型', '5');
INSERT INTO `csb_dmms` VALUES ('JYLX', '14', '代理业务类', '交易类型', '6');
INSERT INTO `csb_dmms` VALUES ('JYLX', '2', '对公或内部账户类', '交易类型', '7');
INSERT INTO `csb_dmms` VALUES ('JYLX', '3', '贷款类', '交易类型', '8');
INSERT INTO `csb_dmms` VALUES ('JYLX', '4', '信合卡类', '交易类型', '9');
INSERT INTO `csb_dmms` VALUES ('JYLX', '5', '重要空白凭证管理', '交易类型', '10');
INSERT INTO `csb_dmms` VALUES ('JYLX', '6', 'ATM交易', '交易类型', '11');
INSERT INTO `csb_dmms` VALUES ('JYLX', '7', '大小额支付', '交易类型', '12');
INSERT INTO `csb_dmms` VALUES ('JYLX', '8', '农信银业务', '交易类型', '13');
INSERT INTO `csb_dmms` VALUES ('JYLX', '9', '预授权类', '交易类型', '14');
INSERT INTO `csb_dmms` VALUES ('KDJ', '1', '普通卡', '卡等级', '1');
INSERT INTO `csb_dmms` VALUES ('KDJ', '2', '金卡', '卡等级', '2');
INSERT INTO `csb_dmms` VALUES ('KDJ', '3', '白金卡', '卡等级', '3');
INSERT INTO `csb_dmms` VALUES ('KDJ', '4', '钻石卡', '卡等级', '4');
INSERT INTO `csb_dmms` VALUES ('KHBDFX', 'asc', '减', '变动方向', '1');
INSERT INTO `csb_dmms` VALUES ('KHBDFX', 'desc', '增', '变动方向', '2');
INSERT INTO `csb_dmms` VALUES ('KHDX', 'HY', '行员', '考核对象', '1');
INSERT INTO `csb_dmms` VALUES ('KHDX', 'JG', '机构', '考核对象', '2');
INSERT INTO `csb_dmms` VALUES ('KHDXLX', '1', '机构', '考核对象类型', '1');
INSERT INTO `csb_dmms` VALUES ('KHDXLX', '2', '个人', '考核对象类型', '2');
INSERT INTO `csb_dmms` VALUES ('KHDXLY', '-1', '所属机构', '考核对象来源', '1');
INSERT INTO `csb_dmms` VALUES ('KHDXLY', '-2', '机构公共户', '考核对象来源', '2');
INSERT INTO `csb_dmms` VALUES ('KHDXLY', '0', '自身', '考核对象来源', '3');
INSERT INTO `csb_dmms` VALUES ('KHDXLY', '1', '总行', '考核对象来源', '4');
INSERT INTO `csb_dmms` VALUES ('KHDXLY', '2', '分行', '考核对象来源', '5');
INSERT INTO `csb_dmms` VALUES ('KHDXLY', '3', '支行', '考核对象来源', '6');
INSERT INTO `csb_dmms` VALUES ('KHFS', '1', '本行账户分配他行', '跨行方式', '1');
INSERT INTO `csb_dmms` VALUES ('KHFS', '2', '他行账户分配本行', '跨行方式', '2');
INSERT INTO `csb_dmms` VALUES ('KHFXKHLX', '1', '对私', '客户分析客户类型', '1');
INSERT INTO `csb_dmms` VALUES ('KHFXKHLX', '2', '对公', '客户分析客户类型', '2');
INSERT INTO `csb_dmms` VALUES ('KHFXSJLX', '1', '时点余额', '客户分析数据类型', '1');
INSERT INTO `csb_dmms` VALUES ('KHFXSJLX', '2', '年日均余额', '客户分析数据类型', '2');
INSERT INTO `csb_dmms` VALUES ('KHGXDCXPX', 'rs.cknrj', '存款日均', '客户分析排序列', '1');
INSERT INTO `csb_dmms` VALUES ('KHGXDCXPX', 'rs.cksd', '存款时点', '客户分析排序列', '2');
INSERT INTO `csb_dmms` VALUES ('KHGXDCXPX', 'rs.dknrj', '贷款日均', '客户分析排序列', '3');
INSERT INTO `csb_dmms` VALUES ('KHGXDCXPX', 'rs.dksd', '贷款时点', '客户分析排序列', '4');
INSERT INTO `csb_dmms` VALUES ('KHLX', '1', '企业', '客户类型', '1');
INSERT INTO `csb_dmms` VALUES ('KHLX', '2', '个人', '客户类型', '2');
INSERT INTO `csb_dmms` VALUES ('KHRQ', 'NC', '年初', '考核日期', '1');
INSERT INTO `csb_dmms` VALUES ('KHRQ', 'QNTQ', '去年同期', '考核日期', '2');
INSERT INTO `csb_dmms` VALUES ('KHRQ', 'SJM', '上季末', '考核日期', '3');
INSERT INTO `csb_dmms` VALUES ('KHRQ', 'SNM', '上年末', '考核日期', '4');
INSERT INTO `csb_dmms` VALUES ('KHRQ', 'SYM', '上月末', '考核日期', '5');
INSERT INTO `csb_dmms` VALUES ('KHRQ', 'TJRQ', '统计日期', '考核日期', '6');
INSERT INTO `csb_dmms` VALUES ('KHZHLX', 'ck', '存款', '帐户类型', '1');
INSERT INTO `csb_dmms` VALUES ('KHZHLX', 'dk', '贷款', '帐户类型', '2');
INSERT INTO `csb_dmms` VALUES ('KHZQ', '1', '月', '考核周期', '1');
INSERT INTO `csb_dmms` VALUES ('KHZQ', '2', '季', '考核周期', '2');
INSERT INTO `csb_dmms` VALUES ('KHZQ', '3', '年', '考核周期', '3');
INSERT INTO `csb_dmms` VALUES ('KL', '1', '借记卡', '卡类', '1');
INSERT INTO `csb_dmms` VALUES ('KL', '2', '贷记卡(信用卡)', '卡类', '2');
INSERT INTO `csb_dmms` VALUES ('KL', '3', '准贷记卡', '卡类', '3');
INSERT INTO `csb_dmms` VALUES ('KZTBZ', '0', '正常', '卡状态标志', '1');
INSERT INTO `csb_dmms` VALUES ('KZTBZ', '1', '销卡', '卡状态标志', '2');
INSERT INTO `csb_dmms` VALUES ('KZTBZ', '2', '冻结', '卡状态标志', '3');
INSERT INTO `csb_dmms` VALUES ('KZTBZ', '3', '其他', '卡状态标志', '4');
INSERT INTO `csb_dmms` VALUES ('ORDERBYTYPE', 'ASC', '升序', '排序类型', '1');
INSERT INTO `csb_dmms` VALUES ('ORDERBYTYPE', 'DESC', '降序', '排序类型', '2');
INSERT INTO `csb_dmms` VALUES ('RWZT', '0', '未执行', '任务状态', '1');
INSERT INTO `csb_dmms` VALUES ('RWZT', '1', '出错', '任务状态', '2');
INSERT INTO `csb_dmms` VALUES ('RWZT', '2', '执行中', '任务状态', '3');
INSERT INTO `csb_dmms` VALUES ('RWZT', '3', '完成', '任务状态', '4');
INSERT INTO `csb_dmms` VALUES ('RZCZLX', '01', '新增', '日志记录标志', '1');
INSERT INTO `csb_dmms` VALUES ('RZCZLX', '02', '修改', '日志记录标志', '2');
INSERT INTO `csb_dmms` VALUES ('SJFL', '4', '呆账', '四级分类', '4');
INSERT INTO `csb_dmms` VALUES ('SJLX', '0', '正常', '数据类型', '1');
INSERT INTO `csb_dmms` VALUES ('SJLX', '1', '警告', '数据类型', '2');
INSERT INTO `csb_dmms` VALUES ('SJLX', '2', '错误', '数据类型', '3');
INSERT INTO `csb_dmms` VALUES ('SYS_GJ_CXZT', '0', '未执行', '程序执行状态', '1');
INSERT INTO `csb_dmms` VALUES ('SYS_GJ_CXZT', '1', '出错', '程序执行状态', '2');
INSERT INTO `csb_dmms` VALUES ('SYS_GJ_CXZT', '2', '执行中', '程序执行状态', '3');
INSERT INTO `csb_dmms` VALUES ('SYS_GJ_CXZT', '3', '完成', '程序执行状态', '4');
INSERT INTO `csb_dmms` VALUES ('TJKJ', '0', '会计口径', '统计口径', '1');
INSERT INTO `csb_dmms` VALUES ('TJKJ', '1', '考核口径', '统计口径', '2');
INSERT INTO `csb_dmms` VALUES ('XTRZPX', 'SJLX', '事件类型', '系统日志排序', '1');
INSERT INTO `csb_dmms` VALUES ('XTRZPX', 'SJSJ', '事件时间', '系统日志排序', '2');
INSERT INTO `csb_dmms` VALUES ('YF', '1', '一月', '月份', '1');
INSERT INTO `csb_dmms` VALUES ('YF', '10', '十月', '月份', '2');
INSERT INTO `csb_dmms` VALUES ('YF', '11', '十一月', '月份', '3');
INSERT INTO `csb_dmms` VALUES ('YF', '12', '十二月', '月份', '4');
INSERT INTO `csb_dmms` VALUES ('YF', '2', '二月', '月份', '5');
INSERT INTO `csb_dmms` VALUES ('YF', '3', '三月', '月份', '6');
INSERT INTO `csb_dmms` VALUES ('YF', '4', '四月', '月份', '7');
INSERT INTO `csb_dmms` VALUES ('YF', '5', '五月', '月份', '8');
INSERT INTO `csb_dmms` VALUES ('YF', '6', '六月', '月份', '9');
INSERT INTO `csb_dmms` VALUES ('YF', '7', '七月', '月份', '10');
INSERT INTO `csb_dmms` VALUES ('YF', '8', '八月', '月份', '11');
INSERT INTO `csb_dmms` VALUES ('YF', '9', '九月', '月份', '12');
INSERT INTO `csb_dmms` VALUES ('YJLX', 'fxzb', '基础指标', '业绩类型', '1');
INSERT INTO `csb_dmms` VALUES ('YJLX', 'khzb', '考核指标', '业绩类型', '2');
INSERT INTO `csb_dmms` VALUES ('YXRYBZ', 'N', '非营销人员', '营销人员标志', '1');
INSERT INTO `csb_dmms` VALUES ('YXRYBZ', 'Y', '营销人员', '营销人员标志', '2');
INSERT INTO `csb_dmms` VALUES ('YYJLPPBZ', '0', '未匹配', '预约记录匹配标志', '1');
INSERT INTO `csb_dmms` VALUES ('YYJLPPBZ', '1', '已匹配', '预约记录匹配标志', '2');
INSERT INTO `csb_dmms` VALUES ('YYRZPX', 'HYDH', '行员代号', '营销人员标志', '3');
INSERT INTO `csb_dmms` VALUES ('YYRZPX', 'SJLX', '事件类型', '营销人员标志', '4');
INSERT INTO `csb_dmms` VALUES ('YYRZPX', 'SJSJ', '事件时间', '营销人员标志', '5');
INSERT INTO `csb_dmms` VALUES ('YYZL', '1', '产品营销计价', '应用种类', '1');
INSERT INTO `csb_dmms` VALUES ('YYZL', '2', '计分卡', '应用种类', '2');
INSERT INTO `csb_dmms` VALUES ('ZBBZ', '01', '人民币', '指标币种', '1');
INSERT INTO `csb_dmms` VALUES ('ZBBZ', '0A', '本外币折人民币', '指标币种', '2');
INSERT INTO `csb_dmms` VALUES ('ZBBZ', '0B', '外币折人民币', '指标币种', '3');
INSERT INTO `csb_dmms` VALUES ('ZBBZ', '0C', '外币折美元', '指标币种', '4');
INSERT INTO `csb_dmms` VALUES ('ZBBZ', 'FF', '非币种', '指标币种', '5');
INSERT INTO `csb_dmms` VALUES ('ZBCSBS', '0', '参数值为科目号', '指标参数标识', '1');
INSERT INTO `csb_dmms` VALUES ('ZBCSBS', '1', '分子', '指标参数标识', '2');
INSERT INTO `csb_dmms` VALUES ('ZBCSBS', '2', '分母', '指标参数标识', '3');
INSERT INTO `csb_dmms` VALUES ('ZBCSBS', '3', '其它', '指标参数标识', '4');
INSERT INTO `csb_dmms` VALUES ('ZBCSBS', '4', '被乘数', '指标参数标识', '5');
INSERT INTO `csb_dmms` VALUES ('ZBCSBS', '5', '乘数', '指标参数标识', '6');
INSERT INTO `csb_dmms` VALUES ('ZBDW', '1', '元', '指标单位', '1');
INSERT INTO `csb_dmms` VALUES ('ZBDW', '2', '%', '指标单位', '2');
INSERT INTO `csb_dmms` VALUES ('ZBDW', '3', '张', '指标单位', '3');
INSERT INTO `csb_dmms` VALUES ('ZBDW', '4', '户', '指标单位', '4');
INSERT INTO `csb_dmms` VALUES ('ZBDW', '5', '个', '指标单位', '5');
INSERT INTO `csb_dmms` VALUES ('ZBDW', '6', '份', '指标单位', '6');
INSERT INTO `csb_dmms` VALUES ('ZBDW', '7', '笔', '指标单位', '7');
INSERT INTO `csb_dmms` VALUES ('ZBDW', '8', '分', '指标单位', '8');
INSERT INTO `csb_dmms` VALUES ('ZBJB', '0', '固定指标', '指标级别', '1');
INSERT INTO `csb_dmms` VALUES ('ZBJB', '1', '一级指标', '指标级别', '2');
INSERT INTO `csb_dmms` VALUES ('ZBJB', '2', '二级指标', '指标级别', '3');
INSERT INTO `csb_dmms` VALUES ('ZBJB', '3', '三级指标', '指标级别', '4');
INSERT INTO `csb_dmms` VALUES ('ZBJB', '4', '四级指标', '指标级别', '5');
INSERT INTO `csb_dmms` VALUES ('ZBJB', '5', '五级指标', '指标级别', '6');
INSERT INTO `csb_dmms` VALUES ('ZBJB', '6', '六级指标', '指标级别', '7');
INSERT INTO `csb_dmms` VALUES ('ZBJB', '7', '七级指标', '指标级别', '8');
INSERT INTO `csb_dmms` VALUES ('ZBJB', '8', '八级指标', '指标级别', '9');
INSERT INTO `csb_dmms` VALUES ('ZBJB', '9', '九级指标', '指标级别', '10');
INSERT INTO `csb_dmms` VALUES ('ZBJB', 'A', '顶级指标', '指标级别', '11');
INSERT INTO `csb_dmms` VALUES ('ZBJSPL', '1', '每天', '指标计算频率', '1');
INSERT INTO `csb_dmms` VALUES ('ZBJSPL', '2', '每月', '指标计算频率', '2');
INSERT INTO `csb_dmms` VALUES ('ZBJSPL', '3', '每季', '指标计算频率', '3');
INSERT INTO `csb_dmms` VALUES ('ZBJSPL', '4', '每年', '指标计算频率', '4');
INSERT INTO `csb_dmms` VALUES ('ZBJSPL', '5', '每旬', '指标计算频率', '5');
INSERT INTO `csb_dmms` VALUES ('ZBSDBS', '1', '时点', '指标时段标识', '1');
INSERT INTO `csb_dmms` VALUES ('ZBSDBS', '2', '月日均', '指标时段标识', '2');
INSERT INTO `csb_dmms` VALUES ('ZBSDBS', '3', '季日均', '指标时段标识', '3');
INSERT INTO `csb_dmms` VALUES ('ZBSDBS', '4', '年日均', '指标时段标识', '4');
INSERT INTO `csb_dmms` VALUES ('ZBSDBS', '5', '月累计', '指标时段标识', '5');
INSERT INTO `csb_dmms` VALUES ('ZBSDBS', '6', '季累计', '指标时段标识', '6');
INSERT INTO `csb_dmms` VALUES ('ZBSDBS', '7', '年累计', '指标时段标识', '7');
INSERT INTO `csb_dmms` VALUES ('ZBSDBS', '8', '月平', '指标时段标识', '8');
INSERT INTO `csb_dmms` VALUES ('ZBTJKJ', '0', '机构会计口径', '指标统计口径', '1');
INSERT INTO `csb_dmms` VALUES ('ZBTJKJ', '1', '机构考核口径', '指标统计口径', '2');
INSERT INTO `csb_dmms` VALUES ('ZBTJKJ', '2', '行员考核口径', '指标统计口径', '3');
INSERT INTO `csb_dmms` VALUES ('ZBWHFS', '0', '数据源', '指标维护方式', '1');
INSERT INTO `csb_dmms` VALUES ('ZBWHFS', '1', '指标库', '指标维护方式', '2');
INSERT INTO `csb_dmms` VALUES ('ZBWHFS', '2', '手工录入', '指标维护方式', '3');
INSERT INTO `csb_dmms` VALUES ('ZBWHFS', '3', '特殊指标', '指标维护方式', '4');
INSERT INTO `csb_dmms` VALUES ('ZBZYJB', '0', '重要指标', '指标重要级别', '1');
INSERT INTO `csb_dmms` VALUES ('ZBZYJB', '1', '非重要指标', '指标重要级别', '2');
INSERT INTO `csb_dmms` VALUES ('ZHBS', '1', '对公', '帐户标识', '1');
INSERT INTO `csb_dmms` VALUES ('ZHBS', '2', '对私', '帐户标识', '2');
INSERT INTO `csb_dmms` VALUES ('ZHGXPX', 'KHRQ', '开户日期', '帐户关系排序', '1');
INSERT INTO `csb_dmms` VALUES ('ZHGXPX', 'ZHNRJYE', '年日均余额', '帐户关系排序', '2');
INSERT INTO `csb_dmms` VALUES ('ZHGXPX', 'ZHYE', '时点余额', '帐户关系排序', '3');
INSERT INTO `csb_dmms` VALUES ('ZHLX', '1', '存款', '帐户类型', '1');
INSERT INTO `csb_dmms` VALUES ('ZHLX', '2', '贷款', '帐户类型', '2');
INSERT INTO `csb_dmms` VALUES ('ZHMXPX', 'ZHNRJYE', '年日均余额', '帐户明细排序', '1');
INSERT INTO `csb_dmms` VALUES ('ZHMXPX', 'ZHYE', '时点余额', '帐户明细排序', '2');
INSERT INTO `csb_dmms` VALUES ('ZJLX', '01', '身份证', '预约存款的证件类别', '1');
INSERT INTO `csb_dmms` VALUES ('ZJLX', '02', '护照', '预约存款的证件类别', '2');
INSERT INTO `csb_dmms` VALUES ('ZJLX', '03', '军官证', '预约存款的证件类别', '3');
INSERT INTO `csb_dmms` VALUES ('ZJLX', '00', '其它', '预约存款的证件类别', '4');
INSERT INTO `csb_dmms` VALUES ('ZJYW', '1', '手机银行', '中间业务', '1');
INSERT INTO `csb_dmms` VALUES ('ZJYW', '2', '网上银行', '中间业务', '2');
INSERT INTO `csb_dmms` VALUES ('ZJYW', '3', 'POS商户', '中间业务', '3');
INSERT INTO `csb_dmms` VALUES ('ZSLX', '1', '按交易笔数', '折算类型', '1');
INSERT INTO `csb_dmms` VALUES ('ZSLX', '2', '按交易金额', '折算类型', '2');
INSERT INTO `csb_dmms` VALUES ('ZXBZ', 'N', '正常', '注销标志', '1');
INSERT INTO `csb_dmms` VALUES ('ZXBZ', 'Y', '注销', '注销标志', '2');
INSERT INTO `csb_dmms` VALUES ('JJRLX', '0', '工作日', '节假日管理', '1');
INSERT INTO `csb_dmms` VALUES ('JJRLX', '1', '正常周末', '节假日管理', '2');
INSERT INTO `csb_dmms` VALUES ('JJRLX', '2', '节假日', '节假日管理', '3');
INSERT INTO `csb_dmms` VALUES ('XQZ', '1', '星期一', '节假日管理', '1');
INSERT INTO `csb_dmms` VALUES ('XQZ', '2', '星期二', '节假日管理', '2');
INSERT INTO `csb_dmms` VALUES ('XQZ', '3', '星期三', '节假日管理', '3');
INSERT INTO `csb_dmms` VALUES ('XQZ', '4', '星期四', '节假日管理', '4');
INSERT INTO `csb_dmms` VALUES ('XQZ', '5', '星期五', '节假日管理', '5');
INSERT INTO `csb_dmms` VALUES ('XQZ', '6', '星期六', '节假日管理', '6');
INSERT INTO `csb_dmms` VALUES ('XQZ', '7', '星期日', '节假日管理', '7');
INSERT INTO `csb_dmms` VALUES ('FYZL', 'A001', '存款维护费', '机构费用管理', '1');
INSERT INTO `csb_dmms` VALUES ('FYZL', 'A002', '展业费用', '机构费用管理', '2');
INSERT INTO `csb_dmms` VALUES ('FYZL', 'A003', '业务招待费', '机构费用管理', '3');
INSERT INTO `csb_dmms` VALUES ('FYZL', 'A004', '网点办公费', '机构费用管理', '4');
INSERT INTO `csb_dmms` VALUES ('FYZL', 'A005', '人员办公费', '机构费用管理', '5');
INSERT INTO `csb_dmms` VALUES ('ZJYWSRKM', '20120503', ' 电子银行业务收入', '手工导入中间业务收入科目', '1');
INSERT INTO `csb_dmms` VALUES ('ZJYWSRKM', '20120501', ' 短信服务业务收入 ', '手工导入中间业务收入科目', '2');
INSERT INTO `csb_dmms` VALUES ('ZJYWSRKM', '20120504', ' 网上银行业务收入', '手工导入中间业务收入科目', '3');
INSERT INTO `csb_dmms` VALUES ('ZJYWSRKM', '20120508', ' 电话银行业务收入', '手工导入中间业务收入科目', '4');
INSERT INTO `csb_dmms` VALUES ('ZJYWSRKM', '20120502', ' 理财业务收入', '手工导入中间业务收入科目', '5');
INSERT INTO `csb_dmms` VALUES ('ZJYWSRKM', '20180502', ' 代收代付业务收入', '手工导入中间业务收入科目', '6');
INSERT INTO `csb_dmms` VALUES ('ZJYWSRKM', '20181002', ' 代理保险业务收入', '手工导入中间业务收入科目', '7');
INSERT INTO `csb_dmms` VALUES ('ZJYWSRKM', '20170501', ' 代理基金业务收入', '手工导入中间业务收入科目', '8');
INSERT INTO `csb_dmms` VALUES ('ZJYWSRKM', '20180504', ' POS手续费收入', '手工导入中间业务收入科目', '9');
INSERT INTO `csb_dmms` VALUES ('ZJYWSRKM', '20029901', ' ATM手续费收入', '手工导入中间业务收入科目', '10');
INSERT INTO `csb_dmms` VALUES ('CZQK', '1', '整改', '违规事件管理', '1');
INSERT INTO `csb_dmms` VALUES ('CZQK', '2', '罚款', '违规事件管理', '2');
INSERT INTO `csb_dmms` VALUES ('CZJG', '1', '通过', '违规事件管理', '1');
INSERT INTO `csb_dmms` VALUES ('CZJG', '0', '不通过', '违规事件管理', '2');
INSERT INTO `csb_dmms` VALUES ('BJJB', '1', '监控', '预警事件管理', '1');
INSERT INTO `csb_dmms` VALUES ('BJJB', '2', '预警', '预警事件管理', '2');
INSERT INTO `csb_dmms` VALUES ('BJJB', '3', '黄色报警', '预警事件管理', '3');
INSERT INTO `csb_dmms` VALUES ('BJJB', '4', '橙色报警', '预警事件管理', '4');
INSERT INTO `csb_dmms` VALUES ('BJJB', '5', '红色报警', '预警事件管理', '5');
INSERT INTO `csb_dmms` VALUES ('CLJG', '1', '正常', '预警事件管理', '1');
INSERT INTO `csb_dmms` VALUES ('CLJG', '2', '异常', '预警事件管理', '2');
INSERT INTO `csb_dmms` VALUES ('BLWJFL', '3', '次级', '贷款不良五级分类', '1');
INSERT INTO `csb_dmms` VALUES ('BLWJFL', '4', '可疑', '贷款不良五级分类', '2');
INSERT INTO `csb_dmms` VALUES ('BLWJFL', '5', '损失', '贷款不良五级分类', '3');
INSERT INTO `csb_dmms` VALUES ('BLLX', '1', '表内', '不良类型', '1');
INSERT INTO `csb_dmms` VALUES ('BLLX', '2', '表外', '不良类型', '2');
INSERT INTO `csb_dmms` VALUES ('QSFS', '1', '现金清收', '不良贷款清收方式', '1');
INSERT INTO `csb_dmms` VALUES ('QSFS', '2', '呆账核销', '不良贷款清收方式', '2');
INSERT INTO `csb_dmms` VALUES ('QSFS', '3', '票据置换', '不良贷款清收方式', '3');
INSERT INTO `csb_dmms` VALUES ('QSFS', '4', '以物抵债', '不良贷款清收方式', '4');
INSERT INTO `csb_dmms` VALUES ('FXJLX', '1', '利息收入', '风险金类型', '1');
INSERT INTO `csb_dmms` VALUES ('FXJLX', '2', '客户管理', '风险金类型', '2');
INSERT INTO `csb_dmms` VALUES ('FHLX', '0', '未返还', '风险金返还状态', '1');
INSERT INTO `csb_dmms` VALUES ('FHLX', '1', '已返还', '风险金返还状态', '2');
INSERT INTO `csb_dmms` VALUES ('QZSM', '1', '原始值', '考核方案权重说明', '1');
INSERT INTO `csb_dmms` VALUES ('QZSM', '2', '权重后值', '考核方案权重说明', '2');
INSERT INTO `csb_dmms` VALUES ('QYGM', '1', '微小', '客户企业规模', '1');
INSERT INTO `csb_dmms` VALUES ('QYGM', '2', '小型', '客户企业规模', '2');
INSERT INTO `csb_dmms` VALUES ('QYGM', '3', '中型', '客户企业规模', '3');
INSERT INTO `csb_dmms` VALUES ('QYGM', '4', '大型', '客户企业规模', '4');
INSERT INTO `csb_dmms` VALUES ('KHJJXZ', '01', '非公司企业法人', '客户经济性质', '1');
INSERT INTO `csb_dmms` VALUES ('KHJJXZ', '02', '有限责任公司', '客户经济性质', '2');
INSERT INTO `csb_dmms` VALUES ('KHJJXZ', '03', '股份有限责任公司', '客户经济性质', '3');
INSERT INTO `csb_dmms` VALUES ('KHJJXZ', '04', '个体工商户', '客户经济性质', '4');
INSERT INTO `csb_dmms` VALUES ('KHJJXZ', '05', '私营独资企业', '客户经济性质', '5');
INSERT INTO `csb_dmms` VALUES ('KHJJXZ', '06', '私营合伙企业', '客户经济性质', '6');
INSERT INTO `csb_dmms` VALUES ('LJFH', '＋', '加', '逻辑运算符号', '1');
INSERT INTO `csb_dmms` VALUES ('LJFH', '-', '减', '逻辑运算符号', '2');
INSERT INTO `csb_dmms` VALUES ('LJFH', '*', '乘', '逻辑运算符号', '3');
INSERT INTO `csb_dmms` VALUES ('LJFH', '/', '除', '逻辑运算符号', '4');
INSERT INTO `csb_dmms` VALUES ('DJPDKHLB', '1', '授信企业客户', '等级评定客户类别', '1');
INSERT INTO `csb_dmms` VALUES ('DJPDKHLB', '2', '非授信企业客户', '等级评定客户类别', '2');
INSERT INTO `csb_dmms` VALUES ('DJPDKHLB', '3', '个人客户', '等级评定客户类别', '3');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'A', '农/林/牧/渔业 ', '客户行业类别', '1');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'B', '采矿业 ', '客户行业类别', '2');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'C', '制造业 ', '客户行业类别', '3');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'D', '电力/热力/燃气及水生产和供应业 ', '客户行业类别', '4');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'E', '建筑业 ', '客户行业类别', '5');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'F', '批发和零售业 ', '客户行业类别', '6');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'G', '交通运输仓储和邮政业 ', '客户行业类别', '7');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'H', '住宿和餐饮业 ', '客户行业类别', '8');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'I', '信息传输/软件和信息技术服务业 ', '客户行业类别', '9');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'J', '金融业 ', '客户行业类别', '10');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'K', '房地产业', '客户行业类别', '11');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'L', '租赁和商务服务业 ', '客户行业类别', '12');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'M', '科学研究和技术服务业 ', '客户行业类别', '13');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'N', '水利/环境和公共设施管理业 ', '客户行业类别', '14');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'O', '居民服务/修理和其他服务业 ', '客户行业类别', '15');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'P', '教育 ', '客户行业类别', '16');
INSERT INTO `csb_dmms` VALUES ('MENU_SYS_TYPE', '0', '资源系统', '属于所有资源系统', '0');
INSERT INTO `csb_dmms` VALUES ('MENU_SYS_TYPE', '1', 'A银行', '属于A银行系统', '1');
INSERT INTO `csb_dmms` VALUES ('MENU_SYS_TYPE', '2', 'B银行', '属于B银行系统 ', '2');
INSERT INTO `csb_dmms` VALUES ('SPZSZFS', '1', '审批者角色', '业绩流程管理-审批步骤', '1');
INSERT INTO `csb_dmms` VALUES ('SPXYFS', '1', '任意人审批', '业绩流程管理-审批步骤', '1');
INSERT INTO `csb_dmms` VALUES ('SPZT', '0', '未审批', '业绩审批', '1');
INSERT INTO `csb_dmms` VALUES ('SPZT', '1', '已审批', '业绩审批', '2');
INSERT INTO `csb_dmms` VALUES ('SPJG', '0', '开始', '业绩审批', '1');
INSERT INTO `csb_dmms` VALUES ('SPJG', '1', '同意', '业绩审批', '2');
INSERT INTO `csb_dmms` VALUES ('SPJG', '2', '否决', '业绩审批', '3');
INSERT INTO `csb_dmms` VALUES ('YWLCZT', '0', '取消', '业绩流程管理', '1');
INSERT INTO `csb_dmms` VALUES ('YWLCZT', '1', '选中', '业绩流程管理', '2');
INSERT INTO `csb_dmms` VALUES ('ZYBZ', '0', '管护', '业绩管理-存款', '2');
INSERT INTO `csb_dmms` VALUES ('ZYBZ', '1', '自营', '业绩管理-存款', '1');
INSERT INTO `csb_dmms` VALUES ('GXLY', '00', '存量清理', '业绩管理', '1');
INSERT INTO `csb_dmms` VALUES ('GXLY', '01', '业绩预约', '业绩管理', '2');
INSERT INTO `csb_dmms` VALUES ('GXLY', '02', '接口提供', '业绩管理', '3');
INSERT INTO `csb_dmms` VALUES ('GXLY', '03', '同机构存量默认', '业绩管理', '4');
INSERT INTO `csb_dmms` VALUES ('GXLY', '11', '业绩认领', '业绩管理', '5');
INSERT INTO `csb_dmms` VALUES ('GXLY', '12', '业绩认领导入', '业绩管理', '6');
INSERT INTO `csb_dmms` VALUES ('GXLY', '21', '业绩调整', '业绩管理', '7');
INSERT INTO `csb_dmms` VALUES ('GXLY', '22', '业绩调整导入', '业绩管理', '8');
INSERT INTO `csb_dmms` VALUES ('GXLY', '23', '员工业绩转移', '业绩管理', '9');
INSERT INTO `csb_dmms` VALUES ('GXZT', '0', '正常', '业绩管理-存款', '1');
INSERT INTO `csb_dmms` VALUES ('GXZT', '1', '锁定', '业绩管理-存款', '2');
INSERT INTO `csb_dmms` VALUES ('RLBS', '0', '按账户', '业绩管理-存款', '1');
INSERT INTO `csb_dmms` VALUES ('RLBS', '1', '按客户', '业绩管理-存款', '2');
INSERT INTO `csb_dmms` VALUES ('CKTJGMRYJPDGZ', '01', '同机构存量客户', '业绩流程管理-默认业绩', '1');
INSERT INTO `csb_dmms` VALUES ('CKTJGMRYJPDGZ', '02', '同机构存量客户的账户归属关系一致', '业绩流程管理-默认业绩', '2');
INSERT INTO `csb_dmms` VALUES ('CKTJGMRYJPDGZ', '03', '同机构存量客户的账户100% 归属同一行员', '业绩流程管理-默认业绩', '3');
INSERT INTO `csb_dmms` VALUES ('CKTJGMRYJMRGZ', '01', '按照同机构存量最近开账户当天归属关系默认', '业绩流程管理-默认业绩', '1');
INSERT INTO `csb_dmms` VALUES ('CKTJGMRYJMRGZ', '02', '按照同机构存量账户归属关系进行默认', '业绩流程管理-默认业绩', '2');
INSERT INTO `csb_dmms` VALUES ('CDLX', '3', '按钮', '菜单类型', '4');
INSERT INTO `csb_dmms` VALUES ('JGDJ', '1', '总行', '机构等级', '1');
INSERT INTO `csb_dmms` VALUES ('JGDJ', '2', '分行', '机构等级', '2');
INSERT INTO `csb_dmms` VALUES ('JGDJ', '3', '支行', '机构等级', '3');
INSERT INTO `csb_dmms` VALUES ('JGDJ', '4', '经营行', '机构等级', '4');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'Q', '卫生和社会工作 ', '客户行业类别', '17');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'R', '文化/体育和娱乐业 ', '客户行业类别', '18');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'S', '公共管理/社会保障和社会组织 ', '客户行业类别', '19');
INSERT INTO `csb_dmms` VALUES ('KHHYLB', 'T', '国际组织', '客户行业类别', '20');
INSERT INTO `csb_dmms` VALUES ('ZBHSDW', '1', '万元', '万元', '1');
INSERT INTO `csb_dmms` VALUES ('ZBHSDW', '2', '笔', '笔', '2');
INSERT INTO `csb_dmms` VALUES ('ZBHSDW', '3', '户', '户', '3');
INSERT INTO `csb_dmms` VALUES ('DRZT', '0', '未导入', '导入状态', '1');
INSERT INTO `csb_dmms` VALUES ('DRZT', '1', '已导入', '导入状态', '2');
INSERT INTO `csb_dmms` VALUES ('LRCSLX', 'BZCS', '标准参数', '利润参数类型', '1');
INSERT INTO `csb_dmms` VALUES ('LRCSLX', 'DKJTBL', '贷款五级分类计提比例', '利润参数类型', '2');
INSERT INTO `csb_dmms` VALUES ('LRCSLX', 'DKXTXX', '贷款形态系数标准', '利润参数类型', '3');
INSERT INTO `csb_dmms` VALUES ('DKJTBL', 'CJJTBL', '次级计提比例', '贷款计提比例', '1');
INSERT INTO `csb_dmms` VALUES ('DKJTBL', 'GZJTBL', '关注计提比例', '贷款计提比例', '2');
INSERT INTO `csb_dmms` VALUES ('DKJTBL', 'KYJTBL', '可疑计提比例', '贷款计提比例', '3');
INSERT INTO `csb_dmms` VALUES ('DKJTBL', 'SSJTBL', '损失计提比例', '贷款计提比例', '4');
INSERT INTO `csb_dmms` VALUES ('DKJTBL', 'ZCJTBL', '正常计提比例', '贷款计提比例', '5');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'DGZHYXHNRJ', '对公帐户有效户最低年日均标准', '标准参数', '1');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'DSZHYXHNRJ', '对私帐户有效户最低年日均标准', '标准参数', '2');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'POSYXHJYCS', 'POS有效户交易次数最低标准', '标准参数', '3');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'POSYXHJYJE', 'POS有效户交易金额最低标准', '标准参数', '4');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'POSYXHSXF', 'POS有效户手续费最低标准', '标准参数', '5');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'SJYXHJYCS', '手机银行有效户交易次数最低标准', '标准参数', '6');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'SJYXHJYJE', '手机银行有效户交易金额最低标准', '标准参数', '7');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'SJYXHSXF', '手机银行有效户手续费最低标准', '标准参数', '8');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'WYYXHJYCS', '网银有效户交易次数最低标准', '标准参数', '9');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'WYYXHJYJE', '网银有效户交易金额最低标准', '标准参数', '10');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'WYYXHSXF', '网银有效户手续费最低标准', '标准参数', '11');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'YXKJYCS', '有效卡交易次数最低标准', '标准参数', '12');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'YXKNRJYE', '有效卡最低年日均余额标准', '标准参数', '13');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'SSLXFXJJTBL', '贷款利息收入风险金计提比例', '标准参数', '14');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'DKGLFXJJTBL', '贷款客户管理风险金计提比例', '标准参数', '15');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'DKLXSRQZ', '计算贷款利息收入计奖的权重比例', '标准参数', '16');
INSERT INTO `csb_dmms` VALUES ('BZCS', 'GDCBFTBL', '固定成本分滩比例', '标准参数', '17');
INSERT INTO `csb_dmms` VALUES ('DKXTXX', 'ZCDKXTXX', '正常类贷款形态系数', '贷款形态系数', '1');
INSERT INTO `csb_dmms` VALUES ('DKXTXX', 'GZDKXTXX', '关注类贷款形态系数', '贷款形态系数', '2');
INSERT INTO `csb_dmms` VALUES ('DKXTXX', 'CJDKXTXX', '次级类贷款形态系数', '贷款形态系数', '3');
INSERT INTO `csb_dmms` VALUES ('DKXTXX', 'KYDKXTXX', '可疑类贷款形态系数', '贷款形态系数', '4');
INSERT INTO `csb_dmms` VALUES ('DKXTXX', 'SSDKXTXX', '损失类贷款形态系数', '贷款形态系数', '5');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'DKJEQJ', '贷款金额区间', '区间参数类型', '1');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'XJFCZSBS', '难易系数区间（现金付出）', '区间参数类型', '2');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'XJSRZSBS', '难易系数区间（现金收入）', '区间参数类型', '3');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'ZHYEQJ', '贷款账户余额区间', '区间参数类型', '4');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'CKYEQJ', '存款账户余额区间', '区间参数类型', '5');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'FFRQQJ', '发放日期区间', '区间参数类型', '6');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'DQRQQJ', '到期日期区间', '区间参数类型', '7');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'YQTSQJ', '逾期天数区间', '区间参数类型', '8');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'BCYWLXS', '补偿业务量调节系数', '区间参数类型', '9');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'GJYWCLJYJEQJ', '国际业务存量客户交易金额区间', '区间参数类型', '10');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'GJYWZLJYJEQJ', '国际业务存量客户交易金额区间', '区间参数类型', '11');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'JXGZQJ', '绩效工资区间分布', '区间参数类型', '12');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'TXLXSRJL', '贴现利息收入奖励标准区间', '区间参数类型', '13');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'YBDKLXSRJL', '一般贷款利息收入奖励标准区间', '区间参数类型', '14');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'DGDKGLJL', '对公贷款客户管理奖励标准区间', '区间参数类型', '15');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'DSDKGLJL', '对私贷款客户管理奖励标准区间', '区间参数类型', '16');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'DKQXKF', '贷款欠息扣罚标准区间', '区间参数类型', '17');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'CKJTJJXX', '存款阶梯计价系数', '区间参数类型', '18');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'LSKHNLQJ', '零售客户年龄区间', '区间参数类型', '19');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'CKDGKHYEQJ', '存款对公客户余额区间', '区间参数类型', '20');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'CKDSKHYEQJ', '存款对私客户余额区间', '区间参数类型', '21');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'DKDGKHYEQJ', '贷款对公客户余额区间', '区间参数类型', '22');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'DKDSKHYEQJ', '贷款对私客户余额区间', '区间参数类型', '23');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'DGCKNRJQJ', '对公存款年日均余额区间', '区间参数类型', '24');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'DSCKNRJQJ', '对私存款年日均余额区间', '区间参数类型', '25');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'DGCKJSQJ', '对公存款结算笔数区间', '区间参数类型', '26');
INSERT INTO `csb_dmms` VALUES ('YWLX', '1', '对公', 'FTP业务类型', '1');
INSERT INTO `csb_dmms` VALUES ('YWLX', '2', '个人', 'FTP业务类型', '2');
INSERT INTO `csb_dmms` VALUES ('DJFF', '1', '简易定价法', 'FTP定价方法', '1');
INSERT INTO `csb_dmms` VALUES ('DJFF', '2', '指定利率法', 'FTP定价方法', '2');
INSERT INTO `csb_dmms` VALUES ('DJQX', '1', 'RMB内部收益率曲线', 'FTP定价曲线', '1');
INSERT INTO `csb_dmms` VALUES ('DJQX', '2', 'FTP基准利率', 'FTP定价曲线', '2');
INSERT INTO `csb_dmms` VALUES ('SJSJDW', '2', '月', '数据时间单位', '1');
INSERT INTO `csb_dmms` VALUES ('SJSJDW', '4', '年', '数据时间单位', '2');
INSERT INTO `csb_dmms` VALUES ('LRSDBS', '1', '月累计', '利润报表时段', '1');
INSERT INTO `csb_dmms` VALUES ('LRSDBS', '2', '季累计', '利润报表时段', '2');
INSERT INTO `csb_dmms` VALUES ('LRSDBS', '3', '年累计', '利润报表时段', '3');
INSERT INTO `csb_dmms` VALUES ('FFBS', '0', '未发放', '贷款发放标识', '1');
INSERT INTO `csb_dmms` VALUES ('FFBS', '1', '当年发放', '贷款发放标识', '2');
INSERT INTO `csb_dmms` VALUES ('FFBS', '2', '往年发放', '贷款发放标识', '3');
INSERT INTO `csb_dmms` VALUES ('DQBS', '0', '未到期', '贷款到期标识', '1');
INSERT INTO `csb_dmms` VALUES ('DQBS', '1', '当月到期 ', '贷款到期标识', '2');
INSERT INTO `csb_dmms` VALUES ('DQBS', '2', '季初至上月末到期', '贷款到期标识', '3');
INSERT INTO `csb_dmms` VALUES ('DQBS', '3', '年初至上季末到期', '贷款到期标识', '4');
INSERT INTO `csb_dmms` VALUES ('DQBS', '4', '往年到期', '贷款到期标识', '5');
INSERT INTO `csb_dmms` VALUES ('GDZL', '1', '个人经营性', '贷款个贷种类', '1');
INSERT INTO `csb_dmms` VALUES ('GDZL', '2', '个人其他', '贷款个贷种类', '2');
INSERT INTO `csb_dmms` VALUES ('KHGXDCXPX', 'rs.hjlr', '合计利润', '客户分析排序列', '5');
INSERT INTO `csb_dmms` VALUES ('XTTZKYK', '0', '所有人', '所有人', '1');
INSERT INTO `csb_dmms` VALUES ('XTTZKYK', '1', '按机构', '按机构', '2');
INSERT INTO `csb_dmms` VALUES ('XTTZKYK', '2', '按行员类别', '按行员类别', '3');
INSERT INTO `csb_dmms` VALUES ('XTTZKYK', '3', '按行员', '按行员', '4');
INSERT INTO `csb_dmms` VALUES ('ZJYW', '5', '代发工资', '中间业务', '5');
INSERT INTO `csb_dmms` VALUES ('QJCSLX', 'ZRDKJEQJ', '责任贷款金额区间', '区间参数类型', '27');
INSERT INTO `csb_dmms` VALUES ('ZJYWDXLX', '1', '卡', ' 中间业务对象类型', '1');
INSERT INTO `csb_dmms` VALUES ('ZJYWDXLX', '2', '网上银行', ' 中间业务对象类型', '2');
INSERT INTO `csb_dmms` VALUES ('ZJYWDXLX', '3', '手机银行', ' 中间业务对象类型', '3');
INSERT INTO `csb_dmms` VALUES ('ZJYWDXLX', '4', 'POS商户', ' 中间业务对象类型', '4');
INSERT INTO `csb_dmms` VALUES ('ZJYWDXLX', '5', '代发工资', ' 中间业务对象类型', '5');
INSERT INTO `csb_dmms` VALUES ('ZJYWDXLX', '6', '国际业务', ' 中间业务对象类型', '6');
INSERT INTO `csb_dmms` VALUES ('ZJYWDXLX', '7', '基金', ' 中间业务对象类型', '7');
INSERT INTO `csb_dmms` VALUES ('ZJYWDXLX', '8', '理财', ' 中间业务对象类型', '8');
INSERT INTO `csb_dmms` VALUES ('ZJYWDXLX', '9', '保险', ' 中间业务对象类型', '9');
INSERT INTO `csb_dmms` VALUES ('ZJYWDXLX', '10', '第三方存管', ' 中间业务对象类型', '10');
INSERT INTO `csb_dmms` VALUES ('ZJYWDXLX', '11', '短信通', ' 中间业务对象类型', '11');
INSERT INTO `csb_dmms` VALUES ('ckyxj', '0', '低', '查看优先级0-低/1-高', '1');
INSERT INTO `csb_dmms` VALUES ('ckyxj', '1', '高', '查看优先级0-低/1-高', '2');
INSERT INTO `csb_dmms` VALUES ('DJPDZQ', 'YF', '月份', '等级评定周期', '1');
INSERT INTO `csb_dmms` VALUES ('DJPDZQ', 'JD', '季度', '等级评定周期', '2');
INSERT INTO `csb_dmms` VALUES ('DJPDZQ', 'BN', '半年', '等级评定周期', '3');
INSERT INTO `csb_dmms` VALUES ('DJPDZQ', 'QN', '全年', '等级评定周期', '4');
INSERT INTO `csb_dmms` VALUES ('YZKHPJYESZ', '100', '优质客户平均余额', '个人桌面-优质客户时点余额标准', '1');
INSERT INTO `csb_dmms` VALUES ('YZKHSDYESZ', '500', '优质客户时点余额', '个人桌面-优质客户时点余额标准', '2');
INSERT INTO `csb_dmms` VALUES ('DKTZYS', '1', '产品调整值', '贷款调整因素', '1');
INSERT INTO `csb_dmms` VALUES ('DKTZYS', '2', '期限调整值', '贷款调整因素', '2');
INSERT INTO `csb_dmms` VALUES ('CKTZYS', '1', '产品调整值', '存款调整因素', '1');
INSERT INTO `csb_dmms` VALUES ('DKTZYS', '3', '信用风险调整值', '贷款调整因素', '3');
INSERT INTO `csb_dmms` VALUES ('DKTZYS', '4', '政策性调整值', '贷款调整因素', '4');
INSERT INTO `csb_dmms` VALUES ('CKTZYS', '2', '期限调整值', '存款调整因素', '2');
INSERT INTO `csb_dmms` VALUES ('CKTZYS', '3', '信用风险调整值', '存款调整因素', '3');
INSERT INTO `csb_dmms` VALUES ('CKTZYS', '4', '政策性调整值', '存款调整因素', '4');
INSERT INTO `csb_dmms` VALUES ('DKTZYS', '9', '其它调整值', '贷款调整因素', '4');
INSERT INTO `csb_dmms` VALUES ('QLFS', '1', '条件清理', '数据清理方式', '1');
INSERT INTO `csb_dmms` VALUES ('QLFS', '2', '整表移除', '数据清理方式', '3');
INSERT INTO `csb_dmms` VALUES ('CKTZYS', '9', '其它调整值', '存款调整因素', '5');
INSERT INTO `csb_dmms` VALUES ('QLPL', '1', '月', '数据清理频率', '1');
INSERT INTO `csb_dmms` VALUES ('QLPL', '2', '年', '数据清理频率', '2');
INSERT INTO `csb_dmms` VALUES ('QLFS', '0', '自定义', '数据清理方式', '2');
INSERT INTO `csb_dmms` VALUES ('APPJGKHZBQSZB', '2', '贷款时点余额', '个人桌面-行员考核指标占比', '1');
INSERT INTO `csb_dmms` VALUES ('APPJGKHZBQSZB', '9', '存款时点余额', '个人桌面-行员考核指标占比', '2');
INSERT INTO `csb_dmms` VALUES ('APPKHZBQSZB', '41', '存款总余额', '个人桌面-机构存贷款指标趋势', '1');
INSERT INTO `csb_dmms` VALUES ('APPKHZBQSZB', '42', '贷款总余额', '个人桌面-机构存贷款指标趋势', '2');
INSERT INTO `csb_dmms` VALUES ('CDLX', '0', '根', '菜单类型', '1');
INSERT INTO `csb_dmms` VALUES ('CDLX', '1', '目录', '菜单类型', '2');
INSERT INTO `csb_dmms` VALUES ('CDLX', '2', '连接', '菜单类型', '3');
INSERT INTO `csb_dmms` VALUES ('CKHYBZ', 'N', '不能查看机构内的其他行员', '查看行员标志', '1');
INSERT INTO `csb_dmms` VALUES ('CKHYBZ', 'Y', '可查看机构内的其他行员', '查看行员标志', '2');
INSERT INTO `csb_dmms` VALUES ('CKZHJSZB', '1000', '存款总余额指标代号', '支持帐户基数设置的根指标', '1');
INSERT INTO `csb_dmms` VALUES ('CKZHSX', '1', '一般户', '存款帐户属性', '1');
INSERT INTO `csb_dmms` VALUES ('CKZHSX', '2', '基本户', '存款帐户属性', '2');
INSERT INTO `csb_dmms` VALUES ('CKZHSX', '3', '专用户', '存款帐户属性', '3');
INSERT INTO `csb_dmms` VALUES ('CKZHSX', '4', '临时户', '存款帐户属性', '4');
INSERT INTO `csb_dmms` VALUES ('CPLX', '1', '存款', '产品类型', '1');
INSERT INTO `csb_dmms` VALUES ('CPLX', '2', '贷款', '产品类型', '2');
INSERT INTO `csb_dmms` VALUES ('CSLX', '0', '无参数', '产品类型', '3');
INSERT INTO `csb_dmms` VALUES ('CSLX', '1', '参数代号为科目号', '产品类型', '4');
INSERT INTO `csb_dmms` VALUES ('CSLX', '2', '参数代号为指标代号', '产品类型', '5');
INSERT INTO `csb_dmms` VALUES ('CSLX', '3', '其它，参数代号为其他输入值', '产品类型', '6');
INSERT INTO `csb_dmms` VALUES ('CXFS', '1', '按操作员', '查询方式', '1');
INSERT INTO `csb_dmms` VALUES ('CXFS', '2', '按行员', '查询方式', '2');
INSERT INTO `csb_dmms` VALUES ('DJPDFF', 'ZHPD', '综合评定', '等级评定方法', '1');
INSERT INTO `csb_dmms` VALUES ('DJPDFF', 'MCFB', '名次分布', '等级评定方法', '2');
INSERT INTO `csb_dmms` VALUES ('DJPDFF', 'QZFB', '强制分布', '等级评定方法', '3');
INSERT INTO `csb_dmms` VALUES ('DJPDLB', '1', '行员等级评定', '等级评定', '1');

-- ----------------------------
-- Table structure for `grzm_app`
-- ----------------------------
DROP TABLE IF EXISTS `grzm_app`;
CREATE TABLE `grzm_app` (
  `APPID` varchar(50) NOT NULL,
  `APPURL` varchar(500) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `IMGURL` varchar(100) DEFAULT NULL,
  `WIDTH` int(11) DEFAULT NULL,
  `HEIGHT` int(11) DEFAULT NULL,
  `MS` varchar(200) DEFAULT NULL,
  `SFXS` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grzm_app
-- ----------------------------

-- ----------------------------
-- Table structure for `grzm_apppx`
-- ----------------------------
DROP TABLE IF EXISTS `grzm_apppx`;
CREATE TABLE `grzm_apppx` (
  `KHDXDH` int(11) NOT NULL,
  `APPID` varchar(50) NOT NULL,
  `JSDH` int(11) NOT NULL,
  `PXBZ` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grzm_apppx
-- ----------------------------

-- ----------------------------
-- Table structure for `grzm_jsapp`
-- ----------------------------
DROP TABLE IF EXISTS `grzm_jsapp`;
CREATE TABLE `grzm_jsapp` (
  `JSDH` int(11) NOT NULL,
  `APPID` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grzm_jsapp
-- ----------------------------

-- ----------------------------
-- Table structure for `grzm_yhapp`
-- ----------------------------
DROP TABLE IF EXISTS `grzm_yhapp`;
CREATE TABLE `grzm_yhapp` (
  `KHDXDH` int(11) NOT NULL,
  `APPID` varchar(50) NOT NULL,
  `JSDH` int(11) NOT NULL,
  `PXBZ` int(11) NOT NULL,
  `SFZDZZ` int(11) DEFAULT NULL,
  `ROWIDX` int(11) DEFAULT NULL,
  `COLIDX` int(11) DEFAULT NULL,
  `ROWSPAN` int(11) DEFAULT NULL,
  `COLSPAN` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grzm_yhapp
-- ----------------------------

-- ----------------------------
-- Table structure for `khdx_hy`
-- ----------------------------
DROP TABLE IF EXISTS `khdx_hy`;
CREATE TABLE `khdx_hy` (
  `KHDXDH` int(11) NOT NULL,
  `HYDH` varchar(20) DEFAULT NULL,
  `HYMC` varchar(100) DEFAULT NULL,
  `XL` varchar(2) DEFAULT NULL,
  `LXDH` varchar(30) DEFAULT NULL,
  `SFZ` varchar(25) DEFAULT NULL,
  `YXRYBZ` varchar(1) DEFAULT NULL,
  `XNHYBZ` varchar(1) DEFAULT NULL,
  `DLMC` varchar(20) DEFAULT NULL,
  `DLMM` varchar(32) DEFAULT NULL,
  `AQJB` varchar(1) DEFAULT NULL,
  `ZXZT` varchar(1) DEFAULT NULL,
  `SCDL` varchar(1) DEFAULT NULL,
  `ZPXX` varchar(100) DEFAULT NULL,
  `CZYBH` varchar(12) DEFAULT NULL,
  `ZXRQ` int(11) DEFAULT NULL,
  `CSRQ` int(11) DEFAULT NULL,
  `GZRQ` int(11) DEFAULT NULL,
  `RHRQ` int(11) DEFAULT NULL,
  `FGBZ` varchar(1) DEFAULT NULL,
  `PXBZ` int(11) DEFAULT NULL,
  `FHDH` varchar(30) DEFAULT NULL,
  KEY `index_dlmc` (`DLMC`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of khdx_hy
-- ----------------------------
INSERT INTO `khdx_hy` VALUES ('0', 'admin', 'admin', '01', '13000000000', '888888888888888888', 'N', 'N', 'admin', '96E79218965EB72C92A549DD5A330112', '0', 'N', '0', '0', 'admin', '29991231', '19000101', '19000101', '20090101', '1', '0', 'dn0');

-- ----------------------------
-- Table structure for `khdx_jg`
-- ----------------------------
DROP TABLE IF EXISTS `khdx_jg`;
CREATE TABLE `khdx_jg` (
  `KHDXDH` int(11) NOT NULL,
  `JGDH` varchar(15) DEFAULT NULL,
  `JGMC` varchar(100) DEFAULT NULL,
  `JYJGBZ` varchar(1) DEFAULT NULL,
  `PXBZ` int(11) DEFAULT NULL,
  `ZXZT` varchar(1) NOT NULL,
  `ZXRQ` int(11) DEFAULT NULL,
  `FHDH` varchar(15) DEFAULT NULL,
  `FHBZ` varchar(2) DEFAULT NULL,
  `JGDJ` varchar(2) DEFAULT NULL,
  `KYRQ` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of khdx_jg
-- ----------------------------
INSERT INTO `khdx_jg` VALUES ('44', '2320381991', '电子银行部', 'N', '1046', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('61', '2320381998', '授权中心', 'N', '1063', 'N', '29991231', '1', '0', '3', null);
INSERT INTO `khdx_jg` VALUES ('49', '2320381999', '信息科技部', 'N', '1051', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('4', '2320381003', '瓦窑支行', 'Y', '1004', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('5', '2320381004', '港头支行', 'Y', '1005', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('6', '2320381005', '合沟支行', 'Y', '1006', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('7', '2320381006', '草桥支行', 'Y', '1007', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('8', '2320381007', '窑湾支行', 'Y', '1008', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('9', '2320381008', '王楼支行', 'Y', '1009', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('10', '2320381009', '堰头支行', 'Y', '1010', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('11', '2320381010', '棋盘支行', 'Y', '1011', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('12', '2320381011', '城岗支行', 'Y', '1012', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('13', '2320381012', '马陵山支行', 'Y', '1013', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('14', '2320381013', '邵店支行', 'Y', '1014', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('15', '2320381014', '新店支行', 'Y', '1015', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('16', '2320381015', '小湖支行', 'Y', '1016', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('17', '2320381016', '金昌支行', 'Y', '1017', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('18', '2320381017', '时集支行', 'Y', '1018', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('19', '2320381018', '踢球山支行', 'Y', '1019', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('20', '2320381019', '高流支行', 'Y', '1020', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('21', '2320381020', '双塘支行', 'Y', '1021', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('22', '2320381021', '阿湖支行', 'Y', '1022', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('23', '2320381022', '黑埠支行', 'Y', '1023', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('24', '2320381023', '城中支行', 'Y', '1024', 'N', '20160930', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('25', '2320381024', '沭东支行', 'Y', '1025', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('26', '2320381025', '唐店支行', 'Y', '1026', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('27', '2320381026', '北沟支行', 'Y', '1027', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('28', '2320381027', '墨河支行', 'Y', '1028', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('29', '2320381028', '新安镇支行', 'Y', '1029', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('30', '2320381029', '城关支行', 'Y', '1030', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('31', '2320381030', '城北支行', 'Y', '1031', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('32', '2320381031', '沭西支行', 'Y', '1032', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('33', '2320381032', '徐海支行', 'Y', '1035', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('34', '2320381033', '城南支行', 'Y', '1036', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('35', '2320381034', '城东支行', 'Y', '1037', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('36', '2320381035', '新兴支行', 'Y', '1038', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('37', '2320381036', '钟吾支行', 'Y', '1039', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('38', '2320381037', '广场支行', 'Y', '1040', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('39', '2320381038', '南京路支行', 'Y', '1041', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('40', '2320381039', '新华路支行', 'Y', '1042', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('42', '2320381040', '古镇分理处', 'Y', '1044', 'N', '20160930', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('41', '2320381900', '清算中心', 'N', '1043', 'N', '29991231', '1', '0', '3', null);
INSERT INTO `khdx_jg` VALUES ('43', '2320381901', '行长办公室', 'N', '1045', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('45', '2320381903', '风险管理部', 'N', '1047', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('46', '2320381904', '合规管理部', 'N', '1048', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('47', '2320381905', '计划财务部', 'N', '1049', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('48', '2320381906', '安全保卫部', 'N', '1050', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('50', '2320381908', '业务发展部', 'N', '1052', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('51', '2320381909', '人力资源部', 'N', '1053', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('52', '2320381910', '审计稽核部', 'N', '1054', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('53', '2320381911', '信贷管理部', 'N', '1055', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('55', '2320381913', '资产管理部', 'N', '1057', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('56', '2320381914', '办公室', 'N', '1058', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('57', '2320381915', '董事会办公室', 'N', '1059', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('58', '2320381916', '授信管理部', 'N', '1060', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('59', '2320381917', '金融市场部', 'N', '1061', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('60', '2320381918', '监事会办公室', 'N', '1062', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('54', '2320381931', '运营管理部', 'N', '1056', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('1', '2320381000', '江苏新沂农村商业银行', 'N', '1001', 'N', '29991231', '1', '0', '1', null);
INSERT INTO `khdx_jg` VALUES ('2', '2320381001', '营业部', 'Y', '1002', 'N', '29991231', '1', '0', '2', null);
INSERT INTO `khdx_jg` VALUES ('3', '2320381002', '新立支行', 'Y', '1003', 'N', '29991231', '1', '0', '2', null);

-- ----------------------------
-- Table structure for `khdx_jgcc`
-- ----------------------------
DROP TABLE IF EXISTS `khdx_jgcc`;
CREATE TABLE `khdx_jgcc` (
  `KHDXDH` int(11) NOT NULL,
  `QSRQ` int(11) NOT NULL,
  `JSRQ` int(11) DEFAULT NULL,
  `SJKHDXDH` int(11) DEFAULT NULL,
  `JGDH` varchar(15) NOT NULL,
  `SJJGDH` varchar(10) DEFAULT NULL,
  `JGJB` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of khdx_jgcc
-- ----------------------------
INSERT INTO `khdx_jgcc` VALUES ('1', '19000101', '29991231', '0', '2320381000', '0', '0');
INSERT INTO `khdx_jgcc` VALUES ('2', '19000101', '29991231', '1', '2320381001', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('3', '19000101', '29991231', '1', '2320381002', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('4', '19000101', '29991231', '1', '2320381003', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('5', '19000101', '29991231', '1', '2320381004', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('6', '19000101', '29991231', '1', '2320381005', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('7', '19000101', '29991231', '1', '2320381006', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('8', '19000101', '29991231', '1', '2320381007', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('9', '19000101', '29991231', '1', '2320381008', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('10', '19000101', '29991231', '1', '2320381009', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('11', '19000101', '29991231', '1', '2320381010', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('12', '19000101', '29991231', '1', '2320381011', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('13', '19000101', '29991231', '1', '2320381012', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('14', '19000101', '29991231', '1', '2320381013', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('15', '19000101', '29991231', '1', '2320381014', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('16', '19000101', '29991231', '1', '2320381015', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('17', '19000101', '29991231', '1', '2320381016', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('18', '19000101', '29991231', '1', '2320381017', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('19', '19000101', '29991231', '1', '2320381018', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('20', '19000101', '29991231', '1', '2320381019', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('21', '19000101', '29991231', '1', '2320381020', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('22', '19000101', '29991231', '1', '2320381021', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('23', '19000101', '29991231', '1', '2320381022', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('24', '19000101', '20160930', '1', '2320381023', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('25', '19000101', '29991231', '1', '2320381024', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('26', '19000101', '29991231', '1', '2320381025', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('27', '19000101', '29991231', '1', '2320381026', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('28', '19000101', '29991231', '1', '2320381027', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('29', '19000101', '29991231', '1', '2320381028', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('30', '19000101', '29991231', '1', '2320381029', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('31', '19000101', '29991231', '1', '2320381030', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('32', '19000101', '29991231', '1', '2320381031', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('33', '19000101', '29991231', '1', '2320381032', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('34', '19000101', '29991231', '1', '2320381033', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('35', '19000101', '29991231', '1', '2320381034', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('36', '19000101', '29991231', '1', '2320381035', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('37', '19000101', '29991231', '1', '2320381036', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('38', '19000101', '29991231', '1', '2320381037', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('39', '19000101', '29991231', '1', '2320381038', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('40', '19000101', '29991231', '1', '2320381039', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('41', '19000101', '29991231', '54', '2320381900', '2320381931', '2');
INSERT INTO `khdx_jgcc` VALUES ('42', '19000101', '20160930', '1', '2320381040', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('43', '19000101', '29991231', '1', '2320381901', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('44', '19000101', '29991231', '1', '2320381991', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('45', '19000101', '29991231', '1', '2320381903', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('46', '19000101', '29991231', '1', '2320381904', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('47', '19000101', '29991231', '1', '2320381905', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('48', '19000101', '29991231', '1', '2320381906', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('49', '19000101', '29991231', '1', '2320381999', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('50', '19000101', '29991231', '1', '2320381908', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('51', '19000101', '29991231', '1', '2320381909', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('52', '19000101', '29991231', '1', '2320381910', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('53', '19000101', '29991231', '1', '2320381911', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('54', '19000101', '29991231', '1', '2320381931', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('55', '19000101', '29991231', '1', '2320381913', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('56', '19000101', '29991231', '1', '2320381914', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('57', '19000101', '29991231', '1', '2320381915', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('58', '19000101', '29991231', '1', '2320381916', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('59', '19000101', '29991231', '1', '2320381917', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('60', '19000101', '29991231', '1', '2320381918', '2320381000', '1');
INSERT INTO `khdx_jgcc` VALUES ('61', '19000101', '29991231', '54', '2320381998', '2320381931', '2');

-- ----------------------------
-- Table structure for `khdx_jgcy`
-- ----------------------------
DROP TABLE IF EXISTS `khdx_jgcy`;
CREATE TABLE `khdx_jgcy` (
  `KHDXDH` int(11) NOT NULL,
  `JGKHDXDH` int(11) NOT NULL,
  `QSRQ` int(11) NOT NULL,
  `HYDH` varchar(20) NOT NULL,
  `JGDH` varchar(15) NOT NULL,
  `JSRQ` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of khdx_jgcy
-- ----------------------------
INSERT INTO `khdx_jgcy` VALUES ('574', '2', '20111201', '203600312', '2320381001', '20161211');
INSERT INTO `khdx_jgcy` VALUES ('575', '5', '20111001', '203600689', '2320381004', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('574', '2', '20161212', '203600312', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('288', '6', '20161212', '203600464', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('455', '9', '19000102', '203600589', '2320381008', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('578', '11', '19000101', '203600693', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('359', '11', '19000101', '203600644', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('220', '12', '19000101', '203600672', '2320381011', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('472', '17', '19000102', '203600276', '2320381016', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('579', '21', '19000101', '203600686', '2320381020', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('580', '31', '19000101', '203600682', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('581', '31', '19000101', '203600717', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('379', '34', '19000101', '203600194', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('238', '34', '19000101', '203600677', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('582', '36', '19000101', '203600538', '2320381035', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('583', '37', '19000101', '203600684', '2320381036', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('266', '38', '19000101', '203600078', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('280', '38', '19000101', '203500361', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('265', '38', '19000101', '203600163', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('282', '38', '19000101', '203600275', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('267', '38', '19000101', '203600340', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('281', '38', '19000101', '203600192', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('278', '38', '19000101', '203600455', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('584', '38', '19000101', '203600708', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('585', '38', '19000101', '203600692', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('167', '22', '19000101', '203600150', '2320381021', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('169', '22', '19000101', '203600084', '2320381021', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('586', '18', '19000101', '203600549', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('587', '26', '19000101', '203600687', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('434', '19', '19000101', '203100640', '2320381018', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('466', '16', '19000101', '203600670', '2320381015', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('517', '19', '19000101', '203600299', '2320381018', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('576', '61', '19000101', 'XN2320381998', '2320381998', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('55', '55', '19000101', 'XN2320381913', '2320381913', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('56', '56', '19000101', 'XN2320381914', '2320381914', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('57', '57', '19000101', 'XN2320381915', '2320381915', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('58', '58', '19000101', 'XN2320381916', '2320381916', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('59', '59', '19000101', 'XN2320381917', '2320381917', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('60', '60', '19000101', 'XN2320381918', '2320381918', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('61', '57', '19000101', '203600469', '2320381915', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('62', '57', '19000101', '203600474', '2320381915', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('63', '60', '19000101', '203600397', '2320381918', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('64', '60', '19000101', '203600403', '2320381918', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('65', '60', '19000101', '203600391', '2320381918', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('66', '48', '19000101', '203600351', '2320381906', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('67', '48', '19000101', '203600428', '2320381906', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('68', '48', '19000101', '203600405', '2320381906', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('69', '48', '19000101', '203600232', '2320381906', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('70', '48', '19000101', '203600088', '2320381906', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('71', '48', '19000101', '203600037', '2320381906', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('72', '56', '19000101', '203600379', '2320381914', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('73', '56', '19000101', '203600177', '2320381914', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('74', '56', '19000101', '203700243', '2320381914', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('75', '56', '19000101', '203600423', '2320381914', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('76', '56', '19000101', '203600004', '2320381914', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('77', '56', '19000101', '203600477', '2320381914', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('78', '51', '19000101', '203600420', '2320381909', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('79', '51', '19000101', '203600395', '2320381909', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('80', '51', '19000101', '203600553', '2320381909', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('81', '44', '19000101', '203600260', '2320381991', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('82', '44', '19000101', '203600071', '2320381991', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('83', '44', '19000101', '203600360', '2320381991', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('84', '44', '19000101', '203600326', '2320381991', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('85', '45', '19000101', '203600355', '2320381903', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('86', '45', '19000101', '203600363', '2320381903', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('87', '45', '19000101', '203600556', '2320381903', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('88', '46', '19000101', '203600384', '2320381904', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('89', '46', '19000101', '203600309', '2320381904', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('90', '46', '19000101', '203600304', '2320381904', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('91', '47', '19000101', '203600416', '2320381905', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('92', '47', '19000101', '203600424', '2320381905', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('93', '47', '19000101', '203600470', '2320381905', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('94', '47', '19000101', '203600223', '2320381905', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('95', '59', '19000101', '203600454', '2320381917', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('96', '59', '19000101', '203600438', '2320381917', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('97', '59', '19000101', '203600383', '2320381917', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('98', '59', '19000101', '203600413', '2320381917', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('99', '52', '19000101', '203600468', '2320381910', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('100', '52', '19000101', '203600271', '2320381910', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('101', '52', '19000101', '203600387', '2320381910', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('102', '52', '19000101', '207500628', '2320381910', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('103', '52', '19000101', '203600440', '2320381910', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('104', '52', '19000101', '203600388', '2320381910', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('105', '52', '19000101', '203600354', '2320381910', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('106', '58', '19000101', '203600144', '2320381916', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('107', '58', '19000101', '203600386', '2320381916', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('108', '58', '19000101', '203600139', '2320381916', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('109', '58', '19000101', '203600269', '2320381916', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('110', '53', '19000101', '203600298', '2320381911', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('111', '53', '19000101', '203600472', '2320381911', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('112', '53', '19000101', '203600070', '2320381911', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('113', '53', '19000101', '203600352', '2320381911', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('114', '53', '19000101', '203600500', '2320381911', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('115', '53', '19000101', '203600447', '2320381911', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('116', '53', '19000101', '203600329', '2320381911', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('117', '53', '19000101', '203600094', '2320381911', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('118', '53', '19000101', '203600618', '2320381911', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('119', '49', '19000101', '203600358', '2320381999', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('120', '49', '19000101', '203600357', '2320381999', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('121', '49', '19000101', '203600594', '2320381999', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('122', '49', '19000101', '203600359', '2320381999', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('123', '49', '19000101', '203600627', '2320381999', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('124', '50', '19000101', '203600279', '2320381908', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('125', '50', '19000101', '203600034', '2320381908', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('126', '50', '19000101', '203600303', '2320381908', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('127', '50', '19000101', '203600465', '2320381908', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('128', '50', '19000101', '203600142', '2320381908', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('129', '50', '19000101', '203600480', '2320381908', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('130', '50', '19000101', '203600600', '2320381908', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('131', '54', '19000101', '203600136', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('132', '54', '19000101', '203600462', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('133', '54', '19000101', '203600479', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('134', '54', '19000101', '203600247', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('135', '54', '19000101', '203600100', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('136', '54', '19000101', '203600404', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('137', '54', '19000101', '203600002', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('138', '54', '19000101', '203600056', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('139', '54', '19000101', '203600427', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('140', '54', '19000101', '203600418', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('141', '54', '19000101', '203600419', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('142', '54', '19000101', '203600431', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('143', '54', '19000101', '203600268', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('144', '54', '19000101', '203600296', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('145', '54', '19000101', '203600381', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('146', '54', '19000101', '203600029', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('147', '54', '19000101', '203600244', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('148', '54', '19000101', '203600096', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('149', '54', '19000101', '203600300', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('150', '54', '19000101', '203600195', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('151', '54', '19000101', '203600027', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('152', '54', '19000101', '203600332', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('153', '54', '19000101', '203600164', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('154', '54', '19000101', '203600243', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('0', '1', '19000101', 'xinyi', '2320381000', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('1', '1', '19000101', 'XN2320381000', '2320381000', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('2', '2', '19000101', 'XN2320381001', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('3', '3', '19000101', 'XN2320381002', '2320381002', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('4', '4', '19000101', 'XN2320381003', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('5', '5', '19000101', 'XN2320381004', '2320381004', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('6', '6', '19000101', 'XN2320381005', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('7', '7', '19000101', 'XN2320381006', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('8', '8', '19000101', 'XN2320381007', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('9', '9', '19000101', 'XN2320381008', '2320381008', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('10', '10', '19000101', 'XN2320381009', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('11', '11', '19000101', 'XN2320381010', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('12', '12', '19000101', 'XN2320381011', '2320381011', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('13', '13', '19000101', 'XN2320381012', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('14', '14', '19000101', 'XN2320381013', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('15', '15', '19000101', 'XN2320381014', '2320381014', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('16', '16', '19000101', 'XN2320381015', '2320381015', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('17', '17', '19000101', 'XN2320381016', '2320381016', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('18', '18', '19000101', 'XN2320381017', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('19', '19', '19000101', 'XN2320381018', '2320381018', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('20', '20', '19000101', 'XN2320381019', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('21', '21', '19000101', 'XN2320381020', '2320381020', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('22', '22', '19000101', 'XN2320381021', '2320381021', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('23', '23', '19000101', 'XN2320381022', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('24', '24', '19000101', 'XN2320381023', '2320381023', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('25', '25', '19000101', 'XN2320381024', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('26', '26', '19000101', 'XN2320381025', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('27', '27', '19000101', 'XN2320381026', '2320381026', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('28', '28', '19000101', 'XN2320381027', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('29', '29', '19000101', 'XN2320381028', '2320381028', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('30', '30', '19000101', 'XN2320381029', '2320381029', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('31', '31', '19000101', 'XN2320381030', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('32', '32', '19000101', 'XN2320381031', '2320381031', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('33', '33', '19000101', 'XN2320381032', '2320381032', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('34', '34', '19000101', 'XN2320381033', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('35', '35', '19000101', 'XN2320381034', '2320381034', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('36', '36', '19000101', 'XN2320381035', '2320381035', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('37', '37', '19000101', 'XN2320381036', '2320381036', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('38', '38', '19000101', 'XN2320381037', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('39', '39', '19000101', 'XN2320381038', '2320381038', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('40', '40', '19000101', 'XN2320381039', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('41', '41', '19000101', 'XN2320381900', '2320381900', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('42', '42', '19000101', 'XN2320381040', '2320381040', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('43', '43', '19000101', 'XN2320381901', '2320381901', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('44', '44', '19000101', 'XN2320381991', '2320381991', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('45', '45', '19000101', 'XN2320381903', '2320381903', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('46', '46', '19000101', 'XN2320381904', '2320381904', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('47', '47', '19000101', 'XN2320381905', '2320381905', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('48', '48', '19000101', 'XN2320381906', '2320381906', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('49', '49', '19000101', 'XN2320381999', '2320381999', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('50', '50', '19000101', 'XN2320381908', '2320381908', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('51', '51', '19000101', 'XN2320381909', '2320381909', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('52', '52', '19000101', 'XN2320381910', '2320381910', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('53', '53', '19000101', 'XN2320381911', '2320381911', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('54', '54', '19000101', 'XN2320381931', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('227', '34', '19000101', '203600475', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('228', '34', '19000101', '203600261', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('229', '34', '19000101', '203600295', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('230', '34', '19000101', '203600220', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('231', '34', '19000101', '203300520', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('232', '34', '19000101', '203600053', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('233', '34', '19000101', '203600382', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('234', '34', '19000101', '203600456', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('235', '34', '19000101', '203600441', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('236', '34', '19000101', '203600335', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('237', '34', '19000101', '203600294', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('239', '24', '19000101', '203600333', '2320381023', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('240', '24', '19000101', '203600364', '2320381023', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('241', '5', '19000101', '203600134', '2320381004', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('242', '5', '19000101', '203600169', '2320381004', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('243', '5', '19000101', '203600068', '2320381004', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('244', '5', '19000101', '203600488', '2320381004', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('245', '5', '19000101', '203600513', '2320381004', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('246', '5', '19000101', '203600566', '2320381004', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('247', '5', '19000101', '203600120', '2320381004', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('248', '5', '19000101', '203600663', '2320381004', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('249', '5', '19000101', '203600696', '2320381004', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('250', '5', '19000101', '203500555', '2320381004', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('251', '20', '19000101', '203600214', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('252', '20', '19000101', '203600202', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('253', '20', '19000101', '203600080', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('254', '20', '19000101', '203600082', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('255', '20', '19000101', '203600074', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('256', '20', '19000101', '203600634', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('257', '20', '19000101', '203600149', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('258', '20', '19000101', '203600095', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('259', '20', '19000101', '203600219', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('260', '20', '19000101', '203600668', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('261', '20', '19000101', '203600703', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('262', '20', '19000101', '203600667', '2320381019', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('263', '38', '19000101', '203600102', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('264', '38', '19000101', '203600285', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('268', '38', '19000101', '203600417', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('269', '38', '19000101', '203600251', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('270', '38', '19000101', '203600272', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('271', '38', '19000101', '203600277', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('272', '38', '19000101', '203600321', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('273', '38', '19000101', '203600098', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('274', '38', '19000101', '203600481', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('275', '38', '19000101', '203600331', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('276', '38', '19000101', '203600209', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('277', '38', '19000101', '203600466', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('279', '38', '19000101', '203600676', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('283', '38', '19000101', '203600478', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('284', '38', '19000101', '203600148', '2320381037', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('285', '6', '19000101', '203600449', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('286', '6', '19000101', '203600018', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('287', '6', '19000101', '203600161', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('288', '6', '19000101', '203600464', '2320381005', '20161211');
INSERT INTO `khdx_jgcy` VALUES ('289', '6', '19000101', '203600158', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('290', '6', '19000101', '203600159', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('291', '6', '19000101', '203600225', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('292', '6', '19000101', '203600228', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('293', '6', '19000101', '203600140', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('294', '6', '19000101', '203600389', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('295', '6', '19000101', '203600633', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('296', '6', '19000101', '203600700', '2320381005', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('297', '23', '19000101', '203600025', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('298', '23', '19000101', '203600399', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('299', '23', '19000101', '203600021', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('300', '23', '19000101', '203600450', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('301', '23', '19000101', '203600145', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('302', '23', '19000101', '203600557', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('303', '23', '19000101', '203600130', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('304', '23', '19000101', '203600147', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('305', '23', '19000101', '203600301', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('306', '23', '19000101', '203600510', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('307', '23', '19000101', '203600664', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('308', '23', '19000101', '203600660', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('309', '23', '19000101', '203400518', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('310', '23', '19000101', '203600709', '2320381022', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('311', '17', '19000101', '203600089', '2320381016', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('312', '17', '19000101', '203600446', '2320381016', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('155', '54', '19000101', '203600471', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('156', '54', '19000101', '203600317', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('157', '54', '19000101', '203600318', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('158', '54', '19000101', '203600152', '2320381931', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('159', '55', '19000101', '203600162', '2320381913', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('160', '55', '19000101', '203600123', '2320381913', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('161', '55', '19000101', '203600231', '2320381913', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('162', '55', '19000101', '203600337', '2320381913', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('163', '55', '19000101', '203600311', '2320381913', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('164', '22', '19000101', '203600125', '2320381021', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('165', '22', '19000101', '203600367', '2320381021', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('166', '22', '19000101', '203600127', '2320381021', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('168', '22', '19000101', '203600151', '2320381021', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('170', '22', '19000101', '203600650', '2320381021', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('171', '22', '19000101', '203600509', '2320381021', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('172', '22', '19000101', '203600568', '2320381021', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('173', '22', '19000101', '203600671', '2320381021', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('174', '27', '19000101', '203600173', '2320381026', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('175', '27', '19000101', '203600422', '2320381026', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('176', '27', '19000101', '203600116', '2320381026', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('177', '27', '19000101', '203600006', '2320381026', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('178', '27', '19000101', '203600011', '2320381026', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('179', '27', '19000101', '203600010', '2320381026', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('180', '27', '19000101', '203600007', '2320381026', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('181', '27', '19000101', '203600622', '2320381026', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('182', '27', '19000101', '203600637', '2320381026', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('183', '27', '19000101', '203600656', '2320381026', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('184', '7', '19000101', '203600044', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('185', '7', '19000101', '203600216', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('186', '7', '19000101', '203600485', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('187', '7', '19000101', '203600448', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('188', '7', '19000101', '203600015', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('189', '7', '19000101', '203600017', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('190', '7', '19000101', '203600461', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('191', '7', '19000101', '203600370', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('192', '7', '19000101', '203600208', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('193', '7', '19000101', '203600657', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('194', '7', '19000101', '203600710', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('195', '7', '19000101', '203600705', '2320381006', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('196', '31', '19000101', '203600196', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('197', '31', '19000101', '203600036', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('198', '31', '19000101', '203600197', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('199', '31', '19000101', '203600249', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('200', '31', '19000101', '203600371', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('201', '31', '19000101', '203600201', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('202', '31', '19000101', '203600286', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('203', '31', '19000101', '203600028', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('204', '31', '19000101', '203600492', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('205', '31', '19000101', '203600712', '2320381030', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('206', '35', '19000101', '203600184', '2320381034', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('207', '35', '19000101', '203600348', '2320381034', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('208', '35', '19000101', '203600347', '2320381034', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('209', '35', '19000101', '203600362', '2320381034', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('210', '12', '19000101', '203600051', '2320381011', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('211', '12', '19000101', '203600077', '2320381011', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('212', '12', '19000101', '203600393', '2320381011', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('213', '12', '19000101', '203600047', '2320381011', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('214', '12', '19000101', '203600154', '2320381011', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('215', '12', '19000101', '203600057', '2320381011', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('216', '12', '19000101', '203600283', '2320381011', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('217', '12', '19000101', '203600576', '2320381011', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('218', '12', '19000101', '203600075', '2320381011', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('219', '12', '19000101', '203600054', '2320381011', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('221', '30', '19000101', '203600368', '2320381029', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('222', '30', '19000101', '203600415', '2320381029', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('223', '30', '19000101', '203600128', '2320381029', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('224', '30', '19000101', '203600302', '2320381029', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('225', '30', '19000101', '203600436', '2320381029', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('226', '34', '19000101', '203600639', '2320381033', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('401', '32', '19000101', '203600099', '2320381031', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('402', '32', '19000101', '203600009', '2320381031', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('403', '32', '19000101', '203600505', '2320381031', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('404', '21', '19000101', '203600043', '2320381020', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('405', '21', '19000101', '203600463', '2320381020', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('406', '21', '19000101', '203600458', '2320381020', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('407', '21', '19000101', '203600369', '2320381020', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('408', '21', '19000101', '203600174', '2320381020', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('409', '21', '19000101', '203600073', '2320381020', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('410', '21', '19000101', '203600072', '2320381020', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('411', '21', '19000101', '203600085', '2320381020', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('412', '21', '19000101', '203600695', '2320381020', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('413', '26', '19000101', '203600079', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('414', '26', '19000101', '203600042', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('415', '26', '19000101', '203600104', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('416', '26', '19000101', '203600207', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('417', '26', '19000101', '203600377', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('418', '26', '19000101', '203600372', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('419', '26', '19000101', '203600512', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('420', '26', '19000101', '203600167', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('421', '26', '19000101', '203600124', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('422', '26', '19000101', '203600227', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('423', '26', '19000101', '203600211', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('424', '26', '19000101', '203600255', '2320381025', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('425', '19', '19000101', '203600297', '2320381018', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('426', '19', '19000101', '203600146', '2320381018', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('427', '19', '19000101', '203600156', '2320381018', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('428', '19', '19000101', '203600062', '2320381018', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('429', '19', '19000101', '203600617', '2320381018', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('430', '19', '19000101', '203600215', '2320381018', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('431', '19', '19000101', '203600155', '2320381018', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('432', '19', '19000101', '203600160', '2320381018', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('433', '19', '19000101', '203600654', '2320381018', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('435', '4', '19000101', '203600642', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('436', '4', '19000101', '203600282', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('437', '4', '19000101', '203600012', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('438', '4', '19000101', '203600013', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('439', '4', '19000101', '203600063', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('440', '4', '19000101', '203600226', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('441', '4', '19000101', '203600425', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('442', '4', '19000101', '203600101', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('443', '4', '19000101', '203600341', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('444', '4', '19000101', '203600224', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('445', '4', '19000101', '203600014', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('446', '4', '19000101', '203600138', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('447', '4', '19000101', '203600259', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('448', '4', '19000101', '203600229', '2320381003', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('449', '9', '19000101', '203600190', '2320381008', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('450', '9', '19000101', '203600287', '2320381008', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('451', '9', '19000101', '203600278', '2320381008', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('452', '9', '19000101', '203600143', '2320381008', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('453', '9', '19000101', '203600289', '2320381008', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('454', '9', '19000101', '203600432', '2320381008', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('455', '9', '19000101', '203600589', '2320381008', '19000101');
INSERT INTO `khdx_jgcy` VALUES ('456', '9', '19000101', '203600648', '2320381008', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('457', '9', '19000101', '203600697', '2320381008', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('458', '9', '19000101', '203600702', '2320381008', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('459', '16', '19000101', '203600069', '2320381015', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('460', '16', '19000101', '203600064', '2320381015', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('461', '16', '19000101', '203600049', '2320381015', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('462', '16', '19000101', '203600040', '2320381015', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('463', '16', '19000101', '203600245', '2320381015', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('464', '16', '19000101', '203600563', '2320381015', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('465', '16', '19000101', '203600236', '2320381015', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('467', '16', '19000101', '203600707', '2320381015', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('468', '29', '19000101', '203600262', '2320381028', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('469', '29', '19000101', '203600327', '2320381028', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('470', '29', '19000101', '203600187', '2320381028', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('313', '17', '19000101', '203600153', '2320381016', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('314', '17', '19000101', '203600483', '2320381016', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('315', '17', '19000101', '203600267', '2320381016', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('316', '13', '19000101', '203600328', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('317', '13', '19000101', '203600061', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('318', '13', '19000101', '203600112', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('319', '13', '19000101', '203600041', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('320', '13', '19000101', '203600030', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('321', '13', '19000101', '203600291', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('322', '13', '19000101', '203600107', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('323', '13', '19000101', '203600105', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('324', '13', '19000101', '203600106', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('325', '13', '19000101', '203600048', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('326', '13', '19000101', '203600210', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('327', '13', '19000101', '203600628', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('328', '13', '19000101', '203600632', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('329', '13', '19000101', '203600248', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('330', '13', '19000101', '203600665', '2320381012', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('331', '28', '19000101', '203600310', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('332', '28', '19000101', '203600412', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('333', '28', '19000101', '203600008', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('334', '28', '19000101', '203600022', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('335', '28', '19000101', '203600016', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('336', '28', '19000101', '203600157', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('337', '28', '19000101', '203600117', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('338', '28', '19000101', '203600121', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('339', '28', '19000101', '203600520', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('340', '28', '19000101', '203600185', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('341', '28', '19000101', '203600486', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('342', '28', '19000101', '203600511', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('343', '28', '19000101', '203600346', '2320381027', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('344', '39', '19000101', '203600242', '2320381038', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('345', '39', '19000101', '203600559', '2320381038', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('346', '39', '19000101', '203600345', '2320381038', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('347', '39', '19000101', '203600090', '2320381038', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('348', '11', '19000101', '203600433', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('349', '11', '19000101', '203600067', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('350', '11', '19000101', '203600131', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('351', '11', '19000101', '203600050', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('352', '11', '19000101', '203600217', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('353', '11', '19000101', '203600631', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('354', '11', '19000101', '203600640', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('355', '11', '19000101', '203600045', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('356', '11', '19000101', '203600645', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('357', '11', '19000101', '203600115', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('358', '11', '19000101', '203600320', '2320381010', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('360', '14', '19000101', '203600168', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('361', '14', '19000101', '203600240', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('362', '14', '19000101', '203600493', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('363', '14', '19000101', '203600166', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('364', '14', '19000101', '203600179', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('365', '14', '19000101', '203600165', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('366', '14', '19000101', '203600430', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('367', '14', '19000101', '203600212', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('368', '14', '19000101', '203600238', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('369', '14', '19000101', '203600487', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('370', '14', '19000101', '203600653', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('371', '14', '19000101', '203600699', '2320381013', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('372', '18', '19000101', '203600473', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('373', '18', '19000101', '203600203', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('374', '18', '19000101', '203600564', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('375', '18', '19000101', '203600199', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('376', '18', '19000101', '203600180', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('377', '18', '19000101', '203600181', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('378', '18', '19000101', '203600081', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('380', '18', '19000101', '203600661', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('381', '18', '19000101', '203600652', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('382', '18', '19000101', '203600698', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('383', '18', '19000101', '203600711', '2320381017', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('384', '25', '19000101', '203600597', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('385', '25', '19000101', '203600189', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('386', '25', '19000101', '203600005', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('387', '25', '19000101', '203600356', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('388', '25', '19000101', '203600191', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('389', '25', '19000101', '203600035', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('390', '25', '19000101', '203600038', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('391', '25', '19000101', '203600322', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('392', '25', '19000101', '203600339', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('393', '25', '19000101', '203600183', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('394', '25', '19000101', '203600188', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('395', '25', '19000101', '203600658', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('396', '25', '19000101', '203600452', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('397', '25', '19000101', '203600675', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('398', '25', '19000101', '203600182', '2320381024', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('399', '32', '19000101', '203600234', '2320381031', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('400', '32', '19000101', '203600132', '2320381031', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('471', '29', '19000101', '203600076', '2320381028', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('472', '29', '19000101', '203600276', '2320381028', '19000101');
INSERT INTO `khdx_jgcy` VALUES ('473', '29', '19000101', '203600662', '2320381028', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('474', '29', '19000101', '203600565', '2320381028', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('475', '15', '19000101', '203600205', '2320381014', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('476', '15', '19000101', '203600233', '2320381014', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('477', '15', '19000101', '203600103', '2320381014', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('478', '15', '19000101', '203600178', '2320381014', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('479', '15', '19000101', '203600235', '2320381014', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('480', '15', '19000101', '203600374', '2320381014', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('481', '15', '19000101', '203600641', '2320381014', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('482', '15', '19000101', '203600046', '2320381014', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('483', '15', '19000101', '203600239', '2320381014', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('484', '15', '19000101', '203600620', '2320381014', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('485', '15', '19000101', '203600646', '2320381014', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('486', '40', '19000101', '203600003', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('487', '40', '19000101', '203600172', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('488', '40', '19000101', '203600325', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('489', '40', '19000101', '203600324', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('490', '40', '19000101', '203600031', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('491', '40', '19000101', '203600250', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('492', '40', '19000101', '203600033', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('493', '40', '19000101', '203600055', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('494', '40', '19000101', '203600457', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('495', '40', '19000101', '203600092', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('496', '40', '19000101', '203600258', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('497', '40', '19000101', '203600437', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('498', '40', '19000101', '203600673', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('499', '40', '19000101', '203600674', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('500', '40', '19000101', '203600252', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('501', '40', '19000101', '203600093', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('502', '40', '19000101', '203600489', '2320381039', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('503', '3', '19000101', '203600213', '2320381002', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('504', '3', '19000101', '203600186', '2320381002', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('505', '3', '19000101', '203600193', '2320381002', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('506', '3', '19000101', '203400424', '2320381002', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('507', '3', '19000101', '203600494', '2320381002', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('508', '3', '19000101', '203600514', '2320381002', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('509', '36', '19000101', '203600414', '2320381035', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('510', '36', '19000101', '203300493', '2320381035', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('511', '36', '19000101', '203600058', '2320381035', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('512', '36', '19000101', '203600305', '2320381035', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('513', '36', '19000101', '203600237', '2320381035', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('514', '33', '19000101', '203600114', '2320381032', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('515', '33', '19000101', '203600039', '2320381032', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('516', '33', '19000101', '203600026', '2320381032', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('518', '33', '19000101', '203600659', '2320381032', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('519', '33', '19000101', '203100680', '2320381032', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('520', '10', '19000101', '203600647', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('521', '10', '19000101', '203600396', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('522', '10', '19000101', '203600635', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('523', '10', '19000101', '203600434', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('524', '10', '19000101', '203600222', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('525', '10', '19000101', '203600366', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('526', '10', '19000101', '203600175', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('527', '10', '19000101', '203600506', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('528', '10', '19000101', '203600086', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('529', '10', '19000101', '203600281', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('530', '10', '19000101', '203600649', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('531', '10', '19000101', '203600706', '2320381009', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('532', '8', '19000101', '203600394', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('533', '8', '19000101', '203600135', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('534', '8', '19000101', '203600109', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('535', '8', '19000101', '203600118', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('536', '8', '19000101', '203600290', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('537', '8', '19000101', '203600133', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('538', '8', '19000101', '203600137', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('539', '8', '19000101', '203600293', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('540', '8', '19000101', '203600625', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('541', '8', '19000101', '203600624', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('542', '8', '19000101', '203600636', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('543', '8', '19000101', '203600678', '2320381007', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('544', '2', '19000101', '203600270', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('545', '2', '19000101', '203600274', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('546', '2', '19000101', '203600503', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('547', '2', '19000101', '203600122', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('548', '2', '19000101', '203600170', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('549', '2', '19000101', '203600315', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('550', '2', '19000101', '203600460', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('551', '2', '19000101', '203600024', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('552', '2', '19000101', '203600338', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('553', '2', '19000101', '203600484', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('555', '2', '19000101', '203600353', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('556', '2', '19000101', '203600380', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('557', '2', '19000101', '203600571', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('558', '2', '19000101', '203600308', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('559', '2', '19000101', '203400426', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('560', '2', '19000101', '203600111', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('561', '2', '19000101', '203600476', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('562', '2', '19000101', '203600482', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('563', '2', '19000101', '207400235', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('564', '2', '19000101', '203600453', '2320381001', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('565', '37', '19000101', '203600001', '2320381036', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('566', '37', '19000101', '203600307', '2320381036', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('567', '37', '19000101', '203600221', '2320381036', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('568', '37', '19000101', '203600349', '2320381036', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('569', '37', '19000101', '203600595', '2320381036', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('570', '37', '19000101', '203600284', '2320381036', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('571', '37', '19000101', '203600266', '2320381036', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('572', '37', '19000101', '203600060', '2320381036', '29991231');
INSERT INTO `khdx_jgcy` VALUES ('573', '37', '19000101', '203600198', '2320381036', '29991231');

-- ----------------------------
-- Table structure for `khdx_jglb`
-- ----------------------------
DROP TABLE IF EXISTS `khdx_jglb`;
CREATE TABLE `khdx_jglb` (
  `KHDXDH` int(11) NOT NULL,
  `QSRQ` int(11) NOT NULL,
  `JSRQ` int(11) DEFAULT NULL,
  `LBDH` varchar(10) NOT NULL,
  `LBMC` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of khdx_jglb
-- ----------------------------
INSERT INTO `khdx_jglb` VALUES ('1', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('2', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('3', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('4', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('5', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('6', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('7', '20010101', '29991231', '00', '其他');
INSERT INTO `khdx_jglb` VALUES ('8', '20010101', '29991231', '00', '其他');
INSERT INTO `khdx_jglb` VALUES ('9', '20010101', '29991231', '00', '其他');
INSERT INTO `khdx_jglb` VALUES ('10', '20010101', '29991231', '00', '其他');
INSERT INTO `khdx_jglb` VALUES ('11', '20010101', '29991231', '00', '其他');
INSERT INTO `khdx_jglb` VALUES ('12', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('13', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('14', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('15', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('16', '20141228', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('17', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('18', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('19', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('20', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('21', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('22', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('23', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('24', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('25', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('26', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('27', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('28', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('29', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('30', '20141230', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('31', '20141228', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('32', '20010101', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('33', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('34', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('35', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('36', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('37', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('38', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('39', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('40', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('41', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('42', '20010101', '29991231', '03', '新开支行');
INSERT INTO `khdx_jglb` VALUES ('43', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('44', '20010101', '29991231', '03', '新开支行');
INSERT INTO `khdx_jglb` VALUES ('45', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('46', '20140225', '29991231', '02', '支行');
INSERT INTO `khdx_jglb` VALUES ('47', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('48', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('49', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('50', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('51', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('52', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('53', '20010101', '29991231', '03', '新开支行');
INSERT INTO `khdx_jglb` VALUES ('54', '20010101', '29991231', '03', '新开支行');
INSERT INTO `khdx_jglb` VALUES ('55', '20010101', '29991231', '03', '新开支行');
INSERT INTO `khdx_jglb` VALUES ('56', '20010101', '29991231', '03', '新开支行');
INSERT INTO `khdx_jglb` VALUES ('57', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('58', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('59', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('60', '20010101', '29991231', '01', '小微中心');
INSERT INTO `khdx_jglb` VALUES ('61', '20010101', '29991231', '01', '小微中心');

-- ----------------------------
-- Table structure for `mycat_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `mycat_sequence`;
CREATE TABLE `mycat_sequence` (
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '100',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of mycat_sequence
-- ----------------------------
INSERT INTO `mycat_sequence` VALUES ('GLOBAL', '100100', '100');
INSERT INTO `mycat_sequence` VALUES ('KHDX_JG', '1000247', '1');
INSERT INTO `mycat_sequence` VALUES ('T_USER', '126', '1');
INSERT INTO `mycat_sequence` VALUES ('USER', '101', '1');
INSERT INTO `mycat_sequence` VALUES ('XTB_XTCD', '674', '1');

-- ----------------------------
-- Table structure for `xtb_aqjs`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_aqjs`;
CREATE TABLE `xtb_aqjs` (
  `JSDH` int(11) NOT NULL,
  `JSMC` varchar(20) DEFAULT NULL,
  `JSLX` int(11) DEFAULT NULL,
  `SJJS` int(11) DEFAULT NULL,
  `SZFS` int(11) DEFAULT NULL,
  `CJZ` int(11) DEFAULT NULL,
  `CJSJ` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `JSCODE` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_aqjs
-- ----------------------------
INSERT INTO `xtb_aqjs` VALUES ('1', '江*新*农村商业银行', '2', '0', '1', null, '2017-11-15 10:34:41.415438', 'role_2');

-- ----------------------------
-- Table structure for `xtb_auth`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_auth`;
CREATE TABLE `xtb_auth` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FHDH` varchar(20) NOT NULL,
  `HYS` int(11) NOT NULL,
  `QSSJ` int(11) NOT NULL,
  `JZSJ` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_auth
-- ----------------------------
INSERT INTO `xtb_auth` VALUES ('3', 'dn0', '503', '20170310', '20170330');
INSERT INTO `xtb_auth` VALUES ('14', 'dn4', '2543', '20170301', '20181231');
INSERT INTO `xtb_auth` VALUES ('19', 'dn3', '540', '20170301', '20171231');
INSERT INTO `xtb_auth` VALUES ('20', 'dn5', '2543', '20171101', '20191110');
INSERT INTO `xtb_auth` VALUES ('22', 'dn2', '2543', '20170307', '20191128');
INSERT INTO `xtb_auth` VALUES ('24', 'dn6', '2543', '20171201', '20211201');
INSERT INTO `xtb_auth` VALUES ('25', 'dn7', '2543', '20171201', '20211201');
INSERT INTO `xtb_auth` VALUES ('26', 'dn8', '2543', '20171201', '20211201');
INSERT INTO `xtb_auth` VALUES ('27', 'dn9', '2543', '20171201', '20211201');
INSERT INTO `xtb_auth` VALUES ('28', 'dn10', '2543', '20171201', '20211201');
INSERT INTO `xtb_auth` VALUES ('29', 'dn11', '2543', '20171201', '20211201');
INSERT INTO `xtb_auth` VALUES ('32', 'dn12', '2543', '20151201', '20211201');
INSERT INTO `xtb_auth` VALUES ('33', 'dn13', '2543', '20180201', '20220226');
INSERT INTO `xtb_auth` VALUES ('37', 'dn17', '2544', '20180301', '20210309');
INSERT INTO `xtb_auth` VALUES ('38', 'dn18', '2544', '20180101', '20190101');
INSERT INTO `xtb_auth` VALUES ('39', 'dn19', '2544', '20180101', '20190101');
INSERT INTO `xtb_auth` VALUES ('42', 'dn15', '391', '20180302', '20210309');
INSERT INTO `xtb_auth` VALUES ('43', 'dn16', '391', '20180301', '20210309');
INSERT INTO `xtb_auth` VALUES ('44', 'dn14', '391', '20180221', '20220228');
INSERT INTO `xtb_auth` VALUES ('46', 'dn1', '576', '20161201', '20190430');
INSERT INTO `xtb_auth` VALUES ('47', 'dn20', '578', '20180101', '20190101');

-- ----------------------------
-- Table structure for `xtb_fhcd`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_fhcd`;
CREATE TABLE `xtb_fhcd` (
  `XMDH` int(11) NOT NULL,
  `FHDH` varchar(15) NOT NULL,
  `SFXS` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_fhcd
-- ----------------------------
INSERT INTO `xtb_fhcd` VALUES ('62', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('6', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('7', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('18', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('29', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('21', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('24', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('30', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('32', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('39', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('40', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('41', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('42', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('43', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('44', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('45', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('46', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('47', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('48', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('49', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('13', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('25', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('59', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('8', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('12', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('60', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('22', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('26', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('28', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('31', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('61', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('1', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('2', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('3', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('4', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('5', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('11', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('34', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('33', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('37', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('35', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('36', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('38', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('50', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('51', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('52', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('53', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('54', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('63', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('56', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('57', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('58', '801000000', '0');
INSERT INTO `xtb_fhcd` VALUES ('64', '801000000', '0');

-- ----------------------------
-- Table structure for `xtb_jsgnqx`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_jsgnqx`;
CREATE TABLE `xtb_jsgnqx` (
  `JSDH` int(11) NOT NULL,
  `XMDH` int(11) NOT NULL,
  `FHDH` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_jsgnqx
-- ----------------------------
INSERT INTO `xtb_jsgnqx` VALUES ('1', '1', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '2', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '3', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '4', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '5', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '6', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '7', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '8', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '11', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '18', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '21', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '22', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '24', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '26', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '28', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '29', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '30', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '31', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '32', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '33', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '34', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '35', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '36', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '37', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '38', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '39', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '40', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '41', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '42', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '43', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '44', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '45', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '46', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '47', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '48', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '49', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '50', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '51', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '52', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '53', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '54', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '56', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '57', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '58', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '59', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '60', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '61', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '62', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '63', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '64', '801000000');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '65', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '66', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '67', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '68', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '69', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '70', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '71', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '72', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '73', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '74', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '76', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '77', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '78', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '79', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '80', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '82', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '83', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '84', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '85', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '86', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '87', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '88', 'dn0');
INSERT INTO `xtb_jsgnqx` VALUES ('1', '89', 'dn0');

-- ----------------------------
-- Table structure for `xtb_jssjqx`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_jssjqx`;
CREATE TABLE `xtb_jssjqx` (
  `JSDH` int(11) NOT NULL,
  `JGKHDXDH` int(11) NOT NULL,
  `CKHYBZ` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_jssjqx
-- ----------------------------
INSERT INTO `xtb_jssjqx` VALUES ('1', '1', 'Y');

-- ----------------------------
-- Table structure for `xtb_jsywqx`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_jsywqx`;
CREATE TABLE `xtb_jsywqx` (
  `YWLB` varchar(2) NOT NULL,
  `JSDH` int(11) NOT NULL,
  `MS` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_jsywqx
-- ----------------------------

-- ----------------------------
-- Table structure for `xtb_pascode`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_pascode`;
CREATE TABLE `xtb_pascode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `size` float DEFAULT NULL,
  `selected` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_pascode
-- ----------------------------


-- ----------------------------
-- Table structure for `xtb_pasfile`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_pasfile`;
CREATE TABLE `xtb_pasfile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `funId` varchar(100) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `type` varchar(200) DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  `desc` varchar(50) DEFAULT NULL,
  `pid` varchar(100) DEFAULT NULL,
  `fhdh` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6199 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_pasfile
-- ----------------------------
INSERT INTO `xtb_pasfile` VALUES ('3416', 'bldkkfmxcx', '不良贷款扣罚明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3417', 'bldkqsjlgl', '不良贷款清收奖励管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3418', 'bldkqsjlgldr', '不良贷款清收奖励管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3419', 'bskhjxcx', '部室考核绩效查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3420', 'bxyjcx', '保险业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3421', 'bxyjfp', '保险业绩分配', 'yjgx', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3422', 'bxyjfprz', '保险业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3423', 'cdkcplrfx', '存贷款产品利润分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3424', 'cdkgrlrmx', '存贷款个人利润明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3425', 'cdkhylrfx', '存贷款行员利润分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3426', 'cdkjglrfx', '存贷款机构利润分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3427', 'cdkkhlrfx', '存贷款客户利润分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3428', 'cdkqxjgfx', '存贷款期限结构分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3429', 'cdkqxjgmx', '客户存贷款期限结构明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3430', 'cdkqxlrfx', '存贷款期限利润分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3431', 'cdkyjfprz', '存贷款业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3432', 'cdkyjgxcx', '存贷款业绩关系查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3433', 'cdkzhlrmx', '存贷款帐户利润明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3434', 'ckftpgl', '存款FTP价格管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3435', 'ckyjfpjk', '存款业绩分配监控', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3436', 'ckyjmfpmx', '存款业绩未分配明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3437', 'ckyjmspmx', '存款业绩未审批明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3438', 'ckyjyfpmx', '存款业绩已分配明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3439', 'ckzbj', '存款准备金管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3440', 'ckzhjstz', '存款账户基数调整', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3441', 'ckzhlrmxch', '存款帐户利润明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3442', 'clql', '存量清理', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3443', 'clyjrjrlfx', '存量业绩人均认领分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3444', 'clyjrlmxfx', '存量业绩认领明细分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3445', 'cystdkzhgl', '参与社团贷款账户管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3446', 'cystdkzhgldr', '参与社团贷款账户管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3447', 'dfgzyjcx', '代发工资业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3448', 'dfgzyjfp', '代发工资业绩分配', 'yjgx', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3449', 'dfgzyjfprz', '代发工资业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3450', 'dgkhcdkqygmfx', '客户存贷款企业规模分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3451', 'djpdbzcx', '等级评定标准查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3452', 'dkffjxmxcx', '贷款发放绩效明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3453', 'dkftpgl', '贷款FTP价格管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3454', 'dkkhglmxcx', '贷款客户管理明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3455', 'dklrmxcx', '贷款帐户利润明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3456', 'dklxsrmxcx', '贷款利息收入明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3457', 'dkqxkfmxcx', '贷款欠息扣罚明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3458', 'dktjjx', '贷款推荐绩效查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3459', 'dktjrgl', '贷款推荐人管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3460', 'dktjrgldr', '贷款推荐人管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3461', 'dkwjfldr', '贷款五级分类导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3462', 'dkxzkhjxmxcx', '贷款新增客户绩效明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3463', 'dkyjfpjk', '贷款业绩分配监控', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3464', 'dkyjmfpmx', '贷款业绩未分配明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3465', 'dkyjyfpmx', '贷款业绩已分配明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3466', 'dkzhjstz', '贷款账户基数调整', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3467', 'dkzhjstzdr', '贷款账户基数调整导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3468', 'dkzhjstzmx', '贷款账户基数调整明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3469', 'dkzryjfpcx', '贷款责任业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3470', 'dkzryjmx', '贷款责任业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3471', 'dqcytjxsgl', '地区差异调节系数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3472', 'dqcytjxsgldr', '地区差异调节系数管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3473', 'dqcyxsgl', '地区差异系数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3474', 'dqywcyxsgl', '地区业务差异系数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3475', 'dsfcgyjcx', '第三方存管业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3476', 'dsfcgyjfp', '第三方存管业绩分配', 'yjgx', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3477', 'dsfcgyjfprz', '第三方存管业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3478', 'dxtyjfp', '短信通业绩分配', 'yjgx', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3479', 'dxtyjfprz', '短信通业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3480', 'dxtyjmx', '短信通业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3481', 'eckhjxcx', '二次考核绩效查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3482', 'eckhjxfp', '二次考核绩效分配', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3483', 'eckhjxfpdr', '二次考核绩效分配导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3484', 'ftpjgfx', 'FTP价格分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3485', 'gjywyjcx', '国际业务业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3486', 'gjywyjfp', '国际业务业绩分配', 'yjgx', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3487', 'gjywyjfprz', '国际业务业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3488', 'gljxcx', '管理绩效查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3489', 'GRCDKHBD', '个人存贷客户变动', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3490', 'GRCDKHMX', '个人存贷客户明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3491', 'gryjcx', '个人业绩查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3492', 'gryjmx', '个人存贷款业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3493', 'grzmapp', '个人桌面APP', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3494', 'gwxxgl', '岗位信息管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3495', 'gybcywlgl', '柜员补偿业务量管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3496', 'gybcywlgldr', '柜员补偿业务量管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3497', 'gyhydygx', '柜员管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3498', 'gysgywldr', '柜员手工业务量导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3499', 'gysgywlgl', '柜员手工业务量管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3500', 'gyywlcx', '柜员业务量查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3501', 'gyywlhzcx', '柜员业务量汇总查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3502', 'gyywlmxcx', '柜员业务量明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3503', 'gyywlzsxsgl', '业务量折算系数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3504', 'gyywlzsxsgldr', '业务量折算系数管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3505', 'htgyzsxsgl', '后台柜员折算系数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3506', 'hyckjxgzcx', '行员存款绩效工资查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3507', 'hydbfx', '行员对比分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3508', 'hydjpdcx', '行员等级评定查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3509', 'hydjpdtz', '行员等级评定调整', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3510', 'hydkjxgz', '行员贷款绩效工资查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3511', 'hydkjxgzhzcx', '行员贷款绩效工资查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3512', 'hyfxjhzcx', '行员风险金汇总查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3513', 'hyfxjmxcx', '行员风险金明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3514', 'hyfxzbgl', '行员分析指标管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3515', 'hygzxsdr', '行员工资系数导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3516', 'hyhgfx', '行员宏观分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3517', 'hyjfxsdr', '行员计分系数导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3518', 'hyjfxsgl', '行员计分系数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3519', 'hyjhjdcx', '行员计划进度查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3520', 'hyjjhgl', '行员季计划管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3521', 'hyjjhgldr', '行员季计划管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3522', 'hyjstzgldr', '行员基数调整管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3523', 'hyjxgzcx', '行员绩效工资查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3524', 'hyjxgzcxmx', '行员绩效工资明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3525', 'hyjxgzfbfx', '行员绩效工资分布分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3526', 'hyjxgzhz', '行员绩效工资汇总', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3527', 'hyjxgzmxcx', '行员绩效工资汇总查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3528', 'hyjxtlcx', '行员绩效提留查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3529', 'hyjxtldxdr', '行员绩效提留兑现', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3530', 'hyjxtldxlr', '行员绩效提留兑现', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3531', 'hyjxtljemxcx', '行员绩效提留金额明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3532', 'hyjxtlmxcx', '行员绩效提留明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3533', 'hykhdfcx', '行员考核得分查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3534', 'hykhdfcxmx', '行员考核得分明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3535', 'hykhjsgl', '行员考核角色管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3536', 'hykhjspldr', '行员手工基数导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3537', 'hykhjsplgl', '行员手工基数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3538', 'hykhzbgl', '行员考核指标管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3539', 'hykhzbgldr', '行员考核指标管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3540', 'hykhzbgzmx', '行员考核指标工资明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3541', 'hykhzbxsgl', '行员工资系数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3542', 'hylbjxgzhz', '行员类别绩效工资汇总', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3543', 'hylbkhzbgzmx', '行员类别考核指标工资明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3544', 'hylcxsbb', '行员理财产品销售报表', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3545', 'hylrmxb', '行员利润明细表', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3546', 'hynjhdr', '行员年计划导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3547', 'hynjhgl', '行员年计划管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3548', 'hyqsfx', '行员趋势分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3549', 'hysgzbdr', '行员手工指标导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3550', 'hysgzbgl', '行员手工指标管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3551', 'hywgjfcx', '行员违规积分查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3552', 'hyxxgl', '行员信息管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3553', 'hyxxgldr', '行员信息管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3554', 'hyydrzcx', '行员异动日志查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3555', 'hyyjbb', '行员业绩报表', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3556', 'hyyjcx', '个人业绩查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3557', 'hyyjhgl', '行员月计划管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3558', 'hyyjhgldr', '行员月计划管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3559', 'hyywljxgzcx', '行员业务量绩效工资查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3560', 'hyzjywsrdr', '行员中间业务收入导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3561', 'hyzjywsrgl', '行员中间业务收入管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3562', 'hyzxpfdfmx', '行员在线评分得分明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3563', 'hyzxpfdr', '行员在线评分导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3564', 'hyzxpfjdcx', '行员在线评分进度查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3565', 'jcdjmxcx', '行员计酬单价明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3566', 'jgcdkhbd', '机构存贷客户变动', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3567', 'jgcdkhmx', '机构存贷客户明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3568', 'jgdbfx', '机构对比分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3569', 'jgdbrsdr', '机构定编人数导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3570', 'jgdbrsgl', '机构定编人数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3571', 'jgdjpdcx', '机构等级评定查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3572', 'jgdjpdtz', '机构等级评定调整', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3573', 'jgfxzbgl', '机构分析指标管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3574', 'jgfydr', '机构费用导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3575', 'jgfygl', '机构费用管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3576', 'jggzxsdr', '机构工资系数导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3577', 'jggzxsgl', '机构工资系数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3578', 'jghgfx', '机构宏观分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3579', 'jgjcdjmxcx', '机构得分系数明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3580', 'jgjfxsdr', '机构计分系数导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3581', 'jgjhjdcx', '机构计划进度查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3582', 'jgjjhgl', '机构季计划管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3583', 'jgjjhgldr', '机构季计划管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3584', 'jgjxgzcx', '机构绩效工资查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3585', 'jgjxgzcxmx', '机构绩效工资明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3586', 'jgjxgzhz', '机构绩效工资汇总', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3587', 'jgjxtlcx', '机构绩效提留查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3588', 'jgjxtlmxcx', '机构绩效提留明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3589', 'jgkhdfcx', '机构考核得分查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3590', 'jgkhdfcxmx', '机构考核得分明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3591', 'jgkhjsgl', '机构考核角色管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3592', 'jgkhjspldr', '机构手工基数导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3593', 'jgkhjsplgl', '机构手工基数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3594', 'jgkhjxcx', '机构考核绩效查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3595', 'jgkhzbgl', '机构考核指标管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3596', 'jgkhzbgldr', '机构考核指标管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3597', 'jgkhzbgzmx', '机构考核指标工资明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3598', 'jgkhzbxsgl', '机构计分系数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3599', 'jglbjxgzhz', '机构类别绩效工资汇总', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3600', 'jglbkhzbgzmx', '机构类别考核指标工资明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3601', 'jglccpxsfx', '机构理财产品销售报表', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3602', 'jglrmxb', '机构利润明细表', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3603', 'jgnjhdr', '机构年计划导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3604', 'jgnjhgl', '机构年计划管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3605', 'jgqsfx', '机构趋势分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3606', 'jgsgzbdr', '机构手工指标导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3607', 'jgsgzbgl', '机构手工指标管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3608', 'jgwgjfcx', '机构违规积分查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3609', 'jgyjbb', '机构业绩报表', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3610', 'jgyjcx', '机构业绩查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3611', 'jgyjhgl', '机构月计划管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3612', 'jgyjhgldr', '机构月计划管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3613', 'jgywcyxsgl', '机构业务差异系数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3614', 'jgywlhzcx', '机构业务量汇总查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3615', 'jgywlmxcx', '机构业务量明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3616', 'jgzxpfdr', '机构在线评分导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3617', 'jgzxpfjdcx', '机构在线评分进度查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3618', 'jjyjcx', '基金业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3619', 'jjyjfp', '基金业绩分配', 'yjgx', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3620', 'jjyjfprz', '基金业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3621', 'jsjsfapz', '基数计算方案配置', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3622', 'jxgzmxqjhymxb', '绩效工资区间行员明细表', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3623', 'jxkhcsgl', '绩效考核参数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3624', 'jxkhcspz', '绩效考核参数配置', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3625', 'jydmgl', '交易代码管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3626', 'jzmzdkgl', '尽职免责贷款账户管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3627', 'jzmzdkgldr', '尽职免责贷款管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3628', 'khcdkfctj', '客户存贷款分层分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3629', 'khcdksshyfx', '客户存贷款所属行业分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3630', 'khgxddfcx', '客户贡献度得分查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3631', 'khgxdfx', '客户存贷款分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3632', 'khnljgfx', '客户年龄结构分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3633', 'khsrtx', '客户生日提醒', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3634', 'khsypmfx', '客户收益排名分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3635', 'khxjcx', '客户星级查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3636', 'khxjflfx', '客户星级分类分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3637', 'khxjpdgxxdfdr', '客户星级评定贡献项得分导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3638', 'khxjpdgxxdfgl', '客户贡献度得分管理', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3639', 'khxjsglr', '客户星级手工管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3640', 'khxjsglrdr', '客户星级手工管理导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3641', 'khxjtz', '客户星级调整管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3642', 'khxxcx', '客户信息查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3643', 'khywzkfx', '客户业务状况分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3644', 'khzbjxgzhz', '考核指标绩效工资汇总', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3645', 'kjgyjmx', '跨机构存贷款业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3646', 'kmzzbb', '科目总账报表', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3647', 'kyjfprz', '卡业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3648', 'kyjmxcx', '卡业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3649', 'lcyjcx', '理财业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3650', 'lcyjfp', '理财业绩分配', 'yjgx', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3651', 'lcyjfprz', '理财业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3652', 'lzjxcx', '履职绩效查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3653', 'mpasxtxxgl', 'MPAS系统消息管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3654', 'posshyjfp', 'POS商户业绩分配', 'yjgx', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3655', 'posshyjfprz', 'POS商户业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3656', 'posyjcx', 'POS商户业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3657', 'psrz', '跑数日志查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3658', 'qhjxgzhzbb', '全行绩效工资汇总报表', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3659', 'qjpzgl', '区间配置管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3660', 'qyflgl', '区域分类管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3661', 'sjyhyjcx', '手机银行业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3662', 'sjyhyjfp', '手机银行业绩分配', 'yjgx', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3663', 'sjyhyjfprz', '手机银行业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3664', 'test', 'test', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3666', 'testyzz', 'testyzz', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3667', 'tycsgl', '通用参数管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3668', 'wgjfxmfl', '违规积分项目分类', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3669', 'wgjfxmgl', '违规积分项目管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3670', 'wgsjgl', '违规事件管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3671', 'wrlmx', '未认领明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3672', 'wsyhyjcx', '网上银行业绩明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3673', 'wsyhyjfp', '网上银行业绩分配', 'yjgx', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3674', 'wsyhyjfprz', '网上银行业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3675', 'xcdkzhdr', '瑕疵贷款账户导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3676', 'xcdkzhgl', '瑕疵贷款账户管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3677', 'xnhyfpjxdr', '虚拟行员绩效工资分配导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3678', 'xnhyfpjxgzgl', '虚拟行员绩效工资分配', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3679', 'xtdlhyxxmx', '系统登陆行员信息明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3680', 'xtrzcx', '最新日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3681', 'xtsyqktj', '系统使用情况分析', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3682', 'xttzdjckgd', '系统通知查看更多', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3683', 'xtwsyhyxxmx', '系统未使用行员信息明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3684', 'xxtsgl', '行员类别信息配置', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3685', 'xzbldkkffhcx', '新增不良贷款扣罚返还查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3686', 'xzbldkkfmxcx', '新增不良贷款扣罚明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3687', 'yjsjgl', '预警事件管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3688', 'yrlzymhz', '已认领子页面汇总', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3689', 'yrlzymmx', '已认领子页面明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3690', 'yswslxkfjfhcx', '应收未收利息扣罚及返还查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3691', 'yxbldkzhdr', '隐性不良贷款账户导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3692', 'yxbldkzhgl', '隐性不良贷款账户管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3693', 'yyckyj', '预约业绩管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3694', 'yyfamx', '引用方案明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3695', 'yyjlhd', '预约记录核对', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3696', 'yyrzcx', '应用日志查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3697', 'zfzjyjg', '总分账检验结果', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3698', 'zhgxxgrzcxora', '存贷款业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3699', 'zhhylbrjrlmx', '支行行员类别人均认领明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3700', 'zhjstz', '账户基数调整', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3701', 'zhjstzdr', '存款账户基数调整导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3702', 'zhjstzdr2', '存款账户基数调整导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3703', 'zhjstzmx', '账户基数调整明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3704', 'zhsthyrjrlye', '支行实体行员人均认领余额', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3705', 'zhyebdmx', '账户余额变动明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3706', 'zjywgrmxcx', '中间业务个人明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3707', 'zjywgryjcxmx', '中间业务个人业绩查询明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3708', 'zjywyjfprz', '中间业务业绩分配日志', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3709', 'znbskhdfcx', '职能部室考核得分查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3710', 'znbskhdfcxmx', '职能部室考核得分明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3711', 'znbskhjdcx', '职能部室考核进度查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3712', 'znbskhjdmxcx', '职能部室考核进度查询明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3713', 'znbskhjgjycx', '职能部室考核结果评价查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3714', 'znbskhjgjycxmx', '职能部室考核结果评价明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3715', 'znbskhpycx', '职能部室考核评语查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3716', 'znbsxxpfdr', '职能部室线下评分导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3717', 'znbsygkhdfcx', '职能部室员工考核得分查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3718', 'znbsygkhdfcxmx', '职能部室员工考核得分查询明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3719', 'znbsygkhjdcx', '职能部室员工考核进度查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3720', 'znbsygkhjdcxmx', '职能部室员工考核进度查询明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3721', 'znbsygkhjgjycx', '职能部室员工考核结果评价查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3722', 'znbsygkhjgjycxmx', '职能部室员工考核结果评价明细查询', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3723', 'znbsygxxpfdr', '职能部室员工线下评分导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3724', 'zxlldr', '执行利率导入', 'import', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3725', 'zxllgl', '执行利率管理', 'manage', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');
INSERT INTO `xtb_pasfile` VALUES ('3726', 'zxpfdfmx', '机构在线评分得分明细', 'query', '1.0.0.0', '标准版', '', 'dn0', '2018-07-27 16:22:42');

-- ----------------------------
-- Table structure for `xtb_xtcd`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_xtcd`;
CREATE TABLE `xtb_xtcd` (
  `XMDH` int(11) NOT NULL,
  `XMMC` varchar(50) DEFAULT NULL,
  `XMDZ` varchar(200) DEFAULT NULL,
  `SJXM` int(11) DEFAULT NULL,
  `CDJB` varchar(1) DEFAULT NULL,
  `DZLX` varchar(1) DEFAULT NULL,
  `CLASSID` varchar(20) DEFAULT NULL,
  `SFXS` int(11) DEFAULT NULL,
  `IMGURL` varchar(256) DEFAULT NULL,
  `CKYXJ` varchar(1) DEFAULT NULL,
  `QXBS` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_xtcd
-- ----------------------------
-- INSERT INTO `xtb_xtcd` VALUES ('69', '菜单查询', '/module/xtcd/list', '6', '3', '1', 'A2', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('70', '系统菜单分配', '/module/xtcd/toAuth', '6', '3', '1', 'A3', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('71', '系统菜单同步', '/module/xtcd/toAssign', '6', '3', '1', 'A4', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('72', '系统菜单编辑与增加', '/module/xtcd/toAdd', '6', '3', '1', 'A5', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('73', '系统菜单删除', '/module/xtcd/delete', '6', '3', '1', 'A6', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('74', '系统资源菜单分配', '/module/passCloud/choseSys', '11', '3', '1', 'A2', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('79', '跑数日志', '/module/parser/query/visitHtml/psrz', '77', '2', '1', 'A2', '1', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('76', '系统监控', '#', '1', '0', '1', 'A1', '0', 'xtcd/performance.png', null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('87', '日志事件', 'http://mpas2.tunnel.qydev.com/#/events', '83', '2', '1', 'A4', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('88', '仓库管理', 'http://mpas2.tunnel.qydev.com/#/registry', '83', '2', '1', 'A5', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('65', 'tes1URl', '/module/XXX/XX', '35', '2', '1', 'A4', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('66', '资源管理', '#', '1', '0', '1', 'A2', '0', 'xtcd/library.png', null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('68', '租户管理', '/module/passCloud/manage', '67', '2', '1', 'A3', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('83', '云管理', '#', '82', '1', '1', 'A1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('77', '系统监控', '#', '76', '1', '1', 'A1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('78', '系统监控', '/module/passCloud/systemMoniter', '77', '2', '1', 'A1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('82', '私有云管理', '#', '1', '0', '1', 'A4', '0', 'xtcd/library.png', null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('84', '容器管理', 'http://mpas2.tunnel.qydev.com/#/containers', '83', '2', '1', 'A1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('85', '镜像管理', 'http://mpas2.tunnel.qydev.com/#/images', '83', '2', '1', 'A2', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('86', '节点管理', 'http://mpas2.tunnel.qydev.com/#/nodes', '83', '2', '1', 'A3', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('62', '存贷款账户利润明细', '/module/parser/query/visitHtml/cdkzhlrmx', '3', '2', '1', 'C4', '1', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('6', '菜单管理', '/module/xtcd/index', '3', '2', '1', 'C3', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('7', '机构管理', '/module/jggl/index', '3', '2', '1', 'C4', '1', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('18', '行员理财产品销售报表', '/module/parser/query/visitHtml/hylcxsbb', '3', '2', '1', 'C4', '1', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('29', '考核方案', '#', '1', '0', '1', 'A1', '1', 'xtcd/library.png\\r\\n', null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('21', '等级评定配置', '/module/djpdpz/index', '54', '2', '1', 'C7', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('24', '行员信息管理', '/module/parser/manage/visitHtml/hyxxgl', '3', '2', '1', 'C4', '1', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('30', '考核方案管理', '#', '29', '1', '1', 'B1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('32', '公式数据源配置', '/module/gssjypz/index', '30', '2', '1', 'C2', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('39', '产品分类管理', '/module/cpflgl/index', '38', '2', '1', 'C8', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('40', '指标数据源配置', '/module/zbpzsjy/index', '34', '2', '1', 'C8', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('41', '基数计算方案配置', '/module/jscs/index', '35', '2', '1', 'C1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('42', 'FTP价格管理(公用)', '#', '37', '0', '1', 'B4', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('43', '存款FTP价格管理', '/module/ckftpjggl/index', '42', '2', '1', 'C1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('44', '贷款FTP价格管理', '/module/dkftpjggl/index', '42', '2', '1', 'C2', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('45', 'FTP参数管理(简易定价法)', '#', '37', '0', '1', 'B2', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('46', '利差部门分配管理', '/module/lcbmfpgl/index', '45', '2', '1', 'C1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('47', '业绩管理', '#', '1', '0', '1', 'C5', '1', 'xtcd/performance.png', null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('48', 'FTP价格走势分析', '/module/ftpjgzsfx/index', '47', '2', '1', 'C1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('49', 'FTP利差走势分析', '/module/ftplczsfx/index', '47', '2', '1', 'C2', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('67', '公共资源管理', '#', '66', '1', '1', 'A2', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('80', '系统应用日志', '/module/yyrz/index', '77', '2', '1', 'A2', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('59', '职能部室考核进度查询', '/module/parser/query/visitHtml/znbskhjdcx', '56', '2', '1', '99', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('8', '开发中心', '/devcenter/main', '3', '2', '1', 'C5', '1', 'xtcd/pas-plus.png', null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('60', '指标配置', '/module/zbpz/index', '34', '2', '1', 'A1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('22', '行员异动日志查询', '/module/parser/query/visitHtml/hyydrzcx', '3', '2', '1', 'C4', '1', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('26', '行员信息管理导入', '/module/parser/import/visitHtml/hyxxgldr', '3', '2', '1', 'C4', '1', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('28', '职能部室挂靠支行管理', '/module/znbsgkzhgl/index', '56', '2', '1', 'C7', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('31', '考核方案配置', '/module/khfa/index', '30', '2', '1', 'C1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('61', '保险业绩分配', '/module/parser/yjgx/visitHtml/bxyjfp', '3', '2', '1', 'C4', '1', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('1', 'PAS菜单', '#', '0', '0', '1', 'A01', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('2', '系统管理', '#', '1', '0', '1', 'A02', '0', 'xtcd/system.png', null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('3', '系统管理', '#', '2', '1', '1', 'B1', '0', 'xtcd/system.png', null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('4', '行员管理', '/module/hygl/index', '3', '2', '1', 'C1', '1', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('5', '角色管理', '/module/aqjs/index', '3', '2', '1', 'C2', '1', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('11', '菜单资源管理', '/module/passCloud/index', '67', '2', '1', 'A1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('34', '指标管理', '#', '33', '0', '1', 'B2', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('33', '指标库', '#', '1', '0', '1', 'B2', '1', 'xtcd/kpi.png', null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('37', 'FTP利润', '#', '1', '0', '1', 'B2', '1', 'xtcd/profit.png', null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('35', '基数设置', '#', '33', '0', '1', 'B3', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('36', '行员基数调整管理', '/module/hyjstzgl/index', '35', '2', '1', 'C6', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('38', 'FTP参数管理(公用)', '#', '37', '0', '1', 'B1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('50', '公式算法模板管理', '/module/gssfmbgl/index', '30', '2', '1', 'C3', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('51', '贷款绩效', '#', '29', '0', '1', 'B7', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('52', '不良贷款扣罚标准管理', '/module/bldkkfgl/index', '51', '2', '1', 'C1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('53', '风险金返还管理', '/module/fxjfhgl/index', '51', '2', '1', 'C3', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('54', '等级评定', '#', '29', '0', '1', 'B8', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('63', '考核方案系数', '#', '29', '1', '1', '20', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('56', '职能部室', '#', '29', '0', '1', 'B6', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('57', '职能部室方案配置', '/module/znbskh/znbsfapzIndex', '56', '2', '1', 'C1', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('58', '职能部室考核设置', '/module/znbskh/khszIndex', '56', '2', '1', 'C2', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('64', '地区差异系数管理', '/module/parser/manage/visitHtml/dqcyxsgl', '63', '2', '1', 'C4', '0', null, null, '0');
-- INSERT INTO `xtb_xtcd` VALUES ('89', '服务器监控', 'http://192.168.0.112:8080/module/main/index.html', '77', '2', '1', 'A3', '0', '', null, '0');

INSERT INTO `xtb_xtcd` VALUES ('2', '系统管理', '#', '1', '0', '1', 'A02', '0', 'xtcd/system.png', null, '0');
INSERT INTO `xtb_xtcd` VALUES ('3', '系统管理', '#', '2', '1', '1', 'B1', '0', 'xtcd/system.png', null, '0');
INSERT INTO `xtb_xtcd` VALUES ('6', '菜单管理', '/module/xtcd/index', '3', '2', '1', 'C3', '0', null, null, '0');
INSERT INTO `xtb_xtcd` VALUES ('76', '系统监控', '#', '1', '0', '1', 'A1', '0', 'xtcd/performance.png', null, '0');
INSERT INTO `xtb_xtcd` VALUES ('77', '系统监控', '#', '76', '1', '1', 'A1', '0', null, null, '0');
INSERT INTO `xtb_xtcd` VALUES ('78', '系统监控', '/module/passCloud/systemMoniter', '77', '2', '1', 'A1', '0', null, null, '0');
INSERT INTO `xtb_xtcd` VALUES ('89', '服务器监控', 'http://192.168.0.112:8080/module/main/index.html', '77', '2', '1', 'A3', '0', '', null, '0');
INSERT INTO `xtb_xtcd` VALUES ('80', '系统应用日志', '/module/yyrz/index', '77', '2', '1', 'A2', '0', null, null, '0');
INSERT INTO `xtb_xtcd` VALUES ('66', '资源管理', '#', '1', '0', '1', 'A2', '0', 'xtcd/library.png', null, '0');
INSERT INTO `xtb_xtcd` VALUES ('67', '公共资源管理', '#', '66', '1', '1', 'A2', '0', null, null, '0');
INSERT INTO `xtb_xtcd` VALUES ('68', '租户管理', '/module/passCloud/manage', '67', '2', '1', 'A3', '0', null, null, '0');

-- ----------------------------
-- Table structure for `xtb_xtcs`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_xtcs`;
CREATE TABLE `xtb_xtcs` (
  `CSMC` varchar(50) NOT NULL,
  `ZDBZ` varchar(1) DEFAULT NULL,
  `CSMS` varchar(255) DEFAULT NULL,
  `CSZ` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_xtcs
-- ----------------------------
INSERT INTO `xtb_xtcs` VALUES ('MPAS', '1', '项目是否包含MPAS,0--不包含，1--包含，包含时职能部室考核提供导入导出数据按钮', '1');
INSERT INTO `xtb_xtcs` VALUES ('test0715', '1', '123', '123');
INSERT INTO `xtb_xtcs` VALUES ('SYS_DDLB', '1', '当前的调度类别(在跑数时实时更新)', '01');
INSERT INTO `xtb_xtcs` VALUES ('SYS_QSRQ', '1', '接口文件最小提供数据日期，即为第一年的日均类数据计算日期，YYYYMMDD格式', '20140310');
INSERT INTO `xtb_xtcs` VALUES ('BLDKXLHDRQ', '1', '不良贷款新老划断日期标志，在此日期之前的不良贷款认定为老不良贷款(可能不考核）', '20140101');
INSERT INTO `xtb_xtcs` VALUES ('ZNBS_PF_CLIENT', '1', '职能部室评分客户端：0=PAS端；1=APP', '0');
INSERT INTO `xtb_xtcs` VALUES ('test07151', '1', '1231', '1231');
INSERT INTO `xtb_xtcs` VALUES ('NAVBAR_TYPE', '0', '当主题格式为compound时，导航菜单的子菜单位置目前有两种放置(top,left)', 'top');
INSERT INTO `xtb_xtcs` VALUES ('JGJSSD', '1', '机构基数计算方式设定，值0表示以上年业绩作为基数，值1表示以行员基数汇总作为基数', '1');
INSERT INTO `xtb_xtcs` VALUES ('SYS_DATE', '0', '系统计算日期（影响增量重算的调度时间，请不要随意修改)', '20160127');
INSERT INTO `xtb_xtcs` VALUES ('XTBBH', '1', '标准版系统版本号，如V2.3.0', 'V3.1');
INSERT INTO `xtb_xtcs` VALUES ('AUTH_MODE', '0', '1=总行授权模式；2=二级授权模式；3=三级授权模式；', '3');
INSERT INTO `xtb_xtcs` VALUES ('CDKYJDRCS', '1', '存贷款业绩批量导入模板列数(除子帐号），数据必须为奇数如：15、17，最大值为25', '17');
INSERT INTO `xtb_xtcs` VALUES ('DATA_FOLDER', '2', 'ETL数据接口表的导入路径末尾必须带\\D:\\SMARTDB2\\PAS_DATA_SRC\\', 'E:\\PASDB2\\PAS_DATA_SRC\\');
INSERT INTO `xtb_xtcs` VALUES ('DEGREE', '1', '并行度个数设置,1代表不启动并行', '5');
INSERT INTO `xtb_xtcs` VALUES ('INITMENU', '1', '初如化菜单时默认显示常用功能还是全部功能，1：常用功能，0：全部功能', '1');
INSERT INTO `xtb_xtcs` VALUES ('LIST_DEBUG', '0', '列表调试', '1');
INSERT INTO `xtb_xtcs` VALUES ('SFSYYZ', '1', '是否使用阈值分配方式', '1');
INSERT INTO `xtb_xtcs` VALUES ('SJQSRQ', '1', '开始扣减计提损失最早日期', '20131115');
INSERT INTO `xtb_xtcs` VALUES ('SYS_ARCH', '1', '当前系统架构1总分支、0总支', '0');
INSERT INTO `xtb_xtcs` VALUES ('SYS_CSTS', '0', '重算天数，工作日，此值会在跑批时自动更新成要重算的工作日（自动排除节假日期）', '42');
INSERT INTO `xtb_xtcs` VALUES ('SYS_LOG', '1', '是否将动态语句记录在调试日志表中0:不写入,1:写入', '1');
INSERT INTO `xtb_xtcs` VALUES ('SYS_MODE', '1', '系统模式0为开发模式，1为产品模式', '0');
INSERT INTO `xtb_xtcs` VALUES ('SYS_SCDL', '1', '是否使用首次登录修改用户密码的功能，0：不使用；1：使用', '1');
INSERT INTO `xtb_xtcs` VALUES ('SYS_STATUS', '0', '系统状态  0－正常 1－错误 2－数据处理中', '0');
INSERT INTO `xtb_xtcs` VALUES ('SYS_STYLE', '1', '系统样式 red-红色,blue-蓝色', 'green');
INSERT INTO `xtb_xtcs` VALUES ('SYS_XMMC', '0', '项目标题（需要重启生效）', '云平台管理系统');
INSERT INTO `xtb_xtcs` VALUES ('XJSFPDBZ', '1', '现金收付判断标准(1--表示按现转标志=1加收付标志(0--收入,1--付出)作为判断标准取交易金额) 2--直接取现金借方/贷方发生额)', '1');
INSERT INTO `xtb_xtcs` VALUES ('YXKQSRQ', '1', '有效卡判断标准的签约起始日期（自然天数N天内（CSZ里面直接输入N天)/当月初/当季初/当年初(CSZ里面输入@YC或@JC或@NC）', '@YC');
INSERT INTO `xtb_xtcs` VALUES ('YXPOSQSRQ', '1', '有效POS商户判断标准的签约起始日期（自然天数N天内（CSZ里面直接输入N天)/当月初/当季初/当年初(CSZ里面输入@YC或@JC或@NC）', '@YC');
INSERT INTO `xtb_xtcs` VALUES ('YXSJYHQSRQ', '1', '有效手机银行判断标准的签约起始日期（自然天数N天内（CSZ里面直接输入N天)/当月初/当季初/当年初(CSZ里面输入@YC或@JC或@NC）', '@YC');
INSERT INTO `xtb_xtcs` VALUES ('YXWYQSRQ', '1', '有效网银判断标准的签约起始日期（自然天数N天内（CSZ里面直接输入N天)/当月初/当季初/当年初(CSZ里面输入@YC或@JC或@NC）', '@YC');
INSERT INTO `xtb_xtcs` VALUES ('YXJJQSRQ', '1', '有效基金判断标准的签约起始日期（自然天数N天内（CSZ里面直接输入N天)/当月初/当季初/当年初(CSZ里面输入@YC或@JC或@NC）', '@YC');
INSERT INTO `xtb_xtcs` VALUES ('ZFRMBWCCS', '1', '总/分户人民币误差范围', '999999999');
INSERT INTO `xtb_xtcs` VALUES ('SYS_THEME', '0', '主题值   目前仅有(red、blue、green、orange)(需注销)', 'green');
INSERT INTO `xtb_xtcs` VALUES ('CONTROL_FOLDER', '2', 'ORALCE版本control.sql文件存放详细路径', 'E:\\PASORA\\control.sql');
INSERT INTO `xtb_xtcs` VALUES ('YYSXTS', '1', '预约存款默认的预约结束日期，通过预约起始日期+此参数值表示为预约结束日期', '5');
INSERT INTO `xtb_xtcs` VALUES ('MRCSTS', '0', '默认重算天数，工作日，此值设定好后不变，作为SYS_CSTS的参照值', '30');
INSERT INTO `xtb_xtcs` VALUES ('PFKSTS', '0', '有效评分开始天数（从考核期数末往前推算的天数）,超出此天数代表评分未开始，例如此参数设定为5天，考核期数=2014年第一季度，则评分开始时间为：20140327', '5');
INSERT INTO `xtb_xtcs` VALUES ('PFJSTS', '0', '有效评分结束天数（从考核期数末往后推算的天数）,超出此天数代表评分关闭，例如此参数设定为5天，考核期数=2014年第一季度，则评分关闭时间为：20140405', '5');
INSERT INTO `xtb_xtcs` VALUES ('HQLDDH', '1', '活期利率的利率代号（用于定期提前支取获取）', '00');
INSERT INTO `xtb_xtcs` VALUES ('DB_SYSTEM', '1', '数据库所在服务器的操作系统，1-WINDOWS：2-LINUX/AIX', '1');
INSERT INTO `xtb_xtcs` VALUES ('DB_HOME', '1', '数据库安装路径（LINUX/AIX操作系统下的ORACLE版本需要配置此路径)', '/oracle/');
INSERT INTO `xtb_xtcs` VALUES ('CKYJGXMS', '1', '存款业绩关系模式：0--帐号作为主键 1--帐号+子帐号作为主键', '1');
INSERT INTO `xtb_xtcs` VALUES ('DKYJGXMS', '1', '贷款业绩关系模式：0--帐号作为主键 1--帐号+子帐号作为主键', '0');
INSERT INTO `xtb_xtcs` VALUES ('KHFAPZMS', '1', '考核方案配置模式 1--按机构类别 2--按机构（过滤有权限机构）', '2');
INSERT INTO `xtb_xtcs` VALUES ('SYS_THEME_TYPE', '0', '主题格式 目前有(smartone、compound)(需要重启生效)', 'smartone');
INSERT INTO `xtb_xtcs` VALUES ('LOGIN_TYPE', '0', '登陆界面样式 目前有(login_old、login_smartone、login_compound)', 'login_compound');

-- ----------------------------
-- Table structure for `xtb_xtrz`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_xtrz`;
CREATE TABLE `xtb_xtrz` (
  `SJDH` int(11) NOT NULL,
  `SJSJ` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `SJLX` varchar(1) DEFAULT NULL,
  `SJLY` varchar(50) DEFAULT NULL,
  `SJMS` varchar(4000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_xtrz
-- ----------------------------

-- ----------------------------
-- Table structure for `xtb_xttz`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_xttz`;
CREATE TABLE `xtb_xttz` (
  `TZDH` int(11) NOT NULL,
  `BT` varchar(200) DEFAULT NULL,
  `NR` varchar(4000) DEFAULT NULL,
  `TZFID` int(11) DEFAULT NULL,
  `TZRQ` int(11) DEFAULT NULL,
  `KHDXDH` int(11) DEFAULT NULL,
  `KYK` varchar(1) DEFAULT NULL,
  `CKFW` varchar(4000) DEFAULT NULL,
  `ZT` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_xttz
-- ----------------------------

-- ----------------------------
-- Table structure for `xtb_xttzmx`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_xttzmx`;
CREATE TABLE `xtb_xttzmx` (
  `TZDH` int(11) NOT NULL,
  `KHDXDH` int(11) NOT NULL,
  `SFYD` varchar(1) DEFAULT NULL,
  `SFZD` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_xttzmx
-- ----------------------------

-- ----------------------------
-- Table structure for `xtb_xtzycd`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_xtzycd`;
CREATE TABLE `xtb_xtzycd` (
  `XMDH` int(11) NOT NULL,
  `XMMC` varchar(50) DEFAULT NULL,
  `XMDZ` varchar(200) DEFAULT NULL,
  `SJXM` int(11) DEFAULT NULL,
  `CDJB` varchar(1) DEFAULT NULL,
  `DZLX` varchar(1) DEFAULT NULL,
  `CLASSID` varchar(20) DEFAULT NULL,
  `SFXS` int(11) DEFAULT NULL,
  `IMGURL` varchar(256) DEFAULT NULL,
  `CKYXJ` varchar(1) DEFAULT NULL,
  `QXBS` int(11) DEFAULT '0',
  `version` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`XMDH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_xtzycd
-- ----------------------------
INSERT INTO `xtb_xtzycd` VALUES ('1', 'PAS菜单', '#', '0', '0', '1', 'A01', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('2', '系统管理', '#', '1', '0', '1', 'A02', '0', 'xtcd/system.png', null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('3', '系统管理', '#', '2', '1', '1', 'B1', '0', 'xtcd/system.png', null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('4', '行员管理', '/module/hygl/index', '3', '2', '1', 'C1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('5', '安全角色管理', '/module/aqjs/index', '3', '2', '1', 'C2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('6', '机构管理', '/module/jggl/index', '3', '2', '1', 'C3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('7', '菜单管理', '/module/xtcd/index', '3', '2', '1', 'C4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('8', '开发中心', '/devcenter/main', '201', '2', '1', 'C5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('9', '指标管理', '#', '1', '0', '1', 'A2', '0', 'xtcd/kpi.png', null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('10', '指标管理', '#', '9', '1', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('11', 'FTP利润', '#', '1', '0', '1', 'A9', '0', 'xtcd/profit.png', null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('13', '产品分类管理', '/module/cpflgl/index', '231', '2', '1', 'C1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('14', '业绩管理', '#', '1', '0', '1', 'A4', '0', 'xtcd/performance.png', null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('16', '存款FTP价格管理', '/module/ckftpjggl/index', '231', '2', '1', 'C1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('17', '贷款FTP价格管理', '/module/dkftpjggl/index', '231', '2', '1', 'C2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('23', '基数设置', '#', '9', '1', '1', 'B3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('24', '基数方案设置', '/module/jsfasz/index', '23', '2', '1', 'C1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('25', '行员基数调整管理', '/module/hyjstzgl/index', '23', '2', '1', 'C6', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('26', '考核方案', '#', '1', '0', '1', 'A8', '0', 'xtcd/library.png', null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('50', '开发平台', '#', '1', '0', '1', 'A0', '0', 'xtcd/pas-plus.png', null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('51', '计划管理', '#', '1', '0', '1', 'A3', '0', 'xtcd/tasks.png', null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('52', '业绩查询', '#', '1', '0', '1', 'A5', '0', 'xtcd/yjcx.png', null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('53', '绩效分析', '#', '1', '0', '1', 'A6', '0', 'xtcd/analysis.png', null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('201', '开发中心', '#', '50', '1', '1', 'A1', '0', 'xtcd/pas-plus.png', null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('205', '手工指标', '#', '9', '1', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('207', '行员计划', '#', '51', '1', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('208', '机构计划', '#', '51', '1', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('210', '业绩监控', '#', '14', '1', '1', 'A5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('211', '业绩预约', '#', '14', '1', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('212', '业绩分配', '#', '14', '1', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('213', '业务量管理', '#', '14', '1', '1', 'B6', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('215', '业务量查询', '#', '52', '1', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('216', '业绩查询', '#', '52', '1', '1', 'A1', '0', 'xtcd/yjcx.png', null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('217', '业绩明细', '#', '52', '1', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('218', '日志查询', '#', '52', '1', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('219', '业绩分析', '#', '53', '1', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('220', '业绩报表', '#', '53', '1', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('222', '考核角色管理', '#', '26', '1', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('224', '考核方案管理', '#', '26', '1', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('225', '考核方案系数管理', '#', '26', '1', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('227', '应用结果查询', '#', '26', '1', '1', 'A5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('228', '职能部室考核', '#', '26', '1', '1', 'A6', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('229', '贷款绩效明细', '#', '26', '1', '1', 'A7', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('230', '等级评定', '#', '26', '1', '1', 'B8', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('231', 'FTP参数管理', '#', '11', '1', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('233', 'FTP利润分析', '#', '11', '1', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('313', '行员手工指标管理', '/module/parser/manage/visitHtml/hysgzbgl', '205', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('314', '机构手工指标管理', '/module/parser/manage/visitHtml/jgsgzbgl', '205', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('315', '账户基数调整', '/module/parser/manage/visitHtml/zhjstz', '23', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('317', '机构手工基数管理', '/module/parser/manage/visitHtml/jgkhjsplgl', '23', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('318', '行员手工基数管理', '/module/parser/manage/visitHtml/hykhjsplgl', '23', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('319', '贷款账户基数调整', '/module/parser/manage/visitHtml/dkzhjstz', '23', '2', '1', 'A4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('320', '行员月计划管理', '/module/parser/manage/visitHtml/hyyjhgl', '207', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('321', '行员季计划管理', '/module/parser/manage/visitHtml/hyjjhgl', '207', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('322', '行员年计划管理', '/module/parser/manage/visitHtml/hynjhgl', '207', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('323', '行员计划进度查询', '/module/parser/query/visitHtml/hyjhjdcx', '207', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('324', '机构月计划管理', '/module/parser/manage/visitHtml/jgyjhgl', '208', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('325', '机构季计划管理', '/module/parser/manage/visitHtml/jgjjhgl', '208', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('326', '机构年计划管理', '/module/parser/manage/visitHtml/jgnjhgl', '208', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('327', '机构计划进度查询', '/module/parser/query/visitHtml/jgjhjdcx', '208', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('333', '存款业绩分配监控', '/module/parser/query/visitHtml/ckyjfpjk', '210', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('334', '贷款业绩分配监控', '/module/parser/query/visitHtml/dkyjfpjk', '210', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('335', '预约业绩管理', '/module/parser/manage/visitHtml/yyckyj', '211', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('336', '预约记录核对', '/module/parser/query/visitHtml/yyjlhd', '211', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('347', '柜员补偿业务量管理', '/module/parser/manage/visitHtml/gybcywlgl', '213', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('348', '柜员手工业务量管理', '/module/parser/manage/visitHtml/gysgywlgl', '213', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('349', '业务量折算系数管理', '/module/parser/manage/visitHtml/gyywlzsxsgl', '213', '2', '1', 'C1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('350', '后台柜员折算系数管理', '/module/parser/manage/visitHtml/htgyzsxsgl', '213', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('351', '交易代码管理', '/module/parser/manage/visitHtml/jydmgl', '213', '2', '1', 'A4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('352', '地区差异系数管理', '/module/parser/manage/visitHtml/dqcyxsgl', '225', '2', '1', 'A5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('353', '贷款五级分类导入', '/module/parser/import/visitHtml/dkwjfldr', '596', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('354', '机构费用管理', '/module/parser/manage/visitHtml/jgfygl', '596', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('355', '机构定编人数管理', '/module/parser/manage/visitHtml/jgdbrsgl', '596', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('357', '机构业务量汇总查询', '/module/parser/query/visitHtml/jgywlhzcx', '215', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('358', '机构业务量明细查询', '/module/parser/query/visitHtml/jgywlmxcx', '215', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('359', '柜员业务量汇总查询', '/module/parser/query/visitHtml/gyywlhzcx', '215', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('360', '柜员业务量明细查询', '/module/parser/query/visitHtml/gyywlmxcx', '215', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('361', '个人业绩查询', '/module/parser/query/visitHtml/gryjcx', '216', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('362', '机构业绩查询', '/module/parser/query/visitHtml/jgyjcx', '216', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('363', '个人存贷业绩明细', '/module/parser/query/visitHtml/gryjmx', '217', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('364', '跨机构存贷业绩明细', '/module/parser/query/visitHtml/kjgyjmx', '217', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('390', '机构对比分析', '/module/parser/query/visitHtml/jgdbfx', '219', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('391', '机构趋势分析', '/module/parser/query/visitHtml/jgqsfx', '219', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('392', '机构宏观分析', '/module/parser/query/visitHtml/jghgfx', '219', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('393', '行员对比分析', '/module/parser/query/visitHtml/hydbfx', '219', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('394', '行员趋势分析', '/module/parser/query/visitHtml/hyqsfx', '219', '2', '1', 'A4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('395', '行员宏观分析', '/module/parser/query/visitHtml/hyhgfx', '219', '2', '1', 'A5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('396', '行员业绩报表', '/module/parser/query/visitHtml/hyyjbb', '220', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('397', '机构业绩报表', '/module/parser/query/visitHtml/jgyjbb', '220', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('415', '机构考核角色管理', '/module/parser/manage/visitHtml/jgkhjsgl', '222', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('416', '行员考核角色管理', '/module/parser/manage/visitHtml/hykhjsgl', '222', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('417', '岗位信息管理', '/module/parser/manage/visitHtml/gwxxgl', '222', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('426', '机构考核指标管理', '/module/parser/manage/visitHtml/jgkhzbgl', '224', '2', '1', 'C4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('427', '行员考核指标管理', '/module/parser/manage/visitHtml/hykhzbgl', '224', '2', '1', 'C5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('428', '机构计分系数管理', '/module/parser/manage/visitHtml/jgkhzbxsgl', '225', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('429', '机构工资系数管理', '/module/parser/manage/visitHtml/jggzxsgl', '225', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('430', '行员计分系数管理', '/module/parser/manage/visitHtml/hyjfxsgl', '225', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('431', '行员工资系数管理', '/module/parser/manage/visitHtml/hykhzbxsgl', '225', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('432', '地区业务差异系数管理', '/module/parser/manage/visitHtml/dqywcyxsgl', '225', '2', '1', 'A4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('433', '机构业务差异系数管理', '/module/parser/manage/visitHtml/jgywcyxsgl', '225', '2', '1', 'A5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('436', '虚拟行员绩效工资分配', '/module/parser/manage/visitHtml/xnhyfpjxgzgl', '546', '2', '1', 'B0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('437', '机构考核得分查询', '/module/parser/query/visitHtml/jgkhdfcx', '227', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('439', '机构绩效工资查询', '/module/parser/query/visitHtml/jgjxgzcx', '227', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('440', '行员绩效工资查询', '/module/parser/query/visitHtml/hyjxgzcx', '227', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('441', '机构考核得分明细查询', '/module/parser/query/visitHtml/jgkhdfcxmx', '227', '2', '1', 'A4', '1', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('442', '行员考核得分查询', '/module/parser/query/visitHtml/hykhdfcx', '227', '2', '1', 'A5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('443', '机构绩效工资明细查询', '/module/parser/query/visitHtml/jgjxgzcx', '227', '2', '1', 'A6', '1', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('444', '行员绩效工资明细查询', '/module/parser/query/visitHtml/hyjxgzcxmx', '227', '2', '1', 'A7', '1', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('451', '不良贷款扣罚明细查询', '/module/parser/query/visitHtml/bldkkfmxcx', '229', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('452', '贷款客户管理明细查询', '/module/parser/query/visitHtml/dkkhglmxcx', '229', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('454', '贷款欠息扣罚明细查询', '/module/parser/query/visitHtml/dkqxkfmxcx', '229', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('458', '不良贷款清收奖励管理', '/module/parser/manage/visitHtml/bldkqsjlgl', '229', '2', '1', 'A7', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('459', '机构等级评定查询', '/module/parser/query/visitHtml/jgdjpdcx', '230', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('460', '行员等级评定查询', '/module/parser/query/visitHtml/hydjpdcx', '230', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('461', '等级评定标准查询', '/module/parser/query/visitHtml/djpdbzcx', '230', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('462', '机构等级评定调整', '/module/parser/manage/visitHtml/jgdjpdtz', '230', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('463', '行员等级评定调整', '/module/parser/manage/visitHtml/hydjpdtz', '230', '2', '1', 'A4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('464', '存款准备金管理', '/module/parser/manage/visitHtml/ckzbj', '231', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('472', '机构利润分析', '/module/parser/query/visitHtml/cdkjglrfx', '233', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('473', '产品利润分析', '/module/parser/query/visitHtml/cdkcplrfx', '233', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('474', '期限利润分析', '/module/parser/query/visitHtml/cdkqxlrfx', '233', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('475', '客户利润分析', '/module/parser/query/visitHtml/cdkkhlrfx', '233', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('476', '行员利润分析', '/module/parser/query/visitHtml/cdkhylrfx', '233', '2', '1', 'A4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('477', '存贷款个人利润明细', '/module/parser/query/visitHtml/cdkgrlrmx', '233', '2', '1', 'A5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('478', '存贷款账户利润明细', '/module/parser/query/visitHtml/cdkzhlrmx', '233', '2', '1', 'A6', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('500', '风险金返还管理', '/module/fxjfhgl/index', '229', '2', '1', 'B1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('501', '考核方案配置', '/module/khfa/index', '224', '2', '1', 'C1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('502', '公式数据源配置', '/module/gssjypz/index', '224', '2', '1', 'C2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('503', '公式算法模板管理', '/module/gssfmbgl/index', '224', '2', '1', 'C3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('504', '指标配置', '/module/zbpz/index', '10', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('505', '指标数据源配置', '/module/zbpzsjy/index', '10', '2', '1', 'B2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('506', '机构考核得分管理', '/module/jgkhdfgl/index', '546', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('507', '行员考核得分管理', '/module/hykhdfgl/index', '546', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('508', '机构绩效工资管理', '/module/jgjxgzgl/index', '546', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('509', '行员绩效工资管理', '/module/hyjxgzgl/index', '546', '2', '1', 'A4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('510', '存贷款业绩分配', '/module/hyzhgxgl/index/1.html', '212', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('511', '存贷款业绩批量导入', '/module/hyyjgxdr/index/1.html', '212', '2', '1', 'C13', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('524', '贷款责任业绩关系导入管理', '/module/yjdr/index/dkzryjdr.html', '212', '2', '1', 'C24', '1', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('526', '系统参数管理', '/module/xtcs/index', '3', '2', '1', 'B99', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('530', '系统使用情况分析', '/module/parser/query/visitHtml/xtsyqktj', '3', '2', '1', 'C4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('534', '贷款责任业绩明细', '/module/parser/query/visitHtml/dkzryjmx', '217', '2', '1', 'C4', '1', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('535', '贷款责任业绩分配日志', '/module/parser/query/visitHtml/dkzryjfpcx', '218', '2', '1', 'C4', '1', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('536', '存贷款业绩分配日志', '/module/parser/query/visitHtml/cdkyjfprz', '218', '2', '1', 'C4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('537', '存贷款业绩关系转移', '/module/yjgxzy/index', '212', '2', '1', 'B1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('538', '等级评定配置', '/module/djpdpz/index', '230', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('542', 'PAS管家', '#', '2', '1', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('543', '调度管理', '/module/ddManager/toDdManagerPage.html', '542', '2', '1', 'A0', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('544', '任务管理', '/module/plan/toPlanPage', '542', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('545', '程序管理', '/module/program/toProgramPage.html', '542', '2', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('546', '应用结果管理', '#', '26', '1', '1', 'A4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('549', '行员管理查询', '/module/hygl/list', '4', '3', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('550', '行员管理增加', '/module/hygl/toAdd', '4', '3', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('552', '行员管理重置密码', '/module/hygl/reset', '4', '3', '1', 'A4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('554', '行员管理编辑', '/module/hygl/toEdit', '4', '3', '1', 'A5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('558', '机构增加与编辑', '/module/jggl/toAdd', '6', '3', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('559', '机构注销', '/module/jggl/deljggl', '6', '3', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('560', '机构查询', '/module/jggl/list', '6', '3', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('561', '存款业绩审批情况', '/module/ckyjspqk/index', '588', '2', '1', 'A3', '1', null, null, '1', null);
INSERT INTO `xtb_xtzycd` VALUES ('562', '存款业绩审批', '/module/ckyjsp/index', '588', '2', '1', 'A2', '1', null, null, '1', null);
INSERT INTO `xtb_xtzycd` VALUES ('563', '安全角色查询', '/module/aqjs/list', '5', '3', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('564', '安全角色编辑', '/module/aqjs/loadAqjs', '5', '3', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('565', '安全角色删除', '/module/aqjs/delete', '5', '3', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('566', '新增各类角色', '/module/aqjs/toAdd', '5', '3', '1', 'A4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('567', '安全角色分配', '/module/aqjs/toAuth', '5', '3', '1', 'A5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('568', '系统菜单编辑与增加', '/module/xtcd/toAdd', '7', '3', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('569', '系统菜单删除', '/module/xtcd/delete', '7', '3', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('570', '系统菜单查询', '/module/xtcd/list', '7', '3', '1', 'A3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('571', '系统菜单分配', '/module/xtcd/toAuth', '7', '3', '1', 'A4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('572', '系统菜单同步', '/module/xtcd/toAssign', '7', '3', '1', 'A5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('583', '跑数日志查询', '/module/parser/query/visitHtml/psrz', '3', '2', '1', 'C4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('585', '参数管理', '#', '2', '1', '1', 'B3', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('586', '通用参数管理', '/module/parser/manage/visitHtml/tycsgl', '585', '2', '1', 'C4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('587', '存款账户基数调整', '/module/parser/manage/visitHtml/ckzhjstz', '23', '2', '1', 'C4', '1', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('588', '业绩审批', '#', '14', '1', '1', 'A4', '1', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('591', '卡业绩明细', '/module/parser/query/visitHtml/kyjmxcx', '217', '2', '1', 'C6', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('593', '系统参数编辑', '/module/xtcs/toEdit', '526', '3', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('594', '系统参数查询', '/module/xtcs/list', '526', '3', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('596', '手工台账', '#', '14', '1', '1', 'B5', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('602', '行员注销', '/module/hygl/delete', '4', '3', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('604', '柜员业务量明细查询', '/module/parser/query/visitHtml/gyywlmxcx.xml', '213', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('610', '节假日管理', '/module/jjrcsgl/index', '3', '2', '1', 'B2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('622', '卡业绩分配', '/module/hykgxgl/index.html', '212', '2', '1', 'A50', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('623', '卡业绩批量导入', '/module/yjdr/index/kyjgxdr.html', '212', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('624', '基金业绩导入', '/module/yjdr/index/jjyjdr.html', '212', '2', '1', 'A2', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('625', '理财业绩导入', '/module/yjdr/index/lcyjdr.html', '212', '2', '1', 'A11', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('626', '保险业绩导入', '/module/yjdr/index/bxyjdr.html', '212', '2', '1', 'A13', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('627', '第三方存管业绩导入', '/module/yjdr/index/dsfcgyjdr.html', '212', '2', '1', 'A14', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('628', 'POS商户业绩导入', '/module/yjdr/index/posshyjdr.html', '212', '2', '1', 'A15', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('629', '手机银行业绩导入', '/module/yjdr/index/sjyhyjdr.html', '212', '2', '1', 'A16', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('630', '网上银行业绩导入', '/module/yjdr/index/wsyhyjdr.html', '212', '2', '1', 'A17', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('631', '国际业务业绩导入', '/module/yjdr/index/gjywyjdr.html', '212', '2', '1', 'A17', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('632', '代发工资业绩导入', '/module/yjdr/index/dfgzyjdr.html', '212', '2', '1', 'A18', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('633', '科目总账管理', '/module/kmzzgl/index', '3', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('634', '短信通业绩导入', '/module/yjdr/index/dxtyjpldr.html', '212', '2', '1', 'A19', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('635', 'POS商户业绩分配日志', '/module/parser/query/visitHtml/posshyjfprz', '218', '2', '1', 'A11', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('636', '执行利率管理', '/module/parser/manage/visitHtml/zxllgl', '231', '2', '1', 'A10', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('637', 'FTP价格分析', '/module/parser/query/visitHtml/ftpjgfx', '233', '2', '1', 'A11', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('638', '个人桌面APP', '/module/parser/manage/visitHtml/grzmapp', '3', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('639', '工资体系', '#', '26', '1', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('640', '工资项配置', '/module/gztx/hygzxpz/index.html', '639', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('641', '行员工资查询', '/module/parser/query/visitHtml/hygzcx', '227', '2', '1', 'C4', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('642', '履职绩效明细查询', '/module/parser/query/visitHtml/lzjxmxcx', '227', '2', '1', 'A1', '0', null, null, '0', null);
INSERT INTO `xtb_xtzycd` VALUES ('650', '测试', '#', '1', '0', '1', 'A', '0', null, null, '0', '#');

-- ----------------------------
-- Table structure for `xtb_yhaqjs`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_yhaqjs`;
CREATE TABLE `xtb_yhaqjs` (
  `KHDXDH` int(11) NOT NULL,
  `JSDH` int(11) NOT NULL,
  `DQJSBZ` int(11) DEFAULT NULL,
  `ZXZT` varchar(1) DEFAULT NULL,
  `ZXRQ` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_yhaqjs
-- ----------------------------
INSERT INTO `xtb_yhaqjs` VALUES ('0', '1', '1', 'N', '29991231');

-- ----------------------------
-- Table structure for `xtb_yyrz`
-- ----------------------------
DROP TABLE IF EXISTS `xtb_yyrz`;
CREATE TABLE `xtb_yyrz` (
  `SJDH` int(11) NOT NULL AUTO_INCREMENT,
  `SJSJ` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `XMDH` int(11) DEFAULT NULL,
  `SJLX` varchar(1) DEFAULT NULL,
  `SJMS` varchar(1000) DEFAULT NULL,
  `HYDH` varchar(12) DEFAULT NULL,
  `BZ` varchar(255) DEFAULT NULL,
  `fhdh` varchar(20) DEFAULT NULL,
  `xmmc` varchar(200) DEFAULT NULL,
  `jgmc` varchar(200) DEFAULT NULL,
  `hymc` varchar(100) DEFAULT NULL,
  `jgdh` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`SJDH`)
) ENGINE=InnoDB AUTO_INCREMENT=25100 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xtb_yyrz
-- ----------------------------
