/*
 * Copyright (c)  RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package org.nest.symboltable.symbols;

import com.google.common.collect.Lists;
import de.monticore.symboltable.CommonSymbol;
import de.monticore.symboltable.SymbolKind;
import org.nest.units.unitrepresentation.UnitRepresentation;

import java.util.Collection;
import java.util.Optional;

/**
 * Represents an ordinary type symbol.
 *
 * @author plotnikov
 */
public class TypeSymbol extends CommonSymbol {
  public final static TypeSymbolKind KIND = new TypeSymbolKind();
  private final Collection<MethodSymbol> builtInMethods = Lists.newArrayList();
  private final Type type;

  public TypeSymbol(final TypeSymbol other) {
    this(other.getName(), other.type);
    builtInMethods.addAll(other.builtInMethods);
  }

  public TypeSymbol(final String name, final Type type) {
    super(name, KIND);
    this.type = type;
  }

  public Type getType() {
    return type;
  }

  public void addBuiltInMethod(final MethodSymbol builtInMethod) {
    builtInMethods.add(builtInMethod);
  }

  public Optional<MethodSymbol> getBuiltInMethod(final String methodName) {
    // TODO signature must be considered
    return builtInMethods.stream().filter(method -> method.getName().equals(methodName)).findFirst();
  }

  @Override
  public String toString() {
    return "TypeSymbol(" + getFullName() + "," + type + ")";
  }

  public String prettyPrint() {
    if (getType().equals(TypeSymbol.Type.UNIT)) {
      UnitRepresentation unitRepresentation =UnitRepresentation.getBuilder().serialization(getName()).build();
      return unitRepresentation.prettyPrint();
    }
    else {
      return getName(); //primitive and buffer
    }

  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    final TypeSymbol other = (TypeSymbol) obj;

    return com.google.common.base.Objects.equal(this.type, other.type)
        && com.google.common.base.Objects.equal(this.getName(), other.getName());
  }

  @Override
  public int hashCode()
  {
    return com.google.common.base.Objects.hashCode(this.getName(), this.type);
  }

  public enum Type { UNIT, PRIMITIVE, BUFFER}

  private static class TypeSymbolKind implements SymbolKind {

    TypeSymbolKind() {
    }

  }


}
