package manifold.science;

import manifold.science.api.AbstractMeasure;
import manifold.science.util.Rational;

public final class HeatCapacity extends AbstractMeasure<HeatCapacityUnit, HeatCapacity>
{
  public HeatCapacity( Rational value, HeatCapacityUnit unit, HeatCapacityUnit displayUnit )
  {
    super( value, unit, displayUnit );
  }

  public HeatCapacity( Rational value, HeatCapacityUnit unit )
  {
    this( value, unit, unit );
  }

  @Override
  public HeatCapacityUnit getBaseUnit()
  {
    return HeatCapacityUnit.BASE;
  }

  @Override
  public HeatCapacity make( Rational value, HeatCapacityUnit unit, HeatCapacityUnit displayUnit )
  {
    return new HeatCapacity( value, unit, displayUnit );
  }

  @Override
  public HeatCapacity make( Rational value, HeatCapacityUnit unit )
  {
    return new HeatCapacity( value, unit );
  }

  public Energy times( Temperature temperature )
  {
    return new Energy( toBaseNumber() * temperature.toBaseNumber(), EnergyUnit.BASE, getDisplayUnit() * temperature.getDisplayUnit() );
  }
}