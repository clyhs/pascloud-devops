package com.pas.cloud.studio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.CDATA;
import org.jdom.Element;

import com.google.gson.Gson;

public class DataSource implements Serializable {
	// {"name":"新数据源1","tables":[{"tname":"XTB_AQJS","name":"AQJS"},{"tname":"XTB_DD","name":"DD"}],"selects":[{"tname":"XTB_AQJS","cname":"JSDH","name":"AQJS"},{"tname":"XTB_AQJS","cname":"JSMC","name":"AQJS"},{"tname":"XTB_DD","cname":"DDDH","name":"DD"},{"tname":"XTB_DD","cname":"DDMC","name":"DD"},{"tname":"XTB_DD","cname":"DDMS","name":"DD"},{"tname":"XTB_DD","cname":"QSJD","name":"DD"}],
	// "wheres":[{"sname":"DD","scname":"FHDH","logic":"=","tname":"DD","tcname":"DDLB"}],"groupbys":[],"havings":[],"orderbys":[]}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2838879640605546375L;

	private String name;

	private List<Tables> tables;

	private List<Selects> selects;

	private List<Wheres> wheres;

	private List<Groupbys> groupbys;

	private List<Havings> havings;

	private List<Orderbys> orderbys;

	private String unlockSql;

	public String getUnlockSql() {
		return unlockSql;
	}

	public void setUnlockSql(String unlockSql) {
		this.unlockSql = unlockSql;
	}

	private String getTablesSql() {
		String sql = "";
		// {tname,name}
		Tables idxT = getIndexTable();// 第一个主表
		sql = " from " + idxT.getTname() + ' ' + idxT.getName();
		for (int i = 0; i < tables.size(); i++) {
			if (idxT.getName() != tables.get(i).getName()) {
				sql += "\n " + tables.get(i).getJoinType() + " join "
						+ tables.get(i).getTname() + " "
						+ tables.get(i).getName() + " "
						+ getTableJoinCon(idxT, tables.get(i));
			}
		}
		return sql == "" ? "" : '\n' + sql + " ";
	}

	// 返回第一个主表
	private Tables getIndexTable() {
		for (int i = 0; i < tables.size(); i++) {
			if (tables.get(i).getJoinType().equals("inner")) {
				return tables.get(i);
			}
		}
		return null;
	}

	// 返回表关联条件 主表 从表
	private String getTableJoinCon(Tables main, Tables other) {
		String sql = "ON";
		for (int i = 0; i < wheres.size(); i++) {
			if (wheres.get(i).getIst2t() != null) {// 是表关联
				// sname,scname，logic,tname,tcname,isvalue
				if ((wheres.get(i).getSname() == other.getName())
						|| (wheres.get(i).getSname() == main.getName() && wheres
								.get(i).getTname() == other.getName())) {
					if (sql.length() > 2) {
						sql += " and " + wheres.get(i).getSname() + "."
								+ wheres.get(i).getScname()
								+ wheres.get(i).getLogic()
								+ wheres.get(i).getTname() + "."
								+ wheres.get(i).getTcname();
					} else {
						sql += ' ' + wheres.get(i).getSname() + "."
								+ wheres.get(i).getScname()
								+ wheres.get(i).getLogic()
								+ wheres.get(i).getTname() + "."
								+ wheres.get(i).getTcname();
					}
				}
			}
		}
		if (sql == "ON")
			return "";
		return sql;
	}

	private String getSelectsSql() {
		String sql = "";
		for (int i = 0; i < selects.size(); i++) {
			if (i != 0) {
				sql += ",";
			}
			sql += selects.get(i).getName() + "." + selects.get(i).getCname()
					+ " " + selects.get(i).getName() + "_"
					+ selects.get(i).getCname() + " ";
		}
		return sql == "" ? "SELECT * " : "SELECT " + sql;
	}

