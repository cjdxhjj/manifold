/*
 * Copyright (c) 2023 - Manifold Systems LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package manifold.sql;

import manifold.rt.api.util.StreamUtil;
import manifold.sql.rt.api.ConnectionProvider;
import manifold.sql.schema.simple.ScratchTest;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static manifold.rt.api.util.TempFileUtil.makeTempFile;

public abstract class BaseDbTest
{
  void _setup( String db_resource )
  {
    // Copy database to temp dir, the url in DbConfig uses it from there.
    // Note that "/Runtime" is necessary due to the url's use of #resource,
    // which distinguishes between run-time, compile-time, design-time

    File tempDbFile = makeTempFile( "/Runtime" + db_resource );
    try( InputStream in = ScratchTest.class.getResourceAsStream( db_resource );
         FileOutputStream out = new FileOutputStream( tempDbFile ) )
    {
      StreamUtil.copy( in, out );
    }
    catch( IOException e )
    {
      throw new RuntimeException( e );
    }
  }

  void _cleanup( String db_resource ) throws SQLException
  {
    // close db connections
    ConnectionProvider.PROVIDERS.get().forEach( p -> p.closeAll() );
    ConnectionProvider.PROVIDERS.clear();

    // delete temp db
    if( !makeTempFile( "/Runtime" + db_resource ).delete() )
    {
      throw new RuntimeException( "Could not delete temporary file: " + db_resource );
    }
  }
}
