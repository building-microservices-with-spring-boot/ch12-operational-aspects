package com.example.stats;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class HttpConnectionMetrics implements PublicMetrics {

    @Autowired
    PoolingHttpClientConnectionManager connectionManager;

    @Override
    public Collection<Metric<?>> metrics() {
        PoolStats stats = this.connectionManager.getTotalStats();

        Set<Metric<?>> metrics = new HashSet<>();
        metrics.add(new Metric<>("http.connection.pool.leased", stats.getLeased()));
        metrics.add(new Metric<>("http.connection.pool.pending", stats.getPending()));
        metrics.add(new Metric<>("http.connection.pool.available", stats.getAvailable()));
        metrics.add(new Metric<>("http.connection.pool.max", stats.getMax()));
        return metrics;
    }
}
