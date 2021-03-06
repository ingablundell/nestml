/*
 * Copyright (c)  RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package org.nest.commons._ast;

import de.monticore.types.types._ast.ASTQualifiedName;
import de.se_rwth.commons.Names;

import java.util.List;

/**
 *  HW extension of the AST classes. Provides method to print function's name.
 *
 * @author plotnikov
 */
public class ASTFunctionCall extends ASTFunctionCallTOP {
  public ASTFunctionCall() {
  }

  public ASTFunctionCall(final ASTQualifiedName name, final List<ASTExpr> argList) {
    super(name, argList);
  }

  /**
   * @return The name of the function which is called.
   */
  public String getCalleeName() {
    return Names.getQualifiedName(getName().getParts());
  }
}