	private void getWheresSql(Element sqlPanel) {
		String sql = "WHERE 1=1 ";
		sqlPanel.addContent(sql);
		sql = "";
		for (int i = 0; i < wheres.size(); i++) {
			if (wheres.get(i).getIsvalue() != null) {// 常量
				if (i != 0 && wheres.get(i).getIsbindSearchCon() == null) {
					sql = "AND ";
					sqlPanel.addContent(sql);
				}
				if (wheres.get(i).getLogic().indexOf("like") != -1) {
					if (wheres.get(i).getLogic().equals("alike")) {
						sql += wheres.get(i).getSname() + '.'
								+ wheres.get(i).getScname() + " LIKE " + "%"
								+ wheres.get(i).getIsvalue() + "% ";
					} else if (wheres.get(i).getLogic().equals("llike")) {
						sql += wheres.get(i).getSname() + '.'
								+ wheres.get(i).getScname() + " LIKE " + "%"
								+ wheres.get(i).getIsvalue() + " ";
					} else if (wheres.get(i).getLogic().equals("rlike")) {
						sql += wheres.get(i).getSname() + '.'
								+ wheres.get(i).getScname() + " LIKE " + ""
								+ wheres.get(i).getIsvalue() + "% ";
					}
				} else {
					sql = wheres.get(i).getSname() + "."
							+ wheres.get(i).getScname()
							+ wheres.get(i).getLogic()
							+ wheres.get(i).getIsvalue() + " ";
				}
			} else if (wheres.get(i).getIsbetween() != null) {// bwtween
				if (i != 0 && wheres.get(i).getIsbindSearchCon() == null) {
					sql = "AND ";
					sqlPanel.addContent(sql);
				}
				sql = wheres.get(i).getSname() + "."
						+ wheres.get(i).getScname() + " bwtween "
						+ wheres.get(i).getTname() + "."
						+ wheres.get(i).getTcname() + " and "
						+ wheres.get(i).getBtname() + "."
						+ wheres.get(i).getBtcname();
			} else if (wheres.get(i).getIsglobal() != null) {
				if (i != 0 && wheres.get(i).getIsbindSearchCon() == null) {
					sql = "AND ";
					sqlPanel.addContent(sql);
				}
				sql = "#" + wheres.get(i).getGlobalname() + "# bwtween "
						+ wheres.get(i).getTname() + "."
						+ wheres.get(i).getTcname() + " and "
						+ wheres.get(i).getBtname() + "."
						+ wheres.get(i).getBtcname();
			} else if (wheres.get(i).getIsbindSearchCon() != null) {
				if (i != 0 && wheres.get(i).getIsbindSearchCon() == null) {
					sql = "AND ";
					sqlPanel.addContent(sql);
				}
				Element isEq = new Element("isNotEmpty");
				isEq.setAttribute("prepend", "AND ");
				isEq.setAttribute("property", wheres.get(i).getSearchConName());
				// sql ="'<'isNotEmpty prepend=\"AND\"
				// property=\""+wheres.get(i).getSearchConName()+"\"'>'";
				if (wheres.get(i).getLogic().indexOf("like") != -1) {
					if (wheres.get(i).getLogic().equals("alike")) {
						sql += wheres.get(i).getSname() + '.'
								+ wheres.get(i).getScname() + " LIKE " + "'%$"
								+ wheres.get(i).getSearchConName() + "$%' ";
					} else if (wheres.get(i).getLogic().equals("llike")) {
						sql += wheres.get(i).getSname() + '.'
								+ wheres.get(i).getScname() + " LIKE " + "'%$"
								+ wheres.get(i).getSearchConName() + "$' ";
					} else if (wheres.get(i).getLogic().equals("rlike")) {
						sql += wheres.get(i).getSname() + '.'
								+ wheres.get(i).getScname() + " LIKE " + "'$"
								+ wheres.get(i).getSearchConName() + "$%' ";
					}
				} else {
					sql = wheres.get(i).getSname() + "."
							+ wheres.get(i).getScname()
							+ wheres.get(i).getLogic() + "#"
							+ wheres.get(i).getSearchConName() + "# ";
				}
				// sql ="'<'/isNotEmpty'>'";
				isEq.addContent(sql);
				sqlPanel.addContent(isEq);
				sql = "";
			} else {// 表关联
				// sql +=
				// wheres.get(i).getSname()+"."+wheres.get(i).getScname()+wheres.get(i).getLogic()+wheres.get(i).getTname()+"."+wheres.get(i).getTcname()+"
				// ";
			}
			sqlPanel.addContent(sql);
		}
		// return sql==""?"":"\nwhere " + sql;
	}

