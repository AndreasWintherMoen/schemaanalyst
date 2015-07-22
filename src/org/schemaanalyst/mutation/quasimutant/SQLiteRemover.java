/*
 */
package org.schemaanalyst.mutation.quasimutant;

import org.schemaanalyst.sqlrepresentation.datatype.*;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Removes those mutant schemas that will be rejected by the SQLite DBMS, but
 * may be accepted by other DBMSs.
 *
 * @author Chris J. Wright
 */
public class SQLiteRemover extends StaticDBMSRemover {

    /*
      Automatically generated by class org.schemaanalyst.util.dbms.DBMSCompatibleDataTypeResolver
     for SQLiteDBMS on Jul 22,2015 09:53

      Map<Class<?>, Set<Class<?>>> compatibleTypes;
    */
    protected void initializeCompatibleTypes() {
        compatibleTypes = new HashMap<>();
        compatibleTypes.put(BigIntDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(BigIntDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(FloatDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(DateDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(NumericDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(IntDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(RealDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(TextDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(CharDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(TimeDataType.class);
        compatibleTypes.get(BigIntDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(BooleanDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(BooleanDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(FloatDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(DateDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(NumericDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(IntDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(RealDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(TextDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(CharDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(TimeDataType.class);
        compatibleTypes.get(BooleanDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(CharDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(CharDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(CharDataType.class).add(FloatDataType.class);
        compatibleTypes.get(CharDataType.class).add(DateDataType.class);
        compatibleTypes.get(CharDataType.class).add(NumericDataType.class);
        compatibleTypes.get(CharDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(CharDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(CharDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(CharDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(CharDataType.class).add(IntDataType.class);
        compatibleTypes.get(CharDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(CharDataType.class).add(RealDataType.class);
        compatibleTypes.get(CharDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(CharDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(CharDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(CharDataType.class).add(TextDataType.class);
        compatibleTypes.get(CharDataType.class).add(CharDataType.class);
        compatibleTypes.get(CharDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(CharDataType.class).add(TimeDataType.class);
        compatibleTypes.get(CharDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(DateDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(DateDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(DateDataType.class).add(FloatDataType.class);
        compatibleTypes.get(DateDataType.class).add(DateDataType.class);
        compatibleTypes.get(DateDataType.class).add(NumericDataType.class);
        compatibleTypes.get(DateDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(DateDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(DateDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(DateDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(DateDataType.class).add(IntDataType.class);
        compatibleTypes.get(DateDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(DateDataType.class).add(RealDataType.class);
        compatibleTypes.get(DateDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(DateDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(DateDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(DateDataType.class).add(TextDataType.class);
        compatibleTypes.get(DateDataType.class).add(CharDataType.class);
        compatibleTypes.get(DateDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(DateDataType.class).add(TimeDataType.class);
        compatibleTypes.get(DateDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(DateTimeDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(DateTimeDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(FloatDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(DateDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(NumericDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(IntDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(RealDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(TextDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(CharDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(TimeDataType.class);
        compatibleTypes.get(DateTimeDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(DecimalDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(DecimalDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(FloatDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(DateDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(NumericDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(IntDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(RealDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(TextDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(CharDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(TimeDataType.class);
        compatibleTypes.get(DecimalDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(DoubleDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(DoubleDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(FloatDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(DateDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(NumericDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(IntDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(RealDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(TextDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(CharDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(TimeDataType.class);
        compatibleTypes.get(DoubleDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(FloatDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(FloatDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(FloatDataType.class).add(FloatDataType.class);
        compatibleTypes.get(FloatDataType.class).add(DateDataType.class);
        compatibleTypes.get(FloatDataType.class).add(NumericDataType.class);
        compatibleTypes.get(FloatDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(FloatDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(FloatDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(FloatDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(FloatDataType.class).add(IntDataType.class);
        compatibleTypes.get(FloatDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(FloatDataType.class).add(RealDataType.class);
        compatibleTypes.get(FloatDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(FloatDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(FloatDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(FloatDataType.class).add(TextDataType.class);
        compatibleTypes.get(FloatDataType.class).add(CharDataType.class);
        compatibleTypes.get(FloatDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(FloatDataType.class).add(TimeDataType.class);
        compatibleTypes.get(FloatDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(IntDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(IntDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(IntDataType.class).add(FloatDataType.class);
        compatibleTypes.get(IntDataType.class).add(DateDataType.class);
        compatibleTypes.get(IntDataType.class).add(NumericDataType.class);
        compatibleTypes.get(IntDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(IntDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(IntDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(IntDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(IntDataType.class).add(IntDataType.class);
        compatibleTypes.get(IntDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(IntDataType.class).add(RealDataType.class);
        compatibleTypes.get(IntDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(IntDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(IntDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(IntDataType.class).add(TextDataType.class);
        compatibleTypes.get(IntDataType.class).add(CharDataType.class);
        compatibleTypes.get(IntDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(IntDataType.class).add(TimeDataType.class);
        compatibleTypes.get(IntDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(MediumIntDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(MediumIntDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(FloatDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(DateDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(NumericDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(IntDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(RealDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(TextDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(CharDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(TimeDataType.class);
        compatibleTypes.get(MediumIntDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(NumericDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(NumericDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(NumericDataType.class).add(FloatDataType.class);
        compatibleTypes.get(NumericDataType.class).add(DateDataType.class);
        compatibleTypes.get(NumericDataType.class).add(NumericDataType.class);
        compatibleTypes.get(NumericDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(NumericDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(NumericDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(NumericDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(NumericDataType.class).add(IntDataType.class);
        compatibleTypes.get(NumericDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(NumericDataType.class).add(RealDataType.class);
        compatibleTypes.get(NumericDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(NumericDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(NumericDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(NumericDataType.class).add(TextDataType.class);
        compatibleTypes.get(NumericDataType.class).add(CharDataType.class);
        compatibleTypes.get(NumericDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(NumericDataType.class).add(TimeDataType.class);
        compatibleTypes.get(NumericDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(RealDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(RealDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(RealDataType.class).add(FloatDataType.class);
        compatibleTypes.get(RealDataType.class).add(DateDataType.class);
        compatibleTypes.get(RealDataType.class).add(NumericDataType.class);
        compatibleTypes.get(RealDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(RealDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(RealDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(RealDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(RealDataType.class).add(IntDataType.class);
        compatibleTypes.get(RealDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(RealDataType.class).add(RealDataType.class);
        compatibleTypes.get(RealDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(RealDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(RealDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(RealDataType.class).add(TextDataType.class);
        compatibleTypes.get(RealDataType.class).add(CharDataType.class);
        compatibleTypes.get(RealDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(RealDataType.class).add(TimeDataType.class);
        compatibleTypes.get(RealDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(SingleCharDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(SingleCharDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(FloatDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(DateDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(NumericDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(IntDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(RealDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(TextDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(CharDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(TimeDataType.class);
        compatibleTypes.get(SingleCharDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(SmallIntDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(SmallIntDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(FloatDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(DateDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(NumericDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(IntDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(RealDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(TextDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(CharDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(TimeDataType.class);
        compatibleTypes.get(SmallIntDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(TextDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(TextDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(TextDataType.class).add(FloatDataType.class);
        compatibleTypes.get(TextDataType.class).add(DateDataType.class);
        compatibleTypes.get(TextDataType.class).add(NumericDataType.class);
        compatibleTypes.get(TextDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(TextDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(TextDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(TextDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(TextDataType.class).add(IntDataType.class);
        compatibleTypes.get(TextDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(TextDataType.class).add(RealDataType.class);
        compatibleTypes.get(TextDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(TextDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(TextDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(TextDataType.class).add(TextDataType.class);
        compatibleTypes.get(TextDataType.class).add(CharDataType.class);
        compatibleTypes.get(TextDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(TextDataType.class).add(TimeDataType.class);
        compatibleTypes.get(TextDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(TimeDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(TimeDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(TimeDataType.class).add(FloatDataType.class);
        compatibleTypes.get(TimeDataType.class).add(DateDataType.class);
        compatibleTypes.get(TimeDataType.class).add(NumericDataType.class);
        compatibleTypes.get(TimeDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(TimeDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(TimeDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(TimeDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(TimeDataType.class).add(IntDataType.class);
        compatibleTypes.get(TimeDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(TimeDataType.class).add(RealDataType.class);
        compatibleTypes.get(TimeDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(TimeDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(TimeDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(TimeDataType.class).add(TextDataType.class);
        compatibleTypes.get(TimeDataType.class).add(CharDataType.class);
        compatibleTypes.get(TimeDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(TimeDataType.class).add(TimeDataType.class);
        compatibleTypes.get(TimeDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(TimestampDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(TimestampDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(FloatDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(DateDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(NumericDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(IntDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(RealDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(TextDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(CharDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(TimeDataType.class);
        compatibleTypes.get(TimestampDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(TinyIntDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(TinyIntDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(FloatDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(DateDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(NumericDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(IntDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(RealDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(TextDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(CharDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(TimeDataType.class);
        compatibleTypes.get(TinyIntDataType.class).add(DecimalDataType.class);
        compatibleTypes.put(VarCharDataType.class, new HashSet<Class<?>>());
        compatibleTypes.get(VarCharDataType.class).add(DateTimeDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(FloatDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(DateDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(NumericDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(TinyIntDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(SmallIntDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(SingleCharDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(BigIntDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(IntDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(MediumIntDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(RealDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(BooleanDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(DoubleDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(TimestampDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(TextDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(CharDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(VarCharDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(TimeDataType.class);
        compatibleTypes.get(VarCharDataType.class).add(DecimalDataType.class);
    }

}
