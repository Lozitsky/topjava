package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.concurrent.TimeUnit;

public class MyJUnitStopWatch extends Stopwatch {
    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        String className = description.getClassName();

        System.out.println(String.format(
                "--------------------------------------------------Test-Time-----------------------------------------------------\n"+
                "Time test for %s %s %s, spent %d milliseconds"
                +"\n--------------------------------------------------Test-Time-----------------------------------------------------",
//                testName, status, TimeUnit.NANOSECONDS.toMicros(nanos)));
                className, testName, status, TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

/*    @Override
    public long runtime(TimeUnit unit) {
        return super.runtime(unit);
    }*/

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "finished", nanos);
    }
}