	private String getWheresSql() {
		String sql = "";
		for (int i = 0; i < wheres.size(); i++) {
			if (i != 0 && wheres.get(i).getIsbindSearchCon() != null) {
				sql += "AND ";
			}
			if (wheres.get(i).getIsvalue() != null) {// 常量 like
				sql += wheres.get(i).getSname() + "."
						+ wheres.get(i).getScname() + wheres.get(i).getLogic()
						+ wheres.get(i).getIsvalue() + " ";
			} else if (wheres.get(i).getIsbetween() != null) {// bwtween
				sql += wheres.get(i).getSname() + "."
						+ wheres.get(i).getScname() + " BETWEEN "
						+ wheres.get(i).getTname() + "."
						+ wheres.get(i).getTcname() + " AND "
						+ wheres.get(i).getBtname() + "."
						+ wheres.get(i).getBtcname();
			} else if (wheres.get(i).getIsglobal() != null) {// 全局
				sql += "#" + wheres.get(i).getGlobalname() + "# bwtween "
						+ wheres.get(i).getTname() + "."
						+ wheres.get(i).getTcname() + " AND "
						+ wheres.get(i).getBtname() + "."
						+ wheres.get(i).getBtcname();
			} else if (wheres.get(i).getIsbindSearchCon() != null) {// 绑定变量
				sql += "'<'isNotEmpty prepend=\"AND\" property=\""
						+ wheres.get(i).getSearchConName() + "\"'>'";
				sql += wheres.get(i).getSname() + "."
						+ wheres.get(i).getScname() + wheres.get(i).getLogic()
						+ "#" + wheres.get(i).getSearchConName() + "# ";
				sql += "'<'/isNotEmpty'>'";
			} else {// 表关联
				sql += wheres.get(i).getSname() + "."
						+ wheres.get(i).getScname() + wheres.get(i).getLogic()
						+ wheres.get(i).getTname() + "."
						+ wheres.get(i).getTcname() + " ";
			}
		}
		return sql == "" ? "" : "\nWHERE " + sql;
	}

	private String getGroupbysSql() {
		String sql = "";
		for (int i = 0; i < groupbys.size(); i++) {
			if (i != 0) {
				sql += ",";
			}
			sql += groupbys.get(i).getTname() + "."
					+ groupbys.get(i).getCname() + " ";
		}
		return sql == "" ? "" : "\nGROUP BY " + sql;
	}

	private String getHavingsSql() {
		String sql = "";
		for (int i = 0; i < havings.size(); i++) {
			if (i != 0) {
				sql += "AND ";
			}
			if (havings.get(i).getIsvalue() != null) {// 常量
				sql += havings.get(i).getSname() + "."
						+ havings.get(i).getScname()
						+ havings.get(i).getLogic()
						+ havings.get(i).getIsvalue() + " ";
			} else {// 表关联
				sql += havings.get(i).getSname() + "."
						+ havings.get(i).getScname()
						+ havings.get(i).getLogic() + havings.get(i).getTname()
						+ "." + havings.get(i).getTcname() + " ";
			}
		}
		return sql == "" ? "" : "\nHAVING " + sql;
	}

	private String getOrderbysSql() {
		String sql = "";
		for (int i = 0; i < orderbys.size(); i++) {
			if (i != 0) {
				sql += ",";
			}
			sql += orderbys.get(i).getName() + "." + orderbys.get(i).getCname()
					+ " " + orderbys.get(i).getOrderby();
		}
		return sql == "" ? "" : "\nORDER BY " + sql;
	}

	/**/

