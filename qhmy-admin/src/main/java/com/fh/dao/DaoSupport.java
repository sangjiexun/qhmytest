package com.fh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.fh.entity.system.Department;
import com.fh.entity.system.Project;
import com.fh.entity.system.User;
/**
 * @author 
 * 修改时间：2015、12、11
 */
@Repository("daoSupport")
public class DaoSupport implements DAO {

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 保存对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object save(String str, Object obj) throws Exception {
		return sqlSessionTemplate.insert(str, obj);
	}
	
	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object batchSave(String str, List objs )throws Exception{
		return sqlSessionTemplate.insert(str, objs);
	}
	
	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object update(String str, Object obj) throws Exception {
		return sqlSessionTemplate.update(str, obj);
	}
	
	

	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void batchUpdate(String str, List objs )throws Exception{
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		try{
			if(objs!=null){
				for(int i=0,size=objs.size();i<size;i++){
					sqlSession.update(str, objs.get(i));
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		}finally{
			sqlSession.close();
		}
	}
	
	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object batchDelete(String str, List objs )throws Exception{
		return sqlSessionTemplate.delete(str, objs);
	}
	
	/**
	 * 删除对象 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object delete(String str, Object obj) throws Exception {
		return sqlSessionTemplate.delete(str, obj);
	}
	 
	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String str, Object obj) throws Exception {
		return sqlSessionTemplate.selectOne(str, obj);
	}

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForList(String str, Object obj) throws Exception {
		return sqlSessionTemplate.selectList(str, obj);
	}
	
	public Object findForMap(String str, Object obj, String key, String value) throws Exception {
		return sqlSessionTemplate.selectMap(str, obj, key);
	}

	@Override
	public List<Project> typeName(String typeName) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeName);
	}

	@Override
	public List<Department> findAll(String findAll) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(findAll);
	}

	@Override
	public List<User> findAll1(String findAll) throws Exception {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(findAll);
	}
	@Override
	public void creatSeq(String str, String value) throws Exception {
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate
				.getSqlSessionFactory();
		
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
//		SqlSession sqlSession = sqlSessionFactory.openSession(
//				ExecutorType.BATCH, false);
		Connection conn = null;
		Statement st = null;
		try {
			String sql = "create sequence "
					+ value
					+ "    minvalue 1   maxvalue 9999   start with 1   increment by 1    order";
			conn = sqlSession.getConnection();
			st = conn.createStatement();
			int count = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("插入数据失败" + e.getMessage());
		} finally {
//			if(st!=null){
//				st.close();
//			}
//			if(conn!=null){
//				conn.close();
//			}
			sqlSession.close();
			
		}
	}
	
	@Override
	public void creatSeq2(String str, String value) throws Exception {
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate
				.getSqlSessionFactory();
		
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
//		SqlSession sqlSession = sqlSessionFactory.openSession(
//				ExecutorType.BATCH, false);
		Connection conn = null;
		Statement st = null;
		try {
			String sql = "create sequence "
					+ value
					+ "    minvalue 1   maxvalue 99999   start with 1   increment by 1    order";
			conn = sqlSession.getConnection();
			st = conn.createStatement();
			int count = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("插入数据失败" + e.getMessage());
		} finally {
//			if(st!=null){
//				st.close();
//			}
//			if(conn!=null){
//				conn.close();
//			}
			sqlSession.close();
			
		}
	}
	
	@Override
	public String getSeq4(String value) throws Exception {
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate
				.getSqlSessionFactory();
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
//		SqlSession sqlSession = sqlSessionFactory.openSession(
//				ExecutorType.BATCH, false);
		
		Connection conn = null;
		Statement st = null;
		 ResultSet res = null;  
		 String rec="";
		try {
			//select SEQ_ZUZHI.nextval as ZZBM from dual 
			String sql = "select lpad("
					+ value
					+ ".nextval,4,'0')   as NEXTBM from dual for update";
			conn = sqlSession.getConnection();
			st = conn.createStatement();
			 res = st.executeQuery(sql);
			 while(res.next())  
	            {  
	                 rec = res.getString("NEXTBM");  
	            }  
		} catch (SQLException e) {
			System.out.println("插入数据失败" + e.getMessage());
			throw new Exception("生成序列出问题了！");
		} finally {
//			if(res!=null){
//				res.close();
//			}
//			if(st!=null){
//				st.close();
//			}
//			if(conn!=null){
//				conn.close();
//			}
			sqlSession.close();
		}
		
		return String.format("%04d", Integer.parseInt(String.valueOf(rec)));

	}
	
	@Override
	public String getSeq(String value) throws Exception {
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate
				.getSqlSessionFactory();
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
//		SqlSession sqlSession = sqlSessionFactory.openSession(
//				ExecutorType.BATCH, false);
		
		Connection conn = null;
		Statement st = null;
		 ResultSet res = null;  
		 String rec="";
		try {
			//select SEQ_ZUZHI.nextval as ZZBM from dual 
			String sql = "select lpad("
					+ value
					+ ".nextval,4,'0')   as NEXTBM from dual ";
			conn = sqlSession.getConnection();
			st = conn.createStatement();
			 res = st.executeQuery(sql);
			 while(res.next())  
	            {  
	                 rec = res.getString("NEXTBM");  
	            }  
		} catch (SQLException e) {
			System.out.println("插入数据失败" + e.getMessage());
		} finally {
//			if(res!=null){
//				res.close();
//			}
//			if(st!=null){
//				st.close();
//			}
//			if(conn!=null){
//				conn.close();
//			}
			sqlSession.close();
		}
		
		return String.format("%04d", Integer.parseInt(String.valueOf(rec)));

	}
	
	@Override
	public String getSeq2(String value) throws Exception {
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate
				.getSqlSessionFactory();
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
//		SqlSession sqlSession = sqlSessionFactory.openSession(
//				ExecutorType.BATCH, false);
		
		Connection conn = null;
		Statement st = null;
		 ResultSet res = null;  
		 String rec="";
		try {
			//select SEQ_ZUZHI.nextval as ZZBM from dual 
			String sql = "select lpad("
					+ value
					+ ".nextval,5,'0')   as NEXTBM from dual ";
			conn = sqlSession.getConnection();
			st = conn.createStatement();
			 res = st.executeQuery(sql);
			 while(res.next())  
	            {  
	                 rec = res.getString("NEXTBM");  
	            }  
		} catch (SQLException e) {
			System.out.println("插入数据失败" + e.getMessage());
		} finally {
//			if(res!=null){
//				res.close();
//			}
//			if(st!=null){
//				st.close();
//			}
//			if(conn!=null){
//				conn.close();
//			}
			sqlSession.close();
		}
		
		return String.valueOf(rec);

	}

	
}


