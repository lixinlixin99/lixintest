package com.itheima.solrj;

import static org.junit.Assert.*;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;

/**
 * SolrJ的
 * 添加 
 * 修改
 * 删除
 * 查询
 * @author 李鑫
 *
 */
public class SolrJDemo {
	//添加
	@Test
	public void testSolrj() throws Exception {
		//两句代码已经连上了Solr服务器
		String baseURL = "http://localhost:8080/solr";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		
		//文档对象
		SolrInputDocument doc = new SolrInputDocument();
		//设置域名域值
		doc.setField("id", "3");
		doc.setField("name", "朱茵");
		
		//添加
		//这里没有update方法 , 因为solr的添加方法和修改方法一样
		solrServer.add(doc);
		
		//需要手动提交
		solrServer.commit();
	}
	//删除
	@Test
	public void testDelete() throws Exception {
		//两句代码已经连上了Solr服务器
		String baseURL = "http://localhost:8080/solr";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		
		//根据id删除
//		solrServer.deleteById("3");
		
		//根据条件删除
		//会根据条件进行分词, 根据分出的词删除, 意思就是只要有一个分词符合就会删除 
//		solrServer.deleteByQuery("name:朱茵");
		solrServer.deleteByQuery("*:*");
		
		solrServer.commit();
	}
	//查询 -- 简单查询
	@Test
	public void testQuery() throws Exception {
		//两句代码已经连上了Solr服务器
		String baseURL = "http://localhost:8080/solr";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		
		//查询条件
		SolrQuery params = new SolrQuery();
		params.set("q", "name:朱");
		
		//执行查询
		QueryResponse response = solrServer.query(params);
		//结果集
		SolrDocumentList results = response.getResults();
		//打印总条数
		long numFound = results.getNumFound();
		System.out.println("总条数: " + numFound);
		
		for (SolrDocument doc : results) {
			System.out.println("id : " + doc.get("id"));
			System.out.println("name : " + doc.get("name"));
		}
		
	}
}
