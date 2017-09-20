// Copyright 2017 JanusGraph Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.janusgraph.graphdb.tinkerpop.plugin;

import java.util.Optional;
import org.apache.tinkerpop.gremlin.jsr223.Customizer;
import org.apache.tinkerpop.gremlin.jsr223.DefaultImportCustomizer;
import org.apache.tinkerpop.gremlin.jsr223.GremlinPlugin;
import org.apache.tinkerpop.gremlin.jsr223.ImportCustomizer;


/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class JanusGraphGremlinPlugin implements GremlinPlugin {

    private static final ImportCustomizer janusGraph = DefaultImportCustomizer.build()
        .addClassImports(JanusGraphImports.getClassImports())
        .addEnumImports(JanusGraphImports.getEnumImports())
        .addMethodImports(JanusGraphImports.getMethodImports()).create();

    private static final Customizer[] customizers = new Customizer[]{janusGraph};

    private static final JanusGraphGremlinPlugin INSTANCE = new JanusGraphGremlinPlugin();

    private JanusGraphGremlinPlugin(){}

    public static JanusGraphGremlinPlugin instance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "janusgraph.imports";
    }

    @Override
    public Optional<Customizer[]> getCustomizers(String scriptEngineName) {
        return Optional.of(customizers);
    }

    @Override
    public boolean requireRestart() {
        return true;
    }

}
