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
package com.dremio.service.autocomplete.catalog;

import org.apache.arrow.util.Preconditions;

/**
 * Mock of CatalogReader
 */
public final class MockCatalogReader extends CatalogReader {
  private final HomeCatalogNode homeCatalogNode;

  public MockCatalogReader(HomeCatalogNode homeCatalogNode) {
    Preconditions.checkNotNull(homeCatalogNode);

    this.homeCatalogNode = homeCatalogNode;
  }


  @Override
  public HomeCatalogNode getHomeCatalogNode() {
    return homeCatalogNode;
  }
}
