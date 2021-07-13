package db.migration;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.*;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V2021071209301626136209__create_table_product_technology extends BaseJavaMigration {
    public void migrate(Context context) throws Exception {
        var create = using(context.getConnection());
        create.transaction(configuration -> {
            using(configuration)
                .createTableIfNotExists("product_technology")
                    .column("product_id", BIGINT.nullable(true))
                    .column("technology_id", BIGINT.nullable(true))
                .constraints(
                    foreignKey("product_id").references("product", "id"),
                    foreignKey("technology_id").references("technology", "id"))
                .execute();
        });
    }
}
