package com.gilt.flume.elasticsearch;

import org.apache.commons.lang.StringUtils;
import org.apache.flume.Event;
import org.apache.flume.sink.elasticsearch.ContentBuilderUtil;
import org.apache.flume.sink.elasticsearch.ElasticSearchLogStashEventSerializer;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class ElasticSearchLogStashEventSerializerV12 extends ElasticSearchLogStashEventSerializer {

  @Override
  public XContentBuilder getContentBuilder(Event event) throws IOException {
    XContentBuilder builder = jsonBuilder().startObject();
    appendBody(builder, event);
    appendHeaders(builder, event);
    return builder;
  }

  protected void appendBody(XContentBuilder builder, Event event)
      throws IOException {
    byte[] body = event.getBody();
    ContentBuilderUtil.appendField(builder, "message", body);
  }

  protected void appendHeaders(XContentBuilder builder, Event event)
      throws IOException {
    Map<String, String> headers = Maps.newHashMap(event.getHeaders());

    String timestamp = headers.get("timestamp");
    if (!StringUtils.isBlank(timestamp)
        && StringUtils.isBlank(headers.get("timestamp"))) {
      long timestampMs = Long.parseLong(timestamp);
      builder.field("timestamp", new Date(timestampMs));
    }

    for(Map.Entry<String,String> entry: headers.entrySet()) {
      String value = entry.getValue();
      if(StringUtils.isNotBlank(value))
        builder.field(entry.getKey(), value);
    }

    builder.endObject();
  }
}
