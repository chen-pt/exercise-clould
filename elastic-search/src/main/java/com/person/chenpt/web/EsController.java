package com.person.chenpt.web;

import com.person.chenpt.common.Result;
import com.person.chenpt.entity.EsUser;
import com.person.chenpt.entity.Page;
import com.person.chenpt.utils.JacksonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2022-08-01 16:55
 * @Modified By:
 */
@RestController
@RequestMapping("/es")
@Api(tags = "搜索引擎")
public class EsController {
    private static final String es_user_index = "chenpt";
    private static final String es_user_index2 = "test_cloud";
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @ApiOperation("索引创建")
    @GetMapping("/createIndex")
    public Result createIndex(){
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(es_user_index);
        CreateIndexResponse response = null;
        try {
            response = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(response);
    }

    @ApiOperation("索引是否存在")
    @GetMapping("/existIndex")
    public Result existIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(es_user_index);
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        return Result.success(exists);
    }

    @ApiOperation("索引删除")
    @GetMapping("/delIndex")
    public Result delIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(es_user_index);
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return Result.success(delete);
    }

    /**
     * 当es中索引不存在时，会自动添加
     * @throws Exception
     */
    @ApiOperation("添加文档")
    @GetMapping("/createDocument")
    public Result createDocument() throws Exception {
        EsUser user = new EsUser(UUID.randomUUID().toString(),"测试",888,"梦梦");
        IndexRequest request = new IndexRequest("test_cloud");
        request.id(user.getId());
        //将我们的数据放入请求，json
        request.source(JacksonUtils.obj2json(user));
        //客服端发送请求
        IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return Result.success(index);
    }

    @ApiOperation("更新文档")
    @GetMapping("/updateDocument")
    public Result updateDocument() throws Exception {
        EsUser user = new EsUser("12121","测试",18,"梦梦修改");
        UpdateRequest request = new UpdateRequest(es_user_index2,"cKx9XYIBoYJ1eulGHDEK");
        request.doc(JacksonUtils.obj2json(user),XContentType.JSON);
        UpdateResponse index = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        return Result.success(index);
    }

    @ApiOperation("删除文档")
    @GetMapping("/delDocument")
    public Result delDocument() throws Exception {
        DeleteRequest request = new DeleteRequest(es_user_index2,"cKx9XYIBoYJ1eulGHDEK");
        DeleteResponse delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        return Result.success(delete);
    }

    @ApiOperation("批量添加文档")
    @GetMapping("/bulkCreateDocument")
    public Result bulkCreateDocument() throws Exception {
        BulkRequest bulkRequest = new BulkRequest();
        List<EsUser> users = new ArrayList<EsUser>();
        users.add(new EsUser(UUID.randomUUID().toString(), "测试1", 280, "梦梦1"));
        users.add(new EsUser(UUID.randomUUID().toString(), "测试2", 280, "梦梦2"));
        users.add(new EsUser(UUID.randomUUID().toString(), "测试3", 280, "梦梦3"));
        for (EsUser user : users) {
            bulkRequest.add(new IndexRequest(es_user_index2)
                            .id(user.getId())
                            .source(JacksonUtils.obj2json(user),XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return Result.success(bulk);
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageSearch")
    public Result pageSearch(String keyWords,Integer pageNum,Integer pageSize) throws Exception {
        if(pageNum<=1){
            pageNum=1;
        }
        SearchRequest searchRequest = new SearchRequest(es_user_index2);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(pageNum);
        sourceBuilder.size(pageSize);
//        sourceBuilder.query(QueryBuilders.termsQuery("name",keyWords,""));//name字段可以匹配多个词条
        sourceBuilder.query(QueryBuilders.multiMatchQuery(keyWords, "name","remark"));//一个词条匹配多个字段
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red'>").postTags("</span>").field("*");
        sourceBuilder.highlighter(highlightBuilder);
        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //解析结果
        List<Map<String, Object>> maps = new ArrayList<>();
        for (SearchHit hit : search.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            Map<String,Object> map = hit.getSourceAsMap();
            highlightFields.forEach((s, highlightField) -> {
                if (highlightField == null){
                    return;
                }
                // 取得定义的高亮标签
                String texts = StringUtils.join(highlightField.fragments());
                map.put(s,texts);
            });
            maps.add(map);
        }
        return Result.success(new Page(pageNum,pageSize,(int) search.getHits().getTotalHits().value,maps));
    }
}
