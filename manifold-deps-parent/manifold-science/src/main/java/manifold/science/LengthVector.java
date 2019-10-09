/*
 * Copyright (c) 2019 - Manifold Systems LLC
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

package manifold.science;

import manifold.science.util.Rational;

public final class LengthVector extends Vector<Length, LengthUnit, LengthVector>
{
  public LengthVector( Length magnitude, Angle angle )
  {
    super( magnitude, angle );
  }

  @Override
  public LengthVector make( Length magnitude, Angle angle )
  {
    return new LengthVector( magnitude, angle );
  }

  @Override
  public LengthVector copy( Rational magnitude )
  {
    return new LengthVector(
      new Length( magnitude, getMagnitude().getBaseUnit(), getMagnitude().getDisplayUnit() ), getAngle() );
  }
}