package DataBase;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.type.descriptor.jdbc.IntegerJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.SqlTypes;

public class SQLiteDialect extends Dialect {
    public SQLiteDialect() {
        super(DatabaseVersion.make(3, 35)); // Specify your SQLite version here
    }

    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.contributeTypes(typeContributions, serviceRegistry);
        // Register INTEGER and TEXT types for SQLite
        typeContributions.getTypeConfiguration().getJdbcTypeRegistry()
            .addDescriptor(SqlTypes.INTEGER, IntegerJdbcType.INSTANCE);
        typeContributions.getTypeConfiguration().getJdbcTypeRegistry()
            .addDescriptor(SqlTypes.VARCHAR, VarcharJdbcType.INSTANCE);
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new SQLiteIdentityColumnSupport();
    }

    // Inner class for SQLite-specific identity support
    public static class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {
        @Override
        public boolean supportsIdentityColumns() {
            return true;
        }

        @Override
        public String getIdentityColumnString(int type) {
            // SQLite uses INTEGER PRIMARY KEY AUTOINCREMENT for identity columns
            return "integer primary key autoincrement";
        }

        @Override
        public String getIdentityInsertString() {
            return "null";
        }
    }
}
