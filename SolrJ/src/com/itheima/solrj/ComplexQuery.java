package com.itheima.solrj;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class ComplexQuery {
	@Test
	public void complexQuery() throws Exception {
		SolrServer solrServer = new HttpSolrServer("Http://localhost:8080/solr");
		
		//创建query对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery("钻石");
		
		//过滤条件
		query.setFilterQueries("product_catalog_name:幽默杂货");
		
		//排序条件]
		query.setSort("product_price",ORDER.desc);
		
		//分页处理
		query.setStart(0);
		query.setRows(10);
		
		//结果域中的列表
		query.setFields("id","product_name","product_catalog_name","product_price","product_picture");
		
		//设置默认搜索域
		query.set("df", "product_keywords");
		
		
		//高亮显示
		query.setHighlight(true);
		//高亮显示的域
		query.addHighlightField("product_name");
		//高亮显示的前缀
		query.setHighlightSimplePre("<font color='red'");
		//高亮显示的后缀
		query.setHighlightSimplePost("</font>");
		
		
		//执行查询
		QueryResponse queryResponse = solrServer.query(query);
		
		//获取查询结果
		SolrDocumentList results = queryResponse.getResults();
		
		//共查询dao的商品数量
		long numFound = results.getNumFound();
		System.out.println("共查到 : " +numFound);

		//遍历查询到的结果
		for(SolrDocument result : results){
			System.out.println(result.get("id"));
			
			//取  高亮显示
			String productName = "";
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			
			List<String> list = highlighting.get(result.get("id")).get("product_name");
			//判断是否有高亮的内容
			if(null != list){
				productName = list.get(0);
			}else{
				productName = (String)result.get("product_name");
			}
			System.out.println(productName);
			System.out.println(result.get("product_price"));
			System.out.println(result.get("product_catalog_name"));
			System.out.println(result.get("product_picture"));

			
			
		}
		
		
		
		
		
		
	}
}
