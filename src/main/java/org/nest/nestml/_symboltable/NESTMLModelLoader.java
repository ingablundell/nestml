/*
 * Copyright (c)  RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package org.nest.nestml._symboltable;

import de.monticore.symboltable.ArtifactScope;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolverConfiguration;
import de.monticore.symboltable.Scope;
import de.se_rwth.commons.Names;
import de.se_rwth.commons.logging.Log;
import org.nest.nestml._ast.ASTNESTMLCompilationUnit;

import static de.se_rwth.commons.logging.Log.debug;

/**
 * Creates symbol table for the {@code NESTMLLanguage} from the parsed model.
 *
 * @author plotnikov
 */
public class NESTMLModelLoader extends NESTMLModelLoaderTOP {

  public NESTMLModelLoader(NESTMLLanguage language) {
    super(language);
  }

  @Override
  protected void createSymbolTableFromAST(
      final ASTNESTMLCompilationUnit ast,
      final String modelName,
      final MutableScope enclosingScope,
      final ResolverConfiguration resolverConfiguration) {
    final String NO_CREATOR = "Register symbol table creator in the language.";
    final NESTMLSymbolTableCreator symbolTableCreator = getModelingLanguage()
        .getSymbolTableCreator(resolverConfiguration, enclosingScope)
        .orElseThrow(() -> new RuntimeException(NO_CREATOR));

    ast.setArtifactName(Names.getSimpleName(modelName));
    ast.setPackageName(Names.getQualifier(modelName));

    if (symbolTableCreator != null) {
      debug("Start creation of symbol table for model \"" + modelName + "\".",
          NESTMLModelLoader.class.getSimpleName());
      final Scope scope = symbolTableCreator.createFromAST(ast);

      if (!(scope instanceof ArtifactScope)) {
        Log.warn("Top scope of model " + modelName + " is expected to be a compilation scope, but"
            + " is scope \"" + scope.getName() + "\"");
      }

      debug("Created symbol table for model \"" + modelName + "\".",
          NESTMLModelLoader.class.getSimpleName());
    }
    else {
      Log.warn("No symbol created, because '" + getModelingLanguage().getName()
          + "' does not define a symbol table creator.");
    }

  }

}
