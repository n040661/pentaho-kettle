/*
 * *****************************************************************************
 *
 *  Pentaho Data Integration
 *
 *  Copyright (C) 2002-2017 by Hitachi Vantara : http://www.pentaho.com
 *
 *  *******************************************************************************
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 *  this file except in compliance with the License. You may obtain a copy of the
 *  License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * *****************************************************************************
 *
 */

package org.pentaho.di.engine.configuration.impl.extension;

import org.pentaho.di.base.AbstractMeta;
import org.pentaho.di.core.attributes.metastore.EmbeddedMetaStore;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.extension.ExtensionPoint;
import org.pentaho.di.core.extension.ExtensionPointInterface;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.engine.configuration.api.RunConfiguration;
import org.pentaho.di.engine.configuration.impl.EmbeddedRunConfigurationManager;
import org.pentaho.di.engine.configuration.impl.RunConfigurationManager;

/**
 * Created by bmorrise on 5/3/17.
 */
@ExtensionPoint( id = "RunConfigurationSaveExtensionPoint", extensionPointId = "JobEntryTransSave",
  description = "" )
public class RunConfigurationSaveExtensionPoint implements ExtensionPointInterface {

  private RunConfigurationManager runConfigurationManager;

  public RunConfigurationSaveExtensionPoint( RunConfigurationManager runConfigurationManager ) {
    this.runConfigurationManager = runConfigurationManager;
  }

  @Override public void callExtensionPoint( LogChannelInterface logChannelInterface, Object o ) throws KettleException {
    AbstractMeta abstractMeta = (AbstractMeta) ( (Object[]) o )[ 0 ];
    String runConfiguration = (String) ( (Object[]) o )[ 1 ];
    final EmbeddedMetaStore embeddedMetaStore = abstractMeta.getEmbeddedMetaStore();

    RunConfigurationManager embeddedRunConfigurationManager =
      EmbeddedRunConfigurationManager.build( embeddedMetaStore );
    embeddedRunConfigurationManager.deleteAll();

    RunConfiguration loadedRunConfiguration = runConfigurationManager.load( runConfiguration );
    embeddedRunConfigurationManager.save( loadedRunConfiguration );
  }

}
