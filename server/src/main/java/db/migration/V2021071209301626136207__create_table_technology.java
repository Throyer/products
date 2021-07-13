package db.migration;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.*;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V2021071209301626136207__create_table_technology extends BaseJavaMigration {
    public void migrate(Context context) throws Exception {
        var create = using(context.getConnection());
        create.transaction(configuration -> {
            using(configuration)
                .createTableIfNotExists("technology")
                    .column("id", BIGINT.identity(true))
                    .column("name", VARCHAR(100).nullable(false))
                .constraints(primaryKey("id"), unique("name"))
                    .execute();
        });
    }
}