	public void sqlString2ElementPanel(Element sqlPanel) {
		sqlPanel.addContent(getSelectsSql() + getTablesSql());
		getWheresSql(sqlPanel);
		sqlPanel.addContent(getGroupbysSql() + getHavingsSql()
				+ getOrderbysSql());
	}

	public String toSqlString() {
		return getSelectsSql() + getTablesSql() + getWheresSql()
				+ getGroupbysSql() + getHavingsSql() + getOrderbysSql();
	}
	/**
	 * 处理用户自定义输入的sql
	 * 
	 * @param sqlPanel
	 */
	public void unlockSql2ElementPanel(Element sqlPanel,String deleteOrderBy) {
		String unlockSql = new StringBuffer(this.unlockSql).toString();
		
		unlockSql = formatSql(unlockSql);
		if ("deleteOrderBy".equals(deleteOrderBy)){
			//unlockSql = deleteOrderBy(unlockSql);
		}

		Object[] sc = parseSqlCondition(unlockSql);
		int start = 0;
		for(int i=0;i<sc.length;i++){
			SqlCondition s = (SqlCondition)sc[i];
			//sqlPanel.addContent(new CDATA(unlockSql.substring(start, s.getStart())));
			autoAddCDATA(unlockSql.substring(start, s.getStart()),sqlPanel);
			if(s.getSqlClips().indexOf('$')!=-1){
				Element isEq = new Element("isNotEmpty");
				//isEq.setAttribute("prepend", "AND ");
				isEq.setAttribute("property", getConName(s.getSqlClips()));
				//isEq.addContent(new CDATA(s.getSqlClips()));
				autoAddCDATA(s.getSqlClips(),isEq);
				sqlPanel.addContent(isEq);
			}else{
				autoAddCDATA(s.getSqlClips(),sqlPanel);
			}
			
			start = s.getEnd();
		}

		autoAddCDATA(unlockSql.substring(start, unlockSql.length()),sqlPanel);
	}
	
	
	public void unlockSql2NotParser(Element sqlPanel) {
		String unlockSql = new StringBuffer(this.unlockSql).toString();
		autoAddCDATA(unlockSql,sqlPanel);
		//sqlPanel.addContent(unlockSql);
	}
	
	private void autoAddCDATA(String text,Element panel){
		//<![CDATA[<]]>
		if(text.indexOf('<')!=-1 || text.indexOf('>')!=-1){
			panel.addContent(new CDATA(text));
			return ;
		}

		panel.addContent(text.replaceAll("\r\n", " "));	
	}
	
	

	public static void main(String[] args) {
		Gson gson = new Gson();

		String gsons = "{'name':'新数据源1','tables':[{'tname':'XTB_AQJS','name':'AQJS'},{'tname':'XTB_DD','name':'DD'}],'selects':[{'tname':'XTB_AQJS','cname':'JSDH','name':'AQJS'},{'tname':'XTB_AQJS','cname':'JSMC','name':'AQJS'},{'tname':'XTB_DD','cname':'DDDH','name':'DD'},{'tname':'XTB_DD','cname':'DDMC','name':'DD'},{'tname':'XTB_DD','cname':'DDMS','name':'DD'},{'tname':'XTB_DD','cname':'QSJD','name':'DD'}],'wheres':[{'sname':'DD','scname':'FHDH','logic':'=','tname':'DD','tcname':'DDLB'}],'groupbys':[],'havings':[],'orderbys':[]}";

//		DataSource ds = gson.fromJson(gsons, DataSource.class);

		// System.out.println(ds.getWheres().get(0).getLogic());

		// 假设sql的分隔都是一个空格，并且所有变量前的条件，都是带有1=1的
		String unlockSql = "select JS.JSDH,JS.JSMC,JS.FHDH from XTB_AQJS JS "
				+ "where 1=1 and js.jsmc=#jsmc# and jsmc='abc' and fhdh=#fhdh# and #tjrq# between js.qsrq and js.jsrq and jsmc='abc'";
		/*
		for (int i = 0; i < unlockSql.length(); i++) {
			char c = unlockSql.charAt(i);
			if (c == '#') {// 变量开始
				int s = getBeforeAndIdx(unlockSql, i);
				int e = getNextAndIdx(unlockSql, i + 1);
				//System.out.println(s + ":" + e);
				//System.out.println(unlockSql.substring(s, e));
				i = e;
			}

		}
		*/
		
		DataSource dd = new DataSource();
		String testSql = "select hy.hydh ,hy.hymc from khdx_hy hy,inner join khdx_jgcy cy ON cy.khdxdh=hy.khdxdh where hy.khdxdh=$tjrq$ and hy.hymc='abc'";
		

	}

