package d.candy.f.com.ralgo.domain.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;
import d.candy.f.com.ralgo.data_store.sql_database.PlanEntryContract;
import d.candy.f.com.ralgo.domain.RepositoryUser;
import d.candy.f.com.ralgo.domain.structure.Plan;
import d.candy.f.com.ralgo.infra.Repository;
import d.candy.f.com.ralgo.infra.data_package.SqlEntryPackage;

/**
 * Created by daichi on 8/17/17.
 */

public class PlanEntryRWService extends Service implements RepositoryUser {

    private Repository mRepository = null;

    public PlanEntryRWService() {}

    @Override
    boolean isReady() {
        return (mRepository != null);
    }

    @Override
    public void setRepository(@NonNull Repository repository) {
        mRepository = repository;
    }

    @Nullable
    public Plan readPlanForId(long id) {
        onServiceStart();
        EntryRWService entryRWService = new EntryRWService();
        entryRWService.setRepository(mRepository);
        SqlEntryPackage result = entryRWService.readEntryForId(
                PlanEntryContract.TABLE_NAME, PlanEntryContract.COL_ID, id);

        return (result != null) ? createPlanFromEntryPackage(result) : null;
    }

    public long writePlan(@NonNull Plan plan) {
        onServiceStart();
        if (plan.getId() != DbContract.NULL_ID) {
            return (updatePlan(plan)) ? plan.getId() : DbContract.NULL_ID;
        } else {
            return savePlan(plan);
        }
    }

    private long savePlan(@NonNull Plan plan) {
        if (!isPlanValid(plan, false)) {
            return DbContract.NULL_ID;
        }

        SqlEntryPackage entryPackage = createEntryPackageFromPlan(plan, false);
        return mRepository.saveSqlEntry(PlanEntryContract.TABLE_NAME, entryPackage);
    }

    private boolean updatePlan(@NonNull Plan plan) {
        if (!isPlanValid(plan, true)) {
            return false;
        }

        SqlEntryPackage entryPackage = createEntryPackageFromPlan(plan, true);
        return mRepository.updateSqlEntry(
                PlanEntryContract.TABLE_NAME,
                entryPackage,
                PlanEntryContract.COL_ID);
    }

    @NonNull
    private Plan createPlanFromEntryPackage(@NonNull SqlEntryPackage entryPackage) {
        Plan plan = new Plan();

        plan.setId(entryPackage.getAsLongOrDefault(
                PlanEntryContract.COL_ID, DbContract.NULL_ID));

        plan.setName(entryPackage.getAsStringOrDefault(
                PlanEntryContract.COL_NAME, Plan.DEFAULT_NAME));

        plan.setNote(entryPackage.getAsStringOrDefault(
                PlanEntryContract.COL_NOTE, Plan.DEFAULT_NOTE));

        return plan;
    }

    @NonNull
    private SqlEntryPackage createEntryPackageFromPlan(@NonNull Plan plan, boolean includeId) {
        SqlEntryPackage entryPackage = new SqlEntryPackage();
        if (includeId) {
            entryPackage.put(PlanEntryContract.COL_ID, plan.getId());
        }

        entryPackage.put(PlanEntryContract.COL_NAME, plan.getName());
        entryPackage.put(PlanEntryContract.COL_NOTE, plan.getNote());

        return entryPackage;
    }

    private boolean isPlanValid(@NonNull Plan plan, boolean checkId) {
        if (checkId) {
            return PlanEntryContract.isPlanValid(plan.getId(), plan.getName());
        } else {
            return PlanEntryContract.isPlanValid(plan.getName());
        }
    }

}
