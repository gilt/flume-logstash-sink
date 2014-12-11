package com.gilt.flume.elasticsearch;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.flume.Event;
import org.apache.flume.sink.elasticsearch.TimeBasedIndexNameBuilder;

import java.util.TimeZone;

public class TimeBasedIndexNameBuilderV12 extends TimeBasedIndexNameBuilder {

  public static final String DEFAULT_DATE_FORMAT = "yyyy.MM.dd";

  private FastDateFormat fastDateFormat = FastDateFormat.getInstance(DEFAULT_DATE_FORMAT,
      TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
  /**
   * Gets the name of the index to use for an index request
   * @param event
   *          Event for which the name of index has to be prepared
   * @return index name of the form 'indexPrefix-formattedTimestamp'
   */
  @Override
  public String getIndexName(Event event) {
    TimestampedEvent timestampedEvent = new TimestampedEvent(event);
    long timestamp = timestampedEvent.getTimestamp();

    return getIndexPrefix(event) + '-' + fastDateFormat.format(timestamp);
  }
}