	/**
	 * 前一个and的位置
	 * 
	 * @param sql
	 * @param idx
	 * @return
	 */
	private static int getBeforeAndIdx(String sql, int idx) {
		String tmp = sql.substring(0, idx);
		return tmp.toUpperCase().lastIndexOf(" AND ");
	}

	/**
	 * 后一个and的位置，between里面的and会有特殊处理
	 * 
	 * @param sql
	 * @param idx
	 * @return
	 */
	private static int getNextAndIdx(String sql, int idx) {
		if (idx >= sql.length())
			return sql.length();
		String tmp = sql.substring(idx, sql.length());// 从当前位置开始，截取到end
		int index = 0;
		int a1 = tmp.toUpperCase().indexOf(" AND ");
		int a2 = tmp.toUpperCase().indexOf(" INNER ");
		int a3 = tmp.toUpperCase().indexOf(" UNION ");// 拿到下一个分隔点，
		try {
			int add = getMinInt(a1, a2, a3);// 取最近的点
			if (add == -1) {// 到sql结尾了，下一个点的位置就是sql的结尾
				index = idx + tmp.length();
			} else {// 没有结尾，拿到下一个点的位置
				index = idx + add;
			}

			if (sql.substring(idx, index).toUpperCase().indexOf("BETWEEN") != -1) {// 如果起始点、到结束点的中间，包含有between
				// sql
				int is = getNextAndIdx(sql, index + 1);
				return is;
			}
			return index;
		} catch (RuntimeException e) {
			// System.out.print("error---");
			// System.out.print(sql);
			// System.out.println(idx);
		}
		return index;
	}

	/**
	 * 返回idx数组中最小的值
	 * 
	 * @param idx
	 * @return
	 */
	private static int getMinInt(int... idx) {
		Arrays.sort(idx);
		return idx[0];
	}

	/**
	 * 拿到##包围的字符串，如#tjrq#，返回tjrq
	 * 
	 * @param sql
	 * @return
	 */
	private static String getConName(String sql) {
		List<Character> paras = new ArrayList<Character>();
		boolean isstart = false;
		for (int i = 0; i < sql.length(); i++) {
			char c = sql.charAt(i);
			if (c == '$' && isstart) {// #结束
				isstart = false;
				return characterList2String(paras).split(":")[0].replaceAll("'", "");
			} else if (c == '$' && !isstart) {
				isstart = true;
				continue;
			}
			if (isstart) {// #开始
				paras.add(c);
			}

		}
		//System.out.println(sql);
		return null;
	}
	
	private String addString2Sql(String sql,String str,int idx){
		StringBuffer sb = new StringBuffer();
		sb.append(sql.substring(0, idx)).append(str).append(sql.substring(idx, sql.length()));
		return sb.toString();
	}

