package com.person.chenpt;

import com.alibaba.fastjson.JSON;
import com.person.chenpt.BootStrap;
import com.person.chenpt.entity.User;
import com.person.chenpt.utils.JacksonUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;
import sun.misc.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = BootStrap.class)
class BootStrapTests {
    private static final String ES_INDEX = "cestest2";
    private static final String ES_TYPE = "_doc";

    @Autowired
    @Qualifier("elasticsearchClient")
    private RestHighLevelClient client;

    /**
     * 创建索引
     * @throws IOException
     */
    @Test
    void creatEsIndex() throws Exception {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(ES_INDEX);
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.isAcknowledged());
        System.out.println(createIndexResponse);
        client.close();
    }

    /**
     * 判断索引是否存在
     */
    @Test
    void testIndexIsExists() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(ES_INDEX,ES_TYPE);
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
        client.close();
    }

    /**
     * 删除索引
     */
    @Test
    void deleteEsIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(ES_INDEX);
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
        client.close();
    }

    /**
     * 添加文档
     */
    @Test
    void addEsDoc() throws Exception {
        User user1 = new User("01","test1",18);
        IndexRequest request = new IndexRequest(ES_INDEX,ES_TYPE,user1.getEsId());
        request.source(JacksonUtils.obj2json(user1), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
        System.out.println(response);
    }

    /**
     * 批量添加
     * @throws Exception
     */
    @Test
    void addBatchEsDoc() throws Exception {
        List<User> userLst = new ArrayList<>();
        userLst.add(new User("01","test1",18));
        userLst.add(new User("02","test2",17));
        userLst.add(new User("03","test3",16));
        userLst.add(new User("04","test3",18));
        userLst.add(new User("05","我是安徽人",18));
        userLst.add(new User("06","我是中国人",18));
        BulkRequest bulkRequest = new BulkRequest();
        for(int i=0;i<userLst.size();i++){
            IndexRequest request = new IndexRequest(ES_INDEX,ES_TYPE,userLst.get(i).getEsId());
            request.source(JacksonUtils.obj2json(userLst.get(i)), XContentType.JSON);
            bulkRequest.add(request);
        }
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.status());
    }

    /**
     * 获取es文档信息
     */
    @Test
    void getEsDoc() throws IOException {
        GetRequest getRequest = new GetRequest(ES_INDEX,ES_TYPE,"01");
        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response.isExists());
        System.out.println(response.getSource());
    }

    /**
     * 更新es文档
     */
    @Test
    void updEsDoc() throws Exception {
        UpdateRequest request = new UpdateRequest(ES_INDEX,ES_TYPE,"01");
        User user = new User("01","test",10);
        request.doc(JacksonUtils.obj2json(user),XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 删除es文档
     */
    @Test
    void delEsDoc() throws IOException {
        DeleteRequest request = new DeleteRequest(ES_INDEX,ES_TYPE,"01");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 精准匹配查询
     * SearchRequest 搜索请求
     * SearchSourceBuilder 条件构造
     * SearchSourceBuilder 条件构造
     * TermQueryBuilder 精确查询
     * MatchAllQueryBuilder
     */
    @Test
    void searchTermQueryEsDoc() throws IOException {
        //构建请求对象
        SearchRequest searchRequest = new SearchRequest(ES_INDEX);
        //构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //精准查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("name", "我"));//词语匹配查询
//        boolQueryBuilder.filter(QueryBuilders.fuzzyQuery("name", "test"));//模糊查询
//        boolQueryBuilder.filter(QueryBuilders.rangeQuery("age").gte(18));

        sourceBuilder.query(boolQueryBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        // 5.查看返回结果
        SearchHits hits = response.getHits();
        for (SearchHit documentFields : hits.getHits()) {
            System.out.println(documentFields.getSourceAsString());
        }
    }

    /**
     * 匹配查询
     * SearchRequest 搜索请求
     * SearchSourceBuilder 条件构造
     * SearchSourceBuilder 条件构造
     * TermQueryBuilder 精确查询
     * MatchAllQueryBuilder
     */
    @Test
    void searchMatchAllQueryEsDoc() throws IOException {
        //构建请求对象
        SearchRequest searchRequest = new SearchRequest(ES_INDEX);
        //构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.termQuery("name","test1"));

        sourceBuilder.query(boolQueryBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        // 5.查看返回结果
        SearchHits hits = response.getHits();
        for (SearchHit documentFields : hits.getHits()) {
            System.out.println(documentFields.getSourceAsString());
        }
    }




}
