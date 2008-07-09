package org.jtv.backend;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jtv.common.RecordingData;
import org.jtv.common.TvRecordingInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class DbTvRecordingInfo implements TvRecordingInfo
{
  
  
  private static final String TABLE_RECORDINGS = "recordings";
  private static final String COL_ID = "id";
  private static final String COL_CHANNEL = "channel";
  private static final String COL_START = "start";
  private static final String COL_END = "end_";
  private static final String COL_NAME = "name";
  
  private static class RecordingRowMapper implements RowMapper
  {

    public Object mapRow(ResultSet rs, int i) throws SQLException
    {
      RecordingData recording = new RecordingData();
      int id = rs.getInt(COL_ID);
      recording.setId(id);
      int channel = rs.getInt(COL_CHANNEL);
      recording.setChannel(channel);
      long start = rs.getTimestamp(COL_START).getTime();
      recording.setStart(start);
      long end = rs.getTimestamp(COL_END).getTime();
      recording.setEnd(end);
      String name = rs.getString(COL_NAME);
      recording.setName(name);
      return recording;
    }
  }
  
  private Logger logger = Logger.getLogger(getClass());
  private DataSource dataSource;
  
  public DbTvRecordingInfo(DataSource dataSource)
  {
    super();
    this.dataSource = dataSource;
    initDb(dataSource);
  }

  private void initDb(DataSource dataSource)
  {
    boolean exists = false;
    try
    {
      DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
      ResultSet rs = metaData.getTables(null, null, null, new String[] {"TABLE"});
      while (rs.next() && !exists)
      {
        String tableName = rs.getString("TABLE_NAME");
        exists = TABLE_RECORDINGS.equalsIgnoreCase(tableName);
      }
      rs.close();
    }
    catch (SQLException sqle)
    {
      logger.error("could not retrieve metadata from db", sqle);
    }
    if (!exists)
    {
      JdbcTemplate sql = new JdbcTemplate(dataSource);
      sql.update("create table " + TABLE_RECORDINGS + " ("
          + COL_ID + " int not null generated always as identity, "
          + COL_CHANNEL + " smallint not null, "
          + COL_START + " timestamp not null, "
          + COL_END + " timestamp not null, "
          + COL_NAME + " varchar(64) not null, "
          + "primary key (" + COL_ID + "))");      
    }
  }
  
  public synchronized SortedSet<RecordingData> getFuture(long from)
  {
    JdbcTemplate sql = new JdbcTemplate(dataSource);
    Object[] params = new Object[] {new Timestamp(from)};
    List<RecordingData> result = sql.query("select * from " + TABLE_RECORDINGS + " where " + COL_START + " >= ?", params, new RecordingRowMapper());
    return new TreeSet<RecordingData>(result);
  }

  public synchronized void scheduleRecording(int channel, long start, long end, String name)
  {
    JdbcTemplate sql = new JdbcTemplate(dataSource);
    Object[] params = new Object[] {channel, new Timestamp(start), new Timestamp(end), name};
    sql.update("insert into " + TABLE_RECORDINGS + "(" + COL_CHANNEL + ", " + COL_START + ", " + COL_END + ", " + COL_NAME + ")" +
            " values (?, ?, ?, ?)", params);
  }

  public void cancelRecordingId(int id)
  {
    JdbcTemplate sql = new JdbcTemplate(dataSource);
    Object[] params = new Object[] {id};
    sql.update("delete from " + TABLE_RECORDINGS + " where " + COL_ID + " = ?", params);
  }
  
  public static void main(String[] args)
  {
    BasicConfigurator.configure();
    EmbeddedDataSource dataSource = new EmbeddedDataSource();
    dataSource.setDatabaseName("TvDb;create=true");
    new DbTvRecordingInfo(dataSource);
  }
  
}
