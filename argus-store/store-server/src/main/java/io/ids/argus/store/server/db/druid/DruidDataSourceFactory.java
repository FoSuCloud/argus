package io.ids.argus.store.server.db.druid;

import com.alibaba.druid.pool.DruidDataSource;
import io.ids.argus.core.base.conf.ArgusLogger;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public class DruidDataSourceFactory implements DataSourceFactory {
    private final ArgusLogger log = new ArgusLogger(DruidDataSourceFactory.class);

    private Properties properties;

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public DataSource getDataSource() {
        var dataSource = new DruidDataSource();
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));

        try {
            dataSource.init();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return dataSource;
    }

}
