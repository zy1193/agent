package com.keicei.agent.domain.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.keicei.agent.persistence.mapper.CardMapper;
import com.keicei.util.SequenceUtil;

@Ignore
@ContextConfiguration(locations = { "classpath:spring-jdbc.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class Cvt {

	private static final Logger log = Logger.getLogger(Cvt.class);

	@SuppressWarnings("unused")
	@Resource
	private CardMapper cardMapper;

	@Test
	public void importRecyle() throws IOException {
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"batch.csv");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		List<String> sqls = new ArrayList<String>();
		String sql;
		String sn;
		for (;;) {
			line = reader.readLine();
			if (line != null) {
				line = line.trim();
				String aid, money;
				String[] strs = line.split(",");
				aid = strs[0];
				money = strs[1];
				long amount = 1000 * Long.parseLong(money);
				sn = SequenceUtil.id();
				sql = "";
				sql += "insert into acct_trans_log (acct_id,pay_amount,income_amount,balance,trans_type,trans_sn,trans_time,rmk) "
						+ "select '"
						+ aid
						+ "',0,"
						+ amount
						+ ",available_balance+"
						+ amount
						+ ",'03','"
						+ sn
						+ "',now(),'补销毁卡' from acct where id='"
						+ aid
						+ "';\r\n";
				sql += "update acct set available_balance=available_balance+"
						+ amount + " where id='" + aid + "';\r\n";
				sql += "\r\n";
				sqls.add(sql);
			} else {
				break;
			}
		}
		in.close();

		log.info(sqls.size());

		for (String str : sqls) {

			log.info(str);
		}

	}

	/*
	 * @Test
	 * public void test() {
	 * 
	 * List<String> despwds = cardMapper.nullpwd();
	 * 
	 * Assert.assertNotNull(despwds);
	 * 
	 * Card card;
	 * for (String despwd : despwds) {
	 * card = new Card();
	 * card.setDespwd(despwd);
	 * cardMapper.cvt(card.getPassword(), card.getDespwd());
	 * }
	 * 
	 * }
	 * 
	 * @Test
	 * public void test1() {
	 * List<String> list = cardMapper.z();
	 * System.out.println(list);
	 * 
	 * for (String str : list) {
	 * String agt = cardMapper.x(str);
	 * System.out.println(agt);
	 * cardMapper.y(agt, str);
	 * }
	 * 
	 * }
	 * 
	 * @Test
	 * public void test2() {
	 * List<String> list = cardMapper.s1();
	 * System.out.println(list);
	 * 
	 * for (String str : list) {
	 * String agt = cardMapper.s2(str);
	 * System.out.println(agt);
	 * cardMapper.s3(agt, str);
	 * }
	 * 
	 * }
	 * 
	 * @Test
	 * public void test3() {
	 * List<String> list = cardMapper.handpwd();
	 * 
	 * for (String str : list) {
	 * log.info(
	 * "UPDATE T_W_UserCard set ISACTIVED=1,SACTIVETIME=date_format(NOW(),'%Y%m%d%H%i%s'),SACTIVEENDDATE='20000101',NVALID=1 WHERE CARDPASSWORD=md5('"
	 * + str + "') AND ISACTIVED=0;");
	 * }
	 * 
	 * }
	 * 
	 * @Test
	 * public void test4() {
	 * List<String> list = cardMapper.todel();
	 * for (String str : list) {
	 * log.info(
	 * "UPDATE T_W_UserCard set ISACTIVED=1,SACTIVETIME=date_format(NOW(),'%Y%m%d%H%i%s'),SACTIVEENDDATE='20000101',NVALID=1 WHERE CARDPASSWORD=md5('"
	 * + str + "') AND ISACTIVED=0;");
	 * }
	 * 
	 * }
	 * 
	 * @Test
	 * public void test5() {
	 * List<String> list = cardMapper.tomark();
	 * for (String str : list) {
	 * Card card = new Card();
	 * card.setPassword(str);
	 * String despwd =card.getDespwd();
	 * log.info(
	 * "update card set sale_status='2',recharge_status='1',recharge_time=now(),pay_orderno='系统销卡' where password='"
	 * + str + "' or despwd='" + despwd + "';");
	 * }
	 * 
	 * }
	 *//** 回收入库 **/
	/*
	 * @Test
	 * public void importRecyle() throws IOException {
	 * Set<String> set = new HashSet<String>();
	 * InputStream in = getClass().getClassLoader().getResourceAsStream(
	 * "recyle.txt");
	 * BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	 * StringBuilder buf = new StringBuilder();
	 * buf.append("insert into recyle (pwd) values ");
	 * String line;
	 * for (;;) {
	 * line = reader.readLine();
	 * if (line != null) {
	 * line = line.trim();
	 * if (line.length() == 12) {
	 * set.add(line);
	 * } else if (line.length() < 12) {
	 * log.info(line);
	 * } else {
	 * String[] pwds = line.split(",");
	 * for (String str : pwds) {
	 * str = str.trim();
	 * if (str.length() == 12) {
	 * set.add(str);
	 * } else {
	 * log.info(str);
	 * }
	 * }
	 * }
	 * } else {
	 * break;
	 * }
	 * }
	 * in.close();
	 * for (String pwd : set) {
	 * buf.append("('").append(pwd).append("'),");
	 * }
	 * log.info(buf.toString());
	 * 
	 * }
	 *//** 退卡入库 **/
	/*
	 * @Test
	 * public void importBack() throws IOException {
	 * Set<String> set = new HashSet<String>();
	 * InputStream in = getClass().getClassLoader().getResourceAsStream(
	 * "all_pwd.txt");
	 * BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	 * String sql = "insert into hand (pwd) values ";
	 * String line;
	 * for (;;) {
	 * line = reader.readLine();
	 * if (line == null) {
	 * break;
	 * }
	 * line = line.trim();
	 * if (line.length() == 12) {
	 * set.add(line);
	 * }
	 * }
	 * in.close();
	 * 
	 * List<String> sqls = new ArrayList<String>();
	 * for (String pwd : set) {
	 * sqls.add(sql + "('" + pwd + "');");
	 * }
	 * 
	 * for (String s : sqls) {
	 * log.info(s);
	 * }
	 * }
	 * 
	 * @Test
	 * public void importBack1() throws IOException {
	 * Set<String> set = new HashSet<String>();
	 * InputStream in = getClass().getClassLoader().getResourceAsStream(
	 * "backcardno.txt");
	 * BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	 * String sql = "insert into hand (cardno) values ";
	 * String line;
	 * for (;;) {
	 * line = reader.readLine();
	 * if (line == null) {
	 * break;
	 * }
	 * line = line.trim();
	 * if (line.length() > 0) {
	 * set.add(line);
	 * }
	 * }
	 * in.close();
	 * 
	 * List<String> sqls = new ArrayList<String>();
	 * for (String pwd : set) {
	 * sqls.add(sql + "('" + pwd + "');");
	 * }
	 * 
	 * for (String s : sqls) {
	 * log.info(s);
	 * }
	 * 
	 * }
	 * 
	 * @Test
	 * public void importBack2() throws IOException {
	 * Set<String> set = new HashSet<String>();
	 * InputStream in = getClass().getClassLoader().getResourceAsStream(
	 * "back_pwd.txt");
	 * BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	 * StringBuilder buf = new StringBuilder();
	 * buf.append("insert into hand (pwd) values ");
	 * String line;
	 * for (;;) {
	 * line = reader.readLine();
	 * if (line != null) {
	 * line = line.trim();
	 * if (line.length() != 12) {
	 * continue;
	 * } else {
	 * set.add(line);
	 * }
	 * } else {
	 * break;
	 * }
	 * }
	 * in.close();
	 * 
	 * for (String pwd : set) {
	 * buf.append("('").append(pwd).append("'),");
	 * }
	 * 
	 * log.info(buf.toString());
	 * 
	 * }
	 */

}