	/**
	 * 格式化用户输入的sql,前转换成大写
	 * 
	 * @param sql
	 * @return
	 */
	private String formatSql(String sql) {
		/*中文注释过滤 start*/
		
		//添加并行格式处理  1-----> /*+ append parallel(zh,8) +*/  把/*+转换成{     +*/转换成}
		sql = sql.replaceAll("/\\*\\+", "\\{").replaceAll("\\+\\*/", "\\}");
		
		Pattern note = Pattern.compile("/\\*.+\\*/",Pattern.CASE_INSENSITIVE);
		Matcher noteMch = note.matcher(sql);
		List<SqlCondition> notelist = new ArrayList<SqlCondition>();
		while(noteMch.find()){
			SqlCondition sc = new SqlCondition(noteMch.start(), noteMch.end(), noteMch.group());
			String key = noteMch.group();
			sc.setEnd(sc.getStart()+sc.getSqlClips().indexOf(key)+key.length());
			notelist.add(sc);
		}
		for(int i=notelist.size()-1;i>=0;i--){
			SqlCondition sc = notelist.get(i);
			sql = sql.substring(0, sc.getStart())+sql.substring(sc.getEnd(), sql.length());
		}
		
		//添加并行格式处理 2-----> /*+ append parallel(zh,8) +*/  把{转换成/*+     }转换成*/
		sql = sql.replaceAll("\\{", "/\\*\\+").replaceAll("\\}", "\\*/");
		
		/*中文注释过滤 finish*/
		
		List<SqlCondition> list = new ArrayList<SqlCondition>();
		Pattern addSql = Pattern.compile("\\s+(where|on)\\s+\\w+\\.\\w+\\s*[=><like]+\\s*['%]*\\$\\w+\\$['%]*",Pattern.CASE_INSENSITIVE);
		Matcher ma = addSql.matcher(sql);
		while (ma.find()) {
			SqlCondition sc = new SqlCondition(ma.start(), ma.end(), ma.group());
			String key = ma.group(1);
			sc.setEnd(sc.getStart()+sc.getSqlClips().indexOf(key)+key.length());
			list.add(sc);
		}
		for(int i=list.size()-1;i>=0;i--){
			SqlCondition sc = list.get(i);
			//if(sc.getSqlClips().indexOf("$")!=-1){//带有变量的，需要做1=1处理
				sql=addString2Sql(sql," 1=1 and ",sc.getEnd());
			//}
		}
		
		return sql;
		/*
		return sql.replaceAll("\\s+", " ")
				.replaceAll("\\s*=\\s*", "=")
				// 替换空格，=号，<号，>号，<>号，!=号
				.replaceAll("\\s*<\\s*", "<").replaceAll("\\s*>\\s*", ">")
				.replaceAll("\\s*<>\\s*", "<>").replaceAll("\\s*!=\\s*", "!=")
				.toLowerCase();
		*/		
	}
	
	/**
	 * 保存时删除sqlCount的order by语句
	 * 
	 *
	 * @param sql
	 * @return
	 */
	private static String deleteOrderBy(String sql){
		//检查sql语句是否存在order by
		int num = sql.toLowerCase().indexOf("order by");
		if (num!=-1){//不存在order by -1则退出
			int i = num + "order by".length();
			//碰到over的，则跳过
			boolean isOver = false;
			//有空再写
			
			boolean isFind = false;
			int zkh = 0;
			while(i<sql.length()&&!isFind){
				if(sql.charAt(i) == '('){
					zkh ++;
				}else if(sql.charAt(i) == ')'){
					if(zkh > 0){
						zkh --;
					}else{
						isFind = true;
						break;
					}
				}
				i++;
			}
			if(isFind){
				sql = sql.substring(0,num)+ sql.substring(i,sql.length());
			}else{
				sql = sql.substring(0,num);
			}
			return deleteOrderBy(sql);
		}else{
			return sql;
		}
	}
	private Object[] parseSqlCondition(String sql) {
		List<SqlCondition> list = new ArrayList<SqlCondition>();
		//and hy.hymc like '%$hymc$%'
		Pattern and = Pattern//匹配 比较符号=<>条件正则  --新增加like判断
				.compile("\\s+(where|and|on)\\s+[\\w(\\w]+\\.[\\w)]+\\s*[=><like]+\\s*['%]*\\$\\w+\\$['%\\w\\d]*",Pattern.CASE_INSENSITIVE);
		Matcher ma = and.matcher(sql);
		while (ma.find()) {
			list.add(new SqlCondition(ma.start(), ma.end(), ma.group()));
		}
		/* --集成到上面的正则中
		Pattern like = Pattern//匹配like正则
				.compile("\\s+(where|and|on)\\s+\\w+\\.\\w+\\s+like\\s+['%]*\\$\\w+\\$['%]*",Pattern.CASE_INSENSITIVE);
		Matcher ml = like.matcher(sql);
		while (ml.find()) {
			list.add(new SqlCondition(ml.start(), ml.end(), ml.group()));
		}
		*/
		
		Pattern in = Pattern//匹配 in 正则
				.compile("\\s+(where|and|on)\\s+\\w+\\.\\w+\\s+in\\s+[\\(']*\\$\\w+\\$['\\)]*",Pattern.CASE_INSENSITIVE);
		Matcher ml = in.matcher(sql);
		while (ml.find()) {
			list.add(new SqlCondition(ml.start(), ml.end(), ml.group()));
		}
		
		Pattern between = Pattern//匹配between正则
				.compile("\\s+(where|and|on)\\s+[\\$\\w:\\.\\w']+\\s+between\\s+[\\$\\w\\.:\\w']+\\s+and\\s+[\\$\\w\\.:\\w']+",Pattern.CASE_INSENSITIVE);
		Matcher mb = between.matcher(sql);
		while (mb.find()) {
			list.add(new SqlCondition(mb.start(), mb.end(), mb.group()));
		}
		Object[] sc = list.toArray();
		Arrays.sort(sc);
		return sc;
	}
	

