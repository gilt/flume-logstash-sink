flume-logstash-sink
===================

Flume sink to send logging events to an ES cluster with logstash V2 format

See [this post](http://blog.tpa.me.uk/2013/11/20/logstash-v1-1-v1-2-json-event-layout-format-change/)

To enable the event serializer you need to add this line to the flume sink configuration:

`[agent_name].sinks.[sink_name].indexNameBuilder = com.gilt.flume.elasticsearch.TimeBasedIndexNameBuilderV12`

and set the serializer too:

`[agent_name].sinks.[sink_name].serializer = com.gilt.flume.elasticsearch.ElasticSearchLogStashEventSerializerV12`

You will need to add the jar to your flume lib folder too so that the classes are accessible.