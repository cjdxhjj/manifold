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

package manifold.sql.rt.api;

import manifold.rt.api.util.ServiceUtil;
import manifold.util.concurrent.LocklessLazyVar;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public interface TxScopeProvider
{
  LocklessLazyVar<Set<TxScopeProvider>> PROVIDERS =
    LocklessLazyVar.make( () -> {
      Set<TxScopeProvider> registered = new HashSet<>();
      ServiceUtil.loadRegisteredServices( registered, TxScopeProvider.class, TxScopeProvider.class.getClassLoader() );
      return registered;
    } );

  LocklessLazyVar<TxScopeProvider> BY_PRIORITY =
    LocklessLazyVar.make( () ->
      PROVIDERS.get().stream().max( Comparator.comparingInt( TxScopeProvider::getPriority ) )
        .orElseThrow( () -> new IllegalStateException( "No " + TxScopeProvider.class.getSimpleName() + "'s found." ) ) );

  static TxScope newScope( Class<? extends SchemaType> schemaClass )
  {
    return BY_PRIORITY.get().create( schemaClass );
  }


  TxScope create( Class<? extends SchemaType> schemaClass );


  /**
   * Greater = higher priority. Higher priority overrides lower. Default implementations are lowest priority. They can be
   * overridden.
   */
  default int getPriority()
  {
    return Integer.MIN_VALUE;
  }
}
