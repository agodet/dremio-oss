/*
 * Copyright (C) 2017-2019 Dremio Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dremio.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * A connection factory that caches connections based on given {@link com.dremio.jdbc.ConnectionInfo}.
 */
public class MultiConnectionCachingFactory implements CachingConnectionFactory {
  private static final Logger logger = LoggerFactory.getLogger(MultiConnectionCachingFactory.class);

  private final ConnectionFactory delegate;
  private final Map<ConnectionInfo, Connection> cache = Maps.newHashMap();

  public MultiConnectionCachingFactory(ConnectionFactory delegate) {
    this.delegate = delegate;
  }

  /**
   * Creates a {@link com.dremio.jdbc.NonClosableConnection connection} and caches it.
   *
   * The returned {@link com.dremio.jdbc.NonClosableConnection connection} does not support
   * {@link java.sql.Connection#close()}. Consumer must call {#close} to close the cached connections.
   */
  @Override
  public Connection getConnection(ConnectionInfo info) throws Exception {
    Connection conn = cache.get(info);
    if (conn == null) {
      conn = delegate.getConnection(info);
      cache.put(info, conn);
    }
    return new NonClosableConnection(conn);
  }

  /**
   * Closes all active connections in the cache.
   */
  public void closeConnections() throws SQLException {
    for (Connection conn : cache.values()) {
      conn.close();
    }
  }
}