	/**
	 * 把List里面的Character连接成一个字符串并返回
	 * 
	 * @param paras
	 * @return
	 */
	private static String characterList2String(List<Character> paras) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < paras.size(); i++) {
			sb.append(paras.get(i));
		}
		return sb.toString();
	}
	
	/**
	 * 拿到'$tjrq$'中的变量tjrq和数据类型：varchar，integer
	 * 
	 * @param sql
	 * @return
	 */
	public static String getProConName(String sql,Map<String,String> params) {
		Pattern p = Pattern.compile("([']?\\$.+?\\$[']?)");
		Matcher m = p.matcher(sql);
		while(m.find()){
		    String tempSqlParams=m.group(1);
		    String key=getConName(tempSqlParams);
		    if(params.containsKey(key)){
		    	continue;
		    }else{
		    	if(tempSqlParams.startsWith("'")){
		    		params.put(key, "VARCHAR");
		    	}else{
		    		params.put(key, "INTEGER");
		    	}
		    }
		}
		return sql.replaceAll("([']?\\$.+?\\$[']?)","?");
	}
	public List<Groupbys> getGroupbys() {
		return groupbys;
	}

	public void setGroupbys(List<Groupbys> groupbys) {
		this.groupbys = groupbys;
	}

	public List<Havings> getHavings() {
		return havings;
	}

	public void setHavings(List<Havings> havings) {
		this.havings = havings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Orderbys> getOrderbys() {
		return orderbys;
	}

	public void setOrderbys(List<Orderbys> orderbys) {
		this.orderbys = orderbys;
	}

	public List<Selects> getSelects() {
		return selects;
	}

	public void setSelects(List<Selects> selects) {
		this.selects = selects;
	}

	public List<Tables> getTables() {
		return tables;
	}

	public void setTables(List<Tables> tables) {
		this.tables = tables;
	}

	public List<Wheres> getWheres() {
		return wheres;
	}

	public void setWheres(List<Wheres> wheres) {
		this.wheres = wheres;
	}

	/**
	 * 保存sql的带有参数的条件的位置信息，包含起始位置、sql片断等
	 * 
	 * @author luoxt
	 * 
	 */
	class SqlCondition implements Comparable<SqlCondition> {
		int start;

		int end;

		String sqlClips;

		public SqlCondition(int start, int end, String sqlClips) {
			this.start = start;
			this.end = end;
			this.sqlClips = sqlClips;
		}

		public int getStart() {
			return start;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public String getSqlClips() {
			return sqlClips;
		}

		public void setSqlClips(String sqlClips) {
			this.sqlClips = sqlClips;
		}

		public int compareTo(SqlCondition o) {
			// TODO Auto-generated method stub
			return (this.start > o.getStart() ? 1 : -1);
		}
	}

}
