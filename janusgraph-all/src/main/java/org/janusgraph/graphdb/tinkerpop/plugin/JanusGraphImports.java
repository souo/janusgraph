package org.janusgraph.graphdb.tinkerpop.plugin;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.janusgraph.core.*;
import org.janusgraph.core.attribute.*;
import org.janusgraph.core.schema.*;
import org.janusgraph.example.GraphOfTheGodsFactory;
import org.janusgraph.graphdb.tinkerpop.JanusGraphIoRegistry;
import org.janusgraph.hadoop.MapReduceIndexManagement;
import org.javatuples.Pair;

/**
 * @author souo
 */
public class JanusGraphImports {

    private final static Set<Class> CLASS_IMPORTS = new LinkedHashSet<>();
    private final static Set<Method> METHOD_IMPORTS = new LinkedHashSet<>();
    private final static Set<Enum> ENUM_IMPORTS = new LinkedHashSet<>();

    static {
        /////////////
        // CLASSES //
        /////////////
        CLASS_IMPORTS.add(BaseVertexQuery.class);
        CLASS_IMPORTS.add(Cardinality.class);
        CLASS_IMPORTS.add(EdgeLabel.class);
        CLASS_IMPORTS.add(Idfiable.class);
        CLASS_IMPORTS.add(InvalidElementException.class);
        CLASS_IMPORTS.add(InvalidIDException.class);
        CLASS_IMPORTS.add(JanusGraph.class);
        CLASS_IMPORTS.add(JanusGraphComputer.class);
        CLASS_IMPORTS.add(JanusGraphConfigurationException.class);
        CLASS_IMPORTS.add(JanusGraphEdge.class);
        CLASS_IMPORTS.add(JanusGraphElement.class);
        CLASS_IMPORTS.add(JanusGraphException.class);
        CLASS_IMPORTS.add(JanusGraphFactory.class);
        CLASS_IMPORTS.add(JanusGraphIndexQuery.class);
        CLASS_IMPORTS.add(JanusGraphMultiVertexQuery.class);
        CLASS_IMPORTS.add(JanusGraphProperty.class);
        CLASS_IMPORTS.add(JanusGraphQuery.class);
        CLASS_IMPORTS.add(JanusGraphRelation.class);
        CLASS_IMPORTS.add(JanusGraphTransaction.class);
        CLASS_IMPORTS.add(JanusGraphVertex.class);
        CLASS_IMPORTS.add(JanusGraphVertexProperty.class);
        CLASS_IMPORTS.add(JanusGraphVertexQuery.class);
        CLASS_IMPORTS.add(Multiplicity.class);
        CLASS_IMPORTS.add(Namifiable.class);
        CLASS_IMPORTS.add(PropertyKey.class);
        CLASS_IMPORTS.add(QueryDescription.class);
        CLASS_IMPORTS.add(QueryException.class);
        CLASS_IMPORTS.add(ReadOnlyTransactionException.class);
        CLASS_IMPORTS.add(RelationType.class);
        CLASS_IMPORTS.add(SchemaViolationException.class);
        CLASS_IMPORTS.add(Transaction.class);
        CLASS_IMPORTS.add(TransactionBuilder.class);
        CLASS_IMPORTS.add(VertexLabel.class);
        CLASS_IMPORTS.add(VertexList.class);

        //attribute
        CLASS_IMPORTS.add(AttributeSerializer.class);
        CLASS_IMPORTS.add(Cmp.class);
        CLASS_IMPORTS.add(Contain.class);
        CLASS_IMPORTS.add(Geo.class);
        CLASS_IMPORTS.add(Geoshape.class);
        CLASS_IMPORTS.add(GeoshapeHelper.class);
        CLASS_IMPORTS.add(JtsGeoshapeHelper.class);
        CLASS_IMPORTS.add(Text.class);

        //schema
        CLASS_IMPORTS.add(ConsistencyModifier.class);
        CLASS_IMPORTS.add(DefaultSchemaMaker.class);
        CLASS_IMPORTS.add(EdgeLabelMaker.class);
        CLASS_IMPORTS.add(Index.class);
        CLASS_IMPORTS.add(JanusGraphConfiguration.class);
        CLASS_IMPORTS.add(JanusGraphIndex.class);
        CLASS_IMPORTS.add(JanusGraphManagement.class);
        CLASS_IMPORTS.add(JanusGraphSchemaElement.class);
        CLASS_IMPORTS.add(JanusGraphSchemaType.class);
        CLASS_IMPORTS.add(JobStatus.class);
        CLASS_IMPORTS.add(Mapping.class);
        CLASS_IMPORTS.add(Parameter.class);
        CLASS_IMPORTS.add(PropertyKeyMaker.class);
        CLASS_IMPORTS.add(RelationTypeIndex.class);
        CLASS_IMPORTS.add(RelationTypeMaker.class);
        CLASS_IMPORTS.add(SchemaAction.class);
        CLASS_IMPORTS.add(SchemaInspector.class);
        CLASS_IMPORTS.add(SchemaManager.class);
        CLASS_IMPORTS.add(SchemaStatus.class);
        CLASS_IMPORTS.add(VertexLabelMaker.class);


        CLASS_IMPORTS.add(GraphOfTheGodsFactory.class);
        CLASS_IMPORTS.add(MapReduceIndexManagement.class);
        CLASS_IMPORTS.add(JanusGraphIoRegistry.class);

        //java.time
        CLASS_IMPORTS.add(Instant.class);
        CLASS_IMPORTS.add(Clock.class);
        CLASS_IMPORTS.add(DayOfWeek.class);
        CLASS_IMPORTS.add(Duration.class);
        CLASS_IMPORTS.add(LocalDate.class);
        CLASS_IMPORTS.add(LocalTime.class);
        CLASS_IMPORTS.add(LocalDateTime.class);
        CLASS_IMPORTS.add(Month.class);
        CLASS_IMPORTS.add(MonthDay.class);
        CLASS_IMPORTS.add(OffsetDateTime.class);
        CLASS_IMPORTS.add(OffsetTime.class);
        CLASS_IMPORTS.add(Period.class);
        CLASS_IMPORTS.add(Year.class);
        CLASS_IMPORTS.add(YearMonth.class);
        CLASS_IMPORTS.add(ZonedDateTime.class);
        CLASS_IMPORTS.add(ZoneId.class);
        CLASS_IMPORTS.add(ZoneOffset.class);

        /////////////
        // METHODS //
        /////////////
        uniqueMethods(Geo.class).forEach(METHOD_IMPORTS::add);
        uniqueMethods(Text.class).forEach(METHOD_IMPORTS::add);

        ///////////
        // ENUMS //
        ///////////
        Collections.addAll(ENUM_IMPORTS, Geo.values());
        Collections.addAll(ENUM_IMPORTS, Text.values());
        Collections.addAll(ENUM_IMPORTS, Multiplicity.values());
        Collections.addAll(ENUM_IMPORTS, Cardinality.values());
        Collections.addAll(ENUM_IMPORTS, ChronoUnit.values());
    }

    private JanusGraphImports() {
        // static methods only, do not instantiate class
    }

    public static Set<Class> getClassImports() {
        return Collections.unmodifiableSet(CLASS_IMPORTS);
    }

    public static Set<Method> getMethodImports() {
        return Collections.unmodifiableSet(METHOD_IMPORTS);
    }

    public static Set<Enum> getEnumImports() {
        return Collections.unmodifiableSet(ENUM_IMPORTS);
    }


    /**
     * Filters to unique method names on each class.
     */
    private static Stream<Method> uniqueMethods(final Class<?> clazz) {
        final Set<String> unique = new LinkedHashSet<>();
        return Stream.of(clazz.getMethods())
            .filter(m -> Modifier.isStatic(m.getModifiers()))
            .map(m -> Pair.with(generateMethodDescriptor(m), m))
            .filter(p -> {
                final boolean exists = unique.contains(p.getValue0());
                if (!exists) unique.add(p.getValue0());
                return !exists;
            })
            .map(Pair::getValue1);
    }

    private static String generateMethodDescriptor(final Method m) {
        return m.getDeclaringClass().getCanonicalName() + "." + m.getName();
    }

}
